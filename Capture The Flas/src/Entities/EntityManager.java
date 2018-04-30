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
import Player.Player;

public class EntityManager {
	
	private Player player;
	private Map map;
	
	private ArrayList<Projectile> projectiles;
	//private ArrayList<Character> characters;
	
	
	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera) {
		player = new Player(keyManager, mouseManager, camera, JOptionPane.showInputDialog("Please enter a username"));
		player.setCharacter(new Runner(1000, 1000, map, this));
		
		projectiles = new ArrayList<>();
		//characters = new ArrayList<>();
		//addCharacter(runner);
		
		this.map = map;
	}
	
	
	public void tick(float elaspedTime) {
		player.tick(elaspedTime);
		
		boolean dead;
		for(int i = projectiles.size()-1; i>=0; i--) {
			dead = false;
			if(projectiles.get(i).getX() < projectiles.get(i).getRadius() || projectiles.get(i).getX() > map.getWidth() - projectiles.get(i).getRadius()
					|| projectiles.get(i).getY() < projectiles.get(i).getRadius() || projectiles.get(i).getY() > map.getHeight() - projectiles.get(i).getRadius()) {
				dead = true;
			}
			for(Obstacle obstacle : map.getObstacles()) {
				if(obstacle.touches(projectiles.get(i))) {
					dead = true;
					break;
				}
			}
			if(dead) {
				projectiles.remove(i);
			}
			else {
				projectiles.get(i).tick(elaspedTime);
			}
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		player.render(g, cameraX, cameraY);
		for(Projectile projectile : projectiles) {
			projectile.render(g, cameraX, cameraY);
		}
	}
	
	
	public Player getPlayer() {
		return player;
	}
	
	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

}
