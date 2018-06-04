package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import Utils.InputField;

public class KeyManager implements KeyListener{
	
	private boolean[] keys;
	private ArrayList<InputField> activeInputFields = new ArrayList<>();
	
	
	public KeyManager() {
		keys = new boolean[1024];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		for(InputField inputField : activeInputFields) {
			inputField.onKeyTyped(e);
		}
	}
	
	public boolean isKeyPressed(int id) {
		return keys[id];
	}
	
	public boolean[] getKeys() {
		return keys;
	}
	
	
	public void addActiveInputField(InputField input) {
		if(!activeInputFields.contains(input)) {
			activeInputFields.add(input);
		}
	}
	
	public void removeActiveInputFields(InputField input) {
		activeInputFields.remove(input);
	}

}
