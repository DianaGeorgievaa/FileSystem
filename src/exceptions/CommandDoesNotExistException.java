package exceptions;

public class CommandDoesNotExistException extends ExistanceException {
	public CommandDoesNotExistException(String message) {
		super(message);
	}
}
