package commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

public class ParserTest {
	private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
	}

	@Test
	public void testGetCommandFromInput() {
		String input = "cat firstFile secondFile";
		String command = parser.getCommand(input);

		assertEquals("cat", command);
	}

	@Test
	public void testGetDirectoriesFromInput() {
		String input = "cd /dir1/dir2/dir3";
		String[] directories = parser.getArgumentsSplittedBySlash(input);
		String[] expectedResult = new String[3];
		expectedResult[0] = "dir1";
		expectedResult[1] = "dir2";
		expectedResult[2] = "dir3";

		Arrays.equals(expectedResult, directories);
	}

	@Test
	public void getCommandArgumentsFromInput() {
		String input = "cat file1 file2";
		List<String> arguments = parser.getCommandArguments(input);
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("file");
		expectedResult.add("file2");

		expectedResult.equals(arguments);
	}
}
