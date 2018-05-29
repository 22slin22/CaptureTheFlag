package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import Entities.Tanks.Heavy;
import Entities.Tanks.Light;
import Entities.Tanks.Medium;
import Entities.Weapons.Gun;
import Entities.Weapons.Laser;
import Entities.Weapons.Shotgun;
import Entities.Weapons.Weapon;
import Input.KeyManager;
import Main.Game;
import Map.Flag;
import Map.Map;
import UI.Overlay.Killfeed;
import Utils.Teams;

public class EntityManager {

	//private LocalPlayer localPlayer;
	private ArrayList<Hero> heros = new ArrayList<>();
	private ArrayList<Flag> flags = new ArrayList<>();
	
	private Map map;
	private Killfeed killfeed;

	public EntityManager(KeyManager keyManager, Map map, Game game, JFrame frame, Killfeed killfeed) {
		this.map = map;
		this.killfeed = killfeed;
		
		//localPlayer = new LocalPlayer(keyManager, mouseManager, camera, game, this);
		//localPlayer.setHero(new Medium(localPlayer.getTeam(), map, this, localPlayer, killfeed));
		//localPlayer.getHero().setWeapon(new Laser(localPlayer.getHero()));
		//players.add(localPlayer);

		flags.add(new Flag(this, Teams.BLUE, game));
		flags.add(new Flag(this, Teams.RED, game));
	}

	public void tick() {
		synchronized(heros) {
			for(Hero hero : heros) {
				hero.tick();
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
		synchronized(heros) {
			for(Hero hero : heros) {
				hero.render(g, cameraX, cameraY);
			}
		}
	}
	
	public void addHero(Hero hero) {
		synchronized (heros) {
			heros.add(hero);
		}
	}	
	
	public void addHero(String username, int team) {
		Hero player = new Hero(team, map, killfeed);
		player.setUsername(username);
		synchronized (heros) {
			heros.add(player);	
		}
	}
	
	public void removeHero(String username) {
		System.out.println("Deleting " + username);
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					heros.remove(hero);
					break;
				}
			}
		}
	}
	
	public void updateHero(String username, float x, float y, double gunAngle) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					hero.setX(x);
					hero.setY(y);
					hero.setGunAngle(gunAngle);
				}
			}
		}
	}
	
	public void changeTeam(String username, int team) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					hero.setTeam(team);
					//player.getHero().move(Teams.getRandomSpawn(player.getTeam()));
				}
			}
		}
	}
	
	public void changeHero(String username, int tank, int weapon) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					switch (tank) {
					case Hero.LIGHT:
						hero.setTank(new Light(hero));
						break;
					case Hero.MEDIUM:
						hero.setTank(new Medium(hero));
						break;
					case Hero.HEAVY:
						hero.setTank(new Heavy(hero));
						break;
					}
					
					switch (weapon) {
					case Weapon.GUN:
						hero.setWeapon(new Gun(hero));
						break;
					case Weapon.SHOTGUN:
						hero.setWeapon(new Shotgun(hero));
						break;
					case Weapon.LASER:
						hero.setWeapon(new Laser(hero));
						break;
					}
				}
			}
		}
	}
	
	public void heroShoot(String username) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					hero.shoot();
				}
			}
		}
	}
	
	public void hitHero(String usernameAttack, String username, int damage, int projectileId) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					for(Hero attacker : heros) {
						if(attacker.getUsername().equals(usernameAttack)) {
							hero.gotHit(damage, attacker);
						}
					}
				}
			}
		
		
			for(Hero hero : heros) {
				if(hero.getUsername().equals(usernameAttack)) {
					hero.getProjectiles().get(projectileId).setRemove(true);
				}
			}
		}
	}
	
	public void flagPickup(String username, int flagIndex) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					flags.get(flagIndex).setCarrier(hero);
				}
			}
		}
	}
	
	public void reset() {
		for(Hero hero : heros) {
			hero.getProjectiles().clear();
			hero.move(Teams.getRandomSpawn(hero.getTeam()));
		}
		for(Flag flag : flags) {
			flag.returnFlag();
		}
	}
	
	public void flagReturn(int flagIndex) {
		flags.get(flagIndex).returnFlag();
	}
	
	public void score(int flagIndex) {
		flags.get(flagIndex).score();
	}
	
	//public LocalPlayer getLocalPlayer() {
	//	return localPlayer;
	//}
	
	public ArrayList<Hero> getHeros(){
		return heros;
	}
	
	public ArrayList<Flag> getFlags(){
		return flags;
	}

}
