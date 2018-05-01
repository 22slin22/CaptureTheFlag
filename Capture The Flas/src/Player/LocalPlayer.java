package Player;

import java.awt.event.KeyEvent;

import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;

public class LocalPlayer extends Player{
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private Camera camera;
	

	public LocalPlayer(KeyManager keyManager, MouseManager mouseManager, Camera camera, String username) {
		super(username);
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		this.camera = camera;
		
	}
	
	@Override
	public void tick() {
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
		
		hero.tick();
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
	

}
