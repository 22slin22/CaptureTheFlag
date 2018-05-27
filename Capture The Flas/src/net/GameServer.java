package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import Utils.Teams;

public class GameServer extends Thread{
	
	private DatagramSocket socket;
	private ArrayList<MultiPlayer> connections = new ArrayList<>();
	
	private boolean started = false;
	
	public GameServer() {
		try {
			this.socket = new DatagramSocket(2222);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			byte[] message = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
			try {
				socket.receive(datagramPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Packet packet = new Packet(datagramPacket.getData());
			String[] data = packet.getData();
			switch (packet.getId()) {
			case Packet.LOGIN:
				System.out.println("[" + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort() + "] : " + data[0] + " has connected");
				handleLogin(datagramPacket, data);
				break;
				
			case Packet.DISCONNECT:
				System.out.println("[" + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort() + "] : " + data[0] + " has disconnected");
				handleDisconnect(datagramPacket, data);
				break;
				
			case Packet.START_GAME:
				started = true;
				sendDataToAllClients(packet.getMessage());
				break;
				
			case Packet.RESTART:
				started = false;
				sendDataToAllClients(packet.getMessage());
				break;
				
			case Packet.SHOOT:
			case Packet.FLAG_PICKUP:
			case Packet.FLAG_RETURN:
			case Packet.SCORED:
			case Packet.CHANGE_TEAM:
			case Packet.HIT:
			case Packet.CHANGE_HERO:
				sendDataToAllClients(packet.getMessage());
				break;
				
			case Packet.UPDATE_PLAYER:
				sendDataToAllClientsExceptSender(datagramPacket, packet.getMessage());
				break;
			}
			
		}
	}
	
	
	private void handleLogin(DatagramPacket dataPacket, String[] data) {
		for(MultiPlayer player : connections) {
			if(dataPacket.getAddress() == player.getIpAddress() && dataPacket.getPort() == player.getPort()) {
				Packet failPacket = new Packet(Packet.INVALID, "You are already connected");
				sendData(failPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
				return;
			}
			if(player.getUsername().equals(data[0])) {
				System.out.println("Error, same name");
				Packet failPacket = new Packet(Packet.INVALID, "Name is already taken");
				sendData(failPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
				return;
			}
		}
		
		// setting a team
		int reds = 0;
		int blues = 0;
		int team;
		for(MultiPlayer player : connections) {
			if(player.getTeam() == Teams.BLUE)
				blues++;
			if(player.getTeam() == Teams.RED)
				reds++;
		}
		if(reds < blues)
			team = Teams.RED;
		else
			team = Teams.BLUE;
		Packet teamPacket = new Packet(Packet.CHANGE_TEAM, data[0] + "," + team);
		sendData(teamPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
		
		connections.add(new MultiPlayer(dataPacket.getAddress(), dataPacket.getPort(), data[0], team));
		Packet packet = new Packet(Packet.LOGIN, data[0] + "," + team);															//username + team
		sendDataToAllClientsExceptSender(dataPacket, packet.getMessage());
		
		// Sending all current players to the new one
		for(MultiPlayer player : connections) {
			packet = new Packet(Packet.LOGIN, player.getUsername() + "," + player.getTeam());
			sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
		}
		if(started) {
			packet = new Packet(Packet.START_GAME, "");
			sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
		}
		
	}
	
	private void handleDisconnect(DatagramPacket dataPacket, String[] data) {
		for(MultiPlayer player : connections) {
			if(dataPacket.getAddress().equals(player.getIpAddress()) && dataPacket.getPort() == player.getPort()) {
				Packet packet = new Packet(Packet.DISCONNECT, data[0]);
				sendDataToAllClientsExceptSender(dataPacket, packet.getMessage());
				connections.remove(player);
				break;
			}
		}
	}
	
	
	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDataToAllClients(byte[] message) {
		for(MultiPlayer player : connections) {
			sendData(message, player.getIpAddress(), player.getPort());
		}
	}

	public void sendDataToAllClientsExceptSender(DatagramPacket dataPacket, byte[] message) {
		for(MultiPlayer player : connections) {
			if(!(dataPacket.getAddress().equals(player.getIpAddress()) && dataPacket.getPort() == player.getPort())) {
				sendData(message, player.getIpAddress(), player.getPort());
			}
		}
	}

}
