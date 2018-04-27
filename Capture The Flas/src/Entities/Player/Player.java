package Entities.Player;

import java.util.ArrayList;

import Entities.Entity;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Obstacle;

public abstract class Player extends Entity{
	
	protected int max_x, max_y;
	protected int radius;
	
	protected KeyManager keyManager;
	protected MouseManager mouseManager;
	
	protected Camera camera;

	
	public Player(float x, float y, int radius, int max_x, int max_y, KeyManager keyManager, MouseManager mouseManager, Camera camera) {
		super(x, y);
		this.max_x = max_x;
		this.max_y = max_y;
		this.radius = radius;
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		this.camera = camera;
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
			if(obs.containes(this)) {
				this.x -= x;
				this.y -= y;
			}
		}
	}
	
	
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
			System.out.println("true");
			
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
