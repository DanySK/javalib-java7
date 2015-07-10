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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.danilopianini.lang.CollectionWithCurrentElement;
import org.danilopianini.lang.RangedInteger;

/**
 * @author Danilo Pianini
 * 
 */
public class ObjectModFrame extends JFrame {

	private static final long serialVersionUID = 5816449414660853953L;

	private String actionCommand = "";
	private final AtomicInteger idgen = new AtomicInteger();
	private final List<ActionListener> listeners = Collections.synchronizedList(new ArrayList<ActionListener>());

	private class BooleanGUIComponent extends NoRefGUIComponent implements ActionListener {
		private static final long serialVersionUID = -7149307455972615932L;
		private final JCheckBox box;

		public BooleanGUIComponent(final String name, final Object obj, final Field f) throws IllegalAccessException {
			super(obj, f);
			box = new JCheckBox(name, getField().getBoolean(getObject()));
			add(box);
			box.addActionListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			update(box.isSelected());
		}
	}

	private class EnumGUIComponent extends NoRefGUIComponent implements ActionListener {
		private static final long serialVersionUID = 3329099673991014137L;
		private final JComboBox<?> box;

		public EnumGUIComponent(final String name, final Object obj, final Field f) throws IllegalAccessException {
			super(obj, f);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			box = new JComboBox<Object>(f.getType().getEnumConstants());
			box.setSelectedItem(f.get(obj));
			add(new JLabel(name));
			add(Box.createHorizontalStrut(10));
			add(box);
			box.addActionListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			update(box.getSelectedItem());
		}
	}

	private class CurrentCollectionGUIComponent extends NoRefGUIComponent implements ActionListener {
		private static final long serialVersionUID = 6031819767955928108L;
		private final JComboBox<?> box;
		private final CollectionWithCurrentElement<Object> col;

		@SuppressWarnings("unchecked")
		public CurrentCollectionGUIComponent(final String name, final Object obj, final Field f) throws IllegalAccessException {
			super(obj, f);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			col = (CollectionWithCurrentElement<Object>) f.get(obj);
			box = new JComboBox<Object>(col.toArray());
			box.setSelectedItem(col.getCurrent());
			add(new JLabel(name));
			add(Box.createHorizontalStrut(10));
			add(box);
			box.addActionListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			col.setCurrent(box.getSelectedItem());
		}
	}

	private abstract class NoRefGUIComponent extends JPanel {
		private static final long serialVersionUID = 7131340105783363250L;
		private final Field field;
		private final Object object;

		public NoRefGUIComponent(final Object obj, final Field f) {
			super();
			field = f;
			object = obj;
		}

		protected Field getField() {
			return field;
		}

		protected Object getObject() {
			return object;
		}

		protected void update(final Object fieldVal) {
			try {
				field.set(object, fieldVal);
				notifyListeners();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace(); // NOPMD by danysk on 1/23/15 6:40 PM
			}
		}
	}

	private class RangedIntegerGUIComponent extends JPanel implements ChangeListener {
		private static final long serialVersionUID = 4082696268464803253L;
		public static final int SLIDERS_MAJOR_TICKS = 10;
		private final RangedInteger n;
		private final JSlider slider = new JSlider();
		private final JLabel label = new JLabel();

		public RangedIntegerGUIComponent(final String name, final RangedInteger num) {
			super();
			n = num;
			add(new JLabel(name));
			add(slider);
			add(label);
			final int min = n.getMin();
			final int max = n.getMax();
			slider.setMaximum(max);
			slider.setMinimum(min);
			slider.setMinorTickSpacing(1);
			slider.setMajorTickSpacing((max - min) / SLIDERS_MAJOR_TICKS);
			slider.setValue(n.getVal());
			label.setText(Integer.toString(n.getVal()));
			slider.addChangeListener(this);
		}

		@Override
		public void stateChanged(final ChangeEvent e) {
			n.setVal(slider.getValue());
			label.setText(Integer.toString(n.getVal()));
			pack();
			notifyListeners();
		}
	}

	private class StringGUIComponent extends NoRefGUIComponent implements DocumentListener {
		private static final long serialVersionUID = -1557933366572097377L;
		private final JTextField text;

		public StringGUIComponent(final String name, final Object obj, final Field f) throws IllegalAccessException {
			super(obj, f);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(new JLabel(name));
			add(Box.createHorizontalStrut(10));
			text = new JTextField(f.get(obj).toString());
			add(text);
			text.getDocument().addDocumentListener(this);
		}

		@Override
		public void changedUpdate(final DocumentEvent e) {
			update();
		}

		@Override
		public void insertUpdate(final DocumentEvent e) {
			update();
		}

		@Override
		public void removeUpdate(final DocumentEvent e) {
			update();
		}

		private void update() {
			update(text.getText());
		}

	}

	private static boolean assignable(final Class<?> clazz, final Field field) throws IllegalAccessException {
		return clazz.isAssignableFrom(field.getType());
	}

	/**
	 * @param obj
	 *            the object to inspect
	 * @throws IllegalAccessException
	 *             if the fields supposed to be managed by the graphical
	 *             interface are not accessible
	 */
	public ObjectModFrame(final Object obj) throws IllegalAccessException {
		super();
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		final JPanel contents = new JPanel();
		contents.setBorder(new LineBorder(new Color(0, 0, 0)));
		addMouseListener(new CloseFrameWhenMouseExits(this));
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		for (final Field f : getAllFields(new ArrayList<Field>(), obj.getClass())) {
			if (f.isAnnotationPresent(ExportForGUI.class)) {
				final String expName = f.getAnnotation(ExportForGUI.class).nameToExport();
				final boolean accessible = f.isAccessible();
				if (!accessible) {
					f.setAccessible(true);
				}
				if (assignable(RangedInteger.class, f)) {
					contents.add(new RangedIntegerGUIComponent(expName, (RangedInteger) f.get(obj)));
				} else if (Boolean.class.equals(f.getType()) || f.getType().equals(Boolean.TYPE)) {
					contents.add(new BooleanGUIComponent(expName, obj, f));
				} else if (String.class.equals(f.getType())) {
					contents.add(new StringGUIComponent(expName, obj, f));
				} else if (f.getType().isEnum()) {
					contents.add(new EnumGUIComponent(expName, obj, f));
				} else if (CollectionWithCurrentElement.class.isAssignableFrom(f.getType())) {
					contents.add(new CurrentCollectionGUIComponent(expName, obj, f));
				} else {
					f.setAccessible(accessible);
				}
			}
		}
		getContentPane().add(contents);
		pack();
	}

	/**
	 * Adds an <code>ActionListener</code> to the frame's components.
	 * 
	 * @param l
	 *            the <code>ActionListener</code> to be added
	 */
	public void addActionListener(final ActionListener l) {
		listeners.add(l);
	}

	/**
	 * Returns the action command for this frame's components.
	 * 
	 * @return the action command for this frame's components
	 */
	public String getActionCommand() {
		return actionCommand;
	}

	private void notifyListeners() {
		final ActionEvent e = new ActionEvent(this, idgen.getAndIncrement(), actionCommand);
		for (final ActionListener a : listeners) {
			a.actionPerformed(e);
		}
	}

	/**
	 * Sets the action command for this frame's components.
	 * 
	 * @param ac
	 *            the action command for this frame's components
	 */
	public void setActionCommand(final String ac) {
		this.actionCommand = ac;
	}

	private static List<Field> getAllFields(final List<Field> fields, final Class<?> type) {
	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }
	    for (final Field field: type.getDeclaredFields()) {
	        fields.add(field);
	    }
	    return fields;
	}

	
}
