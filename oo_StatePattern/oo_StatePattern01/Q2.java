package objectOriented01_StatePattern;

public class Q2 implements State{

	@Override
	public State processEvent0()
	{
		return new Q2();
	}

	@Override
	public State processEvent1()
	{	
		return new Q1();
	}

	
}
