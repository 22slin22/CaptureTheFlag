package Main;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import Player.Player;
import States.State;
import States.StateManager;
import States.States;
import UI.Overlay.Overlay;
import Utils.Teams;

public class GameState extends State{
	
	private Map map;
	private Camera camera;
	private Overlay overlay;
	
	private EntityManager entityManager;
	private Player player;
	
	private int cameraX, cameraY;
	
	private static final int SCORE_TO_WIN = 1;
	
	
	public GameState(KeyManager keyManager, MouseManager mouseManager, Game game, Main main) {
		overlay = new Overlay();
		map = new Map();
		overlay.setMap(map);
		camera = new Camera(map.getWidth(), map.getHeight());
		
		entityManager = new EntityManager(keyManager, map, game, main.getDisplay().getFrame(), overlay.getKillfeed());
		player = new Player(keyManager, camera, game, entityManager, map, overlay.getKillfeed());
		entityManager.addHero(player.getHero());
		overlay.setEntityManager(entityManager);
		//camera.setHero(entityManager.getLocalPlayer().getHero());
	}
	

	@Override
	public void tick() {
		player.tick();
		entityManager.tick();
		camera.tick();
		overlay.tick();
		
		if(Teams.getScore(Teams.BLUE) >= SCORE_TO_WIN) {
			StateManager.changeState(States.WIN_SCREEN);
			StateManager.getWinScreen().setWinner(Teams.BLUE);
		}
		else if(Teams.getScore(Teams.RED) >= SCORE_TO_WIN) {
			StateManager.changeState(States.WIN_SCREEN);
			StateManager.getWinScreen().setWinner(Teams.RED);
		}
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

}
