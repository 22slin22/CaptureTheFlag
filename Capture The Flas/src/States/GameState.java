package States;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Input.KeyManager;
import Main.Game;
import Main.Main;
import Map.Camera;
import Map.Map;
import Player.Player;
import UI.Overlay.Overlay;
import Utils.Teams;

public class GameState extends State{
	
	private Map map;
	private Camera camera;
	private Overlay overlay;
	
	private EntityManager entityManager;
	private Player player;
	
	private int cameraX, cameraY;
	
	
	public GameState(KeyManager keyManager, Game game) {
		map = new Map();
		camera = new Camera(map.getWidth(), map.getHeight());
		
		player = new Player(keyManager, camera, game, map);
		
		entityManager = new EntityManager(map);
		overlay = new Overlay(player.getHero(), entityManager);
		overlay.setMap(map);
		entityManager.setKillfeed(overlay.getKillfeed());
		entityManager.addHero(player.getHero());
		overlay.setEntityManager(entityManager);
	}
	

	@Override
	public void tick() {
		player.tick();
		entityManager.tick();
		camera.tick();
		overlay.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getWidth(), Main.getHeight());
		
		cameraX = camera.getX();
		cameraY = camera.getY();
			
		map.render(g, cameraX, cameraY);
		entityManager.render(g, cameraX, cameraY);
		overlay.render(g);
	}
	
	public void start() {
		entityManager.reset();
		camera.setHero(player.getHero());
		Teams.setScore(Teams.BLUE, 0);
		Teams.setScore(Teams.RED, 0);
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Overlay getOverlay() {
		return overlay;
	}

}
