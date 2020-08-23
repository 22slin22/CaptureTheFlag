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
	private Killfeed killfeed;
	

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
		Hero hero = getHero(username);
		heros.remove(hero);
	}
	
	public void updateHero(String username, float x, float y, double gunAngle) {
		Hero hero = getHero(username);
		if(hero != null) {
			hero.setX(x);
			hero.setY(y);
			hero.setGunAngle(gunAngle);
		}
	}
	
	public void updateGunAngle(String username, double gunAngle) {
		Hero hero = getHero(username);
		hero.setGunAngle(gunAngle);
	}
	
	public void updateVelocity(String username, float vx, float vy) {
		Hero hero = getHero(username);
		hero.setVx(vx);
		hero.setVy(vy);
	}
	
	public void changeTeam(String username, int team) {
		Hero hero = getHero(username);
		hero.setTeam(team);
	}
	
	public void changeHero(String username, int tank, int weapon) {
		Hero hero = getHero(username);
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
	
	public void heroShoot(String username) {
		Hero hero = getHero(username);
		hero.shoot();
	}
	
	public void removeProjectile(int projectileId) {
		synchronized(projectiles) {
			projectiles.remove(projectileId);
		}
	}
	
	public void hitHero(String usernameTarget, String usernameAttacker, int damage, int projectileId) {
		Hero target = getHero(usernameTarget);
		Hero attacker = getHero(usernameAttacker);
		
		target.gotHit(damage);
		removeProjectile(projectileId);
	
		attacker.getStats().increaseDamage(damage);
	}
	
	public void killHero(String usernameTarget, String usernameKiller) {
		Hero target = getHero(usernameTarget);
		Hero killer = getHero(usernameKiller);
		
		target.kill();
		
		killer.getStats().increaseKills();
		target.getStats().increaseDeaths();
		if(killfeed != null)
			killfeed.addKill(killer, target);
	}
	
	public void flagPickup(String username, int flagIndex) {
		Hero hero = getHero(username);
		flags.get(flagIndex).setCarrier(hero);
		hero.setFlag(flags.get(flagIndex));
	}
	
	public void scored(String username, int flagIndex) {
		flagReturn(flagIndex);
		getHero(username).getStats().increaseScored();
	}
	
	public void reset() {
		synchronized(heros) {
			for(Hero hero : heros) {
				hero.move(Teams.getRandomSpawn(hero.getTeam()));
				hero.getStats().reset();
			}
			for(Flag flag : flags) {
				flag.returnFlag();
			}
			projectiles.clear();
		}
	}
	
	public void flagReturn(int flagIndex) {
		flags.get(flagIndex).returnFlag();
	}
	
	public ArrayList<Hero> getHeros(){
		return heros;
	}
	
	public Hero getHero(String username) {
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.getUsername().equals(username)) {
					return hero;
				}
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
