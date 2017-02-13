package procedural01;

import static org.junit.Assert.*;
import org.junit.Test;



public class TestAutomat1 {

	@Test
	public void testLegalEvents()
	{
		String testSequence01 = "0";
		String testSequence02 = "010";
		String testSequence03 = "0110";
		
		boolean result1 = Automat1.procedure(testSequence01);
		boolean result2 = Automat1.procedure(testSequence02);
		boolean result3 = Automat1.procedure(testSequence03);
		
		assertTrue("Legal Events - TestSequenz 1", result1 == true);
		assertTrue("Legal Events - TestSequenz 2", result2 == true);
		assertTrue("Legal Events - TestSequenz 3", result3 == true);
	}

	
	@Test
	public void testIllegalEvents()
	{
		String testSequence01 = "1";
		String testSequence02 = "011";
		String testSequence03 = "032";
		
		boolean result1 = Automat1.procedure(testSequence01);
		boolean result2 = Automat1.procedure(testSequence02);
		boolean result3 = Automat1.procedure(testSequence03);
				
		assertTrue("Illegal Events - TestSequenz 1", result1 == false);
		assertTrue("Illegal Events - TestSequenz 2", result2 == false);
		assertTrue("Illegal Events - TestSequenz 3", result3 == false);
	}
	
	
}
