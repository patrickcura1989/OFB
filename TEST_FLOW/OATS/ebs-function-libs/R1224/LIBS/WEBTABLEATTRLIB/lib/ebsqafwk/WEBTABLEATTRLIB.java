//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.ebsqafwk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import oracle.oats.scripting.modules.webdom.api.elements.DOMElement;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.applet.api.*;
import lib.*;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class WEBTABLEATTRLIB extends FuncLibraryWrapper
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

	public String tableactions(String tableName, List<String[]> actions, HashMap<String, String> searchColumns) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("tableactions", tableName, actions, searchColumns);
	}

	public String tableactions(String tableName, HashMap<String, String> searchColumns, List<String[]> actions) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("tableactions", tableName, searchColumns, actions);
	}

	public String tableactions(String tableName, String rowNumber, List<String[]> actions) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("tableactions", tableName, rowNumber, actions);
	}

	/**
	 * Gets Table Object
	 * @param tableID -- It looks like "Select" or "Select;1" or "Select;1,document=0,form=0"
	 * @return
	 * @throws Exception
	 */
	public void getWebTableObject(String tableID) throws AbstractScriptException {
		checkInit();
		callFunction("getWebTableObject", tableID);
	}

	public void initializeWebTable(String tableAttribute, int tableIndex, int documentIndex, int formIndex, String listPath, int syncTime) throws AbstractScriptException {
		checkInit();
		callFunction("initializeWebTable", tableAttribute, tableIndex, documentIndex, formIndex, listPath, syncTime);
	}

	public List<DOMElement> getRowElements() throws AbstractScriptException {
		checkInit();
		return (List<DOMElement>) callFunction("getRowElements");
	}

	/**
	 * Click the button when the button name is passed from component
	 * @param tableName
	 * @param rowNumber
	 * @param actions
	 * @throws Exception
	 */
	public void clickButton(String tableName, int rowNumber, HashMap<String, String> actions) throws AbstractScriptException {
		checkInit();
		callFunction("clickButton", tableName, rowNumber, actions);
	}

	/**
	 * Click the Image when the alt image is passed from component
	 * @param tableName
	 * @param rowNumber
	 * @param actions
	 * @throws Exception
	 */
	public void clickImage(String tableName, int rowNumber, HashMap<String, String> actions) throws AbstractScriptException {
		checkInit();
		callFunction("clickImage", tableName, rowNumber, actions);
	}

	/**
	 * Click the Link when the button name is passed from component
	 * @param tableName
	 * @param rowNumber
	 * @param actions
	 * @throws Exception
	 */
	public void clickLink(String tableName, int rowNumber, HashMap<String, String> actions) throws AbstractScriptException {
		checkInit();
		callFunction("clickLink", tableName, rowNumber, actions);
	}

	/**
	 * Click the link when the link name is passed from test data
	 * @param rowNumber
	 * @param expectedLinkValue
	 * @throws Exception
	 */
	public void clickLink(int rowNumber, String expectedLinkValue) throws AbstractScriptException {
		checkInit();
		callFunction("clickLink", rowNumber, expectedLinkValue);
	}

	public int findRowNumber(HashMap<String, String> searchColumns) throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("findRowNumber", searchColumns);
	}

	/**
	 * Get Row Number
	 * @param colNames
	 * @param rowValues
	 * @return
	 * @throws Exception
	 */
	public int getRowNumber(String[] colNames, String[] rowValues) throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("getRowNumber", (Object) colNames, (Object) rowValues);
	}

	public List<String> getRowData(int rowNumber) throws AbstractScriptException {
		checkInit();
		return (List<String>) callFunction("getRowData", rowNumber);
	}

	public List<String> getRowData(int rowNumber, List<String> componentTags) throws AbstractScriptException {
		checkInit();
		return (List<String>) callFunction("getRowData", rowNumber, componentTags);
	}

	public DOMElement getCellElement(int rowNumber, String compType, String expectedComponentValue, int compSequence) throws AbstractScriptException {
		checkInit();
		return (DOMElement) callFunction("getCellElement", rowNumber, compType, expectedComponentValue, compSequence);
	}

	public String getCurrentWindowXPath() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentWindowXPath");
	}

	public String getCurrentWindowTitle() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentWindowTitle");
	}

	public boolean compareStrings(String expectedString, String actualString) throws AbstractScriptException {
		checkInit();
		return (Boolean) callFunction("compareStrings", expectedString, actualString);
	}

	public String[] getKeysFromHashMapValuesAreList(HashMap<String, List<String>> searchColumns) throws AbstractScriptException {
		checkInit();
		return (String[]) callFunction("getKeysFromHashMapValuesAreList", searchColumns);
	}

	public String[] getKeysFromHashMap(HashMap<String, String> searchColumns) throws AbstractScriptException {
		checkInit();
		return (String[]) callFunction("getKeysFromHashMap", searchColumns);
	}

	public String[] getValuesFromHashMap(HashMap<String, String> searchColumns) throws AbstractScriptException {
		checkInit();
		return (String[]) callFunction("getValuesFromHashMap", searchColumns);
	}

	@SuppressWarnings("unchecked")
	public List<String>[] getValuesFromHashMapValuesAreList(HashMap<String, List<String>> searchColumns) throws AbstractScriptException {
		checkInit();
		return (List<String>[]) callFunction("getValuesFromHashMapValuesAreList", searchColumns);
	}

}

