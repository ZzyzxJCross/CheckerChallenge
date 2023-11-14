package checkers;

import java.util.Arrays;

//the structure of the neural net is one input node for each of the valid spaces on the checker board
//and one input node for jumping and two more input nodes for the x and y location of the jumping piece.
//also one input node for which side the player is.
//then there are 28 hidden nodes and 4 output nodes(start x, start y, end x, end y)

//36 input nodes times 28 hidden nodes + 28 hidden nodes * 4 output nodes = 1120 weights
public class AIIndividual {
	double[] weights;

	public AIIndividual(double[] weights) {
		super();
		this.weights = weights;
	}
	
	public double[] generateMove(double[] input) {
		//initialize the nodes
		double[] nodes = new double[68];
		Arrays.fill(nodes, 1.0);
		for(int i = 0; i < 36; i++) {
			nodes[i] = input[i];
		}
		//update the hidden nodes
		for(int i = 0; i < 36; i++) {
			for(int j = 36; j < 64; j++) {
				nodes[j] = nodes[j] * nodes[i] * weights[((i * 28) + (j - 36))];
			}
		}
		//update the outputNodes
		for(int i = 36; i < 64; i++) {
			for(int j = 64; j < 68; j++) {
				nodes[j] = nodes[j] * nodes[i] * weights[(((i - 36) * 4) + (j - 64))];
			}
		}
		//package the output
		double[] output = new double[4];
		for(int i = 0; i < 4; i++) {
			output[i] = nodes[i + 64];
		}
		return output;
	}
	

}
