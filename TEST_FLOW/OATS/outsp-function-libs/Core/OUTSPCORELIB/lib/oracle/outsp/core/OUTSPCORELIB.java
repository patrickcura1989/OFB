//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.oracle.outsp.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.webService.api.*;
import oracle.oats.scripting.modules.webService.api.WSService.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.http.api.*;
import oracle.oats.scripting.modules.http.api.HTTPService.*;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;

public class OUTSPCORELIB extends FuncLibraryWrapper
{

	public void runBatchFile(String fileName) throws AbstractScriptException {
		checkInit();
		callFunction("runBatchFile", fileName);
	}

	public void killBatchFile(String processName)
			throws AbstractScriptException {
		checkInit();
		callFunction("killBatchFile", processName);
	}

	public String getCurrentTimeInMilliSeconds() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentTimeInMilliSeconds");
	}

	public String randomStringWithGivenRange(int lo, int hi)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomStringWithGivenRange", lo, hi);
	}

	public String randomString() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomString");
	}

	public String compare2Strings(String String_A, String String_B)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("compare2Strings", String_A, String_B);
	}

	public String randomNumberUsingDateTime() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomNumberUsingDateTime");
	}

	public String getCurrentDateTimeWithGivenDateFormat(String dFormat)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentDateTimeWithGivenDateFormat",
				dFormat);
	}

	public String getDateDiffInSecsWithGivenDateFormat(String dateStart,
			String dateStop, String dFormat) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getDateDiffInSecsWithGivenDateFormat",
				dateStart, dateStop, dFormat);
	}

	public String getAdjustedTimeWithGivenDateTime(String dateTime,
			String offset, String dFormat) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getAdjustedTimeWithGivenDateTime",
				dateTime, offset, dFormat);
	}

	/**
	 * The function returns the Date and Time after adding the specified offset to current date and current time in the specified date time format. This is a case where the user has inputs to the function as date and time separately rather than datetime as a single variable as is the case with previous function
	 * @param cuDate ,cuTime,offset,dFormat
	 * @return  String Author: 
	 */
	public String getAdjustedTimeWithCurrentDateTime(String offset,
			String dFormat) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getAdjustedTimeWithCurrentDateTime",
				offset, dFormat);
	}

	public String getAdjustedTimeWithGivenDateAndTime(String cuDate,
			String cuTime, String offset, String dFormat)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getAdjustedTimeWithGivenDateAndTime",
				cuDate, cuTime, offset, dFormat);
	}

	/**
	 * The function adds number of days to the current date and returns it with the specified format.
	 * @param days ,dFormat
	 * @return  String Author:  
	 */
	public String addDaysToCurrentDateWithGivenFormat(String noOfDays,
			String dFormat) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("addDaysToCurrentDateWithGivenFormat",
				noOfDays, dFormat);
	}

	public String serverDate() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("serverDate");
	}

	/**
	 * Executes SQL and returns record set
	 * @param Query
	 */
	public ResultSet executeSQLQry(String Query) throws AbstractScriptException {
		checkInit();
		return (ResultSet) callFunction("executeSQLQry", Query);
	}

	/**
	 * Executes SQL and returns record set
	 * @param Query
	 */
	public ResultSet executeSQLQryWithGivenDBDetails(String Query,
			String ConnectionString, String DBUsername, String DBPassword)
			throws AbstractScriptException {
		checkInit();
		return (ResultSet) callFunction("executeSQLQryWithGivenDBDetails",
				Query, ConnectionString, DBUsername, DBPassword);
	}

	public String serverTime() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("serverTime");
	}

	public void waitForTime(String strWaitTimeInMinutes)
			throws AbstractScriptException {
		checkInit();
		callFunction("waitForTime", strWaitTimeInMinutes);
	}

	/**
	 * Verify if batch is running in the last x mins
	 */
	public String verifyLastBatchRun(String Batch_CD, String strMaXTimeToCheck)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("verifyLastBatchRun", Batch_CD,
				strMaXTimeToCheck);
	}

	public String getCurrentOffsetTime(String cuDate, String cuTime,
			String offset, String timeFormat) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentOffsetTime", cuDate, cuTime,
				offset, timeFormat);
	}

	public String addDaysToAGivenDate(String date, String noOfDays)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("addDaysToAGivenDate", date, noOfDays);
	}

	public String randomNumber() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomNumber");
	}

	/**
	 * Gets wait condition state. This funciton should be called in while loop
	 * @param StartTime ,TimeInMinutes
	 * @return  boolean Author:  
	 */
	public void createFile(String FilePath) throws AbstractScriptException {
		checkInit();
		callFunction("createFile", FilePath);
	}

	public String compare2Files(String strFileName_A, String strFileName_B)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("compare2Files", strFileName_A,
				strFileName_B);
	}

	/**
	 * Function to copy files from source to destination
	 * @param srcFile ,destFile
	 * @returnType  void Author:  
	 */
	public void copyFile(String srcFilePath, String destFilePath)
			throws AbstractScriptException {
		checkInit();
		callFunction("copyFile", srcFilePath, destFilePath);
	}

	/**
	 * Function to delete the files
	 * @param file
	 * @returnType  void Author:  
	 */
	public void deleteFile(String filePath) throws AbstractScriptException {
		checkInit();
		callFunction("deleteFile", filePath);
	}

	/**
	 * Executes SQL query for update query
	 * @param Query ,dbDetails
	 * @return  boolean Author:  
	 */
	public String executeSQLQryUpdate(String Query, String ConnectionString,
			String DBUsername, String DBPassword)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("executeSQLQryUpdate", Query,
				ConnectionString, DBUsername, DBPassword);
	}

	/**
	 * The function returns the count of distinct objects in a specific column in a table
	 * @param tableName , columnName,condition, dbDetails
	 * @return  int Author:  
	 */
	public String getDistinctObjects(String tableName, String columnName,
			String condition, String ConnectionString, String DBUsername,
			String DBPassword) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getDistinctObjects", tableName,
				columnName, condition, ConnectionString, DBUsername, DBPassword);
	}

	/**
	 * The function returns the count of M1 objects in a specific table
	 * @param tableName , condition, dbDetails
	 * @return  int Author:  
	 */
	public String setVariableValueUsingListIndex(String listVariableName,
			String index) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("setVariableValueUsingListIndex",
				listVariableName, index);
	}

	public void closeConnection() throws AbstractScriptException {
		checkInit();
		callFunction("closeConnection");
	}

	public String executeSQLQryForSingleRecord(String Query, String recId)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("executeSQLQryForSingleRecord", Query,
				recId);
	}

	public String appendStrings(String strValue1, String strValue2,
			String strValue3, String strValue4, String strValue5,
			String strValue6) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("appendStrings", strValue1, strValue2,
				strValue3, strValue4, strValue5, strValue6);
	}

	public String getCurrentMonth() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentMonth");
	}

	public void initialize() throws AbstractScriptException {
		checkInit();
		callFunction("initialize");
	}

	public void run() throws AbstractScriptException {
		checkInit();
		callFunction("run");
	}

	public void finish() throws AbstractScriptException {
		checkInit();
		callFunction("finish");
	}

}

