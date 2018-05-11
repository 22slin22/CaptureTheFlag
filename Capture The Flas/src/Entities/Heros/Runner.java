package Entities.Heros;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Display.UI.Killfeed;
import Entities.EntityManager;
import Entities.Projectiles.StandardProjectile;
import Map.Map;
import Player.Player;
import Utils.Teams;

public class Runner extends Hero{
	
	private final static float SPEED = 0.25f;
	private final static int DIAMETER = 40;
	
	private final static float COOLDOWN = 0.2f;
	private final static float PROJECTILE_SPEED = 1f;
	

	public Runner(int team, Map map, EntityManager entityManager, Player player, Killfeed killfeed) {
		super(team, DIAMETER/2, SPEED, map, COOLDOWN, entityManager, player, killfeed);
	}
	

	@Override
	public void render(Graphics g, int cameraX, int cameraY) {
		super.render(g, cameraX, cameraY);
		
		g.setColor(Teams.getColor(team));
		Graphics2D g2d = (Graphics2D) g.create();
		/*	Shotgun
		Rectangle rect = new Rectangle((int)x - cameraX - 15 + 5,			// -10 to center it and +5 to let it stick out
				(int)y - cameraY - 15, 30, 30);
		*/
		
		Rectangle rect = new Rectangle((int)x - cameraX - 20 + 5, (int)y - cameraY - 12, 40, 24);		// -20 to center it and +5 to let it stick out
		g2d.rotate(-gunAngle, x-cameraX, y-cameraY);
		g2d.fill(rect);
		
		g2d.setColor(new Color(50, 50, 50));
		g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		g2d.dispose();
		g2d = (Graphics2D) g.create();
		
		g.setColor(Teams.getColor(team));
		g.fillOval((int)x - cameraX - DIAMETER/2, (int)y - cameraY - DIAMETER/2, DIAMETER, DIAMETER);
		g.setColor(new Color(50, 50, 50));
		g.drawOval((int)x - cameraX - DIAMETER/2, (int)y - cameraY - DIAMETER/2, DIAMETER, DIAMETER);
	}

	@Override
	public void shoot() {
		float spawnX = x + (float)(Math.cos(gunAngle) * 30);
		float spawnY = y + (float)(-Math.sin(gunAngle) * 30);
		
		synchronized (projectiles) {
			projectiles.add(new StandardProjectile(spawnX, spawnY, gunAngle, PROJECTILE_SPEED, map));
		}
		lastShot = System.currentTimeMillis();
	}

}
