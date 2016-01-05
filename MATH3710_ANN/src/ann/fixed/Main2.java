package ann.fixed;

import java.io.File;

import javax.swing.SwingUtilities;

import linear.algebra.Utils;
import ann.UI.Controller;
import ann.UI.UI;
import ann.UI.UIFactory;
import utils.DataModule;
import utils.ImageModule;

public class Main2 {

	public static void main(String[] args) {
		
		//File testPath = UIFactory.directorySelectorPop("choose BMP dir", "select", "select"); 

		//DataSet test = ImageModule.buildDataSetFromBMPDir(testPath);
		
		new Controller();
		
/*		
		int testInputCount = 900;
		
		double testNetSum = 600;
		
		double scaleFactor = 1/new Double(testInputCount); 
		
		double result = Utils.logisticFunction(testNetSum*scaleFactor);
		
		System.out.println(result);
*/
	}

}
