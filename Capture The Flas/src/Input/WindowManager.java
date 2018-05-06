package Input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import Main.Game;
import net.Packet;

public class WindowManager implements WindowListener{
	
	private Game game;
	
	
	public WindowManager(Game game) {
		this.game = game;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		Packet packet = new Packet(Packet.DISCONNECT, game.getEntityManager().getLocalPlayer().getUsername());
		game.getClient().sendData(packet.getMessage());
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

}
