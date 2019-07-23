package tiptaco.image;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class Dataset {

	/**
	 * This class handles loading all the images from the MNIST database:
	 * The files for this are scaved into the /res/ working folder of this project, but the code
	 * may be modified later to dynamically download them if required
	 * 
	 * @Author Adrian Shedley
	 */
	
	public static final int IMG_WIDTH = 28, IMG_HEIGHT = 28, IMG_CHANNELS = 1;
	
	// Image offsets for the data at the start
	public static final int IMAGE_OFFSET = 16, LABEL_OFFSET = 8;
	
	// The actual byte array data for each of the images
	private int[][][][] images;
	private int[] labels;
	
	/**
	 * Dataset will load the images and their corresponding labels.
	 * @param imagePath
	 * @param labelPath
	 */
	public Dataset(String imagePath, String labelPath) {
		
		loadImages(imagePath);
		loadLabels(labelPath);
	}
	
	/**
	 * Load the images from the file specified. Error will occur if file doesn't exist
	 * @param path
	 */
	private void loadImages(String path) {
		
		int[] input = loadData(path);
		
		if (input != null) {
			
			int imgSize = IMG_WIDTH * IMG_HEIGHT * IMG_CHANNELS;
			int numImages = (input.length - IMAGE_OFFSET) / imgSize;
						
			images = new int[numImages][IMG_CHANNELS][IMG_WIDTH][IMG_HEIGHT];
			
			for (int ii = 0 ; ii < numImages ; ii++) {
				
				for (int xx = 0 ; xx < IMG_WIDTH ; xx++) {
					for (int yy = 0 ; yy < IMG_HEIGHT ; yy++ ) {
						
						int position = IMAGE_OFFSET + ii * imgSize + yy * IMG_WIDTH + xx;
						
						images[ii][0][xx][yy] = input[position];
					}
				}
			}	
		}
	}
	
	/**
	 * Load the labels for the dataset
	 * @param path
	 */
	private void loadLabels(String path) {
		
		int[] input = loadData(path);
				
		if (input != null) {
			
			int imgSize = 1;
			int numImages = (input.length - LABEL_OFFSET) / imgSize;
					
			labels = new int[numImages];
			
			for (int ii = 0 ; ii < numImages ; ii++) {
				
				labels[ii] = input[ii + LABEL_OFFSET];

			}
		}
	}
	
	/**
	 * Private helper method to load the raw bytes from the file, including any headers
	 * @param path
	 * @return
	 */
	private int[] loadData(String path) {

		File file = new File(path);

		RandomAccessFile rf = null;
		
		ArrayList<int[]> ar = new ArrayList<int[]>();
		
		byte[] output = null;
		int[] out = null;
		try {
			
			//while (true) ar.add((int)rf.read());
			
			//file.rea
			
			//rf = new RandomAccessFile(path, "r");
			//rf.readFully(output);
			

			output = Files.readAllBytes(file.toPath());
			
			out = new int[output.length];
			
			for (int ii = 0 ; ii < output.length ; ii++)
			{
				if (output[ii] < 0) {
					out[ii] = 127 - output[ii]; 
				} else {
					out[ii] = output[ii];
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out;		
	}
	
	public int[][][][] getImages()
	{
		return images;
	}
	
	public int[] getLabels() {
		return labels;
	}
}
