package ann.neat;

import java.util.ArrayList;
import java.util.Random;

public class Genome {
	private ArrayList<SynapseGene> _genes;
	
	// initialize genome for minimal starting network
	public Genome(int inputCount, int outputCount) {
		_genes = new ArrayList<SynapseGene>();
		
		int innovationNumber = 1;
				
		// initialize weights randomly
		double weightScale = 1/new Double(inputCount);
		Random generator = new Random();
		
		for (int o=0; o<outputCount; o++) {
			for (int i=0; i<inputCount; i++) {
				_genes.add(new SynapseGene(innovationNumber, i, o, generator.nextDouble()*weightScale, true));
				innovationNumber++;
			}
		}
		
	}
	
	
	// create a clone of the supplied genome
	public Genome(Genome g) {
		_genes = new ArrayList<SynapseGene>();
		for (SynapseGene s : g.getGeneList()) {
			_genes.add(s);
		}
	}
	
	
	
	public ArrayList<SynapseGene> getGeneList() {
		return _genes;
	}
	public int highestInnovationNumber() {
		SynapseGene g = _genes.get(_genes.size()-1);
		return g.innovationNumber();
	}
}
