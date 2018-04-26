package Entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Input.KeyManager;
import Main.Main;

public class Runner extends Player{
	
	private final static float SPEED = 5;
	private final static int DIAMETER = 50;

	public Runner(float x, float y, KeyManager keyManager) {
		super(x, y, keyManager);
		System.out.println(x);
		System.out.println(y);
	}

	@Override
	public void tick() {
		if(keyManager.isKeyPressed(KeyEvent.VK_A) || keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
			move(-SPEED, 0);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_W) || keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			move(0, -SPEED);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D) || keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {	
			move(SPEED, 0);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S) || keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			move(0, SPEED);
		}
	}

	@Override
	public void render(Graphics g, int xOffset, int yOffset) {
		g.setColor(Color.BLACK);
		System.out.print("X");
		System.out.println(x);
		System.out.println(xOffset);
		System.out.println(x + xOffset - DIAMETER/2);
		g.fillOval((int)x + xOffset - DIAMETER/2, (int)y + yOffset - DIAMETER/2 + yOffset, DIAMETER, DIAMETER);
	}

}
