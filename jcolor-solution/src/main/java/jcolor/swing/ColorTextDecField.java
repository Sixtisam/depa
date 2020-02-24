package jcolor.swing;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jcolor.ColorChannel;
import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorTextDecField extends JTextField implements DocumentListener, FocusListener, ColorListener {
	private ColorModel model;
	private ColorChannel channel;

	ColorTextDecField(ColorModel model, ColorChannel channel) {
		super("", 5);
		this.model = model;
		this.channel = channel;
		getDocument().addDocumentListener(this);
		addFocusListener(this);
		model.addColorListener(this);
	}

	// DocumentListener implementation

	@Override
	public void insertUpdate(DocumentEvent e) {
		textChangeNotification();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChangeNotification();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	private void textChangeNotification() {
		try {
			int value = Integer.parseInt(getText());

			if (value >= 0 && value < 256) {
				model.setColor(channel.modifiedColor(model.getColor(), value));
			}
		} catch (NumberFormatException e) {
			// do nothing, i.e. keep old color value
			// but characters remain in the text field
			// model.setColor(c) does not help as color does not change
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	// FocusListener implementation

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		try {
			ColorTextDecField.super.setText("" + Integer.parseInt(getText()));
		} catch (Exception ex) {
			super.setText("" + channel.getValue(model.getColor()));
		}
	}

	// ColorListener implementation

	@Override
	public void colorValueChanged(Color color) {
		setText("" + channel.getValue(color));
	}

	/**
	 * necessary due to IllegalStateException. If setText is called during a
	 * mutation, then an IllegalStateException is thrown.
	 */
	@Override
	public void setText(String text) {
		if (!text.equals(getText())) {
			super.setText(text);
		}
	}

//	@Override
//	public void setText(String text) {
//		// assert text is always a number as it is only called from
//		// colorValueChanged
//		// actually, setText is called during initialization processes with an
//		// empty argument list
//		if (channel == ColorChannel.RED)
//			System.out.println(">>setText(" + getText() + "=> " + text + ")");
//		if (!text.equals(getText())) {
//			int oldValue = parse(getText());
//			int newValue = parse(text);
//			if (oldValue != newValue || "".equals(getText())) {
//				super.setText(text);
//			}
//		}
//		if (channel == ColorChannel.RED)
//			System.out.println("<<setText(" + text + ")");
//	}

//	private int parse(String text) {
//		try {
//			return Integer.parseInt(text, 10);
//		} catch (Exception e) {
//			return 0;
//		}
//	}

}
