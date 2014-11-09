package mandelbrot;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * 
 * Instances of this class represent the set of numbers that make up a portion
 * of the complex plane. Owns 360000 instances of ComplexValue.
 */
public class ComplexSet {
	/**
	 * size of the set
	 */
	public static final int ROWS = 600,COLS = 600;
	/**
	 * allows the graphic to initially appear in the middle of the paint area,
	 * i.e., zero not quite in the middle
	 */
	private static final int DEFAULT_X_OFFSET = 500, DEFUALT_Y_OFFSET = 280;
	/**
	 * a 2D array of ComplexValues that span the range of this set
	 */
	private ComplexValue[][] complexSet;
	/**
	 * the least imaginary value this set covers
	 */
	private double minImaginary;
	/**
	 * the greatest imaginary value this set covers
	 */
	private double maxImaginary;
	/**
	 * the least real value this set covers
	 */
	private double minReal;
	/**
	 * the greatest real value this set covers
	 */
	private double maxReal;
	/**
	 * our image is a port-view onto a part of the complex plane. Which
	 * part is in view is controlled by the zoom, xOffset and yOffset. We
	 * keep track of the zoomStep for the owner of this object
	 */
	private double zoom, zoomStep;
	private int xOffset, yOffset;


	public ComplexSet(double zoom, double zoomFactor, int xOffset, int yOffset) {
		super();
		this.zoom = zoom;
		this.zoomStep = zoomFactor;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		complexSet = new ComplexValue[ROWS][COLS];
		
		createSet();
	}

	/**
	 * @return the array of ComplexValues that this object holds
	 */
	public ComplexValue[][] getComplexSet() {
		Main.log(ComplexSet.class.getSimpleName() + ".getComplexSet() entered");

		return complexSet;
	}

	/**
	 * on instantiation, create the ComplexValue objects from the range of
	 * values this object holds
	 */
	private void createSet() {
		Main.log(ComplexSet.class.getSimpleName() + ".createSet() entered");
		
		double theReal, theImaginary;
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				theReal = ((double)(x - DEFAULT_X_OFFSET + xOffset)) / zoom;
				if (x == 0) minReal = theReal;
				else if (x == COLS - 1) maxReal = theReal;
				
				theImaginary = ((double)(y - DEFUALT_Y_OFFSET + yOffset)) / zoom;
				if (y == 0) minImaginary = theImaginary;
				else if (y == ROWS - 1) maxImaginary = theImaginary;
				
				complexSet[x][y] = new ComplexValue(theReal, theImaginary);
			}
		}
	}

	// setters and getters for fields
	/**
	 * @return the minImaginary
	 */
	public double getMinImaginary() {
		return minImaginary;
	}

	/**
	 * @return the maxImaginary
	 */
	public double getMaxImaginary() {
		return maxImaginary;
	}

	/**
	 * @return the minReal
	 */
	public double getMinReal() {
		return minReal;
	}

	/**
	 * @return the maxReal
	 */
	public double getMaxReal() {
		return maxReal;
	}

	/**
	 * @return the zoom
	 */
	public double getZoom() {
		return zoom;
	}

	/**
	 * @return the xOffset
	 */
	public int getxOffset() {
		return xOffset;
	}

	/**
	 * @return the yOffset
	 */
	public int getyOffset() {
		return yOffset;
	}

	/**
	 * @return the zoomStep
	 */
	public double getzoomStep() {
		return zoomStep;
	}
	
	
}
