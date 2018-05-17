package Main;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import Input.KeyManager;
import Input.MouseManager;
import States.StateManager;
import States.States;
import net.GameClient;
import net.GameServer;
import net.Packet;

public class Game {
	private StateManager stateManager;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private GameClient client;
	private GameServer server;
	
	private Main main;
	
	
	public Game(Main main, KeyManager keyManager, MouseManager mouseManager) {
		this.main = main;
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		
		
		stateManager = new StateManager(keyManager, mouseManager, main, this);
		StateManager.changeState(States.START_MENU);
	}
	
	public void tick() {
		StateManager.getActiveState().tick();
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
	
	public void joinServer(String ipAddress) {
		// ip till "192.168.2.126"
		
		client = new GameClient(this, ipAddress);
		client.start();
		
		Packet packet = new Packet(Packet.LOGIN, StateManager.getGameState().getEntityManager().getLocalPlayer().getUsername());
		client.sendData(packet.getMessage());
		
		StateManager.changeState(States.LOBBY);
	}
	
	public void createServer() {
		server = new GameServer();
		server.start();
		
		client = new GameClient(this, "localhost");
		client.start();
		
		StateManager.getLobby().setHost(true);
		StateManager.getWinScreen().setHost(true);
		
		Packet packet = new Packet(Packet.LOGIN, StateManager.getGameState().getEntityManager().getLocalPlayer().getUsername());
		client.sendData(packet.getMessage());
		
		StateManager.changeState(States.LOBBY);
	}
	
	public void restart() {
		StateManager.getGameState().restart();
		StateManager.changeState(States.LOBBY);
	}

}
