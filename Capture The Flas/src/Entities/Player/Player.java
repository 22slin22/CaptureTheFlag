package Entities.Player;

import Entities.Entity;
import Input.KeyManager;

public abstract class Player extends Entity{
	
	protected KeyManager keyManager;

	public Player(float x, float y, KeyManager keyManager) {
		super(x, y);
		this.keyManager = keyManager;
	}

}
