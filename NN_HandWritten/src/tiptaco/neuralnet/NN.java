package tiptaco.neuralnet;

public class NN {

	public final int[] NETWORK_LAYER_SIZES;
	public final int INPUT_SIZE, OUTPUT_SIZE, NETWORK_SIZE;
	
	private double[][] output;
	private double[][][] weights;
	private double[][] bias;
	
	private double[][] errorSignal;
	private double[][] outputDeriv;
	
	
	public NN (int... networkLayers) {
		
		this.NETWORK_LAYER_SIZES = networkLayers;
		this.NETWORK_SIZE = networkLayers.length;
		
		this.INPUT_SIZE = networkLayers[0];
		this.OUTPUT_SIZE = networkLayers[networkLayers.length - 1];
		

		this.output = new double[NETWORK_SIZE][];
		this.weights = new double[NETWORK_SIZE][][];
		this.bias = new double[NETWORK_SIZE][];
		this.errorSignal = new double[NETWORK_SIZE][];
		this.outputDeriv = new double[NETWORK_SIZE][];
		
		for (int ii = 0 ; ii < NETWORK_SIZE ; ii++) {
			
			this.output[ii] = new double[NETWORK_LAYER_SIZES[ii]];
			this.bias[ii] = NetworkHelper.randomArray(NETWORK_LAYER_SIZES[ii], 0.3, 0.7);
			
			this.errorSignal[ii] = new double[NETWORK_LAYER_SIZES[ii]];
			this.outputDeriv[ii] = new double[NETWORK_LAYER_SIZES[ii]];
			
			if (ii > 0) {
				
				// Create weights for every layer after the input
				this.weights[ii] =  NetworkHelper.randomArray(NETWORK_LAYER_SIZES[ii], NETWORK_LAYER_SIZES[ii-1], -0.3, 0.5);
			}
			
		}
		
	}
	
	public double[] calculate(double... input) {
		
		if (input.length != this.INPUT_SIZE) return null;
		
		this.output[0] = input; // set layer input
		
		for (int layer = 1; layer < NETWORK_SIZE; layer++) {
			for (int neuron = 0 ; neuron < NETWORK_LAYER_SIZES[layer] ; neuron++) {
				
				double x = 0.0;
				
				for (int k = 0 ; k < NETWORK_LAYER_SIZES[layer-1]; k++) {
					x += output[layer-1][k] * weights[layer][neuron][k];
					
				}
				
				x += bias[layer][neuron];
				output[layer][neuron] = sigmoid(x);
				outputDeriv[layer][neuron] = output[layer][neuron] * (1.0 - output[layer][neuron]);
			}
			
		}
		
		return output[NETWORK_SIZE-1]; // return the output 'last' layer
		
	}
	
	
	public void train(double[] input, double target[], double eta) {
		if (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) return;
		
		calculate(input);
		backpropError(target);
		updateWeights(eta);
		
	}
	
	public void backpropError(double[] target) {
		
		for (int neuron = 0 ; neuron < NETWORK_LAYER_SIZES[NETWORK_SIZE-1] ; neuron++) {
			
			errorSignal[NETWORK_SIZE-1][neuron] = (output[NETWORK_SIZE-1][neuron] - target[neuron]) 
					* outputDeriv[NETWORK_SIZE-1][neuron];
		}
		
		for (int layer = NETWORK_SIZE - 2; layer > 0; layer--) {
			for (int neuron = 0 ; neuron < NETWORK_LAYER_SIZES[layer] ; neuron++) {
				
				double sum = 0;
				
				for (int nextNeuron = 0 ; nextNeuron < NETWORK_LAYER_SIZES[layer+1] ; nextNeuron++) {
					sum += weights[layer+1][nextNeuron][neuron]  * errorSignal[layer+1][nextNeuron];
					
				}
				
				this.errorSignal[layer][neuron] = sum * outputDeriv[layer][neuron];
			}
		}
	}
	
	public void updateWeights(double eta) {
		
		for (int layer = 1; layer < NETWORK_SIZE; layer++) {
			for (int neuron = 0 ; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
				
				double delta = - eta * errorSignal[layer][neuron];
				
				for (int prevNeuron = 0 ; prevNeuron < NETWORK_LAYER_SIZES[layer-1] ; prevNeuron++) {

					weights[layer][neuron][prevNeuron] += delta * output[layer-1][prevNeuron];
				}
				
				bias[layer][neuron] += delta;
			}	
		}		
	}
	
	private double sigmoid(double x) {
		return 1.0 / ( 1.0 + Math.exp(-x));
	}
	
}
