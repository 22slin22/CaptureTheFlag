package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Entities.EntityManager;
import Entities.Hero;
import Main.Game;
import States.StateManager;
import States.States;
import UI.Overlay.NotificationManager;
import Utils.Teams;

public class GameClient extends Thread{
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;
	private EntityManager entityManager;
	private NotificationManager notificationManager;
	
	public GameClient(Game game, String ipAddress, NotificationManager notificationManager) {
		this.game = game;
		entityManager = StateManager.getGameState().getEntityManager();
		this.notificationManager = notificationManager;
		
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
				entityManager.addHero(data[0], Integer.parseInt(data[1]));
				break;
				
			case Packet.DISCONNECT:
				entityManager.removeHero(data[0]);
				break;
				
			case Packet.UPDATE_PLAYER:
				float x = Float.parseFloat(data[1]);
				float y = Float.parseFloat(data[2]);
				double gunAngle = Double.parseDouble(data[3]);
				entityManager.updateHero(data[0], x, y, gunAngle);
				break;
				
			case Packet.GUN_ANGLE:
				entityManager.updateGunAngle(data[0], Double.parseDouble(data[1]));
				break;
				
			case Packet.PLAYER_MOVING:
				entityManager.updateVelocity(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]));
				break;
				
			case Packet.SHOOT:
				entityManager.heroShoot(data[0]);
				break;
				
			case Packet.HIT:
				entityManager.hitHero(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));		// username got hit, damage, projectile id
				break;
				
			case Packet.KILL:
				entityManager.killHero(data[0], data[1]);
				break;
				
			case Packet.FLAG_PICKUP:
				entityManager.flagPickup(data[0], Integer.parseInt(data[1]));		// username  ,  flag index
				notificationManager.addNotification(data[0], "picked up the flag");
				break;
				
			case Packet.FLAG_RETURN:
				entityManager.flagReturn(Integer.parseInt(data[0]));
				break;
				
			case Packet.SCORED:
				Teams.increaseScore(StateManager.getGameState().getEntityManager().getFlags().get(Integer.parseInt(data[1])).getCarrier().getTeam());
				entityManager.scored(data[0], Integer.parseInt(data[1]));		// username, flagIndex
				notificationManager.addNotification(data[0], "has scored");
				break;
				
			case Packet.START_GAME:
				StateManager.changeState(States.GAME_STATE);
				StateManager.getGameState().start();
				break;
				
			case Packet.CHANGE_TEAM:
				entityManager.changeTeam(data[0], Integer.parseInt(data[1]));
				break;
				
			case Packet.EQUIP_HERO:
				entityManager.changeHero(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));		// username  ,  tank  ,  weapon
				entityManager.getHero(data[0]).setPlaying(true);
				break;
				
			case Packet.RESTART:
				game.restart();
				synchronized(entityManager.getHeros()) {
					for(Hero hero : entityManager.getHeros()) {
						hero.setPlaying(false);
					}
				}
				break;
				
			case Packet.VALID_LOGIN:
				System.out.println("Valid Login");
				StateManager.changeState(States.CUSTOMIZE_MENU);
				break;
				
			case Packet.INVALID_LOGIN:
				StateManager.getStartMenu().showError(data[0]);
				break;
				
			case Packet.REMOVE_PROJECTILE:
				entityManager.removeProjectile(Integer.parseInt(data[0]));
				break;
				
			case Packet.WIN:
				StateManager.changeState(States.WIN_SCREEN);
				StateManager.getWinScreen().setBestPlayers();
				StateManager.getWinScreen().setWinner(Integer.parseInt(data[0]));
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
