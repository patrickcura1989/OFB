//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.ebsqafwk;

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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Types;
import javax.swing.UIManager;
import lib.*;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class GENAPILIB extends FuncLibraryWrapper
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

	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

	/**
	 * This method prompts the user for the DB Connection Details. It creates the input box and then user enters the details for the DB. The details are set as session  variables  which will be available throughout the session. To get the details user would have to  create the object of this class and call this method. This method does not return anything and this is why it is set as return type void. <p> Call for this method can be done as follows:<br> EBSLibrary.oracle_prompt_sql() <p>     To access the session variables user would need to do following <p>	    getVariables().get("env_dbq");<br> getVariables().get("env_dsn);<br> getVariables().get("env_user");<br> getVariables().get("env_pw");<br> getVariables().get("env_server");<br> getVariables().get("env_port");<br> <p>
	 */
	public void oracle_prompt_sql() throws AbstractScriptException {
		checkInit();
		callFunction("oracle_prompt_sql");
	}

	/**
	 * Connecting to Database Host, Port and Instant Name are stored in the global variables
	 * @throws Exception
	 */
	public void connectDB() throws AbstractScriptException {
		checkInit();
		callFunction("connectDB");
	}

	/**
	 * Execute a script
	 * @param anonymousBlock Anonymous Block
	 * @return
	 * @throws Exception
	 */
	public String executeScript(String anonymousBlock) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("executeScript", anonymousBlock);
	}

	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public void commit() throws AbstractScriptException {
		checkInit();
		callFunction("commit");
	}

	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public void verifyData(String expectedData, String actualData, String Context) throws AbstractScriptException {
		checkInit();
		callFunction("verifyData", expectedData, actualData, Context);
	}

	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public void conditionalCommit(String expectedValue, String actualValue) throws AbstractScriptException {
		checkInit();
		callFunction("conditionalCommit", expectedValue, actualValue);
	}

	/**
	 * This method is used to close an existing database connection		 		
	 */
	public void closeConnection() throws AbstractScriptException {
		checkInit();
		callFunction("closeConnection");
	}

	/**
	 * This method is used to parse the Scipt output and converts the output to Key-Value pair of PLSQL Variable Name and Value
	 * @param scriptOutput   Script Output returned through CLOB Protocol		   		 
	 */
	public void parseOutputVariables(String scriptOutput) throws AbstractScriptException {
		checkInit();
		callFunction("parseOutputVariables", scriptOutput);
	}

	public String GetErrorHandlingBlock() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("GetErrorHandlingBlock");
	}

	/**
	 * This method is used to select some values from a database table and assign then to openscript variables		 		
	 */
	public void selectQuery(String tableName, HashMap<String, String> columns, ArrayList<String[]> query_conditions) throws AbstractScriptException {
		checkInit();
		callFunction("selectQuery", tableName, columns, query_conditions);
	}

	/**
	 * This method is used to insert data into an interface table from an excel sheet		 		
	 */
	public void insertTable(String tableName, String sheetName) throws AbstractScriptException {
		checkInit();
		callFunction("insertTable", tableName, sheetName);
	}

	public void setProperty(String key, String value) throws AbstractScriptException {
		checkInit();
		callFunction("setProperty", key, value);
	}

	public String getProperty(String key) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getProperty", key);
	}

	/**
	 * This method is used to enable DBMS Output 		 		
	 * @param bufferSize   Specify the required buffer size		   
	 */
	public void enableOutput(int bufferSize) throws AbstractScriptException {
		checkInit();
		callFunction("enableOutput", bufferSize);
	}

	/**
	 * This method is used to display  DBMS Output 		 		 	  
	 */
	public void showOutput() throws AbstractScriptException {
		checkInit();
		callFunction("showOutput");
	}

	/**
	 * This method is used to disable and close DBMS Output  		 		 	  
	 */
	public void disableOutput() throws AbstractScriptException {
		checkInit();
		callFunction("disableOutput");
	}

	public void createPLSQLAnonymousBlock(String block) throws AbstractScriptException {
		checkInit();
		callFunction("createPLSQLAnonymousBlock", block);
	}

}

