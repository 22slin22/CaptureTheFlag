package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import Entities.EntityManager;
import Entities.Hero;
import Player.MultiPlayer;
import Player.Player;
import Utils.Teams;
import net.Packet;

public class Server extends Thread{
	
	private DatagramSocket socket;
	private ArrayList<MultiPlayer> connections = new ArrayList<>();
	
	private boolean started = false;
	
	
	private ServerMain serverMain;
	private EntityManager entityManager;
	
	public Server() {
		try {
			this.socket = new DatagramSocket(2222);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		serverMain = new ServerMain(this);
		serverMain.start();
		
		entityManager = serverMain.getServerGameState().getEntityManager();
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
				handleLogin(datagramPacket, data[0]);
				break;
				
			case Packet.DISCONNECT:
				System.out.println("[" + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort() + "] : " + data[0] + " has disconnected");
				handleDisconnect(datagramPacket, data);
				break;
				
			case Packet.START_GAME:
				sendDataToAllClientsIfReady(packet.getMessage());
				
				started = true;
				serverMain.setPlaying(true);
				serverMain.getServerGameState().reset();
				for(MultiPlayer player : connections) {
					if(player.isReady()) {
						entityManager.getHero(player.getUsername()).setPlaying(true);
					}
				}
				break;
				
			case Packet.RESTART:
				sendDataToAllClients(packet.getMessage());
				
				System.out.println("Server has been stopped");
				started = false;
				serverMain.setPlaying(false);
				for(Hero hero : entityManager.getHeros()) {
					hero.setPlaying(false);
				}
				break;
				
			case Packet.EQUIP_HERO:
				sendDataToAllClients(packet.getMessage());
				handleEquip(datagramPacket, data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));			// user name + tank + weapon
				break;
				
			case Packet.SHOOT:
				sendDataToAllClients(packet.getMessage());
				entityManager.heroShoot(data[0]);
				break;
				
			case Packet.CHANGE_TEAM:
				sendDataToAllClients(packet.getMessage());
				handleChangeTeam(datagramPacket, data[0], Integer.parseInt(data[1]));
				break;
				
			case Packet.UPDATE_PLAYER:
				sendDataToAllClientsExceptSender(datagramPacket, packet.getMessage());
				entityManager.updateHero(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]), Double.parseDouble(data[3]));
				break;
				
			case Packet.GUN_ANGLE:
				sendDataToAllClientsExceptSender(datagramPacket, packet.getMessage());
				entityManager.updateGunAngle(data[0], Double.parseDouble(data[1]));
				break;
				
			case Packet.PLAYER_MOVING:
				sendDataToAllClientsExceptSender(datagramPacket, packet.getMessage());
				entityManager.updateVelocity(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]));
				break;
			}
			
		}
	}
	
	
	private void handleLogin(DatagramPacket dataPacket, String username) {
		for(MultiPlayer player : connections) {
			if(dataPacket.getAddress() == player.getIpAddress() && dataPacket.getPort() == player.getPort()) {
				Packet failPacket = new Packet(Packet.INVALID_LOGIN, "You are already connected");
				sendData(failPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
				return;
			}
			if(player.getUsername().equals(username)) {
				Packet failPacket = new Packet(Packet.INVALID_LOGIN, "Name is already taken");
				sendData(failPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
				return;
			}
			Packet validLoginPacket = new Packet(Packet.VALID_LOGIN, "");
			sendData(validLoginPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
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
		Packet teamPacket = new Packet(Packet.CHANGE_TEAM, username + "," + team);
		sendData(teamPacket.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
		
		// sending all current players the new player
		Packet packet = new Packet(Packet.LOGIN, username + "," + team);
		sendDataToAllClients(packet.getMessage());
		
		// Sending the new player all current players
		for(MultiPlayer player : connections) {
			packet = new Packet(Packet.LOGIN, player.getUsername() + "," + player.getTeam());
			sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
			
			if(player.getTank() != -1 && player.getWeapon() != -1) {
				packet = new Packet(Packet.EQUIP_HERO, player.getUsername() + "," + player.getTank() + "," + player.getWeapon());
				sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
			}
		}
		//if(started) {
		//	packet = new Packet(Packet.START_GAME, "");
		//	sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
		//}
		
		connections.add(new MultiPlayer(dataPacket.getAddress(), dataPacket.getPort(), username, team));
		entityManager.addHero(username, team);
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
		entityManager.removeHero(data[0]);
	}
	
	private void handleEquip(DatagramPacket dataPacket, String username, int tank, int weapon) {
		for(MultiPlayer player : connections) {
			if(dataPacket.getAddress().equals(player.getIpAddress()) && dataPacket.getPort() == player.getPort()) {
				player.setTank(tank);
				player.setWeapon(weapon);
				
				if(!player.isReady()) {
					player.setReady(true);
					if(started) {
						// if the game has already started and the player now is ready, start the game for him
						Packet packet = new Packet(Packet.START_GAME, "");
						sendData(packet.getMessage(), dataPacket.getAddress(), dataPacket.getPort());
						entityManager.getHero(username).setPlaying(true);
					}
				}
			}
		}
		entityManager.changeHero(username, tank, weapon);
	}
	
	private void handleChangeTeam(DatagramPacket dataPacket, String username, int team) {
		for(MultiPlayer player : connections) {
			if(dataPacket.getAddress().equals(player.getIpAddress()) && dataPacket.getPort() == player.getPort()) {
				player.setTeam(team);
			}
		}
		entityManager.changeTeam(username, team);
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
	
	public void sendDataToAllClientsIfReady(byte[] message) {
		for(MultiPlayer player : connections) {
			if(player.isReady()) {
				sendData(message, player.getIpAddress(), player.getPort());
			}
		}
	}


	public void sendDataToAllClientsExceptSender(DatagramPacket dataPacket, byte[] message) {
		for(MultiPlayer player : connections) {
			if(!(dataPacket.getAddress().equals(player.getIpAddress()) && dataPacket.getPort() == player.getPort())) {
				sendData(message, player.getIpAddress(), player.getPort());
			}
		}
	}
	

	public ServerMain getServerMain() {
		return serverMain;
	}
}
