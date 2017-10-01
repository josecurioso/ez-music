package gui;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;

import logger.CustomErrorOutputStream;
import logger.CustomInfoOutputStream;
import logger.Logger;

import java.awt.Toolkit;
import java.io.PrintStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextOutputWindow extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JButton btnClearOutput;
	private JCheckBox chckbxDebug;
	private JTextPane textPane;
	
	private Logger logger;

	/**
	 * Create the dialog.
	 */
	public TextOutputWindow(Logger logger) {	
		this.logger = logger;
		setTitle("ez-music [text output]");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TextOutputWindow.class.getResource("/gui/images/256x256.png")));
		setResizable(false);
		setBounds(100, 100, 553, 428);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(getScrollPane());
		getContentPane().add(getBtnClearOutput());
		getContentPane().add(getChckbxDebug());
		
		System.setOut(new PrintStream(new CustomInfoOutputStream(textPane)));
		System.setErr(new PrintStream(new CustomErrorOutputStream(textPane)));
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 527, 345);
			scrollPane.setViewportView(getTextPane());
		}
		return scrollPane;
	}
	private JButton getBtnClearOutput() {
		if (btnClearOutput == null) {
			btnClearOutput = new JButton("Clear output");
			btnClearOutput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearOutput();
				}
			});
			btnClearOutput.setBounds(119, 367, 101, 23);
		}
		return btnClearOutput;
	}
	private JCheckBox getChckbxDebug() {
		if (chckbxDebug == null) {
			chckbxDebug = new JCheckBox("Debug");
			chckbxDebug.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeDebugState();
				}
			});
			chckbxDebug.setBounds(336, 367, 57, 23);
		}
		return chckbxDebug;
	}
	private JTextPane getTextPane() {
		if (textPane == null) {
			textPane = new JTextPane();
			textPane.setEditable(false);
		}
		return textPane;
	}
	
	
	/*
	 * My methods
	 */
	
	public void clearOutput(){
		textPane.setText("");
	}
	
	public void changeDebugState(){
		logger.setDebug(chckbxDebug.isSelected());
	}
}
