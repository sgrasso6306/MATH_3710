package ann.neat;

import linear.algebra.Vector;

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
			System.out.println(n.sourceIndex()+"->"+n.destIndex()+" innovation: "+n.innovationNumber()+" weight: "+n.getWeight());
		}
		
		
		Network net = new Network(g);
		System.out.println("network:");
		System.out.println("Inputs");
		for (Neuron n : net._inputNeurons.values()) {
			System.out.println(n.getNeuronIndex() + " Output:"+n.getNeuronOutput());
		}		
		System.out.println("Hidden:");
		for (Neuron n : net._hiddenNeurons.values()) {
			System.out.println(n.getNeuronIndex() + " Output:"+n.getNeuronOutput());
		}				
		System.out.println("Output:");
		for (Neuron n : net._outputNeurons.values()) {
			System.out.println(n.getNeuronIndex() + " Output:"+n.getNeuronOutput());
		}		
		System.out.println("synapses:");
		for (Synapse s : net._synapseSet.getAllSynapsesAsList()) {
			System.out.println(s.getSource()+"->"+s.getDestination()+"  weight: "+s.getWeight());
		}		

		Vector input = new Vector(new double[] { 2.5, 5.4, 3.2 });
		net.propagate(input);
		System.out.println();
		System.out.println("propagated");
		System.out.println("Inputs");
		for (Neuron n : net._inputNeurons.values()) {
			System.out.println(n.getNeuronIndex() + " Output:"+n.getNeuronOutput());
		}		
		System.out.println("Hidden:");
		for (Neuron n : net._hiddenNeurons.values()) {
			System.out.println(n.getNeuronIndex() + " Output:"+n.getNeuronOutput());
		}				
		System.out.println("Output:");
		for (Neuron n : net._outputNeurons.values()) {
			System.out.println(n.getNeuronIndex() + " Output:"+n.getNeuronOutput());
		}		
		System.out.println("synapses:");
		for (Synapse s : net._synapseSet.getAllSynapsesAsList()) {
			System.out.println(s.getSource()+"->"+s.getDestination()+"  weight: "+s.getWeight());
		}			
		
	}

}
