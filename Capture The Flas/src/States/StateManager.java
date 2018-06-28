package States;

import java.util.ArrayList;

import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Main.Main;
import UI.Menus.CustomizeMenu;
import UI.Menus.Lobby;
import UI.Menus.StartMenu;
import UI.Menus.WinScreen;

public class StateManager {
	
	private static State activeState;
	private static ArrayList<State> states = new ArrayList<>();
	
	
	public static void init(KeyManager keyManager, Main main, Game game) {
		states.add(new GameState(keyManager, game));
		states.add(new StartMenu(keyManager, game));
		states.add(new Lobby(getGameState().getEntityManager().getHeros(), game, getGameState().getPlayer()));
		states.add(new WinScreen(game));
		states.add(new CustomizeMenu(game));
	}
	
	public static void changeState(int state) {
		activeState = states.get(state);
		MouseManager.setLeftButton(false);
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
	
	public static StartMenu getStartMenu() {
		return (StartMenu) states.get(States.START_MENU);
	}
	
	public static CustomizeMenu getCustomizeMenu() {
		return (CustomizeMenu) states.get(States.CUSTOMIZE_MENU);
	}

}
