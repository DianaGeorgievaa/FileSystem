package files;

import org.junit.Test;

import exceptions.InvalidArgumentException;

import static org.junit.Assert.*;

import org.junit.Before;

import files.File;

public class FileTest {

	private File file;

	@Before
	public void setUp() {
		file = new File();
	}

	@Test
	public void testWritingInEmtyFile() throws InvalidArgumentException {
		file.write(true, 4, "abcgf");
		assertEquals(10, file.getCalculatedSize());
	}

	@Test
	public void testdWritingWithAppend() throws InvalidArgumentException {
		file.write(true, 0, "abcgf");
		file.write(true, 0, "a");
		assertEquals(2, file.getCalculatedSize());
	}

	@Test
	public void testWritingWithoutAppend() throws InvalidArgumentException {
		file.write(true, 0, "xx");
		file.write(false, 0, "yy");
		assertEquals(5, file.getCalculatedSize());
	}

	@Test
	public void testCountWordsInEmptyContent() {
		long numberOfWords = file.countWords();
		assertEquals(1, numberOfWords);
	}

	@Test
	public void testCountWordsInFileWithContent() throws InvalidArgumentException {
		file.write(true, 1, "first second");
		file.write(true, 3, "third");
		file.write(false, 3, "a");
		long numberOfWords = file.countWords();
		assertEquals(3, numberOfWords);
	}

	@Test
	public void testCountLinesInFileWithContent() throws InvalidArgumentException {
		file.write(true, 7, "abcd");
		int numberOfLines = file.countLines();
		assertEquals(8, numberOfLines);
	}

	@Test
	public void testCountLinesInFileWithEmptyContent() {
		int numberOfLines = file.countLines();
		assertEquals(0, numberOfLines);
	}
}
