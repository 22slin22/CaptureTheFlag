package States;

import java.util.ArrayList;

import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Main.GameState;
import Main.Main;
import UI.Menus.Lobby;
import UI.Menus.StartMenu;
import UI.Menus.WinScreen;
import net.GameClient;

public class StateManager {
	
	private static State activeState;
	private static ArrayList<State> states = new ArrayList<>();
	
	
	public StateManager(KeyManager keyManager, MouseManager mouseManager, Main main, Game game) {
		states.add(new GameState(keyManager, mouseManager, game, main));
		states.add(new StartMenu(keyManager, game, getGameState().getEntityManager()));
		states.add(new Lobby(getGameState().getEntityManager().getPlayers(), game, getGameState().getEntityManager().getLocalPlayer()));
		states.add(new WinScreen(game));
	}
	
	
	public static void changeState(int state) {
		activeState = states.get(state);
	}
	
	public static State getActiveState() {
		return activeState;
	}
	
	public static GameState getGameState() {
		return (GameState)states.get(States.GAME_STATE);
	}
	
	public static Lobby getLobby(){
		return (Lobby)states.get(States.LOBBY);
	}
	
	public static WinScreen getWinScreen() {
		return (WinScreen) states.get(States.WIN_SCREEN);
	}

}
