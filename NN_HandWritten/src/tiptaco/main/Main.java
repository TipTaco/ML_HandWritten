package tiptaco.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import tiptaco.image.Dataset;
import tiptaco.image.Display;
import tiptaco.neuralnet.NetworkHelper;
import tiptaco.neuralnet.NeuralController;
import tiptaco.neuralnet.NeuralHelper;
import tiptaco.neuralnet.NeuralNetwork;
import tiptaco.neuralnet.TrainingSet;

public class Main {

	public static double ETA = 0.1;
	
	public static void main (String args[]) throws IOException
	{
		Dataset training = new Dataset("res/t10k-images.idx3-ubyte", "res/t10k-labels.idx1-ubyte");
		Dataset testing = new Dataset("res/train-images.idx3-ubyte", "res/train-labels.idx1-ubyte");
        
		//displayImage(training, 0);
		
		
		NeuralController nc = new NeuralController(ETA, 28*28, 2500, 300, 36, 10);
		
		TrainingSet train = new TrainingSet(28*28, 10);
		train.addData(training);
		
		TrainingSet test = new TrainingSet(28*28, 10);
		test.addData(testing);
		
		for (int e = 0; e < 1 ; e++) {
			System.out.println("Epoch <<<<< " + e + " >>>>>");
			nc.train(train, 100, 6000);
		}
			
		double MSE = nc.getNeuralNetwork().MSE(test, 1000);
		
		System.out.println("Trained with MSE " + MSE + " error" );
		
		int correct = 0;
		int size = 1000;
		
		ArrayList<int[][]> failed = new ArrayList<int[][]>();
		
		for (int ii = 0 ; ii < size; ii++) {
		
			int gave = testing.getLabels()[ii];
			int got = NetworkHelper.getLargest(nc.getNeuralNetwork().calculate(NetworkHelper.generateInput(testing.getImages()[ii][0])));
			
			System.out.println("gave " + gave + " got " + got);
			
			if (gave == got ) {
				correct++;
			} else {
				failed.add(testing.getImages()[ii][0]);
			}
		
		}
		
		displayFailed(failed);
		
		
		System.out.println("Overall got " + correct + " / " + size); 
		
		/*
		
		NeuralNetwork nn = new NeuralNetwork(28*28, 10, 400);
		
		double[][] input2 = nn.makeInputFromImage(training.getImages()[0][0]);
		double[][] expOutput2 = nn.makeOutputFromLabel(training.getLabels()[0]);
		System.out.println(Arrays.deepToString(input2));
		System.out.println(Arrays.deepToString(expOutput2));
		
		for (int ii = 0 ; ii < training.getImages().length ; ii++) {
			double[][] input = nn.makeInputFromImage(training.getImages()[ii][0]);
			double[][] expOutput = nn.makeOutputFromLabel(training.getLabels()[ii]);
			
			nn.train(input, expOutput);
		}
		
		for (int jj = 0 ; jj < 10 ; jj++) {
		
			System.out.println(" Was " + training.getLabels()[jj] + " got " +  Arrays.deepToString(nn.result(nn.makeInputFromImage(testing.getImages()[jj][0]))) + " ");
		
		}
		
		*/
	}

	
	public static void displayFailed(ArrayList<int[][]> failed) throws IOException {
		Display disp = new Display("Failed");
		
		for (int ii = 0 ; ii < failed.size(); ii++) {
			disp.addImage(failed.get(ii));
		}
	}
	
	/**
	 * display an Image from the selected dataset with the label in console
	 * @param ds
	 * @param index
	 * @throws IOException
	 */
	public static void displayImage(Dataset ds , int index) throws IOException
	{
		Display disp = new Display("Labelled" + index);
		disp.addImage(ds.getImages()[index][0]);
		System.out.println(ds.getLabels()[index]);	
	}
	
}
