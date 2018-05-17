package UI.Overlay;

import Player.Player;

public class Kill {
	
	private Player killer;
	private Player target;
	private long addTime;
	
	public Kill(Player killer, Player target, long addTime) {
		this.killer = killer;
		this.target = target;
		this.addTime = addTime;
	}
	
	public String getKillerName() {
		return killer.getUsername();
	}
	
	public String getTargetName() {
		return target.getUsername();
	}
	
	public long getAddTime() {
		return addTime;
	}
	
	public Player getKiller() {
		return killer;
	}
	
	public Player getTarget() {
		return target;
	}

}
