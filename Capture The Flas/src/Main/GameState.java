package Main;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import States.State;
import States.States;
import States.StateManager;
import UI.Overlay.Overlay;
import Utils.Teams;
import net.GameClient;
import net.GameServer;

public class GameState extends State{
	
	private int cameraX, cameraY;
	private static final int SCORE_TO_WIN = 1;
	
	private Map map;
	private EntityManager entityManager;
	
	private Camera camera;
	private Overlay overlay;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private Game game;
	private Main main;
	
	
	public GameState(KeyManager keyManager, MouseManager mouseManager, Game game, Main main) {
		overlay = new Overlay();
		map = new Map();
		overlay.setMap(map);
		camera = new Camera(map.getWidth(), map.getHeight());
		
		entityManager = new EntityManager(keyManager, mouseManager, map, camera, game, main.getDisplay().getFrame(), overlay.getKillfeed());
		overlay.setEntityManager(entityManager);
		camera.setHero(entityManager.getLocalPlayer().getHero());
	}
	

	@Override
	public void tick() {
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
	
	public void restart() {
		entityManager.restart();
		Teams.setScore(Teams.BLUE, 0);
		Teams.setScore(Teams.RED, 0);
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
