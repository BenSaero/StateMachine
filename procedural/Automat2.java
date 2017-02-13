package procedural02;

public class Automat2 {
	
	public static boolean procedure(String sequence)
	{

		String eventSequence = sequence;
		int index = 0;
		String state = "S";
		boolean error = false;
		
		while(index < eventSequence.length())
		{
			char event = eventSequence.charAt(index);
			
			switch(state)
			{
				case "S":
					
					switch(event)
					{
						case 'a': 	state = "Q1"; break;
						case 'b': 	state = "R1"; break;
						default:	System.err.println("Falsches Event");
									error = true;
									break;
					}
					break;
					
				case "Q1":
				
					switch(event)
					{
						case 'a':	state = "Q1"; break;
						case 'b':	state = "Q2"; break;
						default:	System.err.println("Falsches Event");
									error = true;
									break;
					}
					break;
					
				case "Q2":
					
					switch(event)
					{
						case 'a':	state = "Q1"; break;
						case 'b':	state = "Q2"; break;
						default:	System.err.println("Falsches Event");
									error = true;
									break;
					}
					break;
					
				case "R1":
					
					switch(event)
					{
						case 'a':	state = "R2"; break;
						case 'b':	state = "R1"; break;
						default:	System.err.println("Falsches Event");
									error = true;
									break;
					}
					break;
					
				case "R2":
					
					switch(event)
					{
						case 'a':	state = "R2"; break;
						case 'b':	state = "R1"; break;
						default:	System.err.println("Falsches Event");
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
		
		if(state == "S" || state == "Q2" || state == "R2")
		{
			System.err.println("Ungültiger Endzustand (" + state + ")");
			error = true;
		}
		
		return (!error);
	}
	

}
