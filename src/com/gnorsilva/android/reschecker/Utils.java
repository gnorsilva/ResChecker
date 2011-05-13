package com.gnorsilva.android.reschecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class Utils {
	
	/**
	 * 
	 * Taken from http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	 * 
	 * @param The file to be read
	 * @return The contents of a file as a string
	 * @throws IOException
	 */
	public static String readFile(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			/* TODO Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

}
