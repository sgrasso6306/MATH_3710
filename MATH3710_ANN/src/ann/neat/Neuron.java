package ann.neat;

import java.util.ArrayList;

import linear.algebra.Utils;

public class Neuron {
	private double 	_output;						// current output
	//private double 	_lastOutput;					// last output (for recurrent connections)
	private double 	_inputCollector;				// collects incoming signals
	private int		_neuronIndex;
	private int		_incomingSignalCount;			// number of incoming synapses
	private int 	_receivedSignalCount;			// signals received so far during current propagation
	private ArrayList<Integer> _destinationNeurons;	// indices of neurons that receive this neuron's output
	
	public Neuron(NeuronGene n) {
		_neuronIndex = n.getNeuronIndex();
		_output = n.getLastNeuronOutput();
		_inputCollector = 0;
		_incomingSignalCount = 0;							// updated through synapse neuron genes 
		_receivedSignalCount = 0;							
		_destinationNeurons = new ArrayList<Integer>();		// updated through synapse neuron genes
	}
	
	// used by a source neuron to deliver its partial signal to this neuron. If input collection is complete for this neuron, computes output, resets counters, and returns true
	public boolean collectSignal(double signal) {
		_inputCollector += signal;
		_receivedSignalCount += 1;
		
		if (_receivedSignalCount == _incomingSignalCount) {
			computeOutput();
			resetNeuronCounters();
			return true;
		}
		else {
			return false;
		}
	}
	public void collectRecurrentSignal(double signal) {
		_inputCollector += signal;
	}
	
	// called between network propagations, resets signal received count and input collector 
	public void resetNeuronCounters() {
		_inputCollector = 0;
		_receivedSignalCount = 0;
	}	
	
	// uses value from input collector to compute neuron output
	public void computeOutput() {
		_output = Utils.neatLogisticFunction(_inputCollector);
	}
	
	// add a neuron to the list of destination neurons
	public void addNeuronToDestinationList(int destIndex) {
		_destinationNeurons.add(destIndex);
	}
	public void deleteNeuronfromDestinationList(int oldDestIndex) {
		_destinationNeurons.remove((Object)oldDestIndex);
	}
	
	// increment count of incoming connections
	public void incrementIncomingConnections() {
		_incomingSignalCount++;
	}
	public void decrementIncomingConnections() {
		_incomingSignalCount--;
	}
	
	// used by input neurons
	public void setOutput(double newOutput) {
		_output = newOutput;
	}
	
	
	public int getNeuronIndex() {
		return _neuronIndex;
	}
	public double getNeuronOutput() {
		return _output;
	}
	public ArrayList<Integer> getDestinationNodes() {
		return _destinationNeurons;
	}
	
	
}
