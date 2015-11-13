package linear.algebra;

public class Vector {					// assumed to be column vector

	private double[] _vector;
	
	public Vector(int size) {
		_vector = new double[size];
	}
	
	public Vector(double[] vector) {
		_vector = vector;
	}
	
	
	
	public void setVector(double[] vector) {
		_vector = vector;
	}
	
	public void setElement(int index, double element) {
		_vector[index] = element;
	}
	
	public double getElement(int index) {
		return _vector[index];
	}	
	
	
	
	// get vector dimension
	public int size() {
		return _vector.length;
	}
	
	public void printVector() {
		for (int i=0; i<size(); i++) {
			System.out.println(_vector[i]);
		}
		System.out.println();
	}
	
}
