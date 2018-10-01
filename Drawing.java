import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JComponent;

public class Drawing extends JComponent {
	private Image image;
	private boolean isBoard;
	private int xPos;
	private int yPos;

	public Drawing(int xPosition, int yPosition, Image images, boolean board) {
		image = images;
		isBoard = board;
		xPos = xPosition;
		yPos= yPosition;

	}
	//Paints the graphic
	public void paint(Graphics g) {		//Paints the board and the pieces in starting position. might have to change image library because it seems tough to edit this one. 
		Graphics2D g2 = (Graphics2D) g;
		if(isBoard){
			image.getScaledInstance(1000,1400, Image.SCALE_AREA_AVERAGING);
			g2.drawImage(image, 50,50, this);		
			}
		else{
			g2.drawImage(image, (xPos*500/8),(yPos*500/8),this);
		}
		g2.finalize();
	}
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
