package Map;

import java.awt.Graphics;

public class Obstacle {
	
	private int x, y;
	private int width, height;
	
	
	public Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g) {
		g.fillRect(x, y, width, height);
	}

}
