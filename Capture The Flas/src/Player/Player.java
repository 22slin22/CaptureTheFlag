package Player;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.security.GeneralSecurityException;

import Entities.Heros.Hero;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;

public class Player {
	
	private Hero hero;
	
	protected KeyManager keyManager;
	protected MouseManager mouseManager;
	
	protected Camera camera;
	
	
	public Player(KeyManager keyManager, MouseManager mouseManager, Camera camera) {
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		this.camera = camera;
	}
	
	
	public void tick(float elapsedTime) {
		hero.setVx(0);
		if(keyManager.isKeyPressed(KeyEvent.VK_A) || keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
			hero.setVx(-hero.getSpeed());
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D) || keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {	
			hero.setVx(hero.getVx() + hero.getSpeed());
		}
		
		
		hero.setVy(0);
		if(keyManager.isKeyPressed(KeyEvent.VK_W) || keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			hero.setVy(-hero.getSpeed());
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S) || keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			hero.setVy(hero.getVy() + hero.getSpeed());
		}
		
		
		hero.setGunAngle(getMouseAngle());
		
		if(mouseManager.isLeftButton()) {
			if(System.currentTimeMillis() - hero.getLastShot() > hero.getCooldown()*1000) {
				hero.shoot();
				hero.setLastShot(System.currentTimeMillis());
			}
		}
		
		hero.tick(elapsedTime);
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		hero.render(g, cameraX, cameraY);
	}
	
	public double getMouseAngle() {
		// displayX and displayY    =    Position on the screen
		
		int displayX = (int)hero.getX() - camera.getX();
		int displayY = (int)hero.getY() - camera.getY();
		
		int mouseX = mouseManager.getX();
		int mouseY = mouseManager.getY();
		
		double r = Math.sqrt(Math.pow(displayY - mouseY, 2) + Math.pow(mouseX - displayX, 2));
		
		double angle = Math.asin((displayY - mouseY) / r);
		
		if(mouseX < displayX) {
			if(angle > 0) {
				angle = Math.PI - angle;
			}
			else {
				angle = -Math.PI - angle;
			}
		}
		
		return angle;
	}
	
	
	public Hero getHero() {
		return hero;
	}
	
	public void setCharacter(Hero character) {
		this.hero = character;
	}

}
