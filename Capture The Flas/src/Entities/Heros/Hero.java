package Entities.Player;

import java.util.ArrayList;

import Entities.Entity;
import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import Map.Obstacle;

public abstract class Player extends Entity{
	
	protected int max_x, max_y;
	protected int radius;
	
	protected float cooldown;
	private double lastShot;
	
	protected KeyManager keyManager;
	protected MouseManager mouseManager;
	
	protected Camera camera;
	protected EntityManager entityManager;

	
	public Player(float x, float y, int radius, Map map, float cooldown, KeyManager keyManager, MouseManager mouseManager, Camera camera, EntityManager entityManager) {
		super(x, y, map.getObstacles());
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.radius = radius;
		this.cooldown = cooldown;
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		this.camera = camera;
		this.entityManager = entityManager;
	}
	
	
	public void tick() {
		if(mouseManager.isLeftButton()) {
			if(System.currentTimeMillis() - lastShot > cooldown*1000) {
				shoot(getMouseAngle());
				lastShot = System.currentTimeMillis();
			}
		}
	}
	
	public void move(float x, float y, ArrayList<Obstacle> obstacles) {
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
	
	protected abstract void shoot(double angle);
	
	
	public int getRadius() {
		return radius;
	}
	
	public double getMouseAngle() {
		// displayX and displayY    =    Position on the screen
		
		int displayX = (int)x - camera.getX();
		int displayY = (int)y - camera.getY();
		
		int mouseX = mouseManager.getX();
		int mouseY = mouseManager.getY();
		
		double r = Math.sqrt(Math.pow(displayY - mouseY, 2) + Math.pow(mouseX - displayX, 2));
		
		double angle = Math.asin((displayY - mouseY) / r);
		
		if(mouseX < displayX) {
			if(angle > 0) {
				angle = Math.PI - angle;
			}
			else {
				angle = -Math.PI - angle;
			}
		}
		
		return angle;
	}

}
