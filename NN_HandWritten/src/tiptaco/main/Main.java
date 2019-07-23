package tiptaco.main;

import java.io.IOException;
import java.util.Arrays;

import tiptaco.image.Dataset;
import tiptaco.image.Display;
import tiptaco.neuralnet.NN;
import tiptaco.neuralnet.NeuralNetwork;

public class Main {

	public static void main (String args[]) throws IOException
	{
		Dataset training = new Dataset("res/t10k-images.idx3-ubyte", "res/t10k-labels.idx1-ubyte");
		Dataset testing = new Dataset("res/train-images.idx3-ubyte", "res/train-labels.idx1-ubyte");
		
		displayImage(training, 0);
		
		NN nue = new NN(4,1,3,4);
		
		double[] input = new double[] {0.1, 0.2, 0.2, 0.9};
		double[] output = new double[] {0.0, 1.0, 0.0, 0.0};
		
		for (int ii = 0 ; ii < 1000000 ; ii++) {
			
			nue.train(input, output, 0.1);
			
		}
		
		double[] out = nue.calculate(input);
		
		System.out.println(Arrays.toString(out));
		
		
		/*
		NeuralNetwork nn = new NeuralNetwork(28*28, 10, 400);
		
		double[][] input2 = nn.makeInputFromImage(training.getImages()[0][0]);
		double[][] expOutput2 = nn.makeOutputFromLabel(training.getLabels()[0]);
		System.out.println(Arrays.deepToString(input2));
		System.out.println(Arrays.deepToString(expOutput2));
		
		for (int ii = 0 ; ii < training.getImages().length ; ii++) {
			double[][] input = nn.makeInputFromImage(training.getImages()[ii/20][0]);
			double[][] expOutput = nn.makeOutputFromLabel(training.getLabels()[ii/20]);
			
			nn.train(input, expOutput);
		}
		
		for (int ii = 0 ; ii < training.getImages().length ; ii++) {
			double[][] input = nn.makeInputFromImage(training.getImages()[ii/20][0]);
			double[][] expOutput = nn.makeOutputFromLabel(training.getLabels()[ii/20]);
			
			nn.train(input, expOutput);
		}
		
		for (int jj = 0 ; jj < 10 ; jj++) {
		
			System.out.println(" Was " + training.getLabels()[jj] + " got " +  Arrays.deepToString(nn.result(nn.makeInputFromImage(testing.getImages()[jj][0]))) + " ");
		
		}*/
		
	}
	
	/**
	 * display an Image from the selected dataset with the label in console
	 * @param ds
	 * @param index
	 * @throws IOException
	 */
	public static void displayImage(Dataset ds , int index) throws IOException
	{
		Display disp = new Display(ds.getImages()[index][0]);
		System.out.println(ds.getLabels()[index]);	
	}
	
}
