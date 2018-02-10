package gui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import gui.locales.Messages;
import logger.Logger;
import logic.MainLogic;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.EventQueue;

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
import javax.swing.JComboBox;

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
	private JButton btnTextOutput;
	private JLabel lblInfo;
	private JComboBox<String> comboBox;
	JButton btnPaste;
	String[] info;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	//From here on, my atributes
	final static ImageIcon DEFAULT_THUMBNAIL = new ImageIcon(MainWindow.class.getResource("/gui/images/mqdefault.jpg"));
	Logger logger;
	GuiActionListener guiListener;
	MainLogic logicMain;
	TextOutputWindow txtOutput;
	Messages messages;

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
		/*
		MainWindow frame = new MainWindow();
		frame.setVisible(true);
		*/
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
		messages = new Messages();
		logger = new Logger();
		guiListener = new GuiActionListener(this, txtOutput, logger);
		logicMain = new MainLogic(this, logger);
		txtOutput = new TextOutputWindow(logger);
		
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
		contentPane.add(getComboBox());
	}
	private JPanel getLinkPane() {
		if (linkPane == null) {
			linkPane = new JPanel();
			linkPane.setBorder(new TitledBorder(null, messages.getString("MainWindow.4"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$
			linkPane.setBounds(10, 11, 710, 81);
			linkPane.setLayout(null);
			linkPane.add(getTextLink());
			linkPane.add(getBtnPaste());
		}
		return linkPane;
	}
	private JPanel getInfoPane() {
		if (infoPane == null) {
			infoPane = new JPanel();
			infoPane.setLayout(null);
			infoPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), messages.getString("MainWindow.6"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))); //$NON-NLS-2$
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
			downloadPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), messages.getString("MainWindow.8"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))); //$NON-NLS-2$
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
			textLink.setBounds(21, 29, 576, 20);
			textLink.setColumns(10);
			textLink.getDocument().addDocumentListener(new ChangeListener(this, logger));
		}
		return textLink;
	}
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel(messages.getString("MainWindow.10")); //$NON-NLS-1$
			lblTitle.setLabelFor(getTextTitle());
			lblTitle.setBounds(351, 64, 46, 14);
		}
		return lblTitle;
	}
	private JLabel getLblChannel() {
		if (lblChannel == null) {
			lblChannel = new JLabel(messages.getString("MainWindow.11")); //$NON-NLS-1$
			lblChannel.setLabelFor(getTextChannel());
			lblChannel.setBounds(351, 102, 68, 14);
		}
		return lblChannel;
	}
	private JLabel getLblAmount() {
		if (lblAmount == null) {
			lblAmount = new JLabel(messages.getString("MainWindow.12")); //$NON-NLS-1$
			lblAmount.setLabelFor(getTextAmount());
			lblAmount.setBounds(351, 138, 68, 14);
		}
		return lblAmount;
	}
	private JLabel getLblDescription() {
		if (lblDescription == null) {
			lblDescription = new JLabel(messages.getString("MainWindow.13")); //$NON-NLS-1$
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
			lblPath = new JLabel(messages.getString("MainWindow.14")); //$NON-NLS-1$
			lblPath.setLabelFor(getTextPath());
			lblPath.setBounds(20, 29, 46, 14);
		}
		return lblPath;
	}
	private JLabel getLblMode() {
		if (lblMode == null) {
			lblMode = new JLabel(messages.getString("MainWindow.15")); //$NON-NLS-1$
			lblMode.setBounds(20, 66, 46, 14);
		}
		return lblMode;
	}
	private JTextField getTextPath() {
		if (textPath == null) {
			textPath = new JTextField();
			textPath.setBounds(49, 26, 539, 20);
			textPath.setColumns(10);
		}
		return textPath;
	}
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton(messages.getString("MainWindow.16")); //$NON-NLS-1$
			btnSelect.addActionListener(guiListener);
			btnSelect.setActionCommand("select");
			btnSelect.setBounds(598, 25, 89, 23);
		}
		return btnSelect;
	}
	private JRadioButton getRdbtnAudio() {
		if (rdbtnAudio == null) {
			rdbtnAudio = new JRadioButton(messages.getString("MainWindow.18")); //$NON-NLS-1$
			rdbtnAudio.setSelected(true);
			buttonGroup.add(rdbtnAudio);
			rdbtnAudio.addActionListener(guiListener);
			rdbtnAudio.setActionCommand("audioSwitch");
			rdbtnAudio.setBounds(59, 62, 67, 23);
		}
		return rdbtnAudio;
	}
	private JRadioButton getRdbtnVideo() {
		if (rdbtnVideo == null) {
			rdbtnVideo = new JRadioButton(messages.getString("MainWindow.20")); //$NON-NLS-1$
			buttonGroup.add(rdbtnVideo);
			rdbtnVideo.addActionListener(guiListener);
			rdbtnVideo.setActionCommand("videoSwitch");
			rdbtnVideo.setBounds(128, 62, 75, 23);
		}
		return rdbtnVideo;
	}
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton(messages.getString("MainWindow.22")); //$NON-NLS-1$
			btnDownload.addActionListener(guiListener);
			btnDownload.setActionCommand("download");
			btnDownload.setBounds(211, 62, 476, 23);
		}
		return btnDownload;
	}
	private JButton getBtnTextOutput() {
		if (btnTextOutput == null) {
			btnTextOutput = new JButton(messages.getString("MainWindow.24")); //$NON-NLS-1$
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
			lblInfo = new JLabel(messages.getString("MainWindow.26")); //$NON-NLS-1$
			lblInfo.setBounds(568, 655, 152, 14);
		}
		return lblInfo;
	}
	private JComboBox<String> getComboBox() {
		if (comboBox == null) {
			//comboBox = new JComboBox(messages.getLanguages());
			comboBox = new JComboBox<String>(messages.getLanguages());
			comboBox.addActionListener(guiListener);
			comboBox.setActionCommand("cbLanguage");
			comboBox.setBounds(10, 638, 74, 20);
		}
		return comboBox;
	}
	private JButton getBtnPaste() {
		if (btnPaste == null) {
			btnPaste = new JButton(messages.getString("MainWindow.34"));
			btnPaste.setBounds(609, 27, 78, 25);
			btnPaste.addActionListener(guiListener);
			btnPaste.setActionCommand("paste");
		}
		return btnPaste;
	}
	
	
	/*
	 * From here on, my own methods
	 */
	
	public void updateInfoPane(String[] info){
		this.info = info;
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
		lblInfo.setText(messages.getString("MainWindow.28")); //$NON-NLS-1$
	}
	
	public void downloadFinished(){
		btnDownload.setEnabled(true);
		textLink.setEnabled(true);
		textPath.setEnabled(true);
		lblInfo.setText(messages.getString("MainWindow.29")); //$NON-NLS-1$
	}
	
	public String[] getCurrentRequest(){
		String mode = "audio";
		if(rdbtnAudio.isSelected()) mode = "audio";
		if(rdbtnVideo.isSelected()) mode = "video";
		
		String[] request = {textLink.getText(), textPath.getText(), mode};
		return request;
	}
	
	public MainLogic getLogicMain(){
		return this.logicMain;
	}

	public void openFileChooser(){
		JFileChooser chooserWindow = new JFileChooser();
		chooserWindow.setCurrentDirectory(null);
        chooserWindow.setDialogTitle(messages.getString("MainWindow.33")); //$NON-NLS-1$
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
	
	public void changeLocale(){
		messages.switchSource((String) comboBox.getSelectedItem());
		
		linkPane.setBorder(new TitledBorder(null, messages.getString("MainWindow.4"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		infoPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), messages.getString("MainWindow.6"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		downloadPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), messages.getString("MainWindow.8"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblTitle.setText(messages.getString("MainWindow.10")); //$NON-NLS-1$
		lblChannel.setText(messages.getString("MainWindow.11")); //$NON-NLS-1$
		lblAmount.setText(messages.getString("MainWindow.12")); //$NON-NLS-1$
		lblDescription.setText(messages.getString("MainWindow.13")); //$NON-NLS-1$
		lblPath.setText(messages.getString("MainWindow.14")); //$NON-NLS-1$
		lblMode.setText(messages.getString("MainWindow.15")); //$NON-NLS-1$
		btnSelect.setText(messages.getString("MainWindow.16")); //$NON-NLS-1$
		rdbtnAudio.setText(messages.getString("MainWindow.18")); //$NON-NLS-1$
		rdbtnVideo.setText(messages.getString("MainWindow.20")); //$NON-NLS-1$
		btnDownload.setText(messages.getString("MainWindow.22")); //$NON-NLS-1$
		btnTextOutput.setText(messages.getString("MainWindow.24")); //$NON-NLS-1$
		lblInfo.setText(messages.getString("MainWindow.26")); //$NON-NLS-1$
		btnPaste.setText(messages.getString("MainWindow.34")); //$NON-NLS-1$
	}
	
	public void pasteAction(){
		try {
			textLink.setText((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
		} catch (Exception e) {
		}
	}
	
	public String[] getCurrentInfo() {
		return this.info;
	}
}
