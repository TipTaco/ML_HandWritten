package tiptaco.neuralnet;

import java.util.ArrayList;
import java.util.Random;

import tiptaco.image.Dataset;

public class TrainingSet {

	public final int INPUT_SIZE, OUTPUT_SIZE;
	
	private ArrayList<double[][]> data;
	
	public TrainingSet(int input, int output) {
		
		this.INPUT_SIZE = input;
		this.OUTPUT_SIZE = output;
		
		data = new ArrayList<double[][]>();
		
	}

	public void addData(Dataset ds) {
		
		for (int ii = 0 ; ii < ds.getLabels().length; ii++) {
			double[] input = NetworkHelper.generateInput(ds.getImages()[ii][0]);
			double[] target = NetworkHelper.generateTarget(ds.getLabels()[ii], OUTPUT_SIZE);
			
			addData(input, target);
		}
		
	}
	
	public void addData(double[] input, double[] output) {
		
		if (input.length != INPUT_SIZE || output.length != OUTPUT_SIZE) return;
		
		double joinedData[][] = new double[2][];
		
		joinedData[0] = input;
		joinedData[1] = output;
		
		data.add(joinedData);
	}
	
	public ArrayList<double[][]> getBatch(int size) {
		
		ArrayList<double[][]> batch = new ArrayList<double[][]>();
		Random rand = new Random();
		
		for (int ii = 0 ; ii < size; ii++) {
			
			int random = rand.nextInt(data.size());
			
			batch.add(data.get(random));
		}
		
		return batch;
	}
	
	public ArrayList<double[][]> getData() {
		return data;
	}
}
