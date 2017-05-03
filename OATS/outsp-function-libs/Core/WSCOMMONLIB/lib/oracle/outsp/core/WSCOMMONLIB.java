//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.oracle.outsp.core;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.xml.MarkupBuilder;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import org.bouncycastle.util.encoders.Base64;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;
import com.predic8.wstool.creator.RequestTemplateCreator;
import com.predic8.wstool.creator.SOARequestCreator;
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
import system.DateTime;
import lib.*;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;

public class WSCOMMONLIB extends FuncLibraryWrapper
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

	public void initalizeSetUp(String outputFileName, String runTimePath,
			String strScriptPath) throws AbstractScriptException {
		checkInit();
		callFunction("initalizeSetUp", outputFileName, runTimePath,
				strScriptPath);
	}

	public void LoadGlobalVariablesFromPropertiesFile(String strFilePath)
			throws AbstractScriptException {
		checkInit();
		callFunction("LoadGlobalVariablesFromPropertiesFile", strFilePath);
	}

	/**
	 * Logs Results Output into specified file path
	 * @param FilePath
	 * @param Description
	 */
	public void logComments(String outputfilename, String Description,
			String aStrTestStatus) throws AbstractScriptException {
		checkInit();
		callFunction("logComments", outputfilename, Description, aStrTestStatus);
	}

	/**
	 * Logs Results Output into specified file path - overloaded to log comment without status
	 * @param FilePath
	 * @param Description
	 */
	public void logComments(String outputfilename, String Description)
			throws AbstractScriptException {
		checkInit();
		callFunction("logComments", outputfilename, Description);
	}

	/**
	 * Creates file. If there is any existing then deletes it an creates a new file.
	 * @param FileName  --  filename
	 */
	public void CreateFile(String FileName) throws AbstractScriptException {
		checkInit();
		callFunction("CreateFile", FileName);
	}

	public void CreateWSRequest() throws AbstractScriptException {
		checkInit();
		callFunction("CreateWSRequest");
	}

	/**
	 * Creates complete WSDL URL
	 * @param WSDLName
	 */
	public String getCompleteXAIURLFormat(String WebServiceName)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCompleteXAIURLFormat", WebServiceName);
	}

	public void convertWSDLToXML(String aStrWSDLURL, String aStrWSDLName)
			throws AbstractScriptException {
		checkInit();
		callFunction("convertWSDLToXML", aStrWSDLURL, aStrWSDLName);
	}

	public void formatXAI(String aStrXAIFilepath, String operationName,
			String aStrWSDLName) throws AbstractScriptException {
		checkInit();
		callFunction("formatXAI", aStrXAIFilepath, operationName, aStrWSDLName);
	}

	public String readXmlSchema(String file) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("readXmlSchema", file);
	}

	public String processXAIUsingDatabankWithXpath(String XAIString,
			String DatabankName) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("processXAIUsingDatabankWithXpath",
				XAIString, DatabankName);
	}

	public void processDatabank(String DatabankName)
			throws AbstractScriptException {
		checkInit();
		callFunction("processDatabank", DatabankName);
	}

	public String constructChildElementBlock(String[] strArr,
			String strNameSpace, String value) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("constructChildElementBlock",
				(Object) strArr, strNameSpace, value);
	}

	public void singleElementHandler(String strElement, String Value)
			throws AbstractScriptException {
		checkInit();
		callFunction("singleElementHandler", strElement, Value);
	}

	public String getDocumentString() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getDocumentString");
	}

	@SuppressWarnings("unchecked")
	public void multiChildElementHandler(String strElement, String Value,
			Integer rowIndex) throws AbstractScriptException {
		checkInit();
		callFunction("multiChildElementHandler", strElement, Value, rowIndex);
	}

	public void ProcessWSRequest() throws AbstractScriptException {
		checkInit();
		callFunction("ProcessWSRequest");
	}

	public void generateAndSendReport() throws AbstractScriptException {
		checkInit();
		callFunction("generateAndSendReport");
	}

	/**
	 * It Creates email message body based on scenario
	 */
	public String buildMessageBody(String aStrScenarioReportHeader,
			String outputFilePath) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("buildMessageBody",
				aStrScenarioReportHeader, outputFilePath);
	}

	public String getScenarioNameFromFilePath(String outputFilePath)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getScenarioNameFromFilePath",
				outputFilePath);
	}

	/**
	 * Reads output File And Creates html email body Rows Data
	 * @param filePath
	 */
	public String readFileAndCreateRowsData(String filePath)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("readFileAndCreateRowsData", filePath);
	}

	/**
	 * Created html Email BodyT able
	 * @param RowsData
	 * @param TableHeaderText
	 */
	public String createFinalHtmlEmailBodyTable(String RowsData,
			String TableHeaderText, Boolean IsEmailBodyMessagePart)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("createFinalHtmlEmailBodyTable", RowsData,
				TableHeaderText, IsEmailBodyMessagePart);
	}

	public boolean sendGenericMail(String aStrRecipients, String aStrSubject,
			String aStrMessage, String SMTP_HOST_NAME, String SMTP_PORT)
			throws AbstractScriptException {
		checkInit();
		return (Boolean) callFunction("sendGenericMail", aStrRecipients,
				aStrSubject, aStrMessage, SMTP_HOST_NAME, SMTP_PORT);
	}

	/**
	 * To Send Mail
	 * @param aStrRecipients
	 * @param aStrSubject
	 * @param aStrMessage
	 * @param aStrFrom
	 */
	public void sendMail(String aStrRecipients, String aStrSubject,
			String aStrMessage, String aStrFrom, String SMTP_HOST_NAME,
			String SMTP_PORT) throws AbstractScriptException {
		checkInit();
		callFunction("sendMail", aStrRecipients, aStrSubject, aStrMessage,
				aStrFrom, SMTP_HOST_NAME, SMTP_PORT);
	}

	public boolean validateEmail(String sEmail) throws AbstractScriptException {
		checkInit();
		return (Boolean) callFunction("validateEmail", sEmail);
	}

}

