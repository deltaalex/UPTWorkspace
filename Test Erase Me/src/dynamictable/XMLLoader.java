package dynamictable;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLLoader {

	public BuildInfo loadAndVerifyXML(String fullPath) 
		throws TransformerException, ParserConfigurationException, 
			   IOException, SAXException, MissingElementException
	{
		/*
		<build>
  			<actions>
  				...
    			...
    			<hudson.plugins.PerfPublisher.PerfPublisherBuildAction>
	      			...
	      			<report>
	        		 	...
	        			<name>CESSAR Build 18.04.2011</name>
	        			<tests>
	          				<hudson.plugins.PerfPublisher.Report.Test>
            					<name>PerfProjectCreationR40Test</name>						            
					            <success>
					              <passed>true</passed>
					              <state>100.0</state>
					              <hasTimedOut>false</hasTimedOut>
					            </success>
					            <compileTime>
					              <measure>0.0</measure>
					              <relevant>false</relevant>
					            </compileTime>
					            <executionTime>
					              <unit>s</unit>
					              <measure>23.02</measure>
					              <relevant>true</relevant>
					            </executionTime>
					            <performance>
					              <unit>byte</unit>
					              <measure>5.4770176E7</measure>
					              <relevant>true</relevant>
					            </performance>	            		            
          					</hudson.plugins.PerfPublisher.Report.Test>
          					...
		 */
		
		BuildInfo bInfo = new BuildInfo();
		File fXml = new File(fullPath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXml);
	    doc.getDocumentElement().normalize();
	    NodeList nList;	    	       	  
	    
	    Node nNode = (Node)(doc.getElementsByTagName("actions").item(0));   
	    Element element = (Element)((Element) nNode).getElementsByTagName("hudson.plugins.PerfPublisher.PerfPublisherBuildAction").item(0);	   
	    element = (Element)element.getElementsByTagName("report").item(0);
	    Element nElement = (Element)element.getElementsByTagName("name").item(0);	    
	    bInfo.name = nElement.getFirstChild().getNodeValue().trim();
	    
	    element = (Element)element.getElementsByTagName("tests").item(0);
	    nList = element.getElementsByTagName("hudson.plugins.PerfPublisher.Report.Test");
	    Element tElement;
	    for(int i=0; i<nList.getLength(); ++i)
	    {
	    	tElement = (Element)nList.item(i);	    	
	    	TestInfo tInfo = new TestInfo();
	    	
	    	element = (Element)tElement.getElementsByTagName("name").item(0);	    		    	
	    	tInfo.name = element.getFirstChild().getNodeValue();
	    	
	    	element = (Element)tElement.getElementsByTagName("compileTime").item(0);
	    	element = (Element)element.getElementsByTagName("measure").item(0);
	    	tInfo.compileTime = element.getFirstChild().getNodeValue();
	    	
	    	element = (Element)tElement.getElementsByTagName("executionTime").item(0);
	    	element = (Element)element.getElementsByTagName("measure").item(0);
	    	tInfo.executionTime = element.getFirstChild().getNodeValue();
	    	
	    	element = (Element)tElement.getElementsByTagName("performance").item(0);
	    	element = (Element)element.getElementsByTagName("measure").item(0);
	    	tInfo.performance = element.getFirstChild().getNodeValue();    
	    	
	    	bInfo.tests.add(tInfo);
	    }
	    	    	   		 
	    return bInfo;
	}
}
