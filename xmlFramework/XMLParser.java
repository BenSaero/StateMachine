package app;

import java.io.File;
import java.io.IOException;
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

public class XMLParser implements ConfigParser{

	private String xmlSchemaPath;
	
	public XMLParser(String xmlSchemaPath)
	{
		this.xmlSchemaPath = xmlSchemaPath;
	}
	
	public void setXMLSchemaPath(String path)
	{
		this.xmlSchemaPath = path;
	}
	
	public String getXMLSchemaPath()
	{
		return xmlSchemaPath;
	}
	
	public Document getDocument(String documentPath) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = parser.parse(new File(documentPath));
	
		document.getDocumentElement().normalize();
		
		validate(document);
		
		return document;
			
	}
	
	private void validate(Document doc) throws SAXException, IOException
	{
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);		
		
		Source schemaFile = new StreamSource(new File(xmlSchemaPath));
		Schema schema = factory.newSchema(schemaFile);
	
		Validator validator = schema.newValidator ();
		validator.validate(new DOMSource(doc));
	}
	
	public String[][] readStates(Document doc)
	{
		NodeList stateList = doc.getElementsByTagName("state");
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
	
	public String[][] readTransitions(Document doc)
	{	
		
		NodeList transitionList = doc.getElementsByTagName("transition");
		String[][] transitions = new String[transitionList.getLength()][3];
		
		for (int t = 0; t < transitionList.getLength(); t++)
		{
			Node node = transitionList.item(t);
			
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				String source = element.getAttribute("source");
				String target = element.getAttribute("target");
				String event = element.getAttribute("event");
				
				transitions[t][0] = source;
				transitions[t][1] = target;
				transitions[t][2] = event;
				
			}
			
		}
		
		return transitions;
	}
	
	public String[] readEvents(Document doc)
	{
		NodeList transitionList = doc.getElementsByTagName("transition");
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
	
	public String readStartState(Document doc)
	{
		NodeList startStateList = doc.getElementsByTagName("startState");
		Node node = startStateList.item(0);
		
		String startStateName = "";
		
		if (node.getNodeType() == Node.ELEMENT_NODE)
		{
			Element element = (Element) node;
			startStateName = element.getAttribute("name");
		}
		
		return startStateName;
	}
	
	public String[] readEndStates(Document doc)
	{
		NodeList endStateList = doc.getElementsByTagName("startState");
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
