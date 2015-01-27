package de.kit.sem.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kit.sem.CarRentalModel;
import de.kit.sem.CarRentalModelApplication;

public class CarRentalModelTest {

	private CarRentalModel testModel;

	@Before
	public void setUp() throws Exception {
		this.testModel = new CarRentalModel();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransferFunction() {
		for (int numberAtBusStop = 0; numberAtBusStop <= 12; numberAtBusStop++) {
			for (int numberInWaitingRoom = 0; numberInWaitingRoom <= 10; numberInWaitingRoom++) {
				if (numberAtBusStop <= 2) {
					assertTrue(testModel.calculateTransferValue(numberAtBusStop, numberInWaitingRoom) == 0);
				}
				if (numberAtBusStop >= 3 && numberAtBusStop <= 8) {
					assertTrue(testModel.calculateTransferValue(numberAtBusStop, numberInWaitingRoom) == Math
							.min(numberAtBusStop, CarRentalModelApplication.waitingRoomCapacity-numberInWaitingRoom));
				}
				if (numberAtBusStop >= 9) {
					assertTrue(testModel.calculateTransferValue(numberAtBusStop, numberInWaitingRoom) == Math.min(8,CarRentalModelApplication.waitingRoomCapacity-numberInWaitingRoom));
				}
			}

		}
	}
	
	@Test
	public void testCalceNumerOfWorkers(){
		
//		int currentAtBusStop = 3;
//		int currentAtWaitingRoom = 5;
//		int targetAtWaitingRoom = 6;
		
		assertTrue(testModel.calculateTransferValue(3, 5)==3);
		assertTrue(testModel.calcNumberOfWorkers(3, 5, 6)==2);
	}
	
	

}
