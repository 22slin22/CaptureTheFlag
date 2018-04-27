package Map;

import Entities.Player.Player;
import Main.Main;

public class Camera {
	
	private Player player;
	private int max_x, max_y;
	
	private int x, y;
	
	
	public Camera(Player player, int max_x, int max_y) {
		this.player = player;
		this.max_x = max_x;
		this.max_y = max_y;
	}
	
	
	public void tick() {
		x = (int)(player.getX() - Main.getWidth()/2);
		y = (int)(player.getY() - Main.getHeight()/2);
		
		System.out.println(x);
		System.out.println(y);
		System.out.println("");
		
		if(x < 0 - 30) {
			x = 0 - 30;
		}
		else if(x > max_x - Main.getWidth() + 30) {
			x = max_x - Main.getWidth() + 30;
		}
		if(y < 0 - 30) {
			y = 0 - 30;
		}
		else if(y > max_y - Main.getHeight() + 30) {
			y = max_y - Main.getHeight() + 30;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
