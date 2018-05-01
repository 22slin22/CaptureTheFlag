package Entities.Heros;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Entities.EntityManager;
import Entities.Projectiles.Projectile;
import Map.Map;
import Map.Obstacle;
import Player.Player;
import net.Packet;

public abstract class Hero extends Entity{
	
	protected int max_x, max_y;
	protected int radius;
	protected float speed;
	
	protected ArrayList<Projectile> projectiles = new ArrayList<>();
	protected double gunAngle;
	protected float cooldown;
	private double lastShot;
	public static final int DAMAGE = 20;
	private int health = 100;
	
	protected EntityManager entityManager;
	protected Map map;

	
	public Hero(float x, float y, int radius, float speed, Map map, float cooldown, EntityManager entityManager) {
		super(x, y, map.getObstacles());
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.radius = radius;
		this.speed = speed;
		this.map = map;
		this.cooldown = cooldown;
		this.entityManager = entityManager;
	}
	
	
	@Override
	public void tick() {
		super.tick();
		
		synchronized (projectiles) {
			for(Projectile projectile : projectiles) {
				projectile.tick();
			}
			
			for (int i = projectiles.size() - 1; i >= 0; i--) {
				if(projectiles.get(i).isRemove()) {
					projectiles.remove(i);
				}
			}
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		synchronized (projectiles) {
			for(Projectile projectile : projectiles) {
				projectile.render(g, cameraX, cameraY);
			}
		}
	}
	
	@Override
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
		
		if(this.x < 0 + radius) {
			this.x = radius;
		}
		else if(this.x > max_x - radius) {
			this.x = max_x - radius;
		}
		if(this.y < 0 + radius) {
			this.y = radius;
		}
		else if(this.y > max_y - radius) {
			this.y = max_y - radius;
		}
		
		for(Obstacle obs : obstacles) {
			if(obs.touches(this)) {
				this.x -= x;
				this.y -= y;
			}
		}
	}
	
	public void gotHit(int damage) {
		health -= damage;
		System.out.println(this + ": " + health);
	}
	
	
	public abstract void shoot();
	
	
	public int getRadius() {
		return radius;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public double getLastShot() {
		return lastShot;
	}
	
	public void setLastShot(double lastShot) {
		this.lastShot = lastShot;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public void setGunAngle(double d) {
		this.gunAngle = d;
	}
	
	public double getGunAngle() {
		return gunAngle;
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}

}
