package ann.neat;

import java.util.ArrayList;
import java.util.Random;

import linear.algebra.Utils;
import linear.algebra.Vector;
import utils.DataSet;

public class Population {
	private ArrayList<Network>	_networks;
	ArrayList<Species> _species;
	private int _nextInnovationNumber, _fitnessStagnationLimit;
	private double _c1, _c2, _c3, _deltaThreshold, _crossBreedProb, _nonMatingProportion, _addNeuronProb, _addSynapseProb, _weightMutateProb, _uniformPerturbProb, _perturbationRatio;
	
	public Population(int inputCount, int outputCount, int popSize) {
		_c1 = 1.0;
		_c2 = 1.0;
		_c3 = 1.0;
		_deltaThreshold = 3.0;
		_nextInnovationNumber = 0;
		_fitnessStagnationLimit = 15;
		_crossBreedProb = 0.001;
		_nonMatingProportion = 0.25;
		_addNeuronProb = 0.03;
		_addSynapseProb = 0.05;
		_weightMutateProb = 0.8;
		_uniformPerturbProb = 0.9;
		_perturbationRatio = 0.1;
		_species = new ArrayList<Species>();
		
		for (int i=0; i<popSize; i++) {
			_networks.add(new Network(new Genome(inputCount, outputCount)));
		}
		
		_nextInnovationNumber = _networks.get(0).getGenome().nextInnovationNumber();
	}
	
	
	
	public void advanceGeneration(DataSet dataset) {
		
		// determine fitness of population using supplied dataset
		for (int i=0; i<dataset.observationCount(); i++) {
			Vector currentObservation = dataset.getObservationAsVector(i);
			Vector currentTargets = dataset.getOutputAsVector(i);
			
			for (Network n : _networks) {
				n.propagate(currentObservation, currentTargets);
			}
		}
		
		// classify population into species
		ArrayList<Network> assignedNetworks = new ArrayList<Network>();
		// refresh all species (clear all networks except for fittest and random representative. update max fitness and stagnation count. update representative. update next gen count)
		for (Species s : _species) {
			s.refreshSpecies();
			
			for(Network n : s.getNetworkList()) {
				assignedNetworks.add(n);
			}
		}
		
		for (Network n : _networks) {
			if (assignedNetworks.contains(n)) {
				continue;
			}
			
			// assign network to first matching species
			boolean assigned = false;
			for (Species s : _species) {
				Network rep = s.getCurrentRepresentative();
				
				// found matching species
				if (computeCompatibilityDistance(rep,n) < _deltaThreshold) {
					s.addNetworkToSpecies(n);
					assigned = true;
					break;
				}
			}
			
			// no matching species, initialize new species with unmatched network
			if (!assigned) {
				_species.add(new Species(n));				
			}
		}
			
		// create new generation of networks within each species
		for (Species s : _species) {
						
			// if species hasn't improved within stagnation limit, skip species
			if (s.getFitnessStagnation() > _fitnessStagnationLimit) {
				continue;
			}
				
			int remainingNetCount = s.getNextGenCount();
			ArrayList<Network> elites = s.getEliteNetworks();
			
			// set proportion of next generation is a previous elite, possibly with mutation 
			int nonMatingCount = Math.toIntExact(Math.round((double)s.getNextGenCount() * _nonMatingProportion));
			for (int i=0; i<nonMatingCount; i++) {
				Genome nonMatingGenome = new Genome(Utils.selectRandomElement(elites).getGenome());
				Network nonMatingNetwork = new Network(nonMatingGenome);
				
				// set probability of add neuron mutation
				if (Utils.bernoulliTrial(_addNeuronProb)) {
					nonMatingNetwork.addNeuron(Utils.selectRandomElement(nonMatingNetwork.getSynapsesAsList()), _nextInnovationNumber);
					_nextInnovationNumber += 2;
				}
				
				
				
				// set probability of add synapse mutation
				if (Utils.bernoulliTrial(_addSynapseProb)) {
					ArrayList<Neuron> sourceList = new ArrayList<Neuron>();
					sourceList.addAll(nonMatingNetwork.getInputNeuronsAsList());
					sourceList.addAll(nonMatingNetwork.getHiddenNeuronsAsList());
					sourceList.addAll(nonMatingNetwork.getOutputNeuronsAsList());
					
					ArrayList<Neuron> destList = new ArrayList<Neuron>();
					destList.addAll(nonMatingNetwork.getHiddenNeuronsAsList());
					destList.addAll(nonMatingNetwork.getOutputNeuronsAsList());
										
					int sourceIndex = Utils.selectRandomElement(sourceList).getNeuronIndex();
					int destIndex = Utils.selectRandomElement(destList).getNeuronIndex();
					
					nonMatingNetwork.addSynapse(sourceIndex, destIndex, _nextInnovationNumber);
					
					_nextInnovationNumber ++;
				}
				
				s.addNetworkToSpecies(nonMatingNetwork);
				remainingNetCount--;
			}
			
			// each elite has a set probability of reproducing across species
			for (Network n : elites) {
				
				if (Utils.bernoulliTrial(_crossBreedProb)) {
					// select random elite network from random species (other than this one)
					Species crossSpecies = Utils.selectRandomElementExcluding(_species, s);
					Network mateNet = Utils.selectRandomElement(crossSpecies.getNetworkList());
					
					s.addNetworkToSpecies(new Network(n,mateNet));
					remainingNetCount --;
				}
				
				if (remainingNetCount == 0) {
					break;
				}
			}
			
			// populate remaining networks in species with elite reproductions
			while (remainingNetCount > 0) {
				Network parent1 = Utils.selectRandomElement(elites);
				Network parent2 = Utils.selectRandomElementExcluding(elites, parent1);
				
				s.addNetworkToSpecies(new Network(parent1,parent2));
				remainingNetCount --;
			}
			
			
		}
		
		// need to update _networkList with new networks 
		_networks.clear();
		for (Species s : _species) {
			for (Network n : s.getNetworkList()) {
				_networks.add(n);
			}
		}
		
		// all networks in species have a set probability of weight mutation 
		// 80% chance a network would have weights mutated
				// each weight has 90% chance to be uniformly perturbed, 10% chance of new random value
		for (Network n : _networks) {
			if (Utils.bernoulliTrial(_weightMutateProb)) {
				mutateNetworkWeights(n);
			}
		}
		
		
	}
	
	
	
	
	public void mutateNetworkWeights(Network n) {		
		for (Synapse s : n.getSynapsesAsList()) {		
			// perturbation
			if (Utils.bernoulliTrial(_uniformPerturbProb)) {
				double oldWeight = s.getWeight();
				double perturbation = oldWeight * _perturbationRatio;
				if (Utils.bernoulliTrial(0.5)) {
					perturbation = perturbation*-1;
				}
				double newWeight = oldWeight + perturbation;
				
				s.setWeight(newWeight);
			}
			// random weight reassignment
			else {
				int destIndex = s.getDestination();
				int destCount = n.getNeuron(destIndex).getIncomingSignalCount();
				double weightScale = 1.0/destCount;
				
				Random generator = new Random();
				double newWeight = generator.nextDouble() * weightScale;
				
				s.setWeight(newWeight);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public double computeCompatibilityDistance(Network n1, Network n2) {
		double totalWeightDelta = 0.0;
		int disjointCount = 0;
		int excessCount = 0; 
		int matchingWeightCount = 0;

		ArrayList<SynapseGene> n1Synapses = n1.getGenome().getSynapseGeneList();
		ArrayList<SynapseGene> n2Synapses = n2.getGenome().getSynapseGeneList();
		
		int n1Index = 0;
		int n2Index = 0;
		
		while (n1Index < n1Synapses.size() || n2Index < n2Synapses.size()) {
			SynapseGene n1CurrentSynapse = n1Synapses.get(n1Index);
			SynapseGene n2CurrentSynapse = n2Synapses.get(n2Index);
			
			// matching gene (add weight difference to collector, increment matching weight count, pop both)
			if (n1CurrentSynapse.innovationNumber() == n2CurrentSynapse.innovationNumber()) {
				totalWeightDelta += Math.abs(n1CurrentSynapse.getWeight() - n2CurrentSynapse.getWeight());
				matchingWeightCount ++;
				n1Index++;
				n2Index++;
			}
			// disjoint, both still have remaining genes (pop lower innovation# and increment disjoint count)
			else if (n1Index < n1Synapses.size() && n2Index < n2Synapses.size()) {
				if (n1CurrentSynapse.innovationNumber() < n2CurrentSynapse.innovationNumber()) {
					disjointCount++;
					n1Index++;
				}
				else {
					disjointCount++;
					n2Index++;
				}
			}
			// excess, one list is empty
			else {
				if (n1Index < n1Synapses.size()) {	// if n1 still has elements, pop
					n1Index++;
				}
				else {
					n2Index++;
				}
				excessCount++;
			}
		}
		
		int largerGeneCount;
		if (n1Index < n2Index) {
			largerGeneCount = n2Index;
		}
		else {
			largerGeneCount = n1Index;
		}
		
		double avgWeightDelta = totalWeightDelta/(double)matchingWeightCount;
		double compatibilityDistance = (_c1 * (double)excessCount) / (double)largerGeneCount;
		compatibilityDistance += (_c2 * (double)disjointCount) / (double)largerGeneCount;
		compatibilityDistance += _c3 * avgWeightDelta;
		
		return compatibilityDistance;
	}
	
	
	
	
	
	
	
	
	
	
	
}
