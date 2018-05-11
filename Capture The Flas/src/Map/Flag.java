package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import Entities.EntityManager;
import Player.Player;
import Utils.Collisions;
import Utils.Teams;
import net.GameClient;
import net.Packet;

public class Flag {
	
	private int x, y;
	private int team;
	
	private boolean isPickedUp = false;
	private boolean isCarried = false;
	
	private static final int FLAG_HEIGHT = 80;
	private static final int FLAG_WIDTH = 40;
	
	public static final int PICKUP_RADIUS = 20;
	
	private EntityManager entityManager;
	private Player carrier;
	
	private GameClient client;
	
	
	public Flag(EntityManager entityManager, int team, GameClient client) {
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
		this.client = client;
	}
	
	
	public void tick() {
		if(!isCarried) {
			if(checkPickup(entityManager.getLocalPlayer())) {
				if(entityManager.getLocalPlayer().getTeam() != team) {
					//When picked up by the local player
					Packet packet = new Packet(Packet.FLAG_PICKUP, entityManager.getLocalPlayer().getUsername() + "," + entityManager.getFlags().indexOf(this));
					client.sendData(packet.getMessage());
				}
				else if(isPickedUp){
					Packet packet = new Packet(Packet.FLAG_RETURN, "" + entityManager.getFlags().indexOf(this));
					client.sendData(packet.getMessage());
				}
			}
		}
		else {
			if(carrier.getHero().isDead()) {
				isCarried = false;
			}
			else {
				x = (int)carrier.getHero().getX() ;		//- carrier.getHero().getRadius() * 2;
				y = (int)carrier.getHero().getY() ;		//- 30;
				
				if(carrier == entityManager.getLocalPlayer() && checkScored()) {
					Packet packet = new Packet(Packet.SCORED, entityManager.getFlags().indexOf(this) + "," + carrier.getTeam() + "," + carrier.getUsername());
					client.sendData(packet.getMessage());
				}
			}
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		if(true) {
			g.setColor(Color.BLACK);
			g.drawLine(x - cameraX, y - cameraY, x - cameraX, y - cameraY - FLAG_HEIGHT);
			g.setColor(Teams.getColor(team));
			Polygon flag = new Polygon(new int[] {x-cameraX, x-cameraX, x-cameraX + FLAG_WIDTH}, new int[] {y-cameraY - FLAG_HEIGHT, y-cameraY - FLAG_HEIGHT/2, y-cameraY - FLAG_HEIGHT*3/4}, 3) ;
			g.fillPolygon(flag);
			
			//if stolen draw tanslucid flag;
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
	
	
	public boolean checkPickup(Player player) {
		return Collisions.circleCollision(this.x, this.y, PICKUP_RADIUS, player.getHero().getX(), player.getHero().getY(), player.getHero().getRadius());
	}
	
	public boolean checkScored() {
		return Collisions.circleCollision(Teams.getFlagSpawnX(carrier.getTeam()), Teams.getFlagSpawnY(carrier.getTeam()), PICKUP_RADIUS, carrier.getHero().getX(), carrier.getHero().getY(), carrier.getHero().getRadius());
	}
	
	public void returnFlag() {
		isPickedUp = false;
		x = Teams.getFlagSpawnX(team);
		y = Teams.getFlagSpawnY(team);
	}
	
	public void score() {
		Teams.increaseScore(carrier.getTeam());
		isCarried = false;
		returnFlag();
	}
	
	
	public void setCarrier(Player player) {
		carrier = player;
		isCarried = true;
		isPickedUp = true;
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
