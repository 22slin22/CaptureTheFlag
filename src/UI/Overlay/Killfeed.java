package UI.Overlay;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Hero;
import Utils.Fonts;
import Utils.Teams;

public class Killfeed {
	
	private ArrayList<Kill> kills = new ArrayList<>();
	
	private static final float KILL_SHOW_TIME = 5f;
	private static final int X_OFFSET = 20;
	private static final int Y_OFFSET = 50;
	private static final int Y_OFFSET_KILLS = 0;
	
	
	public void tick() {
		synchronized(kills) {
			for(Kill kill : kills) {
				if(System.currentTimeMillis() - kill.getAddTime() > KILL_SHOW_TIME * 1000) {
					kills.remove(kill);
					break;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		synchronized(kills) {
			for(Kill kill : kills) {
				renderKill(g, kill);
			}
		}
	}
	
	public void addKill(Hero killer, Hero target) {
		synchronized(kills) {
			kills.add(new Kill(killer, target, System.currentTimeMillis()));
		}
	}
	
	private void renderKill(Graphics g, Kill kill) {
		g.setFont(Fonts.killFeedFont);
		FontMetrics metrics = g.getFontMetrics(Fonts.killFeedFont);
		int fontHeight = metrics.getHeight(); // + metrics.getAscent();
		int y = kills.indexOf(kill) * (fontHeight + Y_OFFSET_KILLS) + Y_OFFSET;
		
		g.setFont(Fonts.killFeedFontArial);
		g.setColor(Teams.getColor(kill.getKiller().getTeam()));
		g.drawString(kill.getKillerName(), X_OFFSET, y);
		
		int x = X_OFFSET + metrics.stringWidth(kill.getKillerName());
		g.drawString(" " + Character.toString((char)8594) + " ", x, y);
		
		x = X_OFFSET + metrics.stringWidth(kill.getKillerName() + " " + Character.toString((char)8594) + " ");
		g.setFont(Fonts.killFeedFont);
		g.setColor(Teams.getColor(kill.getTarget().getTeam()));
		g.drawString(kill.getTargetName(), x, y);
	}

}
