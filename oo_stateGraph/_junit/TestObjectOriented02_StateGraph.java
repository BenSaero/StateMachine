package _junit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import objectOriented02_StateGraph.Event;
import objectOriented02_StateGraph.State;
import objectOriented02_StateGraph.Transition;

public class TestObjectOriented02_StateGraph {

	// Globale Variablen der Testumgebung (Init. durch setUp-Methode)
	private static State s;
	private static State q1;
	private static State q2;
	private static State r1;
	private static State r2;
	
	private static Transition toQ1;
	private static Transition toQ2;
	private static Transition toR1;
	private static Transition toR2;
	
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
		//Instanzierung der Zustände
		s = new State("S");
		q1 = new State("Q1");
		q2 = new State("Q2");
		r1 = new State("R1");
		r2 = new State("R2");
		
		//Instanzierung der Übergänge
		toQ1 = new Transition(q1);
		toQ2 = new Transition(q2);
		toR1 = new Transition(r1);
		toR2 = new Transition(r2);
		
		//Zuweisung von legalen Übergängen an Zustände
		
		s.addTransition(Event.a, toQ1);
		s.addTransition(Event.b, toR1);
		
		q1.addTransition(Event.a, toQ1);
		q1.addTransition(Event.b, toQ2);
		
		q2.addTransition(Event.a, toQ1);
		q2.addTransition(Event.b, toQ2);
		
		r1.addTransition(Event.a, toR2);
		r1.addTransition(Event.b, toR1);
		
		r2.addTransition(Event.a, toR2);
		r2.addTransition(Event.b, toR1);
		
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
		//Setzen des Startzustands
		State currentState = s;
		
		//Linker Unterbaum des Graphen
		currentState = currentState.processEvent(Event.a);
		assertTrue("State: S - Event: a", currentState.getName() == q1.getName());
		
		currentState = currentState.processEvent(Event.a);
		assertTrue("State: Q1 - Event: a", currentState.getName() == q1.getName());
		
		currentState = currentState.processEvent(Event.b);
		assertTrue("State: Q1 - Event: b", currentState.getName() == q2.getName());
		
		currentState = currentState.processEvent(Event.b);
		assertTrue("State: Q2 - Event: b", currentState.getName() == q2.getName());
		
		currentState = currentState.processEvent(Event.a);
		assertTrue("State: Q2 - Event: a", currentState.getName() == q1.getName());
		
		//Zurücksetzen des Startzustands
		currentState = s;
		
		//Rechter Unterbaum des Graphen
		currentState = currentState.processEvent(Event.b);
		assertTrue("State: S - Event: b", currentState.getName() == r1.getName());
		
		currentState = currentState.processEvent(Event.b);
		assertTrue("State: R1 - Event: b", currentState.getName() == r1.getName());
		
		currentState = currentState.processEvent(Event.a);
		assertTrue("State: R1 - Event: a", currentState.getName() == r2.getName());
		
		currentState = currentState.processEvent(Event.a);
		assertTrue("State: R2 - Event: a", currentState.getName() == r2.getName());
		
		currentState = currentState.processEvent(Event.b);
		assertTrue("State: R2 - Event: b", currentState.getName() == r1.getName());
		
		
	}
}
