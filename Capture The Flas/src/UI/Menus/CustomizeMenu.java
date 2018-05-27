package UI.Menus;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Main.Game;
import Main.Main;
import States.State;
import States.StateManager;
import States.States;
import Utils.Button;
import Utils.Fonts;
import net.Packet;

public class CustomizeMenu extends State{
	
	private Game game;
	
	
	private static final int titleYOffset = 150;
	
	private ArrayList<Button> tankButtons = new ArrayList<>();
	private ArrayList<Button> weaponButtons = new ArrayList<>();
	
	private static final int buttonsYOffset = Main.getHeight()/3;
	
	private static final int buttonWidth = 170;
	private static final int buttonHeight = 90;
	private static final int distanceBetweenButtonsX = 100;
	private static final int distanceBetweenButtonsY = 100;
	
	private int selectedTank = 0;
	private int selectedWeapon = 0;
	private static final Color defaultColor = Color.YELLOW;
	private static final Color selectedColor = new Color(243, 128, 37);
	
	
	private Button playButton;
	private static final int playButtonYOffset = Main.getHeight() * 3/4;
	private static final int playButtonWidth = 230;
	private static final int playButtonHeight = 120;
	
	
	public CustomizeMenu(Game game) {
		this.game = game;
		
		createButtons();
		
		tankButtons.get(selectedTank).setColor(selectedColor);
		weaponButtons.get(selectedWeapon).setColor(selectedColor);
	}
	
	
	public void tick() {
		for(Button button : tankButtons) {
			button.tick();
			if(button.isClicked()) {
				tankButtons.get(selectedTank).setColor(defaultColor);
				selectedTank = tankButtons.indexOf(button);
				tankButtons.get(selectedTank).setColor(selectedColor);
			}
		}
		for(Button button : weaponButtons) {
			button.tick();
			if(button.isClicked()) {
				weaponButtons.get(selectedWeapon).setColor(defaultColor);
				selectedWeapon = weaponButtons.indexOf(button);
				weaponButtons.get(selectedWeapon).setColor(selectedColor);
			}
		}
		
		playButton.tick();
		if(playButton.isClicked()) {
			Packet packet = new Packet(Packet.CHANGE_HERO, StateManager.getGameState().getEntityManager().getLocalPlayer().getUsername() + "," + selectedTank + "," + selectedWeapon);
			game.getClient().sendData(packet.getMessage());
			StateManager.changeState(States.LOBBY);
		}
	}

	@Override
	public void render(Graphics g) {
		Fonts.drawTitle(g, "Customize Character", new Color(190, 0, 0), Fonts.titleFont, titleYOffset, true);
		
		for(Button button : tankButtons) {
			button.render(g);
		}
		for(Button button : weaponButtons) {
			button.render(g);
		}
		playButton.render(g);
	}
	
	
	private void createButtons() {
		tankButtons.add(new Button(Main.getWidth()/2 - buttonWidth/2 - distanceBetweenButtonsX - buttonWidth, buttonsYOffset, buttonWidth, buttonHeight));
		tankButtons.get(0).setText("LIGHT");
		tankButtons.add(new Button(Main.getWidth()/2 - buttonWidth/2, buttonsYOffset, buttonWidth, buttonHeight));
		tankButtons.get(1).setText("MEDIUM");
		tankButtons.add(new Button(Main.getWidth()/2 + buttonWidth/2 + distanceBetweenButtonsX, buttonsYOffset, buttonWidth, buttonHeight));
		tankButtons.get(2).setText("HEAVY");
		
		weaponButtons.add(new Button(Main.getWidth()/2 - buttonWidth/2 - distanceBetweenButtonsX - buttonWidth, buttonsYOffset + distanceBetweenButtonsY + buttonHeight, buttonWidth, buttonHeight));
		weaponButtons.get(0).setText("GUN");
		weaponButtons.add(new Button(Main.getWidth()/2 - buttonWidth/2, buttonsYOffset + distanceBetweenButtonsY + buttonHeight, buttonWidth, buttonHeight));
		weaponButtons.get(1).setText("SHOTGUN");
		weaponButtons.add(new Button(Main.getWidth()/2 + buttonWidth/2 + distanceBetweenButtonsX, buttonsYOffset + distanceBetweenButtonsY + buttonHeight, buttonWidth, buttonHeight));
		weaponButtons.get(2).setText("LASER");
		
		for(Button button : tankButtons) {
			button.setColor(defaultColor);
			button.setFont(Fonts.buttonFont);
			button.setTextColor(Color.BLACK);
		}
		for(Button button : weaponButtons) {
			button.setColor(defaultColor);
			button.setFont(Fonts.buttonFont);
			button.setTextColor(Color.BLACK);
		}
		
		
		playButton = new Button(Main.getWidth()/2 - playButtonWidth/2, playButtonYOffset, playButtonWidth, playButtonHeight);
		playButton.setColor(Color.GREEN);
		playButton.setFont(Fonts.playButtonFont);
		playButton.setText("PLAY");
		playButton.setTextColor(Color.WHITE);
	}

}
