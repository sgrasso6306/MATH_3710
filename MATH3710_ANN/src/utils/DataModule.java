package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataModule {
	
	public double[][]	_featureVectors;
	public double[]	_outputs;

	public DataModule() {
		
	}
	

	// input: file path of spreadsheet, start row, start col, #features ? 
	// given file path, open excel workbook and parse inputs and y
		
	public void parseXLSX(File xlsxPath, int startrow, int startcol, int featureCount, int observationCount) {
		_featureVectors = new double[observationCount][featureCount];
		_outputs = new double[observationCount];
		try {
	        FileInputStream fis = new FileInputStream(xlsxPath);
	        XSSFWorkbook wb = new XSSFWorkbook(fis);
	        Sheet sheet = wb.getSheetAt(0);
	        
	        Row row;
	        for (int rowIndex = 0; rowIndex < observationCount; rowIndex++) {
	        	// load next row
	        	row = sheet.getRow(startrow+rowIndex);
	        	
	        	// construct feature vector and output entry for this row
	        	int featureIndex = 0;
	        	for(int i=startcol; i<startcol + featureCount; i++) {
	        		_featureVectors[rowIndex][featureIndex] = new Double(row.getCell(i).getNumericCellValue());
	        		featureIndex++;
	        	}
	        	_outputs[rowIndex] = new Double(row.getCell(startcol+featureCount).getNumericCellValue());
	        }
	        fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static final int XLSX_FILE_FILTER = 0;
	public static File fileSelectorPop(String title, String buttonText, String defaultText, int fileFilter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(title);
		chooser.setCurrentDirectory(null);
		chooser.setSelectedFile(new File(defaultText));
		if (fileFilter == XLSX_FILE_FILTER) {
			chooser.setFileFilter(new FileNameExtensionFilter("XLSX file", "xlsx","XLSX"));
		}
		
		chooser.showDialog(null, buttonText);
		return chooser.getSelectedFile();
	}
	public static void setLF() {
    	try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.println("Unable to set LookAndFeel");
        }
	}
	
}
