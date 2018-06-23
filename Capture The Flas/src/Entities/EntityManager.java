package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Projectiles.Projectile;
import Entities.Projectiles.StandardProjectile;
import Entities.Tanks.Heavy;
import Entities.Tanks.Light;
import Entities.Tanks.Medium;
import Entities.Weapons.Gun;
import Entities.Weapons.Laser;
import Entities.Weapons.Shotgun;
import Entities.Weapons.Weapon;
import Map.Flag;
import Map.Map;
import UI.Overlay.Killfeed;
import Utils.Teams;

public class EntityManager {

	private ArrayList<Hero> heros = new ArrayList<>();
	private ArrayList<Flag> flags = new ArrayList<>();
	private ArrayList<StandardProjectile> projectiles = new ArrayList<>();
	
	private Map map;
	Killfeed killfeed;

	public EntityManager(Map map) {
		this.map = map;
		
		flags.add(new Flag(Teams.BLUE));
		flags.add(new Flag(Teams.RED));
	}

	public void tick() {
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.isPlaying()) {
					hero.tick();
				}
			}
		}
		
		synchronized (projectiles) {
			for(Projectile projectile : projectiles) {
				projectile.tick();
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
		
		synchronized (projectiles) {
			for(Projectile projectile : projectiles) {
				projectile.render(g, cameraX, cameraY);
			}
		}
		
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.isPlaying()) {
					hero.render(g, cameraX, cameraY);
				}
			}
		}
	}
	
	public void addHero(Hero hero) {
		synchronized (heros) {
			heros.add(hero);
		}
	}	
	
	public void addHero(String username, int team) {
		Hero player = new Hero(team, map);
		player.setUsername(username);
		synchronized (heros) {
			heros.add(player);	
		}
	}
	
	public void removeHero(String username) {
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
						hero.setWeapon(new Gun(hero, this));
						break;
					case Weapon.SHOTGUN:
						hero.setWeapon(new Shotgun(hero, this));
						break;
					case Weapon.LASER:
						hero.setWeapon(new Laser(hero, this));
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
	
	public void removeProjectile(int projectileId) {
		synchronized(projectiles) {
			projectiles.remove(projectileId);
		}
	}
	
	public void hitHero(String username, int damage, int projectileId) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					hero.gotHit(damage);
				}
			}
		}
		removeProjectile(projectileId);
	}
	
	public void killHero(String username, String usernameKiller) {
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					for(Hero killer : heros) {
						if(killer.getUsername().equals(usernameKiller)) {
							hero.kill();
							if(killfeed != null)
								killfeed.addKill(killer, hero);
						}
					}
				}
			}
		}
	}
	
	public void flagPickup(String username, int flagIndex) {
		synchronized (heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					flags.get(flagIndex).setCarrier(hero);
					hero.setFlag(flags.get(flagIndex));
				}
			}
		}
	}
	
	public void reset() {
		for(Hero hero : heros) {
			projectiles.clear();
			hero.move(Teams.getRandomSpawn(hero.getTeam()));
		}
		for(Flag flag : flags) {
			flag.returnFlag();
		}
	}
	
	public void flagReturn(int flagIndex) {
		flags.get(flagIndex).returnFlag();
	}
	
	public ArrayList<Hero> getHeros(){
		return heros;
	}
	
	public Hero getHero(String username) {
		for(Hero hero : heros) {
			if(hero.getUsername().equals(username)) {
				return hero;
			}
		}
		return null;
	}
	
	public ArrayList<Flag> getFlags(){
		return flags;
	}
	
	public ArrayList<StandardProjectile> getProjectiles(){
		return projectiles;
	}
	
	public void setKillfeed(Killfeed killfeed) {
		this.killfeed = killfeed;
	}

}
