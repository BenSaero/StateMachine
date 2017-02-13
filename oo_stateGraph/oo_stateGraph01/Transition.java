package objectOriented01_StateGraph;

public final class Transition {

	private final State state;
	
	public Transition(State endState)
	{
		super();
		this.state = endState;
	}
	
	public State getEndState()
	{
		return this.state;
	}
}
