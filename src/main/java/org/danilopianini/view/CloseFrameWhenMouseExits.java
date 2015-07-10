/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * @author Danilo Pianini
 *
 */
public class CloseFrameWhenMouseExits implements MouseListener {

	private final JFrame frame;
	
	/**
	 * Builds a new listener, which, when triggered, will close the passed frame
	 * if the mouse is outside its boundaries. Note that the constructor DOES
	 * NOT call addMouseListener on the frame. If you want the frame itself to
	 * generate the events, you must call that method manually.
	 * 
	 * @param f
	 *            the frame to close
	 */
	public CloseFrameWhenMouseExits(final JFrame f) {
		frame = f;
	}

	@Override
	public void mouseClicked(final MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(final MouseEvent arg0) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		final int x = e.getX();
		final int y = e.getY();
		if (x <= 0 || y <= 0 || x >= frame.getWidth() || y >= frame.getHeight()) {
			frame.setVisible(false);
		}
	}

	@Override
	public void mousePressed(final MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(final MouseEvent arg0) {
	}

}
