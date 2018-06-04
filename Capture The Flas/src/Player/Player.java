package Player;

import java.awt.event.KeyEvent;

import Entities.EntityManager;
import Entities.Hero;
import Entities.Projectiles.Projectile;
import Entities.Projectiles.StandardProjectile;
import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Map.Camera;
import Map.Map;
import UI.Overlay.Killfeed;
import net.Packet;

public class Player{
	
	private KeyManager keyManager;
	
	private Camera camera;
	private EntityManager entityManager;
	
	private Game game;
	
	private Hero hero;
	

	public Player(KeyManager keyManager, Camera camera, Game game, EntityManager entityManager, Map map, Killfeed killfeed) {
		this.keyManager = keyManager;
		this.camera = camera;
		this.game = game;
		this.entityManager = entityManager;
		
		hero = new Hero(map, killfeed);
	}
	
	public void tick() {
		updateHero();
		hero.tick();
		
		testShoot();
		synchronized (hero.getProjectiles()) {
			checkHit();
		}
	}
	
	private void checkHit() {
		for(StandardProjectile projectile : hero.getProjectiles()) {
			synchronized (entityManager.getHeros()) {
				for(Hero hero : entityManager.getHeros()) {
					if(hero.getTeam() != this.hero.getTeam() && Utils.Collisions.HeroProjectileCollision(hero, projectile)) {
						hit(hero.getUsername(), hero.getProjectiles().indexOf(projectile));
					}
				}
			}
		}
	}
	
	private void hit(String playerUsername, int projectileId) {
		Packet packet = new Packet(Packet.HIT, hero.getUsername() + "," + playerUsername + "," + hero.getWeapon().getDamage() + "," + projectileId);
		game.getClient().sendData(packet.getMessage());
	}
	
	private void updateHero() {
		hero.setVx(0);
		if(keyManager.isKeyPressed(KeyEvent.VK_A) || keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
			hero.setVx(-hero.getTank().getSpeed());
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D) || keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {	
			hero.setVx(hero.getVx() + hero.getTank().getSpeed());
		}
		
		
		hero.setVy(0);
		if(keyManager.isKeyPressed(KeyEvent.VK_W) || keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			hero.setVy(-hero.getTank().getSpeed());
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S) || keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			hero.setVy(hero.getVy() + hero.getTank().getSpeed());
		}
		
		hero.setGunAngle(getMouseAngle());
		
		Packet packet = new Packet(Packet.UPDATE_PLAYER, hero.getUsername() + "," + hero.getX() + "," + hero.getY() + "," + hero.getGunAngle());
		game.getClient().sendData(packet.getMessage());
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
