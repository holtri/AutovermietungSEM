package de.kit.sem;

import java.io.IOException;

public class CarRentalModelApplication {
	
	public static int waitingRoomCapacity = 10;
	public static int busStopCapacity = 12;
	
	public static void main (String[] args){
		TransitionMatrix transitionMatrix = new TransitionMatrix();
		System.out.println("## SEM1 Autovermietung ##");
		
		try {
			System.out.println("Push any key to exit");
	        System.in.read();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
