package gui;

import java.awt.EventQueue;
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

import download.CustomOutputStream;
import download.HttpDownloader;

import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class MainWindow implements ActionListener {

	private JFrame frmEzmusic;
	private JTextField textLink;
	private JTextField textPath;
	private JButton buttonDownload;
	JTextArea textPanel;

	PrintStream standardOut = System.out;
	PrintStream standardErr = System.err;

	private JTextArea textArea;

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
		frmEzmusic.setIconImage(
				Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/download/256x256.png")));
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
		buttonDownload.setBounds(146, 105, 299, 54);
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

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
				Runnable downloader = new HttpDownloader(requestedURL, savePath, this);
				Thread t = new Thread(downloader);
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
		System.out.println("Download finished");
	}
}