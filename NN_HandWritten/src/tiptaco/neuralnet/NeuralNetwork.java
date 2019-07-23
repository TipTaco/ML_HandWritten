package tiptaco.neuralnet;

import java.util.Arrays;

public class NeuralNetwork {
	
	public static final int OUTPUT_INTERVAL = 500;
	public static final double EPSILON = 0.01;
	
	private int inputSize = 28*28, outputSize = 10, nodes = 50;

    double[][] W1, b1, W2, b2, W3, b3;
	
    int epoch;
    
	public NeuralNetwork(int inputs, int outputs, int hiddens) {
		
		inputSize = inputs;
		outputSize = outputs;
		nodes = hiddens;
		
	    W1 = NeuralHelper.subtract(1.0, NeuralHelper.random(nodes, inputSize));
	    b1 = new double[nodes][1];

	    W2 = NeuralHelper.subtract(1.0, NeuralHelper.random(nodes, nodes));
	    b2 = new double[nodes][1];
	    
	    W3 = NeuralHelper.subtract(1.0, NeuralHelper.random(outputSize, nodes));
	    b3 = new double[outputSize][1];
	    
	    epoch = 0;
	}
	
	public void train(double in[][], double outputExpected[][]) {
		
		double[][] Z1 = NeuralHelper.add(NeuralHelper.dot(W1, in), b1);
        double[][] A1 = NeuralHelper.sigmoid(Z1);
        
        //LAYER 2
        double[][] Z2 = NeuralHelper.add(NeuralHelper.dot(W2, A1), b2);
        double[][] A2 = NeuralHelper.sigmoid(Z2);
    
        //LAYER 3
        double[][] Z3 = NeuralHelper.add(NeuralHelper.dot(W3, A2), b3);
        double[][] A3 = NeuralHelper.sigmoid(Z3);

       // System.out.println("A2 " + A2.length + " , " + A2[0].length);
        
        double cost = NeuralHelper.cross_entropy(outputSize, outputExpected, A3);
        //costs.getData().add(new XYChart.Data(i, cost));
     
        // Back Prop
        //LAYER 2
        double[][] dZ3 = NeuralHelper.subtract(A3, outputExpected);
        double[][] dW3 = NeuralHelper.divide(NeuralHelper.dot(dZ3, NeuralHelper.T(A2)), outputSize);
        double[][] db3 = NeuralHelper.divide(dZ3, outputSize);
        
      //LAYER 2
        double[][] dZ2 = NeuralHelper.multiply(NeuralHelper.dot(NeuralHelper.T(W3), dZ3), NeuralHelper.subtract(1.0, NeuralHelper.power(A2, 2)));
        double[][] dW2 = NeuralHelper.divide(NeuralHelper.dot(dZ2, NeuralHelper.T(A1)), outputSize);
        double[][] db2 = NeuralHelper.divide(dZ2, outputSize);

        //LAYER 1
        double[][] dZ1 = NeuralHelper.multiply(NeuralHelper.dot(NeuralHelper.T(W2), dZ2), NeuralHelper.subtract(1.0, NeuralHelper.power(A1, 2)));
        double[][] dW1 = NeuralHelper.divide(NeuralHelper.dot(dZ1, NeuralHelper.T(in)), outputSize);
        double[][] db1 = NeuralHelper.divide(dZ1, outputSize);

        // G.D
        W1 = NeuralHelper.subtract(W1, NeuralHelper.multiply(EPSILON, dW1));
        b1 = NeuralHelper.subtract(b1, NeuralHelper.multiply(EPSILON, db1));

        W2 = NeuralHelper.subtract(W2, NeuralHelper.multiply(EPSILON, dW2));
        b2 = NeuralHelper.subtract(b2, NeuralHelper.multiply(EPSILON, db2));
        
        W3 = NeuralHelper.subtract(W3, NeuralHelper.multiply(EPSILON, dW3));
        b3 = NeuralHelper.subtract(b3, NeuralHelper.multiply(EPSILON, db3));

        if (epoch % OUTPUT_INTERVAL == 0) {
            System.out.print("==============\n");
            System.out.print("Cost = " + cost);
            System.out.println("\nPredictions = " + Arrays.deepToString(A3));
        }
        
        epoch++;
	}
	
	public double[][] result(double in[][]) {
		double[][] Z1 = NeuralHelper.add(NeuralHelper.dot(W1, in), b1);
        double[][] A1 = NeuralHelper.sigmoid(Z1);

        //LAYER 2
        double[][] Z2 = NeuralHelper.add(NeuralHelper.dot(W2, A1), b2);
        double[][] A2 = NeuralHelper.sigmoid(Z2);
    
        //LAYER 3
        double[][] Z3 = NeuralHelper.add(NeuralHelper.dot(W3, A2), b3);
        double[][] A3 = NeuralHelper.sigmoid(Z3);
        
        return A3;
	}
	
	public double[][] makeOutputFromLabel(int label) {
		
		double labelOut[][] = new double[1][outputSize];
		
		labelOut[0][label] = 1.0;
		
		return NeuralHelper.T(labelOut);
	}
	
	public double[][] makeInputFromImage(int[][] img) {
		double imageOut[][] = new double[1][inputSize];
		
		for (int xx = 0; xx < img.length; xx++) {
			for (int yy = 0 ; yy < img[0].length ; yy++ ) {
				int position = yy * img.length + xx;
				
				imageOut[0][position] = (img[xx][yy] / 255.0 <= 0) ? 0.0 : 1.0;
			}
		}
		
		return  NeuralHelper.T(imageOut);
	}
}
