package net;

public class Packet {
	
	public static final String INVALID = "-1";
	public static final String LOGIN = "00";
	public static final String DISCONNECT = "01";
	public static final String UPDATE_PLAYER = "02";
	public static final String SHOOT = "03";
	public static final String HIT = "04";
	public static final String FLAG_PICKUP = "05";
	public static final String FLAG_RETURN = "06";
	public static final String SCORED = "07";
	public static final String START_GAME = "08";
	public static final String CHANGE_TEAM = "09";
	public static final String RESTART = "10";
	public static final String CHANGE_HERO = "11";
	
	private String id;
	private String data;
	
	
	public Packet(String id, String data) {
		this.id = id;
		this.data = data;
	}
	
	public Packet(String id, String[] data) {
		this.id = id;
		this.data = "";
		for(String d : data) {
			this.data += d;
		}
	}
	
	public Packet(byte[] content) {
		String message = new String(content).trim();
		id = message.substring(0, 2);
		data = message.substring(2);
	}
	
	
	public byte[] getMessage() {
		return (id + data).getBytes();
	}
	
	public String[] getData() {
		return this.data.split(",");
	}
	
	public String getId() {
		return id;
	}

}
