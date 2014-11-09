package mandelbrot;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 *
 * Instances of this class represent a complex number. Is instantiated with its
 * real and imaginary parts.
 * 
 * A ComplexValue determines its inclusion in the Mandelbrot set. If a number
 * is in the set, it has with it an associated order, which is the number of
 * iterations of the operation must be performed to reach the threshold. A
 * ComplexValue has a color, which it determines from its order.
 */
public class ComplexValue {
	/**
	 * how many times we will run the operation
	 */
	public static final int MAX_ITERATIONS = 200;
	/**
	 * if ITERATIONS times through the operation we are below this THRESHOLD in
	 * both the real and imaginary parts, then this complex value is in
	 * the Mandelbrot set
	 */
	//	public static final double THRESHOLD = 2.0D;
	public static final double THRESHOLD = Math.sqrt(5.0d);
	/**
	 * the real portion of this number
	 */
	private double real;
	/**
	 * the imaginary portion of this number
	 */
	private double imaginary;
	/**
	 * the color that this number has as an RGB int
	 */
	private int color;
	/**
	 * true if this number is in the Mandelbrot set
	 */
	private boolean isInSet;
	/**
	 * the number of times the operation is performed before this number reaches
	 * the threshold for inclusion
	 */
	private int order;


	public ComplexValue(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
		order = 0;
		isInSet = true;

		computeOrder();
		color = setColor(order);
	}

	/**
	 * @return the real portion of this ComplexValue
	 */
	public double getReal() {
		return real;
	}
	/**
	 * @return the imaginary portion of this ComplexValue
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * @return the color associated with this ComplexValue
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return the order of this ComplexValue, which is determined by the
	 * number of iterations of the operation that must be performed to reach
	 * the threshold
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @return true if the number this ComplexValue represents is in the
	 * Mandelbrot set
	 */
	public boolean isInMandelbrotSet() {
		return isInSet;
	}

	/**
	 * the algorithm that determines inclusion in the Mandelbrot set:  
	 * <p>for a complex number c = real + imaginary, set a complex number
	 * z to initially be 0 + 0i. Successively redefine z to be
	 * <p>z = z^2 + c
	 * <p>If |zReal| is bounded above, c is in the Mandelbrot set. Otherwise,
	 * the number of times that z has been redefined is the order of c, by
	 * which an associated color can be determined.
	 * 
	 * <p>This method also sets order
	 */
	private void computeOrder() {
		double zReal = 0d, zImaginary = 0d, cReal, cImaginary, temp;
		int count = 0;
		cReal = real;
		cImaginary = imaginary;

		while (Math.sqrt(sqr(zReal) + sqr(zImaginary)) < THRESHOLD 
				&& count < MAX_ITERATIONS ) {
			temp = sqr(zReal) - sqr(zImaginary) + cReal;
			zImaginary = 2 * zReal * zImaginary + cImaginary;
			zReal = temp;
			count++;
		}
		order = count;
		if (order < MAX_ITERATIONS) isInSet = true;
	}

	/**
	 * Produce a color for each complex value
	 * @param count
	 * @return the RGB color
	 */
	private int setColor(int count) {
		int temp = count;
		int exponent = 0;
		
		while (temp > 0) {
			temp = temp / 2;
			exponent++;
		}
		
		/*
		 * we base color selection on the exponent, base 2, of the count
		 * 
		 * count is always greater than zero, so its exponent, by the way we
		 * calculate exponent, is always greater than zero. By the exponent, we
		 * OR the count with a color, chosen across the spectrum, and then
		 * shift the value to the left by a number of bits equal to thrice the
		 * exponent
		 * 
		 * This is different from the traditional ways to color this kind of
		 * graphic
		 */
		switch (exponent) {
		case 1: color = (count | 0x000080) << 3 * exponent;
		case 2:color = (count | 0x0000FF) << 3 * exponent;
		case 3:color = (count | 0x008000) << 3 * exponent;
		case 4:color = (count | 0x008080) << 3 * exponent;
		case 5:color = (count | 0x0080FF) << 3 * exponent;
		case 6:color = (count | 0x00FF00) << 3 * exponent;
		case 7:color = (count | 0x00FF80) << 3 * exponent;
		case 8:color = (count | 0x00FFFF) << 3 * exponent;
		case 9:color = (count | 0xFF0000) << 3 * exponent;
		}
		
		return color;
	}

	/**
	 * square a double
	 * @param theNum a double to be squared
	 * @return the number squared
	 */
	private double sqr(double theNum){
		return theNum * theNum;
	}

}
