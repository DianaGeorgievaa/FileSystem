package files;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import exceptions.DirectoryAlreadyExistsException;
import exceptions.DirectoryDoesNotExistException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;

public class Directory {

	private String name;
	private Directory parent;
	private Map<String, Directory> subdirectories;
	private Files files;

	public Directory() {
		name = "/";
		parent = this;
		files = new Files();
		subdirectories = new HashMap<String, Directory>();
	}

	public Directory(String name) {
		this(name, null);
	}

	public Directory(String name, Directory parent) {
		this.name = name;
		this.parent = parent;
		parent.subdirectories.put(name, this);
		files = new Files();
		subdirectories = new HashMap<String, Directory>();
	}

	public Files getFiles() {
		return files;
	}

	public Directory getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	/**
	 * Creates new directory with the given name.
	 *
	 * @param name
	 * 
	 * @exception DirectoryAlreadyExistsException if exists directory with the same
	 *                                            name.
	 */
	public void makeDirectory(String name) throws DirectoryAlreadyExistsException {
		this.checkIfDirectorExists(name);
		Directory directory = new Directory(name, this);
		subdirectories.put(name, directory);
	}

	/**
	 * Returns sub directory by given name.
	 *
	 * @param name
	 * 
	 * @exception DirectoryDoesNotExistException if directory with the given name
	 *                                           does not exist.
	 */
	public Directory getSubDirectoryIfExists(String name) throws DirectoryDoesNotExistException {
		checkIfDirectoryDoesNotExists(name);
		return subdirectories.get(name);
	}

	/**
	 * Returns the number of words in file.
	 *
	 * @param name is the name of file
	 * 
	 * @see Fileñ.getWordsCount(String)
	 */
	public long getNumberOfWords(String name) throws FileDoesNotExistException {
		return files.getWordsCount(name);
	}

	/**
	 * Returns the number of lines in file.
	 *
	 * @param name is the name of file
	 * 
	 * @see Files.getLinesCount(String)
	 */
	public long getNumberOfLines(String name) throws FileDoesNotExistException {
		return files.getLinesCount(name);
	}

	/**
	 * Deletes lines from file.
	 *
	 * @param name             is the name of file
	 * @param firstLineNumber
	 * @param secondLineNumber
	 * 
	 * @see Files.deleteLines(String,int,int)
	 */
	public void deleteLinesFromFileContent(String name, int fisrtLineNumber, int secondLineNumber)
			throws FileDoesNotExistException, InvalidArgumentException {
		files.deleteLines(name, fisrtLineNumber, secondLineNumber);
	}

	public boolean isExistingDirectory(String name) {
		if (subdirectories.containsKey(name)) {
			return true;
		}
		return false;
	}

	public Directory getPreviousDirectory() {
		return this.getParent();
	}

	/**
	 * Creates new file.
	 *
	 * @param name is the name of file
	 * 
	 * @see Files.add(String)
	 */
	public void addFile(String name) throws FileAlreadyExistsException {
		files.add(name);
	}

	/**
	 * Writes to file.
	 *
	 * @param name        is the name of file
	 * @param toOverwrite
	 * @param text        is the content that will be added
	 * @param line
	 * 
	 * @see Files.writeToFile(String)
	 */
	public void writeToFile(String name, boolean toOverwrite, String text, int line)
			throws FileDoesNotExistException, InvalidArgumentException {
		files.writeToFile(name, toOverwrite, text, line);
	}

	/**
	 * Returns the names of the files and the directories in the current directory.
	 */
	public String getFileNamesInDirectory() {
		StringBuilder directoryContent = new StringBuilder();
		directoryContent.append(getSubdirectories());
		directoryContent.append(files.getFileNames());
		return new String(directoryContent);
	}

	/**
	 * Returns the names of the files and the directories in the current
	 * directory,sorted by size (ascending).
	 */
	public String getFileNamesInDirectoryBySize() {
		StringBuilder directoryContent = new StringBuilder();
		directoryContent.append(getDirectoryNamesSortedBySize());
		directoryContent.append(files.getFileNamesSortedByFileSize());
		return new String(directoryContent);
	}

	/**
	 * Returns the content of each given file.
	 * 
	 * @arguments contains file names
	 * 
	 * @see Files.readFileContent
	 */
	public String concatenateFiles(List<String> arguments) throws FileDoesNotExistException {
		StringBuilder concatenationOfFiles = new StringBuilder();
		for (int i = 0; i < arguments.size(); i++) {
			concatenationOfFiles.append(this.files.readFileContent(arguments.get(i)));
		}
		return new String(concatenationOfFiles);
	}

	/**
	 * Deletes file with given name.
	 * 
	 * @param name
	 * 
	 * @see Files.remove
	 */
	public void removeFile(String name) throws FileDoesNotExistException {
		files.remove(name);
	}

	public int getDirectorySize() {
		return files.getUsedMemory();
	}

	private void checkIfDirectorExists(String name) throws DirectoryAlreadyExistsException {
		if (isExistingDirectory(name)) {
			throw new DirectoryAlreadyExistsException("The directory already exists!");
		}
	}

	private void checkIfDirectoryDoesNotExists(String name) throws DirectoryDoesNotExistException {
		if (!isExistingDirectory(name)) {
			throw new DirectoryDoesNotExistException("The directory does not exists!");
		}
	}

	private String getSubdirectories() {
		StringBuilder directories = new StringBuilder();
		for (String currentFile : subdirectories.keySet()) {
			directories.append(currentFile);
			directories.append("\n");
		}
		return new String(directories);
	}

	private List<Directory> getSortedDirectories() {
		List<Directory> directories = new ArrayList<Directory>();
		for (Map.Entry<String, Directory> entry : subdirectories.entrySet()) {
			directories.add(entry.getValue());
		}
		Collections.sort(directories, new DirectoryComparator());
		return directories;
	}

	private String getDirectoryNamesSortedBySize() {
		StringBuilder sortedDirectories = new StringBuilder();
		for (Directory d : getSortedDirectories()) {
			sortedDirectories.append(d.getName());
			sortedDirectories.append("\n");
		}
		return new String(sortedDirectories);
	}
}