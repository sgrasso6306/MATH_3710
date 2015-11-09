package ann.impl;
import ann.interfaces.InputFunction;

public class SummationInputFunction implements InputFunction {

	@Override
	public double processInput(double[] input, double[] weights) {

		// check that a weight exists for each input
		if (input.length != weights.length) {
			try {
				throw new Exception("inputs and weights do not match!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		double result = 0.0;
		
		// result is the summation of all input values multiplied by their corresponding weights
		for (int i=0; i<input.length; i++) {
			result += (input[i]*weights[i]);
		}
		
		return result;
	}

}
