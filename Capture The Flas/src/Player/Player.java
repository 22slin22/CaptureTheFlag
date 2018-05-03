package Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.security.GeneralSecurityException;

import Entities.Heros.Hero;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Utils.Fonts;
import net.Packet;

public class Player {
	
	protected String username;
	
	protected Hero hero;
	
	
	public Player(String username) {
		this.username = username;
	}
	
	
	public void tick() {
		hero.tick();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		hero.render(g, cameraX, cameraY);
		renderNameTag(g, cameraX, cameraY);
	}
	
	private void renderNameTag(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.GRAY);
		Fonts.drawCenteredText(g, username, (int)hero.getX() - cameraX, (int)hero.getY() - cameraY - hero.getRadius() - 30, Fonts.playerNameFont);
	}


	public Hero getHero() {
		return hero;
	}
	
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	
	public String getUsername() {
		return username;
	}

}
