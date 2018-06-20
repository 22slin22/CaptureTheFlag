package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import Entities.EntityManager;
import Entities.Hero;
import Main.Game;
import Player.Player;
import States.StateManager;
import Utils.Collisions;
import Utils.Teams;
import net.Packet;

public class Flag {
	
	protected int x, y;
	protected int team;
	
	// true if the flag is not on the flag point
	protected boolean isPickedUp = false;
	// true if a playing is carrying the flag
	protected boolean isCarried = false;
	
	protected static final int FLAG_HEIGHT = 80;
	protected static final int FLAG_WIDTH = 40;
	
	protected static final int PICKUP_RADIUS = 20;
	
	protected EntityManager entityManager;
	protected Hero carrier;
	
	
	public Flag(EntityManager entityManager, int team) {
		this.entityManager = entityManager;
		
		if(team == Teams.BLUE) {
			this.x = Teams.FLAG_BLUE_X;
			this.y = Teams.FLAG_BLUE_Y;
		}
		if(team == Teams.RED) {
			this.x = Teams.FLAG_RED_X;
			this.y = Teams.FLAG_RED_Y;
		}
		this.team = team;
	}
	
	public void tick() {
		if(carrier != null) {
			x = (int)carrier.getX();
			y = (int)carrier.getY();
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		if(true) {
			g.setColor(Color.BLACK);
			g.drawLine(x - cameraX, y - cameraY, x - cameraX, y - cameraY - FLAG_HEIGHT);
			g.setColor(Teams.getColor(team));
			Polygon flag = new Polygon(new int[] {x-cameraX, x-cameraX, x-cameraX + FLAG_WIDTH}, new int[] {y-cameraY - FLAG_HEIGHT, y-cameraY - FLAG_HEIGHT/2, y-cameraY - FLAG_HEIGHT*3/4}, 3) ;
			g.fillPolygon(flag);
			
			//if stolen draw tanslucend flag;
			if(isPickedUp) {
				int flagX = Teams.getFlagSpawnX(team);
				int flagY = Teams.getFlagSpawnY(team);
				g.setColor(new Color(0, 0, 0, 64));
				g.drawLine(flagX - cameraX, flagY - cameraY, flagX - cameraX, flagY - cameraY - FLAG_HEIGHT);
				g.setColor(new Color((Teams.getColor(team).getRed()), (Teams.getColor(team).getGreen()), (Teams.getColor(team).getBlue()), 64));
				Polygon stolenFlag = new Polygon(new int[] {flagX-cameraX, flagX-cameraX, flagX-cameraX + FLAG_WIDTH}, new int[] {flagY-cameraY - FLAG_HEIGHT, flagY-cameraY - FLAG_HEIGHT/2, flagY-cameraY - FLAG_HEIGHT*3/4}, 3) ;
				g.fillPolygon(stolenFlag);
			}
		}
	}
	
	
	public boolean checkCollision(Hero hero) {
		if(hero.isPlaying())
			return Collisions.circleCollision(this.x, this.y, PICKUP_RADIUS, hero.getX(), hero.getY(), hero.getRadius());
		return false;
	}
	
	public boolean checkPickup(Hero hero) {
		return checkCollision(hero) && hero.getTeam() != team && !isCarried;
	}
	
	public boolean checkReturn(Hero hero) {
		return checkCollision(hero) && hero.getTeam() == team && !isCarried && isPickedUp;
	}
	
	public boolean checkScored() {
		if(carrier != null)
			return Collisions.circleCollision(Teams.getFlagSpawnX(carrier.getTeam()), Teams.getFlagSpawnY(carrier.getTeam()), PICKUP_RADIUS, carrier.getX(), carrier.getY(), carrier.getRadius());
		return false;
	}
	
	public void returnFlag() {
		isPickedUp = false;
		isCarried = false;
		carrier = null;
		x = Teams.getFlagSpawnX(team);
		y = Teams.getFlagSpawnY(team);
	}
	
	public void drop() {
		carrier = null;
		isCarried = false;
	}
	
	public void setCarrier(Hero hero) {
		carrier = hero;
		isCarried = true;
		isPickedUp = true;
	}
	
	public Hero getCarrier() {
		return carrier;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getTeam() {
		return team;
	}

}
