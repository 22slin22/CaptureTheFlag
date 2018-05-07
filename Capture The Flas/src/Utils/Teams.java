package Utils;

import java.awt.Color;

public class Teams {
	
	public static final int BLUE = 0;
	public static final int RED = 1;
	
	public static final int SPAWN_BLUE_X = 300;
	public static final int SPAWN_BLUE_Y = 1000;
	public static final int SPAWN_RED_X = 2700;
	public static final int SPAWN_RED_Y = 1000;
	
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
	
	public static int getSpawnX(int team) {
		if(team == BLUE) {
			return SPAWN_BLUE_X;
		}
		if(team == RED) {
			return SPAWN_RED_X;
		}
		return 0;
	}
	
	public static int getSpawnY(int team) {
		if(team == BLUE) {
			return SPAWN_BLUE_Y;
		}
		if(team == RED) {
			return SPAWN_RED_Y;
		}
		return 0;
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
	
	public static int getScore(int team) {
		return scores[team];
	}

}
