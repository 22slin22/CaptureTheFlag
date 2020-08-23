package UI.Overlay;

import java.awt.Color;
import java.awt.Graphics;

import Main.Main;
import Utils.Fonts;

public class Notification {
	
	private String[] text;
	private Color[] colors;
	
	// time when the notification started to be shown
	private long timeStartShowing;
	
	public Notification(String[] text, Color[] colors) {
		this.text = text;
		this.colors = colors;
	}
	
	public void render(Graphics g, int y) {
		Fonts.drawCenteredText(g, text, Main.getWidth()/2, y, Fonts.notificationFont, colors);
	}
	
	public void setTimeStartShowing() {
		timeStartShowing = System.currentTimeMillis();
	}
	
	public long getTimeStartShowing() {
		return timeStartShowing;
	}

}
