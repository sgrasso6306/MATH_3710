package ann.neat;

import java.util.ArrayList;
import java.util.HashMap;

public class SynapseSet {

	private HashMap<Integer,ArrayList<Synapse>>		_synapseMap;			// index by source neuron, corresponding value is list of all synapses originating from source neuron
	private ArrayList<Synapse>						_synapseList;						
	
	public SynapseSet() {
		_synapseMap = new HashMap<Integer,ArrayList<Synapse>>();
		_synapseList = new ArrayList<Synapse>();
	}
	
	// constructor allowing all synapse genes to be passed at once
	public SynapseSet(ArrayList<SynapseGene> synapseGenes) {
		_synapseMap = new HashMap<Integer,ArrayList<Synapse>>();
		_synapseList = new ArrayList<Synapse>();
		for (SynapseGene s : synapseGenes) {
			addSynapse(s.sourceIndex(),s.destIndex(),s.getWeight(),s.getEnabled());
		}
	}
	
	public void addSynapse(int source, int dest, double weight, boolean enabled) {
		// create new synapse
		Synapse newSynapse = new Synapse(source, dest, weight, enabled);
		
		// if the hashmap doesn't have an entry for this source node, create one
		if (!_synapseMap.containsKey(source)) {
			ArrayList<Synapse> sList = new ArrayList<Synapse>();
			sList.add(newSynapse);
			_synapseMap.put(source, sList);
		}
		// if hashmap already has list for this source node, add new synapse to the list
		else {
			ArrayList<Synapse> sList = _synapseMap.get(source);
			sList.add(newSynapse);
		}
		
		// add new synapse to list
		_synapseList.add(newSynapse);
	}
	
	public Synapse getSynapse(int source, int dest) {
		ArrayList<Synapse> sList = _synapseMap.get(source);
		Synapse result = null;
		
		for (Synapse s : sList) {
			if (s.getDestination() == dest) {
				result = s;
			}
		}
		
		return result;
	}
	
	public ArrayList<Synapse> getSynapsesForSourceNeuron(int source) {
		return _synapseMap.get(source);
	}
	
	public ArrayList<Synapse> getAllSynapsesAsList() {
		return _synapseList;
	}
}
