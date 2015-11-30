package ann;

import java.util.Random;

import linear.algebra.Utils;
import linear.algebra.Vector;

public class Network {
	
	private int _inputCount, _hiddenNeuronCount, _outputCount;
	
	// state of NN
	private Vector   		_input, _targetOutput, _hiddenRawOutput, _finalOutput, _errors;
	private WeightSet		_weights;	
	private double			_totalError,_learningConstant;
	
	public Network(int inputCount, int hiddenNeuronCount, int outputCount, double learningConstant) {
		// initialize network
		_input = new Vector(inputCount);
		_hiddenRawOutput = new Vector(hiddenNeuronCount);
		_finalOutput = new Vector(outputCount);

		_inputCount = inputCount;
		_hiddenNeuronCount = hiddenNeuronCount;
		_outputCount = outputCount;
		_learningConstant = learningConstant;
		_weights = new WeightSet(inputCount, hiddenNeuronCount, outputCount);
		
		// initialize weights randomly
		Random generator = new Random();
		for (int i=0; i<inputCount; i++) {
			for (int j=0; j<hiddenNeuronCount; j++) {
				_weights.setWeight(generator.nextDouble(), WeightSet.INPUT_LAYER, i, j);
			}
		}
		for (int i=0; i<hiddenNeuronCount; i++) {
			for (int j=0; j<outputCount; j++) {
				_weights.setWeight(generator.nextDouble(), WeightSet.HIDDEN_LAYER, i, j);
			}
		}
		
	}
	
	public void setInputWeights(double[][] weights) {
		for (int i=0; i<_inputCount; i++) {
			for (int j=0; j<_hiddenNeuronCount; j++) {
				_weights.setWeight(weights[i][j], WeightSet.INPUT_LAYER, i, j);
			}
		}
	}
	public void setHiddenWeights(double[][] weights) {
		for (int i=0; i<_hiddenNeuronCount; i++) {
			for (int j=0; j<_outputCount; j++) {
				_weights.setWeight(weights[i][j], WeightSet.HIDDEN_LAYER, i, j);
			}
		}
	}
	
	
	public void forwardPropagation(Vector input) {
		_input = input;
		
		// update hidden raw outputs (start at index 1 to avoid updating biases)
		Vector inputWeights;
		double netInput, rawOutput;
		_hiddenRawOutput.setElement(0, 1);				// init bias
		for (int i=1; i<_hiddenNeuronCount; i++) {
			inputWeights = _weights.getNeuronInputWeights(WeightSet.INPUT_LAYER, i);		// get all input weights for this neuron
			netInput = Utils.dotProduct(input, inputWeights);								// compute net input
			rawOutput = Utils.logisticFunction(netInput);									// compute neuron output
			_hiddenRawOutput.setElement(i, rawOutput);
			
			//System.out.println("hidden layer netInput: "+netInput);
			//System.out.println("hidden layer rawOutput: "+_hiddenRawOutput.getElement(i));
		}
		
		// update final outputs 
		for (int i=0; i<_outputCount; i++) {
			inputWeights = _weights.getNeuronInputWeights(WeightSet.HIDDEN_LAYER, i);		// get all input weights for this neuron
			netInput = Utils.dotProduct(_hiddenRawOutput, inputWeights);					// compute net input
			rawOutput = Utils.logisticFunction(netInput);									// compute neuron output
			_finalOutput.setElement(i, rawOutput);
			
			//System.out.println("output layer netInput: "+netInput);
			//System.out.println("output layer rawOutput: "+_finalOutput.getElement(i)+ " " + rawOutput);
		}
		
	}

	
	public void backwardPropagation(Vector targets) {
		_targetOutput = targets;
		_errors = new Vector(_outputCount);
		
		// calculate error, save totalError and vector of individual errors
		double totalError = 0;
		for (int i=0; i<_outputCount; i++) {
			double delta = (targets.getElement(i) - _finalOutput.getElement(i));
			double error = .5*delta*delta;
			
			_errors.setElement(i, error);
			totalError += error;
		} 
		_totalError = totalError;
		
		// update hidden layer weights
		for (int i=0; i<_hiddenNeuronCount; i++) {
			double sourceOutput = _hiddenRawOutput.getElement(i);
			
			for (int j=0; j<_outputCount; j++) {
				// weight originating from hidden node i, going to output j
				double currentWeight = _weights.getWeight(WeightSet.HIDDEN_LAYER, i, j);
				
				double finalOutput = _finalOutput.getElement(j);
				double targetOutput = targets.getElement(j);
				
				double weightError = (finalOutput - targetOutput)*(finalOutput)*(1-finalOutput)*(sourceOutput);
			
				double newWeight = currentWeight - (_learningConstant)*(weightError);
				
				_weights.setWeight(newWeight, WeightSet.HIDDEN_LAYER, i, j);
			}
		}
		
		// update input layer weights
		for (int i=0; i<_inputCount; i++) {
			double input = _input.getElement(i);
			
			for (int j=0; j<_hiddenNeuronCount; j++) {
				// weight originating from input i, going to hidden node j
				double currentWeight = _weights.getWeight(WeightSet.INPUT_LAYER, i, j);
				
				double destHiddenOutput = _hiddenRawOutput.getElement(j);
				
				// compute total output error
				double totalOutputError = 0;
				
				for (int k=0; k<_outputCount; k++) {
					double connectingHiddenWeight = _weights.getWeight(WeightSet.HIDDEN_LAYER, j, k);
					double finalOutput = _finalOutput.getElement(k);
					double targetOutput = targets.getElement(k);
					
					double outputError = (finalOutput - targetOutput)*(finalOutput)*(1-finalOutput)*(connectingHiddenWeight);
					totalOutputError += outputError;
				}
				
				
				
				double weightError = (totalOutputError)*(destHiddenOutput)*(1-destHiddenOutput)*(input);
			
				double newWeight = currentWeight - (_learningConstant)*(weightError);
				
				_weights.setWeight(newWeight, WeightSet.INPUT_LAYER, i, j);
			}
		}
	}
	
	
	public double computeTotalError(Vector targets) {
		
		// calculate error, save totalError and vector of individual errors
		double totalError = 0;
		for (int i=0; i<_outputCount; i++) {
			double delta = (targets.getElement(i) - _finalOutput.getElement(i));
			double error = .5*delta*delta;
			
			totalError += error;
		} 
		return totalError;
	}
	
	
	public double getTotalError() {
		return _totalError;
	}
	public Vector getOutput() {
		return _finalOutput;
	}
	
	
	
	
	
	
	
	public int getInputCount() {
		return _inputCount;
	}
	public int getHiddenCount() {
		return _hiddenNeuronCount;
	}	
	public int getOutputCount() {
		return _outputCount;
	}
	public double getLearningConstant() {
		return _learningConstant;
	}
	public WeightSet getWeights() {
		return _weights;
	}
	
	
	
	
	
	
	
	
	
	
}
