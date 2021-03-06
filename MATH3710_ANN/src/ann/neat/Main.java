package ann.neat;

import java.util.ArrayList;

import linear.algebra.Utils;
import linear.algebra.Vector;

public class Main {

	public static void main(String[] args) {
		
		/*
		
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
			System.out.println(n.sourceIndex()+"->"+n.destIndex()+" innovation: "+n.innovationNumber()+" weight: "+n.getWeight()+" enabled: "+n.getEnabled());
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
		for (Synapse s : net._synapseSet.getForwardSynapsesAsList()) {
			System.out.println(s.getSource()+"->"+s.getDestination()+"  weight: "+s.getWeight()+" enabled: "+s.getEnabled());
		}		

		Vector input = new Vector(new double[] { 2.5, 5.4, 3.2 });
		net.propagate(input, null);
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
		for (Synapse s : net._synapseSet.getForwardSynapsesAsList()) {
			System.out.println(s.getSource()+"->"+s.getDestination()+"  weight: "+s.getWeight()+" enabled: "+s.getEnabled());
		}			
		
		System.out.println("Firing Sequence:");
		for (int i : net._lastFiringSequence) {
			System.out.println(i);
		}			
		
		
		
		
		
		
		System.out.println("");
		Synapse testSynapse = net._synapseSet.getSynapse(1, 3);
		net.addNeuron(testSynapse);
		
		net.addSynapse(0, 5, false);
		net.addSynapse(4, 5, true);
		
		net.propagate(input, null);
		System.out.println("added neuron between 1->3");
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
		for (Synapse s : net._synapseSet.getForwardSynapsesAsList()) {
			System.out.println(s.getSource()+"->"+s.getDestination()+"  weight: "+s.getWeight()+" enabled: "+s.getEnabled());
		}			
		
		System.out.println("Firing Sequence:");
		for (int i : net._lastFiringSequence) {
			System.out.println(i);
		}
		
		g = net._genome;
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
			System.out.println(n.sourceIndex()+"->"+n.destIndex()+" innovation: "+n.innovationNumber()+" weight: "+n.getWeight()+" enabled: "+n.getEnabled());
		}
		
		*/
		
		Species s = new Species();
		
		Genome g = new Genome(3,2);
		Network n1 = new Network(g);
		Network n2 = new Network(g);
		Network n3 = new Network(g);
		
		n1._lifetimeFitnessSum = 10;
		n1._lifetimeFitnessCount = 1;
		n2._lifetimeFitnessSum = 20;
		n2._lifetimeFitnessCount = 1;
		n3._lifetimeFitnessSum = 3;
		n3._lifetimeFitnessCount = 1;
		
		
		s.addNetworkToSpecies(n1);
		s.addNetworkToSpecies(n2);
		s.addNetworkToSpecies(n3);
		
		
		
	}

}
