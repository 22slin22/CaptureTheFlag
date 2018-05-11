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
		
		if(JOptionPane.showConfirmDialog(main.getDisplay().getFrame(), "Do you want to host a server?") == 0) {
			server = new GameServer();
			server.start();
			
			client = new GameClient(this, "localhost");
		}
		else {
			String input = JOptionPane.showInputDialog("IP Addresse: ");
			if (input.equalsIgnoreCase("till")) {
				client = new GameClient(this, "192.168.2.126");
			}
			if (input.equalsIgnoreCase("l")) {
				client = new GameClient(this, "localhost");
			}
			else {
				client = new GameClient(this, input);
			}
		}
		client.start();
		
		stateManager = new StateManager(keyManager, mouseManager, client, main);
		StateManager.changeState(States.START_MENU);
		
		if(server != null) {
			StateManager.getLobby().setHost(true);
		}
		
		Packet packet = new Packet(Packet.LOGIN, StateManager.getGameState().getEntityManager().getLocalPlayer().getUsername() + "," + StateManager.getGameState().getEntityManager().getLocalPlayer().getTeam());
		client.sendData(packet.getMessage());
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

}
