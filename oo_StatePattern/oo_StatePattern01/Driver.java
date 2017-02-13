package objectOriented01_StatePattern;

public class Driver {
	
	public static void main(String[] args)
	{
		State state = new Q1();
		
		state.processEvent0().processEvent1().processEvent1().processEvent0();
		
		System.out.println( state.getClass().getSimpleName());
	}

}
