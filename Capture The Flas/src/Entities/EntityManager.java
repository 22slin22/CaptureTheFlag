package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.Heros.Runner;
import Entities.Projectiles.Projectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import Map.Obstacle;
import Player.LocalPlayer;
import Player.Player;
import net.GameClient;

public class EntityManager {

	private LocalPlayer localPlayer;
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;

	private ArrayList<Projectile> projectiles;
	// private ArrayList<Character> characters;

	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera, GameClient client) {
		LocalPlayer player = new LocalPlayer(keyManager, mouseManager, camera, client, JOptionPane.showInputDialog("Please enter a username"));
		player.setHero(new Runner(1000, 1000, map, this));
		localPlayer = player;
		players.add(player);

		projectiles = new ArrayList<>();
		// characters = new ArrayList<>();
		// addCharacter(runner);

		this.map = map;
	}

	public void tick() {
		for(Player player : getPlayers()) {
			player.tick();
		}

		boolean dead;
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			dead = false;
			if (projectiles.get(i).getX() < projectiles.get(i).getRadius()
					|| projectiles.get(i).getX() > map.getWidth() - projectiles.get(i).getRadius()
					|| projectiles.get(i).getY() < projectiles.get(i).getRadius()
					|| projectiles.get(i).getY() > map.getHeight() - projectiles.get(i).getRadius()) {
				dead = true;
			}
			for (Obstacle obstacle : map.getObstacles()) {
				if (obstacle.touches(projectiles.get(i))) {
					dead = true;
					break;
				}
			}
			if (dead) {
				projectiles.remove(i);
			} else {
				projectiles.get(i).tick();
			}
		}
	}

	public void render(Graphics g, int cameraX, int cameraY) {
		for(Player player : getPlayers()) {
			player.render(g, cameraX, cameraY);
		}
		
		for (Projectile projectile : projectiles) {
			projectile.render(g, cameraX, cameraY);
		}
	}

	public synchronized ArrayList<Player> getPlayers() {
		return players;
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	
	public void addPlayer(Player player) {
		getPlayers().add(player);
	}
	
	public void addPlayer(String username) {
		if(!(localPlayer.getUsername().equals(username))) {
			System.out.println("Adding " + username);
			Player player = new Player(username);
			player.setHero(new Runner(1000, 1000, map, this));
			getPlayers().add(player);
			
		}
	}
	
	public void updatePlayer(String username, float x, float y, double gunAngle) {
		for(Player player : players) {
			if(player.getUsername().equals(username)) {
				player.getHero().setX(x);
				player.getHero().setY(y);
				player.getHero().setGunAngle(gunAngle);
			}
		}
	}
	
	public void removePlayer(String username) {
		System.out.println("Deleting " + username);
		for(Player player : getPlayers()) {
			if(player.getUsername().equals(username)) {
				getPlayers().remove(player);
				break;
			}
		}
	}
	
	public LocalPlayer getLocalPlayer() {
		return localPlayer;
	}

}
