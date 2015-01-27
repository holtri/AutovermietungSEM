package de.kit.sem;

public class CarRentalModelApplication {
	
	public static int waitingRoomCapacity = 10;
	public static int busStopCapacity = 12;
	
	public static double[] arrivals = {0.265, 0.405, 0.01, 0.11, 0.11, 0.06, 0.01, 0.01, 0.01, 0.005, 0.005};
	public static double[] workers = {0.1,0.1,0.5,0.3};
	
	public static void main (String[] args){
		new CarRentalModel().solve();
	}
}
