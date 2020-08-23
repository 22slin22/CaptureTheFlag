package Map;

import Entities.Hero;
import Main.Main;

public class Camera {
	
	private Hero hero;
	private int max_x, max_y;
	
	private int x, y;
	
	
	public Camera(int max_x, int max_y) {
		this.max_x = max_x;
		this.max_y = max_y;
	}
	
	
	public void tick() {
		x = (int)(hero.getX() - Main.getWidth()/2);
		y = (int)(hero.getY() - Main.getHeight()/2);
		
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
	
	public void setHero(Hero hero) {
		this.hero = hero;
	}

}
