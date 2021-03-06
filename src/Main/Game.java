package Main;

import java.awt.Graphics;

import Input.KeyManager;
import Input.MouseManager;
import Server.Server;
import States.StateManager;
import States.States;
import net.GameClient;
import net.Packet;

public class Game {
	
	private GameClient client;
	private Server server;
	
	private Main main;
	
	
	public Game(Main main, KeyManager keyManager, MouseManager mouseManager) {
		this.main = main;
		
		StateManager.init(keyManager, main, this);
		StateManager.changeState(States.START_MENU);
	}
	
	public void tick() {
		StateManager.getActiveState().tick();
		
		MouseManager.tick();
	}
	
	public void render(Graphics g) {
		StateManager.getActiveState().render(g);
	}
	
	public GameClient getClient() {
		return client;
	}
	
	public Main getMain() {
		return main;
	}
	
	public void joinServer(String ipAddress, String username) {
		// ip till "192.168.2.126"
		
		client = new GameClient(this, ipAddress, StateManager.getGameState().getOverlay().getNotificationManager());
		client.start();
		
		Packet packet = new Packet(Packet.LOGIN, username);
		client.sendData(packet.getMessage());
	}
	
	public void createServer(String username) {
		server = new Server();
		server.start();
		
		client = new GameClient(this, "localhost", StateManager.getGameState().getOverlay().getNotificationManager());
		client.start();
		
		StateManager.getLobby().setHost(true);
		StateManager.getWinScreen().setHost(true);
		
		Packet packet = new Packet(Packet.LOGIN, username);
		client.sendData(packet.getMessage());
		
		StateManager.changeState(States.CUSTOMIZE_MENU);
	}
	
	public void restart() {
		StateManager.changeState(States.CUSTOMIZE_MENU);
	}

}
