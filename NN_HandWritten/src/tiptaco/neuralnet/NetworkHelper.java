package tiptaco.neuralnet;

public class NetworkHelper {

	public static double[][] randomArray(int sizeX, int sizeY, double lower, double upper) {
		
		double output[][] = new double[sizeX][];
		
		for (int ii = 0 ; ii < sizeX; ii++) {
			
			output[ii] = randomArray(sizeY, lower, upper);
			
		}
		
		return output;
	}
	
	public static double[] randomArray(int size, double lower, double upper) {
		
		double output[] = new double[size];
		
		for (int ii = 0 ; ii < size; ii++) {
			
			output[ii] = lower + Math.random()*(upper-lower);
			
		}
		
		return output;
	}
	
}
