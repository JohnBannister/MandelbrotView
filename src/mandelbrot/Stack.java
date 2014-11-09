package mandelbrot;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * 
 * <p>
 * The interface for a last in, first out abstract data type.
 *
 */
public interface Stack<T> {
	/**
	 * save an item to the stack
	 * 
	 * @param item the item to be saved to the stack
	 */
	public void push(T item);
	
	/**
	 * remove the last saved item from the stack
	 * 
	 * @return the item last entered into the stack
	 */
	public T pop() throws Exception;
	
	/**
	 * receive a copy of the last saved item from the stack
	 * 
	 * @return the item last entered into the stack
	 */
	public T peek();
	
	/**
	 * @return true if stack has no items
	 */
	public boolean isEmpty();
	
}
