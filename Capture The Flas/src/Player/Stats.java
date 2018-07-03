package Player;

public class Stats {
	
	private int kills = 0;
	private int deaths = 0;
	private int scored = 0;
	private int damage = 0;
	
	
	
	public void reset() {
		kills = 0;
		deaths = 0;
		scored = 0;
		damage = 0;
	}
	
	public void increaseKills() {
		kills += 1;
	}
	
	public int getKills() {
		return  kills;
	}
	
	public void increaseDeaths() {
		deaths += 1;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void increaseScored() {
		scored += 1;
	}
	
	public int getScored() {
		return scored;
	}
	
	public void increaseDamage(int damage) {
		this.damage += damage;
	}
	
	public int getDamage() {
		return damage;
	}

}
