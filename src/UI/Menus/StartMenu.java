package UI.Menus;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Input.KeyManager;
import Main.Game;
import Main.Main;
import States.State;
import States.StateManager;
import Utils.Button;
import Utils.Fonts;
import Utils.InputField;

public class StartMenu extends State{
	
	private Game game;
	
	
	private int currentButton = -1;
	
	private String error;
	private boolean showError = false;
	
	
	private static final int titleYOffset = 150;
	
	private static final int joinButtonYOffset = Main.getHeight() * 3/4;
	private static final int joinButtonXDistance = 100;
	private static final int joinButtonWidth = 200;
	private static final int joinButtonHeight = 120;
	
	private static final int buttonWidth = 60;
	private static final int buttonHeight = 50;
	private static final int buttonXDistance = 20;
	private static final int buttonYDistance = 15;
	private static final int buttonYOffset = Main.getHeight() /3 ;
	
	private static final int nMiddle = 13;
	private static final int nRightSide = 9;
	private static final int nLeftSide = 8;
	
	private static final int usernameInputFieldYOffset = Main.getHeight() * 3/5 - 80;
	private static final int ipAddressInputFieldYOffset = Main.getHeight() * 3/5;
	private static final int inputFieldWidth = 400;
	private static final int inputFieldHeight = 50;
	
	private static final int errorYOffset = usernameInputFieldYOffset - 30;
	
	
	private ArrayList<Button> buttons = new ArrayList<>();
	private Button joinServerButton;
	private Button createServerButton;
	private InputField usernameInputField;
	private InputField ipAddressInputField;
	
	
	public StartMenu(KeyManager keyManager, Game game) {
		this.game = game;
		
		joinServerButton = new Button(Main.getWidth()/2 - joinButtonWidth - joinButtonXDistance/2, joinButtonYOffset - joinButtonHeight/2, joinButtonWidth, joinButtonHeight, 15, 15);
		joinServerButton.setColor(Color.GREEN);
		joinServerButton.setFont(Fonts.playButtonFont);
		joinServerButton.setText("Join");
		joinServerButton.setTextColor(Color.WHITE);
		
		createServerButton = new Button(Main.getWidth()/2 + joinButtonXDistance/2, joinButtonYOffset - joinButtonHeight/2, joinButtonWidth, joinButtonHeight, 15, 15);
		createServerButton.setColor(Color.GREEN);
		createServerButton.setFont(Fonts.playButtonFont);
		createServerButton.setText("Create");
		createServerButton.setTextColor(Color.WHITE);
		
		createButtons();
		
		usernameInputField = new InputField(keyManager, Main.getWidth()/2 - inputFieldWidth/2, usernameInputFieldYOffset, inputFieldWidth, inputFieldHeight, "username", 10, 10);
		ipAddressInputField = new InputField(keyManager, Main.getWidth()/2 - inputFieldWidth/2, ipAddressInputFieldYOffset, inputFieldWidth, inputFieldHeight, "ip address (optional)", 10, 10);
	}
	
	
	public void tick() {
		joinServerButton.tick();
		createServerButton.tick();
		for(Button button : buttons) {
			button.tick();
		}
		usernameInputField.tick();
		ipAddressInputField.tick();
		
		if(joinServerButton.isClicked()) {
			String username = usernameInputField.getText();		// (JOptionPane.showInputDialog("Please enter a username"));
			String ipAddress = ipAddressInputField.getText();
			StateManager.getGameState().getPlayer().getHero().setUsername(username);
			
			if(ipAddress == ipAddressInputField.getHint() || ipAddress == "") {
				if(currentButton == -1) {
					game.joinServer("localhost", username);
				}
				else {
					game.joinServer("10.9.116." + (currentButton+1), username);
				}
			}
			else {
				if(ipAddress.equals("nils")) {
					game.joinServer("87.147.204.208", username);
				}
				else {
					game.joinServer(ipAddress, username);
				}
			}
		}

		if(createServerButton.isClicked()) {
			String username = usernameInputField.getText();		// (JOptionPane.showInputDialog("Please enter a username"));
			StateManager.getGameState().getPlayer().getHero().setUsername(username);
			
			game.createServer(username);
		}
		
		for(Button button : buttons) {
			if(button.isClicked()) {
				if(currentButton != -1) {
					buttons.get(currentButton).setColor(Color.GRAY);
					buttons.get(currentButton).setTextColor(Color.BLACK);
				}
				
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
		usernameInputField.render(g);
		ipAddressInputField.render(g);
		
		if(showError) {
			renderError(g);
		}
	}
	
	public void renderError(Graphics g) {
		g.setColor(Color.RED);
		Fonts.drawCenteredText(g, error, Main.getWidth()/2, errorYOffset, Fonts.errorFont);
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
			button.setText("" + (nRightSide + (nMiddle-i)));
			button.setTextColor(Color.BLACK);
			buttons.add(button);
		}
		
		for(int i=0; i<nLeftSide; i++) {
			Button buttonLeft = new Button(buttonsXStart - buttonWidth - buttonXDistance, buttonYOffset + (i+1) * (buttonHeight+buttonYDistance), buttonWidth, buttonHeight);
			buttonLeft.setColor(Color.GRAY);
			buttonLeft.setFont(Fonts.buttonFont);
			buttonLeft.setText("" + (nRightSide + nMiddle + i + 1));
			buttonLeft.setTextColor(Color.BLACK);
			buttons.add(buttonLeft);
		}
		
	}

}
