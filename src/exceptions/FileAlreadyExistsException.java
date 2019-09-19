package exceptions;

public class FileAlreadyExistsException extends ExistanceException {
	public FileAlreadyExistsException(String message) {
		super(message);
	}
}
