package Entities.Projectiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import Entities.Hero;
import Map.Map;

/*
 * not used yet
 */

public class LaserBeam extends Projectile{
	
	private Hero hero;
	private Map map;
	
	private int angle;
	
	private static final int WIDTH = 5;
	
	private Line2D.Float line = new Line2D.Float();
	
	
	public LaserBeam(Hero hero, Map map) {
		super(map);
		this.hero = hero;
		this.map = map;
	}
	
	public void update() {
		line.x1 = hero.getX();
		line.y1 = hero.getY();
		
		line.x2 = line.x1 + (float)(Math.cos(hero.getGunAngle()) * 300);
		line.y2 = line.y1 + (float)(-Math.sin(hero.getGunAngle()) * 300);
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(WIDTH));
		
		System.out.println("Drawing laser");
		g.drawLine(cameraX + (int)line.x1, cameraY + (int)line.y1, cameraX + (int)line.x2, cameraY + (int)line.y2);
	}

}
