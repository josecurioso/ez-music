package gui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.text.DefaultCaret;

import org.json.JSONException;
import org.json.JSONObject;

import logic.CustomErrorOutputStream;
import logic.CustomInfoOutputStream;
import logic.FailedDownloadException;
import logic.Logger;
import logic.Logic;
import logic.YouTubeAPI;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.UIManager;
import javax.swing.JToolBar;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MainWindow implements ActionListener {

	JFrame frmEzmusic;
	JTextField textLink;
	JTextField textPath;
	JButton buttonDownload;
	JButton btnAudio;
	JButton btnVideo;
	JButton buttonClear;
	JTextArea textPanel;
	JCheckBox debugCheck;
	JTextPane textArea;
	
	JLabel titleLabel;
	JLabel channelLabel;
	JLabel amountLabel;
	JTextPane textPane;
	ImageIcon thumbnail;
	ImageIcon thumbnailPlaceholder;
	JPanel imagePanel;

	ImageIcon defaultThumb;
	ImageIcon lastThumb;
	
	Logger logger;

	PrintStream standardOut = System.out;
	PrintStream standardErr = System.err;
	
	String mode = "audio";
	private JLabel lblTitle;
	private JLabel lblChannel;
	private JLabel lblAmountifPlaylist;
	

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
		
		logger = new Logger();

		frmEzmusic = new JFrame();
		frmEzmusic.setResizable(false);
		frmEzmusic.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/gui/256x256.png")));
		frmEzmusic.setTitle("ez-music");
		frmEzmusic.setBounds(100, 100, 1053, 520);
		frmEzmusic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEzmusic.getContentPane().setLayout(null);

		textLink = new JTextField();
		textLink.setBounds(497, 31, 540, 20);
		frmEzmusic.getContentPane().add(textLink);
		textLink.setColumns(10);
		textLink.getDocument().addDocumentListener(new ChangeListener(this, logger));

		JLabel lblLink = new JLabel("Link:");
		lblLink.setBounds(447, 34, 31, 14);
		frmEzmusic.getContentPane().add(lblLink);

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(447, 65, 31, 14);
		frmEzmusic.getContentPane().add(lblPath);

		textPath = new JTextField();
		textPath.setColumns(10);
		textPath.setBounds(498, 62, 440, 20);
		frmEzmusic.getContentPane().add(textPath);

		buttonDownload = new JButton("Download");
		buttonDownload.setBounds(447, 97, 590, 54);
		buttonDownload.addActionListener(this);
		buttonDownload.setActionCommand("download");
		frmEzmusic.getContentPane().add(buttonDownload);

		JButton buttonSelect = new JButton("Select");
		buttonSelect.setBounds(948, 61, 89, 23);
		buttonSelect.addActionListener(this);
		buttonSelect.setActionCommand("select");
		frmEzmusic.getContentPane().add(buttonSelect);

		textArea = new JTextPane();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(10, 165, 579, 287);

		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(590, 288);
		scrollPane.setLocation(447, 170);
		scrollPane.add(textArea);
		scrollPane.setViewportView(textArea);

		frmEzmusic.getContentPane().add(scrollPane);

		System.setOut(new PrintStream(new CustomInfoOutputStream(textArea)));
		System.setErr(new PrintStream(new CustomErrorOutputStream(textArea)));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.WHITE);
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 0, 609, 20);
		frmEzmusic.getContentPane().add(toolBar);
		
		btnAudio = new JButton("Audio");
		btnAudio.setSelected(true);
		btnAudio.setBorderPainted(false);
		btnAudio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAudio.addActionListener(this);
		btnAudio.setActionCommand("audioSwitch");
		toolBar.add(btnAudio);
		
		btnVideo = new JButton("Video");
		btnVideo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVideo.addActionListener(this);
		btnVideo.setActionCommand("videoSwitch");
		toolBar.add(btnVideo);
		
		buttonClear = new JButton("Clear output");
		buttonClear.addActionListener(this);
		buttonClear.setActionCommand("clear");
		buttonClear.setBounds(638, 465, 122, 23);
		frmEzmusic.getContentPane().add(buttonClear);
		
		debugCheck = new JCheckBox("Debug");
		debugCheck.setBounds(790, 465, 97, 23);
		debugCheck.addActionListener(this);
		debugCheck.setActionCommand("debug");
		frmEzmusic.getContentPane().add(debugCheck);
		
		imagePanel = new JPanel();
		imagePanel.setBounds(57, 44, 320, 180);
		frmEzmusic.getContentPane().add(imagePanel);
		
		defaultThumb = new ImageIcon(MainWindow.class.getResource("/gui/mqdefault.jpg"));
		lastThumb = defaultThumb;
		imagePanel.add(new JLabel("", defaultThumb, JLabel.CENTER));
		
		
		
		
		lblTitle = new JLabel("Title: ");
		lblTitle.setBounds(23, 235, 46, 14);
		frmEzmusic.getContentPane().add(lblTitle);
		
		lblChannel = new JLabel("Channel: ");
		lblChannel.setBounds(23, 260, 46, 14);
		frmEzmusic.getContentPane().add(lblChannel);
		
		lblAmountifPlaylist = new JLabel("Amount (if playlist): ");
		lblAmountifPlaylist.setBounds(23, 285, 97, 14);
		frmEzmusic.getContentPane().add(lblAmountifPlaylist);
		
		
		
		
		
		
		textPane = new JTextPane();
		//textPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textPane.setEditable(false);
		textPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		textPane.setBounds(23, 325, 401, 133);

		DefaultCaret caret1 = (DefaultCaret) textPane.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setSize(417, 132);
		scrollPane_1.setLocation(17, 325);
		scrollPane_1.add(textPane);
		scrollPane_1.setViewportView(textPane);

		frmEzmusic.getContentPane().add(scrollPane_1);
		
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(23, 310, 143, 14);
		frmEzmusic.getContentPane().add(lblDescription);
		
		titleLabel = new JLabel("");
		titleLabel.setBounds(57, 235, 367, 14);
		frmEzmusic.getContentPane().add(titleLabel);
		
		channelLabel = new JLabel("");
		channelLabel.setBounds(79, 260, 345, 14);
		frmEzmusic.getContentPane().add(channelLabel);
		
		amountLabel = new JLabel("");
		amountLabel.setBounds(130, 285, 294, 14);
		frmEzmusic.getContentPane().add(amountLabel);
		
		
		
/*
		MainWindow hola = new MainWindow();
		hola.frmEzmusic.setVisible(true);

*/
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if("debug".equals(arg0.getActionCommand())){
			logger.setDebug(debugCheck.isSelected());
		}
		if("clear".equals(arg0.getActionCommand())){
			textArea.setText(""); 
		}
		if("audioSwitch".equals(arg0.getActionCommand())){
			mode = "audio";
			textArea.setText(""); 
			logger.log("standard", "info", "Now in " + mode + " mode");
			//lblMode.setText("Audio");
		}
		if("videoSwitch".equals(arg0.getActionCommand())){
			mode = "video";
			textArea.setText(""); 
			logger.log("standard", "info", "Now in " + mode + " mode");
			//lblMode.setText("Video");
		}
	    if ("download".equals(arg0.getActionCommand())) {
	    	String requestedURL;
			String savePath;
			
			requestedURL = "https://www.youtube.com/playlist?list=PL9fZWvwimefsU-MruxuoWATNhP-wkF825";
			savePath = "D:/Escritorio/Prueba";
			requestedURL = textLink.getText();
			savePath = textPath.getText();
	    	if(checkFields(requestedURL, savePath)){				
				new File(textPath.getText() + "\\temp").mkdirs();    		
		        
		        
				buttonDownload.setEnabled(false);
				textLink.setEnabled(false);
				textPath.setEnabled(false);
				
				logger.log("standard", "info", "Starting download, hold tight!");
				
				Runnable logic = new Logic(requestedURL, savePath, mode, this, logger);
				Thread t = new Thread(logic);
				t.start();
				
				
	    	}
	    	else{
	    		logger.log("standard", "error", "Error in one of the fields");
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
	
	//needs to go
	public void updateInfo(){
		try {
			JSONObject data = YouTubeAPI.getInfo(textLink.getText(), logger);
			if(data != null){
				logger.log("debug", "info", data.getString("title"));
				logger.log("debug", "info", data.getString("channel"));
				logger.log("debug", "info", new Integer(data.getInt("qt")).toString());
				logger.log("debug", "info", data.getString("description"));
				
				titleLabel.setText(data.getString("title"));				
				channelLabel.setText(data.getString("channel"));
				if(data.getInt("qt") != -1) amountLabel.setText(new Integer(data.getInt("qt")).toString());
				else amountLabel.setText("");
				textPane.setText(data.getString("description"));
				
				Image image = ImageIO.read(new URL(data.getString("thumb")));
			    thumbnail = new ImageIcon(image);
				if(!lastThumb.equals(thumbnail)){
					imagePanel.removeAll();
					imagePanel.add(new JLabel("", thumbnail, JLabel.CENTER));
					lastThumb = thumbnail;
				}
				imagePanel.repaint();
			}
			else if(data == null){
				throw new RuntimeException();
			}
		}
		catch (Exception e) {
			titleLabel.setText("");				
			channelLabel.setText("");
			amountLabel.setText("");
			textPane.setText("");
			if(!lastThumb.equals(defaultThumb)){
				imagePanel.removeAll();
				imagePanel.add(new JLabel("", defaultThumb, JLabel.CENTER));
				lastThumb = defaultThumb;
			}
			imagePanel.repaint();
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
		logger.log("standard", "info", "Proccess finished");
		
		File file = new File(textPath.getText() + "\\temp");
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	        	f.delete();
	        }
	    }
	    file.delete();
	}
}
