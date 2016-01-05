package ann.fixed;
import ann.impl.SigmoidActivationFunction;
import ann.impl.SummationInputFunction;
import ann.interfaces.ActivationFunction;
import ann.interfaces.InputFunction;


public class Neuron {
	
	private InputFunction _inputFunction;
	private ActivationFunction _activationFunction;
	
	public Neuron(InputFunction inputFunction, ActivationFunction activationFunction) {
		_inputFunction = inputFunction;
		_activationFunction = activationFunction;
	}
	
	// default constructor using default summation input function and default sigmoid activation function
	public Neuron() {
		_inputFunction = new SummationInputFunction();
		_activationFunction = new SigmoidActivationFunction();
	}
	
	public double process(double[] input, double[] weights) {
		
		// give input data and corresponding weights to input function
		double inputFunctionResult = _inputFunction.processInput(input, weights);
		
		// give input result to activation function
		double outputResult = _activationFunction.processActivation(inputFunctionResult);
		
		return outputResult;
	}
	
	
}
