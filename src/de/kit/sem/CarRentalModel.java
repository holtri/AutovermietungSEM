package de.kit.sem;

public class CarRentalModel {

	public void solve() {

	}

	public void createTransitionMatrix() {
		TransitionMatrix transitionMatrix = new TransitionMatrix();
		// iterate over all elements in transition matrix
		iterateOverTransitionMatrix();
	}

	private void iterateOverTransitionMatrix() {
		// iterate over rows
		for (int currentAtBusStop = 0; currentAtBusStop <= CarRentalModelApplication.busStopCapacity; currentAtBusStop++) {
			for (int currentAtWaitingRoom = 0; currentAtWaitingRoom <= CarRentalModelApplication.waitingRoomCapacity; currentAtWaitingRoom++) {
				// iterate over columns
				iterateOverColumns(currentAtBusStop, currentAtWaitingRoom);
			}
		}
	}

	private void iterateOverColumns(int currentAtBusStop,
			int currentAtWaitingRoom) {
		for (int targetAtBusStop = 0; targetAtBusStop <= CarRentalModelApplication.busStopCapacity; targetAtBusStop++) {
			for (int targetAtWaitingRoom = 0; targetAtWaitingRoom <= CarRentalModelApplication.busStopCapacity; targetAtWaitingRoom++) {
				//TODO 
				int numberOfWorkers = calcNumberOfWorkers(currentAtBusStop,
						currentAtWaitingRoom, targetAtWaitingRoom);
			}
		}
	}

	public int calcNumberOfWorkers(int currentAtBusStop,
			int currentAtWaitingRoom, int targetAtWaitingRoom) {
		return currentAtWaitingRoom
				+ calculateTransferValue(currentAtBusStop, currentAtWaitingRoom)
				- targetAtWaitingRoom;
	}

	public int calculateTransferValue(int numberAtBusStation,
			int numberAtWaitingRoom) {
		int result = 0;
		if ((numberAtBusStation >= 3) && (numberAtBusStation <= 8)) {
			result = Math.min(numberAtBusStation,
					CarRentalModelApplication.waitingRoomCapacity
							- numberAtWaitingRoom);
		} else if (numberAtBusStation >= 9) {
			return Math.min(8, CarRentalModelApplication.waitingRoomCapacity
					- numberAtWaitingRoom);
		}
		return result;
	}

}
