package Display.UI.Lobbys;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import Main.Game;
import Main.Main;
import Player.Player;
import Utils.Button;
import Utils.Fonts;
import Utils.Teams;
import net.GameClient;
import net.Packet;

public class Lobby {
	
	public ArrayList<Player> players = new ArrayList<>();
	
	private boolean isHost = false;
	
	private int yOffset = 200;
	private int WIDTH = Main.getWidth() / 2;
	private int HEIGHT = Main.getHeight() - 2 * yOffset;
	private int x = Main.getWidth() / 4;
	
	private Button startButton;
	
	private Game game;
	private GameClient client;
	
	
	public Lobby() {
		startButton = new Button(Main.getWidth()/2 - 70, yOffset + HEIGHT + yOffset/2 - 30, 140, 60);
		startButton.setColor(Color.YELLOW);
		startButton.setText("Start");
		startButton.setTextColor(Color.BLACK);
		startButton.setFont(Fonts.buttonFont);
	}
	public Lobby(ArrayList<Player> players, Game game, GameClient client) {
		this.players = players;
		this.game = game;
		this.client = client;
		
		startButton = new Button(Main.getWidth()/2 - 70, yOffset + HEIGHT + yOffset/2 - 30, 140, 60);
		startButton.setColor(Color.YELLOW);
		startButton.setText("Start");
		startButton.setTextColor(Color.BLACK);
		startButton.setFont(Fonts.buttonFont);
	}
	
	
	public void tick() {
		startButton.tick();
		if(startButton.isClicked() && isHost) {
			game.startGame();
			
			Packet packet = new Packet(Packet.START_GAME, "");
			client.sendData(packet.getMessage());
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Main.getWidth(), Main.getHeight());
		
		renderLayout(g);
		renderNames(g);
		
		if(isHost)
			startButton.render(g);
	}
	
	private int teamNameSpace = 100;
	
	private void renderLayout(Graphics g) {
		g.setColor(new Color(235, 235, 235));
		g.fillRect(x, yOffset, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(x, yOffset, WIDTH, HEIGHT);
		g.drawLine(x, yOffset + teamNameSpace, x + WIDTH, yOffset + teamNameSpace);
		g.drawLine(Main.getWidth()/2, yOffset, Main.getWidth()/2, yOffset + HEIGHT);
		
		g.setColor(Teams.getColor(Teams.BLUE));
		Fonts.drawCenteredText(g, "BLUE", x + WIDTH/4, yOffset + teamNameSpace/2, Fonts.lobbyTeamNameFont);
		g.setColor(Teams.getColor(Teams.RED));
		Fonts.drawCenteredText(g, "RED", Main.getWidth()/2 + WIDTH/4, yOffset + teamNameSpace/2, Fonts.lobbyTeamNameFont);
	}
	
	private int spaceBetweenNames = 10;
	private int nameXOffset = 20;
	
	private void renderNames(Graphics g) {
		int blues = 0;
		int reds = 0;
		
		g.setFont(Fonts.lobbyNameFont);
		FontMetrics metrics = g.getFontMetrics();
		int fontHeight = metrics.getHeight();
		
		synchronized (players) {
			for(Player player : players) {
				g.setColor(Teams.getColor(player.getTeam()));
				
				if(player.getTeam() == Teams.BLUE) {
					int y = yOffset + teamNameSpace + fontHeight + (fontHeight + spaceBetweenNames) * blues;
					g.drawString(player.getUsername(), x + nameXOffset, y);
					blues += 1;
				}
				else if(player.getTeam() == Teams.RED) {
					int y = yOffset + teamNameSpace + fontHeight + (fontHeight + spaceBetweenNames) * reds;
					g.drawString(player.getUsername(), x + WIDTH/2 + nameXOffset, y);
					reds += 1;
				}
			}
		}
	}
	
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

}
