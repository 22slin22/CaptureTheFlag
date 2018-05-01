package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import Main.Game;

public class GameServer extends Thread{
	
	private DatagramSocket socket;
	private Game game;
	private ArrayList<MultiPlayer> connectedPlayers = new ArrayList<>();
	
	public GameServer(Game game) {
		this.game = game;
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
				handleLogin(datagramPacket, packet.getData());
				break;
				
			case Packet.DISCONNECT:
				System.out.println("[" + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort() + "] : " + data[0] + " has disconnected");
				handleDisconnect(datagramPacket, data);
				break;
				
			case Packet.UPDATE_PLAYER:
			case Packet.SHOOT:
				sendDataToAllClients(packet.getMessage());
				break;
			}
			
		}
	}
	
	
	private void handleLogin(DatagramPacket dataPacket, String[] data) {
		for(MultiPlayer player : connectedPlayers) {
			if(dataPacket.getAddress() == player.getIpAddress() && dataPacket.getPort() == player.getPort()) {
				return;
			}
		}
		connectedPlayers.add(new MultiPlayer(dataPacket.getAddress(), dataPacket.getPort(), data[0]));
		Packet packet = new Packet(Packet.LOGIN, new String(data[0]));
		sendDataToAllClients(packet.getMessage());
		
		// Sending all current players to the new one
		for(MultiPlayer player : connectedPlayers) {
			packet = new Packet(Packet.LOGIN, player.getUsername());
			sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
		}
		
	}
	
	private void handleDisconnect(DatagramPacket dataPacket, String[] data) {
		for(MultiPlayer player : connectedPlayers) {
			if(dataPacket.getAddress().equals(player.getIpAddress()) && dataPacket.getPort() == player.getPort()) {
				Packet packet = new Packet(Packet.DISCONNECT, data[0]);
				sendDataToAllClients(packet.getMessage());
				connectedPlayers.remove(player);
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
		for(MultiPlayer player : connectedPlayers) {
			sendData(message, player.getIpAddress(), player.getPort());
		}
	}

}
