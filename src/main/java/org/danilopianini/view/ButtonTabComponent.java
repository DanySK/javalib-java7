/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */

package org.danilopianini.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Component to be used as tabComponent; Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to.
 * 
 * @author Oracle
 * @author Danilo Pianini
 * 
 */
public class ButtonTabComponent extends JPanel {

	private static final long serialVersionUID = -1213910239175658021L;
	private static final byte BORDER_SIZE = 5;
	private static final MouseListener BUTTON_MOUSE_LISTENER = new MouseAdapter() {
		public void mouseEntered(final MouseEvent e) {
			final Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				final AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(final MouseEvent e) {
			final Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				final AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
	
	private final JTabbedPane pane;

	/**
	 * @param tabbedPane
	 *            the {@link JTabbedPane}
	 * @param closeListener
	 *            a component that will react to the close operation. If none
	 *            required, pass null.
	 */
	public ButtonTabComponent(final JTabbedPane tabbedPane, final ActionListener closeListener) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (tabbedPane == null) {
			throw new IllegalArgumentException("TabbedPane is null");
		}
		this.pane = tabbedPane;
		setOpaque(false);

		// make JLabel read titles from JTabbedPane
		final JLabel label = new JLabel() {
			private static final long serialVersionUID = 1L;

			public String getText() {
				int i = tabbedPane.indexOfTabComponent(ButtonTabComponent.this);
				if (i != -1) {
					return tabbedPane.getTitleAt(i);
				}
				return null;
			}
		};

		add(label);
		// add more space between the label and the button
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, BORDER_SIZE));
		// tab button
		final JButton button = new TabButton();
		add(button);
		if (closeListener != null) {
			button.addActionListener(closeListener);
		}
		// add more space to the top of the component
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	private class TabButton extends JButton implements ActionListener {
		private static final long serialVersionUID = -2443563189596390441L;
		private static final byte SIZE = 18, DELTA = 5, STROKE = 3;

		public TabButton() {
			super();
			setPreferredSize(new Dimension(SIZE, SIZE));
			setToolTipText("Close this tab");
			setActionCommand("CloseTab");
			// Make the button looks the same for all Laf's
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			// Making nice rollover effect
			// we use the same listener for all buttons
			addMouseListener(BUTTON_MOUSE_LISTENER);
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
			addActionListener(this);
		}

		public void actionPerformed(final ActionEvent e) {
			final int i = pane.indexOfTabComponent(ButtonTabComponent.this);
			if (i != -1) {
				pane.remove(i);
			}
		}

		public void updateUI() {
			// we don't want to update UI for this button
		}

		// paint the cross
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			final Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}
			g2.setStroke(new BasicStroke(STROKE));
			g2.drawLine(DELTA, DELTA, getWidth() - DELTA - 1, getHeight() - DELTA - 1);
			g2.drawLine(getWidth() - DELTA - 1, DELTA, DELTA, getHeight() - DELTA - 1);
			g2.dispose();
		}
	}

}
