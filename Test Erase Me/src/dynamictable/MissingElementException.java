package dynamictable;

public class MissingElementException extends Exception {

	private static final long serialVersionUID = 1L;

	public MissingElementException() {
		
	}
	
	public MissingElementException(String message) {
		super(message);
		
	}
	
	public MissingElementException(Throwable cause) {
		super(cause);
		
	}

	public MissingElementException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
