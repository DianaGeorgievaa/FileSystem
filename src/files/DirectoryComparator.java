package files;

import java.util.Comparator;

public class DirectoryComparator implements Comparator<Directory> {

	@Override
	public int compare(Directory firsDirectory, Directory secondDirectory) {
		return Long.compare(firsDirectory.getDirectorySize(), secondDirectory.getDirectorySize());
	}
}
