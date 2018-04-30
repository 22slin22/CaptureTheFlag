package Player;

import java.net.InetAddress;

import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;

public class PlayerMP extends Player{

	public InetAddress ipAddress;
	public int port;
	
	public PlayerMP(KeyManager keyManager, MouseManager mouseManager, Camera camera, String username, InetAddress ipAddress, int port) {
		super(keyManager, mouseManager, camera, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public PlayerMP(Camera camera, String username, InetAddress ipAddress, int port) {
		super(null, null, camera, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	@Override
	public void tick(float elapsedTime) {
		super.tick(elapsedTime);
	}

}
