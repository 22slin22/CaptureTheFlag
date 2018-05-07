package Player;

import java.awt.event.KeyEvent;

import Entities.EntityManager;
import Entities.Projectiles.Projectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Flag;
import Map.Map;
import net.GameClient;
import net.Packet;

public class LocalPlayer extends Player{
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private Camera camera;
	private EntityManager entityManager;
	
	private GameClient client;
	

	public LocalPlayer(KeyManager keyManager, MouseManager mouseManager, Camera camera, GameClient client, EntityManager entityManager, String username, int team) {
		super(username, team);
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
		this.camera = camera;
		this.client = client;
		this.entityManager = entityManager;
	}
	
	@Override
	public void tick() {
		updateHero();
		hero.tick();
		
		testShoot();
		synchronized (hero.getProjectiles()) {
			checkHit();
		}
	}
	
	private void checkHit() {
		for(Projectile projectile : hero.getProjectiles()) {
			synchronized (entityManager.getPlayers()) {
				for(Player player : entityManager.getPlayers()) {
					if(player.getTeam() != team && Utils.Collisions.PlayerProjectileCollision(player, projectile)) {
						hit(player.getUsername(), hero.getProjectiles().indexOf(projectile));
					}
				}
			}
		}
	}
	
	private void hit(String playerUsername, int projectileId) {
		Packet packet = new Packet(Packet.HIT, username + "," + playerUsername + "," + hero.DAMAGE + "," + projectileId);
		client.sendData(packet.getMessage());
		entityManager.hitPlayer(username, playerUsername, hero.DAMAGE, projectileId);
	}
	
	private void updateHero() {
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
		
		Packet packet = new Packet(Packet.UPDATE_PLAYER, username + "," + hero.getX() + "," + hero.getY() + "," + hero.getGunAngle());
		client.sendData(packet.getMessage());
	}
	
	private void testShoot() {
		if(mouseManager.isLeftButton()) {
			if(System.currentTimeMillis() - hero.getLastShot() > hero.getCooldown()*1000) {
				hero.shoot();
				hero.setLastShot(System.currentTimeMillis());
				
				Packet packet = new Packet(Packet.SHOOT, username);
				client.sendData(packet.getMessage());
			}
		}
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
