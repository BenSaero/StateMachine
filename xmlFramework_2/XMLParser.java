package app;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

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

public class XMLParser{

	private String xmlSchemaPath;
	private String documentPath;
	private Document currentDocument;
	
	public XMLParser(String xmlSchemaPath)
	{
		this.xmlSchemaPath = xmlSchemaPath;
	}
	
	public XMLParser(String xmlSchemaPath, String documentPath) throws ParserConfigurationException, SAXException, IOException
	{
		this.xmlSchemaPath = xmlSchemaPath;
		this.documentPath = documentPath;
		this.currentDocument = this.getDocument();
		this.validateCurrentDocument();
	}
	
	
	public void setXMLSchemaPath(String path)
	{
		this.xmlSchemaPath = path;
	}
	
	public String getXMLSchemaPath()
	{
		return xmlSchemaPath;
	}
	
	
	public void setDocumentPath(String path)
	{
		this.documentPath = path;
	}
	
	public String getDocumentPath()
	{
		return documentPath;
	}
	
	
	public Document getDocument() throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = parser.parse(new File(documentPath));
	
		document.getDocumentElement().normalize();
		
		currentDocument = document;
		validateCurrentDocument();
		
		return document;
			
	}
	
	private void validateCurrentDocument() throws SAXException, IOException
	{
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);		
		
		Source schemaFile = new StreamSource(new File(xmlSchemaPath));
		Schema schema = factory.newSchema(schemaFile);
	
		Validator validator = schema.newValidator ();
		validator.validate(new DOMSource(currentDocument));
	}
	
	
	
	public HashSet<State> loadStates()
	{
		HashSet<State> returnSet = new HashSet<State>();
		String[][] statesAttributes = readStatesAttributes();
		String[][] transitionsAttributes = readTransitionsAttributes();
		
		
		LinkedList<State> tempStatesList = new LinkedList<State>();
		for( int i = 0; i < statesAttributes.length ; i++)
		{
			State currentState = new State(statesAttributes[i][0]);
			tempStatesList.add(currentState);
		}
		
		LinkedList<Transition> tempTransitionsList = new LinkedList<Transition>();
		for( int i = 0; i < transitionsAttributes.length; i++)
		{
			State currentEndState = null;
			
			for( State s : tempStatesList)
			{
				if( s.getName() == transitionsAttributes[i][1]);
					currentEndState = s;
			}
			
			assert(currentEndState != null);
			
			Transition currentTransition = new Transition(currentEndState);
			tempTransitionsList.add(currentTransition);	
		}
		
		LinkedList<Event> tempEventsList = new LinkedList<Event>();
		for( int i = 0; i < transitionsAttributes.length; i++ )
		{
			Event currentEvent = new Event(transitionsAttributes[i][2]);
			tempEventsList.add(currentEvent);
		}
		
		
		for( State s : tempStatesList )
		{
			for( int i = 0; i < transitionsAttributes.length; i++)
			{
				if( transitionsAttributes[i][0] == s.getName() )
				{
					Transition tmpTransition = null;
					Event tmpEvent = null;
					
					for( Transition t : tempTransitionsList)
					{
						if( t.getEndState().getName() == transitionsAttributes[i][1] )
						{
							tmpTransition = t;
							break;
						}
					}
					
					for( Event e : tempEventsList )
					{
						if( e.getEventName() == transitionsAttributes[i][2] )
						{
							tmpEvent = e;
							break;
						}
					}
					
					s.addTransition(tmpEvent, tmpTransition);
				}
			}
			
			returnSet.add(s);
		}
		
		return returnSet;
		
	}
	
	public String[][] readStatesAttributes()
	{
		NodeList stateList = currentDocument.getElementsByTagName("state");
		String[][] states = new String[stateList.getLength()][3];
	
		for (int tmp = 0; tmp < stateList.getLength(); tmp++)
		{
			Node nNode = stateList.item(tmp);
		
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				String name = eElement.getAttribute("name");
				String entryAction = eElement.getAttribute("entryaction");
				String exitAction   = eElement.getAttribute("exitaction");
				
				states[tmp][0] = name;
				states[tmp][1] = entryAction;
				states[tmp][2] = exitAction;
				
			}
		}
		
		return states;
	}
	
	public String[][] readTransitionsAttributes()
	{	
		
		NodeList transitionList = currentDocument.getElementsByTagName("transition");
		String[][] transitions = new String[transitionList.getLength()][4];
		
		for (int t = 0; t < transitionList.getLength(); t++)
		{
			Node node = transitionList.item(t);
			
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				String source = element.getAttribute("source");
				String target = element.getAttribute("target");
				String event = element.getAttribute("event");
				
				String action = "";
				if(element.hasAttribute("action"))
					action = element.getAttribute("action"); 
				
				transitions[t][0] = source;
				transitions[t][1] = target;
				transitions[t][2] = event;
				transitions[t][3] = action;
				
			}
			
		}
		
		return transitions;
	}
	
	
	
	/*
	public String[] readEvents()
	{
		NodeList transitionList = currentDocument.getElementsByTagName("transition");
		LinkedList<String> events = new LinkedList<String>();
		
		for (int t = 0; t < transitionList.getLength(); t++)
		{
			Node node = transitionList.item(t);
			
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				String event = element.getAttribute("event");
				
				//Ist Event noch nicht bekannt, wird es der List hinzugefügt
				if(events.contains(event) == false)
					events.add(event);
			}
		}
		
		String[] returnEventsList = new String[events.size()];
		for(int rev = 0; rev < returnEventsList.length; rev++)
		{
			returnEventsList[rev] = events.get(rev);
		}
		
		return returnEventsList;
	}
	*/
	
	public State loadStartState()
	{
		HashSet<State> states = loadStates();
		State returnState = null;
		String startStateString = readStartState();
		
		for( State s : states )
		{
			if( s.getName() == startStateString )
				returnState =  s;
		}
		
		return returnState;
	}
	
	public String readStartState()
	{
		NodeList startStateList = currentDocument.getElementsByTagName("startState");
		Node node = startStateList.item(0);
		
		String startStateName = "";
		
		if (node.getNodeType() == Node.ELEMENT_NODE)
		{
			Element element = (Element) node;
			startStateName = element.getAttribute("name");
		}
		
		return startStateName;
	}
	
	
	public HashSet<State> loadEndStates()
	{
		HashSet<State> states = loadStates();
		HashSet<State> returnSet = new HashSet<State>();
		String[] endStatesNames = readEndStates();
		
		for( State s : states )
		{	
			for( int i = 0; i < endStatesNames.length; i++)
			{
				if( s.getName() == endStatesNames[i] )
					returnSet.add(s);
			}
		}
		
		return returnSet;
	}
	
	public String[] readEndStates()
	{
		NodeList endStateList = currentDocument.getElementsByTagName("startState");
		LinkedList<String> endStates = new LinkedList<String>();
		
		for(int end = 0; end < endStateList.getLength(); end++)
		{
			Node endNode = endStateList.item(end);
			String endStateName = null;
			
			if(endNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) endNode;
				endStateName = element.getAttribute("name");
				
				endStates.add(endStateName);
			}
			
		}
		
		String[] returnEndStatesList = new String[endStates.size()];
		for(int rev = 0; rev < returnEndStatesList.length; rev++)
		{
			returnEndStatesList[rev] = endStates.get(rev);
		}
		
		return returnEndStatesList;

	}
}
