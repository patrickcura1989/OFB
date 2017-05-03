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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;

public class EXCELLIB extends FuncLibraryWrapper
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

	public void getExcel(String filePath) throws AbstractScriptException {
		checkInit();
		callFunction("getExcel", filePath);
	}

	public void getExcel(String filePath, int columnRow) throws AbstractScriptException {
		checkInit();
		callFunction("getExcel", filePath, columnRow);
	}

	public void closeExcel() throws AbstractScriptException {
		checkInit();
		callFunction("closeExcel");
	}

	public ArrayList<String> getSheetNames() throws AbstractScriptException {
		checkInit();
		return (ArrayList<String>) callFunction("getSheetNames");
	}

	public ArrayList<String> getColumns(String sheetname) throws AbstractScriptException {
		checkInit();
		return (ArrayList<String>) callFunction("getColumns", sheetname);
	}

	public ArrayList<String> getColumns(String sheetname, int columnRow) throws AbstractScriptException {
		checkInit();
		return (ArrayList<String>) callFunction("getColumns", sheetname, columnRow);
	}

	public int getColumnCount(String sheetname) throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("getColumnCount", sheetname);
	}

	public int getColumnCount(String sheetname, int columnRow) throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("getColumnCount", sheetname, columnRow);
	}

	public int getRowCount(String sheetname) throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("getRowCount", sheetname);
	}

	public String getvalue(String sheetname, int row, String columnname) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getvalue", sheetname, row, columnname);
	}

	public String getvalue(String sheetname, int rw, int cl) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getvalue", sheetname, rw, cl);
	}

	public void setvalue(String sheetname, int row, String columnname, String data) throws AbstractScriptException {
		checkInit();
		callFunction("setvalue", sheetname, row, columnname, data);
	}

	public void setvalue(String sheetname, int rw, int cl, String data) throws AbstractScriptException {
		checkInit();
		callFunction("setvalue", sheetname, rw, cl, data);
	}

	public HashMap<String, String> getRowData(String sheetname, String keycolumn, String keyvalue) throws AbstractScriptException {
		checkInit();
		return (HashMap<String, String>) callFunction("getRowData", sheetname, keycolumn, keyvalue);
	}

}

