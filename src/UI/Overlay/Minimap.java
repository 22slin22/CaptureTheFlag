package UI.Overlay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import Entities.EntityManager;
import Entities.Hero;
import Main.Main;
import Map.Flag;
import Map.Map;
import Map.Obstacle;
import Utils.Teams;

public class Minimap {
	
	private Map map;
	private EntityManager entityManager;
	
	private int width = 300;
	private int height;
	
	private int xOffset = 50;
	private int yOffset = 50;
	
	private int xStart;
	private int yStart;
	
	private float minimapScalar;
	
	private int borderThickness = 3;
	
	
	public Minimap() {
		xStart = Main.getWidth() - xOffset - width;
		yStart = yOffset;
	}
	
	
	public void render(Graphics g) {
		drawBorder(g);
		drawObstacles(g);
		drawPlayers(g);
		drawFlags(g);
	}
	
	private  void drawBorder(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.drawRect(xStart - borderThickness, yStart - borderThickness, width + 2*borderThickness, height + 2*borderThickness);
		
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRect(xStart, yStart, width, height);
	}
	
	private void drawObstacles(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		for(Obstacle obs : map.getObstacles()) {
			g.fillRect(getMinimapX(obs.getX()), getMinimapY(obs.getY()), (int)(obs.getWidth()*minimapScalar), (int)(obs.getHeight()*minimapScalar));
		}
	}
	
	private void drawPlayers(Graphics g) {
		synchronized (entityManager.getHeros()) {
			for(Hero hero : entityManager.getHeros()) {
				g.setColor(new Color(Teams.getColor(hero.getTeam()).getRed(), Teams.getColor(hero.getTeam()).getGreen(), Teams.getColor(hero.getTeam()).getBlue(), 200)); 		// transparent team colors
				if(!hero.isDead()) {
					g.fillOval(xStart + (int)((hero.getX() - (float)hero.getRadius())*minimapScalar), 			// x position	=	x - radius*minimapScalar
							yStart + (int)((hero.getY() - hero.getRadius())*minimapScalar), 					// y position	= 	y - radius*minimapScalar
							(int)(hero.getRadius()*2*minimapScalar), (int)(hero.getRadius()*2*minimapScalar));	// width and height
				}
				else {
					drawX(g, hero);
				}
			}
		}
	}
	
	private void drawX(Graphics g, Hero hero) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));
		g2d.drawLine(xStart + (int)((hero.getX() - (float)hero.getRadius())*minimapScalar), 			// x position	=	x - radius*minimapScalar
				yStart + (int)((hero.getY() - hero.getRadius())*minimapScalar), 						// y position	= 	y - radius*minimapScalar
				xStart + (int)((hero.getX() + (float)hero.getRadius())*minimapScalar), 
				yStart + (int)((hero.getY() + hero.getRadius())*minimapScalar));
		g2d.drawLine(xStart + (int)((hero.getX() - (float)hero.getRadius())*minimapScalar), 			// x position	=	x - radius*minimapScalar
				yStart + (int)((hero.getY() + hero.getRadius())*minimapScalar), 						// y position	= 	y - radius*minimapScalar
				xStart + (int)((hero.getX() + (float)hero.getRadius())*minimapScalar), 
				yStart + (int)((hero.getY() - hero.getRadius())*minimapScalar));
	}
	
	private void drawFlags(Graphics g) {
		for(Flag flag : entityManager.getFlags()) {
			g.setColor(new Color(Teams.getColor(flag.getTeam()).getRed(), Teams.getColor(flag.getTeam()).getGreen(), Teams.getColor(flag.getTeam()).getBlue(), 200));
			drawFlag(g, getMinimapX(flag.getX()), getMinimapY(flag.getY()));
		}
	}
	
	private void drawFlag(Graphics g, int x, int y) {
		Polygon polygon = new Polygon(new int[] {x, x, x + (int) (Flag.FLAG_WIDTH*minimapScalar)}, new int[] {y - (int)(Flag.FLAG_HEIGHT*minimapScalar), y - (int)(Flag.FLAG_HEIGHT/2 * minimapScalar), y - (int)(Flag.FLAG_HEIGHT*3/4 * minimapScalar)}, 3) ;
		g.drawPolygon(polygon);
	}
	
	
	// returns the x position where an object has to be drawn on the minimap
	private int getMinimapX(int x) {
		return (xStart + (int)((x * minimapScalar)));
	}
	
	// returns the y position where an object has to be drawn on the minimap
	private int getMinimapY(int y) {
		return (yStart + (int)((y * minimapScalar)));
	}
	
	
	public void setMap(Map map) {
		this.map = map;
		height = width * map.getHeight() / map.getWidth();
		minimapScalar = (float)width / (float)map.getWidth();
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
