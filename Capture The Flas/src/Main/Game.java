package Main;
import java.awt.Graphics;

import Input.KeyManager;
import Input.MouseManager;
import States.StateManager;
import States.States;
import net.GameClient;
import net.GameServer;
import net.Packet;

public class Game {
	
	private GameClient client;
	private GameServer server;
	
	private Main main;
	
	
	public Game(Main main, KeyManager keyManager, MouseManager mouseManager) {
		this.main = main;
		
		StateManager.init(keyManager, mouseManager, main, this);
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
	
	public void joinServer(String ipAddress, String username) {
		// ip till "192.168.2.126"
		
		client = new GameClient(this, ipAddress);
		client.start();
		
		Packet packet = new Packet(Packet.LOGIN, username);
		client.sendData(packet.getMessage());
		
		//Packet changeHero = new Packet(Packet.CHANGE_HERO, StateManager.getGameState().getEntityManager().getLocalPlayer().getUsername() + "," + "1" + "," + "2");
		//client.sendData(changeHero.getMessage());
		
		//StateManager.changeState(States.LOBBY);
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
		
		StateManager.changeState(States.CUSTOMIZE_MENU);
	}
	
	public void restart() {
		StateManager.changeState(States.CUSTOMIZE_MENU);
	}

}
