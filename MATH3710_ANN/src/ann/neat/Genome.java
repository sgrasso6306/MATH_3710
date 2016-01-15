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
				_synapseGenes.add(new SynapseGene(innovationNumber, inputNeuron.getNeuronIndex(), outputNeuron.getNeuronIndex(), generator.nextDouble()*weightScale, true, false));
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
	
	// creates new hidden neuron gene assigned next neuron index, increments next neuron index, adds new neuron gene to hidden gene list, returns new neuron gene's index 
	public NeuronGene addHiddenNeuronGene() {
		NeuronGene newNeuron = new NeuronGene(NeuronGene.NeuronType.HIDDEN_NEURON,_nextNeuronIndex);
		_nextNeuronIndex++;
		_hiddenNeuronGenes.add(newNeuron);
		return newNeuron;
	}
	
	
	// add new synapse, generate random weight 
	public SynapseGene addSynapseGene(int sourceIndex, int destIndex, boolean recurrent) {
		double weightScale = 1/new Double(_inputNeuronGenes.size());
		Random generator = new Random();
		
		SynapseGene newSynapseGene = new SynapseGene(nextInnovationNumber(), sourceIndex, destIndex, generator.nextDouble()*weightScale, true, recurrent);
		_synapseGenes.add(newSynapseGene);
		return newSynapseGene;
	}
	
	// get synapse gene corresponding to given synapse
	public SynapseGene getSynapseGene(Synapse s) {
		SynapseGene result = null;
		for (SynapseGene sGene : _synapseGenes) {
			if (sGene.sourceIndex() == s.getSource() && sGene.destIndex() == s.getDestination() && sGene.getWeight() == s.getWeight()) {
				result = sGene;
				break;
			}
		}
		return result;
	}
	
	public SynapseGene updateSynapseGeneWeight(int source, int dest, double newWeight) {
		SynapseGene sGene = null;
		
		for (SynapseGene sg : _synapseGenes) {
			if (source == sg.sourceIndex() && dest == sg.destIndex()) {
				sGene = sg;
				break;
			}
		}
		
		sGene.setWeight(newWeight);
		return sGene;
	}
	
	public NeuronGene getNeuronGene(int neuronIndex) {
		for (NeuronGene n : _inputNeuronGenes) {
			if (n.getNeuronIndex() == neuronIndex) {
				return n;
			}
		}
		for (NeuronGene n : _hiddenNeuronGenes) {
			if (n.getNeuronIndex() == neuronIndex) {
				return n;
			}
		}
		for (NeuronGene n : _outputNeuronGenes) {
			if (n.getNeuronIndex() == neuronIndex) {
				return n;
			}
		}
		return null;
	}
	public NeuronGene getNeuronGene(Neuron n) {
		return getNeuronGene(n.getNeuronIndex());
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
		SynapseGene g = _synapseGenes.get(_synapseGenes.size()-1);
		return g.innovationNumber()+1;
	}
	public int nextNeuronIndex() {
		return _nextNeuronIndex;
	}
}
