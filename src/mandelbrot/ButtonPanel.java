package mandelbrot;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * 
 * A panel that holds the buttons for the application
 *
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {
	/**
	 * button to exit the program
	 */
	private JButton exit;
	/**
	 * button to reset view to that at program start
	 */
	private JButton reset;
	/**
	 * button to save the displayed image as a bitmap
	 */
	private JButton save;
	/**
	 * button to scrap the current image and return to the image shown just
	 * before
	 */
	private JButton previous;
	/**
	 * button to zoom the currently displayed view
	 */
	private JButton zoom;

	/**
	 * create buttons
	 */
	public ButtonPanel(Main parent) {
		super();
		setBounds(75, 620, 450, 50); // positions and sizes this control
		exit = new JButton("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(parent);
		add(exit);

		reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(parent);
		add(reset);

		save = new JButton("Save");
		save.setActionCommand("save");
		save.addActionListener(parent);
		add(save);

		previous = new JButton("Previous");
		previous.setActionCommand("previous");
		previous.addActionListener(parent);
		add(previous);
		
		zoom = new JButton("Zoom");
		zoom.setActionCommand("zoom");
		zoom.addActionListener(parent);
		add(zoom);
	}
}
