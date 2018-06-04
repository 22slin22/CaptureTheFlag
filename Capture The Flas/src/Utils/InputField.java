package Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import Input.KeyManager;
import Input.MouseManager;

public class InputField {
	
	private KeyManager keyManager;
	
	
	private int x, y;
	private int width, height;
	
	private String text = "";
	private boolean active = false;
	private boolean showHint;
	
	
	private static final int USERNAME_MAX_LENGHT = 15;
	
	private static final int arcWidth = 10;
	private static final int arcHeight = 10;
	
	private static final int TEXT_X_OFFSET = 10;
	private static final int TEXT_Y_OFFSET = 11;
	
	
	private Rectangle clickBox;
	
	
	public InputField(KeyManager keyManager, int x, int y, int width, int height) {
		this.keyManager = keyManager;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		clickBox = new Rectangle(x, y, width, height);
		
		keyManager.addActiveInputField(this);
	}
	
	public InputField(KeyManager keyManager, int x, int y, int width, int height, String hint) {
		this.keyManager = keyManager;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = hint;
		
		clickBox = new Rectangle(x, y, width, height);
		showHint = true;
		
		keyManager.addActiveInputField(this);
	}
	
	
	public void tick() {
		if(MouseManager.hasClicked()) {
			if(clickBox.contains(MouseManager.getX(), MouseManager.getY())) {
				if(showHint) {
					text = "";
					showHint = false;
				}
			
				if(!active)
					active = true;
				keyManager.addActiveInputField(this);
			}
			else {
				if(active) 
					active = false;
				keyManager.removeActiveInputFields(this);
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
		
		g.setFont(Fonts.inputFieldFont);
		g.drawString(text, x + TEXT_X_OFFSET, y + height - TEXT_Y_OFFSET);
	}
	
	
	public void onKeyTyped(KeyEvent e) {
		if(active) {
			Character c = e.getKeyChar();
			if((Character.isLetter(c) || Character.isDigit(c)) && text.length() < USERNAME_MAX_LENGHT) {
				text += e.getKeyChar();
			}
			if(c.equals('\b') && text.length() > 0) {
				text = text.substring(0, text.length()-1);
			}
		}
	}
	
	
	public String getText() {
		return text;
	}

}
