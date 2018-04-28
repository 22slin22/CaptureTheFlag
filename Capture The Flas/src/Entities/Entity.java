package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import Map.Obstacle;

public abstract class Entity {
	
	protected float x, y;
	protected float vx, vy;
	protected ArrayList<Obstacle> obstacles;
	
	public Entity(float x, float y, ArrayList<Obstacle> obstacles) {
		this.x = x;
		this.y = y;
		this.obstacles = obstacles;
	}
	
	public void tick(float elapsedTime) {
		move(vx * elapsedTime, vy * elapsedTime);
	}
	
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}

}
