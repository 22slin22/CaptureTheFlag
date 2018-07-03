package UI.Overlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Entities.Hero;
import Input.KeyManager;
import Main.Main;
import Utils.Fonts;

public class Tab {
	
	private static final int STATS_WIDTH = Main.getWidth() / 2;
	private static final int STATS_HEIGHT = Main.getHeight() / 5;
	
	
	private KeyManager keyManager;
	private Hero player;
	
	
	public Tab(KeyManager keyManager, Hero player) {
		this.keyManager = keyManager;
		this.player = player;
	}
	
	
	public void render(Graphics g) {
		if(keyManager.isKeyPressed(KeyEvent.VK_TAB)) {
			renderStats(g);
		}
	}
	
	private void renderStats(Graphics g) {
		g.setColor(new Color(0.8f, 0.8f, 0.8f, 0.8f));
		g.fillRect(0, Main.getHeight() - STATS_HEIGHT, STATS_WIDTH, STATS_HEIGHT);
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Scored", STATS_WIDTH/8, Main.getHeight() - STATS_HEIGHT * 2/3, Fonts.tabStatsFont);
		Fonts.drawCenteredText(g, "" + player.getStats().getScored(), STATS_WIDTH/8, Main.getHeight() - STATS_HEIGHT/3, Fonts.tabStatsFont);
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Kills", STATS_WIDTH * 3/8, Main.getHeight() - STATS_HEIGHT * 2/3, Fonts.tabStatsFont);
		Fonts.drawCenteredText(g, "" + player.getStats().getKills(), STATS_WIDTH * 3/8, Main.getHeight() - STATS_HEIGHT/3, Fonts.tabStatsFont);
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Deaths", STATS_WIDTH * 5/8, Main.getHeight() - STATS_HEIGHT * 2/3, Fonts.tabStatsFont);
		Fonts.drawCenteredText(g, "" + player.getStats().getDeaths(), STATS_WIDTH * 5/8, Main.getHeight() - STATS_HEIGHT/3, Fonts.tabStatsFont);
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Damage", STATS_WIDTH * 7/8, Main.getHeight() - STATS_HEIGHT * 2/3, Fonts.tabStatsFont);
		Fonts.drawCenteredText(g, "" + player.getStats().getDamage(), STATS_WIDTH * 7/8, Main.getHeight() - STATS_HEIGHT/3, Fonts.tabStatsFont);
	}
	
}
