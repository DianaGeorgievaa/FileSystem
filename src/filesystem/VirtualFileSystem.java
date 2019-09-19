package filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import commands.RealFilesComparartor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import exceptions.DirectoryAlreadyExistsException;
import exceptions.DirectoryDoesNotExistException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;
import files.Directory;

public class VirtualFileSystem extends FileSystem {

	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private String path;

	public VirtualFileSystem() {
		path = getCurrentDirectoryPath();
	}

	@Override
	public void makeDirectory(List<String> arguments) throws DirectoryAlreadyExistsException, IOException {
		String path = arguments.get(0);
		File fileToAdd;
		if (path.contains("\\")) {
			fileToAdd = new File(path);
		} else {
			fileToAdd = new File(this.path + "\\" + path);
		}
		if (!fileToAdd.mkdir()) {
			throw new IOException("The file can not be created!");
		}
	}

	@Override
	public void changeDirectory(List<String> arguments) throws DirectoryDoesNotExistException {
		path = arguments.get(0);
	}

	@Override
	public void addFile(List<String> arguments) throws FileAlreadyExistsException, IOException {
		File fileToAdd;
		if (arguments.get(0).contains("\\")) {
			fileToAdd = new File(arguments.get(0));
		} else {
			fileToAdd = new File(this.path + "\\" + arguments.get(0));
		}
		if (!fileToAdd.createNewFile()) {
			throw new IOException("The file can not be created!");
		}
	}

	@Override
	public String readFileContent(List<String> arguments) throws FileDoesNotExistException, IOException {
		Charset encoding;
		File fileDir;
		if (arguments.size() == 2) {
			encoding = Charset.forName(arguments.get(1));
		}else {
			encoding = StandardCharsets.UTF_8;
		}
		if (arguments.get(0).contains("\\")) {
			fileDir = new File(arguments.get(0));
		} else {
			fileDir =  new File(this.path + "\\" + arguments.get(0));
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), encoding));
		StringBuilder builder = new StringBuilder();
		String str;
		   
        while ((str = reader.readLine()) != null) {
        	if(str.equals("")) {
        		builder.append("\n");
        		continue;
        	}
            builder.append(str);
        }
		return new String(builder);
	}

	@Override
	public void writeFileContent(List<String> arguments)
			throws FileDoesNotExistException, InvalidArgumentException, IOException {
		String filePath;
		if (arguments.get(0).contains("\\")) {
			filePath = arguments.get(0);
		} else {
			filePath = this.path + "\\" + arguments.get(0);
		}
		BufferedWriter writer = getEncoding(arguments, filePath);
		File file = new File(arguments.get(0));
		for (int i = 0; i < Integer.parseInt(arguments.get(3)); i++) {
			if (file.length() == 0) {
				writer.newLine();
			} else {
				continue;
			}
		}
		if (Boolean.parseBoolean(arguments.get(1)) == true) {
			writer.append(arguments.get(2));
		} else {
			writer.write(arguments.get(2));
		}
		writer.close();
	}

	@Override
	public String listFiles() {
		File currentDirectory = new File(path);
		File[] listOfFiles = currentDirectory.listFiles();
		StringBuilder names = new StringBuilder();

		for (int i = 0; i < listOfFiles.length; i++) {
			names.append(listOfFiles[i]);
			names.append("\n");
		}
		return new String(names);
	}

	@Override
	public String listFilesSorted() {
		File currentDirectory = new File(path);
		File[] listOfFiles = currentDirectory.listFiles();
		StringBuilder names = new StringBuilder();
		Arrays.sort(listOfFiles, new RealFilesComparartor());

		for (int i = 0; i < listOfFiles.length; i++) {
			names.append(listOfFiles[i]);
			names.append("\n");
		}
		return new String(names);
	}

	@Override
	public String countWords(String name) throws FileDoesNotExistException, IOException {
		FileInputStream stream;
		if (name.contains("\\")) {
			stream = new FileInputStream(name);
		} else {
			stream = new FileInputStream(this.path + "\\" + name);
		}
		try (Scanner scanner = new Scanner(stream)) {
			int numberOfWords = 0;
			while (scanner.hasNext()) {
				scanner.next();
				numberOfWords++;
			}
			return Long.toString(numberOfWords);
		}
	}

	@Override
	public String countLines(String name) throws FileDoesNotExistException, IOException {
		long numberOfLines = 0;
		FileReader fileReader;
		if (name.contains("\\")) {
			fileReader = new FileReader(name);
		} else {
			fileReader = new FileReader(this.path + "\\" + name);
		}
		BufferedReader reader = new BufferedReader(fileReader);
		while (reader.readLine() != null) {
			numberOfLines += 1;
		}
		reader.close();
		return Long.toString(numberOfLines);
	}

	@Override
	public void removeFile(String name) throws FileDoesNotExistException, IOException {
		File file;
		if (name.contains("\\")) {
			file = new File(name);
		} else {
			file = new File(this.path + "\\" + name);
		}
		try {
			file.delete();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void removeContent(List<String> arguments)
			throws FileDoesNotExistException, InvalidArgumentException, IOException {
		String fileName;
		if (arguments.get(0).contains("\\")) {
			fileName = arguments.get(0);
		} else {
			fileName = this.path + "\\" + arguments.get(0);
		}
		for (int i = Integer.parseInt(arguments.get(1)); i < Integer.parseInt(arguments.get(2)); i++) {
			removeSpecificLine(fileName, i);
		}
	}

	@Override
	public String getCurrentDirectoryPath() {
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		return currentPath;
	}

	private BufferedWriter getEncoding(List<String> arguments, String filePath)
			throws UnsupportedEncodingException, FileNotFoundException {
		if (arguments.size() == 5) {
			return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), arguments.get(4)));
		}
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), DEFAULT_ENCODING));
	}

	private void removeSpecificLine(String fileName, int line) throws IOException {
		File inputFile = new File(fileName);
		File tempFile = new File("C:\\Users\\user\\Desktop\\temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;
		int count = 0;

		while ((currentLine = reader.readLine()) != null) {
			count++;
			if (count == line) {
				continue;
			}
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		inputFile.delete();
		tempFile.renameTo(inputFile);
	}
}
