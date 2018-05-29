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
import States.StateManager;
import Utils.Button;
import Utils.Fonts;

public class StartMenu extends State{
	
	private Game game;
	private EntityManager entityManager;
	private MouseManager mouseManager;
	
	private static final int titleYOffset = 150;
	
	private Button joinServerButton;
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
	
	
	private String error;
	private boolean showError = false;
	
	
	public StartMenu(KeyManager keyManager, MouseManager mouseManager, Game game, EntityManager entityManager) {
		this.game = game;
		this.entityManager = entityManager;
		
		joinServerButton = new Button(Main.getWidth()/2 - joinButtonWidth - joinButtonXDistance/2, joinButtonYOffset - joinButtonHeight/2, joinButtonWidth, joinButtonHeight);
		joinServerButton.setColor(Color.GREEN);
		joinServerButton.setFont(Fonts.playButtonFont);
		joinServerButton.setText("Join");
		joinServerButton.setTextColor(Color.WHITE);
		
		createServerButton = new Button(Main.getWidth()/2 + joinButtonXDistance/2, joinButtonYOffset - joinButtonHeight/2, joinButtonWidth, joinButtonHeight);
		createServerButton.setColor(Color.GREEN);
		createServerButton.setFont(Fonts.playButtonFont);
		createServerButton.setText("Create");
		createServerButton.setTextColor(Color.WHITE);
		
		createButtons();
	}
	
	
	public void tick() {
		joinServerButton.tick();
		if(joinServerButton.isClicked()) {
			String username = (JOptionPane.showInputDialog("Please enter a username"));
			StateManager.getGameState().getPlayer().getHero().setUsername(username);
			
			if(currentButton == 0)
				game.joinServer("localhost", username);
			else
				game.joinServer("10.9.116." + (currentButton+1), username);
		}
		createServerButton.tick();
		if(createServerButton.isClicked()) {
			String username = (JOptionPane.showInputDialog("Please enter a username"));
			StateManager.getGameState().getPlayer().getHero().setUsername(username);
			
			game.createServer(username);
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
		Fonts.drawTitle(g, "CAPTURE THE FLAG", new Color(7, 21, 188), Fonts.titleFont, titleYOffset, true);
		
		joinServerButton.render(g);
		createServerButton.render(g);
		for(Button button : buttons) {
			button.render(g);
		}
	}
	
	public void showError(String error) {
		this.error = error;
		showError = true;
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
