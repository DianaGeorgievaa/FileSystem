package filesystem;

import filesystem.VirtualFileSystem;
import org.junit.*;
import org.junit.Test;

import exceptions.DirectoryAlreadyExistsException;
import exceptions.DirectoryDoesNotExistException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualFileSystemTest {

	private static final String DEFAULT_PATH = "C:\\Users\\user\\Desktop";
	
	private VirtualFileSystem fileSystem;
	private List<String> arguments;

	@Before
	public void setUp() throws DirectoryAlreadyExistsException, IOException, DirectoryDoesNotExistException,
			FileAlreadyExistsException, FileDoesNotExistException, InvalidArgumentException {
		fileSystem = new VirtualFileSystem();
		arguments = new ArrayList<>();

		arguments.add(DEFAULT_PATH);
		fileSystem.changeDirectory(arguments);
		arguments.remove(0);

		arguments.add(DEFAULT_PATH + "\\newDir");
		fileSystem.makeDirectory(arguments);
		fileSystem.changeDirectory(arguments);
		arguments.remove(0);

		arguments.add(DEFAULT_PATH + "\\newDir\\newFile");
		fileSystem.addFile(arguments);
		arguments.remove(0);
	}

	@After
	public void afterSetUp()
			throws DirectoryDoesNotExistException, FileDoesNotExistException, IOException, InvalidArgumentException {
		arguments = new ArrayList<>();
		arguments.add(DEFAULT_PATH);
		fileSystem.changeDirectory(arguments);
		arguments.remove(0);

		arguments.add(DEFAULT_PATH + "\\newDir");
		fileSystem.changeDirectory(arguments);
		arguments.remove(0);
		arguments.add(DEFAULT_PATH + "\\newDir\\newFile");
		fileSystem.removeFile(arguments.get(0));
		arguments.remove(0);

		arguments.add(DEFAULT_PATH + "\\newDir");
		fileSystem.changeDirectory(arguments);
		fileSystem.removeFile(arguments.get(0));
		arguments.remove(0);
	}

	@Test(expected = IOException.class)
	public void testMakeDirectoryThrowsExceptionWhenDirectoryAlreadyExists()
			throws DirectoryAlreadyExistsException, IOException {
		List<String> arguments = new ArrayList<>();
		arguments.add(DEFAULT_PATH + "\\newDir");
		fileSystem.makeDirectory(arguments);
	}

	@Test(expected = IOException.class)
	public void testAddFileThrowsExceptionWhenFileAlreadyExists() throws FileAlreadyExistsException, IOException {
		List<String> arguments = new ArrayList<>();
		arguments.add("newFile");
		fileSystem.addFile(arguments);
	}

	@Test
	public void testGetCurrentDirectoryPathAfterChangingDirectory() throws DirectoryDoesNotExistException {
		List<String> arguments = new ArrayList<>();
		arguments.add(DEFAULT_PATH);
		fileSystem.changeDirectory(arguments);

		assertEquals(DEFAULT_PATH, fileSystem.getCurrentDirectoryPath());
	}

	@Test
	public void testListFilesInCurrentDirectory() throws DirectoryDoesNotExistException {
		List<String> arguments = new ArrayList<>();
		arguments.add(DEFAULT_PATH + "\\newDir");
		fileSystem.changeDirectory(arguments);
		String resultOfLsCommand = fileSystem.listFiles();
		String expectedResult = DEFAULT_PATH + "\\newDir\\newFile" + "\n";

		assertEquals(expectedResult, resultOfLsCommand);
	}

	@Test
	public void tesCountWordsInFileContent()
			throws DirectoryDoesNotExistException, FileDoesNotExistException, IOException, InvalidArgumentException {
		writeToFile();
		String result = fileSystem.countWords(DEFAULT_PATH + "\\newDir\\newFile");
		
		assertEquals("1", result);
	}

	@Test
	public void tesCountLinesInFileContent()
			throws DirectoryDoesNotExistException, FileDoesNotExistException, IOException, InvalidArgumentException {
		writeToFile();
		String result = fileSystem.countWords(DEFAULT_PATH + "\\newDir\\newFile");
		
		assertEquals("1", result);
	}

	private void writeToFile() throws FileDoesNotExistException, InvalidArgumentException, IOException {
		List<String> arguments = new ArrayList<>();
		arguments.add(DEFAULT_PATH + "\\newDir\\newFile");
		arguments.add("true");
		arguments.add("text");
		arguments.add("1");
		fileSystem.writeFileContent(arguments);
	}
}
