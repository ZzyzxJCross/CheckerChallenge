package checkers;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class AIManager {
	static int populationSize;
	static int generationsBeforeCheck;
	static AIIndividual[] population;
	static int[] p1MoveCords;
	static int p1Move;
	static int[] p2MoveCords;
	static int p2Move;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Random rng = new Random();
		System.out.println("Please enter the desired population size. Please enter an even number. ");
		populationSize = Integer.parseInt(in.nextLine());
		System.out.println("population size = " + populationSize);
		System.out.println("Please enter the desired number of generations before the next check. ");
		generationsBeforeCheck = Integer.parseInt(in.nextLine());
		System.out.println("generations before the next check = " + generationsBeforeCheck);
		System.out.println("\nGENERATING GENERATION 1");
		population = new AIIndividual[populationSize];
		for(int i = 0; i < populationSize; i++) {
			double[] weights = new double[1120];
			for(int j = 0; j < weights.length; j++) {
				weights[j] = rng.nextDouble();
			}
			population[i] = new AIIndividual(weights);
		}
		System.out.println("GENERATION 1 GENERATED");
		
		for(int i = 0; i < populationSize - 1; i += 2) {
			p1Move = 0;
			p2Move = 0;
			try{
				Game.playTheGame();
				//handle any communication with the game here
				p1MoveCords = new int[4];
				while(GameBoardPanel.winner == 0) {
					if(GameBoardPanel.p1Move > p1Move) {
						double[] inputNodeVals = new double[36];
						for(int j = 0; j < 32; j++) {
							inputNodeVals[j] = GameBoardPanel.boardSpaceStates[j];
						}
						if(GameBoardPanel.jumpCom) {
							inputNodeVals[32] = 1.0;
						} else {
							inputNodeVals[32] = 0.0;
						}
						inputNodeVals[33] = GameBoardPanel.jumpComX;
						inputNodeVals[34] = GameBoardPanel.jumpComY;
						inputNodeVals[35] = 0;
						double[] outputVals = population[i].generateMove(inputNodeVals);
						for(int j = 0; j < outputVals.length; j++) {
							//p1MoveCords[j] = (int)(outputVals[j] * 128);
							//sample input the AI could provide
							p1MoveCords = new int[] {42,23,54,9};
						}
						System.out.println("about to increment p1Move in manager");
						p1Move++;
						System.out.println("manager's p1Move was incremented to " + p1Move + ". Panel's is " + GameBoardPanel.p1Move);
					}
					if(GameBoardPanel.p2Move > p2Move) {
						double[] inputNodeVals = new double[36];
						for(int j = 0; j < 32; j++) {
							inputNodeVals[j] = GameBoardPanel.boardSpaceStates[j];
						}
						if(GameBoardPanel.jumpCom) {
							inputNodeVals[32] = 1.0;
						} else {
							inputNodeVals[32] = 0.0;
						}
						inputNodeVals[33] = GameBoardPanel.jumpComX;
						inputNodeVals[34] = GameBoardPanel.jumpComY;
						inputNodeVals[35] = 1;
						double[] outputVals = population[i + 1].generateMove(inputNodeVals);
						for(int j = 0; j < outputVals.length; j++) {
							p2MoveCords[j] = (int)(outputVals[j] * 8);
						}
						p2Move++;
					}
				}
			}catch(IOException e) {
				System.out.println("IOEXCEPTION FOUND: " + e);
			}
		}
		
		in.close();
	}

}
