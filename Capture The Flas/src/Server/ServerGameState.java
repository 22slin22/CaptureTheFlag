package Server;

import Entities.EntityManager;
import Entities.Hero;
import Map.Flag;
import Map.Map;
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
		if(scoreBlue >= SCORE_TO_WIN) {
			Packet packet = new Packet(Packet.WIN, "" + Teams.BLUE);
			server.sendDataToAllClients(packet.getMessage());
			server.getServerMain().setPlaying(false);
		}
		else if(scoreRed >= SCORE_TO_WIN) {
			Packet packet = new Packet(Packet.WIN, "" + Teams.RED);
			server.sendDataToAllClients(packet.getMessage());
			server.getServerMain().setPlaying(false);
		}
	}
	
	public void flagLogic() {
		for(Flag flag : entityManager.getFlags()) {
			for(Hero hero : entityManager.getHeros()) {
				if(!hero.isDead() && flag.checkPickup(hero)) {
					entityManager.flagPickup(hero.getUsername(), entityManager.getFlags().indexOf(flag));
					Packet packet = new Packet(Packet.FLAG_PICKUP, hero.getUsername() + "," + entityManager.getFlags().indexOf(flag));
					server.sendDataToAllClients(packet.getMessage());
				}
				if(!hero.isDead() && flag.checkReturn(hero)) {
					entityManager.flagReturn(entityManager.getFlags().indexOf(flag));
					Packet packet = new Packet(Packet.FLAG_RETURN, "" + entityManager.getFlags().indexOf(flag));
					server.sendDataToAllClients(packet.getMessage());
				}
			}
			if(flag.checkScored()) {
				if(flag.getCarrier().getTeam() == Teams.BLUE)
					scoreBlue += 1;
				else if(flag.getCarrier().getTeam() == Teams.RED)
					scoreRed += 1;
				
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
					if(!hero.isDead() && hero.getTeam() != entityManager.getProjectiles().get(i).getOwner().getTeam() && Utils.Collisions.HeroProjectileCollision(hero, entityManager.getProjectiles().get(i))) {
						// Player got hit 
						Packet packet = new Packet(Packet.HIT, hero.getUsername() + "," + entityManager.getProjectiles().get(i).getOwner().getWeapon().getDamage() + "," + i);
						server.sendDataToAllClients(packet.getMessage());
						
						//test if player is dead now
						if(hero.getHealth() - entityManager.getProjectiles().get(i).getOwner().getWeapon().getDamage() <= 0) {
							packet = new Packet(Packet.KILL, hero.getUsername() + "," + entityManager.getProjectiles().get(i).getOwner().getUsername());
							server.sendDataToAllClients(packet.getMessage());
							entityManager.killHero(hero.getUsername(), entityManager.getProjectiles().get(i).getOwner().getUsername());
						}
						
						entityManager.hitHero(hero.getUsername(), entityManager.getProjectiles().get(i).getOwner().getWeapon().getDamage(), entityManager.getProjectiles().indexOf(entityManager.getProjectiles().get(i)));			// has to been done afterwards because projectile is deleted here
						System.out.println(hero.getHealth());
						i--;				// projectile was deleted --> index should stay the same
						break;
					}
				}
				i++;
			}
		}
	}
	
	
	public void reset() {
		entityManager.reset();
		scoreBlue = 0;
		scoreRed = 0;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
