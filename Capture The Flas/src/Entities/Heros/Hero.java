package Entities.Heros;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Entities.EntityManager;
import Entities.Projectiles.Projectile;
import Entities.Weapons.Weapon;
import Map.Map;
import Map.Obstacle;
import Player.Player;
import UI.Overlay.Killfeed;
import Utils.Teams;

public abstract class Hero extends Entity{
	
	protected int max_x, max_y;
	protected int radius;
	protected float speed;
	
	protected ArrayList<Projectile> projectiles = new ArrayList<>();
	protected double gunAngle;

	protected int defaultHealth;
	protected int currentHealth;
	protected boolean dead = false;
	
	protected int team;
	
	protected EntityManager entityManager;
	protected Map map;
	protected Player player;
	protected Killfeed killfeed;
	
	protected Weapon weapon;
	
	
	public static final int LIGHT = 0;
	public static final int MEDIUM = 1;
	public static final int HEAVY = 2;

	
	public Hero(int team, int radius, float speed, int defaultHealth, Map map, EntityManager entityManager, Player player, Killfeed killfeed) {
		super(Teams.getRandomSpawn(team), map.getObstacles());
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.radius = radius;
		this.speed = speed;
		this.defaultHealth = defaultHealth;
		this.map = map;
		this.entityManager = entityManager;
		this.player = player;
		this.killfeed = killfeed;
		
		currentHealth = defaultHealth;
	}
	
	
	@Override
	public void tick() {
		super.tick();
		
		if(dead) {
			dead = false;
		}
		
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
		
		if(weapon != null) {
			weapon.tick();
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		renderProjectiles(g, cameraX, cameraY);
		renderHealthBar(g, cameraX, cameraY);
		
		if(weapon != null) {
			weapon.render(g, cameraX, cameraY);
		}
		renderBody(g, cameraX, cameraY);
	}
	
	protected void renderProjectiles(Graphics g, int cameraX, int cameraY) {
		synchronized (projectiles) {
			for(Projectile projectile : projectiles) {
				projectile.render(g, cameraX, cameraY);
			}
		}
	}
	
	protected abstract void renderBody(Graphics g, int cameraX, int cameraY);
	
	@Override
	public void move(float x, float y) {
		this.x += x;
		
		if(this.x < 0 + radius) {
			this.x = radius;
		}
		else if(this.x > max_x - radius) {
			this.x = max_x - radius;
		}
		for(Obstacle obs : obstacles) {
			if(obs.touches(this)) {
				this.x -= x;
			}
		}
		
		this.y += y;
		if(this.y < 0 + radius) {
			this.y = radius;
		}
		else if(this.y > max_y - radius) {
			this.y = max_y - radius;
		}
		
		for(Obstacle obs : obstacles) {
			if(obs.touches(this)) {
				this.y -= y;
			}
		}
	}
	
	public void gotHit(int damage, Player hitter) {
		currentHealth -= damage;
		
		if(currentHealth <= 0) {
			currentHealth = defaultHealth;
			move(Teams.getRandomSpawn(team));
			dead = true;
			killfeed.addKill(hitter, player);
		}
	}
	
	
	private int healthBarWidth = 70;
	private int healthBarHeight = 5;
	private int healthBarYOffset = radius + healthBarHeight + 30;
	protected void renderHealthBar(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.GRAY);
		g.drawRect((int)x - cameraX - healthBarWidth/2, (int)y - cameraY - healthBarYOffset, healthBarWidth, healthBarHeight);
		g.setColor(Color.GREEN);
		g.fillRect((int)x - cameraX - healthBarWidth/2, (int)y - cameraY - healthBarYOffset, healthBarWidth * currentHealth / defaultHealth, healthBarHeight);
	}
	
	public void shoot() {
		if(weapon != null) {
			weapon.shoot();
		}
	}
	
	
	public int getRadius() {
		return radius;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setGunAngle(double d) {
		this.gunAngle = d;
	}
	
	public double getGunAngle() {
		return gunAngle;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public int getTeam() {
		return team;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}

}
