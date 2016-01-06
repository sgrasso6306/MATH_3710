package ann.neat;

public class Main {

	public static void main(String[] args) {
		Genome g0 = new Genome(3,2);
		
		Genome g = new Genome(g0);
		
		System.out.println("input indices");
		for (NeuronGene n : g.getInputGeneList()) {
			System.out.println(n.getNeuronIndex() + " "+n.getNeuronType());
		}
		
		System.out.println("hidden indices");
		for (NeuronGene n : g.getHiddenGeneList()) {
			System.out.println(n.getNeuronIndex() + " "+n.getNeuronType());
		}
		
		System.out.println("output indices");
		for (NeuronGene n : g.getOutputGeneList()) {
			System.out.println(n.getNeuronIndex() + " "+n.getNeuronType());
		}
		
		System.out.println("synapses");
		for (SynapseGene n : g.getSynapseGeneList()) {
			System.out.println(n.sourceIndex()+"->"+n.destIndex()+" innovation: "+n.innovationNumber());
		}
		
		
		/*
		Genome h = new Genome(g);
		System.out.println(h.highestInnovationNumber());
		*/
	}

}
