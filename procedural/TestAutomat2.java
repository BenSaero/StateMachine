package procedural02;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestAutomat2 {

	@Test
	public void testLegalEvents()
	{
		String testSequence01 = "aa";
		String testSequence02 = "aabba";
		String testSequence03 = "bb";
		String testSequence04 = "bbaab";
		
		boolean result1 = Automat2.procedure(testSequence01);
		boolean result2 = Automat2.procedure(testSequence02);
		boolean result3 = Automat2.procedure(testSequence03);
		boolean result4 = Automat2.procedure(testSequence04);
		
		assertTrue("Legal Events - TestSequenz 1", result1 == true);
		assertTrue("Legal Events - TestSequenz 2", result2 == true);
		assertTrue("Legal Events - TestSequenz 3", result3 == true);
		assertTrue("Legal Events - TestSequenz 4", result4 == true);
	}

	
	@Test
	public void testIllegalEvents()
	{
		String testSequence01 = "ab";
		String testSequence02 = "ba";
		String testSequence03 = "abc";
		String testSequence04 = "bac";
		
		boolean result1 = Automat2.procedure(testSequence01);
		boolean result2 = Automat2.procedure(testSequence02);
		boolean result3 = Automat2.procedure(testSequence03);
		boolean result4 = Automat2.procedure(testSequence04);
				
		assertTrue("Illegal Events - TestSequenz 1", result1 == false);
		assertTrue("Illegal Events - TestSequenz 2", result2 == false);
		assertTrue("Illegal Events - TestSequenz 3", result3 == false);
		assertTrue("Illegal Events - TestSequenz 4", result4 == false);
		
	}
	
	
	
}
