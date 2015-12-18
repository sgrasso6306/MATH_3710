package ann.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import linear.algebra.Vector;
import utils.ImageModule;
import utils.MNISTModule;
import ann.DataSet;
import ann.Network;

public class Controller {
	private UI			_gui;
	private Network		_openNetwork;
	private File		_networkPath;
	private DataSet		_openDataSet;
	
	public Controller() {
		
		_gui = new UI(this);
		
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            { 
                _gui.createAndDisplayGUI();
            }
        });
	}
	
	public boolean newNetwork() {
		int inputNeurons = UIFactory.intEntryPop("How many input neurons?", "Input Neurons");
		if(inputNeurons == -1) {
			return false;
		}
		int hiddenNeurons = UIFactory.intEntryPop("How many hidden neurons?", "Hidden Neurons");
		if(hiddenNeurons == -1) {
			return false;
		}
		int outputNeurons = UIFactory.intEntryPop("How many output neurons?", "Output Neurons");
		if(outputNeurons == -1) {
			return false;
		}
		double learningConstant = UIFactory.doubleEntryPop("Set initial learning constant", "Learning Constant");
		if(learningConstant == -1) {
			return false;
		}
		
		_openNetwork = new Network(inputNeurons, hiddenNeurons, outputNeurons, learningConstant);
		_networkPath = null;
		_gui.outputPrintln("Initialized new network.");
		_gui.outputPrintln("Input neurons:     "+_openNetwork.getInputCount());
		_gui.outputPrintln("Hidden neurons:    "+_openNetwork.getHiddenCount());
		_gui.outputPrintln("Output neurons:    "+_openNetwork.getOutputCount());
		_gui.outputPrintln("Learning constant: "+_openNetwork.getLearningConstant());
		_gui.outputPrintln("");
		return true;
	}
	
	public boolean openNetwork() {
		File networkPath = UIFactory.fileSelectorPop("Network Path", "Load", "network.ann", UIFactory.ANN_FILE_FILTER);
		if (networkPath == null) {
			return false;
		}
		try {
			FileInputStream fileIn = new FileInputStream(networkPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			_openNetwork = (Network) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			UIFactory.messagePop("Error", "Network load failed!", JOptionPane.WARNING_MESSAGE);
			return false;
		} catch (ClassNotFoundException c) {
			UIFactory.messagePop("Error", "Network load failed, network class not found!", JOptionPane.WARNING_MESSAGE);
			c.printStackTrace();
			return false;
		}
		_networkPath = networkPath;
		_gui.outputPrintln("Loaded network: "+networkPath.getName());
		_gui.outputPrintln("Input neurons:     "+_openNetwork.getInputCount());
		_gui.outputPrintln("Hidden neurons:    "+_openNetwork.getHiddenCount());
		_gui.outputPrintln("Output neurons:    "+_openNetwork.getOutputCount());
		_gui.outputPrintln("Learning constant: "+_openNetwork.getLearningConstant());
		_gui.outputPrintln("");
		return true;
	}
	
	public boolean saveNetworkAs() {
		if(_openNetwork == null) {
			UIFactory.messagePop("Error", "No open network!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		File networkPath = UIFactory.fileSelectorPop("Network Path", "Save", "network.ann", UIFactory.ANN_FILE_FILTER);
		if (!networkPath.toString().endsWith(".ann") && !networkPath.toString().endsWith(".ANN")) {
			networkPath = new File(networkPath.toString()+".ann");
		}
		
		try {			
			FileOutputStream fileOut = new FileOutputStream(networkPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_openNetwork);
			out.close();
			fileOut.close();
			_gui.outputPrintln("Network saved to: "	+ networkPath.toString());
			_gui.outputPrintln("");
			_networkPath = networkPath;
		} catch (IOException e) {
			e.printStackTrace();
			UIFactory.messagePop("Error", "Network save failed!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean saveNetwork() {
		if (_networkPath == null || _openNetwork == null) {
			UIFactory.messagePop("Error", "No open network!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		try {			
			FileOutputStream fileOut = new FileOutputStream(_networkPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_openNetwork);
			out.close();
			fileOut.close();
			_gui.outputPrintln("Network saved to: "	+ _networkPath.toString());
			_gui.outputPrintln("");
		} catch (IOException e) {
			e.printStackTrace();
			UIFactory.messagePop("Error", "Network save failed!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	public boolean setLearningConstant() {
		if (_openNetwork == null) {
			UIFactory.messagePop("Error", "No open network!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		double learningConstant = UIFactory.doubleEntryPop("Enter new learning constant", "Learning Constant");
		if(learningConstant <= 0) {
			UIFactory.messagePop("Nope", "Not a valid learning constant", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		_openNetwork.setLearningConstant(learningConstant);
		_gui.outputPrintln("Learning constant set to: "+_openNetwork.getLearningConstant());										// note: 35000 for 21 mins
		_gui.outputPrintln("");
		return true;
	}
	
	public boolean buildDataSet() {
		String bmpType = "Bitmap Image";
		String MNISTType = "MNIST Data";
		String dataSetType = UIFactory.dropSelectPop( new String[] { bmpType, MNISTType}, "Select data set source type", "Build Data Set");
		
		if (dataSetType.equals(bmpType)) {
			File BMPDir = UIFactory.directorySelectorPop("BMP Dataset", "Select", "BMPDir");
			DataSet ds = ImageModule.buildDataSetFromBMPDir(BMPDir);
			if (ds == null) {
				UIFactory.messagePop("Error", "Dataset build failed!", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			else {
				_openDataSet = ds;
			}
		}
		else if (dataSetType.equals(MNISTType)) {
			File MNISTData = UIFactory.fileSelectorPop("MNIST Data", "Load", "MNISTData", -1);
			File MNISTLabels = UIFactory.fileSelectorPop("MNIST Labels", "Load", "MNISTLabels", -1);
			
			try {		
				_openDataSet = MNISTModule.buildDataSetFromMNIST(MNISTLabels, MNISTData, _gui);
			} catch (IOException e) {
				e.printStackTrace();
				UIFactory.messagePop("Error", "Dataset build failed!", JOptionPane.WARNING_MESSAGE);
				return false;
			}		
		}
		else {
			UIFactory.messagePop("Error", "Dataset build failed!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		_gui.outputPrintln("New dataset constructed:");
		_gui.outputPrintln("Observation count: "+_openDataSet.observationCount());
		_gui.outputPrintln("Input count:     "+_openDataSet.featureCount());
		_gui.outputPrintln("Output count:      "+_openDataSet.outputCount());
		_gui.outputPrintln("");
		return true;
	}
	
	public boolean loadDataSet() {
		File dataSetPath = UIFactory.fileSelectorPop("Data Set", "Load", "dataset.data", UIFactory.DATA_FILE_FILTER);
		if (dataSetPath == null) {
			return false;
		}
		
		try {																				// load data from file
			FileInputStream fileIn = new FileInputStream(dataSetPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			_openDataSet = (DataSet) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			UIFactory.messagePop("Error", "Dataset load failed!", JOptionPane.WARNING_MESSAGE);
			return false;
		} catch (ClassNotFoundException c) {												
			c.printStackTrace();
			UIFactory.messagePop("Error", "Dataset class not found!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		_gui.outputPrintln("Dataset loaded:");
		_gui.outputPrintln("Observation count: "+_openDataSet.observationCount());
		_gui.outputPrintln("Input count:     "+_openDataSet.featureCount());
		_gui.outputPrintln("Output count:      "+_openDataSet.outputCount());
		_gui.outputPrintln("");
		return true;
	}
	
	public boolean saveDataSet() {
		if (_openDataSet == null) {
			UIFactory.messagePop("Error", "No open dataset!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		File dataSetPath = UIFactory.fileSelectorPop("Dataset Path", "Save", "dataset.data", UIFactory.DATA_FILE_FILTER);
		if ((!dataSetPath.toString().endsWith(".data")) && (!dataSetPath.toString().endsWith(".DATA"))) {
			dataSetPath = new File(dataSetPath.toString()+".data");
		}
		
		try {
			FileOutputStream fileOut = new FileOutputStream(dataSetPath);						// save data set
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_openDataSet);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
			UIFactory.messagePop("Error", "Dataset save failed!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		_gui.outputPrintln("Dataset saved to: " + dataSetPath.toString());
		_gui.outputPrintln("");
		return true;
	}
	
	
	public boolean trainNetwork() {
		
		if (_openDataSet == null || _openNetwork == null || _openDataSet.featureCount() != _openNetwork.getInputCount()) {
			UIFactory.messagePop("Error", "Broken!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		int repeats = UIFactory.intEntryPop("Number of repetitions with open data set", "Training");
		long start = System.currentTimeMillis();											// train the network
		for (int j=0; j<repeats; j++) {
			for (int i = 0; i < _openDataSet.observationCount(); i++) { // train network		
				_openNetwork.forwardPropagation(new Vector(_openDataSet.getObservation(i)),false);				
				_openNetwork.backwardPropagation(new Vector(_openDataSet.getOutput(i)));	
				
				if ((i % Math.floor((_openDataSet.observationCount()/250))) == 0) {
					System.out.print(".");
				}
				if ((i % 8000) == 0) {
					
				}
			}
			if ((j % Math.floor((repeats/20))) == 0) {
				System.out.print(" " + j + " / " + repeats);
				long end = System.currentTimeMillis();
				long elapsed = end - start;
				long minutes = elapsed / (1000 * 60);
				long seconds = (elapsed / 1000) - (minutes * 60);
				System.out.println("  " + minutes + " m " + seconds + " s ");
			}

		}
		
		return true;
	}
	
	public boolean computeSingleOutput() {
		if (_openDataSet == null || _openNetwork == null || _openDataSet.featureCount() != _openNetwork.getInputCount()) {
			UIFactory.messagePop("Error", "Broken!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		int index = UIFactory.intEntryPop("Index from dataset to run?", "Compute");
		
		//new Vector(_openDataSet.getOutput(index)).printVector();

		System.out.println("");
		System.out.println("input:");
		
		Vector input = new Vector(_openDataSet.getObservation(index));
		Vector targets = new Vector(_openDataSet.getOutput(index));
		targets.printVector();
		
		_openNetwork.forwardPropagation(input,false);
		
		System.out.println("output: ");
		_openNetwork.getOutput().printVector();
		
		return true;
	}
	
	
	public void printNetworkStats() {
		if (_openNetwork != null) {
			_gui.outputPrintln("Network stats:");
			_gui.outputPrintln("Input neurons:     "+_openNetwork.getInputCount());
			_gui.outputPrintln("Hidden neurons:    "+_openNetwork.getHiddenCount());
			_gui.outputPrintln("Output neurons:    "+_openNetwork.getOutputCount());
			_gui.outputPrintln("Learning constant: "+_openNetwork.getLearningConstant());
			_gui.outputPrintln("");
		}
	}
	
}
