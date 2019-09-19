package files;

import java.util.Comparator;

public class FileComparator implements Comparator<File> {

	@Override
	public int compare(File firsFile, File secondFile) {
		return Long.compare(firsFile.getCalculatedSize(), secondFile.getCalculatedSize());
	}
}
