package mandelbrot;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * Routine to save a bitmap file
 */
public final class FileManipulation {
	/**
	 * a file chooser
	 */
	private static JFileChooser chooseFile = new JFileChooser();

	public FileManipulation() {}
	
	/**
	 * Uses a file save dialog to return a File to save.
	 * @return a file handle to the file that will be saved
	 */
	public static File save() {
		File theSaveFile = null;
		// tip from
		// http://stackoverflow.com/questions/356671/jfilechooser-showsavedialog-how-to-set-suggested-file-name
		chooseFile.setSelectedFile(new File("mandelbrot.bmp"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP file", ".bmp");
		chooseFile.setFileFilter(filter);
		int returnedStatus = chooseFile.showSaveDialog(null);
		if (returnedStatus == JFileChooser.APPROVE_OPTION) {
			theSaveFile = chooseFile.getSelectedFile();
		}

		return theSaveFile;
	}

}
