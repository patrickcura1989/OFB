import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.applet.api.*;

import java.io.StringReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;

	public void initialize() throws Exception {
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}

	public void finish() throws Exception {
	}
	
	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public String getNodeElememtData(String soapResp, String nodeElementXPath, String nodeElementIndex) throws Exception
	{
		ArrayList<String> elementValues = new ArrayList<String>();
		
		//Default value
		int elementIndex = 0;
		if(nodeElementIndex != null || !"".equalsIgnoreCase(nodeElementIndex)){
			elementIndex = Integer.parseInt(nodeElementIndex);
		}
		
		try
		{
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource xmlStream = new InputSource();
			xmlStream.setCharacterStream(new StringReader(soapResp));
			Document xmlDocument = docBuilder.parse(xmlStream);
			
			if(nodeElementXPath.indexOf('/')!= -1 )
			{
					XPathFactory 		xPathFactory 	= 	XPathFactory.newInstance();
					XPath 				xPath 			= 	xPathFactory.newXPath();
					XPathExpression 	xPression 		=	xPath.compile(nodeElementXPath);
					
					NodeList nodeList = (NodeList) xPression.evaluate(xmlDocument, XPathConstants.NODESET);

					for (int i = 0; i < nodeList.getLength(); ++i) 
					{
							Element element = (Element) nodeList.item(i);	    		    							 
						 	if(	element.getTextContent()!= null	) 
						 		elementValues.add(element.getTextContent());
						 	else		
						 		elementValues.add("");
					}
			}
			else
			{
				NodeList nodeList 	= xmlDocument.getElementsByTagName(nodeElementXPath);

				for (int i = 0; i < nodeList.getLength(); ++i) 
				{
						Element element = (Element) nodeList.item(i);	    	    							 
					 	if(	element.getTextContent()!= null	) elementValues.add(element.getTextContent());
					 	else		elementValues.add("");
				}
				
			}

			if(elementIndex >= elementValues.size())
				return "";
			else 
				return elementValues.get(elementIndex);
			
			
		}
		catch(Exception e)
		{
			reportFailure("Exception in method getNodeElememtData() is : "+e);
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public String verifyData(String expectedData, String actualData) throws Exception
	{
		try
		{
			boolean stringMatched = false;

			Pattern pattern = Pattern.compile(expectedData);
			Matcher matcher = pattern.matcher(actualData);

			//this.sop("expectedString :" + expectedString);
			//this.sop("actualString :" + actualString);
			if (matcher.matches()) {
				stringMatched = true;
			}

			return stringMatched+"";
		}
		catch(Exception e)
		{
			reportFailure("Exception in method verifyData() is : "+e);
			e.printStackTrace();
		}
		
		return "";
	}
}
