package app;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import app.State;

public class FSMConfig {
	
	private List<State> fsmStates = new LinkedList<State>();
	private List<Event> fsmEvents = new LinkedList<Event>();

	private State fsmStartState = new State("stub");
	private List<State> fsmEndStates = new LinkedList<State>();
	
	public FSMConfig(String filename) throws ParserConfigurationException, SAXException, IOException
	{
		this.loadConfig(filename);
	}
		
	public void loadConfigXML(String filename, String schemaPath) throws ParserConfigurationException, SAXException, IOException
	{

			ConfigParser confPars = new XMLParser(schemaPath);
			Document doc = ((XMLParser) confPars).getDocument(filename);
			
			String[][] states = confPars.readStates(doc);
			String[] events = confPars.readEvents(doc);
			String[][] transitions = confPars.readTransitions(doc);
			String startState = confPars.readStartState(doc);
			String[] endStates = confPars.readEndStates(doc);
			
			//Importieren der Zustände und Events
			this.importStates(states);
			this.importEvents(events);
			

			//Zuständen werden Übergänge zugeordnet
			this.addTransitions(transitions);
			
			this.importStartState(startState);
			this.importEndStates(endStates);
			
	}
	
	public void loadConfig(String filename) throws ParserConfigurationException, SAXException, IOException
	{
		loadConfigXML(filename, "D:/GoogleDrive/Angewandte Informatik/Semester_4/SWTP/Zustandsautomat/XML/AutomatSchema.xml");
	}
	
	private void importStates(String[][] states)
	{	
		//Zurücksetzen der Konfigurationsvariablen
		
		fsmStates.clear();
		
		//Erzeugen der Objekte

		for(int s = 0; s < states.length; s++)
		{
			fsmStates.add(new State(states[s][0]));
		}
		
	}
	
	private void importEvents(String[] events)
	{
		fsmEvents.clear();
		
		for(String e: events)
			fsmEvents.add(new Event(e));
		
	}
	
	private void addTransitions(String[][] transitions)
	{
		for(int t = 0; t < transitions.length; t++)
		{
			String startStateName = transitions[t][0];
			String endStateName = transitions[t][1];
			String eventName = transitions[t][2];
			
			int startStateIndex = Integer.MAX_VALUE;
			int endStateIndex = Integer.MAX_VALUE;
			String currentStateListName = "";
			
			for(int s = 0; s < fsmStates.size(); s++)
			{
				currentStateListName = fsmStates.get(s).getName();
								
				if(currentStateListName.compareTo(startStateName) == 0)
				{
					startStateIndex = s;
				}
				
				if(currentStateListName.compareTo(endStateName) == 0)
				{
					endStateIndex = s;
				}
			}
			

			Event transitionEvent = null;
			
			for(Event e : fsmEvents)
			{
				if(e.getName().compareTo(eventName) == 0)
				{
					transitionEvent = e;
				}
			}

			assert(startStateIndex != Integer.MAX_VALUE);
			assert(endStateIndex != Integer.MAX_VALUE);
			assert(transitionEvent != null);
			
			Transition transition = new Transition(fsmStates.get(endStateIndex));
			fsmStates.get(startStateIndex).addTransition(transitionEvent, transition);

		}
		
		
	}

	private void importStartState(String startState)
	{
		for(State s : fsmStates)
			if(s.getName().compareTo(startState) == 0)
				fsmStartState = s;
	}
	
	private void importEndStates(String[] endStates)
	{
		for(String es : endStates)
		{
			for(State s : fsmStates)
			{
				if(s.getName() == es)
					fsmEndStates.add(s);
			}
		}
	
	}

	public boolean compareTo(FSMConfig secondConf)
	{
		
		if(fsmStates.size() != secondConf.getStates().size())
		{
			return false;
		}
			
		for(int i = 0; i < fsmStates.size(); i++)
		{
			String fsmStateName = fsmStates.get(i).getName();
			String secondStateEventName = secondConf.getStates().get(i).getName();
			
			if(fsmStateName.compareTo(secondStateEventName) != 0)
				return false;		
		}
		
		
		if(fsmEvents.size() != secondConf.getEvents().size())
		{
			System.out.println("Events: List Size");
			return false;
		}
			
		for(int i = 0; i < fsmEvents.size(); i++)
		{
			String fsmEventName = fsmEvents.get(i).getName();
			String secondConfEventName = secondConf.getEvents().get(i).getName();
			
			if(fsmEventName.compareTo(secondConfEventName) != 0)
				return false;
		}
		
		
		if(fsmStartState.getName().compareTo(secondConf.getStartState().getName()) != 0)
			return false;
		
		
		if(fsmEndStates.size() != secondConf.getEndStates().size())
			return false;
			
		for(int i = 0; i < fsmEndStates.size(); i++)
		{
			String fsmEndStateName = fsmEndStates.get(i).getName();
			String secondConfEndStateName = secondConf.getEndStates().get(i).getName();
				
			if(fsmEndStateName.compareTo(secondConfEndStateName) != 0)
				return false;
		}
		
		return true;
	}
	
	public List<State> getStates() {
		return fsmStates;
	}

	public List<Event> getEvents() {
		return fsmEvents;
	}

	public State getStartState() {
		return fsmStartState;
	}

	public List<State> getEndStates() {
		return fsmEndStates;
	}

}
