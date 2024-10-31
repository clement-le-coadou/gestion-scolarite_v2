package exceptions;

public class UniqueConstraintViolationException extends Exception {
	public UniqueConstraintViolationException(String msg) {
		super(msg);
	}
}
