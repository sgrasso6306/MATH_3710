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
		 * double[] input = {1.0,2.0,3.0,4.0,5.0}; double[] weights =
		 * {1.0,2.0,1.0,1.0,2.0};
		 * 
		 * InputFunction inF = new SummationInputFunction();
		 * System.out.println(inF.processInput(input, weights));
		 * 
		 * 
		 * 
		 * double sigmoidResult = 1 / (2 + Math.expm1(-5));
		 * 
		 * System.out.println(sigmoidResult);
		 */

		/*
		 * int val = 0; double[][] test = new double[400][5000]; for (int i=0;
		 * i<400; i++) { for (int j=0; j<5000; j++) { test[i][j] = val; val++; }
		 * }
		 * 
		 * Matrix testMatrix = new Matrix(test);
		 * 
		 * System.out.println();
		 * 
		 * long startMillis = java.lang.System.currentTimeMillis();
		 * 
		 * Matrix multTest = Utils.multiply(testMatrix,testMatrix.transpose());
		 * //multTest.printMatrix();
		 * 
		 * long endMillis = java.lang.System.currentTimeMillis(); long
		 * multMillis = endMillis - startMillis;
		 * 
		 * System.out.println("done, took "+ multMillis + " milliseconds");
		 * 
		 * // some milli counts on desktop: // 400 x 500 = 725 // 400 x 5000 =
		 * 12531 // 4000 x 5000 = 2057747 34min17.747s
		 * 
		 * // laptop: // 400 x 500 = 786 // 400 x 5000 = 16423
		 * 
		 * // only using about 25% cpu, can I parallelize the multiplication?
		 */

		/*
		 * DataModule dm = new DataModule();
		 * 
		 * DataModule.setLF(); File path =
		 * DataModule.fileSelectorPop("choose xlsx file", "select", "Data.xlsx",
		 * DataModule.XLSX_FILE_FILTER);
		 * 
		 * System.out.println("path: "+path);
		 * 
		 * dm.parseXLSX(path, 1, 1, 3,3);
		 * 
		 * for (int i=0; i<dm._featureVectors.length; i++) { for (int j=0;
		 * j<dm._featureVectors[0].length; j++) {
		 * System.out.print(dm._featureVectors[i][j]+" "); }
		 * System.out.println(dm._outputs[i]); }
		 */

		/*
		 * // try to build an AND gate network Network testNet = new Network();
		 * 
		 * // first input is always 1 for bias double[] input = {1,1,1};
		 * double[][][][] weights = new double[2][3][2][3]; weights[0][0][1][0]
		 * = -30; // layer 0, node 0 (bias weight) to output module
		 * weights[0][1][1][0] = 20; weights[0][2][1][0] = 20;
		 * 
		 * testNet.setInput(input); testNet.setWeights(weights);
		 */

		
		/*
		
		// logical AND, only one neuron for this
		Neuron outputNeuron = new Neuron();
		double[] input = { 1, 1, 0 };
		double[] weights = { -30, 20, 20 };

		double result = outputNeuron.process(input, weights);

		System.out.println("result: " + result);
		*/

		//Neuron outputNeuron = new Neuron();											// YES forward propagation seems to be working!!!!!
		/*

		double[][] inputWeights = new double[3][3];
		double[][] hiddenWeights = new double[3][2];
		
		inputWeights[0][0] = 1;			// bias 
		inputWeights[0][1] = 0.35;
		inputWeights[0][2] = 0.35;
		
		inputWeights[1][0] = 1;
		inputWeights[1][1] = .15;
		inputWeights[1][2] = .25;
		
		inputWeights[2][0] = 1;
		inputWeights[2][1] = .2;
		inputWeights[2][2] = .3;
		
		hiddenWeights[0][0] = .6;		// bias 
		hiddenWeights[0][1] = .6;
		
		hiddenWeights[1][0] = .4;
		hiddenWeights[1][1] = .5;
		
		hiddenWeights[2][0] = .45;
		hiddenWeights[2][1] = .55;
	*/
		double[] input = { 1, .05, .1 };
		double[] targets = { 1, 0 };
		
		//Network(int inputCount, int hiddenNeuronCount, int outputCount, double learningConstant, int initMode)
		Network testNet = new Network(3, 3, 2, 10, Network.RUN_MODE);
		//testNet.setInputWeights(inputWeights);
		//testNet.setHiddenWeights(hiddenWeights);
		
		testNet.forwardPropagation(new Vector(input));
		
		System.out.println("initial error: "+testNet.computeTotalError(new Vector(targets)));
		
		
		for (int i=0; i<1000000; i++) {
			testNet.backwardPropagation(new Vector(targets));
			
			testNet.forwardPropagation(new Vector(input));
			

		}
		
		System.out.println("error after backprop: "+testNet.computeTotalError(new Vector(targets)));
		System.out.println("output 0: "+testNet.getOutput().getElement(0));
		System.out.println("output 1: "+testNet.getOutput().getElement(1));
		System.out.println();
	}

}
