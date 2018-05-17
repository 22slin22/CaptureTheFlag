package UI.Menus;

import java.awt.Color;
import java.awt.Graphics;

import Main.Game;
import Main.Main;
import States.State;
import Utils.Button;
import Utils.Fonts;
import Utils.Teams;
import net.Packet;

public class WinScreen extends State{
	
	private boolean isHost = false;
	
	private static final int winnerYOffset = 200;
	private int winner;
	
	private Button playAgain;
	private static final int playAgainWidth = 180;
	private static final int playAgainHeight = 80;
	private static final int playAgainYOffset = Main.getHeight()/2;
	
	private Game game;
	
	
	public WinScreen(Game game) {
		this.game = game;
		
		playAgain = new Button(Main.getWidth()/2 - playAgainWidth/2, playAgainYOffset - playAgainHeight/2, playAgainWidth, playAgainHeight);
		playAgain.setColor(Color.GREEN);
		playAgain.setFont(Fonts.buttonFont);
		playAgain.setText("Play Again");
		playAgain.setTextColor(Color.WHITE);
	}
	
	
	public void tick() {
		if(isHost) {
			playAgain.tick();
			if(playAgain.isClicked()) {
				Packet packet = new Packet(Packet.RESTART, "");
				game.getClient().sendData(packet.getMessage());
			}
		}
	}
	
	public void render(Graphics g) {
		renderWinnerTitle(g);
		if(isHost) {
			playAgain.render(g);
		}
	}

	private void renderWinnerTitle(Graphics g) {
		Fonts.drawCenteredText(g, new String[] {Teams.getTeamName(winner), " team won the game"}, Main.getWidth()/2, winnerYOffset, Fonts.winScreenWinner, new Color[]{Teams.getColor(winner), Color.BLACK});
		g.setColor(Teams.getColor(winner));
	}
	
	public void setWinner(int team) {
		winner = team;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

}
