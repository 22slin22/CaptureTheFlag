package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import Map.Obstacle;

public abstract class Entity {
	
	protected float x, y;
	protected ArrayList<Obstacle> obstacles;
	
	public Entity(float x, float y, ArrayList<Obstacle> obstacles) {
		this.x = x;
		this.y = y;
		this.obstacles = obstacles;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g, int cameraX, int cameraY);
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

}
