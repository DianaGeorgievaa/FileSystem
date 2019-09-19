package filesystem;

import java.io.IOException;

import exceptions.ExistanceException;
import exceptions.InvalidArgumentException;
import exceptions.NotEnoughMemoryException;

public class Main {
	public static void main(String[] args) throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException {
		Terminal t = new Terminal();
		t.run();
	}
}
