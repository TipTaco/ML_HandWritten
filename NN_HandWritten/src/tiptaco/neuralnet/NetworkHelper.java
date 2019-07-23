package tiptaco.neuralnet;

import java.lang.annotation.Target;

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
	
	public static double[] generateInput(int[][] img) {
		
		double outputImg[] = new double[img.length * img[0].length];
		
		for (int xx = 0 ; xx < img.length ; xx++) {
			for (int yy = 0 ; yy < img[0].length ; yy++) {
				
				int position = yy * img.length + xx;
				
				outputImg[position] = (img[xx][yy] / 255.0);				
			}
		}
		
		return outputImg;
	}
	
	public static double[] generateTarget(int target, int length) {
		
		double outputTarget[] = new double[length];
		
		outputTarget[target] = 1.0;
		
		return outputTarget;		
	}
	
	public static int getLargest(double[] A) {
		
		double largest = Double.MIN_VALUE;
		int largestID = 0;
		
		for (int ii = 0 ; ii < A.length ; ii++) {
			if (A[ii] > largest ) {
				largest = A[ii];
				largestID = ii;
			}
		}
		
		return largestID;
	}
}
