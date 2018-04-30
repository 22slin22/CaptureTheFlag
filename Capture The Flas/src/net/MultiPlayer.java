package net;

import java.net.InetAddress;

public class MultiPlayer {
	
	private InetAddress ipAddress;
	private int port;
	private String username;
	
	
	public MultiPlayer(InetAddress ipAddress, int port, String username) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.username = username;
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

}
