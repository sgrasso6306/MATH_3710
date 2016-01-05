package ann.fixed;

import linear.algebra.Vector;

public class WeightSet  implements java.io.Serializable{
	public static final int INPUT_LAYER = 0;
	public static final int HIDDEN_LAYER = 1;
	
	private double[][] _inputWeights, _hiddenWeights;
	private int _inputCount, _hiddenNeuronCount, _outputCount; 
	
	public WeightSet(int inputCount, int hiddenNeuronCount, int outputCount) {
		
		_inputWeights = new double[inputCount][hiddenNeuronCount];
		_hiddenWeights = new double[hiddenNeuronCount][outputCount];
		
		_inputCount = inputCount;
		_hiddenNeuronCount = hiddenNeuronCount;
		_outputCount = outputCount;
	}
	
	
	public double getWeight(int layer, int sourceIndex, int destIndex) {
		if (layer == INPUT_LAYER) {
			return _inputWeights[sourceIndex][destIndex];
		}
		else {
			return _hiddenWeights[sourceIndex][destIndex];
		}
	}
	
	public void setWeight(double weight, int layer, int sourceIndex, int destIndex) {
		if (layer == INPUT_LAYER) {
			_inputWeights[sourceIndex][destIndex] = weight;
		}
		else {
			_hiddenWeights[sourceIndex][destIndex] = weight;
		}
	}
	
	
	// get all input weights to a given node as a vector
	public Vector getNeuronInputWeights(int layer, int neuronIndex) {
		Vector result;
		
		if (layer == INPUT_LAYER) {
			result = new Vector(_inputCount);
			for (int i=0; i<_inputCount; i++) {
				result.setElement(i, _inputWeights[i][neuronIndex]);
			}
		}
		else {
			result = new Vector(_hiddenNeuronCount);
			for (int i=0; i<_hiddenNeuronCount; i++) {
				result.setElement(i, _hiddenWeights[i][neuronIndex]);
			}
		}	
		
		return result;
	}
	
	
	
	
	
	
	public int inputCount() {
		return _inputCount;
	}
	public int hiddenNeuronCount() {
		return _hiddenNeuronCount;
	}	
	public int outputCount() {
		return _outputCount;
	}
}
