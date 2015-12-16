package ann.UI;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UIFactory {

	public static final int XLSX_FILE_FILTER = 0;
	public static final int BMP_FILE_FILTER = 1;
	public static final int ANN_FILE_FILTER = 2;
	public static final int DATA_FILE_FILTER = 3;
	
	
	
	
	
	
	
	
	
	
	// generate popup to select file, with filetype filters
	public static File fileSelectorPop(String title, String buttonText,	String defaultText, int fileFilter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(title);
		chooser.setCurrentDirectory(null);
		chooser.setSelectedFile(new File(defaultText));
		if (fileFilter == XLSX_FILE_FILTER) {
			chooser.setFileFilter(new FileNameExtensionFilter("XLSX file",
					"xlsx", "XLSX"));
		}
		if (fileFilter == BMP_FILE_FILTER) {
			chooser.setFileFilter(new FileNameExtensionFilter("BMP file",
					"bmp", "BMP"));
		}	
		if (fileFilter == ANN_FILE_FILTER) {
			chooser.setFileFilter(new FileNameExtensionFilter("ANN file",
					"ann", "ANN"));
		}
		if (fileFilter == DATA_FILE_FILTER) {
			chooser.setFileFilter(new FileNameExtensionFilter("DATA file",
					"data", "DATA"));
		}	

		int ret = chooser.showDialog(null, buttonText);
		if (ret == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return chooser.getSelectedFile();
	}
	
	// generate popup to select directory
	public static File directorySelectorPop(String title, String buttonText, String defaultText) {
		
		JFileChooser chooser = new JFileChooser();
		
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(title);
		chooser.setCurrentDirectory(null);
		chooser.setSelectedFile(new File(defaultText));
		
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    
		chooser.showDialog(null, buttonText);
		return chooser.getSelectedFile();
	}
	
	

	// display message popup
	// JOptionPane.INFORMATION_MESSAGE, WARNING_MESSAGE
	public static void messagePop(String title, String message, int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}
		
	// dropdown selection menu
	public static String dropSelectPop(String[] selections, String message, String title) {
		JDialog.setDefaultLookAndFeelDecorated(true);
	    Object[] selectionValues = selections;
	    Object selection = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, null, selectionValues, null);
	    if (selection == null) {return null;}
	    return selection.toString();
	}
	
	// text entry popup
	public static String textEntryPop(String message, String title) {
		String result = JOptionPane.showInputDialog(null,message,title,JOptionPane.PLAIN_MESSAGE);
		return result;
	}
	
	// returns 0,1,2 for yes, no, cancel respectively.
	public static int yesNoCancelPop(String title, String message, String yesButton, String noButton, String cancelButton) {
		//Custom button text
		Object[] options = {yesButton, noButton, cancelButton};
		int n = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
		    options,
		    options[2]);
		
		return n;
	}
	
	// integer entry popup
	public static int intEntryPop(String message, String title) {
		String r = JOptionPane.showInputDialog(null,message,title,JOptionPane.PLAIN_MESSAGE);
		int result = -1;
		while (r != null) {
			if (isInteger(r)) {
				result = Integer.parseInt(r);
				break;
			}
			else {
				messagePop("Nope", "input an integer value", JOptionPane.WARNING_MESSAGE);
				r = JOptionPane.showInputDialog(null,message,title,JOptionPane.PLAIN_MESSAGE);
			}			
		}
		return result;
	}
	
	// double entry popup
	public static double doubleEntryPop(String message, String title) {
		String r = JOptionPane.showInputDialog(null,message,title,JOptionPane.PLAIN_MESSAGE);
		double result = -1;
		while (r != null) {
			if (isDouble(r)) {
				result = Double.parseDouble(r);
				break;
			}
			else {
				messagePop("Nope", "input a double value", JOptionPane.WARNING_MESSAGE);
				r = JOptionPane.showInputDialog(null,message,title,JOptionPane.PLAIN_MESSAGE);
			}			
		}
		return result;
	}
	
	
	// Utility method. 
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}

	public static void setLF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Unable to set LookAndFeel");
		}
	}

}
