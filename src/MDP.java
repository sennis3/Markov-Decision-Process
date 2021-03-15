import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MDP {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//File input and gridworld setup
		GridWorld gridworld = getInput(args);
		
		//Value Iteration
		System.out.println("Iteration 1:");
		gridworld.printGrid();
		valueIteration(gridworld);
		
		//Print final values after convergence
		System.out.println("Final Value After Convergence:");
		gridworld.printGrid();
		
		//Print Policy
		gridworld.printFinalPolicy();
		
	}
	
	//Reads the input from the file and creates the gridworld with these values
	public static GridWorld getInput(String[] args) throws FileNotFoundException {
		//Variables to fill
		int numRows = 0;
		int numCols = 0;
		ArrayList<ArrayList<Integer>> wallStates = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> termStates = new ArrayList<ArrayList<Integer>>();
		double reward = 0;
		double successProb = 0;
		double slipProb = 0;
		double discountFactor = 0;
		double epsilon = 0;
		
		//Read in input from file
		File inputFile = null;
		if (args.length > 0) {
			inputFile = new File(args[0]);
		}
		else {
			System.out.println("Input File Missing");
			System.exit(0);
		}
		
		Scanner scan = new Scanner(inputFile);
		
		//Parses input line by line
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			
			if (!(line.length() == 0 || line.substring(0, 1).equals("#"))) {
				String[] splitLine = line.split(": "); //Splits the numbers from the beginning of the string
				String[] values = splitLine[1].split("[, ]+"); //Puts the numerical values into list
				
				String[] words = line.split(" ");
				
				//Finds correct variable to fill based on keyword
				switch (words[0]) {
				case "size":
					numCols = Integer.parseInt(values[0]);
					numRows = Integer.parseInt(values[1]);
					break;
					
				case "walls":
					for (int i = 0; i < values.length; i+=2) {
						int col = Integer.parseInt(values[i]);
						int row = Integer.parseInt(values[i+1]);
						
						ArrayList<Integer> newWall = new ArrayList<Integer>();
						newWall.add(col);
						newWall.add(row);
						
						wallStates.add(newWall);
					}
					break;
					
				case "terminal_states":
					for (int i = 0; i < values.length; i+=3) {
						int col = Integer.parseInt(values[i]);
						int row = Integer.parseInt(values[i+1]);
						int termReward = Integer.parseInt(values[i+2]);
						
						ArrayList<Integer> newTermState = new ArrayList<Integer>();
						newTermState.add(col);
						newTermState.add(row);
						newTermState.add(termReward);
						
						termStates.add(newTermState);
					}
					break;
					
				case "reward":
					reward = Double.parseDouble(values[0]);
					break;
					
				case "transition_probabilities":
					successProb = Double.parseDouble(values[0]);
					slipProb = Double.parseDouble(values[1]);
					break;
					
				case "discount_rate":
					discountFactor = Double.parseDouble(values[0]);
					break;
					
				case "epsilon":
					epsilon = Double.parseDouble(values[0]);
					break;
				default:
					System.out.println("Input Unrecognized");
				}
			}
		}
		
		//Creates gridworld object using input values
		GridWorld newGridworld = new GridWorld(numRows, numCols, reward, discountFactor, epsilon, successProb, slipProb);
		
		//Sets the walls in gridworld
		for (int i = 0; i < wallStates.size(); i++) {
			ArrayList<Integer> wall = wallStates.get(i);
			newGridworld.addWall(wall.get(1), wall.get(0));
		}
		
		System.out.println();
		System.out.println("Iteration 0:");
		newGridworld.printGrid();
		
		//Sets the terminal states in gridworld
		for (int i = 0; i < termStates.size(); i++) {
			ArrayList<Integer> termState = termStates.get(i);
			newGridworld.addTerminalState(termState.get(1), termState.get(0), termState.get(2));
		}
		return newGridworld;
	}
	
	//Performs value iteration algorithm
	public static void valueIteration(GridWorld gridworld) {
		double epsilon = gridworld.getEpsilon();
		double discountFactor = gridworld.getDiscountFactor();
		
		boolean isConverged = false;
		int count = 2;
		while (!isConverged) { //Iterates until the values have converged
			System.out.println("Iteration " + count + ": ");
			
			ArrayList<State> stateList = gridworld.getStateList();
			double maxChange = 0;
			for (int i = 0; i < stateList.size(); i++) { //Updates the utility for each state
				State currState = stateList.get(i);
				double utilChange = currState.updateUtilities(gridworld.getReward(), gridworld.getDiscountFactor(), gridworld.getSuccessProb(), gridworld.getSlipProb());
				if (utilChange > maxChange)  //Finds the max change in utility for convergence formula
					maxChange = utilChange;
			}
			gridworld.printGrid();
			
			if (maxChange <= (epsilon * (1 - discountFactor) / discountFactor)) { //Convergence formula
				isConverged = true;;
			}
			count++;
		}
	}
	
}
