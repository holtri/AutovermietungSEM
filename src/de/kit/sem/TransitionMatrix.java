package de.kit.sem;

public class TransitionMatrix {

	private double[][][][] transitionMatrix;

	public TransitionMatrix() {
		this.transitionMatrix = new double[CarRentalModelApplication.busStopCapacity][CarRentalModelApplication.waitingRoomCapacity][CarRentalModelApplication.busStopCapacity][CarRentalModelApplication.waitingRoomCapacity];
	}

	public double getTransitionElement(int currentAtBusStop, int currentAtWaitingRoom, int targetAtBusStop, int targetAtWaitingRoom){
		return this.transitionMatrix[currentAtBusStop][currentAtWaitingRoom][targetAtBusStop][targetAtWaitingRoom];
	}
	
	public void setTransitionElement(int currentAtBusStop, int currentAtWaitingRoom, int targetAtBusStop, int targetAtWaitingRoom, double value){
		this.transitionMatrix[currentAtBusStop][currentAtWaitingRoom][targetAtBusStop][targetAtWaitingRoom] = value;
	}

}
