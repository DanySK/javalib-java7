/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.view;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * @author Danilo Pianini
 * 
 */
public class FloatInputVerifier extends InputVerifier implements Serializable {

	private static final long serialVersionUID = 6709850468286128438L;
	private final JTextField textField;
	
	private boolean status;

	/**
	 * Builds a new FloatInputVerifier.
	 * 
	 * @param field the text field to verify
	 */
	public FloatInputVerifier(final JTextField field) {
		super();
		textField = field;
		status = verify(textField);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 */
	@Override
	public final boolean verify(final JComponent input) {
		if (input.equals(textField)) {
			try {
				Float.parseFloat(textField.getText());
				textField.setForeground(Color.BLACK);
				status = true;
			} catch (NumberFormatException e) {
				textField.setForeground(Color.RED);
				status = false;
			}
		}
		return status;
	}
	
	/**
	 * @return true if the textfield is compliant with the verifier
	 */
	public boolean isOK() {
		return status;
	}

}
