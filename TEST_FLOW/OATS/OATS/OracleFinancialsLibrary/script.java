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

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	

	public void initialize() throws Exception {
		browser.launch();
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}

	public void finish() throws Exception {
	}
	
	public void web_login (String userName,String password) throws Exception{
		WebDriver.web_login(this, web, Constant.ORACLE_URL , userName, password) ;
	}
	
	public void web_login_change_password (String userName,String password) throws Exception{
		WebDriver.web_login_change_password(this, web, Constant.ORACLE_URL , userName, password) ;
	}
	
	public void web_clickLink (String xpath)throws Exception{
		WebDriver.web_clickLink(this, web, xpath) ;
	}
	
	public void forms_setTextField (String colName, int row, String val) throws Exception{
		Forms.forms_setTextField(this, forms, colName, row, val, true, "NEXT_FIELD") ;	
	}
	
	public void forms_setTextFieldToNextLine (String colName, String val) throws Exception{
		Forms.forms_setTextFieldToNextLine(this, forms, colName, val, true, "NEXT_FIELD");
	}
	
	
	public void forms_setTextFieldThenPressEnter (String colName, int row, String val) throws Exception{
		Forms.forms_setTextField(this, forms, colName, row, val, true, "RETURN") ;	
	}
	
	
	public void forms_textFieldInvokeKey(String path, String key)  throws Exception
	{
		Forms.forms_textFieldInvokeKey(this, forms, path, key);
	}
	
	public int forms_getEditableTextFieldRow (String colName, int row) throws Exception{
		Forms.forms_setTextFieldFocus(this, forms, colName, row);
		while(!Forms.forms_getTextFieldEditable(this, forms, colName, row))
		{
			row++;
			Forms.forms_setTextFieldFocus(this, forms, colName, row);
		}
		return row;	
	}
	
	public void forms_setTextField_NoTab (String colName, int row, String val) throws Exception{
		Forms.forms_setTextField(this, forms, colName, row, val, false, "") ;	
	}
	
	public void web_waitForBrowserReady (String xpath, int timeout) throws Exception{
		WebDriver.web_waitForBrowserReady(this, web, xpath, timeout);   
	}
	
	public void forms_clickTextField (String colName, int row) throws Exception{
		Forms.forms_clickTextField(this, forms, colName, row) ;	
	}
	
	public void forms_setFlexWindow(String field, String val) throws Exception{
		Forms.forms_setFlexWindow(this, forms, field, val) ;	
	}
	
	public void forms_setFlexWindowTextField (String field, String colHeader,String val) throws Exception{
	    Forms.forms_setFlexWindowTextField(this, forms, field, colHeader, val) ;
	}
	
	public void forms_flexWindowClickOK() throws Exception{
		Forms.forms_flexWindowClickOK(this, forms) ;	
	}
	
	public void forms_flexWindowClickCancel() throws Exception{
		Forms.forms_flexWindowClickCancel(this, forms) ;	
	}
	
	public void forms_setTextFieldFocus (String colName, int row) throws Exception{
		Forms.forms_setTextFieldFocus(this, forms, colName, row) ;		
	}
	
	public void forms_waitForWindowExists (String title , int second) throws AbstractScriptException{
	   Forms.forms_waitForWindowExists(this, forms, title, second) ;		   
	}
	
	public void forms_waitForWindowsExistsByTitle (String title , int second) throws AbstractScriptException{
		Forms.forms_waitForWindowsExistsByTitle(this,forms, title, second);
	}
	
	public void forms_waitForFlexWindowsExists  (String title , int second) throws AbstractScriptException{
		Forms.forms_waitForFlexWindowsExists(this, forms, title, second) ;
	}
	
	public void forms_waitForWindowNotExists (String title , int second) throws AbstractScriptException{
		Forms.forms_waitForWindowNotExists(this, forms, title, second) ;
	}
	
	public void forms_assertTransactionBatchesWindowExists () throws AbstractScriptException{
		  Forms.forms_waitForWindowExists(this, forms, "BGW_BATCH", 60) ;
	}
	
	public void forms_closeWindow (String title) throws AbstractScriptException{
	   Forms.forms_closeWindow(this, forms, title) ;			   
	}
	
	public void forms_close_active_window () throws AbstractScriptException{
		   Forms.forms_close_active_window(this, forms) ;			   
		}
	
	public void creatingInvoice (String name, int [] count){
		for (int c : count) {
			System.out.println(name + "says :" + c);
		}
	}
	
	public void forms_invokeResponsibility () throws AbstractScriptException{
	   Forms.forms_invokeResponsibility(this, forms) ;			   
	}
	
	
	
	public void forms_selectResponsibility (String responsibility) throws AbstractScriptException{
	   Forms.forms_selectResponsibility(this, forms, responsibility) ;			   
	}
	
	public void forms_spreadTableClickLink (String path, int row, int col) throws AbstractScriptException{
		   Forms.forms_spreadTableClickLink(this, forms, path, row, col) ;			   
		}
	
	public void forms_spreadTableFocusRow (String path, int row) throws AbstractScriptException{
		   Forms.forms_spreadTableFocusRow(this, forms, path, row) ;			   
		}
	
	public void forms_selectNavigator_function (String path) throws AbstractScriptException{
	   Forms.forms_selectNavigator_function(this, forms, path) ;			   
	}
	
	public void forms_selectNavigator_Path (String path) throws AbstractScriptException {
	   Forms.forms_selectNavigator_Path(this, forms, path)	;
	}
	
	
	
	public void forms_clickInvoiceWorkbenchTab(String title) throws AbstractScriptException{
	   Forms.forms_clickTab(this, forms, "TAB_INVOICE_SUM_REGIONS", title) ;			   
	}
	
	public void forms_clickRecurringInvoicesTab(String title) throws AbstractScriptException{
		   Forms.forms_clickTab(this, forms, "REC_INV_REGIONS", title) ;			   
		}
	
	public void forms_clickCollectionsTab(String title) throws AbstractScriptException{
		   Forms.forms_clickTab(this, forms, "IEXRCALL_TAB", title) ;			   
	}
	
	public void forms_clickTab(String tabName, String title) throws AbstractScriptException{
		   Forms.forms_clickTab(this, forms, tabName , title) ;			   
	}
	
	public void web_refreshPaymentProcessrequest (String req_id , String status , int timeout) throws Exception{
		WebDriver.web_refreshPaymentProcessrequest (this,web, req_id, status, timeout);
	}
	
	public String randomString(int len){
		return Common.randomStringNumber(len);
	}
	
	public String randomNumNonRepeat (int len){
		return Common.randomStringNumber_NoRepeat(len);
	}
	
	public String randomStringNonRepeat (int len){
		return Common.randomStringNumber_NoRepeat(len);
	}
	
	public void forms_clickButton(String btnName)throws AbstractScriptException{
		Forms.forms_clickButton(this, forms, btnName);
	}
	
		
	public void forms_save(String windowTitle)throws AbstractScriptException{
		Forms.forms_save(this, forms, windowTitle) ;
	}
	
	public void forms_savePath(String path)throws AbstractScriptException{
		Forms.forms_savePath(this, forms, path) ;
	}
	
	public void forms_tickCheckBox(String path, boolean checked)throws AbstractScriptException{
		Forms.forms_tickCheckBox(this, forms, path, checked) ;
	}
			
	public void forms_query_mode(String path)throws AbstractScriptException{
		Forms.forms_query_mode(this, forms, path);			
	}
	
	public void forms_find(String windowName)throws AbstractScriptException{
		Forms.forms_find(this, forms, windowName);			
	}
	
	public void forms_waitForOracleFormsReady (int timeout, int interval) throws AbstractScriptException{
		Forms.forms_waitForOracleFormsReady(this, forms, 2, timeout, interval);
	}
	
	public void forms_run(String path)throws AbstractScriptException{
		Forms.forms_run(this, forms, path);			
	}
	
	public void forms_requests(String path) throws AbstractScriptException{
		Forms.forms_requests(this, forms, path);
	}
	
	public void forms_validateTextFieldValue(String path, String value)throws AbstractScriptException {
		String actualValue = Forms.forms_getText(this, forms, path);
		if(!value.equals(actualValue))
		{
			this.reportFailure("Expected value should be " + value + ", Actual value is " + actualValue) ;
		}		
	}
	
	public void forms_validateTextFieldExists(String path)throws AbstractScriptException {
		boolean actualValue = Forms.forms_validateTextFieldExists(this, forms, path);
		if(!actualValue)
		{
			this.reportFailure("Expected value should be " + true + ", Actual value is " + actualValue) ;
		}
			
	}
	
	public void forms_validateTextFieldNumberValue(String path, Float value)throws AbstractScriptException {
		String actualValue = Forms.forms_getText(this, forms, path);
		if(value != Float.parseFloat(actualValue))
		{
			this.reportFailure("Expected value should be " + value + ", Actual value is " + actualValue) ;
		}
			
	}
	
	public void forms_validateCheckboxValue(String path, boolean value)throws AbstractScriptException {
		boolean actualValue = Forms.forms_checkBoxGetValue(this, forms, path);
		if(!actualValue) 
		{
			this.reportFailure("Expected value should be " + value + ", Actual value is " + actualValue) ;
		}
	}
	
	public String forms_getText (String path) throws AbstractScriptException {
	   return Forms.forms_getText(this, forms, path) ;
	}
	
	public String web_getElementText (String path) throws AbstractScriptException {
		   return WebDriver.web_getElementText(this, web, path) ;
		}
	
	public void web_validateElementContainsText (String path, String value) throws AbstractScriptException {	
		String actualValue = WebDriver.web_getElementText(this, web, path) ;
		if(!actualValue.contains(value)) 
		{
			this.reportFailure("The text " + value + ", cannot be found in " + actualValue) ;
		}
	}

	public void forms_waitForStatusChanged (String path, int timeout, int interval, String expectedV) throws AbstractScriptException{
	   Forms.forms_waitForStatusChanged(this, forms, path, timeout, interval, expectedV)	;
	}
	
	public void forms_waitForStatusChangedDoNotCloseWindow (String path, int timeout, int interval, String expectedV) throws AbstractScriptException{
		   Forms.forms_waitForStatusChangedDoNotCloseWindow(this, forms, path, timeout, interval, expectedV)	;
	}
	
	public void forms_waitForStatusChangedByRequestID (String path, String requestID, int timeout, int interval ,String expectedVa) throws AbstractScriptException{
		  Forms.forms_waitForStatusChangedByRequestID(this, forms, path, requestID, timeout, interval, expectedVa) ;
	}
	
	public void forms_waitForStatusChangedByName (String path, String name, int timeout, int interval ,String expectedVa) throws AbstractScriptException{
		  Forms.forms_waitForStatusChangedByName(this, forms,  path,  name,  timeout,  interval , expectedVa) ;		
	}
	
	public String forms_findRequestByName (String name) throws AbstractScriptException {
		return  Forms.forms_findRequestByName(this, forms, name) ;
	}
	
	public int forms_findItemInJournalsByBatchName (String name) throws AbstractScriptException {
		 int index = Forms.forms_findItemInJournalsByBatchName(forms, name) ;
		 return index ;
	}
	
	public String forms_getSubmitRequestIDByIndex (String path, int index) throws AbstractScriptException {
		return Forms.forms_getSubmitRequestIDByIndex(this, forms, path, index) ;
	}
	
	public void forms_activateMainWindow () throws AbstractScriptException{
		   Forms.forms_activateMainWindow(this, forms)	;
	}
	
	public void forms_close_w (String title) throws AbstractScriptException {
		   Forms.forms_close_w(this, forms, title) ;
	}
	
	public String [] forms_getOpenWindowsTitle () throws AbstractScriptException{
		   return Forms.forms_getOpenWindowsTitle(this, forms) ;
	}
	
	public void forms_assertInvoiceWorkbench (String status) throws AbstractScriptException{
		 String actual = Forms.forms_getText(this, forms, "//forms:textField[(@name='INV_SUM_FOLDER_WFAPPROVAL_STATUS_DSP_0')]") ;
	     if (!status.equals(actual)){
	    	  this.reportFailure("Expected Status should be " + status + ", Actual status is " + actual) ;
	     }
	}
	
	public void forms_assertInvoiceWorkbenchStatus (String status) throws AbstractScriptException{
		 String actual = Forms.forms_getText(this, forms, "//forms:textField[(@name='INV_SUM_FOLDER_APPROVAL_STATUS_DISPLAY_0')]") ;
	     if (!status.equals(actual)){
	    	  this.reportFailure("Expected Status should be " + status + ", Actual status is " + actual) ;
	     }
	}

	public String forms_getClosedPeriod() throws Exception
	{
		String period = Forms.forms_getText(this, forms, Forms_Open_And_Close_Periods.PERIOD_TEXTFIELD_1);
		
		if(forms_checkValue (Forms_Open_And_Close_Periods.STATUS_TEXTFIELD_1, "Closed"))
		{
			return period;
		}
		else
		{
			forms_setTextField(Forms_Open_And_Close_Periods.STATUS_TEXTFIELD_1, -1, "Closed");
			forms_textFieldInvokeKey(Forms_Open_And_Close_Periods.STATUS_TEXTFIELD_1, "COMMIT");
			forms_closeWindow("LATEST");
			forms_choiceBox_clickButton("OK");			
			forms_selectNavigator_function("Other");
			forms_selectNavigator_function("Other|Requests");
			forms_clickButton(Forms_Find_Requests.FIND_BUTTON);	
			forms_waitForStatusChanged(Forms_Requests.JOB_PHASE_STATUS, 10 * 60, 30,"Completed");
			forms_selectNavigator_function("Other");
		}
		return period;
	}
	
	public boolean forms_checkValue (String path, String status) throws AbstractScriptException{
		 String actual = Forms.forms_getText(this, forms, path) ;
	     return status.equals(actual);	    	  
	}
	
	public void forms_textFieldOpenDialog (String colName, int index) throws AbstractScriptException{
		   Forms.forms_textFieldOpenDialog(this, forms, colName, index) ;		  
	}
	
	public void forms_flexWindowOpenDialog (String colName) throws AbstractScriptException{
		   Forms.forms_flexWindowOpenDialog(this, forms, colName) ;		  
	}
	
	public void forms_activateWindow(String title)throws AbstractScriptException{	
		Forms.forms_activateWindow(this, forms, title);
	}
	
	public void forms_selectItemInListOfValues(String item)throws AbstractScriptException{	
		Forms.forms_selectItemInListOfValues(this, forms, item);
	}

	public void forms_selectFirstItemInListOfValues()throws AbstractScriptException{	
		Forms.forms_selectFirstItemInListOfValues(this, forms);
	}
	
	public void forms_selecttemInListOfValues(String item)throws AbstractScriptException{	
		Forms.forms_selecttemInListOfValues(this, forms, item);
	}
	
	public void forms_choiceBox_clickButton(String btnName)throws AbstractScriptException{	
		Forms.forms_choiceBox_clickButton(this, forms, btnName);
	}
	
	public String forms_postJournalsByName (String batchName) throws AbstractScriptException{
		return  Forms.forms_postJournalsByName(this,forms, batchName, "OK") ;
	}
	
	public void web_waitForWindowsExists (String title , int timeout) throws AbstractScriptException {
		String  template = "/web:window[@title='$']".replace("$", title) ; 
		WebDriver.web_waitForWindowsExists(this, web, template, timeout) ;
	}
	
	public void web_waitForWindowsExistsUsingPath (String path , int timeout) throws AbstractScriptException {
		WebDriver.web_waitForWindowsExists(this, web, path, timeout) ;
	}
	
	public void web_expandRepositoryManagementTree(String item) throws Exception {
		String template = "/web:window[@title='Oracle Report Manager']/web:document[@index='0']/web:td[@text='$']".replace("$", item) ;		
		WebDriver.web_ExpandTreeItem(this, web, template) ;		
	}
	
	public void web_viewRepositoryManagementItem (String item) throws Exception{
		String template = "/web:window[@title='Oracle Report Manager']/web:document[@index='0']/web:td[@text='$']".replace("$", item) ;
		WebDriver.web_viewRepositoryManagementItem(this, web ,template) ;
	}
	
	public void forms_selectItemInList(String list, String item)throws AbstractScriptException{	
		Forms.forms_selectItemInList(this, forms, list, item);
	}
	
	public void forms_selectRadioButton(String name)throws AbstractScriptException{	
		Forms.forms_selectRadioButton(this, forms, name);
	}
	
	public void web_clickTextBox (String xpath)throws Exception{
		WebDriver.web_clickTextBox(this, web, xpath) ;
	}	
	
	public void web_setTextBox (String xpath, String value)throws Exception{
		WebDriver.web_setTextBox(this, web, xpath, value) ;
	}
	
	public void web_selectBoxSelectOption (String xpath, String value)throws Exception{
		WebDriver.web_selectBoxSelectOption(this, web, xpath, value) ;
	}
	
	public void web_clickButton (String xpath)throws Exception{
		WebDriver.web_clickButton(this, web, xpath) ;
	}
	
	public void web_clickCheckbox (String xpath)throws Exception{
		WebDriver.web_clickCheckbox(this, web, xpath);
	}
	
	public void web_clickImage (String xpath)throws Exception{
		WebDriver.web_clickImage(this, web, xpath) ;
	}
	
	public boolean web_elementExists (String xpath)throws Exception{
		return WebDriver.web_elementExists(this, web, xpath) ;
	}
	
	public void web_validateElementExists(String xpath)throws Exception{					
		if(!WebDriver.web_elementExists(this, web, xpath))
		{
			this.reportFailure( xpath + " does not exist") ;
		}
	}
	
	public void web_validateElementWithTextExists(String xpath, String text)throws Exception{
		xpath = xpath.replace("%", text);
		if(!WebDriver.web_elementExists(this, web, xpath))
		{
			this.reportFailure( xpath + " does not exist") ;
		}
	}
	
	public void web_clickTDLinkInTable(String windowTitle, String tableAttribute, String attributeValue, int rowIndex, int tdIndex) throws Exception{
		WebDriver.web_clickTDLinkInTable(this, web, windowTitle, tableAttribute, attributeValue, rowIndex, tdIndex);
	}
	
	public void web_clickLastTDLinkInHomePageTable() throws Exception{
		WebDriver.web_clickLastTDLinkInHomePageTable(this, web, "Oracle Applications Home Page", "summary", "Notification List");
	}
	
	public void web_clickLatestTDLinkInHomePageTable() throws Exception{
		WebDriver.web_clickLatestTDLinkInHomePageTable(this, web, "Oracle Applications Home Page", "summary", "Notification List");
	}
	
	public void web_getLatestTDLinkInHomePageTable() throws Exception{
		WebDriver.web_getLatestTDLinkInHomePageTable(this, web, "Oracle Applications Home Page", "summary", "Notification List");
	}
	
	public void web_refreshUntilTextAppearsInHomePageTable(final String text, int timeout) throws Exception{				
		while (!WebDriver.web_findLinkByColText(this, web, "Oracle Applications Home Page", "summary", "Notification List", 2, text) && timeout > 0) {
			web.window("/web:window[@title='Oracle Applications Home Page']").refresh();
			WebDriver.web_waitForWindowsExists(this, web, "/web:window[@title='Oracle Applications Home Page']", 60);
			Common.sleep(20, TimeUnit.SECONDS);
			timeout -= 20;
		}					
	}
	
	public void web_navigateToLink(String path) throws Exception{
		WebDriver.web_navigateToLink(this, web, path);
	}
	
	public void web_setTextArea(String path, String value) throws Exception{
		WebDriver.web_setTextArea(this, web, path, value);
	}

	public Queue<String> getEmailContent (int timeout, int expectedEmailItem){
		try {
			EmailClient client = new EmailClient (EmailConfiguration.USER_NAME,EmailConfiguration.PASSWORD,EmailConfiguration.URL) ;
			Queue<String> mails = client.getEmails(120, 1);
			return mails ;
		} catch (Exception e) {
			return new LinkedList<String> () ; 
		}
    	
    	 
	}
	
	
	public void web_assertOuputDisplayed() throws Exception {
		WebDriver.web_waitForWindowsExists(this, web, "/web:window[@title='*FNDWRR.exe*']", 60);
		WebDriver.web_assertOuputDisplayed(this, web);
	}
	
	public void web_assertPageContainsText(String title, String text) throws Exception {		
		WebDriver.web_assertPageContainsText(this, web, title, text);
	}
	
	public boolean checkProcessRunning(String processName){
		return Common.checkProcessRunning(processName);
	}
	
}

