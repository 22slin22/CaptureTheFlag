package Entities.Heros;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Map.Map;
import Player.Player;
import UI.Overlay.Killfeed;
import Utils.Teams;

public class Light extends Hero{
	
	private static final float SPEED = 0.25f;
	private static final int DIAMETER = 40;
	private static final int DEFAULT_HEALTH = 100;

	
	public Light(int team, Map map, EntityManager entityManager, Player player, Killfeed killfeed) {
		super(team, DIAMETER/2, SPEED, DEFAULT_HEALTH, map, entityManager, player, killfeed);
	}
	
	
	protected void renderBody(Graphics g, int cameraX, int cameraY) {
		g.setColor(Teams.getColor(team));
		g.fillOval((int)x - cameraX - radius, (int)y - cameraY - radius, radius*2, radius*2);
		g.setColor(new Color(50, 50, 50));
		g.drawOval((int)x - cameraX - radius, (int)y - cameraY - radius, radius*2, radius*2);
	}

}

