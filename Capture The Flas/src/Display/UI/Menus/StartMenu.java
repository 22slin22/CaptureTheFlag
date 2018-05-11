package Display.UI.Menus;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextField;

import Input.KeyManager;
import Main.Main;
import States.State;
import States.States;
import States.StateManager;
import Utils.Button;
import Utils.Fonts;

public class StartMenu extends State{
	
	/*
	private static int yOffset = 200;
	private static int WIDTH = Main.getWidth() / 2;
	private static int HEIGHT = Main.getHeight() - 2 * yOffset;
	private static int x = Main.getWidth() / 4;
	*/
	
	private static final int titleYOffset = 150;
	
	private Button playButton;
	
	private JTextField nameInput;
	
	
	public StartMenu(KeyManager keyManager) {
		/*
		nameInput = new JTextField("Hallo erst", 20);
		nameInput.requestFocus();
		//nameInput.setBounds(Main.getWidth()/2 - 50, Main.getHeight()/2 - 15, 100, 30);
		nameInput.setBounds(new Rectangle(700, 700, 800, 100));
		nameInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		nameInput.setBackground(Color.GREEN);
		nameInput.setForeground(Color.BLACK);
		nameInput.setEditable(true);
		//nameInput.addKeyListener(keyManager);
		//nameInput.setText("Hallo erstmal");
		 */
		
		playButton = new Button(Main.getWidth()/2 - 100, Main.getHeight()/2 - 60, 200, 120);
		playButton.setColor(Color.GREEN);
		playButton.setFont(Fonts.playButtonFont);
		playButton.setText("PLAY");
		playButton.setTextColor(Color.WHITE);
	}
	
	
	public void tick() {
		playButton.tick();
		if(playButton.isClicked()) {
			StateManager.changeState(States.LOBBY);
		}
	}
	
	public void render(Graphics g) {
		renderTitle(g);
		playButton.render(g);
		//nameInput.printAll(g);
	}
	
	/*
	private void renderLayout(Graphics g) {
		g.setColor(new Color(235, 235, 235));
		g.fillRect(x, yOffset, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(x, yOffset, WIDTH, HEIGHT);
	}
	*/
	
	private void renderTitle(Graphics g) {
		g.setColor(Color.GRAY);
		Fonts.drawCenteredText(g, "CAPTURE THE FLAG", Main.getWidth()/2 + 7, titleYOffset + 5, Fonts.startMenuTitleFont);
		g.setColor(new Color(7, 21, 188));
		Fonts.drawCenteredText(g, "CAPTURE THE FLAG", Main.getWidth()/2, titleYOffset, Fonts.startMenuTitleFont);
	}

}
