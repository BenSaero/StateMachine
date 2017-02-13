package _junit;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import objectOriented01_StateGraph.*;
/**
 * 	Testklasse der mithife eines objektorienterten Ansatz realisierten 
 * 	Zustandsautomaten, basierend auf einem Zustandsgraphen.
 * 
 * 	@author besa0004@stud.hs-kl.de
 *	@version 1.0
 *
 *	Date 07/12/2016
 */
public class TestObjectOriented01_StateGraph
{
	// Globale Variablen der Testumgebung (Init. durch setUp-Methode)
	private static State q1;
	private static State q2;
	private static Transition toQ1;
	private static Transition toQ2;
	
	/**
	 * 	SetUp-Methode zur Instanzierung und Konfiguration der Testumgebung
	 * 
	 * 	@author besa0004@stud.hs-kl.de
	 * 	@version 1.0
	 * 	Date 07/12/16
	 */
	@BeforeClass
	public static void setUp()
	{
		q1 = new State("Q1");
		q2 = new State("Q2");
		
		toQ1 = new Transition(q1);
		toQ2 = new Transition(q2);
		
		q1.addTransition(Event.ZERO, toQ1);
		q1.addTransition(Event.ONE, toQ2);
		q2.addTransition(Event.ZERO, toQ1);
		q2.addTransition(Event.ONE, toQ2);
		
	}
	
	/**
	 * 	Funktionstest(black-box-design):
	 * 
	 * 	Erzeugen alle legalen Übergänge den richtigen Endzustand ?
	 * 
	 * 	@author besa0004@stud.hs-kl.de
	 * 	@version 1.0
	 * 	Date 07/12/16
	 */
	@Test
	public void testLegalTransitions()
	{
		State currentState = q1;
		
		currentState = currentState.processEvent(Event.ZERO);	
		assertTrue("State: Q1 - Event: ZERO", currentState.getName() == q1.getName());
		
		currentState = currentState.processEvent(Event.ONE);
		assertTrue("State: Q1 - Event: ONE", currentState.getName() == q2.getName());
		
		currentState = currentState.processEvent(Event.ONE);
		assertTrue("State: Q2 - Event: ONE", currentState.getName() == q2.getName());
		
		currentState = currentState.processEvent(Event.ZERO);
		assertTrue("State: Q2 - Event: ZERO", currentState.getName() == q1.getName());
	}
	
}
