import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.jdom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.http.api.*;
import oracle.oats.scripting.modules.http.api.HTTPService.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webService.api.*;
import oracle.oats.scripting.modules.webService.api.WSService.*;
@SuppressWarnings("unused")

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.http.api.HTTPService http;
	@ScriptService oracle.oats.scripting.modules.webService.api.WSService ws;
	
	public void initialize() throws Exception {
	}
	
	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {
		
	}
	public  void removeEmptyNodes(Node node) throws Exception {
		//info("EMPTY FUNCTION CALLED");
		
	    NodeList list = node.getChildNodes();
	    List<Node> nodesToRecursivelyCall = new LinkedList();

	    for (int i = 0; i < list.getLength(); i++) {
	        nodesToRecursivelyCall.add(list.item(i));
	    }

	    for(Node tempNode : nodesToRecursivelyCall) {
	        removeEmptyNodes(tempNode);
	    }

	    boolean emptyElement = node.getNodeType() == Node.ELEMENT_NODE 
	          && node.getChildNodes().getLength() == 0;
	    boolean emptyText = node.getNodeType() == Node.TEXT_NODE 
	          && node.getNodeValue().trim().isEmpty();

	    if (emptyElement || emptyText) {
	        if(!node.hasAttributes()) {
	            node.getParentNode().removeChild(node);
	        }
	    }

	}
	public String removeEmptyNodes(String document) throws Exception{
		 NodeList nList=DocumentBuilderFactory.newInstance().newDocumentBuilder()
         .parse(new InputSource(new StringReader(document))).getElementsByTagName("testRoot");
		 removeEmptyNodes(nList.item(0));
		 org.w3c.dom.Document document1 = nList.item(0).getOwnerDocument();
		 DOMImplementationLS domImplLS = (DOMImplementationLS) document1 .getImplementation();
		 LSSerializer serializer = domImplLS.createLSSerializer();
		 String str = serializer.writeToString( nList.item(0));
		 return str;
		
			
	
	}
	
	/**
	 * Add any cleanup code or perform any operations after all iterations are performed.
	 */
	public void finish() throws Exception {
		
	}
}
