package UI.Menus;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Entities.Hero;
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
	private static final int playAgainYOffset = Main.getHeight() * 3/4;
	
	private Game game;
	private EntityManager entityManager;
	
	private Hero playerMostKills;
	private Hero playerMostScored;
	private Hero playerMostDamage;
	
	
	public WinScreen(Game game, EntityManager entityManager) {
		this.game = game;
		this.entityManager = entityManager;
		
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
		renderBestPlayers(g);
		if(isHost) {
			playAgain.render(g);
		}
	}

	private void renderWinnerTitle(Graphics g) {
		Fonts.drawCenteredText(g, new String[] {Teams.getTeamName(winner), " team won the game"}, Main.getWidth()/2, winnerYOffset, Fonts.winScreenWinner, new Color[]{Teams.getColor(winner), Color.BLACK});
		g.setColor(Teams.getColor(winner));
	}
	
	private void renderBestPlayers(Graphics g) {
		int yOffset = Main.getHeight()/15;
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Most Kills", Main.getWidth() / 4, Main.getHeight()/2 - yOffset, Fonts.lobbyNameFont);
		g.setColor(Teams.getColor(playerMostKills.getTeam()));
		Fonts.drawCenteredText(g, playerMostKills.getUsername(), Main.getWidth() / 4, Main.getHeight() / 2, Fonts.winScreenNameFont);
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "" + playerMostKills.getStats().getKills(), Main.getWidth() / 4, Main.getHeight()/2 + yOffset, Fonts.lobbyNameFont);
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Most Flag Returns", Main.getWidth() / 2, Main.getHeight()/2 - yOffset, Fonts.lobbyNameFont);
		g.setColor(Teams.getColor(playerMostScored.getTeam()));
		Fonts.drawCenteredText(g, playerMostScored.getUsername(), Main.getWidth() / 2, Main.getHeight() / 2, Fonts.winScreenNameFont);
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "" + playerMostScored.getStats().getScored(), Main.getWidth() / 2, Main.getHeight()/2 + yOffset, Fonts.lobbyNameFont);
		
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "Most Damage", Main.getWidth() * 3/4, Main.getHeight()/2 - yOffset, Fonts.lobbyNameFont);
		g.setColor(Teams.getColor(playerMostDamage.getTeam()));
		Fonts.drawCenteredText(g, playerMostDamage.getUsername(), Main.getWidth() * 3/4, Main.getHeight() / 2, Fonts.winScreenNameFont);
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, "" + playerMostDamage.getStats().getDamage(), Main.getWidth() * 3/4, Main.getHeight()/2 + yOffset, Fonts.lobbyNameFont);
	}
	
	
	private Hero getPlayerMostKills() {
		int mostKills = -1;
		Hero best = null;
		
		synchronized(entityManager.getHeros()) {
			for(Hero hero : entityManager.getHeros()) {
				if(hero.getStats().getKills() > mostKills) {
					best = hero;
					mostKills = hero.getStats().getKills();
				}
			}
		}
		return best;
	}
	
	private Hero getPlayerMostScored() {
		int mostScored = -1;
		Hero best = null;
		
		synchronized(entityManager.getHeros()) {
			for(Hero hero : entityManager.getHeros()) {
				if(hero.getStats().getScored() > mostScored) {
					best = hero;
					mostScored = hero.getStats().getScored();
				}
			}
		}
		return best;
	}
	
	private Hero getPlayerMostDamage() {
		int mostDamage = -1;
		Hero best = null;
		
		synchronized(entityManager.getHeros()) {
			for(Hero hero : entityManager.getHeros()) {
				if(hero.getStats().getDamage() > mostDamage) {
					best = hero;
					mostDamage = hero.getStats().getDamage();
				}
			}
		}
		return best;
	}
	
	public void setBestPlayers() {
		playerMostKills = getPlayerMostKills();
		playerMostScored = getPlayerMostScored();
		playerMostDamage = getPlayerMostDamage();
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
