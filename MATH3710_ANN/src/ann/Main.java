package ann;
import java.io.File;

import org.apache.poi.xssf.eventusermodel.XLSX2CSV;

import utils.DataModule;
import linear.algebra.Matrix;
import linear.algebra.Utils;
import linear.algebra.Vector;
import ann.impl.SummationInputFunction;
import ann.interfaces.InputFunction;


public class Main {

	public static void main(String[] args) {

		
		/*
		double[] input = {1.0,2.0,3.0,4.0,5.0};
		double[] weights = {1.0,2.0,1.0,1.0,2.0};
		
		InputFunction inF = new SummationInputFunction();
		System.out.println(inF.processInput(input, weights));
		
		
		
		double sigmoidResult = 1 / (2 + Math.expm1(-5));
		
		System.out.println(sigmoidResult);
		*/
		
		/*
		int val = 0;
		double[][] test = new double[400][5000];
		for (int i=0; i<400; i++) {
			for (int j=0; j<5000; j++) {
				test[i][j] = val;
				val++;
			}
		}
		
		Matrix testMatrix = new Matrix(test);
		
		System.out.println();
		
		long startMillis = java.lang.System.currentTimeMillis();
		
		Matrix multTest = Utils.multiply(testMatrix,testMatrix.transpose());
		//multTest.printMatrix();
		
		long endMillis = java.lang.System.currentTimeMillis();
		long multMillis = endMillis - startMillis;
		
		System.out.println("done, took "+ multMillis + " milliseconds");
		
		// some milli counts on desktop: 
		// 400 x 500  = 725
		// 400 x 5000 = 12531
		// 4000 x 5000 = 2057747		34min17.747s
		
		// laptop:
		// 400 x 500  = 786
		// 400 x 5000 = 16423
		
		// only using about 25% cpu, can I parallelize the multiplication?
		*/
		
		DataModule dm = new DataModule();
		
		DataModule.setLF();
		File path = DataModule.fileSelectorPop("choose xlsx file", "select", "Data.xlsx", DataModule.XLSX_FILE_FILTER);
	
		System.out.println("path: "+path);
		
		dm.parseXLSX(path, 1, 1, 3,3);
		
		for (int i=0; i<dm._featureVectors.length; i++) {
			for (int j=0; j<dm._featureVectors[0].length; j++) {
				System.out.print(dm._featureVectors[i][j]+" ");
			}
			System.out.println(dm._outputs[i]);
		}
	}

}
