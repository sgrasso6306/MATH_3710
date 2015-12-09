package linear.algebra;

public class Utils {

	
	public static double dotProduct(Vector a, Vector b) {
		// check that vectors are same size
		if (a.size() != b.size()) {
			try {
				throw new Exception("vector sizes do not match!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		double result = 0.0;
		
		for (int i=0; i<a.size(); i++) {
			result += a.getElement(i) * b.getElement(i);
		}
		
		
		return result;
	}
	
	
	
	// terrible, naive matrix multiplication		(should implement strassen or something)
	public static Matrix multiply(Matrix a, Matrix b) {
		double[][] result = new double[a.rowCount()][b.colCount()];
		
		for (int row=0; row<a.rowCount(); row++) {
			for (int col=0; col<b.colCount(); col++) {
				// compute resultant matrix element row,col
				// result[row][col] = a[row] dot b[col]

				result[row][col] = dotProduct(a.getRowVector(row), b.getColVector(col));		
				
			}
		}
		
		
		return new Matrix(result);
	}
	
	public static double logisticFunction(double input) {
		// expm1(x) = e^(x) - 1 				expm1(x) + 1 is a more accurate approximation than exp(x) for results near zero	
		return 1 / (2 + Math.expm1(-input));
	}
	
	

	/*
	public static double normalize(double lowerBound, double upperBound, double input) {									// no no no needs to be the same number of divisions for all data
		double range = upperBound - lowerBound;
		double scaledInput = input;
		
		while (scaledInput > range) {						// no nonononono
			scaledInput = scaledInput / 10.0;
		}
		
		scaledInput = scaledInput + lowerBound;
		
		return scaledInput;
	}
	*/
}
