package Utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Fonts {
	
	public static Font playerNameFont = new Font("Arial", Font.PLAIN, 25);
	public static Font scoreFont = new Font("Arial", Font.BOLD, 45);
	public static Font killFeedFont = new Font("Arial", Font.PLAIN, 30);
	
	public static void drawCenteredText(Graphics g, String text, int x, int y, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		
		x = x - metrics.stringWidth(text) / 2;
		y = y - metrics.getHeight() / 2 + metrics.getAscent();
		
		g.setFont(font);
		g.drawString(text, x, y);
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

}
