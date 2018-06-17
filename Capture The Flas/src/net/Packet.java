package net;

public class Packet {
	
	public static final String INVALID = "-1";					// error message
	public static final String LOGIN = "00";					// username
	public static final String VALID_LOGIN = "01";				// -
	public static final String INVALID_LOGIN = "02";			// error message
	public static final String DISCONNECT = "03";				// username
	public static final String START_GAME = "04";				// -
	public static final String RESTART = "05";					// -
	public static final String WIN = "06";						// team
	
	public static final String CHANGE_TEAM = "10";				// username, team
	public static final String EQUIP_HERO = "11";				// username, tank, weapon
	
	public static final String UPDATE_PLAYER = "20";			// username, x, y, gunAngle
	public static final String SHOOT = "21";					// username
	public static final String HIT = "22";						// attacker, username, damage, projectileID
	public static final String REMOVE_PROJECTILE = "23";		// projectileId
	public static final String FLAG_PICKUP = "24";				// username, flagIndex
	public static final String FLAG_RETURN = "25";				// flagIndex
	public static final String SCORED = "26";					// flagIndex
	
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
