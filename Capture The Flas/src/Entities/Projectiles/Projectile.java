package Entities.Projectiles;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Map.Map;
import Map.Obstacle;

public abstract class Projectile extends Entity{
	
	protected boolean remove = false;
	
	protected Map map;
	
	public Projectile(Map map) {
		super(map.getObstacles());
	}

	public Projectile(float x, float y, float vx, float vy, int radius, Map map) {
		super(x, y, map.getObstacles());
		this.map = map;
	}
	public Projectile(float x, float y, Map map) {
		super(x, y, map.getObstacles());
		this.map = map;
	}

	
	@Override
	public abstract void render(Graphics g, int cameraX, int cameraY);
	
	public boolean isRemove() {
		return remove;
	}
	
	public void setRemove(boolean remove) {
		this.remove = remove;
	}

}
