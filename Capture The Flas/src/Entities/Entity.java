package Entities;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import Map.Obstacle;

public abstract class Entity {
	
	protected float x, y;
	protected float vx, vy;
	protected ArrayList<Obstacle> obstacles;
	
	private long frameTime;
	
	public Entity(float x, float y, ArrayList<Obstacle> obstacles) {
		this.x = x;
		this.y = y;
		this.obstacles = obstacles;
		
		frameTime = System.currentTimeMillis();
	}
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		
		frameTime = System.currentTimeMillis();
	}
	
	public Entity(Point pos, ArrayList<Obstacle> obstacles) {
		this.x = pos.x;
		this.y = pos.y;
		this.obstacles = obstacles;
		
		frameTime = System.currentTimeMillis();
	}
	
	public Entity(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}
	
	
	public void tick() {
		long elapsedTime = System.currentTimeMillis() - frameTime;
		frameTime = System.currentTimeMillis();
		
		if(vx != 0 || vy != 0) {
			move(vx * elapsedTime, vy * elapsedTime);
		}
		update();
	}
	
	protected void update() {
		
	}
	
	public abstract void render(Graphics g, int cameraX, int cameraY);
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void move(Point pos) {
		x = pos.x;
		y = pos.y;
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
