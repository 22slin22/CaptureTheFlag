package Entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Input.KeyManager;
import Main.Main;

public class Runner extends Player{
	
	private final static float SPEED = 3;
	private final static int RADIUS = 40;

	public Runner(float x, float y, KeyManager keyManager) {
		super(x, y, keyManager);
	}

	@Override
	public void tick() {
		if(keyManager.isKeyPressed(KeyEvent.VK_A)) {
			move(-SPEED, 0);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_W)) {
			move(0, -SPEED);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D)) {	
			move(SPEED, 0);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S)) {
			move(0, SPEED);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(Main.getWidth()/2 - RADIUS/2, Main.getHeight()/2 - RADIUS/2, RADIUS, RADIUS);
	}

}
