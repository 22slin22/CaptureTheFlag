package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.Heros.Runner;
import Entities.Projectiles.Projectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Flag;
import Map.Map;
import Map.Obstacle;
import Player.LocalPlayer;
import Player.Player;
import Utils.Teams;
import net.GameClient;

public class EntityManager {

	private LocalPlayer localPlayer;
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	private ArrayList<Flag> flags = new ArrayList<>();

	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera, GameClient client) {
		LocalPlayer player = new LocalPlayer(keyManager, mouseManager, camera, client, this, JOptionPane.showInputDialog("Please enter a username"));
		player.setHero(new Runner(Teams.BLUE, map, this));
		localPlayer = player;
		players.add(player);

		flags.add(new Flag(this, Teams.BLUE));
		flags.add(new Flag(this, Teams.RED));
		
		this.map = map;
	}

	public void tick() {
		for(Player player : getPlayers()) {
			player.tick();
		}
		
		for(Flag flag : flags) {
			flag.tick();
		}
	}

	public void render(Graphics g, int cameraX, int cameraY) {
		for(Flag flag : flags) {
			flag.render(g, cameraX, cameraY);
		}
		
		for(Player player : getPlayers()) {
			player.render(g, cameraX, cameraY);
		}
	}
	
	public void addPlayer(Player player) {
		getPlayers().add(player);
	}
	
	public void addPlayer(String username) {
		if(!(localPlayer.getUsername().equals(username))) {
			System.out.println("Adding " + username);
			Player player = new Player(username);
			player.setTeam(Teams.RED);
			player.setHero(new Runner(Teams.RED, map, this));
			getPlayers().add(player);
			
		}
	}
	
	public void updatePlayer(String username, float x, float y, double gunAngle) {
		for(Player player : players) {
			if(player.getUsername().equals(username)) {
				player.getHero().setX(x);
				player.getHero().setY(y);
				player.getHero().setGunAngle(gunAngle);
			}
		}
	}
	
	public void removePlayer(String username) {
		System.out.println("Deleting " + username);
		for(Player player : getPlayers()) {
			if(player.getUsername().equals(username)) {
				getPlayers().remove(player);
				break;
			}
		}
	}
	
	public void playerShoot(String username) {
		for(Player player : getPlayers()) {
			if(player.getUsername().equals(username)) {
				player.getHero().shoot();
			}
		}
	}
	
	public void hitPlayer(String usernameAttack, String username, int damage, int projectileId) {
		for(Player player : getPlayers()) {
			if(player.getUsername().equals(username)) {
				player.getHero().gotHit(damage);
			}
		}
		
		for(Player player : getPlayers()) {
			if(player.getUsername().equals(usernameAttack)) {
				player.getHero().getProjectiles().get(projectileId).setRemove(true);
			}
		}
	}
	
	public LocalPlayer getLocalPlayer() {
		return localPlayer;
	}
	
	public synchronized ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Flag> getFlags(){
		return flags;
	}

}
