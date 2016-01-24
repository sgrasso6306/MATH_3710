package ann.neat;



public class NeuronGene {
	public enum NeuronType {
	    INPUT_NEURON, HIDDEN_NEURON, OUTPUT_NEURON 
	}

	private NeuronType		_neuronType;
	private int 			_neuronIndex;
	private double 			_lastNeuronOutput;		// enables genetic memory, so reconstituted networks with recurrent connections have an initial value
	
	public NeuronGene(NeuronType neuronType, int neuronIndex) {
		_neuronType = neuronType;
		_neuronIndex = neuronIndex;
		_lastNeuronOutput = 0.0;
	}
	
	// copy constructor
	public NeuronGene(NeuronGene nGene) {
		_neuronType = nGene.getNeuronType();
		_neuronIndex = nGene.getNeuronIndex();
		_lastNeuronOutput = nGene.getLastNeuronOutput();
	}
	
	public NeuronType getNeuronType() {
		return _neuronType;
	}
	public int getNeuronIndex()	{
		return _neuronIndex;
	}
	public double getLastNeuronOutput() {
		return _lastNeuronOutput;
	}
	
	public void setLastNeuronOutput(double lastOutput) {
		_lastNeuronOutput = lastOutput;
	}
}
