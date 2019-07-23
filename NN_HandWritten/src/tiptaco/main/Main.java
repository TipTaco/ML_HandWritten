package tiptaco.main;

import java.io.IOException;

import tiptaco.image.Dataset;
import tiptaco.image.Display;

public class Main {

	public static void main (String args[]) throws IOException
	{
		
		
		Dataset training = new Dataset("res/t10k-images.idx3-ubyte", "res/t10k-labels.idx1-ubyte");
		Dataset testing = new Dataset("res/train-images.idx3-ubyte", "res/train-labels.idx1-ubyte");
		
		displayImage(training, 100);
	}
	
	public static void displayImage(Dataset ds , int index) throws IOException
	{
		Display disp = new Display(ds.getImages()[index][0]);
		System.out.println(ds.getLabels()[index]);	
	}
	
}
