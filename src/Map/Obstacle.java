package Map;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Hero;
import Entities.Projectiles.StandardProjectile;

public class Obstacle{

	private int x, y;
	private int width, height;

	public Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	

	public void render(Graphics g, int xOffset, int yOffset) {
		g.setColor(Color.BLACK);
		g.fillRect(x - xOffset, y - yOffset, width, height);
	}

	public boolean touches(Hero player) {
		int radius = player.getRadius();
		float plX = player.getX();
		float plY = player.getY();

		if (Math.sqrt(Math.pow(x - plX, 2) + Math.pow(y - plY, 2)) < radius) {
			return true;
		}
		if (Math.sqrt(Math.pow(x + width - plX, 2) + Math.pow(y - plY, 2)) < radius) {
			return true;
		}
		if (Math.sqrt(Math.pow(x - plX, 2) + Math.pow(y + height - plY, 2)) < radius) {
			return true;
		}
		if (Math.sqrt(Math.pow(x + width - plX, 2) + Math.pow(y + height - plY, 2)) < radius) {
			return true;
		}
		
		if (x < plX && x + width > plX) {
			if (Math.abs(y + height/2 - plY) < radius + height / 2) {
				return true;
			}
		}
		if (y < plY && y + height > plY) {
			if (Math.abs(x + width/2 - plX) < radius + width / 2) {
				return true;
			}
		}

		return false;
	}
	
	public boolean touches(StandardProjectile projectile) {
		int radius = projectile.getRadius();
		float plX = projectile.getX();
		float plY = projectile.getY();

		if (Math.sqrt(Math.pow(x - plX, 2) + Math.pow(y - plY, 2)) < radius) {
			return true;
		}
		if (Math.sqrt(Math.pow(x + width - plX, 2) + Math.pow(y - plY, 2)) < radius) {
			return true;
		}
		if (Math.sqrt(Math.pow(x - plX, 2) + Math.pow(y + height - plY, 2)) < radius) {
			return true;
		}
		if (Math.sqrt(Math.pow(x + width - plX, 2) + Math.pow(y + height - plY, 2)) < radius) {
			return true;
		}
		
		if (x < plX && x + width > plX) {
			if (Math.abs(y + height/2 - plY) < radius + height / 2) {
				return true;
			}
		}
		if (y < plY && y + height > plY) {
			if (Math.abs(x + width/2 - plX) < radius + width / 2) {
				return true;
			}
		}

		return false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
