package ann;

import java.io.File;

import org.apache.poi.xssf.eventusermodel.XLSX2CSV;

import utils.DataModule;
import utils.ImageModule;
import linear.algebra.Matrix;
import linear.algebra.Utils;
import linear.algebra.Vector;
import ann.impl.SummationInputFunction;
import ann.interfaces.InputFunction;

public class Main {

	public static void main(String[] args) {


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
		
		double[] input2 = { 1, 5, 9 };
		double[] targets2 = { 0, 1 };
		
		//Network(int inputCount, int hiddenNeuronCount, int outputCount, double learningConstant)
		Network testNet = new Network(3, 3, 2, 5);
		//testNet.setInputWeights(inputWeights);
		//testNet.setHiddenWeights(hiddenWeights);
		
		//testNet.forwardPropagation(new Vector(input));
		
		//System.out.println("initial error: "+testNet.computeTotalError(new Vector(targets)));
		
		
		for (int i=0; i<1000; i++) {
			testNet.forwardPropagation(new Vector(input));
			testNet.backwardPropagation(new Vector(targets));
			//testNet.forwardPropagation(new Vector(input));
			
			testNet.forwardPropagation(new Vector(input2));
			testNet.backwardPropagation(new Vector(targets2));
			//testNet.forwardPropagation(new Vector(input2));
			
		}
		
		testNet.forwardPropagation(new Vector(input));
		
		System.out.println("error after backprop: "+testNet.computeTotalError(new Vector(targets)));
		System.out.println("output 0: "+testNet.getOutput().getElement(0));
		System.out.println("output 1: "+testNet.getOutput().getElement(1));
		System.out.println();
		
		testNet.forwardPropagation(new Vector(input2));
		
		System.out.println("error after backprop: "+testNet.computeTotalError(new Vector(targets2)));
		System.out.println("output 0: "+testNet.getOutput().getElement(0));
		System.out.println("output 1: "+testNet.getOutput().getElement(1));
		System.out.println();		
		
	/*	
		int iCount = DataModule.loadInputCount(path);
		int hCount = DataModule.loadHiddenCount(path);
		int oCount = DataModule.loadOutputCount(path);
	
		double lConst = DataModule.loadLearningConstant(path);
		
		System.out.println("inputs: "+iCount);
		System.out.println("hidden: "+hCount);
		System.out.println("output: "+oCount);
		System.out.println("lConst: "+lConst);
		
		double[][] inputWeights = DataModule.loadInputWeights(path);
		double[][] hiddenWeights = DataModule.loadHiddenWeights(path);
		
		for (int i=0; i<iCount; i++) {
			for (int h=0; h<hCount; h++) {		
				System.out.println(inputWeights[i][h]);
			}
		}
	
		System.out.println();
		
		for (int i=0; i<hCount; i++) {
			for (int h=0; h<oCount; h++) {	
				System.out.println(hiddenWeights[i][h]);
			}
		}
		
		
		
		Network net = new Network(iCount,hCount,oCount,lConst);
		net.setInputWeights(inputWeights);
		net.setHiddenWeights(hiddenWeights);
		
		
		DataModule.saveNetwork(path, net);
	*/
	
		
	/*
		// 10,000 pixel values
		DataModule.setLF();
		File path = DataModule.fileSelectorPop("choose xlsx file", "select", "Data.xlsx",DataModule.BMP_FILE_FILTER);
		System.out.println(path);
		double[] input1 = ImageModule.loadImage(path);
		double[] targets1 = { 1 , 0 };
		
		path = DataModule.fileSelectorPop("choose xlsx file", "select", "Data.xlsx",DataModule.BMP_FILE_FILTER);
		System.out.println(path);
		double[] input2 = ImageModule.loadImage(path);
		double[] targets2 = { 0 , 1 };
		
		for (int i=0; i<input1.length; i++) {
			if(input1[i] == 0.0) {
				input1[i] = 1.0;
			}
			if(input1[i] == 1.0) {
				input1[i] = 0.0;
			}
		}
		for (int i=0; i<input2.length; i++) {
			if(input2[i] == 0.0) {
				input2[i] = 1.0;
			}
			if(input2[i] == 1.0) {
				input2[i] = 0.0;
			}
		}
		
		
		//Network(int inputCount, int hiddenNeuronCount, int outputCount, double learningConstant)
		Network testNet = new Network(10000, 100, 2, .2);
		
		
		for (int i=0; i<100; i++) {
			testNet.forwardPropagation(new Vector(input1));
			testNet.backwardPropagation(new Vector(targets1));
			
			testNet.forwardPropagation(new Vector(input2));
			testNet.backwardPropagation(new Vector(targets2));
		}
		
		
		
		
		testNet.forwardPropagation(new Vector(input1));
		System.out.println("output 0: "+testNet.getOutput().getElement(0));
		System.out.println("output 1: "+testNet.getOutput().getElement(1));
		
		testNet.forwardPropagation(new Vector(input2));
		System.out.println("output 0: "+testNet.getOutput().getElement(0));
		System.out.println("output 1: "+testNet.getOutput().getElement(1));
		
		*/

	}

}
