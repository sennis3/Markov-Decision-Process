import java.util.ArrayList;

public class GridWorld {
	
	private int numRows;
	private int numColumns;
	double reward;
	double discountFactor;
	double epsilon;
	double successProb;
	double slipProb;
	private ArrayList<State> stateList = new ArrayList<State>();
	private ArrayList<State> walls = new ArrayList<State>();
	private ArrayList<State> terminalStates = new ArrayList<State>();
	
	
	//GridWorld Constructor
	public GridWorld(int numRows, int numColumns, double reward, double discountFactor, double epsilon, double successProb, double slipProb) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.discountFactor = discountFactor;
		this.reward = reward;
		this.epsilon = epsilon;
		this.successProb = successProb;
		this.slipProb = slipProb;
		
		
		//Fills the stateList ArrayList
		for (int i = 0; i < numRows * numColumns; i++) {
			int rowNum = i / numColumns + 1;
			int colNum = i % numColumns + 1;
			
			State newState = new State(rowNum, colNum, reward);
			stateList.add(newState);
		}
		
		//Set adjacent state variables for state objects
		for(int i = 0; i < stateList.size(); i++) {
			State currState = stateList.get(i);
			int rowNum = currState.getRowNum();
			int colNum = currState.getColNum();
			
			//Sets adjacent state values for the state object
			currState.setUpState(findState(rowNum+1, colNum));
			currState.setDownState(findState(rowNum-1, colNum));
			currState.setLeftState(findState(rowNum, colNum-1));
			currState.setRightState(findState(rowNum, colNum+1));
		}
	}
	
	//Returns the correct state given its row and column number
	public State findState(int rowNum, int colNum) {
		if (rowNum < 1 || rowNum > this.numRows || colNum < 1 || colNum > this.numColumns)
			return null;
		int index = (rowNum-1) * numColumns + colNum - 1;  //Calculates index in array
		return stateList.get(index);
	}
	
	//Adds a wall to the gridworld
	public void addWall(int rowNum, int colNum) {
		State wallState = findState(rowNum, colNum);
		wallState.setIsWall();
		walls.add(wallState);
	}
	
	//Adds a terminal state to the gridworld
	public void addTerminalState(int rowNum, int colNum, double terminalReward) {
		State termState = findState(rowNum, colNum);
		termState.setIsTerminalState(terminalReward);
		terminalStates.add(termState);
	}
	
	//Prints the grid with the utility at each square
	public void printGrid() {
		for (int row = numRows; row > 0; row--) {
			for (int col = 1; col <= numColumns; col++) {
				State currState = findState(row, col);
				
				if(currState.getIsWall()) {
					System.out.print("------------------");
				}
				else {
					System.out.print(currState.getUtility());
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
		System.out.println();
	}
	
	//Prints the final policy of the gridworld
	public void printFinalPolicy() {
		System.out.println("Final Policy:");
		for (int row = numRows; row > 0; row--) {
			for (int col = 1; col <= numColumns; col++) {
				State currState = findState(row, col);
				
				if(currState.getIsWall()) {
					System.out.print("-");
				}
				else {
					//currState.findPolicy();
					System.out.print(currState.getPolicy());
				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	
	//Getters and Setters
	
	public ArrayList<State> getStateList(){
		return stateList;
	}
	
	public double getReward() {
		return reward;
	}
	
	public double getDiscountFactor() {
		return discountFactor;
	}
	
	public double getEpsilon() {
		return epsilon;
	}
	
	public double getSuccessProb() {
		return successProb;
	}
	
	public double getSlipProb() {
		return slipProb;
	}
	
}
