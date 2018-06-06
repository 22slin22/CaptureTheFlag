package Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import Main.Main;

public class Fonts {
	
	public static Font playerNameFont = new Font("Arial", Font.PLAIN, 25);
	public static Font scoreFont = new Font("Arial", Font.BOLD, 45);
	public static Font killFeedFont = new Font("Arial", Font.PLAIN, 30);
	
	public static Font lobbyTeamNameFont = new Font("Arial", Font.BOLD, 45);
	public static Font lobbyNameFont = new Font("Arial", Font.PLAIN, 30);
	public static Font buttonFont = new Font("Arial", Font.PLAIN, 30);
	public static Font playButtonFont = new Font("Arial", Font.BOLD, 50);
	public static Font inputFieldFont = new Font("", Font.ITALIC, 40);
	public static Font titleFont = new Font("", Font.BOLD, 75);
	public static Font winScreenWinner = new Font("", Font.BOLD, 60);
	public static Font errorFont = new Font("Arial", Font.ITALIC, 20);
	
	public static void drawCenteredText(Graphics g, String text, int x, int y, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		
		x = x - metrics.stringWidth(text) / 2;
		y = y - metrics.getHeight() / 2 + metrics.getAscent();
		
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	public static void drawCenteredText(Graphics g, String[] strings, int x, int y, Font font, Color[] colors) {
		String text = "";
		for(String s : strings) {
			text += s;
		}
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		x = x - metrics.stringWidth(text) / 2;
		y = y - metrics.getHeight() / 2 + metrics.getAscent();
		
		g.setFont(font);
		
		int xOffset = 0;
		for(int i = 0; i<strings.length; i++) {
			g.setColor(colors[i]);
			g.drawString(strings[i], x + xOffset, y);
			xOffset += metrics.stringWidth(strings[i]);
		}
	}
	
	public static void drawRightAllinedText(Graphics g, String text, int x, int y, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		
		x = x - metrics.stringWidth(text);
		y = y - metrics.getHeight() / 2 + metrics.getAscent();
		
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	public static void drawLeftAllinedText(Graphics g, String text, int x, int y, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		
		y = y - metrics.getHeight() / 2 + metrics.getAscent();
		
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	public static void drawTitle(Graphics g, String text, Color color, Font font, int titleYOffset, boolean shadow) {
		if(shadow) {
			g.setColor(Color.GRAY);
			Fonts.drawCenteredText(g, text, Main.getWidth()/2 + 4, titleYOffset + 5, font);
		}
		g.setColor(color);
		Fonts.drawCenteredText(g, text, Main.getWidth()/2, titleYOffset, font);
	}

}
