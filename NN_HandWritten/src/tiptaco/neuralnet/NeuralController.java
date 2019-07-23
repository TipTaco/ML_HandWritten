package tiptaco.neuralnet;

import java.util.ArrayList;

public class NeuralController {

	private NN network;
	private double eta; 
	
	public NeuralController(double eta, int... size) {
		this.eta = eta;
		
		network = new NN(size);
	}
	
	public void train(TrainingSet ts, int iterations, int batchSize, double eta) {
		
		for (int ii = 0 ; ii < iterations ; ii++ ) {
			
			ArrayList<double[][]> batch = ts.getBatch(batchSize);
			
			for (double[][] data : batch) {
				
				network.train(data[0], data[1], eta);
			}
			
			System.out.println("Iteration " + ii + " " + network.MSE(ts, batchSize));
		}
	}
	
	public void train(TrainingSet ts, int iterations, int batchSize) {
		train (ts, iterations, batchSize, eta);
	}
	
	public double evaluate(TrainingSet ts, int batchSize) {
		
		double sumEMS = 0.0;
		
		ArrayList<double[][]> batch = ts.getBatch(batchSize);
		
		for (double[][] data : batch) {
			
			double output[] = network.calculate(data[0]);

			sumEMS += doEMS(output, data[0]);
		}
		
		return sumEMS / (double)batchSize;
	}
	
	private double doEMS(double[] input, double[] target) {
		
		double sum = 0.0;
		
		for (int ii = 0 ; ii < input.length ; ii++) {
			
			sum += Math.pow(input[ii] - target[ii], 2.0);
		}
		
		return sum / 2.0;
	}
	
	public NN getNeuralNetwork() { return network; }
}
