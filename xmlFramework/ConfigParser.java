package app;


import org.w3c.dom.Document;

public interface ConfigParser {
	
	public String[][] readStates(Document doc);
	
	public String[][] readTransitions(Document doc);
	
	public String[] readEvents(Document doc);
	
	public String readStartState(Document doc);
	
	public String[] readEndStates(Document doc);
	
}
