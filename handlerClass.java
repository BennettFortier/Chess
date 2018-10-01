
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class handlerClass extends JFrame implements MouseListener, MouseMotionListener {
	private JPanel mousePanel;
	private JLabel statusBar;
	private Boolean mouseIsClicked = false;
	private int mouseX;
	private int mouseY;
	MouseEvent e;
	public handlerClass(){
		statusBar = statusBar;

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsClicked = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int getMouseXPosition(){
		return mouseX;
	}

	public int getMouseYPosition(){
		return mouseY;
	}

	public boolean getMouseClicked(){
		return mouseIsClicked;
	}
	public void setMouseClicked(boolean a){
		mouseIsClicked = a;
	}

}

