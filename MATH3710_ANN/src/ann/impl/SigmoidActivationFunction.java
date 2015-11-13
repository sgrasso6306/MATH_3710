package ann.impl;

import ann.interfaces.ActivationFunction;

public class SigmoidActivationFunction implements ActivationFunction {

	@Override
	public double processActivation(double processedInput) {

		// expm1(x) = e^(x) - 1 				expm1(x) + 1 is a more accurate approximation than exp(x) for results near zero	

		return 1 / (2 + Math.expm1(-processedInput));
	}

}
