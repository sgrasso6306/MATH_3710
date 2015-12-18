package ann;

import linear.algebra.Vector;

public class DataSet implements java.io.Serializable{
	private double[][]	_observations;
	private double[][]	_outputs;
	private int 		_observationCount;
	private int			_featureCount;
	private int			_outputCount;
	private int			_currentIndex;
	
	public DataSet(int observationCount, int featureCount, int outputCount) {
		_currentIndex = 0;
		_observationCount = observationCount;
		_featureCount = featureCount;
		_outputCount = outputCount;
		
		_observations = new double[observationCount][featureCount];
		_outputs = new double[observationCount][outputCount];
	}
	
	public boolean addObservation(double[] observation, double[] output) {									// normalize the input
		if (isFull()) {
			System.out.println("Data set full!");
			return false;
		}
		if (observation.length != _featureCount || output.length != _outputCount) {
			System.out.println("Observation not compatable!");
			return false;
		}
		
		_observations[_currentIndex] = observation;
		_outputs[_currentIndex] = output;
		
		_currentIndex++;
		return true;
	}
	
	
	public double[] getObservation(int index) {
		return _observations[index];
	}
	public double[] getOutput(int index) {
		return _outputs[index];
	}	
	
	
	public boolean evaluateClassification(int index, Vector actuals) {
		Vector targets = new Vector(getOutput(index));
		
		// determine index of target output 
		int classifyIndex = -1;
		for (int i=0; i<targets.size(); i++) {
			if (new Double(targets.getElement(i)).equals(1.0)) {
				classifyIndex = i;
				break;
			}
		}
		
		// the actual output corresponding to the target output
		double classifyOutput = actuals.getElement(classifyIndex);
		
		// check whether actual output has highest probability
		for (int i=0; i<actuals.size(); i++) {
			if (i == classifyIndex) {		// make sure to not compare desired actual output against itself!
				continue;
			}
			if (actuals.getElement(i) >= classifyOutput) {		// if another output has higher probability, prediction is false
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
	public int observationCount() {
		return _observationCount;
	}
	public int featureCount() {
		return _featureCount;
	}	
	public int outputCount() {
		return _outputCount;
	}
	
	
	
	
	public boolean isFull() {
		if (_currentIndex < _observationCount) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
}
