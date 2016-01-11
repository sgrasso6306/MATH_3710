package ann.neat;

import java.util.ArrayList;
import java.util.HashMap;

public class Network {
	private Genome _genome;
	private HashMap<Integer,Neuron>	_inputNeurons;
	private HashMap<Integer,Neuron>	_hiddenNeurons;
	private HashMap<Integer,Neuron>	_outputNeurons;
	private HashMap<Integer,Neuron> _allNeurons;
	private SynapseSet				_synapseSet;
	
	public Network(Genome genome) {
		_genome = genome;
		_synapseSet = new SynapseSet(genome.getSynapseGeneList());
		
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
		
		// set all neurons' incoming connection count and list of destination indices
		for (Synapse s: _synapseSet.getAllSynapsesAsList()) {
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
	
	
	
	
	
	public void propagate() {
		ArrayList<Neuron> readyToFire = new ArrayList<Neuron>();
		
		// initialize ready to fire list with input neurons
		for (Neuron n : _inputNeurons.values()) {
			readyToFire.add(n);
		}		
		
		// iterate through elements in ready to fire list manually, append new neurons as they become ready to fire
		for (int i=0; i<readyToFire.size(); i++) {
			Neuron firingNeuron = readyToFire.get(i);
			
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
				}
			}
		}
		
		// at this point, all neurons should have fired and outputs should be computed
		
	}
	
}
