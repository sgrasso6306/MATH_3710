package ann.neat;

import java.util.ArrayList;
import java.util.HashMap;

public class SynapseSet {

	private HashMap<Integer,ArrayList<Synapse>>		_synapseMap;			// index by source neuron, corresponding value is list of all synapses originating from source neuron
	private ArrayList<Synapse>						_forwardSynapseList;
	private ArrayList<Synapse>						_recurrentSynapseList;
	
	public SynapseSet() {
		_synapseMap = new HashMap<Integer,ArrayList<Synapse>>();
		_forwardSynapseList = new ArrayList<Synapse>();
		_recurrentSynapseList = new ArrayList<Synapse>();
	}
	
	// constructor allowing all synapse genes to be passed at once
	public SynapseSet(ArrayList<SynapseGene> synapseGenes) {
		_synapseMap = new HashMap<Integer,ArrayList<Synapse>>();
		_forwardSynapseList = new ArrayList<Synapse>();
		_recurrentSynapseList = new ArrayList<Synapse>();
		for (SynapseGene s : synapseGenes) {
			addSynapse(s.sourceIndex(),s.destIndex(),s.getWeight(),s.getEnabled(),s.getRecurrent());
		}
	}
	
	public Synapse addSynapse(int source, int dest, double weight, boolean enabled, boolean recurrent) {
		// create new synapse
		Synapse newSynapse = new Synapse(source, dest, weight, enabled, recurrent);
		
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
		
		// if recurrent, add to recurrent synapse list. Else, add to forward synapse list
		if (recurrent) {
			_recurrentSynapseList.add(newSynapse);
		}
		else {
			_forwardSynapseList.add(newSynapse);
		}
				
		return newSynapse;
	}
	
	public Synapse addSynapse(SynapseGene sGene) {
		return addSynapse(sGene.sourceIndex(),sGene.destIndex(),sGene.getWeight(),sGene.getEnabled(),sGene.getRecurrent());
	}
	
	
	public Synapse updateSynapseWeight(int source, int dest, double newWeight) {
		Synapse s = getSynapse(source, dest);
		s.setWeight(newWeight);
		return s;
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
	
	public ArrayList<Synapse> getForwardSynapsesAsList() {
		return _forwardSynapseList;
	}
	public ArrayList<Synapse> getRecurrentSynapsesAsList() {
		return _recurrentSynapseList;
	}
}
