package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Entities.EntityManager;
import Main.Game;
import Main.GameState;
import States.StateManager;
import States.States;

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
			case Packet.INVALID:
				System.out.println(data[0]);
				JOptionPane.showMessageDialog(game.getMain().getDisplay().getFrame(), data[0]);
				System.exit(0);
				break;
				
			case Packet.LOGIN:
				System.out.println("Adding a hero");
				StateManager.getGameState().getEntityManager().addHero(data[0], Integer.parseInt(data[1]));
				break;
				
			case Packet.DISCONNECT:
				StateManager.getGameState().getEntityManager().removeHero(data[0]);
				break;
				
			case Packet.UPDATE_PLAYER:
				float x = Float.parseFloat(data[1]);
				float y = Float.parseFloat(data[2]);
				double gunAngle = Double.parseDouble(data[3]);
				StateManager.getGameState().getEntityManager().updateHero(data[0], x, y, gunAngle);
				break;
				
			case Packet.SHOOT:
				StateManager.getGameState().getEntityManager().heroShoot(data[0]);
				break;
				
			case Packet.HIT:
				StateManager.getGameState().getEntityManager().hitHero(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));		// username attack, username got hit, damage, projectile id
				break;
				
			case Packet.FLAG_PICKUP:
				StateManager.getGameState().getEntityManager().flagPickup(data[0], Integer.parseInt(data[1]));
				break;
				
			case Packet.FLAG_RETURN:
				StateManager.getGameState().getEntityManager().flagReturn(Integer.parseInt(data[0]));
				break;
				
			case Packet.SCORED:
				StateManager.getGameState().getEntityManager().score(Integer.parseInt(data[0]));		// 0 = flagIndex		1 = team		2 = username
				break;
				
			case Packet.START_GAME:
				StateManager.changeState(States.GAME_STATE);
				StateManager.getGameState().start();
				break;
				
			case Packet.CHANGE_TEAM:
				StateManager.getGameState().getEntityManager().changeTeam(data[0], Integer.parseInt(data[1]));
				break;
				
			case Packet.EQUIP_HERO:
				StateManager.getGameState().getEntityManager().changeHero(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));		// username  ,  tank  ,  weapon
				break;
				
			case Packet.RESTART:
				game.restart();
				break;
				
			case Packet.VALID_LOGIN:
				System.out.println("Valid Login");
				StateManager.changeState(States.CUSTOMIZE_MENU);
				break;
				
			case Packet.INVALID_LOGIN:
				StateManager.getStartMenu().showError(data[0]);
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
