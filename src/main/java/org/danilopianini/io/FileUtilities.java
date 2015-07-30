/*******************************************************************************
 * Copyright (C) 2009, 2015, Danilo Pianini and contributors
 * listed in the project's build.gradle or pom.xml file.
 *
 * This file is distributed under the terms of the Apache License, version 2.0
 *******************************************************************************/
package org.danilopianini.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.CRC32;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Charsets;

/**
 * This is a collection of tools for manipulating the file system.
 * 
 * @author Danilo Pianini
 * @version 20111021
 * 
 */
public final class FileUtilities {
	
	private static final String BACKUP_EXTENSION = ".bak";

	/**
	 * This method provides a shortcut to deep clone Serializable objects.
	 * 
	 * @param x
	 *            the object to clone
	 * @return a deep clone of x
	 * @param <T>
	 *            the Object type
	 * @throws IOException
	 *             If your memory fails
	 * @throws ClassNotFoundException
	 *             it never should
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneObject(final T x) throws IOException, ClassNotFoundException {
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		final ObjectOutputStream cout = new ObjectOutputStream(bout);
		cout.writeObject(x);
		final ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		final ObjectInputStream cin = new ObjectInputStream(bin);
		return (T) cin.readObject();
	}

	/**
	 * Serializes an object in memory. Its representation as byte[] is returned
	 * 
	 * @param b
	 *            the byte array representation of object to serialize
	 * @return The Object
	 * @param <T>
	 *            the Object type
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserializeObject(final byte[] b) {
		try {
			final ByteArrayInputStream bin = new ByteArrayInputStream(b);
			final ObjectInputStream cin = new ObjectInputStream(bin);
			return (T) cin.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace(); // NOPMD by danysk on 8/20/13 1:48 PM
		}
		return null;
	}

	/**
	 * Computes the CRC32 sum for a given file.
	 * 
	 * @param f
	 *            the file
	 * @return the CRC32
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static long fileCRC32sum(final File f) throws IOException {
		try (final InputStream is = new FileInputStream(f)) {
			final CRC32 crc = new CRC32();
			int val;
			do {
				val = is.read();
				crc.update(val);
			} while (val != -1);
			is.close();
			return crc.getValue();
		}
	}

	/**
	 * This method tries to resemble an Object from a file.
	 * 
	 * @param f
	 *            the file to load
	 * @return the loaded Object (already cast to Serializable)
	 * @throws IOException
	 *             in case of I/O errors
	 * @throws ClassNotFoundException
	 *             in case there is no compatible Class in Classpath for the
	 *             Object to be loaded
	 */
	public static Serializable fileToObject(final File f) throws IOException, ClassNotFoundException {
		if (f.exists()) {
			try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				final Serializable res = (Serializable) ois.readObject();
				ois.close();
				return res;
			}
		}
		return null;
	}

	/**
	 * This method tries to resemble an Object from a file.
	 * 
	 * @param path
	 *            the path of the file to load
	 * @return the loaded Object (already cast to Serializable)
	 * @throws IOException
	 *             in case of I/O errors
	 * @throws ClassNotFoundException
	 *             in case there is no compatible Class in Classpath for the
	 *             Object to be loaded
	 */
	public static Object fileToObject(final String path) throws IOException, ClassNotFoundException {
		return fileToObject(new File(path));
	}

	/**
	 * This method tries to read a file and to instance it as a String.
	 * 
	 * @param f
	 *            the file to read
	 * @return the file as String
	 * @throws IOException
	 *             in case of I/O errors
	 * @deprecated Developers should use
	 *             {@link FileUtils#readFileToString(File, String)} from Apache
	 *             Commons IO
	 */
	@Deprecated
	public static String fileToString(final File f) throws IOException {
		return FileUtils.readFileToString(f, Charsets.UTF_8.name());
	}

	/**
	 * This method tries to read a file and to instance it as a String.
	 * 
	 * @param path
	 *            the path of the file to read
	 * @return the file as String
	 * @throws IOException
	 *             in case of I/O errors
	 * @deprecated Use {@link FileUtilities#fileUTF8ToString(String)} instead, or specify a {@link Charset}.
	 */
	@Deprecated
	public static String fileToString(final String path) throws IOException {
		return fileToString(new File(path));
	}

	/**
	 * This method tries to read a file and to instance it as a String.
	 * 
	 * @param path
	 *            the path of the file to read
	 * @return the file as String
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static String fileUTF8ToString(final String path) throws IOException {
		return fileToString(path, Charsets.UTF_8);
	}

	/**
	 * This method tries to read a file and to instance it as a String.
	 * 
	 * @param path
	 *            the path of the file to read
	 * @param c
	 *            the {@link Charset} to use
	 * @return the file as String
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static String fileToString(final String path, final Charset c) throws IOException {
		return FileUtils.readFileToString(new File(path), c.name());
	}

	/**
	 * Tests whether or not any parent of the given file is hidden.
	 * 
	 * @param file
	 *            the file to test
	 * @return true if at least one of the parent directories is hidden
	 */
	public static boolean isInHiddenPath(final File file) {
		boolean hidden = false;
		File f = file;
		do {
			hidden = f.isHidden();
			f = f.getParentFile();
		} while (!(hidden || f == null));
		return hidden;
	}

	/**
	 * This method loads an Image from the file system.
	 * 
	 * @param path
	 *            the path of the file to read
	 * @return the loaded image
	 */
	public static Image loadImage(final String path) {
		final ClassLoader loader = ClassLoader.getSystemClassLoader();
		final URL fileLocation = loader.getResource(path);
		final Image img = Toolkit.getDefaultToolkit().getImage(fileLocation);
		if (img != null) {
			return img;
		}
		return Toolkit.getDefaultToolkit().getImage(fileLocation);
	}

	/**
	 * This method serializes an Object into a File.
	 * 
	 * @param s
	 *            the Object to serialize
	 * @param f
	 *            the File which will contain the serialized Object
	 * @param backup
	 *            If true and the file already exists, the ".bak" extension will
	 *            be added to the existing file, preventing overwriting
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static void objectToFile(final Object s, final File f, final boolean backup) throws IOException {
		final String op = f.getAbsolutePath();
		if (backup && f.exists()) {
			if (!f.renameTo(new File(op + BACKUP_EXTENSION))) {
				throw new IOException("Can not move " + op + " to " + op + BACKUP_EXTENSION);
			}
		}
		final File ef = new File(op);
		try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ef))) {
			oos.writeObject(s);
			oos.close();
		}
	}

	/**
	 * This method serializes an Object into a File.
	 * 
	 * @param s
	 *            the Object to serialize
	 * @param path
	 *            the path of the File which will contain the serialized Object
	 * @param backup
	 *            If true and the file already exists, the ".bak" extension will
	 *            be added to the existing file, preventing overwriting
	 * @throws IOException
	 *             in case of I/O errors
	 */
	public static void objectToFile(final Serializable s, final String path, final boolean backup) throws IOException {
		objectToFile(s, new File(path), backup);
	}

	/**
	 * Serializes an object in memory. Its representation as byte[] is returned
	 * 
	 * @param x
	 *            the object to serialize
	 * @return its byte array representation
	 * @param <T>
	 *            the Object type
	 */
	public static <T extends Serializable> byte[] serializeObject(final T x) {
		try {
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			final ObjectOutputStream cout = new ObjectOutputStream(bout);
			cout.writeObject(x);
			return bout.toByteArray();
		} catch (IOException e) {
			e.printStackTrace(); // NOPMD by danysk on 8/20/13 1:48 PM
		}
		return new byte[0];
	}

	/**
	 * This method takes a String and save it as a Text file.
	 * 
	 * @param s
	 *            the String to save
	 * @param f
	 *            the File to write to
	 * @param append
	 *            decides whether to append or overwrite the file
	 * @throws IOException
	 *             in case of I/O errors
	 * @deprecated use {@link FileUtils#writeStringToFile(File, String, Charset)} instead
	 */
	@Deprecated
	public static void stringToFile(final String s, final File f, final boolean append) throws IOException {
		final String op = f.getAbsolutePath();
		if (f.exists()) {
			if (!f.renameTo(new File(op + BACKUP_EXTENSION))) {
				throw new IOException("Can not move " + op + " to " + op + BACKUP_EXTENSION);
			}
		}
		FileUtils.writeStringToFile(f, s);
	}

	/**
	 * This method takes a String and save it as a Text file. This method
	 * overwrites.
	 * 
	 * @param string
	 *            the String to save
	 * @param path
	 *            the path of the File to write to
	 * @throws IOException
	 *             in case of I/O errors
	 * @deprecated use {@link FileUtils#writeStringToFile(File, String, Charset)} instead
	 */
	public static void stringToFile(final String string, final String path) throws IOException {
		stringToFile(string, path, false);
	}

	/**
	 * /** This method takes a String and save it as a Text file.
	 * 
	 * @param string
	 *            the String to save
	 * @param path
	 *            the File to write to
	 * @param append
	 *            decides whether to append or overwrite the file
	 * @throws IOException
	 *             in case of I/O errors
	 * @deprecated use {@link FileUtils#writeStringToFile(File, String, Charset, boolean)} instead
	 */
	public static void stringToFile(final String string, final String path, final boolean append) throws IOException {
		stringToFile(string, new File(path), append);
	}

	private FileUtilities() {
	}

}
