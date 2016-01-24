package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class ImageModule {

	// get image input from file
	public static double[] loadImage(File path) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(path);
		} catch (IOException e) {
		}
		
		int w = img.getWidth();
		int h = img.getHeight();
		double[] result = new double[w*h];
		int i = 0;
		
		Raster raster = img.getData();
		for (int j = 0; j < w; j++) {
		    for (int k = 0; k < h; k++) {
		        result[i] = raster.getSample(j, k, 0);
		        i++;
		    }
		}
		
		return result;
	}

	
	// build data set from directory of BMP images				root->sub directories named by digit
	public static DataSet buildDataSetFromBMPDir(File rootDir) {
		
		//DataSet(int observationCount, int featureCount, int outputCount)
		DataSet result;
		
		// check whether rootDir is a directory
		if (!rootDir.isDirectory()) {
			return null;
		}
		
		File[] listOfDirs = rootDir.listFiles();
		//int[] labels = new int[listOfDirs.length];
		int digitCount = 0;
		int imageCount = 0;
		int featureCount = 0;
		
		// diagnostic directory pass
		for (int i=0; i<listOfDirs.length; i++) {
			
			// make sure this sub-directory is a directory
			if (!listOfDirs[i].isDirectory()) {
				return null;
			}
			
			digitCount++;
			
			//labels[i] = Integer.parseInt(listOfDirs[i].getName());
			File[] listOfFiles = listOfDirs[i].listFiles();
			
			// determine image count and feature count
			for (int j=0; j<listOfFiles.length; j++) {
				imageCount++;	
				if (i==0 && j==0) {
					featureCount = loadImage(listOfFiles[0]).length;
				}
			}
		}
		
		result = new DataSet(imageCount, featureCount, digitCount);
		
		for (int i=0; i<listOfDirs.length; i++) {
			
			int label = Integer.parseInt(listOfDirs[i].getName());
			File[] listOfFiles = listOfDirs[i].listFiles();
			
			// build output array
			double[] output = new double[digitCount];
			for (int k=0; k<output.length; k++) {
				if (label == k) {
					output[k] = 1.0;
				}
				else {
					output[k] = 0.0;
				}
			}
	
			// add image to dataSet
			for (int j=0; j<listOfFiles.length; j++) {
				result.addObservation(loadImage(listOfFiles[j]), output);
			}
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
