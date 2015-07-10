/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.view;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A JTextField which comes along with a Label.
 * 
 * @author Danilo Pianini
 * @version 20111021
 * 
 */
public class LabeledTextField extends JPanel {

	private static final boolean DEFAULT_BUF = true;
	private static final int DEFAULT_COLS = 0;
	private static final String DEFAULT_LABEL = "";
	private static final String DEFAULT_TEXT = "";
	private static final long serialVersionUID = -3219032924040094801L;

	private final JLabel lab;

	private final JTextField textField;

	/**
	 * Builds a new LabeledTextField.
	 */
	public LabeledTextField() {
		this(new FlowLayout());
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 */
	public LabeledTextField(final boolean isDoubleBuffered) {
		this(new FlowLayout(), isDoubleBuffered);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final boolean isDoubleBuffered, final int columns) {
		this(new FlowLayout(), isDoubleBuffered, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 */
	public LabeledTextField(final boolean isDoubleBuffered, final String label) {
		this(new FlowLayout(), isDoubleBuffered, label);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final boolean isDoubleBuffered, final String label, final int columns) {
		this(new FlowLayout(), isDoubleBuffered, label, DEFAULT_TEXT, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 */
	public LabeledTextField(final boolean isDoubleBuffered, final String label, final String text) {
		this(new FlowLayout(), isDoubleBuffered, label, text);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final boolean isDoubleBuffered, final String label,
			final String text, final int columns) {
		this(new FlowLayout(), isDoubleBuffered, label, text, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final int columns) {
		this(new FlowLayout(), columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 */
	public LabeledTextField(final LayoutManager layout) {
		this(layout, DEFAULT_BUF);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 */
	public LabeledTextField(final LayoutManager layout, final boolean isDoubleBuffered) {
		this(layout, isDoubleBuffered, DEFAULT_LABEL);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final LayoutManager layout, final boolean isDoubleBuffered,
			final int columns) {
		this(layout, isDoubleBuffered, DEFAULT_LABEL, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 */
	public LabeledTextField(final LayoutManager layout, final boolean isDoubleBuffered,
			final String label) {
		this(layout, isDoubleBuffered, label, DEFAULT_TEXT);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final LayoutManager layout, final boolean isDoubleBuffered,
			final String label, final int columns) {
		this(layout, isDoubleBuffered, label, DEFAULT_TEXT, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 */
	public LabeledTextField(final LayoutManager layout, final boolean isDoubleBuffered,
			final String label, final String text) {
		this(layout, isDoubleBuffered, label, text, DEFAULT_COLS);
	}

	/**
	 * Builds a new LabeledTextField. Other constructors call this one with
	 * default parameters
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param isDoubleBuffered
	 *            a boolean specifying whether to use double buffering
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final LayoutManager layout, final boolean isDoubleBuffered,
			final String label, final String text, final int columns) {
		super(layout, isDoubleBuffered);
		this.lab = new JLabel(label);
		textField = new JTextField(text, columns);
		add(this.lab);
		add(textField);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final LayoutManager layout, final int columns) {
		this(layout, DEFAULT_BUF, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param label
	 *            The label text
	 */
	public LabeledTextField(final LayoutManager layout, final String label) {
		this(layout, DEFAULT_BUF, label);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param label
	 *            The label text
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final LayoutManager layout, final String label, final int columns) {
		this(layout, DEFAULT_BUF, label, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 */
	public LabeledTextField(final LayoutManager layout, final String label, final String text) {
		this(layout, DEFAULT_BUF, label, text);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param layout
	 *            The LayoutManager to use
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final LayoutManager layout, final String label, final String text,
			final int columns) {
		this(layout, DEFAULT_BUF, label, text, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param label
	 *            The label text
	 */
	public LabeledTextField(final String label) {
		this(new FlowLayout(), label);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param label
	 *            The label text
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final String label, final int columns) {
		this(new FlowLayout(), label, columns);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 */
	public LabeledTextField(final String label, final String text) {
		this(new FlowLayout(), label, text);
	}

	/**
	 * Builds a new LabeledTextField.
	 * 
	 * @param label
	 *            The label text
	 * @param text
	 *            The default text in TextField
	 * @param columns
	 *            number of columns for the TextField
	 */
	public LabeledTextField(final String label, final String text, final int columns) {
		this(new FlowLayout(), label, text, columns);
	}

	/**
	 * @return A reference to the internal JLabel
	 */
	public JLabel getLabel() {
		return lab;
	}

	/**
	 * @return A reference to the internal JTextField
	 */
	public JTextField getTextField() {
		return textField;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(final boolean b) {
		super.setEnabled(b);
		lab.setEnabled(b);
		textField.setEnabled(b);
	}
	
}
