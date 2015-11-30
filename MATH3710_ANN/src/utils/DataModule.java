package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import ann.Network;
import ann.WeightSet;

public class DataModule {

	public double[][] _featureVectors;
	public double[] _outputs;

	public DataModule() {

	}

	// given xlsx file path to network save file, load input count
	public static int loadInputCount(File path) {
		int result = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);

			Row row = sheet.getRow(0);
			result = new Integer((int) row.getCell(0).getNumericCellValue());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// given xlsx file path to network save file, load hidden count
	public static int loadHiddenCount(File path) {
		int result = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);

			Row row = sheet.getRow(0);
			result = new Integer((int) row.getCell(1).getNumericCellValue());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// given xlsx file path to network save file, load output count
	public static int loadOutputCount(File path) {
		int result = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);

			Row row = sheet.getRow(0);
			result = new Integer((int) row.getCell(2).getNumericCellValue());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// given xlsx file path to network save file, load output count
	public static double loadLearningConstant(File path) {
		double result = 0;
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);

			Row row = sheet.getRow(0);
			result = new Double(row.getCell(3).getNumericCellValue());
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static double[][] loadInputWeights(File path) {
		int iCount = 0;
		int hCount = 0;
		double[][] inputWeights = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);

			iCount = new Integer((int) row.getCell(0).getNumericCellValue());
			hCount = new Integer((int) row.getCell(1).getNumericCellValue());
			inputWeights = new double[iCount][hCount];

			row = sheet.getRow(1);

			for (int i = 0; i < iCount; i++) {
				inputWeights[i][0] = 1;
				for (int h = 1; h < hCount; h++) { // start inner loop at 1; no
													// edge pointing to next
													// layer's bias node
					inputWeights[i][h] = new Double(row.getCell(
							i * (hCount - 1) + h).getNumericCellValue());
				}
			}

			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputWeights;
	}

	public static double[][] loadHiddenWeights(File path) {
		int hCount = 0;
		int oCount = 0;
		double[][] hiddenWeights = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);

			hCount = new Integer((int) row.getCell(1).getNumericCellValue());
			oCount = new Integer((int) row.getCell(2).getNumericCellValue());
			hiddenWeights = new double[hCount][oCount];

			row = sheet.getRow(2);

			for (int h = 0; h < hCount; h++) {
				for (int o = 0; o < oCount; o++) { // start inner loop at 1; no
													// edge pointing to next
													// layer's bias node
					hiddenWeights[h][o] = new Double(row.getCell(
							h * (oCount) + o + 1).getNumericCellValue());
				}
			}

			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hiddenWeights;
	}

	
	
	public static void saveNetwork(File path, Network network) {
		int iCount = network.getInputCount();
		int hCount = network.getHiddenCount();
		int oCount = network.getOutputCount();
		double lConst = network.getLearningConstant();
		WeightSet weights = network.getWeights();

		// initialize in-memory excel sheet
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row stateRow = sheet.createRow(0);
		Row iWeightRow = sheet.createRow(1);
		Row hWeightRow = sheet.createRow(2);

		// save state
		Cell cell = stateRow.createCell(0);
		cell.setCellValue(iCount);
		cell = stateRow.createCell(1);
		cell.setCellValue(hCount);
		cell = stateRow.createCell(2);
		cell.setCellValue(oCount);
		cell = stateRow.createCell(3);
		cell.setCellValue(lConst);
		
		// save input weights
		for (int i = 0; i < iCount; i++) {
			for (int h = 1; h < hCount; h++) {
				
				cell = iWeightRow.createCell(i * (hCount - 1) + h);
				cell.setCellValue(weights.getWeight(WeightSet.INPUT_LAYER, i, h));							
			}
		}

		// save output weights
		for (int h = 0; h < hCount; h++) {
			for (int o = 0; o < oCount; o++) {
				
				cell = hWeightRow.createCell(h * (oCount) + o + 1);
				cell.setCellValue(weights.getWeight(WeightSet.HIDDEN_LAYER, h, o));							
			}
		}

		
		// write to file
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	
	
	
	// input: file path of spreadsheet, start row, start col, #features ?
	// given file path, open excel workbook and parse inputs and y

	public void parseXLSX(File xlsxPath, int startrow, int startcol,
			int featureCount, int observationCount) {
		_featureVectors = new double[observationCount][featureCount];
		_outputs = new double[observationCount];
		try {
			FileInputStream fis = new FileInputStream(xlsxPath);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);

			Row row;
			for (int rowIndex = 0; rowIndex < observationCount; rowIndex++) {
				// load next row
				row = sheet.getRow(startrow + rowIndex);

				// construct feature vector and output entry for this row
				int featureIndex = 0;
				for (int i = startcol; i < startcol + featureCount; i++) {
					_featureVectors[rowIndex][featureIndex] = new Double(row
							.getCell(i).getNumericCellValue());
					featureIndex++;
				}
				_outputs[rowIndex] = new Double(row.getCell(
						startcol + featureCount).getNumericCellValue());
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final int XLSX_FILE_FILTER = 0;

	public static File fileSelectorPop(String title, String buttonText,
			String defaultText, int fileFilter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(title);
		chooser.setCurrentDirectory(null);
		chooser.setSelectedFile(new File(defaultText));
		if (fileFilter == XLSX_FILE_FILTER) {
			chooser.setFileFilter(new FileNameExtensionFilter("XLSX file",
					"xlsx", "XLSX"));
		}

		chooser.showDialog(null, buttonText);
		return chooser.getSelectedFile();
	}

	public static void setLF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Unable to set LookAndFeel");
		}
	}

}
