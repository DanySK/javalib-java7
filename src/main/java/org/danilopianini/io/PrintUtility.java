/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.io;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.danilopianini.view.GUIUtilities;

/**
 * This class allows to print Components, managing the double buffering.
 * 
 * @author Danilo Pianini
 * @version 20111021
 * 
 */
public final class PrintUtility {

	private PrintUtility() {
	}

	private static final class PrintableComponent implements Printable {

		private final Component componentToBePrinted;

		public PrintableComponent(final Component component) {
			this.componentToBePrinted = component;
		}

		@Override
		public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) {
			if (pageIndex <= 0 && g instanceof Graphics2D) {
				final Graphics2D g2d = (Graphics2D) g;
				pageFormat.setOrientation(PageFormat.LANDSCAPE);
				g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				GUIUtilities.disableDoubleBuffering(componentToBePrinted);
				componentToBePrinted.paint(g2d);
				GUIUtilities.enableDoubleBuffering(componentToBePrinted);
				return PAGE_EXISTS;
			}
			return NO_SUCH_PAGE;
		}

	}

	/**
	 * Opens the print dialog to print with this PrintUtility.
	 * 
	 * @param c
	 *            The component to print
	 * @return true if the user does not cancel the dialog; false otherwise
	 * @throws PrinterException
	 *             an error in the print system caused the job to be aborted.
	 */
	public static boolean print(final Component c) throws PrinterException {
		final boolean printed = PrinterJob.getPrinterJob().printDialog();
		if (printed) {
			quickprint(c);
		}
		return printed;
	}

	/**
	 * Quickly prints the component.
	 * 
	 * @param c
	 *            The component to print
	 * @throws PrinterException
	 *             an error in the print system caused the job to be aborted.
	 */
	public static void quickprint(final Component c) throws PrinterException {
		final PrintableComponent p = new PrintableComponent(c);
		PrinterJob.getPrinterJob().setPrintable(p);
		PrinterJob.getPrinterJob().print();
	}

}
