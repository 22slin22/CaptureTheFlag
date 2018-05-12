package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Display.UI.Killfeed;
import Entities.Heros.Runner;
import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Map.Camera;
import Map.Flag;
import Map.Map;
import Player.LocalPlayer;
import Player.Player;
import Utils.Teams;

public class EntityManager {

	private LocalPlayer localPlayer;
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	private ArrayList<Flag> flags = new ArrayList<>();
	private Killfeed killfeed;

	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera, Game game, JFrame frame, Killfeed killfeed) {
		//Object[] options = {"BLUE", "RED"};
		//int team = JOptionPane.showOptionDialog(frame, "Choose a team!", "Team", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		LocalPlayer player = new LocalPlayer(keyManager, mouseManager, camera, game, this);		 //	JOptionPane.showInputDialog("Please enter a user name")
		player.setHero(new Runner(player.getTeam(), map, this, player, killfeed));
		localPlayer = player;
		players.add(player);

		flags.add(new Flag(this, Teams.BLUE, game));
		flags.add(new Flag(this, Teams.RED, game));
		
		this.map = map;
		this.killfeed = killfeed;
	}

	public void tick() {
		for(Flag flag : flags) {
			flag.tick();
		}
		
		synchronized(players) {
			for(Player player : players) {
				player.tick();
			}
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
			player.setHero(new Runner(Teams.RED, map, this, player, killfeed));
			synchronized (players) {
				players.add(player);
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
				}
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
