/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

import org.danilopianini.lang.LangUtils;

/**
 * A collection of static utilities to deal with Swing GUIs.
 * 
 * @author Danilo Pianini
 */
public final class GUIUtilities {

	/**
	 * Displays a warning message.
	 * 
	 * @param title
	 *            The title of the window
	 * @param content
	 *            The text to display inside
	 */
	public static void alertMessage(final String title, final String content) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, content, title, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Turns the double buffering off. This normally reduces performances, but
	 * is useful for printing purposes
	 * 
	 * @param c
	 *            the component whose double buffering should be disabled
	 */
	public static void disableDoubleBuffering(final Component c) {
		final RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	/**
	 * Displays a Component in center of the screen.
	 * 
	 * @param f
	 *            the component to display
	 */
	public static void displayInCenterOfScreen(final Component f) {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f.setLocation((int) (screenSize.getWidth() - f.getWidth()) / 2, (int) (screenSize.getHeight() - f.getHeight()) / 2);
				f.setVisible(true);
			}
		});
	}

	/**
	 * Turns the double buffering on.
	 * 
	 * @param c
	 *            the component whose double buffering should be enabled
	 */
	public static void enableDoubleBuffering(final Component c) {
		final RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}

	/**
	 * Displays on screen an error containing an Exception.
	 * 
	 * @param e
	 *            the Throwable that generates the error event
	 */
	public static void errorMessage(final Throwable e) {
		errorMessage(e, true);
	}

	/**
	 * Displays on screen an error screen describing an Exception.
	 * 
	 * @param e
	 *            the Throwable that generates the error event
	 * @param stackTrace
	 *            choose wether to print or not the complete stacktrace
	 */
	public static void errorMessage(final Throwable e, final boolean stackTrace) {
		final String s = stackTrace ? LangUtils.stackTraceToString(e) : "";
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + s, "Error! Exception: " + e.toString(), JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	/**
	 * Loads a resized instance of an ImageIcon.
	 * 
	 * @param path
	 *            the path where to load the icon. This method uses the System
	 *            resource loader, and it's thus able to access resources
	 *            located in the whole classpath, jars included
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @return the scaled instance, or null if the resource is unavailable.
	 */
	public static ImageIcon loadScaledImage(final String path, final int w, final int h) {
		final URL resLoaded = GUIUtilities.class.getResource(path);
		if (resLoaded == null) {
			return null;
		}
		final ImageIcon loaded = new ImageIcon(resLoaded);
		final Image scaled = loaded.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}

	/**
	 * Packs a Window and display it in center of the screen.
	 * 
	 * @param f
	 *            the window to pack and display
	 */
	public static void packAndDisplayInCenterOfScreen(final Window f) {
		f.pack();
		displayInCenterOfScreen(f);
	}

	/**
	 * Given a component, saves it on disk as image.
	 * 
	 * @param path
	 *            the file to save
	 * @param target
	 *            the component to save as image
	 * @param format
	 *            a string describing the format. See
	 *            http://docs.oracle.com/javase
	 *            /7/docs/api/javax/imageio/package-
	 *            summary.html#package_description for a list of available
	 *            formats.
	 * @throws IOException
	 *             in case of I/O Errors
	 */
	public static void saveComponentAsImage(final String path, final Component target, final String format) throws IOException {
		final JFrame win = (JFrame) SwingUtilities.getWindowAncestor(target);
		final Dimension size = win.getSize();
		final BufferedImage image = (BufferedImage) win.createImage(size.width, size.height);
		final Graphics g = image.getGraphics();
		win.paint(g);
		g.dispose();
		ImageIO.write(image, format, new File(path));
	}

	/**
	 * Sets the icon for a window.
	 * 
	 * @param window
	 *            the target window
	 * @param imagepath
	 *            the image which should be icon
	 */
	public static void setDefaultIcon(final JFrame window, final String imagepath) {
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(imagepath));
	}

	/**
	 * Displays a warning message.
	 * 
	 * @param title
	 *            the title of the window
	 * @param content
	 *            the content of the warning
	 */
	public static void warningMessage(final String title, final String content) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, title, content, JOptionPane.WARNING_MESSAGE);
			}
		});
	}

	private GUIUtilities() {
	}

}
