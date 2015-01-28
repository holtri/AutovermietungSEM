package de.kit.sem.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kit.sem.CarRentalModelApplication;
import de.kit.sem.TransitionMatrix;

public class CarRentalModelTest {

	private TransitionMatrix testTransitionMatrix;

	private static final double EPSILON = 1e-15;
	
	@Before
	public void setUp() throws Exception {
		this.testTransitionMatrix = new TransitionMatrix();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArrivalProbabilityDistribution() {
		
		double sum = 0;
		for(Double d : TransitionMatrix.arrivals){
			sum+=d;
			assertTrue(d.doubleValue()>=0);
		}
		assertTrue(sum==1);
	}
	
	@Test
	public void testWorkerProbabilityDistribution() {
		double sum = 0;
		for(Double d : TransitionMatrix.workers){
			sum+=d;
			assertTrue(d.doubleValue()>=0);
		}
		assertTrue(sum==1);
	}
	
	@Test
	public void testTransferFunction() {
		for (int numberAtBusStop = 0; numberAtBusStop <= 12; numberAtBusStop++) {
			for (int numberInWaitingRoom = 0; numberInWaitingRoom <= 10; numberInWaitingRoom++) {
				if (numberAtBusStop <= 2) {
					assertTrue(testTransitionMatrix.calculateTransferValue(numberAtBusStop, numberInWaitingRoom) == 0);
				}
				if (numberAtBusStop >= 3 && numberAtBusStop <= 8) {
					assertTrue(testTransitionMatrix.calculateTransferValue(numberAtBusStop, numberInWaitingRoom) == Math
							.min(numberAtBusStop, CarRentalModelApplication.waitingRoomCapacity-numberInWaitingRoom));
				}
				if (numberAtBusStop >= 9) {
					assertTrue(testTransitionMatrix.calculateTransferValue(numberAtBusStop, numberInWaitingRoom) == Math.min(8,CarRentalModelApplication.waitingRoomCapacity-numberInWaitingRoom));
				}
			}

		}
	}
	
	@Test
	public void testCalcNumerOfWorkers(){
		
		int currentAtBusStop = 3;
		int currentAtWaitingRoom = 5;
		int targetAtWaitingRoom = 6;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		
		assertTrue(transfer==3);
		assertTrue(testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer)==2);
	}
	
	
	
	@Test
	public void testCalcNumerOfArrivals(){
		
		int currentAtBusStop = 3;
		int currentAtWaitingRoom = 5;
		int targetAtBusStop = 8;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		
		assertTrue(transfer==3);
		assertTrue(testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer)==8);
	}
	
	@Test
	public void testCalcTransitionProbability1(){
		int currentAtBusStop = 3;
		int currentAtWaitingRoom = 5;
		int targetAtBusStop = 8;
		int targetAtWaitingRoom = 6;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer)==2);
		assertTrue(testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer)==8);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		
		assertTrue(prob == 0.005);
	}
	
	@Test
	public void testCalcTransitionProbability2(){
		int currentAtBusStop = 2;
		int currentAtWaitingRoom = 1;
		int targetAtBusStop = 5;
		int targetAtWaitingRoom = 0;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer)==1);
		assertTrue(testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer)==3);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		
		assertEquals(prob, (0.1+0.5+0.3)*0.11, EPSILON);
	}
	
	@Test
	public void testCalcTransitionProbability3(){
		int currentAtBusStop = 10;
		int currentAtWaitingRoom = 4;
		int targetAtBusStop = 12;
		int targetAtWaitingRoom = 3;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer)==7);
		assertTrue(testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer)==8);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		assertTrue(prob == 0); //needed number of workers is 7 
	}
	
	@Test
	public void testCalcTransitionProbability4(){
		int currentAtBusStop = 10;
		int currentAtWaitingRoom = 4;
		int targetAtBusStop = 12;
		int targetAtWaitingRoom = 8;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(numberOfWorkers==2);
		assertTrue(numberOfArrivals==8);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		
		assertEquals(prob, 0.5 * (0.01+0.005+0.005), EPSILON); 
	}
	
	@Test
	public void testCalcTransitionProbability5(){
		int currentAtBusStop = 2;
		int currentAtWaitingRoom = 0;
		int targetAtBusStop = 2;
		int targetAtWaitingRoom = 0;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(numberOfWorkers==0);
		assertTrue(numberOfArrivals==0);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		assertEquals(prob, 0.265, EPSILON); 
	}
	
	@Test
	public void testCalcTransitionProbability6(){
		int currentAtBusStop = 2;
		int currentAtWaitingRoom = 0;
		int targetAtBusStop = 12;
		int targetAtWaitingRoom = 0;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(numberOfWorkers==0);
		assertTrue(numberOfArrivals==10);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		assertEquals(prob, 0.005, EPSILON); 
	}
	
	@Test
	public void testCalcTransitionProbability7(){
		int currentAtBusStop = 3;
		int currentAtWaitingRoom = 3;
		int targetAtBusStop = 3;
		int targetAtWaitingRoom = 7;
		
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		int numberOfArrivals = testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer);
		int numberOfWorkers = testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer);
		
		assertTrue(testTransitionMatrix.calcNumberOfWorkers(currentAtBusStop, currentAtWaitingRoom, targetAtWaitingRoom, transfer)==-1);
		assertTrue(testTransitionMatrix.calcNumberOfArrivals(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, transfer)==3);

		double prob = testTransitionMatrix.calcTransitionProbability(currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom, numberOfWorkers, numberOfArrivals, transfer);
		assertTrue(prob == 0); //needed number of workers is negative (-1)
	}
	
	@Test
	public void testTransitionRowSum() {
		// iterate rows
		for (int i = 0; i <= CarRentalModelApplication.busStopCapacity; i++) {
			for (int k = 0; k <= CarRentalModelApplication.waitingRoomCapacity; k++) {
				double sum = 0.0;
				for (int j = 0; j <= CarRentalModelApplication.busStopCapacity; j++) {
					for (int l = 0; l <= CarRentalModelApplication.waitingRoomCapacity; l++) {
						sum += testTransitionMatrix.getTransitionElement(i, k,
								j, l);
					}
				}
				assertEquals(sum, 1, EPSILON);
			}
		}
	}

	@Test
	public void testSetWorkersForTransition1(){
		int test = testTransitionMatrix.getWorkersForTransitionElement(1, 1, 4, 4);
		assertNotEquals(test, -100);
		testTransitionMatrix.setWorkersForTransitionElement(1, 1, 4, 4, 42);
		test = testTransitionMatrix.getWorkersForTransitionElement(1, 1, 4, 4);
		assertEquals(test, -1);
	}
	
	@Test
	public void testSetWorkersForTransition2(){
		int currentAtBusStop = 10;
		int currentAtWaitingRoom = 4;
		int targetAtBusStop = 12;
		int targetAtWaitingRoom = 3;
		
		assertTrue(testTransitionMatrix.getWorkersForTransitionElement(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom)==-1); //number of needed workers is 7
	}
	
	@Test
	public void testSetWorkersForTransition3(){
		int currentAtBusStop = 3;
		int currentAtWaitingRoom = 3;
		int targetAtBusStop = 3;
		int targetAtWaitingRoom = 7;
		
		assertTrue(testTransitionMatrix.getWorkersForTransitionElement(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom)==-1); //number of needed workers is negative (-1)
	}
	
	@Test
	public void testSetWorkersForTransition4(){
		
		int currentAtBusStop = 2;
		int currentAtWaitingRoom = 1;
		int targetAtWaitingRoom = 0;
		int targetAtBusStop = 2;
				
		int transfer = testTransitionMatrix.calculateTransferValue(currentAtBusStop, currentAtWaitingRoom);
		
		assertTrue(testTransitionMatrix.getWorkersForTransitionElement(currentAtBusStop, currentAtWaitingRoom, targetAtBusStop, targetAtWaitingRoom)==1); 
	}
	
}
