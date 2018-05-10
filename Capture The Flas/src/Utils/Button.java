package Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Input.MouseManager;

public class Button {
	
	private int x, y;
	private int width, height;
	
	private boolean clicked;
	private boolean pressed;
	
	private String text;
	private Font font;
	private Color color = Color.BLACK;
	private Color textColor = Color.WHITE;
	
	
	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public void tick() {
		if(clicked)
			clicked = false;
		
		if(MouseManager.isLeftButton() && containsMouse()) {
			if(!pressed) {
				clicked = true;
			}
			pressed = true;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		
		g.fillRect(x, y, width, height);
		
		if(text != null) {
			g.setColor(textColor);
			Fonts.drawCenteredText(g, text, x + width/2, y + height/2, font);
		}
	}
	
	private boolean containsMouse() {
		if(MouseManager.getX() >= x && MouseManager.getX() <= x + width
				&& MouseManager.getY() >= y && MouseManager.getY() <= y + height) {
			return true;
		}
		return false;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setTextColor(Color color) {
		textColor = color;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
}
