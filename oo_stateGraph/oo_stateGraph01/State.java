package objectOriented01_StateGraph;

import java.util.HashMap;
import java.util.Map;

public final class State {
	
	private final Map<Event, Transition> transitions = new HashMap<>();
	private final String name;
	
	public State(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void addTransition(Event event, Transition transition)
	{
		this.transitions.put(event, transition);
	}
	
	public State processEvent(Event event)
	{
		Transition transition = this.transitions.get(event);
		
		if( transition != null)
			return transition.getEndState();
		else
			return this;
	}
	
	

}
