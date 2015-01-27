package de.kit.sem;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CarRentalModelApplicationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArrivalProbabilityDistribution() {
		
		double sum = 0;
		for(Double d : CarRentalModelApplication.arrivals){
			sum+=d;
			assertTrue(d.doubleValue()>=0);
		}
		assertTrue(sum==1);
	}
	@Test
	public void testWorkerProbabilityDistribution() {
		double sum = 0;
		for(Double d : CarRentalModelApplication.workers){
			sum+=d;
			assertTrue(d.doubleValue()>=0);
		}
		assertTrue(sum==1);
	}

}
