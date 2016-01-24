package ann.neat;

import java.util.ArrayList;
import java.util.Comparator;

import linear.algebra.Utils;

import org.apache.commons.collections15.buffer.PriorityBuffer;

public class Species {

	PriorityBuffer<Network>		_networkHeap;
	ArrayList<Network>			_networkList;
	Network						_currentRepresentative;
	double						_maxFitness;
	int							_fitnessStagnationCount;
	int 						_nextGenCount;
	double						_eliteRatio;
	
	
	public Species() {
		_networkHeap = new PriorityBuffer<Network>(false, new networkFitnessComparator());
		_networkList = new ArrayList<Network>();
		_eliteRatio = 0.4;
		_maxFitness = 0.0;
		_fitnessStagnationCount = 0;
	}
	
	public Species(Network n) {
		_networkHeap = new PriorityBuffer<Network>(false, new networkFitnessComparator());
		_networkList = new ArrayList<Network>();
		_eliteRatio = 0.4;
		_maxFitness = 0.0;
		_fitnessStagnationCount = 0;
		
		_networkHeap.add(n);
		_networkList.add(n);
		_currentRepresentative = n;
	}	
	
	
	
	public boolean containsNetwork(Network net) {
		for (Network n : _networkList) {
			if (n.equals(net)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	// return top 40% as arraylist
	public ArrayList<Network> getEliteNetworks() {
		
		int eliteCount = Math.toIntExact(Math.round(_networkList.size() * _eliteRatio));
		ArrayList<Network> eliteNetworks = new ArrayList<Network>(eliteCount);
	
		for (int i=0; i<eliteCount; i++) {
			eliteNetworks.add(_networkHeap.remove());
		}
		
		// iterate over elite list to repopulate network heap
		for (Network n : eliteNetworks) {
			_networkHeap.add(n);
		}
		
		return eliteNetworks;
	}
	
	
	
	// determine number of networks in next species gen
	public void updateNextGenCount() {
		
		double totalSharedFitness = 0.0;
		int speciesSize = _networkList.size();
		
		for (Network n : _networkList) {
			totalSharedFitness += (n.getLifetimeFitnessAverage() / (double)speciesSize);
		}
		
		double avgSharedFitness = totalSharedFitness / (double)speciesSize;
		
		_nextGenCount = Math.toIntExact(Math.round(totalSharedFitness / avgSharedFitness));
	}
	
	
	// clear all networks except for fittest and random representative. update max fitness and stagnation count. update representative. update next gen count
	public void refreshSpecies() {
		Network representative = Utils.selectRandomElement(getNetworkList());
		Network fittestNetwork = getFittestNetwork();
		updateNextGenCount();
		
		// update species max fitness
		if (fittestNetwork.getLifetimeFitnessAverage() > _maxFitness) {
			_maxFitness = fittestNetwork.getLifetimeFitnessAverage();
			_fitnessStagnationCount = 0;
		}
		else {
			_fitnessStagnationCount ++;
		}
		
		_networkHeap.clear();
		_networkList.clear();
		
		_networkHeap.add(fittestNetwork);
		_networkHeap.add(representative);
		_networkList.add(fittestNetwork);
		_networkList.add(representative);
		
		_currentRepresentative = representative;
	}
	
	
	
	
	
	
	
	
	public void addNetworkToSpecies(Network n) {
		_networkHeap.add(n);
		_networkList.add(n);
	}
	
	public Network getFittestNetwork() {
		return _networkHeap.get();
	}
	
	public Network getCurrentRepresentative() {
		return _currentRepresentative;
	}
	
	public ArrayList<Network> getNetworkList() {
		return _networkList;
	}
	
	public int getNextGenCount() {
		return _nextGenCount;
	}
	
	public int getFitnessStagnation() {
		return _fitnessStagnationCount;
	}

	
	// network comparator
	private class networkFitnessComparator implements Comparator<Network> {

		@Override
		public int compare(Network net1, Network net2) {			
			double net1Fitness = net1.getLifetimeFitnessAverage();
			double net2Fitness = net2.getLifetimeFitnessAverage();
			
			if (net1Fitness < net2Fitness) {
				return -1;
			}
			else if (net1Fitness > net2Fitness) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
	}
	
}
