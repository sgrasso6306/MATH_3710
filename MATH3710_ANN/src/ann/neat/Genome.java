package ann.neat;

import java.util.ArrayList;
import java.util.Random;

public class Genome {
	private ArrayList<SynapseGene>	_synapseGenes;
	private ArrayList<NeuronGene>	_inputNeuronGenes;
	private ArrayList<NeuronGene>	_hiddenNeuronGenes;
	private ArrayList<NeuronGene>	_outputNeuronGenes;
	private int _nextNeuronIndex;
	
	// initialize genome for minimal starting network
	public Genome(int inputCount, int outputCount) {
		_nextNeuronIndex = 0;
		_synapseGenes = new ArrayList<SynapseGene>();
		_inputNeuronGenes = new ArrayList<NeuronGene>();
		_hiddenNeuronGenes = new ArrayList<NeuronGene>();
		_outputNeuronGenes = new ArrayList<NeuronGene>();
		
		// generate input and output neuron genes
		for (int i=0; i<inputCount; i++) {
			_inputNeuronGenes.add(new NeuronGene(NeuronGene.NeuronType.INPUT_NEURON,_nextNeuronIndex));
			_nextNeuronIndex++;
		}
		for (int i=0; i<outputCount; i++) {
			_outputNeuronGenes.add(new NeuronGene(NeuronGene.NeuronType.OUTPUT_NEURON,_nextNeuronIndex));
			_nextNeuronIndex++;
		}	
		
		
		
		int innovationNumber = 1;
				
		// random weight scaling
		double weightScale = 1/new Double(inputCount);
		Random generator = new Random();
		
		// add initial synapse connections
		for (NeuronGene outputNeuron : _outputNeuronGenes) {
			for (NeuronGene inputNeuron : _inputNeuronGenes) {
				_synapseGenes.add(new SynapseGene(innovationNumber, inputNeuron.getNeuronIndex(), outputNeuron.getNeuronIndex(), generator.nextDouble()*weightScale, true));
				innovationNumber++;
			}
		}
		
	}
	
	
	// create a clone of the supplied genome
	public Genome(Genome g) {
		_synapseGenes = new ArrayList<SynapseGene>();
		_inputNeuronGenes = new ArrayList<NeuronGene>();
		_hiddenNeuronGenes = new ArrayList<NeuronGene>();
		_outputNeuronGenes = new ArrayList<NeuronGene>();
		for (SynapseGene s : g.getSynapseGeneList()) {
			_synapseGenes.add(s);
		}
		for (NeuronGene n : g.getInputGeneList()) {
			_inputNeuronGenes.add(n);
		}
		for (NeuronGene n : g.getHiddenGeneList()) {
			_hiddenNeuronGenes.add(n);
		}
		for (NeuronGene n : g.getOutputGeneList()) {
			_outputNeuronGenes.add(n);
		}
		
		_nextNeuronIndex = g.nextNeuronIndex();
	}
	
	
	
	public ArrayList<SynapseGene> getSynapseGeneList() {
		return _synapseGenes;
	}
	public ArrayList<NeuronGene> getInputGeneList() {
		return _inputNeuronGenes;
	}
	public ArrayList<NeuronGene> getHiddenGeneList() {
		return _hiddenNeuronGenes;
	}
	public ArrayList<NeuronGene> getOutputGeneList() {
		return _outputNeuronGenes;
	}
	public int nextInnovationNumber() {
		SynapseGene g = _synapseGenes.get(_synapseGenes.size());
		return g.innovationNumber();
	}
	public int nextNeuronIndex() {
		return _nextNeuronIndex;
	}
}
