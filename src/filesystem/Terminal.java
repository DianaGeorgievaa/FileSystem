package filesystem;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import commands.Command;
import commands.CommandFactory;
import commands.Parser;
import exceptions.CommandDoesNotExistException;
import exceptions.ExistanceException;
import exceptions.InvalidArgumentException;
import exceptions.NotEnoughMemoryException;

public class Terminal {

	private Parser parser;

	public Terminal() {
		parser = new Parser();
	}

	public void run() throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException {
		String choice = getChoiceForFileSystem();
		if (choice.equals("-v")) {
			runVirtualFileSystem();
		} else if (choice.equals("-r")) {
			runRealFileSystem();
		} else {
			System.out.println("Invalid choice!");
		}
	}

	private void runRealFileSystem()
			throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException {
		Command.setFileSystem(new RealFileSystem());
		executeCommands();
	}

	private void runVirtualFileSystem()
			throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException {
		Command.setFileSystem(new VirtualFileSystem());
		executeCommands();
	}

	private String getChoiceForFileSystem() {
		System.out.println("Choose -v for Virtual filesystem or -r for Real fileSystem: ");
		Scanner inputScanner = new Scanner(System.in);
		String choice = inputScanner.next();
		return choice;
	}

	private void executeCommands() throws IOException, CommandDoesNotExistException {
		CommandFactory commandFactory = new CommandFactory();
		Scanner inputScanner = new Scanner(System.in);
		while (inputScanner.hasNextLine()) {
			String input = inputScanner.nextLine();
			String commandName = parser.getCommand(input);
			Command currentCommand = commandFactory.getCommand(commandName);
			List<String> currentCommandArguments = currentCommand.getArguments(input);
			try {
				String result = currentCommand.executeCommand(currentCommandArguments);
				System.out.println(result);
			} catch (ExistanceException e) {
				System.out.println(e.getMessage());
			} catch (NotEnoughMemoryException e) {
				System.out.println(e.getMessage());
			} catch (InvalidArgumentException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		inputScanner.close();
	}
}
