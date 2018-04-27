package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener{
	
	private boolean leftButton;
	private int x = 0;
	private int y = 0;
	

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftButton = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftButton = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	
	public boolean isLeftButton() {
		return leftButton;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
