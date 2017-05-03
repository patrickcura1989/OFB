//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.oracle.outsp.core;

import java.io.IOException;
import java.io.StringReader;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.http.api.*;
import oracle.oats.scripting.modules.http.api.HTTPService.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webService.api.*;
import oracle.oats.scripting.modules.webService.api.WSService.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;

public class WSVALIDATELIB extends FuncLibraryWrapper
{

	public void initialize() throws AbstractScriptException {
		checkInit();
		callFunction("initialize");
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws AbstractScriptException {
		checkInit();
		callFunction("run");
	}

	/**
	 * Add any cleanup code or perform any operations after all iterations are performed.
	 */
	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

	public void elementNotNull(String responseTag)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementNotNull", responseTag);
	}

	public void elementIsNull(String responseTag)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementIsNull", responseTag);
	}

	public void elementValueEquals(String responseTag, String expectedValue)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementValueEquals", responseTag, expectedValue);
	}

	public void elementValueNotEquals(String responseTag, String expectedValue)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementValueNotEquals", responseTag, expectedValue);
	}

	public void elementValueGreaterThan(String responseTag,
			String valueToCompare) throws AbstractScriptException {
		checkInit();
		callFunction("elementValueGreaterThan", responseTag, valueToCompare);
	}

	public void elementValueGreaterThanEqualTo(String responseTag,
			String valueToCompare) throws AbstractScriptException {
		checkInit();
		callFunction("elementValueGreaterThanEqualTo", responseTag,
				valueToCompare);
	}

	public void elementValueLesserThan(String responseTag, String valueToCompare)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementValueLesserThan", responseTag, valueToCompare);
	}

	public void elementValueLesserThanEqualTo(String responseTag,
			String valueToCompare) throws AbstractScriptException {
		checkInit();
		callFunction("elementValueLesserThanEqualTo", responseTag,
				valueToCompare);
	}

	public void elementContains(String responseTag, String valueToBeChecked)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementContains", responseTag, valueToBeChecked);
	}

	public void elementNotContains(String responseTag, String valueToBeChecked)
			throws AbstractScriptException {
		checkInit();
		callFunction("elementNotContains", responseTag, valueToBeChecked);
	}

	public void ResponseNotContains(String value)
			throws AbstractScriptException {
		checkInit();
		callFunction("ResponseNotContains", value);
	}

	public void responseContains(String value) throws AbstractScriptException {
		checkInit();
		callFunction("responseContains", value);
	}

	public String setVariableFromResponseFunc(String elementXpath)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("setVariableFromResponseFunc",
				elementXpath);
	}

}

