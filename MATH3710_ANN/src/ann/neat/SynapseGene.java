package ann.neat;

public class SynapseGene {
	private int _innovationNumber;
	private boolean _enabled;
	private int _sourceIndex;
	private int _destIndex;
	private double _weight;
	
	public SynapseGene(int innovationNumber, int sourceIndex, int destIndex, double weight, boolean enabled) {
		_innovationNumber = innovationNumber;
		_sourceIndex = sourceIndex;
		_destIndex = destIndex;
		_weight = weight;
		_enabled = enabled;
	}
	
	public void setWeight(double newWeight) {
		_weight = newWeight;
	}
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}
	
	public double getWeight() {
		return _weight;
	}
	public boolean getEnabled() {
		return _enabled;
	}
	
}
