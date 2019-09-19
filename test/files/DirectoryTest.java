package files;

import org.junit.Test;
import static org.junit.Assert.*;
import exceptions.ExistanceException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;
import exceptions.DirectoryAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

public class DirectoryTest {

	private static final String DIRECTORY_NAME = "dir";
	private static final String FILE_NAME = "file";
	private static final String NOT_EXISTING_FILE_NAME = "notexistingfile";

	private Directory directory;

	@Before
	public void setUp() {
		directory = new Directory();
	}

	@Test
	public void testCreateNewDirectory() throws ExistanceException {
		directory.makeDirectory(DIRECTORY_NAME);

		assertTrue(directory.isExistingDirectory(DIRECTORY_NAME));
	}

	@Test(expected = DirectoryAlreadyExistsException.class)
	public void testCreateAlreadyExistingirectoryThrowsException() throws ExistanceException {
		directory.makeDirectory(DIRECTORY_NAME);
		directory.makeDirectory(DIRECTORY_NAME);
	}

	@Test
	public void testAddNewFileToCurrentDirectory() throws ExistanceException {
		directory.addFile(FILE_NAME);
		Files f = this.directory.getFiles();

		assertNotNull(f.getSpecificFileByName(FILE_NAME));
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void testAddAlreadyExistingFileToCurrentDirectoryThrowsException() throws ExistanceException {
		directory.addFile(FILE_NAME);
		directory.addFile(FILE_NAME);
	}

	@Test
	public void testWriteToExistingFileInCurSrentDirectory() throws ExistanceException, InvalidArgumentException {
		directory.addFile(FILE_NAME);
		directory.writeToFile(FILE_NAME, true, "some text", 6);
		Files currentFiles = directory.getFiles();

		assertEquals(16, currentFiles.getSpecificFileByName(FILE_NAME).getCalculatedSize());
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testWriteToNotExistingFileInCurSrentDirectoryThrowsException() throws ExistanceException, InvalidArgumentException {
		directory.writeToFile(NOT_EXISTING_FILE_NAME, true, "some text", 6);
	}

	@Test
	public void testGetSubOfCurrentDirectory() throws ExistanceException {
		this.directory.makeDirectory("subdir");
		Directory subDir = directory.getSubDirectoryIfExists("subdir");
		assertNotNull(subDir);
	}

	@Test
	public void testListFilesInCurrentDirectory() throws ExistanceException {
		directory.addFile(FILE_NAME);
		directory.addFile("otherfile");

		String expectedOutput = "otherfile" + "\n" + FILE_NAME + "\n";
		String actualResult = directory. getFileNamesInDirectory();

		assertEquals(expectedOutput, actualResult);
	}

	@Test
	public void testListSortedFilesInCurrentDirectory() throws ExistanceException, InvalidArgumentException {
		directory.addFile(FILE_NAME);
		directory.addFile("otherfile");
		directory.writeToFile(FILE_NAME, true, "text", 2);

		String expectedOutput = "otherfile" + "\n" + FILE_NAME + "\n";
		String actualResult = directory.getFileNamesInDirectoryBySize();

		assertEquals(expectedOutput, actualResult);

	}

	@Test
	public void testConcatenateTwoFiles() throws ExistanceException, InvalidArgumentException {
		directory.addFile(FILE_NAME);
		directory.addFile("otherfile");
		directory.writeToFile(FILE_NAME, true, "text", 0);
		directory.writeToFile("otherfile", true, "text", 0);

		List<String> files = new ArrayList<String>();
		files.add(FILE_NAME);
		files.add("otherfile");

		String expectedOutput = "text" + "\n" + "text" + "\n";
		String actualResult = this.directory.concatenateFiles(files);

		assertEquals(expectedOutput, actualResult);

	}
}
