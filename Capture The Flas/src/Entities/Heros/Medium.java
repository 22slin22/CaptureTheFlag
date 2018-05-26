package Entities.Heros;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Entities.Projectiles.StandardProjectile;
import Map.Map;
import Player.Player;
import UI.Overlay.Killfeed;
import Utils.Teams;

public class Medium extends Hero{
	
	private final static float SPEED = 0.25f;
	private final static int DIAMETER = 40;
	

	public Medium(int team, Map map, EntityManager entityManager, Player player, Killfeed killfeed) {
		super(team, DIAMETER/2, SPEED, map, entityManager, player, killfeed);
	}
	

	protected void renderBody(Graphics g, int cameraX, int cameraY) {
		g.setColor(Teams.getColor(team));
		g.fillOval((int)x - cameraX - radius, (int)y - cameraY - radius, radius*2, radius*2);
		g.setColor(new Color(50, 50, 50));
		g.drawOval((int)x - cameraX - radius, (int)y - cameraY - radius, radius*2, radius*2);
	}

}

