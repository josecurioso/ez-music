package gui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JTextField;


import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.text.DefaultCaret;

import logic.CustomOutputStream;
import logic.Logic;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JToolBar;

public class MainWindow implements ActionListener {

	private JFrame frmEzmusic;
	private JTextField textLink;
	private JTextField textPath;
	private JButton buttonDownload;
	JButton btnAudio;
	JButton btnVideo;
	JTextArea textPanel;

	PrintStream standardOut = System.out;
	PrintStream standardErr = System.err;
	
	String mode = "audio";

	private JTextArea textArea;
	private JButton buttonClear;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            // Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("FileChooser.useSystemExtensionHiding", false);
	    } 
	    catch (Exception e) {
	       // handle exception
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmEzmusic.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmEzmusic = new JFrame();
		frmEzmusic.setResizable(false);
		frmEzmusic.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/gui/256x256.png")));
		frmEzmusic.setTitle("ez-music");
		frmEzmusic.setBounds(100, 100, 615, 502);
		frmEzmusic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEzmusic.getContentPane().setLayout(null);

		textLink = new JTextField();
		textLink.setBounds(59, 39, 530, 20);
		frmEzmusic.getContentPane().add(textLink);
		textLink.setColumns(10);

		JLabel lblLink = new JLabel("Link:");
		lblLink.setBounds(24, 42, 31, 14);
		frmEzmusic.getContentPane().add(lblLink);

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(24, 73, 31, 14);
		frmEzmusic.getContentPane().add(lblPath);

		textPath = new JTextField();
		textPath.setColumns(10);
		textPath.setBounds(59, 70, 413, 20);
		frmEzmusic.getContentPane().add(textPath);

		buttonDownload = new JButton("Download");
		buttonDownload.setBounds(86, 101, 430, 54);
		buttonDownload.addActionListener(this);
		buttonDownload.setActionCommand("download");
		frmEzmusic.getContentPane().add(buttonDownload);

		JButton buttonSelect = new JButton("Select");
		buttonSelect.setBounds(486, 69, 89, 23);
		buttonSelect.addActionListener(this);
		buttonSelect.setActionCommand("select");
		frmEzmusic.getContentPane().add(buttonSelect);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(10, 165, 579, 287);

		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(579, 282);
		scrollPane.setLocation(10, 170);
		scrollPane.add(textArea);
		scrollPane.setViewportView(textArea);

		frmEzmusic.getContentPane().add(scrollPane);

		System.setOut(new PrintStream(new CustomOutputStream(textArea)));
		System.setErr(new PrintStream(new CustomOutputStream(textArea)));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.LIGHT_GRAY);
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 0, 609, 16);
		frmEzmusic.getContentPane().add(toolBar);
		
		btnAudio = new JButton("Audio");
		btnAudio.addActionListener(this);
		btnAudio.setActionCommand("audioSwitch");
		toolBar.add(btnAudio);
		
		btnVideo = new JButton("Video");
		btnVideo.addActionListener(this);
		btnVideo.setActionCommand("videoSwitch");
		toolBar.add(btnVideo);
		
		buttonClear = new JButton("Clear output");
		buttonClear.addActionListener(this);
		buttonClear.setActionCommand("clear");
		buttonClear.setBounds(228, 450, 122, 23);
		frmEzmusic.getContentPane().add(buttonClear);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if("clear".equals(arg0.getActionCommand())){
			textArea.setText(""); 
		}
		if("audioSwitch".equals(arg0.getActionCommand())){
			mode = "audio";
			textArea.setText(""); 
			System.out.println("Now in " + mode + " mode");
		}
		if("videoSwitch".equals(arg0.getActionCommand())){
			mode = "video";
			textArea.setText(""); 
			System.out.println("Now in " + mode + " mode");
		}
	    if ("download".equals(arg0.getActionCommand())) {
	    	String requestedURL;
			String savePath;
			
			requestedURL = "https://www.youtube.com/playlist?list=PL9fZWvwimefsU-MruxuoWATNhP-wkF825";
			savePath = "D:/Escritorio/Prueba";
			requestedURL = textLink.getText();
			savePath = textPath.getText();
	    	if(checkFields(requestedURL, savePath)){
				buttonDownload.setEnabled(false);
				textLink.setEnabled(false);
				textPath.setEnabled(false);
				
				System.out.println("Starting download, hold tight!");
				
				Runnable logic = new Logic(requestedURL, savePath, mode, this);
				Thread t = new Thread(logic);
				t.start();
				
				
	    	}
	    	else{
	    		System.out.println("Error in one of the fields");
	    		buttonDownload.setEnabled(true);
				textLink.setEnabled(true);
				textPath.setEnabled(true);
	    	}
	    } 
	    else if("select".equals(arg0.getActionCommand())){
	    	JFileChooser chooser = new JFileChooser();
	        chooser.setCurrentDirectory(new java.io.File("."));
	        chooser.setDialogTitle("Choose a directory");
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        chooser.setAcceptAllFileFilterUsed(false);

	        if (chooser.showOpenDialog(frmEzmusic) == JFileChooser.APPROVE_OPTION) {
	        	textPath.setText(chooser.getSelectedFile().getAbsolutePath());
	        } 
	        else {
	        	System.out.println("No Selection");
	        }
	    }	
	}
	
	public boolean checkFields(String link, String path){
		boolean clink = false;
        boolean cpath = false;
        if (link.contains("http://www.youtube.com/playlist?list=") || link.contains("https://www.youtube.com/playlist?list=") || link.contains("http://www.youtube.com/watch?v=") || link.contains("https://www.youtube.com/watch?v=")){
        	clink = true;
        }
        if (path != ""){
        	cpath = true;
        }
        return(cpath && clink);
	}
	
	public void downloadFinished(){
		buttonDownload.setEnabled(true);
		textLink.setEnabled(true);
		textPath.setEnabled(true);
		System.out.println("Proccess finished");
	}
}
