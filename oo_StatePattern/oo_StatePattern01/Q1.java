package objectOriented01_StatePattern;

public class Q1 implements State{

	@Override
	public State processEvent0()
	{
		return new Q1();
	}
	
	@Override
	public State processEvent1()
	{
		return new Q2();
	}
}
