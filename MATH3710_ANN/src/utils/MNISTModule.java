package utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingUtilities;

import ann.DataSet;
import ann.UI.UI;

/**
 * This class implements a reader for the MNIST dataset of handwritten digits. The dataset is found
 * at http://yann.lecun.com/exdb/mnist/.
 * 
 * @author Gabe Johnson <johnsogg@cmu.edu>, adapted by Steve Grasso
 */

public class MNISTModule {

	public static final double INPUT_SCALE_FACTOR = 0.0000001;
	
	
	public static DataSet buildDataSetFromMNIST(File labelFile, File dataFile, UI output) throws IOException {

		DataSet dataSet = null;
		
		DataInputStream labels = new DataInputStream(new FileInputStream(labelFile));
		DataInputStream images = new DataInputStream(new FileInputStream(dataFile));
		int magicNumber = labels.readInt();
		if (magicNumber != 2049) {
			System.err.println("Label file has wrong magic number: "
					+ magicNumber + " (should be 2049)");
			return null;
		}
		magicNumber = images.readInt();
		if (magicNumber != 2051) {
			System.err.println("Image file has wrong magic number: "
					+ magicNumber + " (should be 2051)");
			return null;
		}
		int numLabels = labels.readInt();
		int numImages = images.readInt();
		int numRows = images.readInt();
		int numCols = images.readInt();
		if (numLabels != numImages) {
			System.err
					.println("Image file and label file do not contain the same number of entries.");
			System.err.println("  Label file contains: " + numLabels);
			System.err.println("  Image file contains: " + numImages);
			return null;
		}
		
		dataSet = new DataSet(numImages, numCols*numRows +1, 10);

		long start = System.currentTimeMillis();
		int numLabelsRead = 0;
		int numImagesRead = 0;
		while (labels.available() > 0 && numLabelsRead < numLabels) {
			Byte label = labels.readByte();
			numLabelsRead++;
			int[][] image = new int[numCols][numRows];
			double[] imageLinear = new double[numCols*numRows +1];
			int linearIndex = 1;
			imageLinear[0] = 1;
			for (int colIdx = 0; colIdx < numCols; colIdx++) {
				for (int rowIdx = 0; rowIdx < numRows; rowIdx++) {
					int pixel = images.readUnsignedByte();
					image[colIdx][rowIdx] = pixel;
					
					imageLinear[linearIndex] = pixel*INPUT_SCALE_FACTOR;								// normalize input
					
					linearIndex++;
				}
			}
			dataSet.addObservation(imageLinear, getOutputArray(label));														//add observation
			numImagesRead++;

			// At this point, 'label' and 'image' agree and you can do whatever
			// you like with them.
            
			
			if (numLabelsRead % 10 == 0) {
				System.out.print(".");

			}
			if ((numLabelsRead % 800) == 0) {
				System.out.print(" " + numLabelsRead + " / " + numLabels);
				long end = System.currentTimeMillis();
				long elapsed = end - start;
				long minutes = elapsed / (1000 * 60);
				long seconds = (elapsed / 1000) - (minutes * 60);
				System.out.println("  " + minutes + " m " + seconds + " s ");

			}

		}
		output.outputPrintln("");
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		long minutes = elapsed / (1000 * 60);
		long seconds = (elapsed / 1000) - (minutes * 60);
		output.outputPrintln("Read " + numLabelsRead + " samples in " + minutes
				+ " m " + seconds + " s ");
		
		return dataSet;
	}
	
	public static double[] getOutputArray(int label) {
		if (label == 0) {
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		}
		else if (label == 1) {
			return new double[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
		}
		else if (label == 2) {
			return new double[] { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
		}
		else if (label == 3) {
			return new double[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
		}
		else if (label == 4) {
			return new double[] { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
		}
		else if (label == 5) {
			return new double[] { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
		}
		else if (label == 6) {
			return new double[] { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
		}		
		else if (label == 7) {
			return new double[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
		}
		else if (label == 8) {
			return new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
		}
		else if (label == 9) {
			return new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
		}
		else {
			return null;
		}
	}


}
