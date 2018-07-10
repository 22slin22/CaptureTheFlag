package UI.Overlay;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Collections;

import Entities.EntityManager;
import Entities.Hero;
import Input.KeyManager;
import Main.Main;
import Utils.Fonts;
import Utils.Teams;

public class Tab {
	
	//private static final int STATS_WIDTH = Main.getWidth() / 2;
	//private static final int STATS_HEIGHT = Main.getHeight() / 5;
	
	private static final int STATS_WIDTH = Main.getWidth() * 3/4;
	private static final int STATS_HEIGHT = Main.getHeight() * 3/4;
	private static int X;
	private static int Y;
	private static final int TEAM_NAME_SPACE_HEIGHT = 100;
	private static final int SPACE_BETWEEN_NAMES = 10;
	private static final int NAME_X_OFFSET = 20;
	private static final int NAME_Y_OFFSET = 80;
	private static final float PROPORTION_NAME = 2f/6f;
	private static final float PROPROTION_STAT = 1f/6f;		// 2/6 + 4 * 1/6 (4 Stats) = 1
	
	
	private KeyManager keyManager;
	private Hero player;
	private EntityManager entityManager;
	
	
	public Tab(KeyManager keyManager, Hero player, EntityManager entityManager) {
		this.keyManager = keyManager;
		this.player = player;
		this.entityManager = entityManager;
		
		X = Main.getWidth() / 2 - STATS_WIDTH / 2;
		Y = Main.getHeight()/2 - STATS_HEIGHT/2;
	}
	
	
	public void render(Graphics g) {
		if(keyManager.isKeyPressed(KeyEvent.VK_TAB)) {
			renderStats(g);
		}
	}
	
	private void renderStats(Graphics g) {
		// render box
		g.setColor(new Color(0.7f, 0.7f, 0.7f, 0.8f));
		g.fillRect(X, Y, STATS_WIDTH, STATS_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(X, Y, STATS_WIDTH, STATS_HEIGHT);
		g.drawLine(Main.getWidth()/2, Y, Main.getWidth()/2, Main.getHeight()/2 + STATS_HEIGHT/2);
		g.drawLine(X, Y + TEAM_NAME_SPACE_HEIGHT, X + STATS_WIDTH, Y + TEAM_NAME_SPACE_HEIGHT);
		
		g.setColor(Teams.getColor(Teams.BLUE));
		Fonts.drawCenteredText(g, "BLUE", Main.getWidth()/2 - STATS_WIDTH/4, Y + TEAM_NAME_SPACE_HEIGHT/2, Fonts.lobbyTeamNameFont);
		g.setColor(Teams.getColor(Teams.RED));
		Fonts.drawCenteredText(g, "RED", Main.getWidth()/2 + STATS_WIDTH/4, Y + TEAM_NAME_SPACE_HEIGHT/2, Fonts.lobbyTeamNameFont);
		
		// render stats name
		g.setColor(Color.BLACK);
		int yStatsName = Y + TEAM_NAME_SPACE_HEIGHT + NAME_Y_OFFSET/3;
		Fonts.drawCenteredText(g, "Scored", (int) (X + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT/2f))), yStatsName, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "Kills", (int) (X + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT * 3/2))), yStatsName, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "Deaths", (int) (X + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT * 5/2))), yStatsName, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "Damage", (int) (X + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT * 7/2))), yStatsName, Fonts.lobbyNameFont);
		
		Fonts.drawCenteredText(g, "Scored", (int) (Main.getWidth()/2 + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT/2f))), yStatsName, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "Kills", (int) (Main.getWidth()/2 + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT * 3/2))), yStatsName, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "Deaths", (int) (Main.getWidth()/2 + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT * 5/2))), yStatsName, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "Damage", (int) (Main.getWidth()/2 + ((STATS_WIDTH/2) * (PROPORTION_NAME + PROPROTION_STAT * 7/2))), yStatsName, Fonts.lobbyNameFont);
		
		// render player on leaderboard
		synchronized(entityManager.getHeros()) {
			Collections.sort(entityManager.getHeros());
		}
		
		int blues = 0;
		int reds = 0;
		
		g.setFont(Fonts.lobbyNameFont);
		FontMetrics metrics = g.getFontMetrics();
		int fontHeight = metrics.getHeight();
		
		synchronized (entityManager.getHeros()) {
			for(Hero hero : entityManager.getHeros()) {
				g.setColor(Teams.getColor(hero.getTeam()));
				
				if(hero.getTeam() == Teams.BLUE) {
					int y = Y + TEAM_NAME_SPACE_HEIGHT + NAME_Y_OFFSET + (fontHeight + SPACE_BETWEEN_NAMES) * blues;
					int x = X;
					renderPlayerStats(g, hero, x, y);
					
					blues += 1;
				}
				else if(hero.getTeam() == Teams.RED) {
					int y = Y + TEAM_NAME_SPACE_HEIGHT + NAME_Y_OFFSET + (fontHeight + SPACE_BETWEEN_NAMES) * reds;
					int x = Main.getWidth()/2;
					renderPlayerStats(g, hero, x, y);
					
					reds += 1;
				}
			}
		}
	}
	
	private void renderPlayerStats(Graphics g, Hero hero, int x, int y) {
		Fonts.drawLeftAllinedText(g, hero.getUsername(), x + NAME_X_OFFSET, y, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "" + hero.getStats().getScored(), (int) (x + (PROPORTION_NAME + PROPROTION_STAT/2f) * STATS_WIDTH / 2), y, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "" + hero.getStats().getKills(), (int) (x + (PROPORTION_NAME + PROPROTION_STAT * 3/2) * STATS_WIDTH / 2), y, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "" + hero.getStats().getDeaths(), (int) (x + (PROPORTION_NAME + PROPROTION_STAT * 5/2) * STATS_WIDTH / 2), y, Fonts.lobbyNameFont);
		Fonts.drawCenteredText(g, "" + hero.getStats().getDamage(), (int) (x + (PROPORTION_NAME + PROPROTION_STAT * 7/2) * STATS_WIDTH / 2), y, Fonts.lobbyNameFont);
	}
	
	/*
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
	*/
	
}
