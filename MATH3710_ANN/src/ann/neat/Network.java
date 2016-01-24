package ann.neat;

import java.util.ArrayList;
import java.util.HashMap;

import linear.algebra.Vector;

public class Network {
	 Genome _genome;
	HashMap<Integer,Neuron>	_inputNeurons;
	 HashMap<Integer,Neuron>	_hiddenNeurons;
	 HashMap<Integer,Neuron>	_outputNeurons;
	 HashMap<Integer,Neuron> _allNeurons;
	 SynapseSet				_synapseSet;
	 ArrayList<Integer> _lastFiringSequence;
	double				_lifetimeFitnessSum;
	int					_lifetimeFitnessCount;
	
	
	public Network(Genome genome) {
		_genome = genome;
		_synapseSet = new SynapseSet(genome.getSynapseGeneList());
		_inputNeurons = new HashMap<Integer,Neuron>();
		_hiddenNeurons = new HashMap<Integer,Neuron>();
		_outputNeurons = new HashMap<Integer,Neuron>();
		_allNeurons = new HashMap<Integer,Neuron>();
		_lifetimeFitnessSum = 0.0;
		_lifetimeFitnessCount = 0;
		
		// for all input, hidden, and output neuron genes, construct a corresponding neuron
		for (NeuronGene n : _genome.getInputGeneList()) {
			Neuron newNeuron = new Neuron(n);
			_inputNeurons.put(n.getNeuronIndex(), newNeuron);
			_allNeurons.put(n.getNeuronIndex(), newNeuron);
		}
		for (NeuronGene n : _genome.getHiddenGeneList()) {
			Neuron newNeuron = new Neuron(n);
			_hiddenNeurons.put(n.getNeuronIndex(), newNeuron);
			_allNeurons.put(n.getNeuronIndex(), newNeuron);
		}
		for (NeuronGene n : _genome.getOutputGeneList()) {
			Neuron newNeuron = new Neuron(n);
			_outputNeurons.put(n.getNeuronIndex(), newNeuron);
			_allNeurons.put(n.getNeuronIndex(), newNeuron);
		}
		
		// set all neurons' incoming connection count and list of destination indices	(forward synapses only)
		for (Synapse s : _synapseSet.getForwardSynapsesAsList()) {
			int sourceIndex = s.getSource();
			int destinationIndex = s.getDestination();
			
			Neuron sourceNeuron = _allNeurons.get(sourceIndex);
			Neuron destinationNeuron = _allNeurons.get(destinationIndex);
			
			// destination neuron is added source neuron's destination index
			sourceNeuron.addNeuronToDestinationList(destinationIndex);
			
			// destination neuron's incoming connection count is incremented
			destinationNeuron.incrementIncomingConnections();
		}

		
	}
	
	// reproduction constructor
	public Network(Network parent1, Network parent2) {
		Genome childGenome = new Genome(parent1.getGenome(),parent2.getGenome());
		_genome = childGenome;
		_synapseSet = new SynapseSet(childGenome.getSynapseGeneList());
		_inputNeurons = new HashMap<Integer,Neuron>();
		_hiddenNeurons = new HashMap<Integer,Neuron>();
		_outputNeurons = new HashMap<Integer,Neuron>();
		_allNeurons = new HashMap<Integer,Neuron>();
		_lifetimeFitnessSum = 0.0;
		_lifetimeFitnessCount = 0;
		
		// for all input, hidden, and output neuron genes, construct a corresponding neuron
		for (NeuronGene n : _genome.getInputGeneList()) {
			Neuron newNeuron = new Neuron(n);
			_inputNeurons.put(n.getNeuronIndex(), newNeuron);
			_allNeurons.put(n.getNeuronIndex(), newNeuron);
		}
		for (NeuronGene n : _genome.getHiddenGeneList()) {
			Neuron newNeuron = new Neuron(n);
			_hiddenNeurons.put(n.getNeuronIndex(), newNeuron);
			_allNeurons.put(n.getNeuronIndex(), newNeuron);
		}
		for (NeuronGene n : _genome.getOutputGeneList()) {
			Neuron newNeuron = new Neuron(n);
			_outputNeurons.put(n.getNeuronIndex(), newNeuron);
			_allNeurons.put(n.getNeuronIndex(), newNeuron);
		}
		
		// set all neurons' incoming connection count and list of destination indices	(forward synapses only)
		for (Synapse s : _synapseSet.getForwardSynapsesAsList()) {
			int sourceIndex = s.getSource();
			int destinationIndex = s.getDestination();
			
			Neuron sourceNeuron = _allNeurons.get(sourceIndex);
			Neuron destinationNeuron = _allNeurons.get(destinationIndex);
			
			// destination neuron is added source neuron's destination index
			sourceNeuron.addNeuronToDestinationList(destinationIndex);
			
			// destination neuron's incoming connection count is incremented
			destinationNeuron.incrementIncomingConnections();
		}
	}
	
	// deactivates synapse s, creates new neuron between s's source and destination neurons, connects with two new synapses
	public Neuron addNeuron(Synapse s, int firstInnovationNumber) {
		// genome update
		boolean recurrent = s.getRecurrent();
		int sourceIndex = s.getSource();
		NeuronGene newNeuronGene = _genome.addHiddenNeuronGene();
		int destIndex = s.getDestination();
		SynapseGene oldSynapseGene = _genome.getSynapseGene(s);
		oldSynapseGene.setEnabled(false);
		
		SynapseGene sourceSynapseGene = _genome.addSynapseGene(sourceIndex, newNeuronGene.getNeuronIndex(),firstInnovationNumber, recurrent);
		SynapseGene destSynapseGene = _genome.addSynapseGene(newNeuronGene.getNeuronIndex(), destIndex, firstInnovationNumber+1, recurrent);
		
		// network update
		s.setEnabled(false);
		Neuron newNeuron = new Neuron(newNeuronGene);
		_hiddenNeurons.put(newNeuron.getNeuronIndex(), newNeuron);
		_allNeurons.put(newNeuron.getNeuronIndex(), newNeuron);
		Synapse newSourceSynapse = _synapseSet.addSynapse(sourceSynapseGene);
		Synapse newDestSynapse =_synapseSet.addSynapse(destSynapseGene);
		
		// neuron updates (old source, new neuron)(old destination doesn't need updating)
		Neuron oldSource = _allNeurons.get(sourceIndex);
		oldSource.deleteNeuronfromDestinationList(destIndex);
		oldSource.addNeuronToDestinationList(newNeuronGene.getNeuronIndex());
		
		newNeuron.addNeuronToDestinationList(destIndex);
		newNeuron.incrementIncomingConnections();
		
		
		return newNeuron;
	}
	
	// add new synapse to network and update genome
	public Synapse addSynapse(int sourceIndex, int destIndex, int innovationNumber) {
		boolean recurrent = false;
		for (Integer i : _lastFiringSequence) {
			if (i.equals(sourceIndex)) {
				recurrent = false;
				break;
			}
			if (i.equals(destIndex)) {
				recurrent = true;
				break;
			}
		}
		
		SynapseGene sGene = _genome.addSynapseGene(sourceIndex, destIndex, innovationNumber, recurrent);
		Synapse s = _synapseSet.addSynapse(sGene);
		
		// if not recurrent, update source and destination neurons
		if (!recurrent) {
			Neuron sourceNeuron = _allNeurons.get(sourceIndex);
			Neuron destNeuron = _allNeurons.get(destIndex);
			
			sourceNeuron.addNeuronToDestinationList(destIndex);
			destNeuron.incrementIncomingConnections();
		}
		
		return s;
	}
	
	// change a synapse weight, synchronize genome
	public void updateSynapseWeight(int source, int dest, double newWeight) {
		Synapse s = _synapseSet.getSynapse(source, dest);
		SynapseGene sGene = _genome.getSynapseGene(s);
		
		s.setWeight(newWeight);
		sGene.setWeight(newWeight);
	}
	
	
	// propagates input, returns fitness if given target vector
	public Double propagate(Vector input, Vector targets) {
		
		// first, handle recurrent connections using previous outputs
		ArrayList<Synapse> recurrentSynapses = _synapseSet.getRecurrentSynapsesAsList();
		
		for (Synapse s : recurrentSynapses) {
			Neuron sourceNeuron = _allNeurons.get(s.getSource());
			Neuron destNeuron = _allNeurons.get(s.getDestination());
			
			double signal = sourceNeuron.getNeuronOutput() * s.getWeight();
			
			destNeuron.collectRecurrentSignal(signal);
		}
		
		
		
		ArrayList<Neuron> readyToFire = new ArrayList<Neuron>();
		_lastFiringSequence = new ArrayList<Integer>();
		
		// initialize ready to fire list with input neurons, set input
		int in=0;
		for (Neuron n : _inputNeurons.values()) {
			n.setOutput(input.getElement(in));
			in++;
			readyToFire.add(n);
		}		
		
		// iterate through elements in ready to fire list manually, append new neurons as they become ready to fire
		for (int i=0; i<readyToFire.size(); i++) {
			Neuron firingNeuron = readyToFire.get(i);
			_lastFiringSequence.add(firingNeuron.getNeuronIndex());
			
			// get firing neuron's destination indices
			ArrayList<Integer> destinationIndices = firingNeuron.getDestinationNodes();
			
			// for each destination, deliver this neuron's output * corresponding synapse weight
			for (int j : destinationIndices) {
				Neuron destinationNeuron = _allNeurons.get(j);
				Synapse connectingSynapse = _synapseSet.getSynapse(firingNeuron.getNeuronIndex(), j);
				double signal = firingNeuron.getNeuronOutput() * connectingSynapse.getWeight();
				
				// if true, destination neuron has received all signals and is ready to fire
				if (destinationNeuron.collectSignal(signal)) {
					readyToFire.add(destinationNeuron);
					
					// synchronize neuron gene's output
					NeuronGene destinationNeuronGene = _genome.getNeuronGene(destinationNeuron);
					destinationNeuronGene.setLastNeuronOutput(destinationNeuron.getNeuronOutput());
				}
			}
		}
		
		// at this point, all neurons should have fired and outputs should be computed. Compute raw fitness if target vector supplied
		
		if (targets == null) {
			return null;
		}
		else {
			if (targets.size() != _outputNeurons.size()) {
				return null;
			}
			
			// target vector is valid for outputs, so compute raw fitness
			double squaredErrorSum = 0.0;
			ArrayList<Integer> orderedOutputIndices = _genome.getOrderedOutputList();
			
			for (int i=0; i<targets.size(); i++) {
				double actualOutput = _allNeurons.get(orderedOutputIndices.get(i)).getNeuronOutput();
				double targetOutput = targets.getElement(i);
				squaredErrorSum += (targetOutput - actualOutput) * (targetOutput - actualOutput);
			}
			
			double rawFitness = ((double)targets.size() - squaredErrorSum) / (double)targets.size();
			_lifetimeFitnessSum += rawFitness;
			_lifetimeFitnessCount ++;
			_genome.setLifetimeFitness(getLifetimeFitnessAverage());
			return rawFitness;
		}
		
	}
	
	
	public void updateFiringSequence() {
		ArrayList<Neuron> readyToFire = new ArrayList<Neuron>();
		_lastFiringSequence = new ArrayList<Integer>();
		
		// initialize ready to fire list with input neurons
		for (Neuron n : _inputNeurons.values()) {
			readyToFire.add(n);
		}		
		
		// iterate through elements in ready to fire list manually, append new neurons as they become ready to fire
		for (int i=0; i<readyToFire.size(); i++) {
			Neuron firingNeuron = readyToFire.get(i);
			_lastFiringSequence.add(firingNeuron.getNeuronIndex());
			
			// get firing neuron's destination indices
			ArrayList<Integer> destinationIndices = firingNeuron.getDestinationNodes();
			
			// for each destination, deliver this neuron's output * corresponding synapse weight
			for (int j : destinationIndices) {
				Neuron destinationNeuron = _allNeurons.get(j);
				
				// if true, destination neuron has received all signals and is ready to fire
				if (destinationNeuron.collectSignal(0.0)) {
					readyToFire.add(destinationNeuron);
				}
			}
		}
		
	}
	
	public Genome getGenome() {
		return _genome;
	}
	
	public ArrayList<Synapse> getSynapsesAsList() {
		ArrayList<Synapse> result = new ArrayList<Synapse>();
		result.addAll(_synapseSet.getForwardSynapsesAsList());
		result.addAll(_synapseSet.getRecurrentSynapsesAsList());
		return result;
	}
	
	public ArrayList<Neuron> getInputNeuronsAsList() {
		return new ArrayList<Neuron>(_inputNeurons.values());
	}
	public ArrayList<Neuron> getHiddenNeuronsAsList() {
		return new ArrayList<Neuron>(_hiddenNeurons.values());
	}
	public ArrayList<Neuron> getOutputNeuronsAsList() {
		return new ArrayList<Neuron>(_outputNeurons.values());
	}
	public Neuron getNeuron(int index) {
		return _allNeurons.get(index);
	}
	
	public double getLifetimeFitnessAverage() {
		if (_lifetimeFitnessCount == 0) {
			return 0;
		}
		return _lifetimeFitnessSum / (double)_lifetimeFitnessCount;
	}
	
}
