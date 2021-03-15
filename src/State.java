
public class State {
	
	private int rowNum;
	private int colNum;
	private double utility;
	private double prevUtility;
	private State upState;
	private State downState;
	private State leftState;
	private State rightState;
	private boolean wallUp;
	private boolean wallDown;
	private boolean wallLeft;
	private boolean wallRight;
	private boolean isTerminalState;
	private boolean isWall;
	private String policy;
	
	
	//State Constructor
	public State(int rowNum, int colNum, double reward) {
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.utility = reward;
		prevUtility = utility;
		
		wallUp = false;
		wallDown = false;
		wallLeft = false;
		wallRight = false;
		isTerminalState = false;
		isWall = false;
		
		policy = "";
	}
	
	//Calculates and updates the new utility and policy direction of the state - returns change in utility
	public double updateUtilities(double reward, double discountFactor, double successProb, double slipProb) {
		prevUtility = utility; //Keeps track of previous utility so all utilities can be updated simultaneously
		
		
		if (isTerminalState || isWall)
			return 0;
		
		//Finds the max expected utility and direction using Bellman equation
		
		double max = 0;
		double utilityChange = 0;
		
		//Finds expected utility moving up
		double up = bellmanUp(successProb, slipProb);
		max = up;
		policy = "N";
		
		//Finds expected utility moving down
		double down = bellmanDown(successProb, slipProb);
		if (down > max) {
			max = down;
			policy = "S";
		}
			
		//Finds expected utility moving left
		double left = bellmanLeft(successProb, slipProb);
		if (left > max) {
			max = left;
			policy = "W";
		}
		
		//Finds expected utility moving right
		double right = bellmanRight(successProb, slipProb);
		if (right > max) {
			max = right;
			policy = "E";
		}
		
		utility = reward + discountFactor * max; //Completes equation and updates utility
		utilityChange = Math.abs(utility - prevUtility); //Calculates change in utility
		
		return utilityChange;
	}
	
	//Calculates Bellman Equation for moving up
	public double bellmanUp(double successProb, double slipProb) {
		double sum = 0;
		
		//Successful Move
		if (!wallUp)
			sum += successProb * upState.getPrevUtility();
		else  //Bounces of the wall
			sum += successProb * utility;
		
		//Slips Left
		if (!wallLeft)
			sum += slipProb * leftState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		//Slips Right
		if (!wallRight)
			sum += slipProb * rightState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		return sum;
	}
	
	//Calculates Bellman Equation for moving down
	public double bellmanDown(double successProb, double slipProb) {
		double sum = 0;
		
		//Successful Move
		if (!wallDown)
			sum += successProb * downState.getPrevUtility();
		else  //Bounces of the wall
			sum += successProb * utility;
		
		//Slips Left
		if (!wallLeft)
			sum += slipProb * leftState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		//Slips Right
		if (!wallRight)
			sum += slipProb * rightState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		return sum;
	}
	
	//Calculates Bellman Equation for moving left
	public double bellmanLeft(double successProb, double slipProb) {
		double sum = 0;
		
		//Successful Move
		if (!wallLeft)
			sum += successProb * leftState.getPrevUtility();
		else  //Bounces of the wall
			sum += successProb * utility;
		
		//Slips Up
		if (!wallUp)
			sum += slipProb * upState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		//Slips Down
		if (!wallDown)
			sum += slipProb * downState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		return sum;
	}
	
	//Calculates Bellman Equation for moving right
	public double bellmanRight(double successProb, double slipProb) {
		double sum = 0;
		
		//Successful Move
		if (!wallRight)
			sum += successProb * rightState.getPrevUtility();
		else  //Bounces of the wall
			sum += successProb * utility;
		
		//Slips Up
		if (!wallUp)
			sum += slipProb * upState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		//Slips Down
		if (!wallDown)
			sum += slipProb * downState.getPrevUtility();
		else  //Bounces of the wall
			sum += slipProb * utility;
		
		return sum;
	}
	
	
	//Getters and Setters
	
	public int getRowNum() {
		return rowNum;
	}
	
	public int getColNum() {
		return colNum;
	}
	
	public double getUtility() {
		return utility;
	}
	
	public double getPrevUtility() {
		return prevUtility;
	}

	public State getUpState() {
		return upState;
	}
	
	public void setUpState(State upState) {
		this.upState = upState;
		if(upState == null)
			wallUp = true;
	}
	
	public State getDownState() {
		return downState;
	}
	
	public void setDownState(State downState) {
		this.downState = downState;
		if(downState == null)
			wallDown = true;
	}
	
	public State getLeftState() {
		return leftState;
	}
	
	public void setLeftState(State leftState) {
		this.leftState = leftState;
		if(leftState == null)
			wallLeft = true;
	}
	
	public State getRightState() {
		return rightState;
	}
	
	public void setRightState(State rightState) {
		this.rightState = rightState;
		if(rightState == null)
			wallRight = true;
	}
	
	//Also updates adjacent wall booleans
	public void setIsWall() {
		isWall = true;
		
		if (!wallUp)
			upState.setWallDown();
		if (!wallDown)
			downState.setWallUp();
		if (!wallLeft)
			leftState.setWallRight();
		if (!wallRight)
			rightState.setWallLeft();
	}
	
	public boolean getIsWall() {
		return isWall;
	}
	
	public void setWallUp() {
		wallUp = true;
	}
	
	public void setWallDown() {
		wallDown = true;
	}
	
	public void setWallLeft() {
		wallLeft = true;
	}
	
	public void setWallRight() {
		wallRight = true;
	}
	
	public void setIsTerminalState(double reward) {
		isTerminalState = true;
		utility = reward;
		prevUtility = reward;
		policy = "T";
	}
	
	public String getPolicy() {
		return policy;
	}
}
