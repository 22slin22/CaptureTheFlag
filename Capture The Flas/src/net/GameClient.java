package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Main.Game;

public class GameClient extends Thread{
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;
	
	public GameClient(Game game, String ipAddress) {
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			byte[] message = new byte[1024];
			DatagramPacket dataPacket = new DatagramPacket(message, message.length);
			try {
				socket.receive(dataPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Packet packet = new Packet(dataPacket.getData());
			String[] data = packet.getData();
			
			switch(packet.getId()) {
			case Packet.LOGIN:
				game.getEntityManager().addPlayer(packet.getData()[0]);
				break;
				
			case Packet.DISCONNECT:
				game.getEntityManager().removePlayer(packet.getData()[0]);
				break;
				
			case Packet.UPDATE_PLAYER:
				float x = Float.parseFloat(data[1]);
				float y = Float.parseFloat(data[2]);
				double gunAngle = Double.parseDouble(data[3]);
				game.getEntityManager().updatePlayer(data[0], x, y, gunAngle);
				break;
				
			case Packet.SHOOT:
				game.getEntityManager().playerShoot(data[0]);
				break;
				
			case Packet.HIT:
				game.getEntityManager().hitPlayer(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));		// username attack, username got hit, amount, projectile id
				break;
			}
		}
	}
	
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 2222);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
