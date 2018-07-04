package Player;

import java.awt.event.KeyEvent;

import Entities.Hero;
import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Map.Camera;
import Map.Map;
import net.Packet;

public class Player{
	
	private KeyManager keyManager;
	private Camera camera;
	private Game game;
	private Hero hero;
	
	private int tickCounter = 0;
	

	public Player(KeyManager keyManager, Camera camera, Game game, Map map) {
		this.keyManager = keyManager;
		this.camera = camera;
		this.game = game;
		
		hero = new Hero(map);
	}
	
	public void tick() {
		if(!hero.isDead()) {
			updateHero();
			testShoot();
		}
	}

	
	private void updateHero() {
		float vx = 0;
		if(keyManager.isKeyPressed(KeyEvent.VK_A) || keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
			vx = -hero.getTank().getSpeed();
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D) || keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {	
			vx += hero.getTank().getSpeed();
		}
		
		float vy = 0;
		if(keyManager.isKeyPressed(KeyEvent.VK_W) || keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			vy = -hero.getTank().getSpeed();
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S) || keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			vy += hero.getTank().getSpeed();
		}
		
		if(vx != hero.getVx() || vy != hero.getVy()) {
			Packet packet = new Packet(Packet.PLAYER_MOVING, hero.getUsername() + "," + vx + "," + vy);
			game.getClient().sendData(packet.getMessage());
		}
		
		hero.setVx(vx);
		hero.setVy(vy);
		
		double angle = getMouseAngle();
		if(hero.getGunAngle() != angle) {
			Packet packet = new Packet(Packet.GUN_ANGLE, hero.getUsername() + "," + Math.round(hero.getGunAngle() * 100)/100f);
			game.getClient().sendData(packet.getMessage());
		}
		hero.setGunAngle(getMouseAngle());
		
		if(tickCounter % 15 == 0) {		// every quarter a second
			Packet packet = new Packet(Packet.UPDATE_PLAYER, hero.getUsername() + "," + hero.getX() + "," + hero.getY() + "," + Math.round(hero.getGunAngle() * 100)/100f);		// *100)/100 to round to 2 decimal places
			game.getClient().sendData(packet.getMessage());
		}
		tickCounter++;
	}
	
	private void testShoot() {
		if(MouseManager.isLeftButton()) {
			if(System.currentTimeMillis() - hero.getWeapon().getLastShot() > hero.getWeapon().getCooldown()*1000) {
				Packet packet = new Packet(Packet.SHOOT, hero.getUsername());
				game.getClient().sendData(packet.getMessage());
			}
		}
	}
	
	public double getMouseAngle() {
		// displayX and displayY    =    Position on the screen
		
		int displayX = (int)hero.getX() - camera.getX();
		int displayY = (int)hero.getY() - camera.getY();
		
		int mouseX = MouseManager.getX();
		int mouseY = MouseManager.getY();
		
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
}
