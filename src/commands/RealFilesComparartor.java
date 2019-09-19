package commands;

import java.io.File;
import java.util.Comparator;

import files.Directory;

public class RealFilesComparartor implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		return Long.compare(o1.length(), o2.length());
	}
}
