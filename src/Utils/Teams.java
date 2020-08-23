package Utils;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import Entities.Hero;

public class Teams {
	
	public static final int BLUE = 0;
	public static final int RED = 1;
	
	public static Point[] blueSpawnPoints = new Point[] {
			new Point(300, 300),
			new Point(300, 1700)
	};
	public static Point[] redSpawnPoints = new Point[] {
			new Point(2700, 300),
			new Point(2700, 1700)
	};
	
	public static final int FLAG_BLUE_X = 200;
	public static final int FLAG_BLUE_Y = 1000;
	public static final int FLAG_RED_X = 2800;
	public static final int FLAG_RED_Y = 1000;
	
	private static int[] scores = new int[2];
	
	
	public static Color getColor(int team) {
		if(team == 0) {
			return Color.BLUE;
		}
		if(team == 1) {
			return Color.RED;
		}
		return Color.BLACK;
	}
	
	public static Point getRandomSpawn(int team) {
		int random = (int)(Math.random() * (float)blueSpawnPoints.length);
		if(team == BLUE) {
			return blueSpawnPoints[random];
		}
		if(team == RED) {
			return redSpawnPoints[random];
		}
		return null;
	}
	
	public static int getFlagSpawnX(int team) {
		if(team == BLUE) {
			return FLAG_BLUE_X;
		}
		if(team == RED) {
			return FLAG_RED_X;
		}
		return 0;
	}
	
	public static int getFlagSpawnY(int team) {
		if(team == BLUE) {
			return FLAG_BLUE_Y;
		}
		if(team == RED) {
			return FLAG_RED_Y;
		}
		return 0;
	}
	
	public static void increaseScore(int team) {
		scores[team] += 1;
	}
	
	public static void setScore(int team, int score) {
		scores[team] = score;
	}
	
	public static int getScore(int team) {
		return scores[team];
	}
	
	public static String getTeamName(int team) {
		if(team == BLUE) {
			return "BLUE";
		}
		if(team == RED) {
			return "RED";
		}
		return "";
	}
	
	
	public static Hero getPlayerWithMostScored(ArrayList<Hero> heros, int team) {
		Hero playerMostScored = null;
		int mostScored = -1;
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.getTeam() == team && hero.getStats().getScored() > mostScored) {
					playerMostScored = hero;
					mostScored = hero.getStats().getScored();
				}
			}
		}
		return playerMostScored;
	}
	
	public static Hero getPlayerWithMostKills(ArrayList<Hero> heros, int team) {
		Hero playerMostKills = null;
		int mostKills = -1;
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.getTeam() == team && hero.getStats().getKills() > mostKills) {
					playerMostKills = hero;
					mostKills = hero.getStats().getKills();
				}
			}
		}
		return playerMostKills;
	}
	
	public static Hero getPlayerWithLeastDeaths(ArrayList<Hero> heros, int team) {
		Hero playerLeastDeaths = null;
		int leastDeaths = 999999;
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.getTeam() == team && hero.getStats().getDeaths() < leastDeaths) {
					playerLeastDeaths = hero;
					leastDeaths = hero.getStats().getDeaths();
				}
			}
		}
		return playerLeastDeaths;
	}
	
	public static Hero getPlayerWithMostDamage(ArrayList<Hero> heros, int team) {
		Hero playerMostDamage = null;
		int mostDamage = -1;
		synchronized(heros) {
			for(Hero hero : heros) {
				if(hero.getTeam() == team && hero.getStats().getDamage() > mostDamage) {
					playerMostDamage = hero;
					mostDamage = hero.getStats().getDamage();
				}
			}
		}
		return playerMostDamage;
	}

}
