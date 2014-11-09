package mandelbrot;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * a FILO buffer that holds the history of ComplexSets created
 */
public class HistoryStack implements Stack<ComplexSet> {
	/**
	 * holds the data for the Node entered to the Stack most recently
	 */
	private Node head;
	
	public HistoryStack() {
		head = null;
	}

	@Override
	public void push(ComplexSet item) {
		Main.log(ComplexImage.class.getSimpleName() + ".push() entered");
		Node tmpNode = new Node(item);
		tmpNode.setNext(head);
		head = tmpNode;
	}

	@Override
	public ComplexSet pop() throws HistoryStackException {
		Main.log(ComplexImage.class.getSimpleName() + ".pop() entered");
		if (isEmpty()) throw new HistoryStackException("Stack is empty");
		Node tmpNode = head;
		head = head.getNext();
		tmpNode.setNext(null);
		
		return tmpNode.getData();
	}

	@Override
	public ComplexSet peek() {
		Main.log(ComplexImage.class.getSimpleName() + ".peek() entered");
		return head.getData();
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * @author John Bannister <john_bannister@my.uri.edu>
	 * represents Nodes in the stack which hold, in this case, ComplexSets
	 */
	private class Node {
		/**
		 * the ComplexSet this Node holds
		 */
		private ComplexSet data;
		/**
		 * the Node entered to the Stack just previous to this one
		 */
		private Node next;

		public Node(ComplexSet theData) {
			data = theData;
		}
		
		/**
		 * @return the ComplexSet this Node holds
		 */
		public ComplexSet getData() {
			return data;
		}
		
		/**
		 * set the Node after this one in the Stack
		 * @param nextNode the Node that follows this one
		 */
		public void setNext(Node nextNode) {
			next = nextNode;
		}
		
		/**
		 * get the Node after this one in the Stack
		 * @return the Node that follows this one
		 */
		public Node getNext() {
			return next;
		}
	}
	
	@SuppressWarnings("serial")
	public class HistoryStackException extends Exception {
		HistoryStackException(String mesg){
			super(mesg);
		}
	}
}
