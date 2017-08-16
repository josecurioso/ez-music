package logic;
 
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
 
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
 
/**
 * This class extends from OutputStream to redirect output to a JTextArrea
 * @author www.codejava.net
 *
 */
public class CustomErrorOutputStream extends OutputStream {
    private JTextPane textArea;

	SimpleAttributeSet keyWord;
	StyledDocument doc;
     
    public CustomErrorOutputStream(JTextPane textArea) {
        this.textArea = textArea;
        keyWord = new SimpleAttributeSet();
    	doc = textArea.getStyledDocument();
    	StyleConstants.setForeground(keyWord, Color.RED.darker());
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area    	
    	try
    	{
    		doc.insertString(doc.getLength(), String.valueOf((char)b), keyWord );
    	}
    	catch(Exception e) { System.out.println(e); }
    }
    	
        //textArea.append(String.valueOf((char)b));
}