package mandelbrot;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * 
 * <p>
 * This program allows a user to view a graphical representation of a portion
 * of the complex plane such that those numbers which are members of the
 * Mandelbrot set are visually differentiated from those that are not.
 * 
 * <p>
 * This module creates the main window of the application.
 * 
 * <p>
 * The greatest portion of this window is taken up by a 600 x 600 pixel bitmap
 * that displays a view of a range of complex numbers. The initial range of the
 * view is (-2,0.5) along the real axis and (-1.25i, 1.25i) along the imaginary
 * axis.
 * 
 * <p>
 * The window also has five buttons (the control area) and the text
 * control (the information area). The buttons provide the functionality of
 * <OL>
 * <LI>reset (start from scratch),
 * <LI>previous (display previous view),
 * <LI>save (save a bitmap of the view),
 * <LI>exit (exit the program), and
 * <LI>zoom (zoom into the image).
 * </OL>
 * 
 * <p>
 * The text control displays the location of the mouse pointer with respect to
 * the 600 x 600 pixel grid.
 * 
 * <p>
 * The center of the portion of the plane that is in view is changed by clicking
 * with the mouse at a new center, at which point a new image is drawn.
 */
@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {
	/**
	 * a JPanel to hold the buttons
	 */
	private ButtonPanel theButtons;
	/**
	 * a JPanel to hold the label
	 */
	private PositionText theText;
	/**
	 * a JPanel in charge of displaying the image
	 */
	private ComplexImage theComplexImage;

	/**
	 * Initializes private member variables. Sets up window, buttons and text
	 * display.
	 */
	public Main() {
		super("Mandelbrot Set Fractal Image Viewer");
		
		setLayout(null);
		setSize(new Dimension(610, 725));
		setLocationRelativeTo(null);
		
		theText = new PositionText();
		add(theText);
		
		theButtons = new ButtonPanel(this);
		add(theButtons);
        
		theComplexImage = new ComplexImage(this);
		addMouseMotionListener(theComplexImage);
		add(theComplexImage);
		
		validate();
	}

	/**
	 * Create an instance of this class.
	 * 
	 * @param args Command line arguments not supported.
	 */
	public static void main(String[] args) {
		new Main().setVisible(true);
	}

	/**
	 * Listens for events from theButtons
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionString = ((JButton) e.getSource()).getActionCommand();

		if (actionString == "exit")
			exit();
		else if (actionString == "reset")
			reset();
		else if (actionString == "save")
			saveImage();
		else if (actionString == "previous")
			previous();
		else if (actionString == "zoom")
			zoom();
	}

	/**
	 * zoom the displayed image
	 */
	private void zoom() {
		theComplexImage.zoomImage();
		
	}

	/**
	 * exit the program
	 */
	private void exit() {
		Main.log("Exit button pressed");
		
		System.exit(0);
	}

	/**
	 * save the currently displayed bitmap to the file system
	 */
	private void saveImage() { 
		Main.log("Save button pressed");
		
		theComplexImage.saveImage();
	}

	/**
	 * return to the state the program was in on startup
	 */
	private void reset() {
		Main.log("Reset button pressed");
		
		theComplexImage.reset();
	}

	/**
	 * discard the current image and return to the one previously shown
	 */
	private void previous() {
		Main.log("Previous button pressed");
		
		theComplexImage.previous();
	}	

	/**
	 * update the text label to show the position of the mouse
	 * @param x the position of the mouse on the real line
	 * @param y the position of the mouse on the complex line
	 */
	public void updatePosition(double x, double y) {
		theText.setText(x, y);
	}
	
	/**
	 * very basic logging facility; loosely based on info found 
	 * <a href=http://hanoo.org/index.php?article=how-to-generate-logs-in-java>
	 * here</a>
	 * @param mesg prints to console
	 */
	public static void log(String mesg) {
		logger.setLevel(Level.INFO);

		if (LOGGING) {
			logger.log(Level.INFO, mesg);
		}
	}
	private final static Logger logger = Logger.getLogger(Main.class.getName());
	private final static boolean LOGGING = false;
	
}
