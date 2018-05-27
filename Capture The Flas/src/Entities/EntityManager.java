package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import Entities.Heros.Heavy;
import Entities.Heros.Hero;
import Entities.Heros.Light;
import Entities.Heros.Medium;
import Entities.Weapons.Gun;
import Entities.Weapons.Laser;
import Entities.Weapons.Shotgun;
import Entities.Weapons.Weapon;
import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Map.Camera;
import Map.Flag;
import Map.Map;
import Player.LocalPlayer;
import Player.Player;
import UI.Overlay.Killfeed;
import Utils.Teams;

public class EntityManager {

	private LocalPlayer localPlayer;
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Flag> flags = new ArrayList<>();
	
	private Map map;
	private Camera camera;
	private Killfeed killfeed;

	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera, Game game, JFrame frame, Killfeed killfeed) {
		this.camera = camera;
		this.map = map;
		this.killfeed = killfeed;
		
		localPlayer = new LocalPlayer(keyManager, mouseManager, camera, game, this);
		//localPlayer.setHero(new Medium(localPlayer.getTeam(), map, this, localPlayer, killfeed));
		//localPlayer.getHero().setWeapon(new Laser(localPlayer.getHero()));
		players.add(localPlayer);

		flags.add(new Flag(this, Teams.BLUE, game));
		flags.add(new Flag(this, Teams.RED, game));
	}

	public void tick() {
		synchronized(players) {
			for(Player player : players) {
				player.tick();
			}
		}
		
		for(Flag flag : flags) {
			flag.tick();
		}
	}

	public void render(Graphics g, int cameraX, int cameraY) {
		for(Flag flag : flags) {
			flag.render(g, cameraX, cameraY);
		}
		synchronized(players) {
			for(Player player : players) {
				player.render(g, cameraX, cameraY);
			}
		}
	}
	
	public void addPlayer(Player player) {
		synchronized (players) {
			players.add(player);
		}
	}	
	
	public void addPlayer(String username, int team) {
		if(!(localPlayer.getUsername().equals(username))) {
			System.out.println("Adding " + username);
			Player player = new Player(username, team);
			synchronized (players) {
				players.add(player);
			}
			
		}
	}
	
	public void removePlayer(String username) {
		System.out.println("Deleting " + username);
		synchronized (players) {
			for(Player player : players) {
				if(player.getUsername().equals(username)) {
					getPlayers().remove(player);
					break;
				}
			}
		}
	}
	
	public void updatePlayer(String username, float x, float y, double gunAngle) {
		synchronized (players) {
			for(Player player : players) {
				if(player.getUsername().equals(username)) {
					player.getHero().setX(x);
					player.getHero().setY(y);
					player.getHero().setGunAngle(gunAngle);
				}
			}
		}
	}
	
	public void changeTeam(String username, int team) {
		synchronized (players) {
			for(Player player : players) {
				if(player.getUsername().equals(username)) {
					player.setTeam(team);
					//player.getHero().move(Teams.getRandomSpawn(player.getTeam()));
				}
			}
		}
	}
	
	public void changeHero(String username, int tank, int weapon) {
		synchronized (players) {
			for(Player player : players) {
				if(player.getUsername().equals(username)) {
					switch (tank) {
					case Hero.LIGHT:
						player.setHero(new Light(player.getTeam(), map, this, player, killfeed));
						break;
					case Hero.MEDIUM:
						player.setHero(new Medium(player.getTeam(), map, this, player, killfeed));
						break;
					case Hero.HEAVY:
						player.setHero(new Heavy(player.getTeam(), map, this, player, killfeed));
						break;
					}
					
					switch (weapon) {
					case Weapon.GUN:
						player.getHero().setWeapon(new Gun(player.getHero()));
						break;
					case Weapon.SHOTGUN:
						player.getHero().setWeapon(new Shotgun(player.getHero()));
						break;
					case Weapon.LASER:
						player.getHero().setWeapon(new Laser(player.getHero()));
						break;
					}
				}
			}
		}
	}
	
	public void playerShoot(String username) {
		synchronized (players) {
			for(Player player : players) {
				if(player.getUsername().equals(username)) {
					player.getHero().shoot();
				}
			}
		}
	}
	
	public void hitPlayer(String usernameAttack, String username, int damage, int projectileId) {
		synchronized (players) {
			for(Player player : players) {
				if(player.getUsername().equals(username)) {
					for(Player attacker : getPlayers()) {
						if(attacker.getUsername().equals(usernameAttack)) {
							player.getHero().gotHit(damage, attacker);
						}
					}
				}
			}
		
		
			for(Player player : players) {
				if(player.getUsername().equals(usernameAttack)) {
					player.getHero().getProjectiles().get(projectileId).setRemove(true);
				}
			}
		}
	}
	
	public void flagPickup(String username, int flagIndex) {
		synchronized (players) {
			for(Player player : getPlayers()) {
				if(player.getUsername().equals(username)) {
					flags.get(flagIndex).setCarrier(player);
				}
			}
		}
	}
	
	public void reset() {
		for(Player player : players) {
			player.getHero().getProjectiles().clear();
			player.getHero().move(Teams.getRandomSpawn(player.getTeam()));
		}
		for(Flag flag : flags) {
			flag.returnFlag();
		}
		camera.setHero(localPlayer.getHero());
	}
	
	public void flagReturn(int flagIndex) {
		flags.get(flagIndex).returnFlag();
	}
	
	public void score(int flagIndex) {
		flags.get(flagIndex).score();
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
