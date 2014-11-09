package mandelbrot;

import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * Displays a text label with the coordinates of the mouse with respect to the
 * displayed portion of the complex plane
 */
@SuppressWarnings("serial")
public class PositionText extends JPanel {
	private JLabel theLabel;
	public PositionText() {
		super();
		setBounds(100, 650, 400, 50); // positions and sizes this control
		
		theLabel = new JLabel();
		theLabel.setText("0 + 0i");
		theLabel.setFont(new Font("Monospaced",Font.PLAIN,18));
		
		add(theLabel);
	}

	/**
	 * @param r the real part of the number
	 * @param i the imaginary part of the number
	 */
	public void setText(double r, double i) {
		// truncate received numbers and ...
		DecimalFormat realFormat = new DecimalFormat("0.000E000");
		DecimalFormat imgFormat = new DecimalFormat(" + 0.000E000; - 0.000E000");
		// ... display as strings
		String real = realFormat.format(r);
		String img = imgFormat.format(i);
		theLabel.setText(real + img);
	}
	
}
