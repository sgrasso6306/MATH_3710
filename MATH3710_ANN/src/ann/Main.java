package ann;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import org.apache.poi.xssf.eventusermodel.XLSX2CSV;

import utils.DataModule;
import utils.ImageModule;
import utils.MNISTModule;
import linear.algebra.Matrix;
import linear.algebra.Utils;
import linear.algebra.Vector;
import ann.UI.UIFactory;
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
		 * 
		 * // logical AND, only one neuron for this Neuron outputNeuron = new
		 * Neuron(); double[] input = { 1, 1, 0 }; double[] weights = { -30, 20,
		 * 20 };
		 * 
		 * double result = outputNeuron.process(input, weights);
		 * 
		 * System.out.println("result: " + result);
		 */

		// Neuron outputNeuron = new Neuron(); // YES forward propagation seems
		// to be working!!!!!
		/*
		 * 
		 * double[][] inputWeights = new double[3][3]; double[][] hiddenWeights
		 * = new double[3][2];
		 * 
		 * inputWeights[0][0] = 1; // bias inputWeights[0][1] = 0.35;
		 * inputWeights[0][2] = 0.35;
		 * 
		 * inputWeights[1][0] = 1; inputWeights[1][1] = .15; inputWeights[1][2]
		 * = .25;
		 * 
		 * inputWeights[2][0] = 1; inputWeights[2][1] = .2; inputWeights[2][2] =
		 * .3;
		 * 
		 * hiddenWeights[0][0] = .6; // bias hiddenWeights[0][1] = .6;
		 * 
		 * hiddenWeights[1][0] = .4; hiddenWeights[1][1] = .5;
		 * 
		 * hiddenWeights[2][0] = .45; hiddenWeights[2][1] = .55;
		 */

		/*
		 * double[] input = { 1, .05, .1 }; double[] targets = { 1, 0 };
		 * 
		 * double[] input2 = { 1, 5, 9 }; double[] targets2 = { 0, 1 };
		 * 
		 * //Network(int inputCount, int hiddenNeuronCount, int outputCount,
		 * double learningConstant) Network testNet = new Network(3, 3, 2, 5);
		 * //testNet.setInputWeights(inputWeights);
		 * //testNet.setHiddenWeights(hiddenWeights);
		 * 
		 * //testNet.forwardPropagation(new Vector(input));
		 * 
		 * //System.out.println("initial error: "+testNet.computeTotalError(new
		 * Vector(targets)));
		 * 
		 * 
		 * for (int i=0; i<1000; i++) { testNet.forwardPropagation(new
		 * Vector(input)); testNet.backwardPropagation(new Vector(targets));
		 * //testNet.forwardPropagation(new Vector(input));
		 * 
		 * testNet.forwardPropagation(new Vector(input2));
		 * testNet.backwardPropagation(new Vector(targets2));
		 * //testNet.forwardPropagation(new Vector(input2));
		 * 
		 * }
		 * 
		 * testNet.forwardPropagation(new Vector(input));
		 * 
		 * System.out.println("error after backprop: "+testNet.computeTotalError(
		 * new Vector(targets)));
		 * System.out.println("output 0: "+testNet.getOutput().getElement(0));
		 * System.out.println("output 1: "+testNet.getOutput().getElement(1));
		 * System.out.println();
		 * 
		 * testNet.forwardPropagation(new Vector(input2));
		 * 
		 * System.out.println("error after backprop: "+testNet.computeTotalError(
		 * new Vector(targets2)));
		 * System.out.println("output 0: "+testNet.getOutput().getElement(0));
		 * System.out.println("output 1: "+testNet.getOutput().getElement(1));
		 * System.out.println();
		 */

		/*
		 * int iCount = DataModule.loadInputCount(path); int hCount =
		 * DataModule.loadHiddenCount(path); int oCount =
		 * DataModule.loadOutputCount(path);
		 * 
		 * double lConst = DataModule.loadLearningConstant(path);
		 * 
		 * System.out.println("inputs: "+iCount);
		 * System.out.println("hidden: "+hCount);
		 * System.out.println("output: "+oCount);
		 * System.out.println("lConst: "+lConst);
		 * 
		 * double[][] inputWeights = DataModule.loadInputWeights(path);
		 * double[][] hiddenWeights = DataModule.loadHiddenWeights(path);
		 * 
		 * for (int i=0; i<iCount; i++) { for (int h=0; h<hCount; h++) {
		 * System.out.println(inputWeights[i][h]); } }
		 * 
		 * System.out.println();
		 * 
		 * for (int i=0; i<hCount; i++) { for (int h=0; h<oCount; h++) {
		 * System.out.println(hiddenWeights[i][h]); } }
		 * 
		 * 
		 * 
		 * Network net = new Network(iCount,hCount,oCount,lConst);
		 * net.setInputWeights(inputWeights);
		 * net.setHiddenWeights(hiddenWeights);
		 * 
		 * 
		 * DataModule.saveNetwork(path, net);
		 */

		/*
		 * // 10,000 pixel values DataModule.setLF(); File path =
		 * DataModule.fileSelectorPop("choose xlsx file", "select",
		 * "Data.xlsx",DataModule.BMP_FILE_FILTER); System.out.println(path);
		 * double[] input1 = ImageModule.loadImage(path); double[] targets1 = {
		 * 1 , 0 };
		 * 
		 * path = DataModule.fileSelectorPop("choose xlsx file", "select",
		 * "Data.xlsx",DataModule.BMP_FILE_FILTER); System.out.println(path);
		 * double[] input2 = ImageModule.loadImage(path); double[] targets2 = {
		 * 0 , 1 };
		 * 
		 * for (int i=0; i<input1.length; i++) { if(input1[i] == 0.0) {
		 * input1[i] = 1.0; } if(input1[i] == 1.0) { input1[i] = 0.0; } } for
		 * (int i=0; i<input2.length; i++) { if(input2[i] == 0.0) { input2[i] =
		 * 1.0; } if(input2[i] == 1.0) { input2[i] = 0.0; } }
		 * 
		 * 
		 * //Network(int inputCount, int hiddenNeuronCount, int outputCount,
		 * double learningConstant) Network testNet = new Network(10000, 100, 2,
		 * .2);
		 * 
		 * 
		 * for (int i=0; i<100; i++) { testNet.forwardPropagation(new
		 * Vector(input1)); testNet.backwardPropagation(new Vector(targets1));
		 * 
		 * testNet.forwardPropagation(new Vector(input2));
		 * testNet.backwardPropagation(new Vector(targets2)); }
		 * 
		 * 
		 * 
		 * 
		 * testNet.forwardPropagation(new Vector(input1));
		 * System.out.println("output 0: "+testNet.getOutput().getElement(0));
		 * System.out.println("output 1: "+testNet.getOutput().getElement(1));
		 * 
		 * testNet.forwardPropagation(new Vector(input2));
		 * System.out.println("output 0: "+testNet.getOutput().getElement(0));
		 * System.out.println("output 1: "+testNet.getOutput().getElement(1));
		 */

		/*
		 * DataModule.setLF(); File labelPath =
		 * DataModule.fileSelectorPop("choose label file", // load // images //
		 * and // labels "select", "select", -1); File imagePath =
		 * DataModule.fileSelectorPop("choose image file", "select", "select",
		 * -1); File networkPath = DataModule.fileSelectorPop(
		 * "choose network save file", "select", "select", -1);
		 * 
		 * DataSet dataSet = null; try { dataSet =
		 * MNISTModule.buildDataSetFromMNIST(labelPath, imagePath); // create //
		 * data // set // from // MNIST // files } catch (IOException e) {
		 * e.printStackTrace(); }
		 * 
		 * // Network(int inputCount, int hiddenNeuronCount, int outputCount, //
		 * double learningConstant) Network testNet = new
		 * Network(dataSet.featureCount(), 300, // initialize // network
		 * dataSet.outputCount(), 5);
		 * 
		 * long start = System.currentTimeMillis(); for (int i = 0; i <
		 * dataSet.observationCount(); i++) { // train network
		 * testNet.forwardPropagation(new Vector(dataSet.getObservation(i)));
		 * testNet.backwardPropagation(new Vector(dataSet.getOutput(i)));
		 * 
		 * if (i % 10 == 0) { System.out.print("."); } if ((i % 800) == 0) {
		 * System.out.print(" " + i + " / " + dataSet.observationCount()); long
		 * end = System.currentTimeMillis(); long elapsed = end - start; long
		 * minutes = elapsed / (1000 * 60); long seconds = (elapsed / 1000) -
		 * (minutes * 60); System.out.println("  " + minutes + " m " + seconds +
		 * " s "); } }
		 * 
		 * System.out.println(); long end = System.currentTimeMillis(); long
		 * elapsed = end - start; long minutes = elapsed / (1000 * 60); long
		 * seconds = (elapsed / 1000) - (minutes * 60);
		 * System.out.println("processed " + dataSet.observationCount() +
		 * " samples in " + minutes + " m " + seconds + " s ");
		 * 
		 * // DataModule.saveNetwork(networkPath, testNet); // save network try
		 * { FileOutputStream fileOut = new FileOutputStream(networkPath);
		 * ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 * out.writeObject(testNet); out.close(); fileOut.close();
		 * System.out.println("Serialized network is saved in: " +
		 * networkPath.toString()); } catch (IOException i) {
		 * i.printStackTrace(); }
		 * 
		 * Network loadedNet = null; try { FileInputStream fileIn = new
		 * FileInputStream(networkPath); ObjectInputStream in = new
		 * ObjectInputStream(fileIn); loadedNet = (Network) in.readObject();
		 * in.close(); fileIn.close(); } catch (IOException i) {
		 * i.printStackTrace(); } catch (ClassNotFoundException c) {
		 * System.out.println("Network class not found"); c.printStackTrace(); }
		 * 
		 * System.out.println(); System.out.println("test this thing:"); int
		 * testIndex = 2345; System.out.println("label: " +
		 * dataSet.getOutput(testIndex)[0]);
		 * 
		 * testNet.forwardPropagation(new
		 * Vector(dataSet.getObservation(testIndex)));
		 * 
		 * System.out.println("output: "); testNet.getOutput().printVector();
		 */

		
		
		
		
		
		
		
		
		
		
/*		
		
		
		
		
		
		
		
		
		
		DataModule.setLF();
		File networkPath = DataModule.fileSelectorPop(
				"choose network save file", "select", "select", -1);
		File dataSetPath = DataModule.fileSelectorPop(
				"choose dataSet save file", "select", "select", -1);
/*		
		File labelPath = DataModule.fileSelectorPop("choose label file",					// enable for build data
				"select", "select", -1);
		File imagePath = DataModule.fileSelectorPop("choose image file",
				"select", "select", -1);
*/
		DataSet dataSet = null;
/*
		try {																				// build data from MNIST		do this when INPUT_SCALING_FACTOR changes
			dataSet = MNISTModule.buildDataSetFromMNIST(labelPath, imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(dataSetPath);						// save data set
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(dataSet);
			out.close();
			fileOut.close();
			System.out.println("Serialized dataSet is saved in: "
					+ dataSetPath.toString());
		} catch (IOException i) {
			i.printStackTrace();
		}
*/

		/*
		try {																				// load data from file
			FileInputStream fileIn = new FileInputStream(dataSetPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			dataSet = (DataSet) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {												
			System.out.println("DataSet class not found");
			c.printStackTrace();
		}
		
		
/*		
		Network testNet = new Network(dataSet.featureCount(), 50,							// build new network
				dataSet.outputCount(), 10);
*/		
		
/*		
		Network testNet = null;																// load existing network
		try {
			FileInputStream fileIn = new FileInputStream(networkPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			testNet = (Network) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Network class not found");
			c.printStackTrace();
		}
		
		int repeats = 15;
		long start = System.currentTimeMillis();											// train the network
		for (int j=0; j<repeats; j++) {

			for (int i = 0; i < dataSet.observationCount(); i++) { // train network
				
				if ((i % 800) == 0) {
					testNet.forwardPropagation(new Vector(dataSet.getObservation(i)),true);
									
				}
				else {
					testNet.forwardPropagation(new Vector(dataSet.getObservation(i)),false);
				}
				
				testNet.backwardPropagation(new Vector(dataSet.getOutput(i)));	
				
				if (i % 10 == 0) {
					System.out.print(".");
				}
				if ((i % 800) == 0) {
					System.out.print(" " + i + " / " + dataSet.observationCount());
					long end = System.currentTimeMillis();
					long elapsed = end - start;
					long minutes = elapsed / (1000 * 60);
					long seconds = (elapsed / 1000) - (minutes * 60);
					System.out.println("  " + minutes + " m " + seconds + " s ");
				}
			}
			
			try {																// save the network after every training set
				FileOutputStream fileOut = new FileOutputStream(networkPath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(testNet);
				out.close();
				fileOut.close();
				System.out.println("Serialized network is saved in: "
						+ networkPath.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		System.out.println();
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		long minutes = elapsed / (1000 * 60);
		long seconds = (elapsed / 1000) - (minutes * 60);
		System.out.println("processed " + dataSet.observationCount()
				+ " samples in " + minutes + " m " + seconds + " s ");

		/*
		 * Network testNet = null; try { FileInputStream fileIn = new
		 * FileInputStream(networkPath); ObjectInputStream in = new
		 * ObjectInputStream(fileIn); testNet = (Network) in.readObject();
		 * in.close(); fileIn.close(); } catch (IOException i) {
		 * i.printStackTrace(); } catch (ClassNotFoundException c) {
		 * System.out.println("Network class not found"); c.printStackTrace(); }
		 */
/*		
		System.out.println();
		System.out.println("test this thing:");
	    Random rand = new Random();
		int testIndex = rand.nextInt((50000 - 0) + 1) + 0;			// ((max - min) + 1) + min
		System.out.println("label: ");
		new Vector(dataSet.getOutput(testIndex)).printVector();

		System.out.println("");
		System.out.println("input:");

		// new Vector(dataSet.getObservation(testIndex)).printVector();
		testNet.forwardPropagation(new Vector(dataSet.getObservation(testIndex)),true);
		//testNet.backwardPropagation(new Vector(dataSet.getOutput(testIndex)));
		//testNet.forwardPropagation(new Vector(dataSet.getObservation(testIndex)));

		System.out.println("output: ");
		testNet.getOutput().printVector();
*/


		
		
		
		
		
		
		
		
		
		
		  // 900 pixel values 
		UIFactory.setLF(); 
		File imagePath0 = UIFactory.fileSelectorPop("choose image file 0", "select", "select", UIFactory.BMP_FILE_FILTER); 
		double[] input0 = ImageModule.loadImage(imagePath0); 
		double[] targets0 = { 1 , 0 };
		  
		File imagePath1 = UIFactory.fileSelectorPop("choose image file 1", "select", "select", UIFactory.BMP_FILE_FILTER); 
		double[] input1 = ImageModule.loadImage(imagePath1); 
		double[] targets1 = { 0 , 1 };
		
		File networkPath = UIFactory.fileSelectorPop("choose bmpNetwork save file", "select", "select", -1);
		  
		  for (int i=0; i<input1.length; i++) { 
			  if(input1[i] == 0.0) { 
				  input1[i] = 0.00001; 
			  } 
			  if(input1[i] == 1.0) { 
				  input1[i] = 0.0; 
			  } 
		  } 
		  
		  for (int i=0; i<input0.length; i++) { 
			  if(input0[i] == 0.0) { 
				  input0[i] = 0.00001; 
			  } 
			  if(input0[i] == 1.0) { 
				  input0[i] = 0.0; 
			  } 
		  }
		  
		  
		  Network bmpNet;
		  
		  /*
		  //Network(int inputCount, int hiddenNeuronCount, int outputCount, double learningConstant);		// build new network
		  bmpNet = new Network(900, 100, 2, 10);
		 */
		  
		  bmpNet = null;		// load existing network
			try {
				FileInputStream fileIn = new FileInputStream(networkPath);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				bmpNet = (Network) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Network class not found");
				c.printStackTrace();
			}
		  
		  bmpNet.setLearningConstant(100);
		  
		  
		  
		  
		  long start = System.currentTimeMillis();
		  int repeats = 15400000;												// 100000 : 2:17       1100000 : 26:23
		  for (int i=0; i<repeats; i++) { 
			  bmpNet.forwardPropagation(new Vector(input1), false);
			  bmpNet.backwardPropagation(new Vector(targets1));
		  
			  
			  bmpNet.forwardPropagation(new Vector(input0), false
					  );
			  bmpNet.backwardPropagation(new Vector(targets0)); 
			  
				if (i % 10 == 0) {
					System.out.print(".");
				}
				if ((i % 800) == 0) {
					System.out.print(" " + i + " / " + repeats);
					long end = System.currentTimeMillis();
					long elapsed = end - start;
					long minutes = elapsed / (1000 * 60);
					long seconds = (elapsed / 1000) - (minutes * 60);
					System.out.println("  " + minutes + " m " + seconds + " s ");
				}
				if (i % 10000 == 0) {
					try {																// save the network after every training set
						FileOutputStream fileOut = new FileOutputStream(networkPath);
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(bmpNet);
						out.close();
						fileOut.close();
						System.out.println("Serialized network is saved in: "
								+ networkPath.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		  }
		  
		  
		  
		  System.out.println("input 0:");
		  bmpNet.forwardPropagation(new Vector(input0), true);
		  System.out.println("output 0: "+bmpNet.getOutput().getElement(0));
		  System.out.println("output 1: "+bmpNet.getOutput().getElement(1));
		  
		  System.out.println("");
		  
		  System.out.println("input 1:");
		  bmpNet.forwardPropagation(new Vector(input1), true);
		  System.out.println("output 0: "+bmpNet.getOutput().getElement(0));
		  System.out.println("output 1: "+bmpNet.getOutput().getElement(1));
		 
				
		
			try {																// save the network after every training set
				FileOutputStream fileOut = new FileOutputStream(networkPath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(bmpNet);
				out.close();
				fileOut.close();
				System.out.println("Serialized network is saved in: "
						+ networkPath.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		
		
		
		
		
		
		
		
		

	}
}
