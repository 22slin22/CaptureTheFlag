package UI.Overlay;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import Player.Player;
import Utils.Fonts;
import Utils.Teams;

public class Killfeed {
	
	private ArrayList<Kill> kills = new ArrayList<>();
	
	private static final float KILL_SHOW_TIME = 5f;
	private static final int X_OFFSET = 20;
	private static final int Y_OFFSET = 50;
	private static final int Y_OFFSET_KILLS = 0;
	
	
	
	public void tick() {
		for(Kill kill : kills) {
			if(System.currentTimeMillis() - kill.getAddTime() > KILL_SHOW_TIME * 1000) {
				kills.remove(kill);
				break;
			}
		}
	}
	
	public void render(Graphics g) {
		for(Kill kill : kills) {
			renderKill(g, kill);
		}
	}
	
	public void addKill(Player killer, Player target) {
		kills.add(new Kill(killer, target, System.currentTimeMillis()));
	}
	
	private void renderKill(Graphics g, Kill kill) {
		g.setFont(Fonts.killFeedFont);
		FontMetrics metrics = g.getFontMetrics(Fonts.killFeedFont);
		int fontHeight = metrics.getHeight(); // + metrics.getAscent();
		int y = kills.indexOf(kill) * (fontHeight + Y_OFFSET_KILLS) + Y_OFFSET;
		
		g.setColor(Teams.getColor(kill.getKiller().getTeam()));
		g.drawString(kill.getKillerName() + " -> ", X_OFFSET, y);
		
		int x = X_OFFSET + metrics.stringWidth(kill.getKillerName() + " -> ");
		g.setColor(Teams.getColor(kill.getTarget().getTeam()));
		g.drawString(kill.getTargetName(), x, y);
	}

}
