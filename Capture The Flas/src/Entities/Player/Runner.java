package Entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Obstacle;

public class Runner extends Player{
	
	private final static float SPEED = 5;
	private final static int DIAMETER = 40;

	public Runner(float x, float y, int max_x, int max_y, KeyManager keyManager, MouseManager mouseManager, Camera camera) {
		super(x, y, DIAMETER/2, max_x, max_y, keyManager, mouseManager, camera);
		System.out.println(x);
		System.out.println(y);
	}

	
	public void tick(ArrayList<Obstacle> obstacles) {
		if(keyManager.isKeyPressed(KeyEvent.VK_A) || keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
			move(-SPEED, 0, obstacles);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_W) || keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			move(0, -SPEED, obstacles);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D) || keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {	
			move(SPEED, 0, obstacles);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S) || keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			move(0, SPEED, obstacles);
		}
		
		if(mouseManager.isLeftButton()) {
			System.out.println("left button");
		}
		
		System.out.println(getMouseAngle());
	}

	@Override
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.BLACK);
		g.fillOval((int)x - cameraX - DIAMETER/2, (int)y - cameraY - DIAMETER/2, DIAMETER, DIAMETER);
	}


	@Override
	public void tick() {
		
	}
	
	

}
