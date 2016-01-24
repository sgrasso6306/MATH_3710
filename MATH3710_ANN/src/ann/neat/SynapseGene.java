package ann.neat;

public class SynapseGene {
	private int _innovationNumber;
	private boolean _enabled;
	private boolean _recurrent;
	private int _sourceIndex;
	private int _destIndex;
	private double _weight;
	
	public SynapseGene(int innovationNumber, int sourceIndex, int destIndex, double weight, boolean enabled, boolean recurrent) {
		_innovationNumber = innovationNumber;
		_sourceIndex = sourceIndex;
		_destIndex = destIndex;
		_weight = weight;
		_enabled = enabled;
		_recurrent = recurrent;
	}
	
	// copy constructor
	public SynapseGene(SynapseGene sGene) {
		_innovationNumber = sGene.innovationNumber();
		_sourceIndex = sGene.sourceIndex();
		_destIndex = sGene.destIndex();
		_weight = sGene.getWeight();
		_enabled = sGene.getEnabled();
		_recurrent = sGene.getRecurrent();
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
	public boolean getRecurrent() {
		return _recurrent;
	}
	public int innovationNumber() {
		return _innovationNumber;
	}
	public int sourceIndex() {
		return _sourceIndex;
	}
	public int destIndex() {
		return _destIndex;
	}
	
}
