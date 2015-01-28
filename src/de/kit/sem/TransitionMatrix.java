package de.kit.sem;

public class TransitionMatrix {

	public static double[] arrivals = {0.265, 0.405, 0.01, 0.11, 0.11, 0.06, 0.01, 0.01, 0.01, 0.005, 0.005};
	public static double[] workers = {0.1,0.1,0.5,0.3};
	
	private double[][][][] transitionMatrix; 
	private int[][][][] workersForTransition; //for cost calculation
	
	public TransitionMatrix() {
		//all +1 to include state 0 
		this.transitionMatrix = new double[CarRentalModelApplication.busStopCapacity+1][CarRentalModelApplication.waitingRoomCapacity+1][CarRentalModelApplication.busStopCapacity+1][CarRentalModelApplication.waitingRoomCapacity+1];
		this.workersForTransition = new int[CarRentalModelApplication.busStopCapacity+1][CarRentalModelApplication.waitingRoomCapacity+1][CarRentalModelApplication.busStopCapacity+1][CarRentalModelApplication.waitingRoomCapacity+1];

		calculateTransitionMatrixEntries();
	}

		
	private void calculateTransitionMatrixEntries() {
		// iterate over rows
		for (int currentAtBusStop = 0; currentAtBusStop <= CarRentalModelApplication.busStopCapacity; currentAtBusStop++) {
			for (int currentAtWaitingRoom = 0; currentAtWaitingRoom <= CarRentalModelApplication.waitingRoomCapacity; currentAtWaitingRoom++) {
				iterateOverColumns(currentAtBusStop, currentAtWaitingRoom);
			}
		}
	}
	
	private void iterateOverColumns(int currentAtBusStop,
			int currentAtWaitingRoom) {
		// iterate over columns
		for (int targetAtBusStop = 0; targetAtBusStop <= CarRentalModelApplication.busStopCapacity; targetAtBusStop++) {
			for (int targetAtWaitingRoom = 0; targetAtWaitingRoom <= CarRentalModelApplication.waitingRoomCapacity; targetAtWaitingRoom++) {
				calcTransitionElement(currentAtBusStop, currentAtWaitingRoom,
						targetAtBusStop, targetAtWaitingRoom);
				
			}
		}
	}

	private void calcTransitionElement(int currentAtBusStop,
			int currentAtWaitingRoom, int targetAtBusStop,
			int targetAtWaitingRoom) {
		//calculate transfer, numberOfWorkers, numberOfArival needed for transition
		int transfer = calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfWorkers = calcNumberOfWorkers(currentAtBusStop,
				currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		int numberOfArrival = calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);

		double prob = calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop,
				targetAtWaitingRoom, numberOfWorkers, numberOfArrival, transfer);	
		//update elements in transition and worker matrix
		setTransitionElement(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, prob);
		setWorkersForTransitionElement(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers);
	}

	public double calcTransitionProbability(int currentAtWaitingRoom,
			int targetAtBusStop, int targetAtWaitingRoom, int numberOfWorkers,
			int numberOfArrival, int transfer) {
		double prob = 0.0;
		//only positive probability if number of workers or arrivals needed is possible 
		if(!(numberOfArrival < 0 || numberOfArrival >= arrivals.length || numberOfWorkers < 0 || numberOfWorkers >= workers.length)){
			//only positive probability if less than 3 workers needed for transition
			if(currentAtWaitingRoom + transfer - (workers.length - 1) <= targetAtWaitingRoom){
				//regular case: exactly one transition combination of arrivals and workers exists
				if(targetAtWaitingRoom > 0 && targetAtBusStop < 12) {
					prob = arrivals[numberOfArrival] * workers[numberOfWorkers];
				}
				//boundary case: if target at waiting room is 0, more counters can be opened than people actually need to be processed to get an empty waiting room
				else if(targetAtWaitingRoom == 0 && targetAtBusStop <= 12){	
					for(int i=numberOfWorkers; i < workers.length; i++){
						
						prob+= arrivals[numberOfArrival] * workers[i];
					}
				}
				//boundary case: if target at bus stop is 12, more people than needed can arrive at the bus stop as they will not enqueue
				else if(targetAtWaitingRoom > 0 && targetAtBusStop == 12){	
					for(int i=numberOfArrival; i < arrivals.length; i++){
						prob+= arrivals[i] * workers[numberOfWorkers];
					}
				}
			}
		}
		return prob;
	}


	public int calcNumberOfArrivals(int currentAtBusStop,
			int currentAtWaitingRoom, int targetAtBusStop, int transfer) {
		return -currentAtBusStop + targetAtBusStop + transfer;
	}

	public int calcNumberOfWorkers(int currentAtBusStop,
			int currentAtWaitingRoom, int targetAtWaitingRoom, int transfer) {
		return currentAtWaitingRoom + transfer - targetAtWaitingRoom;
	}

	public int calculateTransferValue(int numberAtBusStation,
			int numberAtWaitingRoom) {
		int result = 0;
		if ((numberAtBusStation >= 3) && (numberAtBusStation <= 8)) {
			result = Math.min(numberAtBusStation, CarRentalModelApplication.waitingRoomCapacity - numberAtWaitingRoom);
		} else if (numberAtBusStation >= 9) {
			return Math.min(8, CarRentalModelApplication.waitingRoomCapacity - numberAtWaitingRoom);
		}
		return result;
	}

	public double getTransitionElement(int currentAtBusStop, int currentAtWaitingRoom, int targetAtBusStop, int targetAtWaitingRoom){
		return this.transitionMatrix[currentAtBusStop][currentAtWaitingRoom][targetAtBusStop][targetAtWaitingRoom];
	}
	
	public void setTransitionElement(int currentAtBusStop, int currentAtWaitingRoom, int targetAtBusStop, int targetAtWaitingRoom, double value){
		this.transitionMatrix[currentAtBusStop][currentAtWaitingRoom][targetAtBusStop][targetAtWaitingRoom] = value;
	}
	
	public void setWorkersForTransitionElement(int currentAtBusStop, int currentAtWaitingRoom, int targetAtBusStop, int targetAtWaitingRoom, int value){
		
		//if too many workers than available or negative amount of workers needed (probability 0) indicate by -1
		if(value>workers.length-1 || value<0){
			value = -1;
		}
		this.workersForTransition[currentAtBusStop][currentAtWaitingRoom][targetAtBusStop][targetAtWaitingRoom] = value;
	}
	
	public int getWorkersForTransitionElement(int currentAtBusStop, int currentAtWaitingRoom, int targetAtBusStop, int targetAtWaitingRoom){
		return this.workersForTransition[currentAtBusStop][currentAtWaitingRoom][targetAtBusStop][targetAtWaitingRoom];
	}
}
