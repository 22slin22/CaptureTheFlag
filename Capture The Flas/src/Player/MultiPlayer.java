package Player;

import java.net.InetAddress;

public class MultiPlayer {
	
	private InetAddress ipAddress;
	private int port;
	private String username;
	
	private int team;
	private int tank = -1;
	private int weapon = -1;
	
	
	public MultiPlayer(InetAddress ipAddress, int port, String username, int team) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.username = username;
		this.team = team;
	}
	
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getTeam() {
		return team;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public int getTank() {
		return tank;
	}
	
	public void setTank(int tank) {
		this.tank = tank;
	}
	
	public int getWeapon() {
		return weapon;
	}
	
	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}
}
