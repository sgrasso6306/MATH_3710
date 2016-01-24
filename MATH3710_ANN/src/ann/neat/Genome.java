package ann.neat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import linear.algebra.Utils;

public class Genome {
	private ArrayList<SynapseGene>	_synapseGenes;
	private ArrayList<NeuronGene>	_inputNeuronGenes;
	private ArrayList<NeuronGene>	_hiddenNeuronGenes;
	private ArrayList<NeuronGene>	_outputNeuronGenes;
	private ArrayList<Integer> 		_outputOrderedIndexList; 
	private int 					_nextNeuronIndex;
	private double 					_lifetimeFitnessAverage;
	
	// initialize genome for minimal starting network
	public Genome(int inputCount, int outputCount) {
		_nextNeuronIndex = 0;
		_synapseGenes = new ArrayList<SynapseGene>();
		_inputNeuronGenes = new ArrayList<NeuronGene>();
		_hiddenNeuronGenes = new ArrayList<NeuronGene>();
		_outputNeuronGenes = new ArrayList<NeuronGene>();
		_outputOrderedIndexList = new ArrayList<Integer>();
		_lifetimeFitnessAverage = 0.0;
		
		// generate input and output neuron genes
		for (int i=0; i<inputCount; i++) {
			_inputNeuronGenes.add(new NeuronGene(NeuronGene.NeuronType.INPUT_NEURON,_nextNeuronIndex));
			_nextNeuronIndex++;
		}
		for (int i=0; i<outputCount; i++) {
			_outputNeuronGenes.add(new NeuronGene(NeuronGene.NeuronType.OUTPUT_NEURON,_nextNeuronIndex));
			_nextNeuronIndex++;
		}	
		
		// build ordered list of output indices
		for (NeuronGene nGene : _outputNeuronGenes) {
			_outputOrderedIndexList.add(nGene.getNeuronIndex());
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
		_nextNeuronIndex = g.nextNeuronIndex();
		_synapseGenes = new ArrayList<SynapseGene>();
		_inputNeuronGenes = new ArrayList<NeuronGene>();
		_hiddenNeuronGenes = new ArrayList<NeuronGene>();
		_outputNeuronGenes = new ArrayList<NeuronGene>();
		_outputOrderedIndexList = new ArrayList<Integer>();
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
		
		// build ordered list of output indices
		for (NeuronGene nGene : _outputNeuronGenes) {
			_outputOrderedIndexList.add(nGene.getNeuronIndex());
		}
		
		_lifetimeFitnessAverage = g.getLifetimeFitness();
	}
	
	// reproduction constructor
	public Genome(Genome parentOne, Genome parentTwo) {
		_nextNeuronIndex = 0;
		_synapseGenes = new ArrayList<SynapseGene>();
		_inputNeuronGenes = new ArrayList<NeuronGene>();
		_hiddenNeuronGenes = new ArrayList<NeuronGene>();
		_outputNeuronGenes = new ArrayList<NeuronGene>();
		_outputOrderedIndexList = new ArrayList<Integer>();
		_lifetimeFitnessAverage = 0.0;
		
		ArrayList<SynapseGene> p1Synapses = parentOne.getSynapseGeneList();
		ArrayList<SynapseGene> p2Synapses = parentTwo.getSynapseGeneList();		
		
		double p1Fitness = parentOne.getLifetimeFitness();
		double p2Fitness = parentTwo.getLifetimeFitness();
		int p1Index = 0;
		int p2Index = 0;
		
		Genome fitterParent = null;
		if (p1Fitness > p2Fitness) {
			fitterParent = parentOne;
		}
		else if (p2Fitness > p1Fitness) {
			fitterParent = parentTwo;
		}
		else {
			if (Utils.bernoulliTrial(.5)) {
				fitterParent = parentOne;
			}
			else {
				fitterParent = parentTwo;
			}
		}
		
		// construct child synapse genes
		while (p1Index < p1Synapses.size() || p2Index < p2Synapses.size()) {
			SynapseGene p1CurrentSynapseGene = p1Synapses.get(p1Index);
			SynapseGene p2CurrentSynapseGene = p2Synapses.get(p2Index);
			
			// matching gene, inherited randomly
			if (p1CurrentSynapseGene.innovationNumber() == p2CurrentSynapseGene.innovationNumber()) {
				if (Utils.bernoulliTrial(.5)) {
					_synapseGenes.add(new SynapseGene(p1CurrentSynapseGene));
				}
				else {
					_synapseGenes.add(new SynapseGene(p2CurrentSynapseGene));
				}
				
				p1Index++;
				p2Index++;
			}
			// disjoint, both still have remaining genes. Only inherit from more fit parent
			else if (p1Index < p1Synapses.size() && p2Index < p2Synapses.size()) {
				if (p1CurrentSynapseGene.innovationNumber() < p2CurrentSynapseGene.innovationNumber()) {		// disjoint genes belong to p1, add to child if p1 more fit
					if (p1Fitness > p2Fitness) {
						_synapseGenes.add(new SynapseGene(p1CurrentSynapseGene));
					}
					p1Index++;
				}
				else {																							// disjoint genes belong to p2, add to child if p2 more fit
					if (p2Fitness > p1Fitness) {
						_synapseGenes.add(new SynapseGene(p2CurrentSynapseGene));
					}
					p2Index++;
				}
			}
			// excess, one list is empty
			else {
				if (p1Index < p1Synapses.size()) {	// if n1 still has elements, pop
					if (p1Fitness > p2Fitness) {
						_synapseGenes.add(new SynapseGene(p1CurrentSynapseGene));
					}
					p1Index++;
				}
				else {
					if (p2Fitness > p1Fitness) {
						_synapseGenes.add(new SynapseGene(p2CurrentSynapseGene));
					}
					p2Index++;
				}
			}
		}
		
		// determine next neuron index
		for (SynapseGene sGene : _synapseGenes) {
			if (sGene.sourceIndex() >= _nextNeuronIndex) {
				_nextNeuronIndex = sGene.sourceIndex() + 1;
			}
			if (sGene.destIndex() >= _nextNeuronIndex) {
				_nextNeuronIndex = sGene.destIndex() + 1;
			}
		}
		
		// keep track of handled indices 
		HashSet<Integer> indexBin = new HashSet<Integer>();
		
		// input and output neuron genes differ only by last output, use fitter parent
		
		if (p1Fitness > p2Fitness) {
			for (NeuronGene n : parentOne.getInputGeneList()) {
				_inputNeuronGenes.add(new NeuronGene(n));
				indexBin.add(n.getNeuronIndex());
			}
			for (NeuronGene n : parentOne.getOutputGeneList()) {
				_outputNeuronGenes.add(new NeuronGene(n));
				indexBin.add(n.getNeuronIndex());
			}
		}
		else {
			for (NeuronGene n : parentTwo.getInputGeneList()) {
				_inputNeuronGenes.add(new NeuronGene(n));
				indexBin.add(n.getNeuronIndex());
			}
			for (NeuronGene n : parentTwo.getOutputGeneList()) {
				_outputNeuronGenes.add(new NeuronGene(n));
				indexBin.add(n.getNeuronIndex());
			}
		}
		// build ordered list of output indices
		for (NeuronGene nGene : _outputNeuronGenes) {
			_outputOrderedIndexList.add(nGene.getNeuronIndex());
		}
		
		
		// handle hidden neurons by examining synapse genes, make collection of indices
		for (SynapseGene sGene : _synapseGenes) {
			if (!indexBin.contains(sGene.sourceIndex())) {
				NeuronGene parentNeuronGene = fitterParent.getNeuronGene(sGene.sourceIndex()); //neuron will always exist in fitter parent
				_hiddenNeuronGenes.add(new NeuronGene(parentNeuronGene));
				indexBin.add(sGene.sourceIndex());
			}
			if (!indexBin.contains(sGene.destIndex())) {
				NeuronGene parentNeuronGene = fitterParent.getNeuronGene(sGene.destIndex()); //neuron will always exist in fitter parent
				_hiddenNeuronGenes.add(new NeuronGene(parentNeuronGene));
				indexBin.add(sGene.destIndex());
			}
		}
	}
	
	

	
	// creates new hidden neuron gene assigned next neuron index, increments next neuron index, adds new neuron gene to hidden gene list, returns new neuron gene's index 
	public NeuronGene addHiddenNeuronGene() {
		NeuronGene newNeuron = new NeuronGene(NeuronGene.NeuronType.HIDDEN_NEURON,_nextNeuronIndex);
		_nextNeuronIndex++;
		_hiddenNeuronGenes.add(newNeuron);
		return newNeuron;
	}
	
	
	// add new synapse, generate random weight 
	public SynapseGene addSynapseGene(int sourceIndex, int destIndex, int innovationNumber, boolean recurrent) {
		double weightScale = 1/new Double(_inputNeuronGenes.size());
		Random generator = new Random();
		
		SynapseGene newSynapseGene = new SynapseGene(innovationNumber, sourceIndex, destIndex, generator.nextDouble()*weightScale, true, recurrent);
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
	
	public void setLifetimeFitness(double newLifetimeFitness) {
		_lifetimeFitnessAverage = newLifetimeFitness;
	}
	public double getLifetimeFitness() {
		return _lifetimeFitnessAverage;
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
	
																			// this must be handled globally, on the population scope. use only to determine initial innovation # count
	public int nextInnovationNumber() {
		SynapseGene g = _synapseGenes.get(_synapseGenes.size()-1);
		return g.innovationNumber()+1;
	}
	
	public int nextNeuronIndex() {
		return _nextNeuronIndex;
	}
	
	public ArrayList<Integer> getOrderedOutputList() {
		return _outputOrderedIndexList;
	}
	

}
