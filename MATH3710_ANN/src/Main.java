import ann.impl.SummationInputFunction;
import ann.interfaces.InputFunction;


public class Main {

	public static void main(String[] args) {

		double[] input = {1.0,2.0,3.0,4.0,5.0};
		double[] weights = {1.0,2.0,1.0,1.0,2.0};
		
		InputFunction inF = new SummationInputFunction();
		System.out.println(inF.processInput(input, weights));
		
	}

}
