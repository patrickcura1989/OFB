//WARNING!
//This file was created by Oracle OpenScript.
//Don't change it.

package lib.com.acc;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.applet.api.*;
import oracle.oats.scripting.modules.basic.api.internal.FuncLibraryWrapper;

public class OracleFinancialsLibrary extends FuncLibraryWrapper
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

	public void web_login(String userName, String password)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_login", userName, password);
	}

	public void web_login_change_password(String userName, String password)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_login_change_password", userName, password);
	}

	public void web_clickLink(String xpath) throws AbstractScriptException {
		checkInit();
		callFunction("web_clickLink", xpath);
	}

	public void forms_setTextField(String colName, int row, String val)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_setTextField", colName, row, val);
	}

	public void forms_setTextFieldToNextLine(String colName, String val)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_setTextFieldToNextLine", colName, val);
	}

	public void forms_setTextFieldThenPressEnter(String colName, int row,
			String val) throws AbstractScriptException {
		checkInit();
		callFunction("forms_setTextFieldThenPressEnter", colName, row, val);
	}

	public void forms_textFieldInvokeKey(String path, String key)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_textFieldInvokeKey", path, key);
	}

	public int forms_getEditableTextFieldRow(String colName, int row)
			throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("forms_getEditableTextFieldRow", colName,
				row);
	}

	public void forms_setTextField_NoTab(String colName, int row, String val)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_setTextField_NoTab", colName, row, val);
	}

	public void web_waitForBrowserReady(String xpath, int timeout)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_waitForBrowserReady", xpath, timeout);
	}

	public void forms_clickTextField(String colName, int row)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_clickTextField", colName, row);
	}

	public void forms_setFlexWindow(String field, String val)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_setFlexWindow", field, val);
	}

	public void forms_setFlexWindowTextField(String field, String colHeader,
			String val) throws AbstractScriptException {
		checkInit();
		callFunction("forms_setFlexWindowTextField", field, colHeader, val);
	}

	public void forms_flexWindowClickOK() throws AbstractScriptException {
		checkInit();
		callFunction("forms_flexWindowClickOK");
	}

	public void forms_flexWindowClickCancel() throws AbstractScriptException {
		checkInit();
		callFunction("forms_flexWindowClickCancel");
	}

	public void forms_setTextFieldFocus(String colName, int row)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_setTextFieldFocus", colName, row);
	}

	public void forms_waitForWindowExists(String title, int second)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForWindowExists", title, second);
	}

	public void forms_waitForWindowsExistsByTitle(String title, int second)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForWindowsExistsByTitle", title, second);
	}

	public void forms_waitForFlexWindowsExists(String title, int second)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForFlexWindowsExists", title, second);
	}

	public void forms_waitForWindowNotExists(String title, int second)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForWindowNotExists", title, second);
	}

	public void forms_assertTransactionBatchesWindowExists()
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_assertTransactionBatchesWindowExists");
	}

	public void forms_closeWindow(String title) throws AbstractScriptException {
		checkInit();
		callFunction("forms_closeWindow", title);
	}

	public void forms_close_active_window() throws AbstractScriptException {
		checkInit();
		callFunction("forms_close_active_window");
	}

	public void creatingInvoice(String name, int[] count)
			throws AbstractScriptException {
		checkInit();
		callFunction("creatingInvoice", name, (Object) count);
	}

	public void forms_invokeResponsibility() throws AbstractScriptException {
		checkInit();
		callFunction("forms_invokeResponsibility");
	}

	public void forms_selectResponsibility(String responsibility)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectResponsibility", responsibility);
	}

	public void forms_spreadTableClickLink(String path, int row, int col)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_spreadTableClickLink", path, row, col);
	}

	public void forms_spreadTableFocusRow(String path, int row)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_spreadTableFocusRow", path, row);
	}

	public void forms_selectNavigator_function(String path)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectNavigator_function", path);
	}

	public void forms_selectNavigator_Path(String path)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectNavigator_Path", path);
	}

	public void forms_clickInvoiceWorkbenchTab(String title)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_clickInvoiceWorkbenchTab", title);
	}

	public void forms_clickRecurringInvoicesTab(String title)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_clickRecurringInvoicesTab", title);
	}

	public void forms_clickCollectionsTab(String title)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_clickCollectionsTab", title);
	}

	public void forms_clickTab(String tabName, String title)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_clickTab", tabName, title);
	}

	public void web_refreshPaymentProcessrequest(String req_id, String status,
			int timeout) throws AbstractScriptException {
		checkInit();
		callFunction("web_refreshPaymentProcessrequest", req_id, status,
				timeout);
	}

	public String randomString(int len) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomString", len);
	}

	public String randomNumNonRepeat(int len) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomNumNonRepeat", len);
	}

	public String randomStringNonRepeat(int len) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("randomStringNonRepeat", len);
	}

	public void forms_clickButton(String btnName)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_clickButton", btnName);
	}

	public void forms_save(String windowTitle) throws AbstractScriptException {
		checkInit();
		callFunction("forms_save", windowTitle);
	}

	public void forms_savePath(String path) throws AbstractScriptException {
		checkInit();
		callFunction("forms_savePath", path);
	}

	public void forms_tickCheckBox(String path, boolean checked)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_tickCheckBox", path, checked);
	}

	public void forms_query_mode(String path) throws AbstractScriptException {
		checkInit();
		callFunction("forms_query_mode", path);
	}

	public void forms_find(String windowName) throws AbstractScriptException {
		checkInit();
		callFunction("forms_find", windowName);
	}

	public void forms_waitForOracleFormsReady(int timeout, int interval)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForOracleFormsReady", timeout, interval);
	}

	public void forms_run(String path) throws AbstractScriptException {
		checkInit();
		callFunction("forms_run", path);
	}

	public void forms_requests(String path) throws AbstractScriptException {
		checkInit();
		callFunction("forms_requests", path);
	}

	public void forms_validateTextFieldValue(String path, String value)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_validateTextFieldValue", path, value);
	}

	public void forms_validateTextFieldExists(String path)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_validateTextFieldExists", path);
	}

	public void forms_validateTextFieldNumberValue(String path, Float value)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_validateTextFieldNumberValue", path, value);
	}

	public void forms_validateCheckboxValue(String path, boolean value)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_validateCheckboxValue", path, value);
	}

	public String forms_getText(String path) throws AbstractScriptException {
		checkInit();
		return (String) callFunction("forms_getText", path);
	}

	public String web_getElementText(String path)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("web_getElementText", path);
	}

	public void web_validateElementContainsText(String path, String value)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_validateElementContainsText", path, value);
	}

	public void forms_waitForStatusChanged(String path, int timeout,
			int interval, String expectedV) throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForStatusChanged", path, timeout, interval,
				expectedV);
	}

	public void forms_waitForStatusChangedDoNotCloseWindow(String path,
			int timeout, int interval, String expectedV)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForStatusChangedDoNotCloseWindow", path,
				timeout, interval, expectedV);
	}

	public void forms_waitForStatusChangedByRequestID(String path,
			String requestID, int timeout, int interval, String expectedVa)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForStatusChangedByRequestID", path, requestID,
				timeout, interval, expectedVa);
	}

	public void forms_waitForStatusChangedByName(String path, String name,
			int timeout, int interval, String expectedVa)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_waitForStatusChangedByName", path, name, timeout,
				interval, expectedVa);
	}

	public String forms_findRequestByName(String name)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("forms_findRequestByName", name);
	}

	public int forms_findItemInJournalsByBatchName(String name)
			throws AbstractScriptException {
		checkInit();
		return (Integer) callFunction("forms_findItemInJournalsByBatchName",
				name);
	}

	public String forms_getSubmitRequestIDByIndex(String path, int index)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("forms_getSubmitRequestIDByIndex", path,
				index);
	}

	public void forms_activateMainWindow() throws AbstractScriptException {
		checkInit();
		callFunction("forms_activateMainWindow");
	}

	public void forms_close_w(String title) throws AbstractScriptException {
		checkInit();
		callFunction("forms_close_w", title);
	}

	public String[] forms_getOpenWindowsTitle() throws AbstractScriptException {
		checkInit();
		return (String[]) callFunction("forms_getOpenWindowsTitle");
	}

	public void forms_assertInvoiceWorkbench(String status)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_assertInvoiceWorkbench", status);
	}

	public void forms_assertInvoiceWorkbenchStatus(String status)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_assertInvoiceWorkbenchStatus", status);
	}

	public String forms_getClosedPeriod() throws AbstractScriptException {
		checkInit();
		return (String) callFunction("forms_getClosedPeriod");
	}

	public boolean forms_checkValue(String path, String status)
			throws AbstractScriptException {
		checkInit();
		return (Boolean) callFunction("forms_checkValue", path, status);
	}

	public void forms_textFieldOpenDialog(String colName, int index)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_textFieldOpenDialog", colName, index);
	}

	public void forms_flexWindowOpenDialog(String colName)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_flexWindowOpenDialog", colName);
	}

	public void forms_activateWindow(String title)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_activateWindow", title);
	}

	public void forms_selectItemInListOfValues(String item)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectItemInListOfValues", item);
	}

	public void forms_selectFirstItemInListOfValues()
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectFirstItemInListOfValues");
	}

	public void forms_selecttemInListOfValues(String item)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selecttemInListOfValues", item);
	}

	public void forms_choiceBox_clickButton(String btnName)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_choiceBox_clickButton", btnName);
	}

	public String forms_postJournalsByName(String batchName)
			throws AbstractScriptException {
		checkInit();
		return (String) callFunction("forms_postJournalsByName", batchName);
	}

	public void web_waitForWindowsExists(String title, int timeout)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_waitForWindowsExists", title, timeout);
	}

	public void web_waitForWindowsExistsUsingPath(String path, int timeout)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_waitForWindowsExistsUsingPath", path, timeout);
	}

	public void web_expandRepositoryManagementTree(String item)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_expandRepositoryManagementTree", item);
	}

	public void web_viewRepositoryManagementItem(String item)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_viewRepositoryManagementItem", item);
	}

	public void forms_selectItemInList(String list, String item)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectItemInList", list, item);
	}

	public void forms_selectRadioButton(String name)
			throws AbstractScriptException {
		checkInit();
		callFunction("forms_selectRadioButton", name);
	}

	public void web_clickTextBox(String xpath) throws AbstractScriptException {
		checkInit();
		callFunction("web_clickTextBox", xpath);
	}

	public void web_setTextBox(String xpath, String value)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_setTextBox", xpath, value);
	}

	public void web_selectBoxSelectOption(String xpath, String value)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_selectBoxSelectOption", xpath, value);
	}

	public void web_clickButton(String xpath) throws AbstractScriptException {
		checkInit();
		callFunction("web_clickButton", xpath);
	}

	public void web_clickCheckbox(String xpath) throws AbstractScriptException {
		checkInit();
		callFunction("web_clickCheckbox", xpath);
	}

	public void web_clickImage(String xpath) throws AbstractScriptException {
		checkInit();
		callFunction("web_clickImage", xpath);
	}

	public boolean web_elementExists(String xpath)
			throws AbstractScriptException {
		checkInit();
		return (Boolean) callFunction("web_elementExists", xpath);
	}

	public void web_validateElementExists(String xpath)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_validateElementExists", xpath);
	}

	public void web_validateElementWithTextExists(String xpath, String text)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_validateElementWithTextExists", xpath, text);
	}

	public void web_clickTDLinkInTable(String windowTitle,
			String tableAttribute, String attributeValue, int rowIndex,
			int tdIndex) throws AbstractScriptException {
		checkInit();
		callFunction("web_clickTDLinkInTable", windowTitle, tableAttribute,
				attributeValue, rowIndex, tdIndex);
	}

	public void web_clickLastTDLinkInHomePageTable()
			throws AbstractScriptException {
		checkInit();
		callFunction("web_clickLastTDLinkInHomePageTable");
	}

	public void web_clickLatestTDLinkInHomePageTable()
			throws AbstractScriptException {
		checkInit();
		callFunction("web_clickLatestTDLinkInHomePageTable");
	}

	public void web_getLatestTDLinkInHomePageTable()
			throws AbstractScriptException {
		checkInit();
		callFunction("web_getLatestTDLinkInHomePageTable");
	}

	public void web_refreshUntilTextAppearsInHomePageTable(final String text,
			int timeout) throws AbstractScriptException {
		checkInit();
		callFunction("web_refreshUntilTextAppearsInHomePageTable", text,
				timeout);
	}

	public void web_navigateToLink(String path) throws AbstractScriptException {
		checkInit();
		callFunction("web_navigateToLink", path);
	}

	public void web_setTextArea(String path, String value)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_setTextArea", path, value);
	}

	public Queue<String> getEmailContent(int timeout, int expectedEmailItem)
			throws AbstractScriptException {
		checkInit();
		return (Queue<String>) callFunction("getEmailContent", timeout,
				expectedEmailItem);
	}

	public void web_assertOuputDisplayed() throws AbstractScriptException {
		checkInit();
		callFunction("web_assertOuputDisplayed");
	}

	public void web_assertPageContainsText(String title, String text)
			throws AbstractScriptException {
		checkInit();
		callFunction("web_assertPageContainsText", title, text);
	}

	public boolean checkProcessRunning(String processName)
			throws AbstractScriptException {
		checkInit();
		return (Boolean) callFunction("checkProcessRunning", processName);
	}		

}

