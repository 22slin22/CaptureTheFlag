package States;

import java.util.ArrayList;

import Display.UI.Menus.Lobby;
import Display.UI.Menus.StartMenu;
import Input.KeyManager;
import Input.MouseManager;
import Main.Game;
import Main.GameState;
import Main.Main;
import net.GameClient;

public class StateManager {
	
	private static State activeState;
	private static ArrayList<State> states = new ArrayList<>();
	
	
	public StateManager(KeyManager keyManager, MouseManager mouseManager, Main main, Game game) {
		states.add(new GameState(keyManager, mouseManager, game, main));
		states.add(new StartMenu(keyManager, game, getGameState().getEntityManager()));
		states.add(new Lobby(getGameState().getEntityManager().getPlayers(), game, getGameState().getEntityManager().getLocalPlayer()));
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

}
