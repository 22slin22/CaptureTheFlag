package UI.Overlay;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.EntityManager;
import Utils.Teams;

public class NotificationManager {
	
	private ArrayList<Notification> queue = new ArrayList<>();
	
	
	private static final int SHOW_TIME = 5000;		// in milliseconds
	private static final int Y = 100;
	
	
	private EntityManager entityManager;
	
	
	public NotificationManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	
	public void tick() {
		if(!queue.isEmpty()) {
			if(System.currentTimeMillis() - queue.get(0).getTimeStartShowing() >= SHOW_TIME) {
				queue.remove(0);
				
				// at index 0 now is a new notification
				if(!queue.isEmpty()) {
					queue.get(0).setTimeStartShowing();
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!queue.isEmpty()) {
			queue.get(0).render(g, Y);
		}
	}
	
	
	public void addNotification(Notification notification) {
		if(queue.isEmpty()) {
			notification.setTimeStartShowing();
		}
		queue.add(notification);
	}
	
	/*
	public void addKillNotification(String usernameKiller, String usernameVictim) {
		addNotification(new Notification(new String[] {usernameKiller, " has killed ", usernameVictim}, 
				new Color[] {Teams.getColor(entityManager.getHero(usernameKiller).getTeam()), 
						Color.GRAY, 
						Teams.getColor(entityManager.getHero(usernameVictim).getTeam())}));
	}
	*/
	
	public void addNotification(String username, String text) {
		addNotification(new Notification(new String[] {username, " " + text},
				new Color[] {Teams.getColor(entityManager.getHero(username).getTeam()), Color.GRAY}));
	}

}
