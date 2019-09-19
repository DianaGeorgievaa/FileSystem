package commands;

import java.util.HashMap;
import java.util.Map;
import exceptions.CommandDoesNotExistException;

public class CommandFactory {
	static final Map<String, Command> COMMANDS = new HashMap<>() {
		{
			put("cat", new CatCommand(Command.getFileSystem()));
			put("cd", new CdCommand(Command.getFileSystem()));
			put("create_file", new CreateFileCommand(Command.getFileSystem()));
			put("mkdir", new MkdirCommand(Command.getFileSystem()));
			put("write", new WriteCommand(Command.getFileSystem()));
			put("ls", new LsCommand(Command.getFileSystem()));
			put("wc", new WcCommand(Command.getFileSystem()));
			put("rm", new RmCommand(Command.getFileSystem()));
			put("remove", new RemoveCommand(Command.getFileSystem()));
			put("pwd", new PwdCommand(Command.getFileSystem()));
			put("|", new PipeCommand(Command.getFileSystem()));
		}
	};

	/**
	 * If the command exists, returns it
	 *
	 * @param command
	 */
	public Command getCommand(String command) throws CommandDoesNotExistException {
		if (!COMMANDS.containsKey(command)) {
			throw new CommandDoesNotExistException("Command does not exist!");
		}
		return COMMANDS.get(command);
	}
}
