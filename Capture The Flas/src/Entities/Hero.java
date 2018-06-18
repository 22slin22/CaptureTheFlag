package Entities;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Tanks.Tank;
import Entities.Weapons.Weapon;
import Map.Flag;
import Map.Map;
import Map.Obstacle;
import Utils.Fonts;
import Utils.Teams;

public class Hero extends Entity{
	private Map map;
	
	
	private int max_x, max_y;
	
	private double gunAngle;

	private int health;
	private boolean dead = false;
	
	private String username;
	private int team;
	
	private Tank tank;
	private Weapon weapon;
	
	private Flag flag;
	
	
	public static final int LIGHT = 0;
	public static final int MEDIUM = 1;
	public static final int HEAVY = 2;
	
	private static final int healthBarWidth = 70;
	private static final int healthBarHeight = 5;
	private int healthBarYOffset;

	
	public Hero(Map map) {
		super(map.getObstacles());
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.map = map;
	}
	
	public Hero(int team, Map map) {
		super(map.getObstacles());
		this.team = team;
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.map = map;
	}
	
	
	@Override
	public void update() {
		if(dead) {
			dead = false;
		}
		
		weapon.tick();
		tank.tick();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		renderHealthBar(g, cameraX, cameraY);
		renderNameTag(g, cameraX, cameraY);
		
		weapon.render(g, cameraX, cameraY);
		tank.render(g, cameraX, cameraY);
		
	}

	private void renderHealthBar(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.GRAY);
		g.drawRect((int)x - cameraX - healthBarWidth/2, (int)y - cameraY - healthBarYOffset, healthBarWidth, healthBarHeight);
		g.setColor(Color.GREEN);
		g.fillRect((int)x - cameraX - healthBarWidth/2, (int)y - cameraY - healthBarYOffset, healthBarWidth * health / tank.getDefaultHealth(), healthBarHeight);
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
		if(!dead) {
			health -= damage;
			if(health <= 0) {
				kill();
			}
		}
	}
	
	public void kill() {
		health = tank.getDefaultHealth();
		move(Teams.getRandomSpawn(team));
		dead = true;
		if(flag != null) {
			System.out.println("Droping flag");
			flag.drop();
			flag = null;
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
	
	public void removeHealth(int damage) {
		health -= damage;
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
		health = tank.getDefaultHealth();
	}
	
	public Tank getTank() {
		return tank;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}

}
