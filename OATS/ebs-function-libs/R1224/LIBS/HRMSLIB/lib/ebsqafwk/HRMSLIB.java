//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.ebsqafwk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

public class HRMSLIB extends FuncLibraryWrapper
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
	 * Create Time Card for work Order
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public void setTimeCard(String day, String startTime, String endTime) throws AbstractScriptException {
		checkInit();
		callFunction("setTimeCard", day, startTime, endTime);
	}

	/**
	 * Select Current Week Period
	 * @throws Exception
	 */
	public void selectTimeCardPeriod(String dateTime, String dateTimeFormat, String noOfWeeks) throws AbstractScriptException {
		checkInit();
		callFunction("selectTimeCardPeriod", dateTime, dateTimeFormat, noOfWeeks);
	}

	public String getCurrentWeek(String time, String format, String nextWeek) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getCurrentWeek", time, format, nextWeek);
	}

	public String getWeekDateFormat(String time, String format) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("getWeekDateFormat", time, format);
	}

}

