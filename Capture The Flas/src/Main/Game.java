package Main;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import net.GameClient;
import net.GameServer;
import net.Packet;

public class Game {
	
	private int cameraX, cameraY;
	
	private Map map;
	private EntityManager entityManager;
	
	private Camera camera;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private GameClient client;
	private GameServer server;
	
	private Main main;
	
	
	public Game(Main main, KeyManager keyManager, MouseManager mouseManager) {
		this.main = main;
		client = new GameClient(this, "localhost");
		client.start();
		if(JOptionPane.showConfirmDialog(main.getDisplay().getFrame(), "Do you want to host a server?") == 0) {
			server = new GameServer(this);
			server.start();
		}
		
		map = new Map();
		camera = new Camera(map.getWidth(), map.getHeight());
		entityManager = new EntityManager(keyManager, mouseManager, map, camera, client);
		camera.setHero(entityManager.getLocalPlayer().getHero());
		
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		
		Packet packet = new Packet(Packet.LOGIN, entityManager.getLocalPlayer().getUsername());
		client.sendData(packet.getMessage());
	}
	
	public void tick() {
		entityManager.tick();
		camera.tick();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getWidth(), Main.getHeight());
		
		cameraX = camera.getX();
		cameraY = camera.getY();
		
		map.render(g, cameraX, cameraY);
		entityManager.render(g, cameraX, cameraY);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public GameClient getClient() {
		return client;
	}

}
