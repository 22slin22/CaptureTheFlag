package UI.Menus;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Main.Main;
import States.State;
import Utils.Button;
import Utils.Fonts;

public class StartMenu extends State{
	
	private Game game;
	private EntityManager entityManager;
	
	/*
	private static int yOffset = 200;
	private static int WIDTH = Main.getWidth() / 2;
	private static int HEIGHT = Main.getHeight() - 2 * yOffset;
	private static int x = Main.getWidth() / 4;
	*/
	
	private static final int titleYOffset = 150;
	
	private Button joinServerbutton;
	private Button createServerButton;
	
	private int joinButtonYOffset = Main.getHeight() * 3/4;
	private int joinButtonXDistance = 100;
	private int joinButtonWidth = 200;
	private int joinButtonHeight = 120;
	
	
	private ArrayList<Button> buttons = new ArrayList<>();
	
	private int currentButton;
	private int buttonWidth = 60;
	private int buttonHeight = 50;
	private int buttonXDistance = 20;
	private int buttonYDistance = 15;
	private int buttonYOffset = Main.getHeight() /3 ;
	
	private int nMiddle = 12;
	private int nRightSide = 9;
	private int nLeftSide = 8;
	
	
	public StartMenu(KeyManager keyManager, Game game, EntityManager entityManager) {
		this.game = game;
		this.entityManager = entityManager;
		
		joinServerbutton = new Button(Main.getWidth()/2 - joinButtonWidth - joinButtonXDistance/2, joinButtonYOffset - joinButtonHeight/2, joinButtonWidth, joinButtonHeight);
		joinServerbutton.setColor(Color.GREEN);
		joinServerbutton.setFont(Fonts.playButtonFont);
		joinServerbutton.setText("Join");
		joinServerbutton.setTextColor(Color.WHITE);
		
		createServerButton = new Button(Main.getWidth()/2 + joinButtonXDistance/2, joinButtonYOffset - joinButtonHeight/2, joinButtonWidth, joinButtonHeight);
		createServerButton.setColor(Color.GREEN);
		createServerButton.setFont(Fonts.playButtonFont);
		createServerButton.setText("Create");
		createServerButton.setTextColor(Color.WHITE);
		
		createButtons();
	}
	
	
	public void tick() {
		joinServerbutton.tick();
		if(joinServerbutton.isClicked()) {
			MouseManager.setLeftButton(false);
			entityManager.getLocalPlayer().setUsername(JOptionPane.showInputDialog("Please enter a username"));
			
			if(currentButton == 0)
				game.joinServer("localhost");
			else
				game.joinServer("10.9.116." + (currentButton+1));
		}
		createServerButton.tick();
		if(createServerButton.isClicked()) {
			entityManager.getLocalPlayer().setUsername(JOptionPane.showInputDialog("Please enter a username"));
			
			game.createServer();
		}
		
		for(Button button : buttons) {
			button.tick();
			if(button.isClicked()) {
				buttons.get(currentButton).setColor(Color.GRAY);
				buttons.get(currentButton).setTextColor(Color.BLACK);
				
				currentButton = Integer.parseInt(button.getText()) - 1;
				button.setColor(new Color(12, 77, 4));
				button.setTextColor(Color.WHITE);
			}
		}
	}
	
	public void render(Graphics g) {
		renderTitle(g);
		
		joinServerbutton.render(g);
		createServerButton.render(g);
		for(Button button : buttons) {
			button.render(g);
		}
	}
	
	private void renderTitle(Graphics g) {
		g.setColor(Color.GRAY);
		Fonts.drawCenteredText(g, "CAPTURE THE FLAG", Main.getWidth()/2 + 7, titleYOffset + 5, Fonts.startMenuTitleFont);
		g.setColor(new Color(7, 21, 188));
		Fonts.drawCenteredText(g, "CAPTURE THE FLAG", Main.getWidth()/2, titleYOffset, Fonts.startMenuTitleFont);
	}
	
	
	private void createButtons() {
		int buttonsXStart = Main.getWidth()/2 - (nMiddle*buttonWidth + (nMiddle-1) * buttonXDistance)/2;
		int buttonsXStart2 = buttonsXStart + ((nMiddle) * (buttonXDistance+buttonWidth));
		
		//	strange for loops to create buttons ordered by number	e.g. Button with "1" is created first;
		for(int i=nRightSide-1; i>=0; i--) {
			Button buttonRight = new Button(buttonsXStart2, buttonYOffset + (i+1) * (buttonHeight+buttonYDistance), buttonWidth, buttonHeight);
			buttonRight.setColor(Color.GRAY);
			buttonRight.setFont(Fonts.buttonFont);
			buttonRight.setText("" + (nRightSide-i));
			buttonRight.setTextColor(Color.BLACK);
			buttons.add(buttonRight);
		}
		
		for(int i=nMiddle-1; i>=0; i--) {
			Button button = new Button(buttonsXStart + i*(buttonWidth + buttonXDistance), buttonYOffset, buttonWidth, buttonHeight);
			button.setColor(Color.GRAY);
			button.setFont(Fonts.buttonFont);
			button.setText("" + (nRightSide + (nMiddle-i) * 1));
			button.setTextColor(Color.BLACK);
			buttons.add(button);
		}
		
		for(int i=0; i<nLeftSide; i++) {
			Button buttonLeft = new Button(buttonsXStart - buttonWidth - buttonXDistance, buttonYOffset + (i+1) * (buttonHeight+buttonYDistance), buttonWidth, buttonHeight);
			buttonLeft.setColor(Color.GRAY);
			buttonLeft.setFont(Fonts.buttonFont);
			buttonLeft.setText("" + (nLeftSide + nMiddle + i + 1));
			buttonLeft.setTextColor(Color.BLACK);
			buttons.add(buttonLeft);
		}
		
	}

}
