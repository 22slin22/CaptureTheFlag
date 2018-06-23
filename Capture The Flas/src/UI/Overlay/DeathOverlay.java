package UI.Overlay;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Hero;
import Main.Main;
import Utils.Fonts;

public class DeathOverlay {
	
	private Hero hero;
	
	
	public DeathOverlay(Hero hero) {
		this.hero = hero;
	}
	
	
	public void render(Graphics g) {
		if(hero.isDead()) {
			g.setColor(Color.RED);
			Fonts.drawTitle(g, "You are dead", Color.RED, Fonts.titleFont, Main.getHeight()/3, true);
			//Fonts.drawCenteredText(g, "You are dead", Main.getWidth()/2, Main.getHeight()/3, Fonts.titleFont);
			
			double elapsedTime = Hero.RESPAWN_TIME - (System.currentTimeMillis() - hero.getDeathTime());
			int timeCountdown = (int)(Math.ceil(elapsedTime/1000));
			g.setColor(new Color(117, 139, 200));
			Fonts.drawCenteredText(g, "" + timeCountdown, Main.getWidth()/2, Main.getHeight()/2, Fonts.respawnTimerFont);
		}
	}

}
