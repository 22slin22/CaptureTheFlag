package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Projectiles.Projectile;
import Entities.Tanks.Tank;
import Entities.Weapons.Weapon;
import Map.Map;
import Map.Obstacle;
import UI.Overlay.Killfeed;
import Utils.Fonts;
import Utils.Teams;

public class Hero extends Entity{
	private Map map;
	private Killfeed killfeed;
	
	
	private int max_x, max_y;
	
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private double gunAngle;

	private int currentHealth;
	private boolean dead = false;
	
	private String username;
	private int team;
	
	private Tank tank;
	private Weapon weapon;
	
	
	public static final int LIGHT = 0;
	public static final int MEDIUM = 1;
	public static final int HEAVY = 2;
	
	private static final int healthBarWidth = 70;
	private static final int healthBarHeight = 5;
	private int healthBarYOffset;

	
	public Hero(Map map, Killfeed killfeed) {
		super(map.getObstacles());
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.map = map;
		this.killfeed = killfeed;
	}
	
	public Hero(int team, Map map, Killfeed killfeed) {
		super(map.getObstacles());
		this.team = team;
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.map = map;
		this.killfeed = killfeed;
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
		
		
		weapon.tick();
		tank.tick();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		renderProjectiles(g, cameraX, cameraY);
		renderHealthBar(g, cameraX, cameraY);
		renderNameTag(g, cameraX, cameraY);
		
		weapon.render(g, cameraX, cameraY);
		tank.render(g, cameraX, cameraY);
		
	}
	
	protected void renderProjectiles(Graphics g, int cameraX, int cameraY) {
		synchronized (projectiles) {
			for(Projectile projectile : projectiles) {
				projectile.render(g, cameraX, cameraY);
			}
		}
	}

	private void renderHealthBar(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.GRAY);
		g.drawRect((int)x - cameraX - healthBarWidth/2, (int)y - cameraY - healthBarYOffset, healthBarWidth, healthBarHeight);
		g.setColor(Color.GREEN);
		g.fillRect((int)x - cameraX - healthBarWidth/2, (int)y - cameraY - healthBarYOffset, healthBarWidth * currentHealth / tank.getDefaultHealth(), healthBarHeight);
	}
	
	private void renderNameTag(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.GRAY);
		Fonts.drawCenteredText(g, username, (int)x - cameraX, (int)y - cameraY + tank.getRadius() + 15, Fonts.playerNameFont);
	}
	
	@Override
	public void move(float x, float y) {
		this.x += x;
		
		if(this.x < 0 + tank.getRadius()) {
			this.x = tank.getRadius();
		}
		else if(this.x > max_x - tank.getRadius()) {
			this.x = max_x - tank.getRadius();
		}
		for(Obstacle obs : obstacles) {
			if(obs.touches(this)) {
				this.x -= x;
			}
		}
		
		this.y += y;
		if(this.y < 0 + tank.getRadius()) {
			this.y = tank.getRadius();
		}
		else if(this.y > max_y - tank.getRadius()) {
			this.y = max_y - tank.getRadius();
		}
		
		for(Obstacle obs : obstacles) {
			if(obs.touches(this)) {
				this.y -= y;
			}
		}
	}
	
	public void gotHit(int damage, Hero hitter) {
		currentHealth -= damage;
		
		if(currentHealth <= 0) {
			currentHealth = tank.getDefaultHealth();
			move(Teams.getRandomSpawn(team));
			dead = true;
			killfeed.addKill(hitter, this);
		}
	}
	
	public void shoot() {
		if(weapon != null) {
			weapon.shoot();
		}
	}
	
	
	public int getRadius() {
		return tank.getRadius();
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
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setTank(Tank tank) {
		this.tank = tank;
		healthBarYOffset = tank.getRadius() + healthBarHeight + 30;
		currentHealth = tank.getDefaultHealth();
	}
	
	public Tank getTank() {
		return tank;
	}

}
