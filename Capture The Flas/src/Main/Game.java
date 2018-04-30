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
	
	private long lastFrameTime;
	
	
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
		entityManager = new EntityManager(keyManager, mouseManager, map, camera);
		camera.setHero(entityManager.getPlayer().getHero());
		
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		
		Packet packet = new Packet(Packet.LOGIN, "slin");
		client.sendData(packet.getMessage());
		
		lastFrameTime = System.currentTimeMillis();
	}
	
	public void tick() {
		long now = System.currentTimeMillis();
		long elapsedTime = now - lastFrameTime;
		lastFrameTime = now;
		
		entityManager.tick(elapsedTime);
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

}
