package Player;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.security.GeneralSecurityException;

import Entities.Heros.Hero;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;

public class Player {
	
	private String username;
	
	protected Hero hero;
	
	
	public Player(String username) {
		this.username = username;
	}
	
	
	public void tick() {
		hero.tick();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		hero.render(g, cameraX, cameraY);
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
