package exceptions;

public class FileDoesNotExistException extends ExistanceException {
	public FileDoesNotExistException(String message) {
		super(message);
	}
}
