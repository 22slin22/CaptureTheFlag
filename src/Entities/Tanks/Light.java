package Entities.Tanks;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Hero;
import Utils.Teams;

public class Light extends Tank{
	
	private static final float SPEED = 0.31f;
	private static final int RADIUS = 19;
	private static final int DEFAULT_HEALTH = 100;

	
	public Light(Hero hero) {
		super(hero, SPEED, RADIUS, DEFAULT_HEALTH);
	}
	

	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Teams.getColor(hero.getTeam()));
		g.fillOval((int)x - cameraX - RADIUS, (int)y - cameraY - radius, radius*2, radius*2);
		g.setColor(new Color(50, 50, 50));
		g.drawOval((int)x - cameraX - RADIUS, (int)y - cameraY - radius, radius*2, radius*2);
	}

}

