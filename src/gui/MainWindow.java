package gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


import logger.Logger;
import logic.LogicMain;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import java.net.URL;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;  //Auto-generated
	private JPanel contentPane;
	private JPanel linkPane;
	private JPanel infoPane;
	private JPanel downloadPane;
	private JLabel lblThumbnail;
	private JTextField textLink;
	private JLabel lblTitle;
	private JLabel lblChannel;
	private JLabel lblAmount;
	private JLabel lblDescription;
	private JScrollPane descScroll;
	private JTextPane descPane;
	private JTextField textTitle;
	private JTextField textChannel;
	private JTextField textAmount;
	private JLabel lblPath;
	private JLabel lblMode;
	private JTextField textPath;
	private JButton btnSelect;
	private JRadioButton rdbtnAudio;
	private JRadioButton rdbtnVideo;
	private JButton btnDownload;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	//From here on, my atributes
	final static ImageIcon DEFAULT_THUMBNAIL = new ImageIcon(MainWindow.class.getResource("/gui/images/mqdefault.jpg"));
	Logger logger;
	GuiActionListener guiListener;
	LogicMain logicMain;
	TextOutput txtOutput;
	private JButton btnTextOutput;
	private JLabel lblInfo;

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
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		
		logger = new Logger();
		guiListener = new GuiActionListener(this, txtOutput, logger);
		logicMain = new LogicMain(this, logger);
		txtOutput = new TextOutput(logger);
		
		setTitle("ez-music");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 698);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/gui/images/256x256.png")));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLinkPane());
		contentPane.add(getInfoPane());
		contentPane.add(getDownloadPane());
		contentPane.add(getBtnTextOutput());
		contentPane.add(getLblInfo());
	}
	private JPanel getLinkPane() {
		if (linkPane == null) {
			linkPane = new JPanel();
			linkPane.setBorder(new TitledBorder(null, "1 - Link", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			linkPane.setBounds(10, 11, 710, 81);
			linkPane.setLayout(null);
			linkPane.add(getTextLink());
		}
		return linkPane;
	}
	private JPanel getInfoPane() {
		if (infoPane == null) {
			infoPane = new JPanel();
			infoPane.setLayout(null);
			infoPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "2 - Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			infoPane.setBounds(10, 103, 710, 405);
			infoPane.add(getLblThumbnail());
			infoPane.add(getLblTitle());
			infoPane.add(getLblChannel());
			infoPane.add(getLblAmount());
			infoPane.add(getLblDescription());
			infoPane.add(getDescScroll());
			infoPane.add(getTextTitle());
			infoPane.add(getTextChannel());
			infoPane.add(getTextAmount());
		}
		return infoPane;
	}
	private JPanel getDownloadPane() {
		if (downloadPane == null) {
			downloadPane = new JPanel();
			downloadPane.setLayout(null);
			downloadPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "3 - Download", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			downloadPane.setBounds(10, 520, 710, 107);
			downloadPane.add(getLblPath());
			downloadPane.add(getLblMode());
			downloadPane.add(getTextPath());
			downloadPane.add(getBtnSelect());
			downloadPane.add(getRdbtnAudio());
			downloadPane.add(getRdbtnVideo());
			downloadPane.add(getBtnDownload());
		}
		return downloadPane;
	}
	private JLabel getLblThumbnail() {
		if (lblThumbnail == null) {
			lblThumbnail = new JLabel("", DEFAULT_THUMBNAIL, SwingConstants.CENTER);
			lblThumbnail.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			lblThumbnail.setBounds(21, 24, 320, 180);
		}
		return lblThumbnail;
	}
	private JTextField getTextLink() {
		if (textLink == null) {
			textLink = new JTextField();
			textLink.setBounds(21, 29, 660, 20);
			textLink.setColumns(10);
			textLink.getDocument().addDocumentListener(new ChangeListener(this, logger));
		}
		return textLink;
	}
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Title:");
			lblTitle.setLabelFor(getTextTitle());
			lblTitle.setBounds(351, 64, 46, 14);
		}
		return lblTitle;
	}
	private JLabel getLblChannel() {
		if (lblChannel == null) {
			lblChannel = new JLabel("Channel:");
			lblChannel.setLabelFor(getTextChannel());
			lblChannel.setBounds(351, 102, 68, 14);
		}
		return lblChannel;
	}
	private JLabel getLblAmount() {
		if (lblAmount == null) {
			lblAmount = new JLabel("Amount:");
			lblAmount.setLabelFor(getTextAmount());
			lblAmount.setBounds(351, 138, 68, 14);
		}
		return lblAmount;
	}
	private JLabel getLblDescription() {
		if (lblDescription == null) {
			lblDescription = new JLabel("Description:");
			lblDescription.setLabelFor(getDescPane());
			lblDescription.setBounds(351, 190, 68, 14);
		}
		return lblDescription;
	}
	private JScrollPane getDescScroll() {
		if (descScroll == null) {
			descScroll = new JScrollPane();
			descScroll.setBounds(21, 215, 668, 166);
			descScroll.setViewportView(getDescPane());
		}
		return descScroll;
	}
	private JTextPane getDescPane() {
		if (descPane == null) {
			descPane = new JTextPane();
			descPane.setEditable(false);
		}
		return descPane;
	}
	private JTextField getTextTitle() {
		if (textTitle == null) {
			textTitle = new JTextField();
			textTitle.setEditable(false);
			textTitle.setBounds(382, 61, 306, 20);
			textTitle.setColumns(10);
		}
		return textTitle;
	}
	private JTextField getTextChannel() {
		if (textChannel == null) {
			textChannel = new JTextField();
			textChannel.setEditable(false);
			textChannel.setBounds(404, 99, 284, 20);
			textChannel.setColumns(10);
		}
		return textChannel;
	}
	private JTextField getTextAmount() {
		if (textAmount == null) {
			textAmount = new JTextField();
			textAmount.setEditable(false);
			textAmount.setBounds(401, 135, 55, 20);
			textAmount.setColumns(10);
		}
		return textAmount;
	}
	private JLabel getLblPath() {
		if (lblPath == null) {
			lblPath = new JLabel("Path:");
			lblPath.setLabelFor(getTextPath());
			lblPath.setBounds(20, 29, 46, 14);
		}
		return lblPath;
	}
	private JLabel getLblMode() {
		if (lblMode == null) {
			lblMode = new JLabel("Mode:");
			lblMode.setBounds(20, 66, 46, 14);
		}
		return lblMode;
	}
	private JTextField getTextPath() {
		if (textPath == null) {
			textPath = new JTextField();
			textPath.setBounds(62, 26, 526, 20);
			textPath.setColumns(10);
		}
		return textPath;
	}
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton("Select");
			btnSelect.addActionListener(guiListener);
			btnSelect.setActionCommand("select");
			btnSelect.setBounds(598, 25, 89, 23);
		}
		return btnSelect;
	}
	private JRadioButton getRdbtnAudio() {
		if (rdbtnAudio == null) {
			rdbtnAudio = new JRadioButton("Audio");
			rdbtnAudio.setSelected(true);
			buttonGroup.add(rdbtnAudio);
			rdbtnAudio.addActionListener(guiListener);
			rdbtnAudio.setActionCommand("audioSwitch");
			rdbtnAudio.setBounds(62, 62, 58, 23);
		}
		return rdbtnAudio;
	}
	private JRadioButton getRdbtnVideo() {
		if (rdbtnVideo == null) {
			rdbtnVideo = new JRadioButton("Video");
			buttonGroup.add(rdbtnVideo);
			rdbtnVideo.addActionListener(guiListener);
			rdbtnVideo.setActionCommand("videoSwitch");
			rdbtnVideo.setBounds(122, 62, 58, 23);
		}
		return rdbtnVideo;
	}
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton("Download");
			btnDownload.addActionListener(guiListener);
			btnDownload.setActionCommand("download");
			btnDownload.setBounds(211, 62, 476, 23);
		}
		return btnDownload;
	}
	private JButton getBtnTextOutput() {
		if (btnTextOutput == null) {
			btnTextOutput = new JButton("Show text output");
			btnTextOutput.setBounds(300, 636, 116, 22);
			btnTextOutput.setFocusPainted(false);
			btnTextOutput.setBorderPainted(false);
			btnTextOutput.addActionListener(guiListener);
			btnTextOutput.setActionCommand("textOutput");
		}
		return btnTextOutput;
	}
	private JLabel getLblInfo() {
		if (lblInfo == null) {
			lblInfo = new JLabel("inactive");
			lblInfo.setBounds(568, 655, 152, 14);
		}
		return lblInfo;
	}
	
	
	/*
	 * From here on, my own methods
	 */
	
	public void updateInfoPane(String[] info){
		textTitle.setText(info[0]);
		textChannel.setText(info[1]);
		textAmount.setText(info[2]);
		descPane.setText(info[3]);
		
		if(info[4].equals("")){
			lblThumbnail.setIcon(DEFAULT_THUMBNAIL);
		}
		else{
			try {
				lblThumbnail.setIcon(new ImageIcon(ImageIO.read(new URL(info[4]))));
			} catch (Exception e) {
				
			}
		}
		lblThumbnail.repaint();
	}
	
	public void downloadStarted(){
		btnDownload.setEnabled(false);
		textLink.setEnabled(false);
		textPath.setEnabled(false);
		lblInfo.setText("Download en course");
	}
	
	public void downloadFinished(){
		btnDownload.setEnabled(true);
		textLink.setEnabled(true);
		textPath.setEnabled(true);
		lblInfo.setText("Download finished, inactive");
	}
	
	public String[] getCurrentRequest(){
		String mode = "audio";
		if(rdbtnAudio.isSelected()) mode = "audio";
		if(rdbtnVideo.isSelected()) mode = "video";
		
		String[] request = {textLink.getText(), textPath.getText(), mode};
		return request;
	}
	
	public LogicMain getLogicMain(){
		return this.logicMain;
	}

	public void openFileChooser(){
		JFileChooser chooserWindow = new JFileChooser();
		chooserWindow.setCurrentDirectory(null);
        chooserWindow.setDialogTitle("Choose a directory");
        chooserWindow.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooserWindow.setAcceptAllFileFilterUsed(false);

        if (chooserWindow.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        	textPath.setText(chooserWindow.getSelectedFile().getAbsolutePath());
        } 
        else {
        	System.out.println("No Selection");
        }
	}
	
	public void showTextOutput(){
		txtOutput.setVisible(true);
	}
}
