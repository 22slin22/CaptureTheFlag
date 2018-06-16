package Server;

import Entities.EntityManager;
import Entities.Hero;
import Entities.Projectiles.StandardProjectile;
import Map.Flag;
import Map.Map;
import States.StateManager;
import States.States;
import Utils.Teams;
import net.Packet;

public class ServerGameState{
	
	private Server server;
	
	
	private int scoreBlue;
	private int scoreRed;
	
	
	private static final int SCORE_TO_WIN = 3;
	
	
	private EntityManager entityManager;
	private Map map;
	

	public ServerGameState(Server server) {
		this.server = server;
		map = new Map();
		entityManager = new EntityManager(map);
	}
	
	
	public void tick() {
		entityManager.tick();
		
		flagLogic();
		projectileLogic();
		hitDetection();
		
		// test only if sb scored
		if(Teams.getScore(Teams.BLUE) >= SCORE_TO_WIN) {
			StateManager.changeState(States.WIN_SCREEN);
			StateManager.getWinScreen().setWinner(Teams.BLUE);
		}
		else if(Teams.getScore(Teams.RED) >= SCORE_TO_WIN) {
			StateManager.changeState(States.WIN_SCREEN);
			StateManager.getWinScreen().setWinner(Teams.RED);
		}
	}
	
	public void flagLogic() {
		for(Flag flag : entityManager.getFlags()) {
			for(Hero hero : entityManager.getHeros()) {
				if(flag.checkPickup(hero)) {
					entityManager.flagPickup(hero.getUsername(), entityManager.getFlags().indexOf(flag));
					Packet packet = new Packet(Packet.FLAG_PICKUP, hero.getUsername() + "," + entityManager.getFlags().indexOf(flag));
					server.sendDataToAllClients(packet.getMessage());
				}
				if(flag.checkReturn(hero)) {
					entityManager.flagReturn(entityManager.getFlags().indexOf(flag));
					Packet packet = new Packet(Packet.FLAG_RETURN, "" + entityManager.getFlags().indexOf(flag));
					server.sendDataToAllClients(packet.getMessage());
				}
			}
			if(flag.checkScored()) {
				entityManager.flagReturn(entityManager.getFlags().indexOf(flag));
				Packet packet = new Packet(Packet.SCORED, "" + entityManager.getFlags().indexOf(flag));
				server.sendDataToAllClients(packet.getMessage());
			}
		}
	}
	
	private void projectileLogic(){
		synchronized(entityManager.getProjectiles()) {
			// backwards for loop in order to prevent an error
			for(int i = entityManager.getProjectiles().size() - 1; i>=0; i--) {
				if(entityManager.getProjectiles().get(i).checkRemove()) {
					entityManager.getProjectiles().remove(i);
					Packet packet = new Packet(Packet.REMOVE_PROJECTILE, "" + i);
					server.sendDataToAllClients(packet.getMessage());
				}
			}
		}
	}
	
	private void hitDetection() {
		synchronized(entityManager.getProjectiles()) {
			// no for loop because projectiles get deleted while the while loop is running
			int i = 0;
			while(i < entityManager.getProjectiles().size()) {
				for(Hero hero : entityManager.getHeros()) {
					if(hero.getTeam() != entityManager.getProjectiles().get(i).getOwner().getTeam() && Utils.Collisions.HeroProjectileCollision(hero, entityManager.getProjectiles().get(i))) {
						// Player got hit 
						Packet packet = new Packet(Packet.HIT, entityManager.getProjectiles().get(i).getOwner().getUsername() + "," + hero.getUsername() + "," + entityManager.getProjectiles().get(i).getOwner().getWeapon().getDamage() + "," + i);
						server.sendDataToAllClients(packet.getMessage());
						entityManager.hitHero(entityManager.getProjectiles().get(i).getOwner().getUsername(), hero.getUsername(), hero.getWeapon().getDamage(), entityManager.getProjectiles().indexOf(entityManager.getProjectiles().get(i)));
						i--;		// projectile was deleted --> index should stay the same
						break;
					}
				}
				i++;
			}
		}
	}
	
	
	public void start() {
		entityManager.reset();
		scoreBlue = 0;
		scoreRed = 0;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
