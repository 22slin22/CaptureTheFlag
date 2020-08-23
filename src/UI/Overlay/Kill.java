package UI.Overlay;

import Entities.Hero;

public class Kill {
	
	private Hero killer;
	private Hero target;
	private long addTime;
	
	public Kill(Hero killer, Hero target, long addTime) {
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
	
	public Hero getKiller() {
		return killer;
	}
	
	public Hero getTarget() {
		return target;
	}

}
