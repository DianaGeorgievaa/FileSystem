package exceptions;

public class DirectoryDoesNotExistException extends ExistanceException {
	public DirectoryDoesNotExistException(String message) {
		super(message);
	}
}