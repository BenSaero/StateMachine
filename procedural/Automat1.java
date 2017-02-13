package procedural01;

public class Automat1 {

	public static boolean procedure(String sequence)
	{
		
		String eventSequence = sequence;
		int index = 0;
		String state = "Q1";
		boolean error = false;
	
		while( index < eventSequence.length() )
		{
			
			char event = eventSequence.charAt(index);
		
			switch(state)
			{
				
				case "Q1":
					
					switch(event)
					{
						case '0':		state = "Q1"; break;
						case '1':		state = "Q2"; break;
						default:		System.err.println("Falsches Event");
										error = true;
										break;
					}
				
					break;
				
				case "Q2":
					switch(event)
					{
						case '0':		state = "Q1"; break;
						case '1':		state = "Q2"; break;
						default:		System.err.println("Falsches Event"); 
										error = true;
										break;
					}
					break;
				
				default:
					System.err.println("Falscher Zustand");
					error = true;
					break;
			}
			
			++index;
		}
		
		if(state == "Q2")
		{
			System.err.println("Ungültiger Endzustand (Q2)");
			error = true;
		}
		return (!error);
	}
}

