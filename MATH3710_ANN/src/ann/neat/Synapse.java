package ann.neat;

public class Synapse {
	private int _sourceIndex;
	private int _destIndex;
	private double _weight;
	private boolean _enabled;
	private boolean _recurrent;
	
	public Synapse(int source, int dest, double weight, boolean enabled, boolean recurrent) {
		_sourceIndex = source;
		_destIndex = dest;
		_weight = weight;
		_enabled = enabled;
		_recurrent = recurrent;
	}
	
	public int getSource() {
		return _sourceIndex;
	}
	public int getDestination() {
		return _destIndex;
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
	
	public void setWeight(double weight) {
		_weight = weight;
	}
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}
}
