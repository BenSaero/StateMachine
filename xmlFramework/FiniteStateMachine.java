package app;

import app.State;

public class FiniteStateMachine {

	private FSMConfig fsmConfig;
	private State currentState;
	
	
	public FiniteStateMachine(FSMConfig config)
	{
		fsmConfig = config;
		currentState = fsmConfig.getStartState();
	}
	
	public void processEvent(String event)
	{
		
		for(Event e : fsmConfig.getEvents())
		{
			if(e.getName().compareTo(event) == 0)
			{
				
				currentState = currentState.processEvent(e);
			}
		}
			
	}
	
	public State getCurrentState()
	{
		return currentState;
	}
}
