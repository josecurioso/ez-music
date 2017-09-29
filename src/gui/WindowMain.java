package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class WindowMain extends JFrame {

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
	private JTextField textField;
	private JButton btnSelect;
	private JRadioButton rdbtnAudio;
	private JRadioButton rdbtnVideo;
	private JButton btnDownload;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowMain frame = new WindowMain();
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
	public WindowMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 746, 676);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLinkPane());
		contentPane.add(getInfoPane());
		contentPane.add(getDownloadPane());
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
			downloadPane.add(getTextField());
			downloadPane.add(getBtnSelect());
			downloadPane.add(getRdbtnAudio());
			downloadPane.add(getRdbtnVideo());
			downloadPane.add(getBtnDownload());
		}
		return downloadPane;
	}
	private JLabel getLblThumbnail() {
		if (lblThumbnail == null) {
			lblThumbnail = new JLabel("", new ImageIcon(WindowMain.class.getResource("/gui/mqdefault.jpg")), SwingConstants.CENTER);
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
		}
		return textLink;
	}
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Title:");
			lblTitle.setBounds(351, 64, 46, 14);
		}
		return lblTitle;
	}
	private JLabel getLblChannel() {
		if (lblChannel == null) {
			lblChannel = new JLabel("Channel:");
			lblChannel.setBounds(351, 102, 68, 14);
		}
		return lblChannel;
	}
	private JLabel getLblAmount() {
		if (lblAmount == null) {
			lblAmount = new JLabel("Amount:");
			lblAmount.setBounds(351, 138, 68, 14);
		}
		return lblAmount;
	}
	private JLabel getLblDescription() {
		if (lblDescription == null) {
			lblDescription = new JLabel("Description:");
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
		}
		return descPane;
	}
	private JTextField getTextTitle() {
		if (textTitle == null) {
			textTitle = new JTextField();
			textTitle.setBounds(382, 61, 306, 20);
			textTitle.setColumns(10);
		}
		return textTitle;
	}
	private JTextField getTextChannel() {
		if (textChannel == null) {
			textChannel = new JTextField();
			textChannel.setBounds(404, 99, 284, 20);
			textChannel.setColumns(10);
		}
		return textChannel;
	}
	private JTextField getTextAmount() {
		if (textAmount == null) {
			textAmount = new JTextField();
			textAmount.setBounds(401, 135, 55, 20);
			textAmount.setColumns(10);
		}
		return textAmount;
	}
	private JLabel getLblPath() {
		if (lblPath == null) {
			lblPath = new JLabel("Path:");
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
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(62, 26, 526, 20);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton("Select");
			btnSelect.setBounds(598, 25, 89, 23);
		}
		return btnSelect;
	}
	private JRadioButton getRdbtnAudio() {
		if (rdbtnAudio == null) {
			rdbtnAudio = new JRadioButton("Audio");
			rdbtnAudio.setBounds(62, 62, 58, 23);
		}
		return rdbtnAudio;
	}
	private JRadioButton getRdbtnVideo() {
		if (rdbtnVideo == null) {
			rdbtnVideo = new JRadioButton("Video");
			rdbtnVideo.setBounds(122, 62, 58, 23);
		}
		return rdbtnVideo;
	}
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton("Download");
			btnDownload.setBounds(211, 62, 476, 23);
		}
		return btnDownload;
	}
}
