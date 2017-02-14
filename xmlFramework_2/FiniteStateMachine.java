package app;

import java.util.HashSet;

public class FiniteStateMachine {
	
	private HashSet<State> fsmStates;
	private State currentState;
	private State startState;
	private HashSet<State> endStates;
	
	public FiniteStateMachine(XMLParser xmlp)
	{
		fsmStates = xmlp.loadStates();
		startState = xmlp.loadStartState();
		endStates = xmlp.loadEndStates();
		
		currentState = startState;
	}
	
	public void processEvent(Event event)
	{
		currentState = currentState.processEvent(event);
	}

	
	public HashSet<State> getFsmStates() {
		return fsmStates;
	}

	public void setFsmStates(HashSet<State> fsmStates) {
		this.fsmStates = fsmStates;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getStartState() {
		return startState;
	}

	public void setStartState(State startState) {
		this.startState = startState;
	}

	public HashSet<State> getEndStates() {
		return endStates;
	}

	public void setEndStates(HashSet<State> endStates) {
		this.endStates = endStates;
	}

}
