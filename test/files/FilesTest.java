package files;

import org.junit.Test;

import exceptions.ExistanceException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;

import static org.junit.Assert.*;

import org.junit.Before;

public class FilesTest {

	private static final String FILE_NAME = "firstFile";
	private static final String UNEXISTING_FILE_NAME = "notexisting";

	private Files availableFiles;

	@Before
	public void setUp() {
		availableFiles = new Files();
	}

	@Test
	public void testAddNewFile() throws FileAlreadyExistsException {
		availableFiles.add(FILE_NAME);
		boolean isExisting = availableFiles.isExistingFile(FILE_NAME);
		assertTrue(isExisting);
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void testAddAlreadyExistingFileThrowsException() throws FileAlreadyExistsException {
		availableFiles.add(FILE_NAME);
		availableFiles.add(FILE_NAME);
	}

	@Test
	public void testWriteContentToExistingFile() throws ExistanceException, InvalidArgumentException {
		availableFiles.add(FILE_NAME);
		availableFiles.writeToFile(FILE_NAME, true, "text", 7);
		assertEquals(12, availableFiles.getSpecificFileByName(FILE_NAME).getCalculatedSize());
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testWriteToNotExistingFileThrowsException() throws FileDoesNotExistException, InvalidArgumentException {
		availableFiles.writeToFile(UNEXISTING_FILE_NAME, true, "text", 7);
	}

	@Test(expected = FileDoesNotExistException.class)
	public void readContentOfNotExistingFileThrowsException() throws FileDoesNotExistException, InvalidArgumentException {
		availableFiles.writeToFile(UNEXISTING_FILE_NAME, true, "text", 7);
	}

	@Test
	public void testGetWordsCountInExistingFile() throws ExistanceException, InvalidArgumentException {
		availableFiles.add(FILE_NAME);
		availableFiles.writeToFile(FILE_NAME, true, "two words", 5);
		long counter = this.availableFiles.getWordsCount(FILE_NAME);
		assertEquals(2, counter);
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testGetWordsCountInNotExistingFileThrowsException() throws FileDoesNotExistException {
		availableFiles.getWordsCount(UNEXISTING_FILE_NAME);
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testGetLinesCountInNotExistingFilesThrowsException() throws FileDoesNotExistException {
		availableFiles.getSpecificFileByName(UNEXISTING_FILE_NAME).countLines();
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testRemoveNotExistingFilesThrowsException() throws FileDoesNotExistException {
		availableFiles.remove(UNEXISTING_FILE_NAME);
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testRemoveExistingFile() throws ExistanceException {
		availableFiles.add(FILE_NAME);
		availableFiles.remove(FILE_NAME);
		availableFiles.getSpecificFileByName(FILE_NAME);
	}

	@Test(expected = FileDoesNotExistException.class)
	public void testRemoveFileContentFromNotExistingFile() throws ExistanceException, InvalidArgumentException {
		this.availableFiles.deleteLines(UNEXISTING_FILE_NAME, 2, 5);
	}

	@Test
	public void testRemoveFileContentFromExistingFile() throws ExistanceException, InvalidArgumentException {
		availableFiles.add(FILE_NAME);
		availableFiles.writeToFile(FILE_NAME, true, "text", 1);
		availableFiles.writeToFile(FILE_NAME, true, "other", 2);
		availableFiles.writeToFile(FILE_NAME, true, "another", 4);
		availableFiles.deleteLines(FILE_NAME, 2, 4);

		String content = this.availableFiles.readFileContent(FILE_NAME);
		StringBuilder expected = new StringBuilder();
		expected.append("\n");
		expected.append("text");
		expected.append("\n");

		assertEquals(new String(expected), content);
	}
}
