package ann;

public class Network {
	

	
	private double[]   _input;				// array of input values
	private double[][] _weights;			// array of edge weights. [i][j] is the weight of the edge between nodes i and j
	private Neuron[][] _neurons;			// neuron nodes in the network, node [i][j] is the jth node in the ith layer

	public Network() {
		
	}
	
	// set input array
	public void setInput(double[] input) {
		_input = input;
	}
	
	// set weights
	public void setWeights(double[][] weights) {
		_weights = weights;
	}	
	
	public double[][] getWeights() {
		return _weights;
	}
	
	public double getWeight(int source, int destination) {
		return _weights[source][destination];
	}
	
	public void setWeight(int source, int destination, double weight) {
		_weights[source][destination] = weight;
	}
	
}
