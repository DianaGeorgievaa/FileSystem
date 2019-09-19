package files;

import java.util.Map;
import java.util.Queue;

import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Files {
	private static final int MEMORY = 200;
	private Map<String, File> availableFiles;
	private Queue<File> removedFiles;

	public Files() {
		availableFiles = new HashMap<String, File>();
		removedFiles = new LinkedList<File>();
	}

	public Queue<File> getRemovedFiles() {
		return removedFiles;
	}

	public Map<String, File> getAvailableFiles() {
		return availableFiles;
	}

	/**
	 * Creates new file.
	 *
	 * @param name is the name of file
	 * 
	 * @exception FileAlreadyExistsException if file with the given name exists
	 */
	public void add(String name) throws FileAlreadyExistsException {
		checkIfFileExists(name);
		File file = new File(name);
		availableFiles.put(name, file);
	}

	/**
	 * Writes content to specific file.
	 *
	 * @param name        is the name of file
	 * @param toOverwrite
	 * @param text        is the content that will be added
	 * @param line
	 * @exception FileDoesNotExistException if file with the given name does not
	 *                                      exist.
	 * @see File.write(Boolean,int,String)
	 */
	public void writeToFile(String name, boolean toOverwrite, String text, int line)
			throws FileDoesNotExistException, InvalidArgumentException {
		checkIfFileDoesNotExist(name);
		availableFiles.get(name).write(toOverwrite, line, text);
	}

	/**
	 * Returns the content of file by given name.
	 *
	 * @param name is the name of file
	 * @exception FileDoesNotExistException if file with the given name does not
	 *                                      exist.
	 * @see File.getContent()
	 */
	public String readFileContent(String name) throws FileDoesNotExistException {
		checkIfFileDoesNotExist(name);
		return availableFiles.get(name).getContent();
	}

	/**
	 * Returns the the names of the files.
	 */
	public String getFileNames() {
		StringBuilder fileNames = new StringBuilder();
		for (String currentFile : availableFiles.keySet()) {
			fileNames.append(currentFile);
			fileNames.append("\n");
		}
		return new String(fileNames);
	}

	/**
	 * Returns the the names of the files, sorted by size(ascending order)
	 */
	public String getFileNamesSortedByFileSize() {
		StringBuilder sortedFileNames = new StringBuilder();
		for (File f : getSortedFiles()) {
			sortedFileNames.append(f.getName());
			sortedFileNames.append("\n");
		}
		return new String(sortedFileNames);
	}

	/**
	 * Returns the number of words in specific file
	 * 
	 * @param name
	 * @exception FileDoesNotExistException if file with the given name does not
	 *                                      exist
	 * @see File.countWords()
	 */
	public long getWordsCount(String name) throws FileDoesNotExistException {
		this.checkIfFileDoesNotExist(name);
		return availableFiles.get(name).countWords();
	}

	/**
	 * Returns the number of lines in specific file
	 * 
	 * @param name
	 * @exception FileDoesNotExistException if file with the given name does not
	 *                                      exist
	 * @see File.counLines()
	 */
	public long getLinesCount(String name) throws FileDoesNotExistException {
		this.checkIfFileDoesNotExist(name);
		return availableFiles.get(name).countLines();
	}

	public boolean isExistingFile(String name) {
		return availableFiles.containsKey(name);
	}

	public File getSpecificFileByName(String name) throws FileDoesNotExistException {
		if (availableFiles.containsKey(name)) {
			return availableFiles.get(name);
		}
		throw new FileDoesNotExistException("File does not exist!");
	}

	/**
	 * Removes file by name
	 * 
	 * @param name
	 * @exception FileDoesNotExistException if file with the given name does not
	 *                                      exist
	 */
	public void remove(String name) throws FileDoesNotExistException {
		if (!availableFiles.containsKey(name)) {
			throw new FileDoesNotExistException("File does not exist!");
		}
		removedFiles.add(availableFiles.get(name));
		availableFiles.remove(name);
	}

	public void deleteRemovedFile() {
		removedFiles.remove();
	}

	/**
	 * Deletes lines from specific file
	 * 
	 * @param name
	 * @param firstLineNumber
	 * @param secondLineNumber
	 * 
	 * @see File.removeLines()
	 */
	public void deleteLines(String name, int fisrtLineNumber, int secondLineNumber)
			throws FileDoesNotExistException, InvalidArgumentException {
		checkIfFileDoesNotExist(name);
		availableFiles.get(name).removeLines(fisrtLineNumber, secondLineNumber);
	}

	/**
	 * Returns the size of specific file after writing some content to it.
	 * 
	 * @param name
	 * @param toOverwrite
	 * @param text
	 * @param line
	 * 
	 * @see File.getSizeAfterWriting()
	 */
	public long getSizeAfterWritingToFile(String name, boolean toOverwrite, String text, int line)
			throws FileDoesNotExistException, InvalidArgumentException {
		checkIfFileDoesNotExist(name);
		return availableFiles.get(name).getSizeAfterWriting(toOverwrite, text, line);
	}

	/**
	 * Returns the number of used memory
	 */

	public int getUsedMemory() {
		int usedMemory = getUsedMemoryOfAvailableFiles() + getUsedMemoryOfRemovedFiles();
		return usedMemory;
	}

	public int getAvailableMemory() {
		return getMemory() - getUsedMemory();
	}

	public int getMemory() {
		return MEMORY;
	}

	private List<File> getSortedFiles() {
		List<File> files = new ArrayList<File>();
		for (Map.Entry<String, File> entry : availableFiles.entrySet()) {
			files.add(entry.getValue());
		}
		Collections.sort(files, new FileComparator());
		return files;
	}

	private void checkIfFileExists(String name) throws FileAlreadyExistsException {
		if (isExistingFile(name)) {
			throw new FileAlreadyExistsException("The file already exists!");
		}
	}

	private void checkIfFileDoesNotExist(String name) throws FileDoesNotExistException {
		if (!this.isExistingFile(name)) {
			throw new FileDoesNotExistException("The file does not exists!");
		}
	}

	private int getUsedMemoryOfAvailableFiles() {
		int usedMemory = 0;
		for (Map.Entry<String, File> entry : availableFiles.entrySet()) {
			File currentFile = entry.getValue();
			usedMemory += currentFile.getCalculatedSize();
		}
		return usedMemory;
	}

	private int getUsedMemoryOfRemovedFiles() {
		int usedMemory = 0;
		for (File file : removedFiles) {
			usedMemory += file.getCalculatedSize();
		}
		return usedMemory;
	}
}