package mandelbrot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import mandelbrot.HistoryStack.HistoryStackException;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * a JPanel that displays the bitmap of the complex plane and listens for
 * MouseEvents that indicate a new image should be created
 */
@SuppressWarnings("serial")
public class ComplexImage extends JPanel implements MouseInputListener {
	/**
	 * this image is these numbers of pixels from the left and top of the
	 * parent control
	 */
	public final static int HORIZ_OFFSET = 5,
			VERT_OFFSET = 5;
	/**
	 * instance of a HistoryStack that saves the history of ComplexSets
	 */
	private HistoryStack history;
	/**
	 * the ComplexSet object that holds the range of complex values
	 */
	private ComplexSet theSet;
	/**
	 * holds the set created on instantiation
	 */
	private ComplexSet defaultSet;
	/**
	 * a BufferedImage to display the image 
	 */
	private BufferedImage screen;
	/**
	 * our image is a port-view onto a part of the complex plane. Which
	 * part is in view is controlled by the zoom, xValue and yValue
	 */
	private double zoom, zoomStep;
	private static double zoomFactor;
	private int xValue, yValue;
	/**
	 * a handle to the parent window
	 */
	private Main parent;

	/**
	 * set fields to default values and setup initial view
	 */
	public ComplexImage(Main parent) {
		super();
		setBounds(5, 5, 600, 600); // position this control in the window

		this.parent = parent;
		addMouseMotionListener(this);
		addMouseListener(this);

		zoom = 250d; // arbitrary values to present a "nice looking" graph
		zoomStep = 100;
		zoomFactor =2.0d;
		xValue = 0;
		yValue = 0;

		history = new HistoryStack();
		theSet = new ComplexSet(zoom, zoomStep, xValue, yValue);
		defaultSet = theSet;
		history.push(theSet);

		fillImage();
	}

	/**
	 * responsible for creating the BufferedImage that displays the range of
	 * complex values
	 */
	private void fillImage() {
		Main.log(ComplexImage.class.getSimpleName() + ".fillImage() entered");

		screen = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
		ComplexValue[][] theValues = theSet.getComplexSet();
		for (int y = 0; y < ComplexSet.ROWS; y++) {
			for (int x = 0; x < ComplexSet.COLS; x++) {
				screen.setRGB(x, y, theValues[x][y].getColor());
			}
		}
	}

	/** let the parent container know how big this control should be
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return new Dimension(ComplexSet.COLS,ComplexSet.ROWS);
	}

	/**
	 * display the bitmap image
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * <p>
	 * influenced by the page
	 * <a href=http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel>
	 * here </a>
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(screen, 0, 0, null);
	}

	/**
	 * shift the center of the view-port by mouse click
	 * @param newX
	 * @param newY
	 */
	private void relocate(int newX, int newY) {
		Main.log(ComplexImage.class.getSimpleName() + ".relocate() entered");

		/* the image is 600 x 600 with the complex point (0,0) at its center,
		 * so we subtract 1/2 of 600 from the coordinates supplied by the
		 * mouse click to get an offset that obeys the signs of our quadrants*/
		int xOffset = ComplexSet.COLS/2;
		int yOffset = ComplexSet.ROWS/2;
		xValue = xValue + newX - xOffset;
		yValue = yValue + newY - yOffset;

		theSet = new ComplexSet(zoom, zoomStep, xValue, yValue);
		history.push(theSet);
		fillImage();
		repaint();
	}
	/**
	 * zooms the image when the zoom button is pushed
	 */
	public void zoomImage() {
		Main.log(ComplexImage.class.getSimpleName() + ".zoomImage() entered");

		// make zoom larger by zoomStep
		zoom += zoomStep;
		// then make zoomStep larger by zoomFactor
		zoomStep = zoomStep * zoomFactor;
		// but must keep the x,y coordinates in the center, so they are also scaled
		xValue *= zoomFactor;
		yValue *= zoomFactor;

		theSet = new ComplexSet(zoom, zoomStep, xValue, yValue);
		history.push(theSet);
		fillImage();
		repaint();
	}

	/**
	 * saves the currently displayed image to the file system as a bitmap file
	 */
	public void saveImage() {
		Main.log(ComplexImage.class.getSimpleName() + ".saveImage() entered");

		File saveFile = FileManipulation.save();
		if (saveFile != null) {
			try {
				ImageIO.write(screen, "BMP", saveFile);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error writing to file!");
			}
		}
	}

	/**
	 * discards the current image and retrieves the last ComplexSet from the
	 * Stack
	 */
	public void previous() {
		Main.log(ComplexImage.class.getSimpleName() + ".previous() entered");

		if (!history.isEmpty()) {
			ComplexSet temp = defaultSet;
			try {
				temp = history.pop();
			} catch (HistoryStackException e) {
				e.printStackTrace();
			}
			if (history.isEmpty()) { //popped the last set
				theSet = temp;
				history.push(theSet);
			}
			else theSet = history.peek();

			zoom = theSet.getZoom();
			zoomStep = theSet.getzoomStep();
			xValue = theSet.getxOffset();
			yValue = theSet.getyOffset();
			
			fillImage();
			repaint();
		}
	}

	/**
	 * empties the Stack and retrieves the oldest ComplexSet into the current
	 * ComplexSet
	 */
	public void reset() {
		Main.log(ComplexImage.class.getSimpleName() + ".reset() entered");

		while (!history.isEmpty()) {
			try {
				theSet = history.pop();
			} catch (HistoryStackException e) {
				e.printStackTrace();
			}
		}
		history.push(theSet); // now the original set

		zoom = theSet.getZoom();
		xValue = theSet.getxOffset();
		yValue = theSet.getyOffset();
		zoomStep = 100;

		fillImage();
		repaint();
	}

	/**
	 * when the mouse is over the image, send the complex value of the pixel
	 * under the cursor to the parent container
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		double minReal, maxReal, minImag, maxImag, xIncrement, yInrementc, xVal, yVal;
		int xPos, yPos;

		minReal = theSet.getMinReal();
		maxReal = theSet.getMaxReal();
		minImag = theSet.getMinImaginary();
		maxImag = theSet.getMaxImaginary();

		xPos = e.getX();
		yPos = e.getY();

		xIncrement = (maxReal - minReal)/((double)ComplexSet.COLS);
		yInrementc = (maxImag - minImag)/((double)ComplexSet.ROWS);

		xVal = minReal + ((double)xPos * xIncrement);
		yVal = maxImag - ((double)yPos * yInrementc);

		if ( ( (xPos > HORIZ_OFFSET) && (yPos > VERT_OFFSET) ) && 
				( (xPos < ComplexSet.COLS + HORIZ_OFFSET) && (yPos < ComplexSet.ROWS + VERT_OFFSET) ) )
			parent.updatePosition(xVal,yVal);
	}

	/**
	 * when there's a mouse click on the image, zoom in at that point
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		relocate(e.getX(), e.getY());
	}

	//not used
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
}
