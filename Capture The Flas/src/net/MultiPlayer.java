package net;

import java.net.InetAddress;

public class MultiPlayer {
	
	private InetAddress ipAddress;
	private int port;
	private String username;
	private int team;
	
	
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

}
