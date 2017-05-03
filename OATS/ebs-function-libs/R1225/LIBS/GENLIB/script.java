import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import oracle.oats.scripting.modules.basic.api.FunctionLibrary;
import oracle.oats.scripting.modules.basic.api.IteratingVUserScript;
import oracle.oats.scripting.modules.basic.api.ScriptService;
import oracle.oats.scripting.modules.basic.api.Variables;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.formsFT.common.api.elements.AbstractWindow;
import oracle.oats.scripting.modules.functionalTest.api.PropertyTestList;
import oracle.oats.scripting.modules.functionalTest.api.TestOperator;
import oracle.oats.scripting.modules.webdom.api.WebDomService;
import oracle.oats.scripting.modules.webdom.api.elements.DOMDocument;
import oracle.oats.scripting.modules.webdom.api.elements.DOMElement;
import oracle.oats.scripting.modules.webdom.api.elements.DOMRadioButton;
import oracle.oats.scripting.modules.webdom.api.elements.DOMSelect;
import oracle.oats.scripting.modules.webdom.api.elements.DOMText;
import oracle.oats.utilities.FileUtility;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.applet.api.*;
import lib.*;

//import lib.ebsqafwk.WEBTABLELIB; 

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	int SYNCTIME = 200;
	public boolean searchSuccess;
	public DOMElement retObj;
	public String docpath, form;
	public int tblcols, tblrows, tblstartrow;

	public String radioAttr = "";
	public String olddocpath = this.docpath = "/web:window[@index='0']/web:document[@index='0']";
	public String oldform = this.form = "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']";
	int W_STATUS_BAR = -7003;
	int P_REQUEST_NO = -7005;
	int flag = 0;
	@FunctionLibrary("WEBTABLEOBJ") lib.ebsqafwk.WEBTABLEOBJ wEBTABLEOBJ;
	@FunctionLibrary("WEBLABELLIB") lib.ebsqafwk.WEBLABELLIB wEBLABELLIB;
	public void initialize() throws Exception {
		browser.launch();
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {
		
		String expectedValue = "Once the document is Cancelled, it cannot be undone. Do you want to proceed\\?";
		
		String Text = "Once the document is Cancelled, it cannot be undone. Do you want to proceed?";
		
		boolean pass = compareStrings(expectedValue,Text);
		
		info("pass:"+pass);
		//this.webSelectLOV("", searchByOption, searchValue, colName, rowValue)
		
	}

	public void finish() throws Exception {
	}
	
	
	/**
	 * 
	 *  Library: GenLib
	 *	Function: extractLiterals
		Parameters: 
		1.	Date:             Specifies the date which the user requires like #SYSTIME("dd-MMM-yyyy",0,-2,0)
		2.	Format:        Format of the date specified in the Date field i.e. “dd-MMM-yyyy”
		3.	Literals:        expected literal like “yyyy” or “mm” etc.
		
		Test Data : Need to pass 3 parameters in the test data.
		
		               Date:                    #SYSTIME("dd-MMM-yyyy",0,-2,0)                           -- To get two months before date, from now.
		               Format:                dd-MMM-yyyy
		               Literals:                yyyy
		
		Code Generated:
		
		               String year =  gENLIB.extractLiterals(gENLIB.getSysTime("DEFAULT","dd-MMM-yyyy",0,-2,0,0,0,0), "dd-MMM-yyyy", "yyyy");

	 * 
	 * 
	 * @param time
	 * @param format
	 * @param literals
	 * @return
	 */
	public String extractLiterals (String time,String format,String literals ){

        String result="";

        try{

               DateFormat formatter = new SimpleDateFormat(format.trim());

               Date date = formatter.parse(time);

               Calendar cal=Calendar.getInstance();

               cal.setTime(date);

               DateFormat format1 = new SimpleDateFormat(literals.trim());

               result=format1.format(cal.getTime());

        }catch(Exception e){

               e.printStackTrace();

        }

        return result;

 }	
	
	
	/**
	 * This is to take the url from the user and sets the value in appropriate global variables
	 * 
	 * urlType 		-- Url Type like oracle_php_url, oracle_jsp_url, oracle_istore_url, oracle_jtt_url, oracle_telnet_url,oracle_env_file
	 * 				-- This should be passed from component displayname
	 * 				-- Displayname should follow following  standards
	 * 							Url Type			Display name value
	 * 						oracle_php_url  		-- php or apps
	 * 						oracle_jsp_url  		-- jsp
	 *  					oracle_istore_url  		-- istore
	 *   					oracle_jtt_url  		-- jtt
	 *    					oracle_telnet_url  		-- telnet
	 *     					oracle_env_file  		-- env or xml
	 *     
	 * instanceUrl	-- Url Name
	 * 			`	-- Url should be passed from testdata	
	 */
	public void setURL(String urlType, String instanceUrl) throws Exception{
		
		if(urlType == null){
			urlType = "";
		}
		
		if(instanceUrl == null){
			instanceUrl = "";
		}
		
		//Please enter the ICXINDEX or AppsLogin URL
		if(urlType.toLowerCase().contains("php") || urlType.toLowerCase().contains("apps")){
			getSettings().set("oracle_php_url",instanceUrl);
			getVariables().set("oracle_php_url", instanceUrl, Variables.Scope.GLOBAL);
		}
		//Please enter the JSP Login URL
		if(urlType.toLowerCase().contains("jsp")){
			getSettings().set("oracle_jsp_url",instanceUrl);
			getVariables().set("oracle_jsp_url", instanceUrl, Variables.Scope.GLOBAL);
		}
		
		//Please enter the iStore Login URL
		if(urlType.toLowerCase().contains("istore")){
			getSettings().set("oracle_istore_url",instanceUrl);
			getVariables().set("oracle_istore_url", instanceUrl, Variables.Scope.GLOBAL);
		}
		
		//Please enter the jtt url
		if(urlType.toLowerCase().contains("jtt")){
			getSettings().set("oracle_jtt_url",instanceUrl);
			getVariables().set("oracle_jtt_url", instanceUrl, Variables.Scope.GLOBAL);
		}
		
		//Please enter the telnet url"
		if(urlType.toLowerCase().contains("telnet")){
			getSettings().set("oracle_telnet_url",instanceUrl);
			getVariables().set("oracle_telnet_url", instanceUrl, Variables.Scope.GLOBAL);
		}
		
		//"Please enter the path to your env XML file"
		if(urlType.toLowerCase().contains("env") || urlType.toLowerCase().contains("xml")){
			getSettings().set("oracle_env_file",instanceUrl);
			getVariables().set("oracle_env_file", instanceUrl, Variables.Scope.GLOBAL);
		}
		
	}

	/**
	 * 
	 * 
	 * @param logical
	 * @param value
	 * @throws AbstractScriptException
	 * 
	 * Developed By Ramaraju on 17th Sep
	 */
	public void setFlexTextByIndex(String logical,String value) throws AbstractScriptException{
        
		String attributes[]=logical.split(",");
        String row="",column="";
        
        for(int i=0;i<attributes.length;i++){
        	
              if(attributes[i].toLowerCase().indexOf("row=")!=-1){
                    row=attributes[i].split("=")[1];
              }else if(attributes[i].toLowerCase().indexOf("column=")!=-1){
                    column=attributes[i].split("=")[1];
              }
        }
        
        forms.flexWindow("//forms:flexWindow").setTextByIndex(Integer.parseInt(row), Integer.parseInt(column), value);
  }

	
	
	
	
	/******************************************
	 * Reusable Functions code for Modal popup
	 **************************************************************************/
	
	/**
	 *  Get Document index for the specific title
	 *  
	 * @param popupTitle
	 * @return
	 * @throws Exception
	 */
	public String getDocumentIndex(String popupTitle, String popUpText) throws Exception{
		
		String tagText ="h1";
		int docIndex = -1;
		String windowPath = getCurrentWindowXPath();
		
		if ( !"".equals(popUpText) && popUpText.contains("type="))
		{
			String[] textDetails = popUpText.split(",");
			
			// Get the text Details passed from User
			for (int index = 0; index < textDetails.length; index++) {

				if (textDetails[index].trim().startsWith("type=")) 
				{
					tagText = textDetails[index].split("=")[1];
				} 
			}
		}
		
		System.out.println("Tag Text :"+tagText);
		
		for(int index=0; ; index++){
			
			String docPath = windowPath + "/web:document[@index='"+index+"']";

			boolean docExists = web.document(docPath).exists();
		
			System.out.println("docExists "+index+":"+docExists);
			
			if(!docExists){
				break;
			}else{
				PropertyTestList prop = new PropertyTestList();
				prop.add("innertext", popupTitle);
				List<DOMElement> elements = web.document(docPath).getElementsByTagName(tagText, prop);
				System.out.println("elements :"+elements.size());
				
				if(elements.size() > 0){
					docIndex = index;
					break;
				}
			}		
		}
		
		
		if(docIndex < 0){
			Thread.sleep(5000);
			docIndex = Integer.parseInt(getDocumentIndex(popupTitle,popUpText));
		}
		
		System.out.println("Document index for the "+popupTitle+" Dialog is :"+docIndex);
		
		getVariables().set("DOCINDEX", docIndex+"");
		
		return docIndex+"";
	}
	
	
	public List<String> getDocumentIndexes(String popupTitle) throws Exception{
		
		List<String> docIndexes = new ArrayList<String>();
		
		int docIndex = -1;
		String windowPath = getCurrentWindowXPath();
		
		
		for(int index=0; ; index++){
			
			String docPath = windowPath + "/web:document[@index='"+index+"']";

			boolean docExists = web.document(docPath).exists();
		
			//System.out.println("docExists :"+index+":"+docExists);
			
			if(!docExists){
				break;
			}else{
				PropertyTestList prop = new PropertyTestList();
				prop.add("innertext", popupTitle);
				List<DOMElement> elements = web.document(docPath).getElementsByTagName("h1", prop);
				//System.out.println("elements :"+elements.size());
				
				if(elements.size() > 0){
					docIndex = index;
					docIndexes.add(index+"");
				}
			}		
		}
		
		if(docIndex < 0){
			Thread.sleep(5000);
			docIndexes = getDocumentIndexes(popupTitle);
		}
		
		System.out.println("Document index for the "+popupTitle+" Dialog is :"+docIndexes.toString());
		
		return docIndexes;
	}
	
	
	public String popupGetText(String title,String beforeWithIndex, String afterWithIndex) throws Exception{
		
		// Get Document Index for the specific title
		String docIndex = getDocumentIndex(title,"");
		
		//System.out.println("Document index :"+docIndex;
		
		String expectedText = webGetText(docIndex, beforeWithIndex, afterWithIndex);
		
		return expectedText;
	}
	
	public boolean popupVerifyTextWithBeforeAfter(String title,String expectedText, String beforeWithIndex, String afterWithIndex) throws Exception{
		
		boolean isTextVerified = false;
			
		// Get Document Index for the specific title
		String docIndex = getDocumentIndex(title,"");
		
		//System.out.println("Document index :"+docIndex;
		isTextVerified = webVerifyText(expectedText, docIndex, beforeWithIndex, afterWithIndex);
		
		System.out.println("Text \""+expectedText+"\" in "+title+" is matched.");
		
		return isTextVerified;
	}
	

	public boolean popupVerifyText(String title,String expectedText) throws Exception{
		
		boolean isTextVerified = false;
			
		// Get Document Index for the specific title
		String docIndex = getDocumentIndex(title,"");
		
		//System.out.println("Document index :"+docIndex;
		webVerifyText(expectedText, docIndex);
			
		return isTextVerified;
	}

	/**
	 * 
	 * @param modalPopupDetails
	 * 			modalPopupDetails = "Warning;Yes"
	 * 			modalPopupDetails.split(";")[0]	- Title
	 * 			modalPopupDetails.split(";")[1]	- Button Name
	 * @throws Exception
	 */
	public void clickPopupButton(String modalPopupDetails) throws Exception
	{
		String btnDocIndex = "";
		
		// Get Document Index for the specific title
		List<String> docIndexes = getDocumentIndexes(modalPopupDetails.split(";")[0]);
		System.out.println("indexes :"+docIndexes.toString());
		
		String windowPath = getCurrentWindowXPath();
		
		for(int index=0; index < docIndexes.size(); index++){
			
			String docIndex = docIndexes.get(index);
			String docPath = windowPath + "/web:document[@index='"+docIndex+"']";
			//System.out.println("docPath :"+docPath);
			
			PropertyTestList propList = new PropertyTestList();
			propList.add("text",modalPopupDetails.split(";")[1],TestOperator.StringWildCard);				
			List<DOMElement> buttonList = web.document(docPath).getElementsByTagName("button",propList);
			
			if(buttonList.size() > 0){
				btnDocIndex = docIndex;
			}
			
			System.out.println("index :"+buttonList.size());
		}
		
		System.out.println("btnDocIndex :"+btnDocIndex);
		
		webClickButton(modalPopupDetails.split(";")[1]+",document="+btnDocIndex);
	}
	
	
	
	/**
	 * 
	 * @param modalPopupDetails
	 * 			modalPopupDetails = "Warning;LinkName"
	 * 			modalPopupDetails.split(";")[0]	- Title
	 * 			modalPopupDetails.split(";")[1]	- Link Name
	 * @throws Exception
	 */
	public void clickPopupLink(String modalPopupDetails) throws Exception
	{
		String lnkDocIndex = "";
		
		// Get Document Index for the specific title
		List<String> docIndexes = getDocumentIndexes(modalPopupDetails.split(";")[0]);
		System.out.println("indexes :"+docIndexes.toString());
		
		String windowPath = getCurrentWindowXPath();
		
		for(int index=0; index < docIndexes.size(); index++){
			
			String docIndex = docIndexes.get(index);
			String docPath = windowPath + "/web:document[@index='"+docIndex+"']";
			//System.out.println("docPath :"+docPath);
			
			PropertyTestList propList = new PropertyTestList();
			propList.add("text",modalPopupDetails.split(";")[1],TestOperator.StringWildCard);				
			List<DOMElement> linkList = web.document(docPath).getElementsByTagName("a",propList);
			
			if(linkList.size() > 0){
				lnkDocIndex = docIndex;
			}
			//System.out.println("index :"+buttonList.size());
		}
		
		System.out.println("linkDocIndex :"+lnkDocIndex);
		
		webClickLink(modalPopupDetails.split(";")[1]+",document="+lnkDocIndex);
	}
	
	public void waitForNewWindow() throws Exception
	 {

        String title= web.getFocusedWindow().getAttribute("title");
        //System.out.println(title);
        String index=web.getFocusedWindow().getAttribute("index");
        //System.out.println("Index"+web.getFocusedWindow().getAttribute("index"));
        web.window("/web:window[@index='"+index+"']").waitForPage(30, true);
	 }
	
	public void  alterEffectiveDate( String sNewdate ) throws  Exception  
	{  

	  forms.window("//forms:window[(@enabled='true' or @index='0' or @visible='true')]").selectMenu("Tools|Alter Effective Date...");
	  Thread.sleep(10000);
	  forms.window("//forms:window[(@name='DAT1')]").activate(true);
	  if(sNewdate!=null && !"".equals(sNewdate))
		  forms.textField("//forms:textField[(@name='DAT1_D_SESSION_DATE_0')]").setText(sNewdate);
	  else if(sNewdate!=null && "".equals(sNewdate))
	  {
		  
	  }
	  Thread.sleep(5000);
	  forms.button("//forms:button[(@name='DAT1_OK_BUTTON_0')]").click();

	 } 
	public String getLPNname() throws Exception {

		String index = web.getFocusedWindow().getAttribute("index");
		
		String path = "web:window[@index='" + index + "' ]/web:document[@index='0']";

		List<DOMElement> list = web.document(path).getElementsByTagName("body");

		String bodyContent = list.get(0).getAttribute("innertext");
		int startIndex = bodyContent.indexOf("Created LPN name") + "Created LPN name".length();
		int endIndex = bodyContent.indexOf("lpn_cnt=1");

		String lpname = bodyContent.substring(startIndex,endIndex).trim();
		System.out.println("LPN Name"+lpname);

		return lpname;
	}
	public void sop(String rs)
	{
		//System.out.println(rs);
		
	/*	String debug	=	getVariables().get("DEBUG_MODE");
		if(debug==null || (debug!=null && debug.equals("OFF")))
		{
			// intentionally left it blank																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																													
		}
		else*/
			System.out.println(rs);
		
	}
	
	public void actOnAssignment(String type) throws Exception
	{
		info("Called	actOnAssignment ");
		if(type==null || "".equals(type) || type.toLowerCase().equals("update"))
		{
			
			forms.button("//forms:button[(@name='SEL1_UPDATE_BUTTON_0')]").click();
			think(4);
			//info("actOnAssignment before if");
			if(forms.choiceBox("//forms:choiceBox").exists())
			{
				//info("Entered if");
				forms.choiceBox("//forms:choiceBox").clickButton("OK");
			}
			else
			{
				info("Choicebox not required");
			}

		}	
		else
		{					
			forms.button("//forms:button[(@name='SEL1_CORRECTION_BUTTON_0')]").click();
		}
	}
	
	public void clickHide(String hideAltAttribute) throws Exception
	{
		/* Document Path */
		String docPath = getCurrentWindowXPath() + "/web:document[@index='0']";
		
		PropertyTestList propertyTestList = new PropertyTestList();
		propertyTestList.add("alt",	hideAltAttribute);

		List<DOMElement> imageList = web.document(docPath).getElementsByTagName("img",propertyTestList);

		this.sop("imageList Count matching " +imageList.size());
		
		for(int index=0; index < imageList.size(); index++){
			imageList.get(index).click();
		}
		
		delay(2);
	}
	/**
	 Code to verify checkbox
	 * */
	
	public boolean formVerifyChoiceBox(String expectedValue) throws Exception {

        String Text = "";

        boolean verify = false;

        String xPath = "//forms:choiceBox";

        if (forms.choiceBox(xPath).exists(5, TimeUnit.SECONDS)) {

        	Text = forms.choiceBox(xPath).getAlertMessage();

        	Text = Text.replaceAll("\n", " ");
        	Text = Text.replaceAll("\r", " ");
			
        	//this.sop("Actual Text 	:" + Text);	
        	//this.sop("Expected Text :" + expectedValue);
        	info("Actual Text 	:" + Text);	
        	info("Expected Text :" + expectedValue);
        	
        	if (compareStrings(expectedValue,Text)) {
        		info("The text :::>"+Text+" matches with the expected value:::>" + expectedValue);
        		verify = true;
        	} else {           

        		reportFailure("The text :::>"+Text+" doesn't match with the expected value :::>"+expectedValue);
        		verify = false;
        	}          
        }else{
			reportFailure("Unable to find the Choice with the XPath :"+xPath);
		}
                                      
        return verify;
	}
	
	public void expandAndSelectNode(String nameAttribute,String navPath)throws Exception{
		
		String xPath = "";
		
		if(nameAttribute.startsWith("@name='")){
			xPath = "//forms:tree[("+nameAttribute+")]";
		}else{
			xPath = "//forms:tree[(@name='"+nameAttribute+"')]";
		}
		
		expandNodes(nameAttribute,navPath);

        forms.tree(xPath).selectNode(navPath);
        
        forms.tree(xPath).activateNode(navPath);
        
        
	}

	public void expandNodes(String nameAttribute,String value)throws Exception{

		String xPath = "";
		
		if(nameAttribute.startsWith("@name='")){
			xPath = "//forms:tree["+nameAttribute+"]";
		}else{
			xPath = "//forms:tree[(@name='"+nameAttribute+"')]";
		}
		
        String nodes[]=value.split("\\|");

        String node="";

        for(int i=0;i<nodes.length;i++){

              node=node+"|"+nodes[i];

              if(node.startsWith("|"))node=node.substring(1,node.length());

              this.sop("Expaned the node "+node);

              forms.tree(xPath).expandNode(node);
        }
	}
	
	public void oracle_prompt_jtt_url() throws Exception
	{
//		String url = javax.swing.JOptionPane.showInputDialog("JTT URL:");
//		getVariables().set("oracle_jtt_url",url);
	}
	
	 public String getSysDate(String zone,String dateFormat, int noOfDays) throws Exception {

         String dateVal = "";

         SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);

         if(!zone.isEmpty()&& !zone.equalsIgnoreCase("default")){
         	dateFormater.setTimeZone(TimeZone.getTimeZone(zone));
            }

         Calendar cal = Calendar.getInstance();

         if (noOfDays > 0) {

                cal.add(Calendar.DATE, noOfDays);

         } else if (noOfDays == 0) {

         }else {
                cal.add(Calendar.DATE, noOfDays);
         }

         dateVal=dateFormater.format(cal.getTime());
         
         return dateVal;
      	
	 }
	 
	 public String getSysTime(String zone,String dateFormat, int... param) 
	 {

	        String temp = "";
	        String retString = "";
	        int valuesCounter = 0, value = 0;
	        Calendar cal = Calendar.getInstance();
	        int valuesLength = param.length;
	        String format = "";
	        String caseFormat="";

	        if(dateFormat.toUpperCase().startsWith("CC")){
	               caseFormat=dateFormat.substring(0, 2);
	               dateFormat=dateFormat.substring(2).trim();
	         }
	        if(dateFormat.toLowerCase().indexOf("dm")!=-1){
	        	format=getLastDayOfMonth(cal, dateFormat, param);
	        
	        }else{

	        	for (int i = 0; i < dateFormat.length(); i++) 
	        	{

	               if (valuesLength > valuesCounter)
	                      value = param[valuesCounter];
	               else
	                      value = 0;

	               if ('/' == dateFormat.charAt(i) || '-' == dateFormat.charAt(i) || ':' == dateFormat.charAt(i)
	                            || '.' == dateFormat.charAt(i) || ' ' == dateFormat.charAt(i)) {

	                      if ("".equalsIgnoreCase(temp)) {
	                            System.out.println("Invalid Date format");
	                            return "";

	                      } else {

	                            format += getTime(cal, temp, value) + dateFormat.charAt(i);
	                            temp = "";
	                            valuesCounter++;
	                      }
	               } else
	                      temp = temp + dateFormat.charAt(i);
	        }
	        

	        if (!"".equalsIgnoreCase(temp))
	               format += getTime(cal, temp,value);
	        }
	        
	        DateFormat df=new SimpleDateFormat(format);

	        if(!zone.isEmpty() && !zone.equalsIgnoreCase("default")){
	       	 df.setTimeZone(TimeZone.getTimeZone(zone));
	        }
	        
	        retString = df.format(cal.getTime());

	        if(caseFormat.equals("CC"))
	               return retString.toUpperCase();

	        else if(caseFormat.equals("cc"))
	               return retString.toLowerCase();
	        else

	        return retString;

	  }
	
	 /**
	  * 
	  * 1.       #SYSTIME(“dm-MMM-yyyy”,0,0,0) àreturns the last day of the current month current year
	  * 2.       #SYSTIME(“dm-MMM-yyyy”,0,+-1,+-1) àreturns the last day of the next/last month next/last year respectively
	  * 3.       #SYSTIME(“dm-MMM-yyyy”,-3,0,0) àreturns the three days before the last day of the month ex: for current month July it returns 28-jul-2013 ( I.e. ,31-3=28)
	  * 4.       #SYSTIME(“dm-MMM-yyyy”,0,0,0,0,0,0,2,2016) àreturns last day Feb. 2016 i.e. 29-Feb-2016
	  * 5.       #SYSTIME(“dm-MMM-yyyy”,0,0,1,0,0,0,2) àreturns last day Feb. next year i.e. 29-Feb-2014
	  * 6.       #SYSTIME(“dm -yyyy-MMM”,0,0,1,0,0,0,2016) à returns last day next month in the year 2016 i.e. 31-Aug-2016
	  * 
	  * In the above examples 4,5,6 there are extra parameters 7 and 8 it refers to fixed month and fixed year the position is depend on Month and year format. 
	  * 	a.       If format has MMM-yyyy   then 7 parameter corresponds to month and 8th refers to year
	  * 	b.      If format has year first and month next ex: yyyy-dm-MMM then 7th refers to year and 8th refers to month
	  * 	c.       In the above example 5 month is considered as Feb. because in the 7th param we are passing is 2 and since there is no 8th parameter we consider the year corresponding value i.e. 1 in position 3
	  * 7.       #SYSTIME(“dm”,0,0,0,0,0,0,2,2016) it returns 29 since we are passing 2 and 2016 in 7,8th parameters In this case 7th refers to month and 8th refers to year
	  * 8.       #(SYSTIME(“dm-MMM”,0,0,0,0,0,0,2,2016) it returns 29-Feb
	  * 
	  * @param cal
	  * @param dateFormat
	  * @param param
	  * @return
	  */
	 private String getLastDayOfMonth(Calendar cal, String dateFormat, int...param)
	 {
		 int valuesLength = param.length;
	     String format = ""; 
	     int valuesCounter = 0, value = 0,dmCounter=0,mmCounter=0,yyCounter=0;
	     String temp="";
	     int dmValue=0;
	     
	     for (int i = 0; i < dateFormat.length(); i++) 
			{

	            if (valuesLength > valuesCounter)
	                   value = param[valuesCounter];
	            else
	                   value = 0;

	            if ('/' == dateFormat.charAt(i) || '-' == dateFormat.charAt(i) || ':' == dateFormat.charAt(i)
	                         || '.' == dateFormat.charAt(i) || ' ' == dateFormat.charAt(i)) {

	                   if ("".equalsIgnoreCase(temp)) {
	                         System.out.println("Invalid Date format");
	                         return "";

	                   } else {
	                	   if("dm".equalsIgnoreCase(temp)){
	                		   dmValue=value;
	                		   format += "dd" + dateFormat.charAt(i);
	                           temp = "";
	                           valuesCounter++;
	                		   
	                	   }else if("MM".equals(temp)||"MMM".equals(temp)||"MMMM".equals(temp)){
	                		   format += temp + dateFormat.charAt(i);
	                		   if(6+dmCounter<valuesLength){
	                			   value=param[6+dmCounter]-1;
	                		        cal.set(Calendar.MONTH,value);
	                		   }else{
	                			   getTime(cal, temp, value);
	                		   }
//	                		   System.out.println("Month in"+cal.getTime());
	                           temp = "";
	                           valuesCounter++; 
	                           mmCounter++;
	                           dmCounter++;
	                	   }else if("yy".equalsIgnoreCase(temp)||"yyyy".equalsIgnoreCase(temp)){
	                		   format += temp.toLowerCase() + dateFormat.charAt(i);
	                		   
	                		   if(6+dmCounter<valuesLength){
	                			   value=param[6+dmCounter];
	                		   cal.set(Calendar.YEAR,value);
	                		   }else{
	                			   getTime(cal, temp, value);
	                		   }
//	                		   System.out.println("Year in"+cal.getTime());
	                           temp = "";
	                           valuesCounter++; 
	                           yyCounter++;
	                           dmCounter++;
	                	   }else{

	                         format += getTime(cal, temp, value) + dateFormat.charAt(i);
	                         temp = "";
	                         valuesCounter++;
	                	   }
	                   }
	            } else
	                   temp = temp + dateFormat.charAt(i);
	     }
			
	     

	     if (!"".equalsIgnoreCase(temp)){
	    	 if("dm".equalsIgnoreCase(temp)){
	  		   dmValue=value;
	  		   format += "dd";
	             temp = "";
	             valuesCounter++;
	  		   
	  	   }else if("MM".equals(temp)||"MMM".equals(temp) ||"MMMM".equals(temp)){
	  		   format += temp ;
	  		 if(6+dmCounter<valuesLength){
				   value=param[6+dmCounter]-1;
			        cal.set(Calendar.MONTH,value);
			   }else{
				   getTime(cal, temp, value);
			   }
//	  		   System.out.println("Month out"+cal.getTime());
	             temp = "";
	             valuesCounter++; 
	             mmCounter++;
	             dmCounter++;
	  	   }else if("yy".equalsIgnoreCase(temp)||"yyyy".equalsIgnoreCase(temp)){
	  		   format += temp.toLowerCase();
	  		   
	  		 if(6+dmCounter<valuesLength){
				   value=param[6+dmCounter];
			   cal.set(Calendar.YEAR,value);
			   }else{
				   getTime(cal, temp, value);
			   }
//	  		 System.out.println("Year out"+cal.getTime());
	             temp = "";
	             valuesCounter++; 
	             yyCounter++;
	             dmCounter++;
	  	   }else
	            format += getTime(cal, temp,value);
	     }
	     
	     /** if only dm is provided*/
	     if(dmCounter==0){
	    	 if(6+dmCounter<valuesLength){
				   value=param[6+dmCounter]-1;
			        cal.set(Calendar.MONTH,value);
			   }else{
				   getTime(cal, temp, value);
			   }
	    	 dmCounter++;
	    	 if(6+dmCounter<valuesLength){
				   value=param[6+dmCounter];
			   cal.set(Calendar.YEAR,value);
			   }else{
				   getTime(cal, temp, value);
			   }
	     }
	     /** if only month or year is defined in format*/
	     //if((mmCounter==1||yyCounter==1)){
	    	 
	     if((mmCounter==1||yyCounter==1)&&dmCounter==1){	    	 
	    	 if(mmCounter==1&&valuesLength==8){
	    		 value=param[7];
	  		   cal.set(Calendar.YEAR,value);
	    	 }
	    	 if(yyCounter==1&&valuesLength==8){
	    		 value=param[7]-1;
	    		   cal.set(Calendar.MONTH,value);
	    	 }
	    	 if(mmCounter==1&&valuesLength>=6 && yyCounter<1){
	    		 value=param[valuesCounter];
	  		   cal.set(Calendar.YEAR,value);
	    	 }
	    	 if(yyCounter==1&&valuesLength>=6 && mmCounter<1){
	    		 value=param[valuesCounter]-1;
	    		   cal.set(Calendar.MONTH,value);
	    	 }
	    	 /*
	    	 if(mmCounter==1&&valuesLength<8){
	    		 value=param[valuesCounter];
	  		   cal.set(Calendar.YEAR,value);
	    	 }
	    	 if(yyCounter==1&&valuesLength<8){
	    		 value=param[valuesCounter]-1;
	    		   cal.set(Calendar.MONTH,value);
	    	 }
	    	 */
	     }
	     
	     getTime(cal, "dm",dmValue);
	     return format;
			
		}
	 
	 
		private String getTime(Calendar cal, String dateFormat, int value) {

			if ("dd".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.DATE,
					value);
				return "dd";
			}
			if ("MMMM".equals(dateFormat)) 
			{
				   cal.add(
				    Calendar.MONTH,
				    value);
				   return "MMMM";
			}
			if ("ddd".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.DATE,
					value);
				return "EE";
			}
			if ("dm".equalsIgnoreCase(dateFormat)) {
				DateFormat df=new SimpleDateFormat("dd");
				cal.add(Calendar.MONTH,1);
				cal.set(Calendar.DATE,1);
				cal.add(Calendar.DATE,-1);
				cal.add(Calendar.DATE,value);
				return "dd";
			}
			if ("M".equals(dateFormat)) {
				cal.add(
					Calendar.MONTH,
					value);
				return "M";
			}
			if ("MM".equals(dateFormat)) {
				cal.add(
					Calendar.MONTH,
					value);
				return "MM";
			}
			if ("MMM".equals(dateFormat)) {
				cal.add(
					Calendar.MONTH,
					value);
				return "MMM";
			}
			if ("yyyy".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.YEAR,
					value);
				return "yyyy";
			}
			if ("yy".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.YEAR,
					value);
				return "yy";
			}

			if ("HH".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.HOUR,
					value);
				return "HH";
			}
			if ("mm".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.MINUTE,
					value);
				return "mm";
			}
			if ("ss".equalsIgnoreCase(dateFormat)) {
				cal.add(
					Calendar.SECOND,
					value);
				return "ss";
			}

			if ("ddMMyy".equalsIgnoreCase(dateFormat)) {
				
				return "ddMMyy";
			}

			return "";
		}
	 
	 
	 
	 
	
    public  String getRandom (String format,int number) {
    	 

        char[] charsString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        char[] charsAlphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

        char[] charsNum = "1234567890".toCharArray();

        char[] chars=null;

        if(format.equalsIgnoreCase("ALPHANUMERIC"))

        chars=charsAlphaNum;

        else if(format.equalsIgnoreCase("INT"))

               chars=charsNum;

        else

               chars=charsString;

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < number; i++) {

               char c = chars[random.nextInt(chars.length)];

               sb.append(c);

        }

        String output = sb.toString();

        return output;

  }
	
	public void clickAlertButton(String buttonName) throws Exception
	{
		if(buttonName.equalsIgnoreCase("YES") || buttonName.equalsIgnoreCase("OK"))
			forms.alertDialog("//forms:alertDialog").clickYes();
		else if(buttonName.equalsIgnoreCase("CANCEL"))
			forms.alertDialog("//forms:alertDialog").clickCancel();
		else if(buttonName.equalsIgnoreCase("NO"))
			forms.alertDialog("//forms:alertDialog").clickNo();	
		
	}
	
	public String extractNumber(String text) {

		String result = "";
		
		if(text.contains(" ")){
			System.out.println("EN: with spaces");
			result=getNumbersFromStr(text," ","","0");
			System.out.println("Result:" + result);
		}else{
			System.out.println("EN: with out spaces");
			result=getNumbersFromStr(text,"","","0");
			System.out.println("Result:" + result);
		}
		
		
		//result=getNumbersFromStr(text," ","","0");
		
		/*text = removeSpecialCharacters(text);
		String texta[] = text.split(" ");

		for (int i = 0; i < texta.length; i++) {
			if (texta[i].matches("[0-9.]+")) {
				result = texta[i];
				break;
			}
		}*/

		return result;
	}
	public String getNumberFromFormTitle(String formAttr) throws Exception
	{
		String formAttrs[]	=	formAttr.split(";");
		String result	=	"";
		String index	=	"0";
		if(formAttrs.length>1)
		{
			index	=	formAttrs[1];
		}
		String title= forms.window("//forms:window[(@name='"+formAttrs[0]+"')]").getTitle();
		if(title.contains(" "))
		{			
			result=getNumbersFromStr(title," ","",index);
			
		}
		else
		{			
			result=getNumbersFromStr(title,"","",index);			
		}
		
		return result;
	}
	public String getNumbersFromStr(String msg,String before,String after,String ind)
	{
		String requests="";
		
		int fromIndex=msg.indexOf(before);
		
		if(before.isEmpty())
			fromIndex=0;
		
		String temp=msg.substring(fromIndex);
		
		int toIndex=temp.indexOf(after);
		
		if(after.isEmpty())
			toIndex=temp.length();
		
		temp=temp.substring(0,toIndex);
		
		Pattern p = Pattern.compile("[0-9]+", Pattern.MULTILINE);
		Matcher match = p.matcher(temp);
		while(match.find())
			requests+=match.group()+",";
   
		String tempRet	=	requests.substring(0,requests.length()-1);
		System.out.println("getNumbersFromStr Value:"+tempRet);
        return getNumberAt(tempRet,ind);		
		
	}
	
	
	public String getNumberAt(String commaStr,String index)
	{
		String arr[]	=	commaStr.split(",");	
		if("".equals(index) || index==null)
			index	=	"0";
		return arr[Integer.parseInt(index)];
	}
	
	public void setProperty(String key, String value) throws Exception{
		
		// Variales Folder Path, which is available in the FlowName > Data folder
        String variablesFilePath = "";
        
        // Flow Name
        String flowName = "";

        if(value == null){
        	value = "";
        }
        
        //Setting value into global variables
        getVariables().set(key, value);

        // Get Current Script folder
        String scriptFolder = getVariables().get("CURR_SCRIPT_FOLDER");
        //this.sop("scriptFolder :"+scriptFolder);
        
        if(scriptFolder==null ||scriptFolder.isEmpty()){
              javax.swing.JOptionPane.showMessageDialog(null, "Variable CURR_SCRIPT_FOLDER is not set propery please refer with MASTERDRIVE");
        }

        // File Object
        File f = new File(scriptFolder);

        if(f.exists()){

        	// Variable File Path
        	 if(f.getAbsolutePath().indexOf(".jwg")!=-1){
        		 
        		 variablesFilePath = f.getParentFile().getParent()+"\\data\\variables.properties";
        		
        		 // Getting Flow Name
                 flowName = f.getParentFile().getParentFile().getName();
                 
                 //getVariables().set("CURR_SCRIPT_FOLDER",f.getParent()+File.separator);
        		 
        	}else{
        		variablesFilePath = f.getParent()+"\\data\\variables.properties";
        		 
        		 // Getting Flow Name
                flowName = f.getParentFile().getName();
        	}
                
        	 //System.out.println("variablesFilePath :\""+variablesFilePath+"\"  flowName :\""+flowName+"\"");
             
        }else{
        	javax.swing.JOptionPane.showMessageDialog(null, "MasterDriver Folder \"variables.properties\" Not Found for setting properties");
        }

        //this.sop("variablesFilePath :"+variablesFilePath);
        File file=new File(variablesFilePath);

        if(!file.exists())
        	file.createNewFile();

        // Creating Properties object for write to & read from variables file
        Properties prop = new Properties();
        FileInputStream in = new FileInputStream(variablesFilePath);
        
        // Load the properties file
        prop.load(in);
       
        // Close input stream
        in.close();
        
        // Creat output stream for writing the variable in variables file
        FileOutputStream out = new FileOutputStream(variablesFilePath);
        
        // Set the property with key, value pair
        prop.setProperty(key, value);
        
        // Stroe the variable
        prop.store(out, flowName);
       
        // Close output stream
        out.close();
       
	}
	
	
	public String getProperty(String key) throws Exception{
		
		// Variales Folder Path, which is available in the FlowName > Data folder
        String variablesFilePath = "";

        // Flow Name
        String flowName = "";

        // Get Current Script folder
        String scriptFolder = getVariables().get("CURR_SCRIPT_FOLDER");

        if(scriptFolder==null ||scriptFolder.isEmpty()){
              javax.swing.JOptionPane.showMessageDialog(null, "Variable CURR_SCRIPT_FOLDER is not set propery please refer with MASTERDRIVE");
        }

        // File Object
        File f = new File(scriptFolder);

        if(f.exists()){
        	
        	// Variable File Path
        	if(f.getAbsolutePath().indexOf(".jwg")!=-1){

        		variablesFilePath = f.getParentFile().getParent()+"\\data\\variables.properties";

        		// Getting Flow Name
        		flowName = f.getParentFile().getParentFile().getName();

        		//getVariables().set("CURR_SCRIPT_FOLDER",f.getParent()+File.separator);

        	}else{
        		variablesFilePath = f.getParent()+"\\data\\variables.properties";

        		// Getting Flow Name
        		flowName = f.getParentFile().getName();
        	}

        	System.out.println("variablesFilePath :\""+variablesFilePath+"\"  flowName :\""+flowName+"\"");
        	
        }else{
              return null;
        }

        // Creating Properties object for write to & read from variables file
        Properties prop = new Properties();

        FileInputStream in = new FileInputStream(variablesFilePath);

        // Load the properties file
        prop.load(in);

        // Close input stream
        in.close();

        // Stroe the variable
        String value = prop.getProperty(key);

        //Setting value into global variables
        getVariables().set(key, value);

        if(value == null){
              value = "";
        }

        return value;
	}
	
	
	public boolean webVerifyLinkBasedOnLabel(String labelWithIndex, String expectedValue) throws Exception {
		
		return wEBLABELLIB.webVerifyLinkBasedOnLabel(labelWithIndex, expectedValue);
		
	}
	
	public boolean webVerifyTextBasedOnLabel(String labelWithIndex, String expectedValue) throws Exception {
		
		return wEBLABELLIB.webVerifyTextBasedOnLabel(labelWithIndex, expectedValue);
	}
	
	public String webGetLinkBasedOnLabel(String labelWithIndex) throws Exception {
		
		return wEBLABELLIB.webGetLinkBasedOnLabel(labelWithIndex);
		
	}
	
	public String webGetTextBasedOnLabel(String labelWithIndex) throws Exception {
		
		return wEBLABELLIB.webGetTextBasedOnLabel(labelWithIndex);
	}
	
	
	public void webSetTextBasedOnLabel(String labelWithIndex, String valueToSet) throws Exception {
		
		wEBLABELLIB.webSetTextBasedOnLabel(labelWithIndex, valueToSet);
		
	}
	
	public String getListboxSelectedValue(String xpath) throws Exception {

		String[] selectedValueArray = web.selectBox(
			xpath).getSelectedText();
		String selectedValueString = java.util.Arrays.toString(selectedValueArray);

		String selectedText = selectedValueString.substring(
			1,
			selectedValueString.length() - 1);

		this.sop("Selected Items in the listbox (with comma separated) is  ::" + selectedText);

		return selectedText;
	}

	public void selectTableRadioButton(String tblName, String srcColNames[], String srcColVals[], String targetCol)
			throws Exception {
		
		int documentIndex = 0;
		int formIndex = 0;
		int tableIndex = 0;
		String expTableName = "";
		
		String tableDetails[] = tblName.split(",");

		// Get the Table Details passed from User
		for (int index = 0; index <tableDetails.length; index++) {

			if (tableDetails[index].trim().startsWith("document=")) {

				documentIndex = Integer.parseInt(tableDetails[index].split("=")[1]);

			} else if (!tableDetails[index].contains("document=") && !tableDetails[index].contains("form=")) {

				String[] tableNameDetails = tableDetails[index].split(";");

				// Similar Table Index
				if (tableNameDetails.length > 1) {
					tableIndex = Integer.parseInt(tableNameDetails[1]);
				}

				// Table Name
				expTableName = tableNameDetails[0];

			} else if (tableDetails[index].trim().startsWith("form=")) {

				formIndex = Integer.parseInt(tableDetails[index].split("=")[1]);
			}
		}
		
		this.sop("****** Table Details ********************* ");
		this.sop("Table Column Name: \"" + expTableName+ "\"  Table Index: \"" + tableIndex+ "\"  Document Index: \""
				+ documentIndex+ "\"  Form Index: \"" + formIndex + "\"");
		
		System.out.println("** Start of Table in WebSelectLov ****");
		
			HashMap <String,String> searchColumns = new HashMap<String,String>();
			for(int i=0; i< srcColNames.length ; i++){
				
				searchColumns.put(srcColNames[i],srcColVals[i]);
				
			}
			
			HashMap<String,String> action = new HashMap<String,String>();
		
			//Keyword ,object ,caption ,logical name, outputvar, function Name, params
			
			action.put("keyword", "SELECT");
			action.put("object","RADIO");
			action.put("displayname", targetCol); 
			action.put("logicalname", targetCol);
			action.put("outputvar", "");
			action.put("funcname", "");
			action.put("value", "true");
			
		wEBTABLEOBJ.getWebTableObject(tblName);
		
		int rowNumber = wEBTABLEOBJ.findRowNumber(searchColumns, tblName);
		
		wEBTABLEOBJ.radioOn(tblName, rowNumber, action);
		
		wEBTABLEOBJ.clearWebTableObject();
	
		//wEBTABLEOBJ.getWebTableObject(expTableName+",document=1");
		
		/*int rowNumber = wEBTABLEOBJ.findRowNumber(searchColumns, tableName);
		
		wEBTABLEOBJ.radioOn(tableName, rowNumber, action);
		
		wEBTABLEOBJ.clearWebTableObject();
		initializeWebTable();*/
		
		//WebTable searchTable = new WebTable(expTableName,tableIndex,documentIndex,formIndex,"",60);
		
		//searchTable.selectRadioButton(srcColNames,srcColVals,targetCol);
	}

	public void wait(String globalWaitTime, String comLevelWaitTime) throws AbstractScriptException {

		if ("".equalsIgnoreCase(comLevelWaitTime)) {
			// delay(toInt(globalWaitTime));
			think(toInt(globalWaitTime));
		} else {
			// delay(toInt(globalWaitTime)+toInt(comLevelWaitTime));
			think(toInt(globalWaitTime) + toInt(comLevelWaitTime));
		}

	}

	public void closeWebPage(String title) throws Exception {

		String [] titles = title.split(";");
		String path = "";
		
		if(titles.length>1){
			
			path = "/web:window[@title='" + titles[0] + "'and @index ='"+titles[1]+"']";
		}
		else{
			
			path = "/web:window[@title='" + title + "']";
		}
		web.window(	path).close();
	}

	public void formsSendKey(String xpath, String value) throws Exception {
		forms.textField(
			xpath).click();
		forms.textField(
			xpath).setText(
			value);
	}

	public void webSendKey(String xpath, String value) throws Exception {
		web.textBox(
			xpath).click();
		web.textBox(
			xpath).setText(
			value);
	}

	public void formSelectLOV5(String editLogicalName, String valueToSelect) throws Exception {
		boolean splitted	=	false;
		String actualValuetoSelect	=	"";
		if(valueToSelect.contains("%"))
		{
			splitted=true;
			actualValuetoSelect	=	valueToSelect.split("%")[0];
			
		}
		if (forms.listOfValues(	"//forms:listOfValues").exists(5, TimeUnit.SECONDS))// || editLogicalName == null || "".equals(editLogicalName)) 
		{
			if (valueToSelect.contains("|")) {
				this.sop("Selected Value is :"+valueToSelect);
				forms.listOfValues("//forms:listOfValues").select(valueToSelect);
				
				if(forms.listOfValues(	"//forms:listOfValues").exists())
				{
					actualValuetoSelect	=	valueToSelect.split("\\|")[1];
					
					int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
					this.sop("Item Count :"+itemCount);
					
					for (int i = 1; i <= itemCount; i++) {
						
						String[] value = forms.listOfValues("//forms:listOfValues").getText(i).split("\\|");

						this.sop(" Values in the list box at index "+i+" is :" + Arrays.asList(value).toString());

						if (value[1].equals(actualValuetoSelect))  {
								
								forms.listOfValues("//forms:listOfValues").choose(i);
								this.sop("Selected Value is :"+valueToSelect);
								break;
						}
					}					
				}
				
				return;
			}
			
			think(4);
			
			// Setting value in find field & click on find button
			this.sop("Setting the value in the List text field and Clicking on Find button");
			forms.listOfValues("//forms:listOfValues").find(valueToSelect);

			think(2);
			this.sop("Selecting the value from list box");
			if (forms.listOfValues("//forms:listOfValues").exists()) {
				
				int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
				this.sop("Item Count :"+itemCount);
				
				for (int i = 1; i <= itemCount; i++) {
					
					String[] value = forms.listOfValues("//forms:listOfValues").getText(i).split("\\|");

					this.sop(" Values in the list box at index "+i+" is :" + Arrays.asList(value).toString());

					if (value[0].trim().equals(valueToSelect)  || (splitted && value[0].equals(actualValuetoSelect))   ) {
							
							forms.listOfValues("//forms:listOfValues").choose(i);
							this.sop("Selected Value is :"+valueToSelect);
							break;
					}
				}
			}else{
				//Here Lov closed after clicking Find button
				this.sop("Selected Value is :"+valueToSelect);
			}
		} else if (!"".equals(editLogicalName) && editLogicalName != null) {
			
			String xpath = "//forms:textField[(@name='" + editLogicalName + "')]";

			if (editLogicalName.contains("name=")) {

				String logicName[] = editLogicalName.split("=");
				xpath = "//forms:textField[(@name=" + logicName[1] + ")]";

			}

			this.sop("Edit Field Xpath is " + xpath);

			/*//Entering value in text field
			if (valueToSelect.contains("|")) {
				String value[] = valueToSelect.split("\\|");
				this.sop("Value set in the textfield :"+value[0]);
				forms.textField(xpath).setText(value[0]);
			}else{
				this.sop("Value set in the textfield wihtout pipe:"+valueToSelect);
				forms.textField(xpath).setText(valueToSelect);
			}*/
			
			// Open Dialog
			forms.textField(xpath).openDialog();

			think(4);
			
			if (forms.choiceBox("//forms:choiceBox").exists()) {
				forms.choiceBox("//forms:choiceBox").clickButton("Yes");
			}

			if (forms.listOfValues("//forms:listOfValues").exists()) {
				
				this.sop("Opened List Box");
				
				try {
					// forms.listOfValues("//forms:listOfValues").select(valueToSelect);
					if (valueToSelect.contains("|")) {
						this.sop("0");
						String value[] = valueToSelect.split("\\|");
						
						String errFormsFT = getSettings().get("err.formsft.forms_ft_error");
						try{
							
							
							getSettings().set("err.formsft.forms_ft_error","Warn");
							
							//this.sop("1");
							forms.listOfValues("//forms:listOfValues").find(value[0]);
							//this.sop("2");
							forms.listOfValues("//forms:listOfValues").select(valueToSelect);
							getSettings().set("err.formsft.forms_ft_error",errFormsFT);
							//this.sop("3");
						}catch(Exception e){
							getSettings().set("err.formsft.forms_ft_error",errFormsFT);
						}
							
							if(forms.listOfValues(	"//forms:listOfValues").exists())
							{
								actualValuetoSelect	=	valueToSelect.split("\\|")[1];
								this.sop("5");
								int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
								this.sop("5");
								this.sop("Item Count :"+itemCount);
								
								for (int i = 1; i <= itemCount; i++) {
									this.sop(forms.listOfValues("//forms:listOfValues").getText(i));
									String[] values = forms.listOfValues("//forms:listOfValues").getText(i).split("\\|");
	
									this.sop(" Values in the list box at index "+i+" is :" + Arrays.asList(values).toString());
									this.sop("values[1]:"+ values[1]);
									this.sop("actualValuetoSelect"+ actualValuetoSelect);
									if (values[1].equals(actualValuetoSelect))  {
											
											forms.listOfValues("//forms:listOfValues").choose(i);
											this.sop("Selected Value is :"+valueToSelect);
											break;
									}
								}					
							}	
							else
							{
								this.sop("Entered else, formsSelectLov is not found");
							}
							
						
						
						
						info("Selected Value is :"+valueToSelect);
						return;
					}
					
					forms.listOfValues("//forms:listOfValues").find(valueToSelect);
					
					
				} catch (Exception e) {
					info("Entered Eception While Selecting Value in FORMSELECTLOV");
				}

				think(2);

				if (forms.listOfValues("//forms:listOfValues").exists()) {
					
					int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
					
					this.sop("Item Count :"+itemCount);
					
					for (int i = 1; i <= itemCount; i++) {
						
						String[] value = forms.listOfValues("//forms:listOfValues").getText(i).split("\\|");

						this.sop(" Values in the list box at index "+i+" is :" + Arrays.asList(value).toString());
						
						if (value[0].equals(valueToSelect) || (splitted && value[0].equals(actualValuetoSelect)) ) 
						{
								
							forms.listOfValues("//forms:listOfValues").choose(i);
							this.sop("Selected Value is :"+valueToSelect);
							break;
						}
					}
				}
			}
		} else {
			info("Do Nothing");
		}
	}

	
	/*
	 * Select value from Form Select LOV
	 * 
	 * Ex:
	 * 
	 * 1. GE
	 * 2. GE|VisionOperations
	 * 3. GE%VisionOperations
	 * 4. GE||Vision Operations
	 * 
	 * 
	 */
	public void formSelectLOV(String editLogicalName, String valueToSelect) throws Exception {
		
		System.out.println("****************** Start: formSelectLOV ******************* ");
		
		String actualValuetoSelect	=	"";
		
		if (forms.listOfValues(	"//forms:listOfValues").exists(5, TimeUnit.SECONDS))// || editLogicalName == null || "".equals(editLogicalName)) 
		{
			if (valueToSelect.contains("|")) {
				
				System.out.println("Value to Select is :"+valueToSelect);
				
				forms.listOfValues("//forms:listOfValues").select(valueToSelect);
				
				formSelectLOV1(valueToSelect);
				
				return;
			}else{
				
				think(4);
				
				// Setting value in find field & click on find button
				System.out.println("Setting the value in the List text field and Clicking on Find button");
				forms.listOfValues("//forms:listOfValues").find(valueToSelect);

				think(2);
				System.out.println("Selecting the value from list box");
				if (forms.listOfValues("//forms:listOfValues").exists(5, TimeUnit.SECONDS)) {
					
					formSelectLOV1(valueToSelect);
					
				}else{
					//Here Lov closed after clicking Find button
					System.out.println("LOV Closed after Clicking Find button");
					System.out.println("Selected Value is :"+valueToSelect);
				}
			}
			
			
		} else if (!"".equals(editLogicalName) && editLogicalName != null) {
			
			System.out.println("Enter the value in textfield and then select value from formSelectLOV");
			
			String xpath = "//forms:textField[(@name='" + editLogicalName + "')]";

			if (editLogicalName.contains("name=")) {

				String logicName[] = editLogicalName.split("=");
				xpath = "//forms:textField[(@name=" + logicName[1] + ")]";

			}

			System.out.println("Edit Field Xpath is " + xpath);
			
			// Open Dialog
			forms.textField(xpath).openDialog();

			think(4);
			
			if (forms.choiceBox("//forms:choiceBox").exists()) {
				forms.choiceBox("//forms:choiceBox").clickButton("Yes");
			}
			
			if (forms.listOfValues("//forms:listOfValues").exists()) {
				
				if (valueToSelect.contains("|")) { // if there is "|" then search the list with first value
					
					String firstValue_ValueToSelect = valueToSelect.split("\\|")[0];
					
					System.out.println("Search string in the formSelectLOV : " + firstValue_ValueToSelect);
					forms.listOfValues("//forms:listOfValues").find(firstValue_ValueToSelect);
				}else{
					System.out.println("Search string in the formSelectLOV : " + valueToSelect);
					forms.listOfValues("//forms:listOfValues").find(valueToSelect);
				}
								
				formSelectLOV1(valueToSelect);
				
			}else{
				//Here Lov closed after clicking Find button
				System.out.println("Selected Value is :"+valueToSelect);
			}
			
			
		} else {
			info("Do Nothing");
		}
		
		this.sop("****************** END: formSelectLOV2 ******************* ");
	}
	
	
	public void formSelectLOV1(String valueToSelect) throws Exception{
		
		System.out.println("****************** START: formSelectLOV1 ******************* ");
		
		if (forms.listOfValues("//forms:listOfValues").exists()) {
			
			// Get Item Count
			int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
			
			System.out.println("Item Count in Form Select LOV :"+itemCount);
			
			// Iterating through each item
			for (int itemIndex = 1; itemIndex <= itemCount; itemIndex++) {
				
				// Get the Item
				String item = forms.listOfValues("//forms:listOfValues").getText(itemIndex);
				System.out.println(" Values in the list box at index "+itemIndex+" is :" + item);
				
				// Split the Values
				String[] actualValueToSelect = item.split("\\|");
				
				boolean itemVerified = false;
				if(valueToSelect.contains("|")){// This is includes both "|" and "*"
					
					String[] expectedValueToSelect	=	valueToSelect.split("\\|");
					
					//Verification of Expected values with Actual values
					for(int index=0; index < expectedValueToSelect.length; index++){
						
						String expColValue = expectedValueToSelect[index];
						String actColValue = actualValueToSelect[index];
						System.out.println("Actual value :"+actColValue+" Expected value :"+expColValue);
						
						if(expColValue.contains("%")){
							expColValue = expColValue.replace("%", ".*");
							
							itemVerified = compareStrings(expColValue+".*", actColValue);
						}else{
							itemVerified = compareStrings(expColValue+".*", actColValue);
						}
					}
				}else if(!valueToSelect.contains("|") && valueToSelect.contains("%")){
					
					String actValue = item.trim();
					String expValue = valueToSelect.replaceAll("%", ".*");
					
					itemVerified = compareStrings(expValue, actValue);
					
					if(!itemVerified){
						
						itemVerified = compareStrings(".*"+expValue+".*", actValue);
					}
					System.out.println("Actual value :"+actValue+" Expected value :"+expValue);
					
					
				}else{// If only first column value is passed, Then there is only one item passed
					
					//itemVerified = compareStrings(valueToSelect+".*", actualValueToSelect[0]);
					
					/**
					 * Not using reqular expression as some times, values are displaying as 
					 * 
					 * Operating Unit Re
					 * Operating Unit abc
					 * Operating Unit
					 * 
					 */
					itemVerified = compareStrings(valueToSelect, actualValueToSelect[0]);
				}
				
			
				System.out.println("Able to find the item to be selected :"+itemVerified);
				if(itemVerified){
					forms.listOfValues("//forms:listOfValues").choose(itemIndex);
					info("Selected Value is :"+item);
					break;
				}
				
			}
		}
		
		this.sop("****************** END: formSelectLOV1 ******************* ");
		
	}
	
	
	public void formSelectDate(String xpath, String dateValue) throws Exception {

		forms.textField(xpath).openDialog();

		if (forms.calendar(54,"//forms:calendar").exists()) {
			forms.calendar(54,"//forms:calendar").enter(dateValue);
		}

	}

	public boolean formVerifyEdit(String editLogicalNameXPath, String expectedValue) throws Exception {

		String Text = "";
		boolean verify = false;

		// forms.textField("//forms:textField[(@name='"+editLogicalName+"')]").exists(
		if (forms.textField(editLogicalNameXPath).exists(5, TimeUnit.SECONDS)) {
			// System.err.println("Inside if");

			Text = forms.textField(editLogicalNameXPath).getText();
			this.sop("Text got from formVerifyEdit ::::>" + Text+"<::::");
			System.out.println("Text got from formVerifyEdit ::::>" + Text+"<::::");
			if("".equals(Text) && "".equals(expectedValue))
			{ 
				info("The text matches with the expected value and is empty as expected");
			}
			else if (Text != null && !"".equals(Text)/* && Text.equals(expectedValue) == true*/) {
				
				verify = compareStrings(expectedValue, Text.trim());
				boolean verify2	=	 compareStrings(expectedValue, Text.trim());
				boolean verify3 = false;
				if(expectedValue!=null && !"".equals(expectedValue))
					verify3	=	compareStrings(expectedValue.trim(), Text.trim());
				if(verify || verify2 || verify3 ){
					System.out.println("The text :::>"+Text+" matches with the expected value :::>" + expectedValue);
					info("The text :::>"+Text+" matches with the expected value :::>" + expectedValue);
				}else{
					reportFailure("The text :::>"+Text+" doesn't match with the expected value :::>" + expectedValue);
				}
				
			} else {
				reportFailure("The text :::>"+Text+" doesn't match with the expected value :::>" + expectedValue);
				verify = false;
			}

		}else{
			reportFailure("Unable to find the TextField with the XPath :"+editLogicalNameXPath);
		}
		
		return verify;

	}

	/**
	 * 
	 * 
	 * Note: Not using this.formSelectLOV(null, fieldValue); because it is returning value as 
	 * 								[Encumbered Amount                                        *ENCUMBERED_AMOUNT_DSP]
	 * hence handling logic in this function
	 * 
	 * @param windowName
	 * @param fieldValue
	 * @throws Exception
	 */
	public void formShowField(String windowName, String fieldValue) throws Exception {

		forms.window("//forms:window[(@name='" + windowName + "')]").selectMenu("Folder|Show Field...");
			
		think(4);

		if (forms.listOfValues(	"//forms:listOfValues").exists()) {

			if (fieldValue.contains("|")) {
				
				String value[] = fieldValue.split("\\|");
				
				forms.listOfValues("//forms:listOfValues").find(value[0]);
				
				forms.listOfValues("//forms:listOfValues").select(fieldValue);
				
				return;
			}
			
			think(4);
			
			// Setting value in find field & click on find button
			this.sop("Setting the value in the List text field and Clicking on Find button");
			forms.listOfValues("//forms:listOfValues").find(fieldValue);

			think(2);
			
			this.sop("Selecting the value from list box");
			if (forms.listOfValues("//forms:listOfValues").exists()) {
				
				int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
				this.sop("Item Count :"+itemCount);
				
				for (int i = 1; i <= itemCount; i++) {
					
					String[] value = forms.listOfValues("//forms:listOfValues").getText(i).split("\\|");

					this.sop(" Values in the list box at index "+i+" is :" + Arrays.asList(value).toString());
					
					String[] firstCell = value[0].split("    ");

					//this.sop("firstCell :"+firstCell[0]);
					
					String actData = firstCell[0].trim();
					this.sop("actData :"+actData);
						
					if (actData.equals(fieldValue)) {
							forms.listOfValues("//forms:listOfValues").choose(i);
						break;
					}
				}
			}
		}
		
		
	}
	public void formHideField(String fieldName,String editFieldName) throws Exception {
		forms.editBox("//forms:window[(@name='" + editFieldName + "')]").setFocus();
		forms.window(
			"//forms:window[(@name='" + fieldName + "')]").selectMenu(
			"Folder|Hide Field");

	}
	public void formHideField(String fieldName) throws Exception {

		forms.window(
			"//forms:window[(@name='" + fieldName + "')]").selectMenu(
			"Folder|Hide Field");

	}

	public void webVerifyText(String text) throws Exception {
		webVerifyText(text, "0");
	}
	
	public void webVerifyText(String text,String docIndex) throws Exception {
		String[] compTypes = { "span", "label", "div", "li", "b", "h1", "h2", "h3", "h4", "h5", "h6", "a" ,"td","p","pre"};

		verifyText(	text , docIndex, compTypes);

	}

	public void webClickImage(String imageName) throws Exception {

		System.out.println("****************** Start of webClickImage ****************** ");

		String windowPath = getCurrentWindowXPath();
		int imageIndex = 0;
		int documentIndex = 0;
		int formIndex = 0;
		String expImgName = "";
		String compType = "img";
		
		String imageDetails[] = imageName.split(",");
		
		for (int index = 0; index < imageDetails.length; index++) {

			if (imageDetails[index].trim().startsWith("document=")) {

				documentIndex = Integer.parseInt(imageDetails[index].split("=")[1]);

			} else if (!imageDetails[index].contains("document=") && !imageDetails[index].contains("form=")) {

				String[] imgDetails = imageDetails[index].split(";");

				// Similar Image Index
				if (imgDetails.length > 1) {
					imageIndex = Integer.parseInt(imgDetails[1]);
				}

				// Image Name
				expImgName = imgDetails[0];

			} else if (imageDetails[index].trim().startsWith("form=")) {

				formIndex = Integer.parseInt(imageDetails[index].split("=")[1]);
			}
		}
		
		
		if(expImgName.contains("alt='")){
			
			expImgName = expImgName.substring(5, expImgName.length()-1);
			
		}

		this.sop("****** Image Details ********************* ");
		this.sop("Image Name: \"" + expImgName + "\"  Image Index: \"" + imageIndex + "\"  Document Index: \""
				+ documentIndex + "\"  Form Index: \"" + formIndex + "\"");

		// Document Path
		String docPath = windowPath + "/web:document[@index='" + documentIndex + "']";
		String formPath = docPath+"/web:form[@index='" + formIndex + "']";
		this.sop("Document Path :" + docPath);

		PropertyTestList propertyTestList = new PropertyTestList();
		propertyTestList.add("alt",	expImgName,TestOperator.StringWildCard);

		List<DOMElement> imageList = web.document(docPath).getElementsByTagName("img",propertyTestList);

		this.sop("imageList Count matching " + expImgName + " is :" + imageList.size());

		try {
			if (imageIndex > 0) {
				imageList.get(imageIndex).click();
			} else {
				imageList.get(0).click();
			}
		} catch (IndexOutOfBoundsException e) {

			reportFailure("No image found for the specified image Name" + imageName);

			throw new Exception("webClickImage : No image found for the specified image Name"+imageName);

		}

		System.out.println("****************** End of webClickImage ****************** ");

	}

	public void webVerifyList(String path, String expectedValue) throws Exception {

		// String [] selectedValues = web.selectBox(path).getSelectedValue();
		String[] selectedValues = web.selectBox(path).getSelectedText();

		String[] expectedValues = expectedValue.split(";");

		verifyData(selectedValues,expectedValues);
	}

	public void webVerifyListBox(String path, String expectedValue) throws Exception {

		// String [] selectedValues = web.selectBox(path).getSelectedValue();
		String[] selectedValues = web.selectBox(path).getSelectedText(); // as per the bug: 572

		this.sop("Selected List :"+Arrays.asList(selectedValues));
		String[] expectedValues = expectedValue.split(";");
		this.sop("Expected List :"+Arrays.asList(expectedValues));

		verifyData(selectedValues,expectedValues);
	}
	// function added by ramaraju
	// 
	public String getNextSunday(String time,String format) {

        // TODO Auto-generated method stub
        return getNextWeekDay("sunday",time,format);
 }
	// function added by ramaraju
	// 
 public String getNextWeekDay(String sDay,String time,String format) {

        // TODO Auto-generated method stub

//      Test Parameters

//      time="06-SEP-2013 12:48:25";

//      format="CC dd-MMM-yyyy hh:mm:ss";

//      sDay="sunday";

        String formatCase="";

        String newTime="";

        if(format.toUpperCase().startsWith("CC")){

               formatCase=format.substring(0,2);

               format=format.substring(2, format.length());

        }

        int offset=0;

        sDay=sDay.substring(0, 3).toUpperCase();

        System.out.println(sDay);

        if(sDay.equals("SUN"))offset=1;

        if(sDay.equals("MON"))offset=2;

        if(sDay.equals("TUE"))offset=3;

        if(sDay.equals("WED"))offset=4;

        if(sDay.equals("THU"))offset=5;

        if(sDay.equals("FRI"))offset=6;

        if(sDay.equals("SAT"))offset=7;



        try{

               DateFormat formatter = new SimpleDateFormat(format.trim());

               Date date = formatter.parse(time);

               Calendar cal=Calendar.getInstance();

               cal.setTime(date);

               int day=cal.get(Calendar.DAY_OF_WEEK);

              

               int diff=offset-day;

               if(diff<0){

                     diff=7+diff;

               }

               if(diff==0){

                    

               }else{

                     cal.add(Calendar.DATE, diff);

                    

               }

               cal.set(Calendar.SECOND,0);

               if(formatCase.trim().equals("CC"))

                     newTime=formatter.format(cal.getTime()).toUpperCase();

               else if(formatCase.trim().equals("cc"))

                     newTime=formatter.format(cal.getTime()).toLowerCase();

               else

                     newTime=formatter.format(cal.getTime());

               System.out.println(newTime);

               return newTime;

                    

        }

        catch(Exception e){

               e.printStackTrace();

        }

        return null;

       

 }	
	// Function developed by Ramraju Gelli.
	// DatE: 6th sep 2013
	 public void oracle_launch_isupport_url() throws Exception {

         String oracle_php_url=getVariables().get("oracle_php_url");

         String oracle_isupport_url=oracle_php_url.substring(0, oracle_php_url.indexOf("OA_HTML")+7)+"/ibuhpage.jsp";

         System.out.println(oracle_isupport_url);

         getVariables().set("oracle_isupport_url",oracle_isupport_url);

//       browser.launch();

         String pageindex = web.getFocusedWindow().getAttribute("index");

         web.window("/web:window[@index='"+pageindex+"']").navigate(getVariables().get("oracle_isupport_url"));

   }
	public void webVerifyListBoxValues(String path, String expectedValue) throws Exception {

		// String [] selectedValues = web.selectBox(path).getSelectedValue();

		String[] selectedValues = web.selectBox(path).getSelectedText();

		String[] expectedValues = expectedValue.split(";");

		verifyData(selectedValues,expectedValues);
	}

	public void webVerifyListValues(String path, String expectedValue) throws Exception {

		// String [] selectedValues = web.selectBox(path).getSelectedValue();
		String[] selectedValues = web.selectBox(path).getSelectedText();

		String[] expectedValues = expectedValue.split(";");

		verifyData(selectedValues,expectedValues);
	}

	public void clickLink(String linkName, int index) throws Exception {
		
		int documentIndex = 0;
		
		String linkPathetails[] = linkName.split(",");	
		String actLinkName = linkPathetails[0];
		
		// Get the Button Details passed from User
		for (int linkNamesIndex = 0; linkNamesIndex < linkPathetails.length; linkNamesIndex++) {

			if (linkPathetails[linkNamesIndex].trim().startsWith("document=")) 
			{

				documentIndex = Integer.parseInt(linkPathetails[linkNamesIndex].split("=")[1]);

			}
			
		}
		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		String windowTitle = web.window(windowPath).getAttribute("title");
		String docPath = windowPath + "/web:document[@index='"+documentIndex+"']";

		this.sop("docPath :" + docPath);
		this.sop("actLinkName :" + actLinkName);
		// Pattern
		PropertyTestList propertyTestList = new PropertyTestList();
		propertyTestList.add("text",actLinkName,TestOperator.StringWildCard);

		List<DOMElement> linksList = web.document(docPath).getElementsByTagName("a",propertyTestList);

		this.sop("Number of links with link name \""+actLinkName+ "\" in the page is :" + linksList.size());

		DOMElement link = linksList.get(index);

		link.click();
		
		if(linksList.size() > 1){
			info("Clicked on "+convertNumberToWords(index)+"\""+actLinkName.trim()+"\" link in the page \""+windowTitle+"\"");
		}else{
			info("Clicked on \""+actLinkName.trim()+"\" link in the page \""+windowTitle+"\"");
		}

	}

	public void clickLink(String linkName, String windowName, int compSequence, boolean isFormPathExists)
			throws Exception {

		boolean linkExists = false;
		String path = null;

		/* Document Path */
		String docPath = getCurrentWindowXPath() + "/web:document[@index='0']";

		if (isFormPathExists) {
			/* Form Path */
			path = docPath + "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']";
		} else {
			/* Doc Path */
			path = docPath;
		}

		HashMap<String, HashMap<String, String>> links = new HashMap<String, HashMap<String, String>>();

		String component = "a";
		String[] linkAttr = { "text", "index", "id", "name" };

		links = getLinkNames(
			linkName,
			linkAttr,
			isFormPathExists);

		if (links != null) {

			HashMap<String, String> linkAttributes = links.get(Integer.toString(compSequence));

			if (linkAttributes != null) {

				String xPath = getCompXPath(
					"a",
					linkAttributes);
				web.link(
					path + xPath).waitFor(
					SYNCTIME);
				web.link(
					path + xPath).click();

				linkExists = true;
			}
		}

		if (linkExists && windowName != null) {

			web.window(
				"/web:window[@title='" + windowName + "']").waitForPage(
				SYNCTIME);
			
		}
	}

	private HashMap<String, HashMap<String, String>> getLinkNames(String linkName, String[] expAttributes)
			throws Exception {

		return getLinkNames(
			linkName,
			expAttributes,
			true);
	}

	@SuppressWarnings("unchecked") private HashMap<String, HashMap<String, String>> getLinkNames(String linkName,
			String[] expAttributes, boolean isFormPathExists) throws Exception {

		HashMap<String, HashMap<String, String>> links = new HashMap<String, HashMap<String, String>>();
		String path = null;

		/* Document Path */
		String docPath = getCurrentWindowXPath() + "/web:document[@index='0']";

		/* Get All the link components */

		List<DOMElement> linkComponents = null;

		if (isFormPathExists) {
			/* Form Path */
			path = docPath + "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']";
			web.element(
				path).waitFor(
				SYNCTIME);
			linkComponents = web.element(
				path).getElementsByTagName(
				"a");
		} else {
			/* Doc Path */
			DOMDocument doc = web.document(docPath);
			doc.waitFor(SYNCTIME);
			linkComponents = doc.getElementsByTagName("a");
		}

		/* Check for the required link */
		for (int compIndex = 0, seqIndex = 0; compIndex < linkComponents.size(); compIndex++) {

			DOMElement Comp = linkComponents.get(compIndex);

			if (Comp.getAttribute("text") != null) {

				if (Comp.getAttribute(
					"text").equalsIgnoreCase(
					linkName)) {

					HashMap<String, String> linkAttributes = new HashMap<String, String>();

					linkAttributes = getTagAttributes(
						Comp,
						expAttributes);

					links.put(
						Integer.toString(seqIndex),
						linkAttributes);
					seqIndex++;
				}
			}
		}
		return links;
	}

	public void webClickLink(String linkName) throws Exception {

		String[] linkNameIndex = linkName.split(";");

		if (linkNameIndex.length > 1) {

			clickLink(linkNameIndex[0],	toInt(linkNameIndex[1]));

		} else {

			clickLink(linkNameIndex[0],0);
		}

	}

	public void webClickButton(String buttonName) throws Exception {

		System.out.println("****** Start of webClickButton *********************** ");

		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		String windowTitle = web.window(windowPath).getAttribute("title");
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		int documentIndex = 0;
		int formIndex = -1;
		int buttonIndex = 0;
		String formName = "";
		String expButtonName = "";
		String compType = "button";
		String buttonType	=	"";

		String buttonPathDetails[] = buttonName.split(",");	

		// Get the Button Details passed from User
		for (int index = 0; index < buttonPathDetails.length; index++) {

			if (buttonPathDetails[index].trim().startsWith("document=")) 
			{

				documentIndex = Integer.parseInt(buttonPathDetails[index].split("=")[1]);

			} else if (!buttonPathDetails[index].contains("document=") && !buttonPathDetails[index].contains("form=") && !buttonPathDetails[index].contains("input")) {

				String[] buttonDetails = buttonPathDetails[index].split(";");

				// Similar Button Index
				if (buttonDetails.length > 1) 
				{
					
					buttonIndex = Integer.parseInt(buttonDetails[1]);
				}

				// Button Name
				expButtonName = buttonDetails[0];
				this.sop("expButtonName>>"+ expButtonName);

			} 
			else if (buttonPathDetails[index].trim().startsWith("form=")) 
			{

				String form = buttonPathDetails[index].split("=")[1];
				
				//this.sop("form :"+form);
				/*boolean formTextAsInt = form.matches("\\d");
				
				if(formTextAsInt){
					formIndex = Integer.parseInt(form);
				}else{
					formName = form;
				}*/
				formIndex = Integer.parseInt(buttonPathDetails[index].split("=")[1]);
			}
			if (buttonPathDetails[index].trim().startsWith("input")) 
			{
				buttonType	=	"input";
			}
		}
		this.sop("Button Type:" + buttonType);
		this.sop("****** Button Details ********************* ");
		this.sop("Button Name: \"" + expButtonName + "\"  Button Index: \"" + buttonIndex + "\"  Document Index: \""
				+ documentIndex + "\"  Form Index: \"" + formIndex + "\"  Form Name: \"" + formName + "\"");

		// Document Path
		String docPath = windowPath + "/web:document[@index='" + documentIndex + "']";
		
		String formPath = "";
		if(formIndex < 0){
			formPath = docPath+"/web:form[@name='" + formName + "']";
		}else{
			formPath = docPath+"/web:form[@index='" + formIndex + "']";
		}

		this.sop("Document Path :" + docPath);

		List<DOMElement> buttonList = new ArrayList<DOMElement>();
		if(!buttonName.contains("form="))
		{
			this.sop("Entered for form is not passed");
			if("input".equals(buttonType))
			{
				this.sop("Entered here:");
				PropertyTestList propList = new PropertyTestList();
				propList.add("type","button",TestOperator.StringWildCard);				
				buttonList = web.document(docPath).getElementsByTagName("input",propList);
			}
			else
				buttonList = web.document(docPath).getElementsByTagName("button");
		}else
		{
			this.sop("Getting the buttons by considering form");		
			buttonList = web.element(formPath).getElementsByTagName("button");
		}
		
		this.sop("Buttons(tag=button) List count:" + buttonList.size());
		
		if (buttonList.size() == 0) 
		{
			

			// List<DOMElement> inputButtonList =
			// web.document(docPath).getElementsByTagName("input",
			// propertyTestList);
			
			if(!buttonName.contains("form=")){
				
				PropertyTestList propertyTestList = new PropertyTestList();
				propertyTestList.add("type","button",TestOperator.StringWildCard);
				
				buttonList = web.document(docPath).getElementsByTagName("input",propertyTestList);
				
				this.sop("buttonList Count :" + buttonList.size());
				
			}else
			{
				
				List<DOMElement> inputList = web.element(formPath).getElementsByTagName("input");
				
				for(int index=0; index <inputList.size() ; index++)
				{
					//this.sop("input button:" + index);
					String inputType = inputList.get(index).getAttribute("type");
					//this.sop("Type:" + inputType);
					if(inputType.equalsIgnoreCase("button")){
						buttonList.add(inputList.get(index));
					}else if(inputType.equalsIgnoreCase("submit")){
						buttonList.add(inputList.get(index));
					}
				}
				
				this.sop("input button List Count :" + buttonList.size());
			}
		}

		List<DOMElement> expectedButtonsList = new ArrayList<DOMElement>();
		
		for (int i = 0; i < buttonList.size(); i++) {

			DOMElement button = buttonList.get(i);

			String value = button.getAttribute("text");
			
			if(value == null){
				value = button.getAttribute("value");
			}

			
			//Start of Removing script tag -- IE9 Change
			List<DOMElement> scriptElements = button.getElementsByTagName("script");
			
			for(int scriptIndex=0; scriptIndex < scriptElements.size(); scriptIndex++){
				
				String scriptText = "";
				
				DOMElement scriptEle =  scriptElements.get(scriptIndex);
				scriptText = scriptEle.getAttribute("text");
				
				if(scriptText != null){
					value = value.replace(scriptText, "");
				}	
			}
			//End of Removing script tag -- IE9 Change
			
			
			this.sop("button"+i+" value :" + value);

			if(value != null && !"".equalsIgnoreCase(value)){
				
				value = value.trim().replaceAll("([\\s]+)([\\s]+)", "");
				
				value = extractButtonValue(value);
				
				this.sop("after replace button value :" + value);

				if (value.trim().equals(expButtonName.trim())) {

					expectedButtonsList.add(button);
				}	
				
			}
		}

		this.sop("Count of Expected Buttons :" + expectedButtonsList.size());

		// Clicking on Specified Button
		if (expectedButtonsList != null && expectedButtonsList.size() != 0) {
			expectedButtonsList.get(buttonIndex).waitFor();
			//expectedButtonsList.
			expectedButtonsList.get(buttonIndex).click();
			
			if(expectedButtonsList.size() > 1){
				info("Clicked on "+convertNumberToWords(buttonIndex)+"\""+expButtonName.trim()+"\" button in the page \""+windowTitle+"\"");
			}else{
				info("Clicked on \""+expButtonName.trim()+"\" button in the page \""+windowTitle+"\"");
			}
			
		} else {
			//this.sop("Unable to find the Expected Button \"" + expButtonName + "\"");
			this.sop("Button tag based buttons are not found checking with input tag buttons.");
			
			/*
			 * Code written by srinivas to check if there are any iinput tag based buttons if not found earlier
			 */
			String[] btnName	=	buttonName.split(";");
			PropertyTestList propertyTestList = new PropertyTestList();
			propertyTestList.add("type","button",TestOperator.StringWildCard);
			propertyTestList.add("value",btnName[0],TestOperator.StringWildCard);
			
			buttonList = web.document(docPath).getElementsByTagName("input",propertyTestList);
			int btnCnt	=	buttonList.size();
			this.sop("input Buttons Count :" + btnCnt + " with display name:" + btnName[0]);
			
			if(btnCnt>0)
			{
				int counter	=	0;
				if(btnName.length>1)
					counter = Integer.parseInt(btnName[1]);
				
				if(buttonList.size()>counter)
				{	
					buttonList.get(counter).click();	
				}
					
			}
			else
			{
				this.sop("Input based button is not found with dsiplay name:" + btnName[0]);
			}
		}

		System.out.println("****** End of webClickButton *********************** ");
	}

	
	/**
	  *  Function to enter value in the textfield having LOV
	  *  
	  * @param textFieldAttributes
	  * @param searchByOption
	  * @param searchValue
	  * @param colName
	  * @param rowValue
	  * @throws Exception
	  */
	 public void webSetTextWithLOV(String textFieldAttributes, String searchByOption, String searchValue, String colName, String rowValue) throws Exception
	 {
		 	int documentIndex = 0;
			int formIndex = 0;
			String textFieldAttribute = "";
			
			Thread.sleep(10000);
			// Get Current Window Index
			String  windowIndex= web.getFocusedWindow().getAttribute("index");
			//info("windowIndex :"+windowIndex);
			
			String textFieldPathDetails[] = textFieldAttributes.split(",");	
			
			
			// Get the Button Details passed from User
			for (int index = 0; index < textFieldPathDetails.length; index++) {

				if (textFieldPathDetails[index].trim().startsWith("document=")) 
				{
					documentIndex = Integer.parseInt(textFieldPathDetails[index].split("=")[1]);

				} else if (textFieldPathDetails[index].trim().startsWith("form=")) 
				{

					String form = textFieldPathDetails[index].split("=")[1];
					
					formIndex = Integer.parseInt(textFieldPathDetails[index].split("=")[1]);
				}else if (textFieldPathDetails[index].trim().startsWith("id=") || textFieldPathDetails[index].trim().startsWith("name=")) 
				{
					if(textFieldPathDetails[index].trim().contains("'")){
						textFieldAttribute = textFieldPathDetails[index].trim();
					}else {
						textFieldAttribute = "";
					}
					
				}
			}
				
			// Set Path for Current Window
			String docPath="/web:window[@index='"+windowIndex+"']/web:document[@index='"+documentIndex+"']";
			String formPath= docPath + "/web:form[@index='"+formIndex+"']";
			String textFieldPath = formPath + "/web:input_text[@"+textFieldAttribute+"]";
			//info("textFieldPath for the testfield having Web Select LOV :"+textFieldPath);
			
			// Set the value in the text field
			web.textBox(textFieldPath).setText(searchValue);
		 
			// Press tab after setting text
			web.textBox(textFieldPath).pressTab();
		
			// Check the LOV window exists
			boolean searchWindowExists = web.window("/web:window[@title='Search and Select List of Values']").exists(40, TimeUnit.SECONDS);
			
			info("searchWindowExists :"+searchWindowExists);
			
			// If window exists, then call WebSelectLOV functions
			if(searchWindowExists){
				//String lovName, String searchByOption, String searchValue, String colName, String rowValue
				webSelectLOV(null, searchByOption, searchValue, colName, rowValue);
			}
	 }
	
	
	
	
	
	public void webSelectLOV(String lovName, String searchByOption, String searchValue, String colName, String rowValue)
			throws Exception {
		System.out.println("Entered webSelectLov");
		List<String> lColNames = new ArrayList<String>();
		List<String> lrowValues = new ArrayList<String>();

		if (colName.contains(";")) {

			String[] sColNames = colName.split(";");
			lColNames = Arrays.asList(sColNames);
		} else {
			lColNames.add(colName);
		}

		if (rowValue.contains(";")) {
			String[] srowValues = rowValue.split(";");
			lrowValues = Arrays.asList(srowValues);
		} else {
			lrowValues.add(rowValue);
		}

		// Converting List to Array
		String[] colNames = lColNames.toArray(new String[lColNames.size()]);
		String[] rowValues = lrowValues.toArray(new String[lrowValues.size()]);
		System.out.println("Before calling actual webSelectLov");
		if (colNames.length == rowValues.length) {
			System.out.println("Inside IF webSelectLOV");
			webSelectFromLOV(
				lovName,
				searchByOption,
				searchValue,
				colNames,
				rowValues);
		} else {
			info("Unable to select the value from WebSelectLOV table as the values provided not mathcing:");
			this.sop("Column Names" + Arrays.asList(
				rowValues).toString() + "    RowValues :" + Arrays.asList(
				rowValues).toString());
		}
	}

	/**
	 * Select From LOV
	 * 
	 * @param lovName
	 * @param searchByOption
	 * @param searchValue
	 * @param colNames
	 * @param rowValues
	 * @throws Exception
	 */
	public void webSelectFromLOV(String lovName, String searchByOption, String searchValue, String[] colNames,
			String[] rowValues) throws Exception {

		System.out.println("******************** Strat of webSelectLoV ************************");
		
		this.sop("** lovName  :\"" + lovName + "\" searchByOption :\"" + searchByOption + "\"  searchValue :\""	+ searchValue + "\"");
		this.sop("** colNames  :\"" + Arrays.asList(colNames).toString() + "\" rowValues :\"" + Arrays.asList(rowValues).toString() + "\"");
		//System.out.println("** lovName  :\"" + lovName + "\" searchByOption :\"" + searchByOption + "\"  searchValue :\""	+ searchValue + "\"");
		//System.out.println("** colNames  :\"" + Arrays.asList(colNames).toString() + "\" rowValues :\"" + Arrays.asList(rowValues).toString() + "\"");

		int SYNCTIME = 10;
		String currentWindowTitle = "";

		//Get Current Window path
		String windowPath = getCurrentWindowXPath();
		currentWindowTitle = web.window(windowPath).getAttribute("title");

		if ((lovName != null) && !("".equals(lovName))) {
			
			/**To handle conditions when alt='Image Name' is passed**/
			if(lovName.contains("alt=")){
				
				String lovNames[] = lovName.split("=");
				
				lovNames[1] = lovNames[1].trim().replaceAll("'", "");
				
				lovName = lovNames[1];
			}
			
			// Clicking on image
			webClickImage(lovName);
		}

		/* LOV */
		System.out.println("******************** Strat of Selecting values from webSelectLoV ************************");
		// LOV Window path
		String searchWindowPath = "/web:window[@title='Search and Select List of Values']/web:document[@index='" + 1+ "']/web:form[@index='0']";
		
		web.window("/web:window[@title='Search and Select List of Values']").waitForPage(200,true);
		
		web.window("/web:window[@title='Search and Select List of Values']").exists(100, TimeUnit.SECONDS);

		if ((searchByOption != null) && (!("".equals(searchByOption)))) {

			/* Select Search By Field */
			web.selectBox(searchWindowPath+ "/web:select[(@id='categoryChoice' or @name='categoryChoice') and multiple mod 'False']").waitFor(SYNCTIME);
			
			//info("searchByOption :"+searchByOption);
			//info("value :"+web.selectBox(searchWindowPath+ "/web:select[(@id='categoryChoice' or @name='categoryChoice') and multiple mod 'False']").getSelectedText());
			web.selectBox(searchWindowPath+ "/web:select[(@id='categoryChoice' or @name='categoryChoice') and multiple mod 'False']").selectOptionByText(
				searchByOption);

		}

		/* Enter Search Text */
		web.textBox(searchWindowPath + "/web:input_text[@name='searchText']").waitFor(SYNCTIME);
		web.textBox(searchWindowPath + "/web:input_text[@name='searchText']").setText(searchValue);
		
		/* Click on Go button */
		System.out.println("Go button path :"+searchWindowPath + "/web:button[@text='Go']");
		web.button(searchWindowPath + "/web:button[@text='Go']").waitFor();
		web.button(searchWindowPath + "/web:button[@text='Go']").click();
		//Waiting for widnow to close
		Thread.sleep(5000);
		
        /* Wait for the text "Quick Select" */
        // ******** START OF BUG #1026***************
		web.radioButton(searchWindowPath+"/web:input_radio[@index='0']").exists(200, TimeUnit.SECONDS);
        // ******** END OF BUG #1026***************


		String lovWindowIndex = web.getFocusedWindow().getAttribute("index");
		if ((lovName == null) && ("".equals(lovName))) {
			lovWindowIndex = web.getFocusedWindow().getAttribute("index");
		}
		info("lovWindowIndex:"+lovWindowIndex);
		
		
		if ((colNames[0] == null) || ("".equals(colNames[0]))) {

			colNames[0] = web.selectBox(searchWindowPath+ "/web:select[(@id='categoryChoice' or @name='categoryChoice') and multiple mod 'False']").getSelectedText()[0];
		}
		if ((rowValues[0] == null) || ("".equals(rowValues[0]))) {
			rowValues[0] = searchValue;
		}

		
		System.out.println("** Start of Table in WebSelectLov ****");
		
		// Creation of Search columns
		HashMap <String,String> searchColumns = new HashMap<String,String>();
		for(int i=0; i< colNames.length ; i++){
			searchColumns.put(colNames[i],rowValues[i]);
		}
		
		//Creation of action hashmap
		//Keyword ,object ,caption ,logical name, outputvar, function Name, params
		HashMap<String,String> action = new HashMap<String,String>();
		action.put("keyword", "SELECT");
		action.put("object","RADIO");
		action.put("displayname", "Select"); 
		action.put("logicalname", "Select");
		action.put("outputvar", "");
		action.put("funcname", "");
		action.put("value", "true");	
		
		// Getting WebTable Object
		String tableName = "Select";
		
		wEBTABLEOBJ.getWebTableObject(tableName+",document=1");
		
		// Finding Row Number
		int rowNumber = wEBTABLEOBJ.findRowNumber(searchColumns, tableName);
		
		// Selecting Radio button
		wEBTABLEOBJ.radioOn("Select", rowNumber, action);
		
		// Clear Web Table Object
		wEBTABLEOBJ.clearWebTableObject();
		
		/* Click on Select button */
		//webClickButton("Select,document=1");
		web.button("/web:window[@title='Search and Select List of Values']/web:document[@index='1']/web:button[@text='Select']").waitFor();
		web.button("/web:window[@title='Search and Select List of Values']/web:document[@index='1']/web:button[@text='Select']").click();
		
		System.out.println("** End of Table in WebSelectLov ****");
		
		//Waiting for widnow to close
		Thread.sleep(5000);
		
		//Check availability of LOV
		while(web.window("/web:window[@title='Search and Select List of Values']").exists())
		{
			info("Checking availability of \"Search and Select List of Values\" window:Available");
			Thread.sleep(1000);
		}
		
		//Get Current Window path
		String latestWindowTitle = web.window(getCurrentWindowXPath()).getAttribute("title");
		info("Current Window Title :"+latestWindowTitle);
		
		info("*** Wait for Page Error is Ignored by the WebSelectLOV function, if occurs after selecting value from LOV***");
		
		getSettings().setErrorRecovery(WebErrorRecovery.ERR_WAIT_FOR_PAGE_TIMEOUT_ERROR, ErrorRecoveryAction.Ignore);
		
		if ((lovName != null) && !("".equals(lovName))) {// When LOV Name is available
			
			try{
				web.window("/web:window[@title='"+currentWindowTitle+"']").waitForPage(100,false);				
				web.window("/web:window[@title='"+currentWindowTitle+"']").exists(100, TimeUnit.SECONDS);
				
			}catch(Exception e){
				info("In the Exception block");
				web.window("/web:window[@title='"+currentWindowTitle+"']").waitForPage(100,true);				
				web.window("/web:window[@title='"+currentWindowTitle+"']").exists(100, TimeUnit.SECONDS);
			}
			
		}else{
			
			int currentWindowIndex = Integer.parseInt(lovWindowIndex)-1;
			System.out.println("** currentWindowIndex:"+currentWindowIndex);
			try{
				
				web.window("/web:window[@index='"+currentWindowIndex+"']").waitForPage(100,false);				
				web.window("/web:window[@index='"+currentWindowIndex+"']").exists(100, TimeUnit.SECONDS);
				/*if(currentWindowIndex>0)
				{
					System.out.println("** Inside If ***");
					web.window("/web:window[@index='"+(currentWindowIndex-1)+"']").waitForPage(200,false);				
					web.window("/web:window[@index='"+(currentWindowIndex-1)+"']").exists(100, TimeUnit.SECONDS);					
				}*/
			}catch(Exception e){
				web.window("/web:window[@index='"+currentWindowIndex+"']").waitForPage(100,true);				
				web.window("/web:window[@index='"+currentWindowIndex+"']").exists(100, TimeUnit.SECONDS);
				/*if(currentWindowIndex>0)
				{
					web.window("/web:window[@index='"+(currentWindowIndex-1)+"']").waitForPage(200,true);				
					web.window("/web:window[@index='"+(currentWindowIndex-1)+"']").exists(100, TimeUnit.SECONDS);					
				}*/				
			}		
		}

		getSettings().setErrorRecovery(WebErrorRecovery.ERR_WAIT_FOR_PAGE_TIMEOUT_ERROR, ErrorRecoveryAction.ReportErrorAndContinue);
		
		System.out.println("******************** End of webSelectLoV ************************");

	}

	private int findRowNumber(String tableName, HashMap<String, String> searchColumns) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void webSelectCheckBoxFromLOV(String colName, String rowValuesToSelect) throws Exception {
		
		String[] rowValues = rowValuesToSelect.split(":");
		String  tblName = "Select";
		
		HashMap<String,String> action = new HashMap<String,String>();
		
		//Keyword ,object ,caption ,logical name, outputvar, function Name, params
		
		action.put("keyword", "CHECK");
		action.put("object","CHECKBOX");
		action.put("displayname", "Select"); 
		action.put("logicalname", "Select");
		action.put("outputvar", "");
		action.put("funcname", "");
		action.put("value", "true");
		/* Select Table */
		//initializeWebTable();
		wEBTABLEOBJ.getWebTableObject(tblName+",document=1");
		
		System.out.println("Selecting checkboxes");
		
		for(int rowIndex=0; rowIndex < rowValues.length;rowIndex++ ){
			
			HashMap <String,String> searchColumns = new HashMap<String,String>();
			
			searchColumns.put(colName,rowValues[rowIndex]);
			
			int rowNumber = wEBTABLEOBJ.findRowNumber(searchColumns, tblName);
			
			System.out.println("rowNumber :"+rowNumber);
			
			wEBTABLEOBJ.checkOn(tblName, rowNumber, action);

		}

		wEBTABLEOBJ.clearWebTableObject();
	}
	
	public void webVerifyLink(String linkPath, String expectedValue) throws Exception {

		String actualVal = web.link(linkPath).getAttribute("text");
		this.sop(" Actual Link value in webVerifyLink  is :" + actualVal);

		if ((actualVal != null) && (actualVal.equals(expectedValue))) {

			info("Expected Value:" + expectedValue + "  matches with actual value:" + actualVal);

		} else {

			this.reportFailure("Expected Value:" + expectedValue + " is not matched with Actual value:" + actualVal);
		}

	}

	public void webVerifyEdit(String path, String expectedValue) throws Exception {

		// String actualVal = web.textBox(path).getAttribute("text");
		String actualVal = web.textBox(path).getAttribute("value");
		
		if(actualVal == null){
			actualVal = "";
		}
		
		if (compareStrings(expectedValue,actualVal)) {

			info("Expected Value \"" + expectedValue + "\" matches with Actual value \"" + actualVal+"\"");

		} else {

			this.reportFailure("Expected Value \"" + expectedValue + "\" is not mathed with Actual value \"" + actualVal+"\"");
		}

	}
	
	public boolean webVerifyEditBasedOnLabel(String labelWithIndex,String expectedValue) throws Exception
	{
        boolean status=false;
        String value=wEBLABELLIB.webGetEditTextBasedOnLabel(labelWithIndex);
        //System.out.println("value :"+value);
        
        status=compareStrings(expectedValue, value);
        
        if(status){
			info("Actual TextField ("+labelWithIndex+") value :\""+value+"\" is matched with Expected TextField value :\""+expectedValue+"\"");
		}else{
			warn("Actual TextField ("+labelWithIndex+") value :\""+value+"\" is not matched with Expected TextField value :\""+expectedValue+"\"");
		}
        return status;
	}

	public void webVerifyTextArea(String path, String expectedValue) throws Exception {

		// String actualVal = web.textArea(path).getAttribute("text");
		String actualVal = web.textArea(path).getAttribute("value");

		if (compareStrings(
			expectedValue,
			actualVal)) {

			info("Expected Value: " + expectedValue + "  matches with actual value:" + actualVal);

		} else {

			this.reportFailure("Expected Value: " + expectedValue + "  where as Actual value is:" + actualVal);
		}

	}

	public void webVerifyCheckBox(String path, String expectedState) throws Exception {

		String actualVal = web.checkBox(path).getAttribute("checked");

		boolean bActualState = Boolean.parseBoolean(actualVal);
		boolean bExpectedState = Boolean.parseBoolean(expectedState);

		if (bActualState == bExpectedState) {

			info("Expected State:" + expectedState + "matches with actual State:" + actualVal);

		} else {

			this.reportFailure("Expected State:" + expectedState + "where as Actual State is:" + actualVal);
		}

	}

	public void webVerifyRadioButton(String path, String expectedState) throws Exception {

		boolean bActualState = web.radioButton(path).isSelected();
		;
		boolean bExpectedState = Boolean.parseBoolean(expectedState);

		if (bActualState == bExpectedState) {

			info("Expected State:" + expectedState + "matches with actual State:" + bActualState);

		} else {

			this.reportFailure("Expected State:" + expectedState + "where as Actual State is:" + bActualState);
		}

	}

	public void webSelectDate(String xpath, String date) throws Exception {

		webSetText(xpath, date);
		//web.textBox(xpath).setText(date);
	}

	public String formTableSearch(HashMap<String, String> searchCol, int visiblelines) throws Exception {

		String attrVal[] = this.getKeysFromHashMap(searchCol);
		String colVals[] = this.getValuesFromHashMap(searchCol);

		// Removing the last character i.e digit from the Columns
		for (int i = 0; i < attrVal.length; i++) {

			attrVal[i] = attrVal[i].substring(0,(attrVal[i].length() - 1));
		}
		
		//Set focus to first record in the table
		String xPath ="//forms:textField[(@name='" + attrVal[0] + "0')]";
		//System.out.println("xPath :"+xPath);
		forms.textField("//forms:textField[(@name='" + attrVal[0] + "0')]").setFocus();
		
		boolean menuEnalbed = forms.window("//forms:window[(@name='*')]").isMenuEnabled("View|Record|First");
		
		if(menuEnalbed){
			
			forms.window("//forms:window[(@name='*')]").selectMenu("View|Record|First");
			
			think(5);
			
			forms.textField("//forms:textField[(@name='" + attrVal[0] + "0')]").setFocus();
		}
		
		//Traversing through table
		int iCount = 0, iRowNum = -1;
		boolean bFound = true;
		
		for (int iCounter = 0; !isBlankRow(iCount,attrVal) ; iCounter++) {
			
			bFound = true;

			iCount = (iCounter >= visiblelines) ? (visiblelines - 1) : iCounter;
			
			xPath ="//forms:textField[(@name='" + attrVal[0] + iCount + "')]";
			
			String firstCell = forms.textField(xPath).getText();
			this.sop("Cell xPath :"+xPath+"   --  Cell Data:"+firstCell);
			
			if (firstCell.trim().equalsIgnoreCase(colVals[0].trim())) {
				
				for (int j = 1; j < colVals.length; j++) {

					String xPath_OtherCol ="//forms:textField[(@name='" + attrVal[j] + iCount + "')]";
					String otherCell = forms.textField(xPath_OtherCol).getText();
					this.sop("Cell xPath :"+xPath_OtherCol+"   --  Cell Data:"+otherCell);
					
					if (!((colVals[j]).equalsIgnoreCase(otherCell))) {
						bFound = false;
						break;
					}
				}

				if (bFound) {

					// For Bug 566
					// iRowNum =(visiblelines>=iCounter)? iCounter :
					// visiblelines ;
					iRowNum = (visiblelines >= iCounter) ? iCounter : visiblelines - 1;
					this.sop("iRowNum is:" + iRowNum);
					return iRowNum + "";
				}

			}

			if (iCounter >= visiblelines) {
				// ft.typeKeys("<DOWN>");
				// For Bug 566
				this.sop("Current Row Number:"+iCounter+" is greater than or equal to max visible lines :"+visiblelines);
				forms.textField("//forms:textField[(@name='" + attrVal[0] + iCount + "')]").invokeSoftKey("DOWN");
			}

		}

		if (iRowNum == -1) {
			this.sop("Specified record doesn't exist");
		} else {
			this.sop("Specified record exists in :" + iRowNum);
		}

		return iRowNum + "";
	}

	private  boolean  isBlankRow(int rowno, String attrVal[] ) throws AbstractScriptException
	{  
		boolean blankrow=true;
		for (int colCount=0; colCount <attrVal.length; colCount++ )
		{
			if(!("".equals(forms.textField("//forms:textField[(@name='"+attrVal[colCount]+rowno+"')]").getText())))
					return  false;
		}
		
		return blankrow;
	}
	
	public boolean verifySpreadCell(String spreadTableAttribute, String rowNum, String colNum, String expectedValToVerify) throws Exception {
		boolean ret = false;
		
		String actual = forms.spreadTable("//forms:spreadTable[(@name='" + spreadTableAttribute + "')]").getCell(Integer.parseInt(rowNum),
			Integer.parseInt(colNum));
		
		if (actual.equalsIgnoreCase(expectedValToVerify)) {
			ret = true;
			info("Actual Value:" + actual + ", Expected value: " + expectedValToVerify);
		} else {
			ret = false;
			reportFailure("Actual Value:" + actual + ", Expected value: " + expectedValToVerify);
		}
		return ret;
	}

	
	public String formSpreadTableSearch(HashMap<String, String> searchCol, String attrValue) throws Exception {
		
		String sRowIndex = "";
		
		System.out.println("*********  formSpreadTableSearch ******************");
		
		int colCount, rowCount;
		int valFound = -1;
		
		// Column Numbers
		String colNumbers[] = this.getKeysFromHashMap(searchCol);
		
		// Column Values
		String colVals[] = this.getValuesFromHashMap(searchCol);
		
		// Get Column Count
		colCount = forms.spreadTable("//forms:spreadTable[(@name='" + attrValue + "')]").getColumnCount();
		
		// Row Count
		rowCount = forms.spreadTable("//forms:spreadTable[(@name='" + attrValue + "')]").getRowCount();

		this.sop("Spread Table Column Count :"+colCount);
		this.sop("Spread Table Row Count :"+rowCount);
		
		// Column Length
		int colLen = colVals.length - 1;

		this.sop("Expected Column To Verify :"+Arrays.asList(colNumbers).toString());
		this.sop("Expected Column Values To Verify :"+Arrays.asList(colVals).toString());
		
		int rowIndex = 1;
		boolean bFound = false;
		
		
		for (; rowIndex <= rowCount; rowIndex++) {
			
			this.sop("Start of Row :"+rowIndex);
			bFound = false;
			for(int reqColIndex = 0; reqColIndex < colNumbers.length; reqColIndex++){
				
				int colIndex 		= Integer.parseInt(colNumbers[reqColIndex]) + 1;
				String expCellValue = colVals[reqColIndex];
				
				String actCellValue = forms.spreadTable("//forms:spreadTable[(@name='" + attrValue + "')]").getCell(rowIndex,colIndex);
				
				//this.sop("actCellValue :"+actCellValue);
				if(actCellValue.equalsIgnoreCase(expCellValue)){
					bFound = true;
				}else{
					this.sop("expCellValue :"+expCellValue+"  does not match with Actual Cell value :"+actCellValue);
					bFound = false;
					break;
				}
			}
			
			if(bFound){
				System.out.println("Specified Data exists in the row :"+rowIndex);
				break;
			}
		}	
			
		/*this.sop("rowIndex :"+rowIndex);
		this.sop("rowCount :"+rowCount);
		this.sop("bFound :"+bFound);*/
		
		if (((rowIndex-1) == rowCount )&& (bFound == false)) {
			System.out.println("Specified record doesn't exist");
			rowIndex = -1;
		} 

		sRowIndex = rowIndex+"";
	
		return sRowIndex.trim();
	}

	public void formsConfirmDialog() throws Exception {

		if (forms.alertDialog("//forms:alertDialog").exists()) {
			forms.alertDialog("//forms:alertDialog").clickYes();
		}

	}

	public void formsChoiceWindow() throws Exception {

		while (forms.choiceBox("//forms:choiceBox").exists()) {
			forms.choiceBox(
				"//forms:choiceBox").clickButton(
				"OK");
		}

	}

	public boolean formVerifyTextArea(String path, String expectedValue) throws Exception {

		String Text = "";
		boolean verify = false;
		String xPath = "";

		if (path.startsWith("//forms")) {
			xPath = path;
		} else {
			xPath = "//forms:textField[(@name='" + path + "')]";
		}

		if (forms.textField(
			xPath).exists()) {
			Text = forms.textField(
				xPath).getText();

			this.sop("Actual Text :" + Text);

			if (compareStrings(
				expectedValue,
				Text)) {
				info("The text :::>"+Text+" matches with the expected value :::>"+expectedValue);
				verify = true;
			} else {
				reportFailure("The text :::>"+Text+" doesn't match with the expected value :::>" + expectedValue);
				verify = false;
			}
		}else{
			reportFailure("Unable to find the TextArea with the XPath :"+xPath);
		}

		return verify;

	}

	public boolean formVerifyRadioButton(String path, String radioButtonStatus) throws Exception {

		boolean actualStatus = false;
		boolean expectedStatus = false;
		String xPath = "";

		if (path.startsWith("//forms")) {
			xPath = path;
		} else {
			xPath = "//forms:radioButton[(@name='" + path + "')]";
		}

		if (radioButtonStatus.equalsIgnoreCase("yes") || radioButtonStatus.equalsIgnoreCase("true")) {
			expectedStatus = true;
		} else if (radioButtonStatus.equalsIgnoreCase("no") || radioButtonStatus.equalsIgnoreCase("false")) {
			expectedStatus = false;
		}

		if (forms.radioButton(
			xPath).exists()) {
			actualStatus = forms.radioButton(
				xPath).isSelected();

			if (expectedStatus == actualStatus) {
				info("Verification successful");
				actualStatus = true;

			} else {
				reportFailure("Verification not successful");
				actualStatus = false;

			}
		}else{
			reportFailure("Unable to find the Radiobutton with the XPath :"+xPath);
		}
		return actualStatus;
	}

	public boolean formVerifyCheckBox(String path, String checkBoxstatus) throws Exception {

		boolean actualStatus = false;
		boolean expectedStatus = false;
		String xPath = "";

		if (path.startsWith("//forms")) {
			xPath = path;
		} else {
			xPath = "//forms:checkBox[(@name='" + path + "')]";
		}

		if (checkBoxstatus.equalsIgnoreCase("yes") || checkBoxstatus.equalsIgnoreCase("true")) {
			expectedStatus = true;
		} else if (checkBoxstatus.equalsIgnoreCase("no") || checkBoxstatus.equalsIgnoreCase("false")) {
			expectedStatus = false;
		}

		if (forms.checkBox(xPath).exists()) {
			actualStatus = forms.checkBox(xPath).isSelected();

			if (expectedStatus == actualStatus) {
				info("Checkbox Verification successfull(Expected Status : "+expectedStatus+" matches with Actual Status :"+actualStatus+")");
				actualStatus = true;
			} else {
				reportFailure("Checkbox Verification Failed(Expected Status : "+expectedStatus+" is not matched with Actual Status :"+actualStatus+")");
				actualStatus = false;
			}
		}else{
			reportFailure("Unable to find the Checkbox with the XPath :"+xPath);
		}
		return actualStatus;
	}

	public boolean formVerifyListBoxValues(String path, String expectedValue) throws Exception {

		String[] expectedValues = expectedValue.split(";");
		boolean verified = true;

		List<String> expectedListValues = Arrays.asList(expectedValues);

		// String[]
		// actualValues=forms.list("//forms:list[(@name='"+path+"')]").getAllItems();
		String[] actualValues = forms.list(path).getAllItems();
		info("List Values :"+Arrays.asList(actualValues).toString());
		if (actualValues != null) {

			List<String> actualListValues = Arrays.asList(actualValues);
			for (int i = 0; i < expectedListValues.size(); i++) {
				this.sop(expectedListValues.get(i));
				if (!actualListValues.contains(expectedListValues.get(i))) {
					verified = false;
					break;
				}

			}
		} else {
			info("Actual values are returning null");
			verified = false;
		}

		if (verified) {
			verified = true;
			info("Expected values are contained in Actual values");
			// this.sop("Expected values are contained in Actual values");
		} else {
			reportFailure("Expected values doesn't contain in Actual values");
			// this.sop("Expected values doesn't contain in Actual values");
		}

		return verified;

	}
	public boolean formVerifyListBox(String path, String expectedValue) throws Exception {

		String[] expectedValues = expectedValue.split(";");
		boolean verified = true;

		List<String> expectedListValues = Arrays.asList(expectedValues);

		// String[]
		// actualValues=forms.list("//forms:list[(@name='"+path+"')]").getAllItems();
		String[] actualValues = forms.list(path).getAllItems();
		//info("List Values :"+Arrays.asList(actualValues).toString());
		if (actualValues != null) {

			List<String> actualListValues = Arrays.asList(actualValues);
			for (int i = 0; i < expectedListValues.size(); i++) {
				this.sop(expectedListValues.get(i));
				//if (!actualListValues.contains(expectedListValues.get(i))) {
				 if (!forms.list(path).isItemSelected(expectedListValues.get(i))) {
					verified = false;
					break;
				}

			}
		} else {
			info("Actual values are returning null");
			verified = false;
		}

		if (verified) {
			verified = true;
			info("Expected value(s) :"+expectedValue+" is | are selected");
			// this.sop("Expected values are contained in Actual values");
		} else {
			reportFailure("Expected value(s) :"+expectedValue+" is | are not selected");
			// this.sop("Expected values doesn't contain in Actual values");
		}

		return verified;

	}
	public boolean formVerifyStatus(String message) throws Exception {

		Thread.sleep(10000);
		
		String statusMessage = forms.getStatusBarMessage();
		boolean verified = false;
		String msg[] = message.split(";;");

		if (statusMessage.matches(msg[0]) || (msg.length > 1 && statusMessage.matches(msg[1]))) {
			info("The actual message >::"+statusMessage+" matches with the expected meaage ::>"+message+"<::");
			verified = true;
		} else {
			reportFailure("The actual message: >::"+statusMessage+"<:: doesn't matches with the expected meaage ::>"+message+"<::");
			verified = false;
		}
		return verified;
	}

	public void switchResponsibility(String valueToSelect) throws Exception {

		if (forms.window(
			"//forms:window[(@name='NAVIGATOR')]").exists()) {
			forms.window(
				"//forms:window[(@name='NAVIGATOR')]").selectMenu(
				"File|Switch Responsibility...");
			{
				think(2);
			}

			/*
			 * forms.listOfValues("//forms:listOfValues").find(valueToSelect);
			 * if(forms.listOfValues("//forms:listOfValues").exists()) {
			 * forms.listOfValues("//forms:listOfValues").choose(1); }
			 */
		}
		formSelectLOV(
			"",
			valueToSelect);

	}

	public String removeSpecialCharacters(String input) {
		String output = "";

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(' || c == ')' || c == '-' || c == '!' || c == '#' || c == '@' || c == '#' || c == '$'
					|| c == '%' || c == '^' || c == '*' || c == '\n') {
				output += " ";
			} else {
				output += c;
			}
		}

		return output;
	}

	

	private String extractButtonValue(String value) throws Exception {

		//this.sop("extractButtonValue :"+value+"****");
		/*
		if(value.equalsIgnoreCase("<<") || value.equalsIgnoreCase("<")||value.equalsIgnoreCase(">") || value.equalsIgnoreCase(">>")){
			this.sop("not replacing");
		}else{
			this.sop("replacing");
			
			*/
			if(value.equals("<") || value.equals(">") || value.equals("<<") || value.equals(">>"))
				return value;
			while (value.contains("<"))
			{
				//this.sop("Entered");
				int StartIndex = value.indexOf('<');
				int closeIndex = value.indexOf('>');
				value = value.replace(value.substring(StartIndex,closeIndex + 1),"");

			}
		//	this.sop("button Value :"+value);
			
		//}
		

		return value;
	}

	public void waitForText(String text) throws Exception {

		waitForText(
			text,
			"span");

	}

	public void waitForText(String text, String compType) throws Exception {

		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(
			windowPath).getAttribute(
			"index"));

		String docPath = windowPath + "/web:document[@index='" + currentWindowIndex + "']";

		/* Get Text Path */
		String textPath = docPath + "/web:" + compType + "[@text='" + text + "']";

		/* Wait for text to present */
		web.element(
			textPath).waitFor(
			SYNCTIME);
	}

	public void verifyAndClosePopup(String msg, String action) throws Exception{
		
		String path =  "/web:dialog_confirm[@text='*' and @index='0']"; 
		String dialogMsg = null;
		
		if(web.exists(path)){
			
			dialogMsg = web.confirmDialog(path).getAttribute("text");
			
			if(action!=null){
				if((action.toLowerCase().equals("yes")||action.toLowerCase().equals("ok"))){
					web.confirmDialog(path).clickOk();
				}
				else{	
					web.confirmDialog(path).clickCancel();
				}
			}
			else{
				web.confirmDialog(path).clickOk();
			}
		}else{
			
			path = "/web:dialog_alert[@text='*' and @index='0']";
			dialogMsg = web.alertDialog(path).getAttribute("text");
			
			web.alertDialog(path).clickOk();
		}
		
		if(dialogMsg.contains(msg)){
			info("GENLIB: verifyPopupMsg: Expected message'"+msg+"' is present in dialog box text");
		}
		else{
			reportFailure("GENLIB: verifyPopupMsg: Expected message'"+msg+"' isn't present in '"+dialogMsg+"' dialog box text");
		}
			
	}


	private boolean verifyText(String text, String[] compTypes) throws Exception {

		return verifyText(text,	"0",compTypes,true);

	}
	
	private boolean verifyText(String text, String docIndex, String[] compTypes) throws Exception {

		return verifyText(text,	docIndex, compTypes,true);

	}

	private boolean verifyText(String text, String[] compTypes, boolean checkForElementExists) throws Exception {
		
		return verifyText(text, "0",compTypes, checkForElementExists);
	}
	
	private boolean verifyText(String text, String docIndex, String[] compTypes, boolean checkForElementExists) throws Exception {

		boolean textExists = false;
		boolean elementExists = false;

		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute(
			"index"));

		String docPath = windowPath + "/web:document[@index='"+docIndex+"']";
		
		

		for (int index = 0; index < compTypes.length; index++) {

			/* Get Text Path */
			String xPath = docPath + "/web:" + compTypes[index] + "[@text='" + text + "']";
			
			//info("xPath:"+xPath);
			
			if (web.element(
				xPath).exists()) {
				elementExists = true;
				break;
			}
		}

		if (checkForElementExists) {

			if (elementExists) {
				textExists = true;
				this.info("Verified the text :\"" + text + "\" successfully as Expected ");
			} else {
				textExists = false;
				this.reportFailure("Unable to Verify the text :\"" + text + "\" ,Which is Expected");
			}

		} else {

			if (elementExists) {
				textExists = false;
				this.reportFailure("Unable to Verify the Non Availability of text :\"" + text
						+ "\". The Specified text is present  ,Which should not be present");
			} else {
				textExists = true;
				this.info("Verified the Non Availability of text :\"" + text + "\" successfully as Expected ");
			}
		}

		return textExists;
	}

	private boolean verifyData(String[] actualItems, String[] expectedItems) throws Exception {

		return verifyData(actualItems,expectedItems,true,true);
	}

	private boolean verifyData(String[] actualItems, String[] expectedItems, boolean partialMatch,
			boolean checkForItemAvailability) throws Exception {

		boolean itemsCheck = true;
		List<String> itemsNotVerified = new ArrayList<String>();
		List<String> itemsVerified = new ArrayList<String>();

		/* Convert Data from Arrays to Lists */
		List<String> lActualItems = Arrays.asList(actualItems);
		List<String> lExpectedItems = Arrays.asList(expectedItems);

		this.sop("Actual Items		: " + lActualItems.toString());
		this.sop("Expected Items	: " + lExpectedItems.toString());

		/* Verificaton of Actual Items with Expected Items */
		if (partialMatch) {

			for (int index = 0; index < lExpectedItems.size(); index++) {

				boolean itemAvailable = false;

				/* Verification of "expected item" in "Actual Items" */
				for (int actindex = 0; actindex < lActualItems.size(); actindex++) {

					if (compareStrings(
						lExpectedItems.get(index),
						lActualItems.get(actindex))) {
						itemAvailable = true;
						break;
					}
				}

				if (itemAvailable) {
					itemsVerified.add(expectedItems[index]);
				} else {
					itemsNotVerified.add(expectedItems[index]);
				}
			}

		} else {

			for (int index = 0; index < expectedItems.length; index++) {

				if (lActualItems.contains(expectedItems[index])) {
					itemsVerified.add(expectedItems[index]);
				} else {
					itemsNotVerified.add(expectedItems[index]);
				}
			}
		}

		/*
		 * Display corresponding message based on the verificaion No of Elements
		 * in itemsNotInTheList > 0, Then Display the Warning Message
		 * (failIfNotExist = true) or info Message (failIfNotExist = false)
		 */
		if (checkForItemAvailability) {

			if (itemsNotVerified.size() > 0) {
				this.warn("Items not Verified : " + itemsNotVerified.toString());
				itemsCheck = false;
			} else {
				this.info("Verified the Items :" + itemsVerified.toString() + " Successfully");
				itemsCheck = true;
			}
		} else {

			if (itemsVerified.size() > 0) {
				this.warn("Items present in the List : " + itemsVerified.toString());
				itemsCheck = false;
			} else {
				this.info("Verified the Non Availability of Items :" + itemsNotVerified.toString() + " Successfully");
				itemsCheck = true;
			}
		}
		return itemsCheck;
	}
	public String getLatestWindowXPath() throws Exception
	{
		int i=0;
		String title	=	"";
		String xpath	=	"";
		for( i=0;i<10;i++)
		{
			if(!web.exists("/web:window[@index='"+i+"']"))
			{
				//System.out.println("Broken At : "+ i);
				break;
			}
		}
		if(i<10)
		{
			if(i!=0)
			{
				i=i-1;
				String tempTitle 	=	web.window("/web:window[@index='" + i + "']").getAttribute("title");
				if(tempTitle != null && "Oracle Applications Home Page".equals(tempTitle))
				{
					i=i-1;
				}
			}
			title = web.window("/web:window[@index='" + i + "']").getAttribute("title");
			String	tempIndex	=	web.window("/web:window[@index='" + i + "']").getAttribute("index");
			xpath	=	"/web:window[ @index='"+tempIndex+"' or @title='" + title + "']";
		}
		return xpath;
	}
	public String getCurrentWindowXPath() throws Exception {

		//Thread.sleep(20000);
		
		String index = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();

		String xPath = "/web:window[@index='" + index + "' or @title='" + title + "']";

		return xPath;
	}

	public boolean compareStrings(String expectedString, String actualString) {

		boolean stringMatched = false;

		Pattern pattern = Pattern.compile(expectedString);
		Matcher matcher = pattern.matcher(actualString);

		//this.sop("expectedString :" + expectedString);
		//this.sop("actualString :" + actualString);
		if (matcher.matches()) {
			stringMatched = true;
		}

		return stringMatched;
	}

	public String[] getKeysFromHashMap(HashMap<String, String> searchColumns) {

		String colVal[] = (String[]) searchColumns.keySet().toArray(
			new String[searchColumns.keySet().size()]);

		return colVal;

	}

	public String[] getValuesFromHashMap(HashMap<String, String> searchColumns) {
		return (String[]) searchColumns.values().toArray(
			new String[searchColumns.keySet().size()]);

	}

	public String getRandomString() {

		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 4; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}

		String output = sb.toString();

		return output;
	}

	public int getRandomNumber(String maxRange) {

		Random randomGenerator = new Random();

		int number = randomGenerator.nextInt(Integer.parseInt(maxRange));

		return number;
	}

	/**
	 * Gives a Random Number within a Specified Ranges
	 * 
	 * @param startRange
	 * @param endRange
	 * @return
	 */
	public int getRandomNumber(int startRange, int endRange) {

		Random random = new Random();

		if (startRange > endRange) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}

		// get the range, casting to long to avoid overflow problems
		long range = (long) endRange - (long) startRange + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * random.nextDouble());

		int randomNumber = (int) (fraction + startRange);

		return randomNumber;
	}

	public String getSysDateTime(String dateFormat, int noOfDays) throws Exception {

		return getSysDate(
			dateFormat,
			noOfDays);
	}

	public String getSysDate(String dateFormat, int noOfDays) throws Exception {

		String dateVal = "";
		if (noOfDays > 0) {

			dateVal = addSysDate(
				dateFormat,
				"" + noOfDays);

		} else if (noOfDays == 0) {

			SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);
			Calendar cal = Calendar.getInstance();
			dateVal = dateFormater.format(cal.getTime());
		}

		else {

			dateVal = subSysDate(
				dateFormat,
				"" + noOfDays);
		}
		return dateVal;
	}

	public String addSysDate(String dateFormatType, String dateValue) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatType);
		Calendar cal = Calendar.getInstance();

		if (dateValue != null) {
			cal.add(
				Calendar.DATE,
				Integer.parseInt(dateValue));
		}

		String DateVal = dateFormat.format(cal.getTime());

		return DateVal;
	}

	public String subSysDate(String dateFormatType, String dateValue) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatType);
		Calendar cal = Calendar.getInstance();

		if (dateValue != null) {
			cal.add(
				Calendar.DATE,
				-(Integer.parseInt(dateValue.substring(1))));
		}

		String DateVal = dateFormat.format(cal.getTime());

		return DateVal;
	}

	private String getCompXPath(String component, HashMap<String, String> tagAtttirbutes) {

		String xPath = "";

		/* Get Attributes */
		Object[] attrObjects = tagAtttirbutes.keySet().toArray();

		/* Get Attribute Values */
		Object[] attrValuesObjects = tagAtttirbutes.values().toArray();

		String[] attrNames = Arrays.asList(
			attrObjects).toArray(
			new String[attrObjects.length]);
		String[] attrValues = Arrays.asList(
			attrValuesObjects).toArray(
			new String[attrValuesObjects.length]);

		for (int tagIndex = 0; tagIndex < attrNames.length; tagIndex++) {

			if (tagIndex == 0) {
				xPath = xPath + "@" + attrNames[tagIndex] + "='" + attrValues[tagIndex] + "'";
			}

			if (tagIndex > 0) {
				xPath = xPath + " and @" + attrNames[tagIndex] + "='" + attrValues[tagIndex] + "'";
			}
		}

		xPath = xPath.trim();

		/* Change Array values to List Values */
		List<String> lAttrNames = Arrays.asList(attrNames);
		List<String> lAttrValues = Arrays.asList(attrValues);

		if (component.equalsIgnoreCase("input")) {

			String type = lAttrValues.get(lAttrNames.indexOf("type"));

			if (type.equalsIgnoreCase("text")) {

				xPath = "/web:input_text[" + xPath + "]";

			} else if (type.equalsIgnoreCase("checkbox")) {

				xPath = "/web:input_checkbox[" + xPath + "]";

			} else if (type.equalsIgnoreCase("radio")) {

				xPath = "/web:input_radio[" + xPath + "]";

			}

		} else if (component.equalsIgnoreCase("a")) {

			xPath = "/web:a[" + xPath + "]";

		} else if (component.equalsIgnoreCase("select")) {

			xPath = "/web:select[" + xPath + "]";

		} else if (component.equalsIgnoreCase("img")) {

			xPath = "/web:img[" + xPath + "]";

		} else if (component.equalsIgnoreCase("span")) {

			xPath = "/web:span[" + xPath + "]";
		}

		// this.sop("xPath :"+xPath);
		return xPath.trim();
	}

	private HashMap<String, String> getTagAttributes(DOMElement element) throws Exception {

		return getTagAttributes(
			element,
			null);
	}

	private HashMap<String, String> getTagAttributes(DOMElement element, String[] expAttributes) throws Exception {

		String[] defAttributes = { "id", "name", "value", "text", "innertext", "type", "summary", "index", "alt",
				"title", "src" };

		String[] attributes = null;

		if (expAttributes == null) {
			attributes = defAttributes;
		} else {
			attributes = expAttributes;
		}

		HashMap<String, String> tagAttributes = new HashMap<String, String>();

		for (int attrIndex = 0; attrIndex < attributes.length; attrIndex++) {

			String attrVal = element.getAttribute(attributes[attrIndex]);

			if (attrVal != null) {

				if (attributes[attrIndex] == "type" && attrVal == "hidden") {
					return null;
				}

				if (attrVal.isEmpty()) {
					tagAttributes.put(
						attributes[attrIndex],
						"");
				} else {
					tagAttributes.put(
						attributes[attrIndex],
						attrVal);
				}
			}
		}

		/*
		 * if the component doesn't have specified attributes, then return null
		 * for that component
		 */
		if (expAttributes != null) {

			if (tagAttributes.keySet().size() > expAttributes.length) {
				tagAttributes = null;
			}
		}

		return tagAttributes;
	}

	public boolean webVerifyText(String expectedText, String beforeWithIndex, String afterWithIndex) throws Exception {
		
		return webVerifyText(expectedText, "0", beforeWithIndex, afterWithIndex);
	}
	
	
	public boolean webVerifyText(String expectedText, String docIndex, String beforeWithIndex, String afterWithIndex) throws Exception {

		String beforeIndex = "0";
		String afterIndex = "0";

		String before[] = beforeWithIndex.trim().split(";");
		String after[] = afterWithIndex.trim().split(";");

		if (before.length > 1) {
			beforeIndex = before[1];
		}

		if (after.length > 1) {
			afterIndex = after[1];
		}

		if ("".equals(beforeWithIndex)) {
			before[0] = "1";
		}

		if ("".equals(afterWithIndex)) {
			after[0] = "1";
		}

		if ("".equals(beforeWithIndex) && "".equals(afterWithIndex)) {
			before[0] = "-1";
			after[0] = "-1";
		}

		return webVerifyText(expectedText,	docIndex, before[0],	after[0],beforeIndex,	afterIndex);
	}

	public boolean webVerifyText(String expectedText, String beforeText, String afterText, String beforeIndex,
			String afterIndex) throws Exception {
		
		return webVerifyText(expectedText, "0", beforeText, afterText, beforeIndex, afterIndex);
	}
	
	public boolean webVerifyText(String expectedText, String docIndex, String beforeText, String afterText, String beforeIndex,
			String afterIndex) throws Exception {

		boolean textExists = false;

		boolean beforeTextAsInt = beforeText.matches("\\d|-\\d");
		this.sop("beforeTextAsInt :" + beforeTextAsInt);

		boolean afterTextAsInt = afterText.matches("\\d|-\\d");
		this.sop("afterTextAsInt :" + afterTextAsInt);

		if (beforeTextAsInt && !afterTextAsInt) {

			this.sop("Before Text is Integer && After Text is String ");

			// Get the Before Text based on number of words specified in
			// beforeText field
			textExists = webVerifyBeforeText(expectedText,	docIndex, afterText,	beforeText,	afterIndex);

		} else if (!beforeTextAsInt && afterTextAsInt) {

			this.sop("Before Text is String && After Text is Integer");

			// Get the After Text based on number of words specified in
			// beforeText field
			textExists = webVerifyAfterText(expectedText, docIndex, beforeText,afterText,beforeIndex);

		} else {

			this.sop("Before Text is String && After Text is String");

			textExists = webVerifyBetweenText(expectedText,	docIndex, beforeText,	afterText,	beforeIndex,afterIndex);
		}

		return textExists;
	}

	public boolean webVerifyBetweenText(String expectedText, String beforeText, String afterText, String beforeIndex,
			String afterIndex) throws Exception {
		
		return webVerifyBetweenText(expectedText, "0", beforeText, afterText, beforeIndex, afterIndex) ;
	}
	
	public boolean webVerifyBetweenText(String expectedText, String docIndex, String beforeText, String afterText, String beforeIndex,
			String afterIndex) throws Exception {

		String actualText = webGetBetweenText(docIndex, beforeText,afterText,beforeIndex,	afterIndex);

		boolean textExists = compareStrings(expectedText,	actualText);

		if (textExists) {
			this.info("Verified the Actual Text \"" + actualText + "\" with the Expected Text \"" + expectedText
					+ "\" successfully.");

		} else {
			this.reportFailure("Unable to Verify the Actual Text \"" + actualText + "\" with the Expected Text \""
					+ expectedText + "\"");
		}

		return textExists;
	}

	
	public boolean webVerifyBeforeText(String expectedText, String afterText, String sWordsRequired, String sIndex)	throws Exception {
		
		return webVerifyBeforeText(expectedText, "0", afterText, sWordsRequired, sIndex);
	}
	
	public boolean webVerifyBeforeText(String expectedText, String docIndex, String afterText, String sWordsRequired, String sIndex)
			throws Exception {

		String expecWordCount = getWordCount(expectedText);
		String actualText = "";

		if (Integer.parseInt(expecWordCount) > Integer.parseInt(sWordsRequired))
			actualText = webGetBeforeText(afterText,expecWordCount,	sIndex);
		else
			actualText = webGetBeforeText(docIndex, afterText,sWordsRequired,sIndex);

		boolean textExists = compareStrings(expectedText,actualText);

		if (textExists) {
			this.info("Verified the Actual Text \"" + actualText + "\" with the Expected Text \"" + expectedText
					+ "\" successfully.");

		} else {
			// this.reportFailure("Unable to Verify the Actual Text \""+actualText+"\" with the Expected Text \""+expectedText+"\"");
			info("Not found in body content  , checking  in td  tag  elements");
			actualText = webGetTextInTable(docIndex,	afterText,sWordsRequired,	sIndex,	"before");
			textExists = compareStrings(expectedText,actualText);
			
			if (textExists)
				this.info("Verified the Actual Text \"" + actualText + "\" with the Expected Text \"" + expectedText
						+ "\" successfully.");
			else
				this.reportFailure("Unable to Verify the Actual Text \"" + actualText + "\" with the Expected Text \""
						+ expectedText + "\"");
		}

		return textExists;

	}

	public boolean webVerifyAfterText(String expectedText, String beforeText, String sWordsRequired, String sIndex) throws Exception {
		
		return webVerifyAfterText(expectedText, "0", beforeText, sWordsRequired, sIndex);
	}
	
	public boolean webVerifyAfterText(String expectedText, String docIndex, String beforeText, String sWordsRequired, String sIndex)
			throws Exception {

		String expecWordCount = getWordCount(expectedText);
		String actualText = "";

		//this.sop("expecWordCount :"+expecWordCount);
		//this.sop("sWordsRequired :"+sWordsRequired);
		
		if (Integer.parseInt(expecWordCount) > Integer.parseInt(sWordsRequired))
			actualText = webGetAfterText(docIndex, beforeText,expecWordCount,	sIndex);
		else
			actualText = webGetAfterText(docIndex, beforeText,sWordsRequired,	sIndex);

		
		this.sop("actualText :"+actualText+"		expectedText :"+expectedText);
		this.sop("expectedText :"+expectedText);
		
		boolean textExists = compareStrings(expectedText,actualText);

		//this.sop("textExists :"+textExists);
		
		if (textExists) {
			this.info("Verified the Actual Text \"" + actualText + "\" with the Expected Text \"" + expectedText
					+ "\" successfully.");

		} else {
			
			this.sop("Unable to verify the Actual Text \"" + actualText + "\" with the Expected Text \"" + expectedText
				+ "\" successfully and hence trying to verify with different approach.");
			
			// this.reportFailure("Unable to Verify the Actual Text \""+actualText+"\" with the Expected Text \""+expectedText+"\"");
			this.sop("Not found in body content  , checking  in td  tag elements");
			
			actualText = webGetTextInTable(docIndex, beforeText,sWordsRequired,	sIndex,	"after");
			this.sop("Verification of text in td Tags  :"+actualText);
			
			textExists = compareStrings(expectedText,actualText);
			
			this.sop("Actual Data getting through td tag elements :" + actualText);
			if (textExists)
				this.info("Verified the Actual Text \"" + actualText + "\" with the Expected Text \"" + expectedText
						+ "\" successfully.");
			else
				this.reportFailure("Unable to Verify the Actual Text \"" + actualText + "\" with the Expected Text \""
						+ expectedText + "\"");

		}

		return textExists;

	}

	public String getWordCount(String text) {
		return text.split(" ").length + "";
	}

	public boolean webVerifyTextWithBefore(String before, String textToVerify) throws Exception {

		this.sop("********************* Start of Web Verify Text with Before ****************");
		
		String expecWordCount = getWordCount(textToVerify);

		this.sop("expecWordCount :" + expecWordCount);

		boolean textExists = webVerifyText(textToVerify, before, expecWordCount);

		this.sop("********************* End of Web Verify Text with Before ****************");
		
		return textExists;

	}

	public boolean webVerifyTextWithBefore(String before, String textToVerify, Integer index) throws Exception {
		
		String expecWordCount = getWordCount(textToVerify);

		this.sop("expecWordCount :" + expecWordCount);

		boolean textExists = webVerifyText(textToVerify, before+";"+index, expecWordCount);

		return textExists;
	}
	
	/*public boolean webVerifyTextWithBefore(String before, String textToVerify, Integer index) throws Exception {

		// Get window Path
		String windowPath = getCurrentWindowXPath();

		// Get Current Window Index
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		// Document Path
		String docPath = windowPath + "/web:document[@index='0']";

		// Content within the page
		String content = web.document(docPath).getElementsByTagName("body").get(0).getAttribute("text");
		// this.sop("webVerifyTextWithBefore:"+content);
		// Finding number of times text is present & storing their positions in
		// the list
		List<Integer> noOfTimesBeforeTextisPresent = new ArrayList<Integer>();
		int beforeindex = 0, startIndex = 0;
		while ((beforeindex = content.indexOf(before,beforeindex)) != -1) {
			noOfTimesBeforeTextisPresent.add(beforeindex);
			beforeindex += before.length() - 1;

		}

		if (noOfTimesBeforeTextisPresent.size() == 0) {

			this.reportFailure("webVerifyTextWithBefore:Before text: " + before + "isn't found in Webpage");

			return false;
		} else {

			int textToVerifyIndex = content.indexOf(
				textToVerify,
				noOfTimesBeforeTextisPresent.get(startIndex) + before.length());

			if (textToVerifyIndex < 0) {

				this.reportFailure("webVerifyTextWithBefore: " + textToVerify + "isnt found in webpage");

				return false;
			}

			else {

				this.sop("webVerifyTextWithBefore:" + textToVerify + "is present after" + before);
				return true;
			}
		}
	}*/

	public boolean webVerifyTextWithAfter(String after, String textToVerify) throws Exception {

		String expecWordCount = getWordCount(textToVerify);

		boolean textExists = webVerifyText(textToVerify,expecWordCount,after);

		return textExists;
	}

	
	public boolean webVerifyTextWithAfter(String after, String textToVerify, Integer index) throws Exception {
		
		String expecWordCount = getWordCount(textToVerify);

		this.sop("expecWordCount :" + expecWordCount);
		
		boolean textExists = webVerifyText(textToVerify,expecWordCount,after+";"+index);

		return textExists;
		
	}
	
	/*public boolean webVerifyTextWithAfter(String after, String textToVerify, Integer index) throws Exception {

		// Get window Path
		String windowPath = getCurrentWindowXPath();

		// Get Current Window Index
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		// Document Path
		String docPath = windowPath + "/web:document[@index='0']";

		// Content within the page
		String content = web.document(docPath).getElementsByTagName("body").get(0).getAttribute("text");

		// Finding number of times text is present & storing their positions in
		// the list
		List<Integer> indexOfAfterTextPresence = new ArrayList<Integer>();
		int afterindex = content.length(), startIndex = 0;

		while ((afterindex = content.lastIndexOf(
			after,
			afterindex)) != -1) {
			indexOfAfterTextPresence.add(afterindex);
			afterindex -= after.length() - 1;

			this.sop("inside");
		}

		if (indexOfAfterTextPresence.size() == 0) {

			this.reportFailure("webVerifyTextWithAfter: after text: " + after + "isn't found in Webpage");

			return false;
		} else {

			Collections.reverse(indexOfAfterTextPresence);

			int textToVerifyIndex = content.lastIndexOf(
				textToVerify,
				indexOfAfterTextPresence.get(startIndex));

			if (textToVerifyIndex < 0) {

				this.reportFailure("webVerifyTextWithAfter: " + textToVerify + "isnt found in webpage");

				return false;
			}

			else {

				this.sop("webVerifyTextWithAfter:" + textToVerify + "is present before" + after);

				return true;
			}
		}
	}*/

	public boolean webVerifyTextWithBeforeAfter(String before, String after, String textToVerify) throws Exception {

		boolean textExists = webVerifyText(textToVerify,before,	after);

		return textExists;

	}
	
	public boolean webVerifyTextWithBeforeAfter(String before, String after, String textToVerify, Integer index)throws Exception {
		
		boolean textExists = webVerifyText(textToVerify,before+";"+index,after+";"+index);

		return textExists;
	}

	/**
	 * 
	 * @param before
	 * @param after
	 * @param textToVerify
	 * @param index
	 */
	/*public boolean webVerifyTextWithBeforeAfter(String before, String after, String textToVerify, Integer index)
			throws Exception {

		// Get window Path
		String windowPath = getCurrentWindowXPath();

		// Get Current Window Index
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		// Document Path
		String docPath = windowPath + "/web:document[@index='0']";

		// Content within the page
		String content = web.document(docPath).getElementsByTagName("body").get(0).getAttribute("text");

		// Finding number of times before text is present & storing their
		// positions in the list
		List<Integer> indexOfAfterTextPresence = new ArrayList<Integer>();
		int afterindex = content.length(), beforeindex = 0, startIndex = 0;

		while ((afterindex = content.lastIndexOf(
			after,
			afterindex)) != -1) {
			indexOfAfterTextPresence.add(afterindex);
			afterindex -= after.length() - 1;

		}

		// Finding number of times after text is present & storing their
		// positions in the list
		List<Integer> noOfTimesBeforeTextisPresent = new ArrayList<Integer>();
		startIndex = 0;
		while ((beforeindex = content.indexOf(
			before,
			beforeindex)) != -1) {
			noOfTimesBeforeTextisPresent.add(beforeindex);
			beforeindex += before.length() - 1;

		}

		if ((indexOfAfterTextPresence.size() == 0) || ((noOfTimesBeforeTextisPresent.size() == 0))) {
			if (noOfTimesBeforeTextisPresent.size() == 0) {

				this.reportFailure(" webVerifyTextWithBeforeAfter: Before text: " + before + "isn't found in Webpage");

				return false;

			} else {
				this.reportFailure("webVerifyTextWithBeforeAfter: after text: " + after + "isn't found in Webpage");

				return false;
			}
		} else {

			if (index == null) {

				startIndex = 0;

			} else {

				startIndex = index;
			}

			// Collections.reverse(indexOfAfterTextPresence);

			String textToVerifyIndex = content.substring(
				noOfTimesBeforeTextisPresent.get(startIndex),
				indexOfAfterTextPresence.get(startIndex));

			if (textToVerifyIndex.contains(textToVerify)) {

				this.sop("webVerifyTextWithBeforeAfter:" + textToVerify + "is present in between " + before + "and" + after);
				return true;
			}

			else {

				this.reportFailure("webVerifyTextWithBeforeAfter: " + textToVerify + "isnt found in webpage");
				return false;
			}
		}
	}
*/
	/*
	 * public String webGetTextInTable(String text,String sWordsRequired,String
	 * sIndex,String action) throws Exception {
	 * 
	 * String[] compTypes = {"td"};
	 * 
	 * Document Path String windowPath = getCurrentWindowXPath();
	 * 
	 * int currentWindowIndex =
	 * Integer.parseInt(web.window(windowPath).getAttribute("index"));
	 * 
	 * String docPath = windowPath +
	 * "/web:document[@index='"+currentWindowIndex+"']"; String returnText="";
	 * boolean elementsFound=false; for(int
	 * index=0;index<compTypes.length;index++){ returnText = ""; Get Text Path
	 * String xPath = docPath + "/web:"+compTypes[index]+"[@text='"+text+"']";
	 * 
	 * 
	 * if(web.element(xPath).exists()){ int counter=0,k=0; elementsFound=true;
	 * DOMElement element=web.element(xPath); if(toInt(sIndex)>0){
	 * List<DOMElement> nameMatchElementsList=
	 * web.document(docPath).getElementsByName(compTypes[index]); for( int i=0;
	 * i<nameMatchElementsList.size(); i++) {
	 * if(text.equalsIgnoreCase(nameMatchElementsList
	 * .get(i).getAttribute("innerText"))) k++; if(k==toInt(sIndex)) { element=
	 * nameMatchElementsList.get(i); }
	 * 
	 * } }
	 * 
	 * for( int words=0; words<toInt(sWordsRequired); words++){ DOMElement
	 * labels; if("after".equalsIgnoreCase(action)) labels=
	 * element.getNextSibling(); else labels = element.getPreviousSibling();
	 * while(labels!= null && labels.getAttribute("innerText") ==null &&
	 * counter<10 ) { String labelVal = labels.getAttribute("innerText");
	 * //System
	 * .out.println("labelVal :"+labels.getNextSibling().getAttribute("innerText"
	 * ) + "tag name"+labels.getTag()+
	 * web.element(xPath).getAttribute("innerText"));
	 * if("after".equalsIgnoreCase(action)) labels= element.getNextSibling();
	 * else labels = labels.getPreviousSibling(); counter++; }
	 * 
	 * if(labels!=null) returnText= returnText+
	 * " "+labels.getAttribute("innerText"); element=labels;
	 * 
	 * } if(elementsFound) break;
	 * 
	 * } }
	 * 
	 * return returnText.trim(); }
	 */

	public String webGetTextInTable(String text, String sWordsRequired, String sIndex, String action) throws Exception {
		return webGetTextInTable("0",text, sWordsRequired, sIndex, action);
	}
	
	public String webGetTextInTable(String docIndex, String text, String sWordsRequired, String sIndex, String action) throws Exception {

		String[] compTypes = { "td" };

		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		String docPath = windowPath + "/web:document[@index='" + docIndex + "']";
		//info("docPath :"+docPath);
		
		String returnText = "";
		boolean elementsFound = false;
		for (int index = 0; index < compTypes.length; index++) {
			
			returnText = "";
			/* Get Text Path */
			String xPath = docPath + "/web:" + compTypes[index] + "[@text='" + text + "']";
			info("xPath :"+xPath);
			
			if (web.element(xPath).exists()) {
				
				int counter = 0, k = 0;
				elementsFound = true;
				
				DOMElement element = web.element(xPath);
				
				if (toInt(sIndex) > 0) {
					
					List<DOMElement> nameMatchElementsList = web.document(docPath).getElementsByName(compTypes[index]);
					
					for (int i = 0; i < nameMatchElementsList.size(); i++) {
						String innerText = nameMatchElementsList.get(i).getAttribute("innerText");
						//info("innerText :"+innerText);
						innerText = innerText.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text.
						
						if (text.equalsIgnoreCase(innerText))
							k++;
						if (k == toInt(sIndex)) {
							element = nameMatchElementsList.get(i);
						}

					}
				}

				//info("Code Testing :"+action);
				
				for (int words = 0; words < toInt(sWordsRequired); words++) {
					
					DOMElement labels;
					if ("after".equalsIgnoreCase(action))
						labels = element.getNextSibling();
					else
						labels = element.getPreviousSibling();
					
					
					//This was considered when "td" is having t('8')
					//Start of New Changes - Updated on 16th Apr
					String labelVal = labels.getAttribute("innerText");
					
					if(labelVal != null){
						labelVal = labelVal.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text.
						if("".equalsIgnoreCase(labelVal)){// When td is empty we are moving next element
							if ("after".equalsIgnoreCase(action))
								labels = labels.getNextSibling();
							else
								labels = labels.getPreviousSibling();
						}
					}
					//End of New Changes - Updated on 16th Apr
					
					
					
					while (labels != null && labels.getAttribute("innerText") == null && counter < 10) {
						
						//info("labels Tag:"+labels.getTag());
						//info("labels Tag:"+labels.getDisplayText());
						
						//String labelVal = labels.getAttribute("innerText");
						//labelVal = labelVal.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text.
						
						//info("labelVal :"+labelVal);
						// this.sop("labelVal :"+labels.getNextSibling().getAttribute("innerText")
						// + "tag name"+labels.getTag()+
						// web.element(xPath).getAttribute("innerText"));
						if ("after".equalsIgnoreCase(action))
							labels = labels.getNextSibling();
						else
							labels = labels.getPreviousSibling();
						
						counter++;
					}
					
					//info("labels :"+labels);
					
					if (labels != null){
						String expectedInnerText = labels.getAttribute("innerText");
						expectedInnerText = expectedInnerText.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text. Updated on 16th Apr
						returnText = returnText + " " +expectedInnerText ;
					}else{
						break; // when labels is null
					}
						
					
					element = labels;

				}
				if (elementsFound)
					break;

			}
		}

		return returnText.trim();
	}

	public String webGetText(String beforeWithIndex, String afterWithIndex) throws Exception {
		return webGetText("0",beforeWithIndex,afterWithIndex);
	}
	
	public String webGetText(String docIndex, String beforeWithIndex, String afterWithIndex) throws Exception {

		String beforeIndex = "0";
		String afterIndex = "0";

		String before[] = beforeWithIndex.trim().split(";");
		String after[] = afterWithIndex.trim().split(";");

		if (before.length > 1) {
			beforeIndex = before[1];
		}

		if (after.length > 1) {
			afterIndex = after[1];
		}

		if ("".equals(before[0])) {
			before[0] = "1"; // if beforetext = "". It means we have get one
								// word before the after text
		}

		if ("".equals(after[0])) {
			after[0] = "1"; // if aftertext = "". It means we have get one word
							// after the before text
		}

		return webGetText(docIndex, before[0],after[0],beforeIndex,afterIndex);
	}

	public String webGetText(String beforeText, String afterText, String beforeIndex, String afterIndex)
	throws Exception {
		
		return webGetText("0",beforeText, afterText, beforeIndex, afterIndex);
	}
	
	public String webGetText(String docIndex,String beforeText, String afterText, String beforeIndex, String afterIndex)
			throws Exception {

		String requiredText = "";

		boolean beforeTextAsInt = beforeText.matches("\\d");
		// this.sop("beforeTextAsInt :"+beforeTextAsInt);

		boolean afterTextAsInt = afterText.matches("\\d");
		// this.sop("afterTextAsInt :"+afterTextAsInt);

		if (beforeTextAsInt && !afterTextAsInt) {

			this.sop("Before Text is Integer && After Text is String ");

			// Get the Before Text based on number of words specified in
			// beforeText field
			requiredText = webGetBeforeText(docIndex, afterText,	beforeText,	afterIndex);

		} else if (!beforeTextAsInt && afterTextAsInt) {

			this.sop("Before Text is String && After Text is Integer");

			// Get the After Text based on number of words specified in
			// beforeText field
			requiredText = webGetAfterText(docIndex,
				beforeText,
				afterText,
				beforeIndex);

		} else {

			this.sop("Before Text is String && After Text is String");

			requiredText = webGetBetweenText(docIndex, beforeText, afterText,	beforeIndex, afterIndex);
		}

		return requiredText;
	}

	
	public String webGetBetweenText(String before, String after, String beforeIndex, String afterIndex)
	throws Exception {
		
		return webGetBetweenText("0",before, after, beforeIndex,afterIndex);
	}
	
	public String webGetBetweenText(String docIndex, String before, String after, String beforeIndex, String afterIndex)
			throws Exception {

		System.out.println("************** Start : webGetBetweenText **********************");

		// Initialize the text with ""
		String text = "";

		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		// String docPath = windowPath +
		// "/web:document[@index='"+currentWindowIndex+"']" ;
		String docPath = windowPath + "/web:document[@index='"+docIndex+"']";

		String content = web.document(docPath).getElementsByTagName("body").get(0).getAttribute("text");
		System.out.println("content :"+content);
		content = content.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text.
		if (before.equals("-1") && after.equals("-1")) {
			return content;
		}

		// javax.swing.JOptionPane.showMessageDialog(null,content); // this
		// hould be removed ,as it blocks flow execution
		//this.sop("html content: " + content);
		//this.sop("before: " + before);
		//this.sop("after: " + after);
		
		List<Integer> indexOfAfterTextPresence = new ArrayList<Integer>();
		int afterindex = content.length() - 1, beforeindex = 0, startIndex = 0;

		while ((afterindex = content.lastIndexOf(after,	afterindex)) != -1) {
			//this.sop("***afterindex: " + content.substring(afterindex));
			indexOfAfterTextPresence.add(afterindex);
			// afterindex -= after.length() - 1;
			afterindex = afterindex - 1;
		}

		List<Integer> noOfTimesBeforeTextisPresent = new ArrayList<Integer>();
		startIndex = 0;
		while ((beforeindex = content.indexOf(before,beforeindex)) != -1) {
			//this.sop("***beforeindex: " + content.substring(beforeindex));
			noOfTimesBeforeTextisPresent.add(beforeindex + before.length());
			beforeindex += before.length() - 1;

		}

		this.sop("noOfTimesBeforeTextisPresent :" + noOfTimesBeforeTextisPresent.toString());
		this.sop("indexOfAfterTextPresence :" + indexOfAfterTextPresence.toString());

		if ((indexOfAfterTextPresence.size() == 0) || ((noOfTimesBeforeTextisPresent.size() == 0))) {
			if (noOfTimesBeforeTextisPresent.size() == 0) {

				this.reportFailure(" webVerifyTextWithBeforeAfter: Before text: " + before + "isn't found in Webpage");

				return text;

			} else {
				this.reportFailure("webVerifyTextWithBeforeAfter: after text: " + after + "isn't found in Webpage");

				return text;
			}
		} else {
			Collections.reverse(indexOfAfterTextPresence);
			
			text = content.substring(noOfTimesBeforeTextisPresent.get(toInt(beforeIndex)),indexOfAfterTextPresence.get(toInt(afterIndex))).trim();
			
			System.out.println("text:"+text);
		}

		System.out.println("************** End : webGetBetweenText **********************");

		return text;
	}

	public String webGetBeforeText(String afterText, String sWordsRequired, String sIndex) throws Exception {
		return webGetBeforeText("0",afterText, sWordsRequired, sIndex);
	}
	
	public String webGetBeforeText(String docIndex,String afterText, String sWordsRequired, String sIndex) throws Exception {

		// Initialize the text with ""
		String text = "";

		// Document Path
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		// String docPath = windowPath +
		// "/web:document[@index='"+currentWindowIndex+"']" ;
		String docPath = windowPath + "/web:document[@index='"+docIndex+"']";

		// Page Content
		String content = web.document(docPath).getElementsByTagName("body").get(0).getAttribute("text");
		content = content.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text.
		int wordsRequired = Integer.parseInt(sWordsRequired);
		int index = Integer.parseInt(sIndex);

		// Finding Number of Times specified String is present in the page
		List<Integer> noOfTimesBeforeTextIsPresent = new ArrayList<Integer>();
		int afterindex = 0;
		while ((afterindex = content.indexOf(afterText,afterindex)) != -1) {
			// Adding starting position of a search string to the list
			noOfTimesBeforeTextIsPresent.add(afterindex);
			afterindex += afterText.length() - 1;
		}

		this.sop("Number of times search string is present :" + noOfTimesBeforeTextIsPresent.size());
		this.sop("Positions of specified String :" + noOfTimesBeforeTextIsPresent.toString());

		String dataBeforeAtterString = content.substring(0,noOfTimesBeforeTextIsPresent.get(index)).trim();

		// this.sop("dataBeforeAtterString :"+dataBeforeAtterString);
		//dataBeforeAtterString = dataBeforeAtterString.replaceAll("t\\(.*?\\)", "").trim();

		if (wordsRequired < 0) {
			return dataBeforeAtterString;
		}

		String[] beforeString = dataBeforeAtterString.split(" ");

		String expected = "";

		if (wordsRequired > beforeString.length) {
			System.err.print("Words Required :" + wordsRequired + " is more than actual Words available :"
					+ beforeString.length);
		} else if (wordsRequired <= 0) {
			System.err.print("Words Required :" + wordsRequired + " is not as supposed be. Please provide valid value");
		} else {

			for (int wordIndex = beforeString.length - wordsRequired; wordIndex < beforeString.length; wordIndex++) {
				expected = expected + " " + beforeString[wordIndex];
			}

			this.sop("Data Expected :" + expected.trim());
		}

		return expected.trim();
	}
	
	public String webGetAfterText(String beforeText, String sWordsRequired, String sIndex) throws Exception {
		return webGetAfterText("0",beforeText, sWordsRequired, sIndex);
	}

	public String webGetAfterText(String docIndex, String beforeText, String sWordsRequired, String sIndex) throws Exception {

		// Initialize the text with ""
		String text = "";

		// Document Path
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));

		// String docPath = windowPath +
		// "/web:document[@index='"+currentWindowIndex+"']" ;
		String docPath = windowPath + "/web:document[@index='"+docIndex+"']";

		// Page Content
		String content = web.document(docPath).getElementsByTagName("body").get(0).getAttribute("text");
		content = content.replaceAll("t\\(.*?\\)", "").trim();//IE9 change for removing script tag which is picked up for all text.
		this.sop("content :"+content);
		// String content = getPageText();

		int wordsRequired = Integer.parseInt(sWordsRequired);
		int index = Integer.parseInt(sIndex);

		// Finding Number of Times specified String is present in the page
		List<Integer> noOfTimesBeforeTextIsPresent = new ArrayList<Integer>();
		int beforeindex = 0;
		while ((beforeindex = content.indexOf(beforeText,beforeindex)) != -1) {
			// Adding starting position of a search string to the list
			noOfTimesBeforeTextIsPresent.add(beforeindex);
			beforeindex += beforeText.length() - 1;
		}

		this.sop("Number of times search string is present :" + noOfTimesBeforeTextIsPresent.size());
		this.sop("Positions of specified String :" + noOfTimesBeforeTextIsPresent.toString());

		String wordAfterBeforeString = content.substring(noOfTimesBeforeTextIsPresent.get(index) + beforeText.length()).trim();

		// this.sop("wordAfterBeforeString :"+wordAfterBeforeString);
		//wordAfterBeforeString = wordAfterBeforeString.replaceAll("t\\(.*?\\)", "").trim();
		
		if (wordsRequired < 0) {
			return wordAfterBeforeString;
		}

		String[] PartlySplitArray = wordAfterBeforeString.split("\n");
		this.sop("PartlySplitArray :" + PartlySplitArray.length);

		ArrayList<String> partlySplitList = new ArrayList<String>();

		for (int iteIndex = 0; iteIndex < PartlySplitArray.length; iteIndex++) {

			String[] partString = PartlySplitArray[iteIndex].trim().split(" ");

			for (int spaceIter = 0; spaceIter < partString.length; spaceIter++) {
				partlySplitList.add(partString[spaceIter]);
			}
		}
		String[] afterString = partlySplitList.toArray(new String[partlySplitList.size()]);

		String expected = "";

		if (wordsRequired > afterString.length) {
			System.err.print("Words Required :" + wordsRequired + " is more than actual Words available :"+ afterString.length);
		} else if (wordsRequired <= 0) {
			System.err.print("Words Required :" + wordsRequired + " is not as supposed be. Please provide valid value");
		} else {

			for (int wordIndex = 0; wordIndex < wordsRequired; wordIndex++) {
				expected = expected + " " + afterString[wordIndex];
			}

			this.sop("Actual Data To Verify :" + expected.trim());
		}

		return expected.trim();

	}

	private String getPageText() throws Exception {

		String content = "";
		String[] compTypes = { "span", "label", "div", "li", "b", "h1", "h2", "h3", "h4", "h5", "h6" };

		// Document Path
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(
			windowPath).getAttribute(
			"index"));

		// String docPath = windowPath +
		// "/web:document[@index='"+currentWindowIndex+"']" ;
		String docPath = windowPath + "/web:document[@index='0']";

		for (int index = 0; index < compTypes.length; index++) {

			List<DOMElement> elements = null;

			elements = web.document(
				docPath).getElementsByTagName(
				compTypes[index]);

			if (elements != null) {

				for (int textIndex = 0; textIndex < elements.size(); textIndex++) {

					String text = "";
					text = elements.get(
						textIndex).getAttribute(
						"text");
					this.sop("text :" + text);
					if (text != null) {
						content = content + " " + text;
					}

				}
			}
		}

		// this.sop("Text Content in the Page :"+content);

		return content;
	}

	public void webSetText(String name, String valueToSet) throws Exception {

		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(
			windowPath).getAttribute(
			"index"));

		String docPath = windowPath + "/web:document[@index='*']";
		List<DOMElement> bodyList = web.document(
			docPath).getElementsByTagName(
			"body");
		DOMElement body = bodyList.get(0);

		String imageDetails[] = name.split(";");
		List<DOMElement> textFieldList = body.getElementsByTagName("input");
		List<DOMElement> requiredTextFieldList = new ArrayList<DOMElement>();

		for (DOMElement textField : textFieldList) {

			if ((textField.getAttribute("type").equals("text"))
					&& (textField.getAttribute("name").equals(imageDetails[0]))) {

				requiredTextFieldList.add(textField);

			}
		}
		this.sop(""+requiredTextFieldList.size());
		try {
			if (imageDetails.length > 1) {

				DOMText textField = (DOMText) requiredTextFieldList.get(Integer.parseInt(imageDetails[1]));
				textField.setText(valueToSet);

			}

			else {

				DOMText textField = (DOMText) requiredTextFieldList.get(0);

				textField.setText(valueToSet);

			}
		} catch (IndexOutOfBoundsException e) {

			reportFailure("No textField found for the specified  Name" + name);

			throw new Exception("webSetText : No textField found for the specified textField Name");

		}
	}

	public void extractZipFile(String zipFilePath, String directoryToExtractTo) throws Exception {

		Enumeration entriesEnum;
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipFilePath);
			entriesEnum = zipFile.entries();

			File directory = new File(directoryToExtractTo);

			/**
			 * Check if the directory to extract to exists
			 */
			if (!directory.exists()) {
				/**
				 * If not, create a new one.
				 */
				new File(directoryToExtractTo).mkdir();
				this.sop("...Directory Created -" + directoryToExtractTo);
			}
			while (entriesEnum.hasMoreElements()) {
				try {
					ZipEntry entry = (ZipEntry) entriesEnum.nextElement();

					if (entry.isDirectory()) {

					} else {

						this.sop("Extracting file: " + entry.getName());

						int index = 0;
						String name = entry.getName();
						index = entry.getName().lastIndexOf(
							"/");
						if (index > 0 && index != name.length())
							name = entry.getName().substring(
								index + 1);

						this.sop(name);

						File f = new File(directoryToExtractTo + name);
						if (f.getParentFile().exists() == false) {
							f.getParentFile().mkdirs();
						}

						writeFile(
							zipFile.getInputStream(entry),
							new BufferedOutputStream(new FileOutputStream(directoryToExtractTo + name)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			zipFile.close();
		} catch (Exception ioe) {
			System.err.println("Some Exception Occurred:");
			ioe.printStackTrace();
			return;
		}

	}

	private void writeFile(InputStream in, OutputStream out) throws Exception {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(
				buffer,
				0,
				len);

		in.close();
		out.close();
	}

	/********************************************************************************************
	 * ****************CDC code
	 *****************************************/

	public void setFlexText(String label, String value) throws Exception {
		forms.flexWindow("//forms:flexWindow").setText(label,"",value);
	}

	/**
	 * Click the ok button within the flexwindow
	 * 
	 * @throws Exception
	 */
	public void clickFlexOK() throws Exception {
		forms.flexWindow(
			"//forms:flexWindow").clickOk();
	}

	/**
	 * verify request status
	 * 
	 * @param expectedValue
	 * @throws Exception
	 */
	public void verifyParenetChildReqs(String Parent, String ReqIndex, String ParentStatus, String commaReqNames,String reqStatuses) throws Exception {
		String pars[] = Parent.split(",");
		verifyParenetChildReqs(
			pars[Integer.parseInt(ReqIndex) - 1],
			ParentStatus,
			commaReqNames,
			reqStatuses);
	}

	public void verifyParenetChildReqs(String Parent, String ParentStatus, String commaReqNames, String reqStatuses)
			throws Exception 
		{
		
		openRequest(Parent,	"");
		boolean parentStatus = verifyRequestStatus(ParentStatus);
		if (parentStatus) {
			if(commaReqNames!=null && "".equals(commaReqNames)) return ;
			String childsNames[] = commaReqNames.split(",");
			String childStatus[] = reqStatuses.split(",");
			for (int i = 0; i < childsNames.length; i++) 
			{
				if(childsNames[i]!=null && "".equals(childsNames[i])) continue;
				
				openRequest("",	childsNames[i]);
				String reqId = findChildRequest(childsNames[i],	Parent);
				if ("".equals(reqId)) 
				{
					reportFailure("Could not find the request with name : " + childsNames[i] + ", and Parent:" + Parent);
				} else 
				{
					info("Found child request : " + reqId + " for the request name: " + childsNames[i]
							+ ", and Parent:" + Parent);
				}

				openRequest(reqId,"");
				verifyRequestStatus(childStatus[i]);
			}

		}

	}

	public String findChildRequest(String reqName, String par) throws Exception {
		String returnReqId = "";
		int maxRows = 10;
		int currRow = 0;
		boolean found = false;
		while (!found) {
			String currReqName = forms.textField(
				"//forms:textField[(@name='JOBS_PROGRAM_" + currRow + "')]").getText();
			String currPar = forms.textField(
				"//forms:textField[(@name='JOBS_PARENT_" + currRow + "')]").getText();
			if (!"".equals(currPar) && par.equals(currPar)) {
				found = true;
				returnReqId = forms.textField(
					"//forms:textField[(@name='JOBS_REQUEST_ID_" + currRow + "')]").getText();
			}

			if (!found) {
				currRow++;
				if (currRow == maxRows) {
					forms.textField(
						"//forms:textField[(@name='JOBS_PROGRAM_" + currRow + "')]").invokeSoftKey(
						"DOWN");
					currRow = 9;
					String status = forms.getStatusBarMessage();
					if (!"".equals(status) && status.contains("you cannot create records")) {
						break;
					}
				}
			}

		}
		return returnReqId;
	}

	public void openRequest(String ReqID, String ReqName) throws Exception {
		this.sop("Opening the request ");
		if (forms.button(
			"//forms:button[(@name='JOBS_CHANGE_QUERY_0')]").exists())
			forms.button(
				"//forms:button[(@name='JOBS_CHANGE_QUERY_0')]").click();

		forms.radioButton(
			"//forms:radioButton[(@name='JOBS_QF_WHICH_JOBS_SPECIFIC_JOBS_0')]").select();
		forms.textField(
			"//forms:textField[(@name='JOBS_QF_REQUEST_ID_0')]").setText(
			"");
		forms.textField(
			"//forms:textField[(@name='JOBS_QF_PROGRAM_0')]").setText(
			"");
		if (!"".equals(ReqID)) {
			forms.textField(
				"//forms:textField[(@name='JOBS_QF_REQUEST_ID_0')]").setText(
				ReqID);
			info("Searching with req id: " + ReqID);
		}

		if (!"".equals(ReqName)) {
			forms.textField(
				"//forms:textField[(@name='JOBS_QF_PROGRAM_0')]").setText(
				ReqName);
			info("Searching with req name: " + ReqName);
		}

		forms.textField(
			"//forms:textField[(@name='JOBS_QF_REQUEST_DAYS_0')]").setText(
			"2");
		{
			think(1);
		}
		forms.button(
			"//forms:button[(@name='JOBS_QF_FIND_0')]").click();
		{
			think(1);
		}

	}

	public boolean verifyRequestStatus(String expectedValue) throws Exception {
		String requestPhase = forms.textField(
			"//forms:textField[(@name='JOBS_PHASE_0')]").getText();
		String requestStatus = forms.textField(
			"//forms:textField[(@name='JOBS_STATUS_0')]").getText();
		boolean retVal = false;
		int i = 1;
		while ((!requestPhase.equals("Completed")) && (i < 500)) 
		{
			forms.button(
				"//forms:button[(@name='JOBS_REFRESH_0')]").click();
			requestPhase = forms.textField(
				"//forms:textField[(@name='JOBS_PHASE_0')]").getText();
			requestStatus = forms.textField(
				"//forms:textField[(@name='JOBS_STATUS_0')]").getText();
			i++;
			delay(2000);
		}
		if(expectedValue.equalsIgnoreCase("COMPLETED"))
		{
			if(requestStatus.equalsIgnoreCase("NORMAL") || requestStatus.equalsIgnoreCase("WARNING"))
			{
				retVal	=	true;
				info("Request Phase: Completed; Actual Request Status: " + requestStatus +  ", Expected status: Normal / Warning");
			}
			else
			{
				reportFailure("Request Phase: Completed; Actual Request Status: " + requestStatus+  ", Expected status: Normal / Warning");
			}	
			return retVal;			
		}
		if (requestPhase.equalsIgnoreCase("Completed")) 
		{
			String reqP[] = expectedValue.split(";");
			if (requestStatus.equalsIgnoreCase(reqP[0]) || (reqP.length > 1 && requestStatus.equalsIgnoreCase(reqP[1]))) {
				info("Request Phase: Completed; Actual Request Status: " + requestStatus + ", Expected status:"
						+ expectedValue);
				retVal = true;
			} else {
				reportFailure("Request Phase: Completed; Actual Request Status: " + requestStatus
						+ ", Expected status:" + expectedValue);
			}

		} else {
			warn("Request Phase is in " + requestPhase + ". Actual Request Status is " + requestStatus
					+ ", Expected status:" + expectedValue + ". Please Check it");
		}
		return retVal;
	}

	@SuppressWarnings("unchecked") 
	public void setValueBasedonLabelAfterUIComponent(String labelName, String compType,	String valueToSet) throws Exception {
		
		//Get Window index
		String winnIndex = web.getFocusedWindow().getAttribute("index");
		
		//Get Window title
		String title = web.getFocusedWindow().getTitle();
		
		//Create Window Path
		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";

		String xPath = winPath + "/web:document[@index='0']/web:td[@text='" + labelName + "']";

		String tdIndex = web.element(xPath).getAttribute("index");
		int finalTdIndex = Integer.parseInt(tdIndex);

		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(finalTdXPath).getElementsByTagName("input");
		DOMElement element = elements.get(0);

		String RTObject_Xpath = null;

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='"+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.selectBox(RTObject_Xpath).selectOptionByText(valueToSet);
		}
		if (compType.equalsIgnoreCase("textArea")) {
			this.sop(element.getAttribute("id"));
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.textArea(
				RTObject_Xpath).setText(
				valueToSet);
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.textBox(
				RTObject_Xpath).setText(
				valueToSet);
		}

		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			boolean theValue = Boolean.parseBoolean(valueToSet);
			web.checkBox(
				RTObject_Xpath).check(
				theValue);
		}
	}

	@SuppressWarnings("unchecked") public void setValueBasedonLabelBeforeUIComponent(String labelName, String compType,	String valueToSet) throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();
		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";

		String xPath = winPath + "/web:document[@index='0']/web:td[@text='" + labelName + "']";

		String tdIndex = web.element(xPath).getAttribute("index");
		int finalTdIndex = Integer.parseInt(tdIndex) + 2;

		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(finalTdXPath).getElementsByTagName(compType);
		DOMElement element = elements.get(0);

		String RTObject_Xpath = null;

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.selectBox(
				RTObject_Xpath).selectOptionByText(
				valueToSet);
		}
		if (compType.equalsIgnoreCase("textArea")) {
			this.sop(element.getAttribute("id"));
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.textArea(
				RTObject_Xpath).setText(
				valueToSet);
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.textBox(
				RTObject_Xpath).setText(
				valueToSet);
		}

		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"
					+ element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			boolean theValue = Boolean.parseBoolean(valueToSet);
			web.checkBox(
				RTObject_Xpath).check(
				theValue);
		}
	}

	@SuppressWarnings("unchecked") public String getValueBasedonLabelBeforeUIComponent(String labelName, String compType)
			throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = winPath + "/web:document[@index='0']/web:form[@index='0']/web:td[@text='" + labelName + "']";

		String tdIndex = web.element(
			xPath).getAttribute(
			"index");

		int finalTdIndex = Integer.parseInt(tdIndex) + 2;
		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(
			finalTdXPath).getElementsByTagName(
			compType);

		DOMElement element = elements.get(0);

		String RTObject_Xpath = null;
		String returnValue = null;

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='"
					+ element.getAttribute("id") + "']";
			returnValue = web.selectBox(
				RTObject_Xpath).getSelectedText()[0];

		}
		if (compType.equalsIgnoreCase("textArea")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='"
					+ element.getAttribute("id") + "']";
			returnValue = web.textArea(
				RTObject_Xpath).getAttribute(
				"value");
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='"
					+ element.getAttribute("id") + "']";
			returnValue = web.textBox(
				RTObject_Xpath).getAttribute(
				"value");
		}

		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"
					+ element.getAttribute("id") + "']";
			String value = web.checkBox(
				RTObject_Xpath).getAttribute(
				"checked");
			returnValue = value;
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked") public String getValueBasedonLabelAfterUIComponent(String labelName, String compType)
			throws Exception {
		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = winPath + "/web:document[@index='0']/web:form[@index='0']/web:td[@text='" + labelName + "']";

		web.element(xPath).focus();
		
		String tdIndex = web.element(
			xPath).getAttribute(
			"index");
		this.sop("tdIndex :" + tdIndex);

		int finalTdIndex = Integer.parseInt(tdIndex);
		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(
			finalTdXPath).getElementsByTagName(
			"input");

		DOMElement element = elements.get(0);

		String RTObject_Xpath = null;
		String returnValue = null;

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='"
					+ element.getAttribute("id") + "']";
			int[] indexvalue = web.selectBox(
				RTObject_Xpath).getSelectedIndex();
			returnValue = web.selectBox(
				RTObject_Xpath).getOptions().toString();
		}
		if (compType.equalsIgnoreCase("textArea")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='"
					+ element.getAttribute("id") + "']";
			returnValue = web.textArea(
				RTObject_Xpath).getAttribute(
				"value");
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='"
					+ element.getAttribute("id") + "']";
			returnValue = web.textBox(
				RTObject_Xpath).getAttribute(
				"value");
		}
		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"
					+ element.getAttribute("id") + "']";
			String value = web.checkBox(
				RTObject_Xpath).getAttribute(
				"checked");
			returnValue = value;
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked") public boolean verifyValueBasedonLabelBeforeUIComponent(String labelName,
			String compType, String expectedValue) throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = winPath + "/web:document[@index='0']/web:form[@index='0']/web:td[@text='" + labelName + "']";

		String tdIndex = web.element(
			xPath).getAttribute(
			"index");

		int finalTdIndex = Integer.parseInt(tdIndex) + 2;
		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(
			finalTdXPath).getElementsByTagName(
			compType);

		DOMElement element = elements.get(0);

		String RTObject_Xpath = null;
		String actualValue = null;
		boolean found = false;

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='"
					+ element.getAttribute("id") + "']";
			actualValue = web.selectBox(
				RTObject_Xpath).getSelectedText()[0];
		}
		if (compType.equalsIgnoreCase("textArea")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='"
					+ element.getAttribute("id") + "']";
			actualValue = web.textArea(
				RTObject_Xpath).getAttribute(
				"value");
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='"
					+ element.getAttribute("id") + "']";
			actualValue = web.textBox(
				RTObject_Xpath).getAttribute(
				"value");

		}

		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"
					+ element.getAttribute("id") + "']";
			String value = web.checkBox(
				RTObject_Xpath).getAttribute(
				"checked");
			boolean theValue = Boolean.parseBoolean(value);
			actualValue = value;
		}

		if (actualValue.equals(expectedValue)) {
			info("PASSED: actualValue:\"" + actualValue + "\" is same as the expected value:\"" + expectedValue + "\"");
			found = true;
		} else {
			warn("FAILED: actualValue:\"" + actualValue + "\" is NOT same as the expected value:\"" + expectedValue
					+ "\"");
			found = false;
		}
		return found;
	}

	@SuppressWarnings("unchecked") public boolean verifyValueBasedonLabelAfterUIComponent(String labelName,
			String compType, String expectedValue) throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = winPath + "/web:document[@index='0']/web:form[@index='0']/web:td[@text='" + labelName + "']";

		String tdIndex = web.element(
			xPath).getAttribute(
			"index");

		int finalTdIndex = Integer.parseInt(tdIndex);
		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(
			finalTdXPath).getElementsByTagName(
			"input");

		DOMElement element = elements.get(0);

		String RTObject_Xpath = null;
		String actualValue = null;
		boolean found = false;

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='"
					+ element.getAttribute("id") + "']";
			actualValue = web.selectBox(
				RTObject_Xpath).getSelectedText()[0];
		}
		if (compType.equalsIgnoreCase("textArea")) {

			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='"
					+ element.getAttribute("id") + "']";
			actualValue = web.textArea(
				RTObject_Xpath).getAttribute(
				"value");
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='"
					+ element.getAttribute("id") + "']";
			actualValue = web.textBox(
				RTObject_Xpath).getAttribute(
				"value");
		}

		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"
					+ element.getAttribute("id") + "']";
			String value = web.checkBox(
				RTObject_Xpath).getAttribute(
				"checked");
			boolean theValue = Boolean.parseBoolean(value);
			actualValue = value;
		}

		if (actualValue.equals(expectedValue)) {
			info("PASSED: actualValue:\"" + actualValue + "\" is same as the expected value:\"" + expectedValue + "\"");
			found = true;
		} else {
			warn("FAILED: actualValue:\"" + actualValue + "\" is NOT same as the expected value:\"" + expectedValue
					+ "\"");
			found = false;
		}
		return found;
	}

	public void selectRadiobuttonBasedonLabel(String labelNameWithIndex) throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();

		int formIndex = 0;
		int documentIndex = 0;
		int defaultLabelIndex = 0; // default value "0" i.e first radio label in
									// the page
		int expLabelIndex = 0;
		String expLabelName = "";

		boolean bFound = false;
		//String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String winPath = getLatestWindowXPath();
		this.sop("winPath:"+ winPath);
		String xPath = null;

		String[] labelPathDetails = labelNameWithIndex.split(",");

		// Get the radiobutton Details passed from User
		for (int index = 0; index < labelPathDetails.length; index++) {

			if (labelPathDetails[index].trim().startsWith(
				"document=")) {

				documentIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			} else if (!labelPathDetails[index].contains("document=") && !labelPathDetails[index].contains("form=")) {

				String[] labelDetails = labelPathDetails[index].split(";");

				// Handling Similar label radiobuttons Index
				if (labelDetails.length > 1) {
					expLabelIndex = Integer.parseInt(labelDetails[1]);
					this.sop("expLabelIndex=" + expLabelIndex);
				}

				// radiobutton label
				expLabelName = labelDetails[0];

			} else if (labelPathDetails[index].trim().startsWith(
				"form=")) {

				formIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			}
		}

		this.sop("****** radio button Details ********************* ");
		this.sop("radiobutton Label Name: \"" + expLabelName + "\"  Label Index: \"" + expLabelIndex
				+ "\"  Document Index: \"" + documentIndex + "\"  Form Index: \"" + formIndex + "\"");

		xPath = winPath + "/web:document[@index='" + documentIndex + "']/web:form[@index='" + formIndex + "']";

		for (int i = 0; web.exists(xPath + "/web:input_radio[@index='" + i + "']"); i++) {
			this.sop("trial : "+ i);
			DOMElement labels = web.radioButton(
				xPath + "/web:input_radio[@index='" + i + "']").getNextSibling();
			String labelVal = labels.getAttribute("text");

			this.sop("labelVal :" + labelVal);

			if ((labelVal != null) && (labelVal.equals(expLabelName))) {

				this.sop("defaultLabelIndex=" + defaultLabelIndex);
				this.sop("expLabelIndex=" + expLabelIndex);

				if (defaultLabelIndex == expLabelIndex) {

					bFound = true;
					web.radioButton(
						xPath + "/web:input_radio[@index='" + i + "']").select();
					break;
				}

				defaultLabelIndex++;
			}
		}

		if (!bFound) {

			reportFailure("No radio button found for label : " + expLabelName);
		}
	}

	
	public void setRadioValueBasedonLabelAfterUIComponent1(String labelNameWithIndex) throws Exception {
		
		String winnIndex = web.getFocusedWindow().getAttribute("index");
		
		String title = web.getFocusedWindow().getTitle();

		int formIndex = 0;
		int documentIndex = 0;
		int defaultLabelIndex = 0; // default value "0" i.e first radio label in the page
		int expLabelIndex = 0;
		String expLabelName = "";

		boolean bFound = false;
		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = null;

		String[] labelPathDetails = labelNameWithIndex.split(",");

		// Get the radiobutton Details passed from User
		for (int index = 0; index < labelPathDetails.length; index++) {

			if (labelPathDetails[index].trim().startsWith("document=")) {

				documentIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			} else if (!labelPathDetails[index].contains("document=") && !labelPathDetails[index].contains("form=")) {

				String[] labelDetails = labelPathDetails[index].split(";");

				// Handling Similar label radiobuttons Index
				if (labelDetails.length > 1) {
					expLabelIndex = Integer.parseInt(labelDetails[1]);
					this.sop("expLabelIndex=" + expLabelIndex);
				}

				// radiobutton label
				expLabelName = labelDetails[0];

			} else if (labelPathDetails[index].trim().startsWith(
				"form=")) {

				formIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			}
		}

		this.sop("****** radio button Details ********************* ");
		this.sop("radiobutton Label Name: \"" + expLabelName + "\"  Label Index: \"" + expLabelIndex
				+ "\"  Document Index: \"" + documentIndex + "\"  Form Index: \"" + formIndex + "\"");

		String docPath = winPath + "/web:document[@index='" + documentIndex + "']";
		xPath = winPath + "/web:document[@index='" + documentIndex + "']";
		
		
		String[] components = {"div","span"};
		DOMRadioButton actualElement = null;
		
		for(int index=0; index<components.length; index++){
			
			List<DOMElement> compList = wEBLABELLIB.getComponents(docPath, components[index],expLabelName);
			
			if(compList.size() > 0 && ((expLabelIndex+1) <= compList.size())){
				
				this.sop("Found label with Tag:"+ components[index]);
				
				// Get the Label Component
				DOMElement labelComp = compList.get(expLabelIndex);
				
				labelComp.focus();
				
				// Get the Component Element
				DOMElement expCompelement = labelComp.getPreviousSibling();
				
				
				// Actual Element
				actualElement = (DOMRadioButton) expCompelement;
				
				break;
			}
		}	
			
		if(actualElement != null){
			
			// Set focus for radiobutton
			actualElement.focus();
			// Added for sync issues - by spotnuru @ 25th sep 2013 - Fixed by Ramaraju.
			actualElement.waitFor(30);
			// Select Radio button
			actualElement.select();
		}else{
			info("Unable to select radion button");
		}	
	}
	
	
	public void setRadioValueBasedonLabelAfterUIComponent(String labelNameWithIndex) throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute("index");
		
		String title = web.getFocusedWindow().getTitle();

		int formIndex = 0;
		int documentIndex = 0;
		int defaultLabelIndex = 0; // default value "0" i.e first radio label in
									// the page
		int expLabelIndex = 0;
		String expLabelName = "";

		boolean bFound = false;
		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = null;

		String[] labelPathDetails = labelNameWithIndex.split(",");

		// Get the radiobutton Details passed from User
		for (int index = 0; index < labelPathDetails.length; index++) {

			if (labelPathDetails[index].trim().startsWith("document=")) {

				documentIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			} else if (!labelPathDetails[index].contains("document=") && !labelPathDetails[index].contains("form=")) {

				String[] labelDetails = labelPathDetails[index].split(";");

				// Handling Similar label radiobuttons Index
				if (labelDetails.length > 1) {
					expLabelIndex = Integer.parseInt(labelDetails[1]);
					this.sop("expLabelIndex=" + expLabelIndex);
				}

				// radiobutton label
				expLabelName = labelDetails[0];

			} else if (labelPathDetails[index].trim().startsWith(
				"form=")) {

				formIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			}
		}

		this.sop("****** radio button Details ********************* ");
		this.sop("radiobutton Label Name: \"" + expLabelName + "\"  Label Index: \"" + expLabelIndex
				+ "\"  Document Index: \"" + documentIndex + "\"  Form Index: \"" + formIndex + "\"");

		xPath = winPath + "/web:document[@index='" + documentIndex + "']/web:form[@index='" + formIndex + "']";

		for (int i = 0; web.exists(xPath + "/web:input_radio[@index='" + i + "']"); i++) {

			DOMElement labels = web.radioButton(xPath + "/web:input_radio[@index='" + i + "']").getNextSibling();
			String labelVal = labels.getAttribute("text");

			this.sop("lableVal :" + labelVal);

			if ((labelVal != null) && (labelVal.equals(expLabelName))) {

				if (defaultLabelIndex == expLabelIndex) {
					bFound = true;
					web.radioButton(
						xPath + "/web:input_radio[@index='" + i + "']").select();
					break;
				}

				defaultLabelIndex++;
			}
		}

		if (!bFound) {

			reportFailure("No radio button found for label" + expLabelName);
		}

	}

	public void setRadioValueBasedonLabelBeforeUIComponent(String labelNameWithIndex) throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();

		int formIndex = 0;
		int documentIndex = 0;
		int defaultLabelIndex = 0; // default value "0" i.e first radio label in
									// the page
		int expLabelIndex = 0;
		String expLabelName = "";

		boolean bFound = false;
		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";
		String xPath = null;

		String[] labelPathDetails = labelNameWithIndex.split(",");

		// Get the radiobutton Details passed from User
		for (int index = 0; index < labelPathDetails.length; index++) {

			if (labelPathDetails[index].trim().startsWith(
				"document=")) {

				documentIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			} else if (!labelPathDetails[index].contains("document=") && !labelPathDetails[index].contains("form=")) {

				String[] labelDetails = labelPathDetails[index].split(";");

				// Handling Similar label radiobuttons Index
				if (labelDetails.length > 1) {
					expLabelIndex = Integer.parseInt(labelDetails[1]);
					this.sop("expLabelIndex=" + expLabelIndex);
				}

				// radiobutton label
				expLabelName = labelDetails[0];

			} else if (labelPathDetails[index].trim().startsWith("form=")) {

				formIndex = Integer.parseInt(labelPathDetails[index].split("=")[1]);

			}
		}

		this.sop("****** radio button Details ********************* ");
		this.sop("radiobutton Label Name: \"" + expLabelName + "\"  Label Index: \"" + expLabelIndex
				+ "\"  Document Index: \"" + documentIndex + "\"  Form Index: \"" + formIndex + "\"");

		xPath = winPath + "/web:document[@index='" + documentIndex + "']/web:form[@index='" + formIndex + "']";
		
		for (int i = 0; web.exists(xPath + "/web:input_radio[@index='" + i + "']"); i++) {

			DOMElement labels = web.radioButton(xPath + "/web:input_radio[@index='" + i + "']").getPreviousSibling();
			String labelVal = labels.getAttribute("text");

			this.sop("lableVal :" + labelVal);

			if ((labelVal != null) && (labelVal.equals(expLabelName))) {

				if (defaultLabelIndex == expLabelIndex) {
					bFound = true;
					web.radioButton(
						xPath + "/web:input_radio[@index='" + i + "']").select();
					break;
				}
				defaultLabelIndex++;
			}
		}

		if (!bFound) {

			reportFailure("No radio button found for label" + expLabelName);
		}
	}

	private int getRandom(String val) {
		int num = 1;
		for (int j = 1; j <= Integer.parseInt(val); j++)
			num = num * 10;
		Integer rand = new Integer((int) Math.round(Math.random() * num));
		if (rand.toString().length() == Integer.parseInt(val))
			return rand;
		else
			return getRandom(val);

	}

	public String getRand(int pattern) {

		return getRand(pattern + "");
	}

	public String getRand(String pattern) {
		String value = "";
		try {
			Pattern p = Pattern.compile(
				"(\\d+)",
				Pattern.MULTILINE);
			Matcher match = p.matcher(pattern);
			int i = 1;
			String val = "";

			while (match.find()) {
				val = match.group(i);
				String prefix = pattern.substring(
					0,
					match.start());
				String sufix = pattern.substring(match.start() + 1);

				if (!val.isEmpty()) {
					int rand = getRandom(val);
					value = value + prefix + rand;
					pattern = sufix;
					match = p.matcher(pattern);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Setting Data in the Dynamic Column Table
	 * 
	 * @param columnName
	 *            -- Column Name
	 * @param rowNum
	 *            -- Row Number
	 * @param valueToSet
	 *            -- Value to Set
	 * @throws Exception
	 */
	public void formSetValueInDynamicColumn(String columnName, String rowNumStr, String valueToSet) throws Exception {

		// Default Column Count is 0

		int colCount = 0;
		int rowNum = 0;
		if (!"".equals(rowNum)) {
			rowNum = Integer.parseInt(rowNumStr);
		}
		// Defining the variables
		String xPath = null;
		ArrayList<String> colNames = new ArrayList<String>();
		HashMap<String, String> colMap = new HashMap<String, String>();

		// Getting Column Names & Count
		for (int index = 1;; index++) {
			if (!forms.textField("//forms:textField[(@name='Q_RES_PROMPT_DISPLAY" + index + "_0')]").exists()) {
				break;
			} else {
				String headerName = forms.textField("//forms:textField[(@name='Q_RES_PROMPT_DISPLAY" + index + "_0')]").getText();
				colNames.add(headerName);
				colMap.put(headerName,index + "");
				colCount++;
			}
		}

		// Display Column Names
		this.sop("ColNames :" + colNames.toString());

		// Below Code handles if the rowNum is more than MAXVISIBLELINES in
		// forms is 9
		if (rowNum > 5 && rowNum < 9) {

			forms.textField("//forms:textField[(@name='Q_RES_DISPLAY1_" + (rowNum - 2) + "')]").setFocus();
			
			forms.textField("//forms:textField[(@name='Q_RES_DISPLAY1_" + (rowNum - 2) + "')]").invokeSoftKey("DOWN");
			xPath = "Q_RES_DISPLAY" + colMap.get(columnName) + "_" + (rowNum - 1);
		} else if (rowNum > 9) {

			forms.textField("//forms:textField[(@name='Q_RES_DISPLAY1_8')]").setFocus();
			forms.textField("//forms:textField[(@name='Q_RES_DISPLAY1_8')]").invokeSoftKey("DOWN");
			xPath = "Q_RES_DISPLAY" + colMap.get(columnName) + "_" + 8;

		} else {
			xPath = "Q_RES_DISPLAY" + colMap.get(columnName) + "_" + (rowNum - 1);
		}

		// Display XPath
		this.sop("xPath=" + xPath);

		// Activate table
		forms.window(
			"//forms:window[(@name='Q_RES')]").activate(
			true);

		// Clicking on the cell
		forms.textField(
			"//forms:textField[(@name='" + xPath + "')]").click();

		// Setting text in the specified cell
		forms.textField(
			"//forms:textField[(@name='" + xPath + "')]").setText(
			valueToSet);
        //**************************** START OF BUG 1035      *********************
        // Adding action invokeSoftKey( NEXT_FIELD )
        forms.textField("//forms:textField[(@name='" + xPath + "')]").invokeSoftKey("NEXT_FIELD");
        //*******************************END OF BUG 1035 *****************************		

	}

	public boolean verifyValueInDynamicColumn(String columnName, String rowNumStr, String expectedValue)
			throws Exception {

		boolean textVerified = false;

		String cellValue = getValueInDynamicColumn(
			columnName,
			rowNumStr);

		if (cellValue.equals(expectedValue)) {
			textVerified = true;
		}

		return textVerified;
	}

	/**
	 * Getting Value from Dynamic Column Table
	 * 
	 * @param columnName
	 *            -- Column Name
	 * @param rowNum
	 *            -- Row Number
	 * @throws Exception
	 */
	public String getValueInDynamicColumn(String columnName, String rowNumStr) throws Exception {

		// Default Column Count is 0
		// this.sop("Start of getValueInDynamicColumn function");

		int colCount = 0;
		int rowNum = 0;
		if (!"".equals(rowNum)) {
			rowNum = Integer.parseInt(rowNumStr);
		}
		// Defining the variables
		String xPath = null;
		ArrayList<String> colNames = new ArrayList<String>();
		HashMap<String, String> colMap = new HashMap<String, String>();
		
		// Getting Column Names & Count
		for (int index = 1;; index++) {
			if (!forms.textField("//forms:textField[(@name='Q_RES_PROMPT_DISPLAY" + index + "_0')]").exists(5,TimeUnit.SECONDS)) { // "//forms:textField[(@name='Q_RES_PROMPT_DISPLAY"+index+"_0')]"
				break;
			} else {
				String headerName = forms.textField(
					"//forms:textField[(@name='Q_RES_PROMPT_DISPLAY" + index + "_0')]").getText();
				//System.out.println("headerName :"+headerName);
				colNames.add(headerName);
				colMap.put(
					headerName,
					index + "");
				colCount++;
			}
		}

		// Display Column Names
		System.out.println("ColNames :" + colNames.toString());
		this.sop("ColNames :" + colNames.toString());

		// Below Code handles if the rowNum is more than MAXVISIBLELINES in
		// forms is 9
		if (rowNum > 5 && rowNum < 9) {

			forms.textField(
				"//forms:textField[(@name='Q_RES_DISPLAY1_" + (rowNum - 2) + "')]").setFocus();
			forms.textField(
				"//forms:textField[(@name='Q_RES_DISPLAY1_" + (rowNum - 2) + "')]").invokeSoftKey(
				"DOWN");
			xPath = "Q_RES_DISPLAY" + colMap.get(columnName) + "_" + (rowNum - 1);
		} else if (rowNum > 9) {

			forms.textField(
				"//forms:textField[(@name='Q_RES_DISPLAY1_8')]").setFocus();
			forms.textField(
				"//forms:textField[(@name='Q_RES_DISPLAY1_8')]").invokeSoftKey(
				"DOWN");
			xPath = "Q_RES_DISPLAY" + colMap.get(columnName) + "_" + 8;

		} else {

			xPath = "Q_RES_DISPLAY" + colMap.get(columnName) + "_" + (rowNum - 1); // "Q_RES_DISPLAY"+colMap.get(columnName)+"_"+(rowNum-1);

			/*
			 * if(columnName.contains(";")){ // As per Chandrika suggestion for
			 * Q_RES_SEQUENCE in Enter Quality Results form xPath =
			 * "Q_RES_SEQUENCE"+columnName.split(";")[1]+"_"+(rowNum-1); }else{
			 * xPath = "Q_RES_DISPLAY"+colMap.get(columnName)+"_"+(rowNum-1); }
			 */

		}

		// Display XPath
		this.sop("xPath=" + xPath);

		// Activate table
		forms.window(
			"//forms:window[(@name='Q_RES')]").activate(
			true);

		// Clicking on the cell
		forms.textField(
			"//forms:textField[(@name='" + xPath + "')]").click();

		// Setting text in the specified cell
		String cellValue = forms.textField(
			"//forms:textField[(@name='" + xPath + "')]").getAttribute(
			"text");

		return cellValue;

	}

	/**
	 * Allows to set required line to top
	 * 
	 * @param maxLine
	 * @param setLineVal
	 * @return
	 */

	public String setLine(String editField, String maxLine, String setLineVal) {
		String retIndex = "0";
		try {
			int setVal = Integer.parseInt(setLineVal);
			int maxLines = Integer.parseInt(maxLine);

			if (setVal >= maxLines) {
				String finalEdit = editField;
				int ind = editField.lastIndexOf("_");
				finalEdit = editField.substring(
					0,
					ind) + "_" + (maxLines - 1);
				this.sop("Final Edit:" + finalEdit);
				forms.textField(
					"//forms:textField[(@name='" + finalEdit + "')]").setFocus();
				think(2);
				forms.textField(
					"//forms:textField[(@name='" + finalEdit + "')]").invokeSoftKey(
					"DOWN");
				think(2);
				retIndex = (maxLines - 1) + "";
			}
		} catch (Exception e) {

		}
		return retIndex;

	}

	@SuppressWarnings({ "unchecked" }) public List<DOMElement> getCompsBasedOnLabel(String label, String compType)
			throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute(
			"index");
		String title = web.getFocusedWindow().getTitle();

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";

		String xPath = winPath + "/web:document[@index='0']/web:td[@text='" + label + "']";

		String tdIndex = web.element(
			xPath).getAttribute(
			"index");
		this.sop("tdIndex :" + tdIndex);

		int finalTdIndex = Integer.parseInt(tdIndex) + 2;
		this.sop("finalIndex :" + finalTdIndex);

		// String xTDPath =
		// winPath+"/web:document[@index='0']/web:td[@index='"+finalTdIndex+"']";
		// this.sop(web.element(xTDPath).getAttribute("innerHTML"));

		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(
			finalTdXPath).getElementsByTagName(
			compType);
		if (elements == null || elements.size() == 0) // if index 2 is not
														// working , increment
														// to 3
		{
			finalTdIndex = Integer.parseInt(tdIndex) + 3;
			this.sop("finalIndex :" + finalTdIndex);

			finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

			elements = web.element(
				finalTdXPath).getElementsByTagName(
				compType);
		}

		return elements;

	}

	@SuppressWarnings({ "unchecked" }) 
	public List<DOMElement> getCompsBasedOnLabel2(String label, String compType)
			throws Exception {

		String winnIndex = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";

		String xPath = winPath + "/web:document[@index='0']/web:td[@text='" + label + "']";

		String tdIndex = web.element(xPath).getAttribute("index");
		this.sop("tdIndex :" + tdIndex);

		int finalTdIndex = Integer.parseInt(tdIndex);
		this.sop("finalIndex :" + finalTdIndex);

		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";

		List<DOMElement> elements = web.element(finalTdXPath).getElementsByTagName(compType);

		return elements;

	}

	public void selectFromLov(String windowId, String img, String srcVal) throws Exception {
		String imgxpath = "";
		String latestDocpath;
		boolean searchimg = false;
		String[] aimg = img.split(";");
		String currIndex = web.getFocusedWindow().getAttribute(
			"index");
		this.sop("child window index:" + currIndex);
		docpath = "/web:window[@index='" + currIndex + "']/web:document[@index='" + currIndex + "']";
		imgxpath = docpath + "/web:img[@alt='" + aimg[0] + "']";
		if (aimg.length > 1) 
		{
			
			searchObjectByLocation(
				"img",
				Integer.parseInt(aimg[1]),
				aimg[0]);
			if (isFound())
			{
				searchimg = true;
				this.sop("Found img");
			}
			else
			{
				this.sop("Not found");
			}
		} else if (!img.contains("{{") && !img.contains("["))
		{
			this.sop("not found checking braces");
			docpath	=	getCurrentWindowXPath()+"/web:document[@index='0']";
			String tempWinPath	=	null;
			if(docpath.contains("Oracle Applications Home Page"))
			{
				tempWinPath	=	getLatestWindowXPath();//indexForWindow
				docpath	=		tempWinPath + "/web:document[@index='0']";
			}
			imgxpath = docpath + "/web:img[@alt='" + img + "']";
		}
		else
		{
			this.sop("Image else part , complete xpath is passed");
			imgxpath = img;
		}	

		this.sop("Image:"+imgxpath);
		if (searchimg)
			click();
		else
			web.image(
				imgxpath).click();

		// String
		// latestWindow="/web:window[@index='"+(brwcnt+1)+"' or @title='Search and Select List of Values']";
		String latestWindow = "/web:window[@title='Search and Select List of Values']";
		// web.window(latestWindow).waitForPage(null);
		if (windowId != null || windowId != "") {
			latestDocpath = latestWindow + "/web:document[@index='" + windowId + "']";
		} else {
			latestDocpath = latestWindow + "/web:document[@index='1']";
		}

		// showDebug("1:Thread.sleep of 60000 msec");
		Thread.sleep(60000);

		if (!web.document(
			latestDocpath).exists()) {
			latestDocpath = latestWindow + "/web:document[@index='" + Integer.toString(Integer.parseInt(windowId) - 1)
					+ "']";
		}
		String searchTextField = latestDocpath
				+ "/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:input_text[@name='*searchText*']";
		String GoBtn = latestDocpath
				+ "/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:button[@value='Go']";

		if (!web.button(
			GoBtn).exists()) {
			// showDebug(" 2:Thread.sleep of 60000 msec");
			Thread.sleep(30000);
		}

		web.textBox(
			searchTextField).click();
		web.textBox(
			searchTextField).setText(
			srcVal);
		web.button(
			GoBtn).click();
		web.window(
			latestWindow).waitForPage(
			null);
		// showDebug("After entering value, Click Go");

		setDocPath(latestDocpath);

		searchTableObject(
			"Select",
			"2",
			srcVal,
			"Select",
			"radio",
			0);// parsed this in actual function
		String xpath = getRadioXpath();
		web.radioButton(
			xpath).select();
		searchObjectByLocation(
			"button",
			0,
			"Select");
		click();
		{
			Thread.sleep(10);
		}
		restore();
	}

	public void clickTableImage(String WebTableName, int columnNumberToVerify, String Record,
			String ColumnNameToSelect, int ColumnNumberToSelect) throws Exception {

		String pageindex = web.getFocusedWindow().getAttribute(
			"index");
		String XpathTable = "/web:window[@index='" + pageindex
				+ "']/web:document[@index='0']/web:table[@firstTableCell='" + WebTableName + "']";
		String colNameToVerify = web.table(
			XpathTable).getCell(
			1,
			columnNumberToVerify);

		searchTableObject(
			WebTableName,
			colNameToVerify,
			Record,
			ColumnNameToSelect,
			"img",
			0);
		if (isFound()) {
			String xpath = getImgXpath();
			web.image(
				xpath).click();
		}
	}

	public void searchObjectByLocation(String objtype, int loc, String name) throws Exception {

		boolean searchSuccess = false;
		String searchAttrib = "";
		String searchAttribVal = "";
		String tags[] = { "", "", "" };
		DOMElement obj = null;
		String tag = "";

		tags = getObjectType(objtype);
		tag = tags[0];
		searchAttrib = tags[1];
		searchAttribVal = tags[2];

		// showDebug("Tag: " + tag);
		// showDebug("Atrribute: " + searchAttrib);
		List<DOMElement> ele = (List<DOMElement>) web.document(
			docpath).getElementsByTagName(
			tag);
		// showDebug("number of elements found: " + ele.size());//debug
		DOMElement temp = null;

		int currentLocation = -1;
		boolean found = false;
		String getString = "";
		for (int i = 0; i < ele.size(); i++) {
			temp = (DOMElement) ele.get(i);
			getString = temp.getAttribute(searchAttrib);
			// showDebug("VB:Link"+i+":"+getString);
			if (getString != null && !"".equals(getString))
				// showDebug("found : "+getString.trim().replaceAll("\\<.*?>",""));
				// // debug
				// showDebug("B:Link"+i+":"+getString);
				if (getString != null && !getString.equals("") && name.equals(getString.trim().replaceAll(
					"\\<.*?>",
					""))) {// more attributes judgment can be added here
							// showDebug("matched : "+temp.getAttribute(searchAttrib));
							// // debug
					currentLocation++;
					if (loc == currentLocation) {
						// temp.click();
						retObj = temp;
						found = true;
						searchSuccess = true;
						break;
					}
				}
			// showDebug("A:Link"+i+":"+getString);
		}
		if (found == false)
			System.out.println("FAIL:element with location: " + loc + " not found");
		else
			System.out.println("PASS:element with location: " + loc + " is found");
	}

	public String[] getObjectType(String objType) {
		String[] str = { "", "", "" };
		str = getObjectType(
			objType,
			false);
		return str;
	}

	public String[] getObjectType(String objType, boolean isTable) {
		String[] str = { "", "", "" };
		if (objType.equalsIgnoreCase("TEXT") || objType.equalsIgnoreCase("HTML_TEXT")
				|| objType.equalsIgnoreCase("EDIT") || objType.equalsIgnoreCase("TEXTFIELD")) {
			str[0] = "input";
			if (!isTable) {
				str[1] = "name";
				str[2] = "";
			} else {
				str[1] = "type";
				str[2] = "text";
			}

		} else if (objType.equalsIgnoreCase("radio") || objType.equalsIgnoreCase("html_radio")
				|| objType.equalsIgnoreCase("radio_button")) {
			str[0] = "input";
			str[1] = "type";
			str[2] = "radio";
		} else if (objType.equalsIgnoreCase("link") || objType.equalsIgnoreCase("html_text_link")
				|| objType.equalsIgnoreCase("html_link")) {
			str[0] = "a";
			str[1] = "text";
			str[2] = "";
		}

		else if (objType.equalsIgnoreCase("check") || objType.equalsIgnoreCase("html_checkbox")) {
			str[0] = "input";
			str[1] = "type";
			str[2] = "checkbox";
		} else if (objType.equalsIgnoreCase("list") || objType.equalsIgnoreCase("combobox")
				|| objType.equalsIgnoreCase("listbox") || objType.equalsIgnoreCase("html_list")) {
			str[0] = "select";
			str[1] = "";
			str[2] = "";
		} else if (objType.equalsIgnoreCase("image") || objType.equalsIgnoreCase("img")
				|| objType.equalsIgnoreCase("html_rect")) {
			str[0] = "img";
			if (!isTable)
				str[1] = "alt";
			else
				str[1] = "";
			str[2] = "";
		} else if (objType.equalsIgnoreCase("plain_text")) {
			str[0] = "span";
			str[1] = "";
			str[2] = "";
		} else if (objType.equalsIgnoreCase("BUTTON") || objType.equalsIgnoreCase("BTN")) {
			str[0] = "button";
			str[1] = "value";
			str[2] = "";
		} else if (objType.equalsIgnoreCase("TABLE")) {
			str[0] = "table";
			str[1] = "firstTableCell";
			str[2] = "";
		}
		return str;
	}

	public boolean isFound() {
		return searchSuccess;
	}

	public void click() throws Exception {
		if (retObj != null)
			retObj.click();
		else
			System.out.println("Object Not Found");

	}

	public void setDocPath(String str) {
		this.olddocpath = this.docpath;
		this.docpath = str;
	}

	public void restore() {
		this.docpath = this.olddocpath;
		this.form = this.oldform;
	}

	public void setRadioAttribute(String radioAttr) {
		this.radioAttr = radioAttr;
	}

	public String getRadioXpath() throws Exception {
		String ret;
		if (radioAttr.equals(""))
			ret = docpath + form + "/web:input_radio[@name='" + retObj.getAttribute("name") + "']";
		else
			ret = docpath + form + "/web:input_radio[@" + radioAttr + "='" + retObj.getAttribute(radioAttr) + "']";
		// showDebug(ret);
		return ret;
	}

	public String getImgXpath() throws Exception {
		String ret = docpath + "/web:img[@alt='" + retObj.getAttribute("alt") + "' or @index='"
				+ retObj.getAttribute("index") + "']";
		return ret;
	}

	public void searchTableObject(String tablename, String SearchCol, String SearchText, String TargetCol,
			String TargetObjType, int objloc) throws Exception {
		searchTableObject(
			0,
			tablename,
			SearchCol,
			SearchText,
			TargetCol,
			TargetObjType,
			objloc);
	}

	@SuppressWarnings("unchecked") public void searchTableObject(int tblloc, String tablename, String SearchCol,
			String SearchText, String TargetCol, String TargetObjType, int objloc) throws Exception {
		// String tablename="Select";
		// String SearchCol="*Member";
		// String SearchText="Brown, Ms. Casey";
		// String TargetCol="Approver";
		// String TargetObjType="check";
		// String TargetCol="Task";
		// String TargetObjType="text";

		// String TargetCol="Access";
		// String TargetObjType="list";
		// int objloc=0;
		searchSuccess = false;
		int tloc = -1;
		int iloc = 0;
		List<DOMElement> ths = (List<DOMElement>) web.document(
			docpath).getElementsByTagName(
			"th");
		String tags[] = { "", "", "" };
		// showDebug("number of elements: " + ths.size());//debug
		int iCounter = 0, found = 0;
		int iCols = 0, iRows = 0, startRow = 0, dummRows = 0, colRow = 0, iSrcCol = 0, iTargetCol = 0;
		DOMElement tr, tbl;
		List<DOMElement> trs = null;
		for (int i = 0; i < ths.size(); i++) {
			DOMElement th = (DOMElement) ths.get(i);

			startRow = 0;
			dummRows = 0;
			colRow = 0;

			if (tablename.equals(th.getAttribute("innerText"))) {
				tloc++;
				if (tblloc != tloc) {
					continue;
				}
				tr = th.getParent();// this will get the TR level objects
				List<DOMElement> cols = (List<DOMElement>) tr.getElementsByTagName("th");
				tblcols = iCols = cols.size();

				List<DOMElement> childtrs = (List<DOMElement>) tr.getElementsByTagName("tr");
				List<DOMElement> childths = (List<DOMElement>) tr.getElementsByTagName("th");
				// showDebug("child trs:"+childtrs.size());//debug
				// showDebug("child ths:"+childths.size());//debug

				if (childtrs.size() > 0) {
					dummRows = childtrs.size();
					// showDebug("Dummy rows:"+dummRows);//debug
				}
				tbl = tr.getParent();
				trs = (List<DOMElement>) tbl.getElementsByTagName("tr");
				tblrows = iRows = trs.size();// -dummRows;
				this.sop("Rows: " + iRows); // debug
				this.sop("Columns: " + iCols);// debug
				if (iCounter == iloc) {
					found = 1;

					tblstartrow = startRow = dummRows + 1;
					boolean isNumber = false; // 15-Jul-2011, for catching the
												// exception for converting
												// string to number
					int check = 0;
					for (int each = 0; each < childths.size(); each++) {
						// showDebug("Col # "+each+":"+childths.get(each).getAttribute("innerText"));
						if (childths.get(
							each).getAttribute(
							"innerText") != null) {

							/*
							 * Added this code to check if on converting the
							 * string is it allowing to convert to number
							 */
							try {
								check = Integer.parseInt(SearchCol);
								isNumber = true;
							} catch (Exception e) {
								// showDebug("Number format Exception");
							}
							if (!isNumber)// for hrms conv @ 14-Jul-2011 by
											// spotnuru, 15-Jul-2011
							{
								if (SearchCol.equals(childths.get(
									each).getAttribute(
									"innerText").trim())) {
									iSrcCol = each;
								}
							}

							if (TargetCol.equals(childths.get(
								each).getAttribute(
								"innerText").trim())) {
								iTargetCol = each;
							}
						}

					}
					if (isNumber) // for hrms conv @ 14-Jul-2011 by spotnuru
					{
						iSrcCol = check;
					}

					break;
				} else
					iCounter++;
			}
		}
		if (found == 1) {

			this.sop("Table found");
			this.sop("Search Col Number:" + iSrcCol);
			this.sop("Target Col Number:" + iTargetCol);
			int iOrgSrcCol, iOrgTargetCol;
			iOrgSrcCol = iSrcCol;
			iOrgTargetCol = iTargetCol;
			// showDebug("Start Row:"+startRow);//debug
			// showDebug("Total Rows:"+iRows);//debug
			for (int iStart = startRow; iStart < iRows; iStart++) {
				iSrcCol = iOrgSrcCol;
				// showDebug("Scanning Row # "+iStart );
				List<DOMElement> tds = (List<DOMElement>) trs.get(
					iStart).getElementsByTagName(
					"td");
				// showDebug("Col Count:"+tds.size()+" iSrcCol:"+iSrcCol);//debug

				if (tds.size() < iCols) {
					// showDebug("Different Row:"+iStart);
					continue;
				}
				// showDebug("Innertext:"+tds.get(iSrcCol).getAttribute("innerText"));//debug

				int flg = 0;
				String type = "";
				int calc = 0;
				int dummy = 0;
				for (int dumb = 0; dummy < iSrcCol; dummy++) {
					List<DOMElement> tds1 = (List<DOMElement>) tds.get(
						dumb).getElementsByTagName(
						"td");
					// showDebug(tds1.size());
					calc += tds1.size() + 1;
					if (tds1.size() > 0)
						dumb += tds1.size();
					else
						dumb++;

				}
				// showDebug("Calc:"+calc);
				iSrcCol = calc;
				String innerHtml = tds.get(
					iSrcCol).getAttribute(
					"innerHtml");
				// showDebug("Outside:"+tds.get(iSrcCol).getAttribute("innerHtml"));
				if (innerHtml.toLowerCase().contains(
					"<select")) {
					type = "select";
				} else if (innerHtml.toLowerCase().contains(
					"<input")) {
					type = "input";
				}
				// showDebug("Type:"+type);
				if (type != "" && tds.size() > iSrcCol) {
					// showDebug("Entered object case"+tds.get(iSrcCol).getAttribute("innerHtml")
					// );//debug
					List<DOMElement> childitems;
					childitems = tds.get(
						iSrcCol).getElementsByTagName(
						type);
					// showDebug("Size:"+childitems.size());
					if (childitems.size() > 0) {
						String test = "";
						if (type == "input") {

							test = childitems.get(
								0).getAttribute(
								"type");
							if (test.equalsIgnoreCase("hidden")) {
								type = "";
							} else {
								test = childitems.get(
									0).getAttribute(
									"value");
								// showDebug("value:"+test);
							}
						} else {
							String rs[] = web.selectBox(
								docpath + form + "/web:select[@name='" + childitems.get(
									0).getAttribute(
									"name") + "']").getSelectedText();
							// showDebug("Value from select Box:"+rs[0]);//debug
							test = rs[0];
						}
						if (SearchText.equals(test))
							flg = 1;
					}
				}

				// showDebug("Source Col Text:\n"+tds.get(iSrcCol).getAttribute("innerText"));
				String comparetext = tds.get(
					iSrcCol).getAttribute(
					"innerText");
				if ((tds.size() > iSrcCol && comparetext != null && SearchText.equals(comparetext.trim())) || flg == 1) {
					// showDebug("Search Found in row # "+iStart);//debug
					this.sop("Search Found");
					String tag = "", attrib = "", attribval = "";
					tags = getObjectType(
						TargetObjType,
						true);

					tag = tags[0];
					attrib = tags[1];
					attribval = tags[2];
					calc = 0;
					iTargetCol = iOrgTargetCol;
					for (int dumb = 0; dumb < iTargetCol; dumb++) {
						List<DOMElement> tds1 = (List<DOMElement>) tds.get(
							dumb).getElementsByTagName(
							"td");
						calc += tds1.size();
						// showDebug(tds1.size());
						if (tds1.size() > 0) {
							dumb += tds1.size();
						}
					}
					iTargetCol = calc + iTargetCol;
					// showDebug("Target:"+iTargetCol);
					// showDebug("Tag:"+tag+" attrib"+ attrib +
					// " attribval"+attribval);//debug
					List<DOMElement> items = (List<DOMElement>) tds.get(
						iTargetCol).getElementsByTagName(
						tag);
					// showDebug("Target Column Text:\n"+tds.get(iTargetCol).getAttribute("innerHtml"));
					int checkloc = -1, ind = 0;
					int objectfound = 0;
					this.sop("Items: " + items.size() + " Tag:" + tag);// debug
					for (ind = 0; ind < items.size(); ind++) {
						// showDebug(items.get(ind).getAttribute(attrib));//debug
						if (!tag.equalsIgnoreCase("input")
								|| (tag.equalsIgnoreCase("input") && ((DOMElement) items.get(ind)).getAttribute(
									attrib).equals(
									attribval))) {
							this.sop("true condition");// debug
							checkloc++;
						}
						if (objloc == checkloc) {
							objectfound = 1;
							break;
						}

					}
					this.sop("Index value:" + ind);// debug
					if (objectfound == 1) {

						this.sop("Object Found");
						retObj = items.get(ind);
						searchSuccess = true;
						break;

					} else {
						this.sop("Object is not found");
					}

				}
			}

		} else {
			this.sop("Table not found");
		}
	}

	public String handleDialog(String action) throws Exception // WebDomService
																// web
	{
		String message = "";

		if (web.confirmDialog("/web:dialog_confirm[@text='*' and @index='0']").exists()) {
			message = web.confirmDialog("/web:dialog_confirm[@text='*' and @index='0']").getAttribute("text");

			if (action.equalsIgnoreCase("true") || action.equalsIgnoreCase("ok"))
				web.confirmDialog("/web:dialog_confirm[@text='*' and @index='0']").clickOk();
			else
				web.confirmDialog("/web:dialog_confirm[@text='*' and @index='0']").clickCancel();

		}else if (web.alertDialog("/web:dialog_alert[@text='*' and @index='0']").exists()) {
				message = web.alertDialog("/web:dialog_alert[@text='*' and @index='0']").getAttribute("text");
				web.alertDialog("/web:dialog_alert[@text='*' and @index='0']").clickOk();

		}else if (web.promptDialog("/web:dialog_prompt[@text='*' and @index='0']").exists()) {
			
				message = web.promptDialog("/web:dialog_prompt[@text='*' and @index='0']").getAttribute("text");
			// if(action)
			// web.promptDialog("/web:dialog_prompt[@text='*' and @index='0']").clickOk();
			// else
			// web.promptDialog("/web:dialog_prompt[@text='*' and @index='0']").clickCancel();

		} else if (web.dialog("/web:dialog[@text='*' and @index='0']").exists()) {
			message = web.dialog("/web:dialog[@text='*' and @index='0']").getAttribute("text");

			if (action.equalsIgnoreCase("true") || action.equalsIgnoreCase("ok"))
				(web.dialog("/web:dialog[@text='*' and @index='0']")).clickButton(1);
			else
				web.dialog("/web:dialog[@text='*' and @index='0']").clickButton(2);

		} else {
			message = "Invalid Dialog Type";
		}

		return message;

	}

	public void webLogout() throws Exception {

		webClickImage("Logout");
		
		Thread.sleep(10000);
	}

	public void closeForms() throws Exception {
		// forms.close();
		String activeWin = getActiveFormWindowName();

		while (activeWin != null && !"".equals(activeWin) && !"NAVIGATOR".equals(activeWin)) {
			this.sop("Wind Name:" + activeWin);
			if (activeWin != null && !"".equals(activeWin)) {
				forms.window(
					"//forms:window[(@name='" + activeWin + "')]").selectMenu(
					"File|Close Form");
			}

			think(2);

			if (forms.alertDialog(
				"//forms:alertDialog").exists()) {
				
				forms.alertDialog(
					"//forms:alertDialog").clickYes();
			}

			activeWin = getActiveFormWindowName();
		}
	}

	public void closeForm() throws Exception {

		closeForm(null);
	}

	public void closeForm(String windowName) throws Exception {
		delay(10000);

		if (windowName != null && !"".equals(windowName)) {
			forms.window(				"//forms:window[(@name='" + windowName + "')]").close();
			
		} else {
			windowName = getActiveFormWindowName();
			if (windowName != null && !"".equals(windowName)) {
				forms.window(
					"//forms:window[(@name='" + windowName + "')]").close();
			} else {
				reportFailure("GENLIB: closeForm:Could not find active window");
			}
		}
		if (forms.alertDialog(	"//forms:alertDialog").exists()) {
			forms.alertDialog(	"//forms:alertDialog").clickYes();
		}
	}

	public String getActiveFormWindowName() throws Exception {
		String winName = "";
		
		AbstractWindow[] wins = forms.getAllOpenWindows();

		for (AbstractWindow win : wins) {
			if (win.isActive()) {
		
				winName = win.getName();
				
				this.sop("GENLIB: getActiveFormWindowName: Current window name ="+ winName);
				break;
			}
		}
		return winName;
	}

	public void navigateToHome() throws Exception {

		webClickImage("Home");

	}

	public void formMenuSelect(String menuPath) throws Exception {
		String formName = null;
		// Get the active form window

		AbstractWindow[] wins = forms.getAllOpenWindows();

		for (AbstractWindow win : wins) {
			if (win.isActive()) {
				formName = win.getName();
				break;
			}
		}

		if (formName == null) {
			throw new Exception("No active form is found.");
		}

		forms.window("//forms:window[(@name='" + formName + "')]").selectMenu(menuPath);

	}

	public void webClickDynamicLink(String linkName) throws Exception {
		webClickLink(linkName);
	}

	public void clickFlexButton(String button) throws Exception {
		if (button.equalsIgnoreCase("OK"))
			forms.flexWindow(
				"//forms:flexWindow").clickOk();
		else if (button.equalsIgnoreCase("cancel"))
			forms.flexWindow(
				"//forms:flexWindow").clickCancel();
		else if (button.equalsIgnoreCase("clear"))
			forms.flexWindow(
				"//forms:flexWindow").clickClear();
		else if (button.equalsIgnoreCase("help"))
			forms.flexWindow(
				"//forms:flexWindow").clickHelp();

	}

	public void saveDialog(String location) throws Exception {

		think(20);
		//IE 8 code
		/*if (web.dialog("/web:dialog_unknown[@text='Do you want to open or save this file?' and @index='0']").exists()) {
			web.dialog("/web:dialog_unknown[@text='Do you want to open or save this file?' and @index='0']").clickButton(1);
		}

		web.dialog("/web:dialog_unknown[@text='Save &in:' and @index='0']").waitFor(SYNCTIME);

		web.dialog("/web:dialog_unknown[@text='Save &in:' and @index='0']").setText(0,location);

		web.dialog("/web:dialog_unknown[@text='Save &in:' and @index='0']").clickButton(0);

		think(10);
		
		// and @index='0'
		if (web.dialog("/web:dialog_unknown[@text='Download*omplete' and @winClass='##32770' and @index='0']").exists()) {
			web.dialog(	"/web:dialog_unknown[@text='Download*omplete' and @winClass='##32770']").clickButton(2);
		}*/
		
		
		//IE9 Browser	
		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		String windowTitle = web.window(windowPath).getAttribute("title");
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
        if (web.notificationBar("/web:window[@index='"+currentWindowIndex+"' or @title='*']").exists()) 
        {
            web.notificationBar("/web:window[@index='"+currentWindowIndex+"' or @title='*']").selectOptionBy("Save", "Save as");
        }

        web.dialog("/web:dialog_unknown[@text='Save As' and @index='0']").waitFor(SYNCTIME);
        web.dialog("/web:dialog_unknown[@text='Save As' and @index='0']").setText(0,location);
        web.dialog("/web:dialog_unknown[@text='Save As' and @index='0']").clickButton(0);

        think(10);
        
        if (web.notificationBar("/web:window[@index='"+currentWindowIndex+"' or @title='*']").exists()) {
            web.notificationBar("/web:window[@index='"+currentWindowIndex+"' or @title='*']").clickButton("Close");
        }

	}

	/*public void setFormsText(String attrb, String value) throws Exception {
		
		forms.textField("//forms:textField[(@name='" + attrb + "')]").setText(value);
	}*/

	public void setFormsText(String editLogicalName, String value) throws Exception {
		
		String valueToset = "";
		String valueToSelect = "";
		
		// Prepare xPath
		String xpath = "//forms:textField[(@name='" + editLogicalName + "')]";

		if (editLogicalName.contains("name=")) {
			String logicName[] = editLogicalName.split("=");
			xpath = "//forms:textField[(@name=" + logicName[1] + ")]";
		}
		this.sop("Edit Field Xpath is " + xpath);

		// Preparing Data for setting & Selecting the value
		if (value.contains("|")) {
			String tempData[] = value.split("\\|");
			valueToset = tempData[0];
		}else{
			valueToset = value;
		}
		
		this.sop("Value to Set in the text field");
		
		// Setting Text And Opening Dialog
		forms.textField(xpath).setText(valueToset);
		
		think(2);
		
		this.sop("visibility :"+forms.textField(xpath).isVisible());
		this.sop("Showing :"+forms.textField(xpath).isShowing());
		
		if(forms.textField(xpath).isVisible()){
			forms.textField(xpath).invokeSoftKey("NEXT_FIELD");
		}
		
		
		//forms.textField(xpath).openDialog();

		// Wait for some time for opening LOV
		think(4);
		
		if (forms.choiceBox("//forms:choiceBox").exists()) {
			
			forms.choiceBox("//forms:choiceBox").clickButton("Yes");
		}

		if (forms.listOfValues("//forms:listOfValues").exists()) {
			
			try {
				
				// Finding the value
				forms.listOfValues("//forms:listOfValues").find(valueToset);
				
				// Selecting the Value
				if (value.contains("|")) {
					forms.listOfValues("//forms:listOfValues").select(value);
					
				}else{
					
					if (forms.listOfValues("//forms:listOfValues").exists()) {
						
						int itemCount = forms.listOfValues("//forms:listOfValues").getItemCount();
						
						this.sop("Item Count :"+itemCount);
						
						for (int i = 1; i <= itemCount; i++) {
							
							String[] temp = forms.listOfValues("//forms:listOfValues").getText(i).split("\\|");

							this.sop(" Values in the list box at index "+i+" is :" + Arrays.asList(value).toString());
							
							if (temp[0].equals(value)) {
								
									forms.listOfValues("//forms:listOfValues").choose(i);
									break;
							}
						}
					}
				}
				
			}catch (Exception e) {
				System.out.println("Entered Eception While Selecting Value in FORMSELECTLOV");
			}
			
		}
	}
	
	
	public void formsSelectColor(String nameAttrb, String color) throws Exception {
		
		forms.textField("//forms:textField[(@name='" + nameAttrb + "')]").openDialog();
		
		if (color.equals("NOFILL")) {
			forms.button("//forms:button[(@name='COLOR_CELL1_0')]").click();
		}

		if (color.equals("RED")) {
			forms.button("//forms:button[(@name='COLOR_CELL2_0')]").click();
		}
		if (color.equals("ORANGE")) {
			forms.button("//forms:button[(@name='COLOR_CELL5_0')]").click();
		}

		if (color.equals("GREEN")) {
			forms.button("//forms:button[(@name='COLOR_CELL13_0')]").click();
		}
		if (color.equals("LIGHTGREEN")) {
			forms.button("//forms:button[(@name='COLOR_CELL11_0')]").click();
		}
		if (color.equals("MEDIUMGREEN")) {
			forms.button("//forms:button[(@name='COLOR_CELL12_0')]").click();
		}

		if (color.equals("BLUE")) {
			forms.button("//forms:button[(@name='COLOR_CELL10_0')]").click();
		}
		if (color.equals("LIGHTBLUE")) {
			forms.button("//forms:button[(@name='COLOR_CELL8_0')]").click();
		}
		if (color.equals("MEDIUMBLUE")) {
			forms.button("//forms:button[(@name='COLOR_CELL9_0')]").click();
		}

		if (color.equals("BROWN")) {
			forms.button("//forms:button[(@name='COLOR_CELL16_0')]").click();
		}
		if (color.equals("MEDIUMBROWN")) {
			forms.button("//forms:button[(@name='COLOR_CELL4_0')]").click();
		}
		if (color.equals("LIGHTBROWN")) {
			forms.button("//forms:button[(@name='COLOR_CELL3_0')]").click();
		}

	}

	
	public boolean setPurchasingPeriod(String[] period, String[] status) throws Exception {
		for (int pointer = 0; pointer < period.length; pointer++) {
			web.textBox(
				"/web:window[@title='Control Purchasing Periods']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName']/web:input_text[@id='SearchPeriodName' or @name='SearchPeriodName']").setText(
				period[pointer]);
			web.button(
				"/web:window[@title='Control Purchasing Periods']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' ]/web:button[@value='Go' and @index='3']").click();
			if (web.exists("/web:window[@title='Control Purchasing Periods']/web:document[@index='0']/web:form[@index='0']/web:select[(@id='PurchPrdTableResultsRN:N:0' or @name='PurchPrdTableResultsRN:N:0' ) and multiple mod 'False']") == true) {
				web.selectBox(
					"{{obj.VRPURPD001.Control Purchasing Periods_PurchPrdTableResultsRN_O_0}}").selectOptionByText(
					status[pointer]);

			}
		}
		web.button(
			"/web:window[@title='Control Purchasing Periods']/web:document[@index='0']/web:form[@index='0']/web:button[@value='Save' or @location='1']").click();
		web.window(
			"/web:window[@title='Control Purchasing Periods']/web:document[@index='0']").waitForPage(
			null);
		// web.verifyText("","Your changes have been saved.","",
		// "PassIfPresent", "");
		webVerifyText("Your changes have been saved");
		// getScript("GenLib").callFunction("webVerifyText","Your changes have been saved.").toString();

		return true;
	}

	public boolean openInventoryPeriod(String sPeriod) throws Exception {

		String[] period = sPeriod.split(",");

		boolean rvalue = false;
		for (int pointer = 0; pointer < period.length; pointer++) {
			if (period[pointer] != "") {
				this.sop("Period" + pointer + " :" + period[pointer]);

				forms.textField("//forms:textField[(@name='PERIODS_STATUS_0')]").setFocus();
				forms.window("//forms:window[(@name='PERIODS')]").activate(	true);

				forms.textField(
					"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setFocus();
				think(1);
				// getScript("palib").callFunction("EnterQuery");
				this.EnterQuery();

				think(1);
				forms.textField(
					"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setFocus();
				forms.textField(
					"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setText(
					period[pointer]);

				// getScript("palib").callFunction("RunQuery");
				this.RunQuery();
				
				if (GetMessage(W_STATUS_BAR,P_REQUEST_NO).equalsIgnoreCase(""))
					flag = 1;
				else {
					forms.window(
						"//forms:window[(@name='PERIODS')]").activate(
						true);
					forms.textField(
						"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setFocus();
					forms.textField(
						"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setText(
						period[pointer]);
					// getScript("palib").callFunction("RunQuery");
					this.RunQuery();
					
					if (GetMessage(W_STATUS_BAR,P_REQUEST_NO).equalsIgnoreCase(""))
						flag = 1;
					else {
						// forms.window("{{obj.INVTTGPM101.Inventory Accounting Periods (VS)}}").activate(true);
						forms.textField(
							"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setFocus();
						forms.textField(
							"//forms:textField[(@name='PERIODS_PERIOD_NAME_0')]").setText(
							period[pointer]);
						// getScript("palib").callFunction("RunQuery");
						this.RunQuery();
						if (GetMessage(W_STATUS_BAR,P_REQUEST_NO).equalsIgnoreCase(""))
							flag = 1;
					}
				}

				String xx = GetMessage(W_STATUS_BAR,P_REQUEST_NO);
				if (!xx.equalsIgnoreCase("")) {
					// getScript("palib").callFunction("CancelQuery");
					this.CancelQuery();
				}
			}

			this.sop("Getting Perioed Status");
			forms.textField(
				"//forms:textField[(@name='PERIODS_STATUS_0')]").setFocus();
			String PeriodStatus = forms.textField(
				"//forms:textField[(@name='PERIODS_STATUS_0')]").getText();

			if ((flag == 1) && (PeriodStatus.equalsIgnoreCase("Open"))) {
				System.out.println(period[pointer] + " Period is found and Open");
			} else if ((flag == 1) && (PeriodStatus.equalsIgnoreCase("Future"))) {
				// forms.window("{{obj.INVTTGPM101.Inventory Accounting Periods (VS)}}").activate(true);
				forms.textField(
					"//forms:textField[(@name='PERIODS_STATUS_0')]").setFocus();
				forms.button(
					"//forms:button[(@label='Change Status...')]").click();
				// Caution
				// forms.choiceBox("//forms:choiceBox").activate(true);
				forms.choiceBox(
					"//forms:choiceBox").clickButton(
					"OK");
				// Oracle Applications - Vision Corporation
				// forms.window("{{obj.APPSALL.Oracle Applications - Vision Corporation}}").activate(true);
				forms.window(
					"//forms:window[(@enabled='true')]").clickToolBarButton(
					"Save");
				System.out.println(period[pointer] + " Period is found and Opened from Future State");
				rvalue = true;
			} else {
				// EBSLibrary.addPassFailResult("FAIL","The required Period "+ddt.getValue(INVTTGPM101,"Sheet1",
				// "Period")+" is not found");
				rvalue = false;
			}

			//info("Completed First Period.");

		}
		return rvalue;
	}

	
	/**
	 * GetMessage
	 * @param Source
	 * @param Request
	 * @return
	 * @throws Exception
	 */
	public String GetMessage(int Source, int Request) throws Exception{
		
		
		String Message="";
		int RequestID=0; 
		int TimeOut;
		String CurrentWindow,ReqID;
		//CurrentWindow = GUI_get_window();
		
		TimeOut = 60;
		
		if(Source == -7001)
		{
			//set_window("Decision", TimeOut);
			 Message=forms.textField("//forms:textField[(@name='Request submitted')]").getAttribute("value"); 
			 RequestID = 1*Integer.valueOf(Message.substring(Message.indexOf("=")+2-1));
		}
		else if(Source == -7002)
		{
			//set_window("Note", TimeOut);
			Message=forms.textField("//forms:textField[(@name='NOTE_MESSAGE')]").getAttribute("value"); 
			 RequestID = 1*Integer.valueOf(Message.substring(Message.indexOf("is")+2-1)); 
			
			
		}
		else if(Source == -7003)
		{
				
			think(20);	
			Message=forms.getStatusBarMessage(); 
			
			if(Message.equalsIgnoreCase("null")){
				Message = "";
			}
			
			//else if(Message.length()>1)RequestID = 1*Integer.valueOf(Message.substring(Message.indexOf("=")+1-1)); 
			 
			 
		}
		else
		{
			this.sop("Invalid Source - GetMessage()"); 
			 /*Not found:set_window(CurrentWindow)*/; 
			 return("-10001"); 
		}
		
		if(Request == -7004)
		{
			this.sop(Message);
			if(Message.length()>1)
				RequestID = Integer.valueOf(Message.substring((Message.indexOf("=")+1), Message.length()-1).trim());
			
		      ReqID=Integer.toString(RequestID);
			 return(ReqID);
		}
		else if(Request == -7005) 
		{
			
			return(Message);
		}else if(Request == -7007) 
		{
			
			return(Message);
		}
		else
		{
			this.sop("Invalid Request Option - GetMessage()");
			
			return("-10001");
		}

		
	}
	
	
	
	public void setPayablePeriods(String Ledger, String OU, String[] Period, String[] Status) throws Exception {
		int W_STATUS_BAR = -7003;
		int P_REQUEST_NO = -7005;
		String v_Find = "false";
		if (forms.window(
			"//forms:window[(@title='Find Payables Periods')]").exists()) {
			forms.window(
				"//forms:window[(@title='Find Payables Periods')]").activate(
				true);
			if (!Ledger.equalsIgnoreCase("")) {
				forms.window(
					"//forms:window[(@title='Find Payables Periods')]").activate(
					true);
				forms.textField(
					"//forms:textField[(@name='PERIOD_QF_SET_OF_BOOKS_NAME_0')]").setFocus();
				forms.textField(
					"//forms:textField[(@name='PERIOD_QF_SET_OF_BOOKS_NAME_0')]").setText(
					Ledger);
				v_Find = "true";
			}
			if (!OU.equalsIgnoreCase("")) {
				forms.window(
					"//forms:window[(@title='Find Payables Periods')]").activate(
					true);
				forms.textField(
					"//forms:textField[(@name='PERIOD_QF_OPERATING_UNIT_NAME_0')]").setFocus();
				forms.textField(
					"//forms:textField[(@name='PERIOD_QF_OPERATING_UNIT_NAME_0')]").setText(
					OU);
				v_Find = "true";
			}
			if (v_Find.equalsIgnoreCase("true")) {
				forms.window(
					"//forms:window[(@title='Find Payables Periods')]").activate(
					true);
				forms.button(
					"//forms:button[(@label='Find')]").click();
			}
		}

		for (int pointer = 0; pointer < Period.length; pointer++) {

			forms.textField(
				"//forms:textField[(@name='GL_PERIOD_STATUSES_PERIOD_NAME_0')]").setFocus();
			getScript("palib").callFunction("EnterQuery");

			forms.textField(
				"//forms:textField[(@name='GL_PERIOD_STATUSES_PERIOD_NAME_0')]").setFocus();
			forms.textField(
				"//forms:textField[(@name='GL_PERIOD_STATUSES_PERIOD_NAME_0')]").setText(
				Period[pointer].toUpperCase());

			getScript(
				"palib").callFunction(
				"RunQuery");

			if (((String) getScript(
				"GenLib").callFunction(
				"GetMessage",
				W_STATUS_BAR,
				P_REQUEST_NO)).equalsIgnoreCase("")) {

				forms.textField(
					"//forms:textField[(@name='GL_PERIOD_STATUSES_DSP_DISPLAYED_FIELD_0')]").setFocus();
				String CurrStatus = forms.textField(
					"//forms:textField[(@name='GL_PERIOD_STATUSES_DSP_DISPLAYED_FIELD_0')]").getText();
				if (!CurrStatus.equalsIgnoreCase(Status[pointer])) {

					forms.textField(
						"//forms:textField[(@name='GL_PERIOD_STATUSES_DSP_DISPLAYED_FIELD_0')]").setFocus();
					forms.textField(
						"//forms:textField[(@name='GL_PERIOD_STATUSES_DSP_DISPLAYED_FIELD_0')]").setText(
						Status[pointer]);

					forms.window(
						"//forms:window[(@title='Control Payables Periods')]").clickToolBarButton(
						"Save");

					System.out.println("The Period  " + Period + "' is Opened Sucessfully");
				} else
					System.out.println("The Period  " + Period + "' is Already Open");

			} else {

				// getScript("palib").callFunction("CancelQuery");
				this.CancelQuery();

			}
		}
		forms.window(
			"//forms:window[(@name='GL_PERIOD_STATUSES')]").close();
		forms.button(
			"//forms:button[(@name='NAV_CONTROLS_COLLAPSE_ALL_0')]").click();

	}

	public void RunQuery() throws Exception {

		if (forms.getStatusBarMessage().equalsIgnoreCase(
			"Enter a query;  press Ctrl+F11 to execute, F4 to cancel.")) {

			// String strkey="{F11}";
			// String
			// VBSFilePath="O:\\EBS QA\\WR2OATS\\OATS_Script_manual\\VBS\\Execute.vbs";
			/* Run query */
			forms.window(
				"//forms:window[(@enabled='true')]").setFocus();

			boolean b = forms.window(
				"//forms:window[(@enabled='true')]").isMenuEnabled(
				"View|Query By Example|Run");
			if (b) {
				forms.window(
					"//forms:window[(@enabled='true')]").selectMenu(
					"View|Query By Example|Run");
			} else {
				forms.window(
					"//forms:window[(@enabled='true')]").activate(
					true);

				forms.window(
					"//forms:window[(@enabled='true')]").invokeSoftKey(
					"EXECUTE_QUERY");

			}

			// Process P =Runtime.getRuntime().exec("wscript " + VBSFilePath +
			// " KeyPress " + strkey);

		} else {
			System.out.println("Forms are not ready to run query");
		}

	}

	public void EnterQuery() throws Exception {

		/* Enter query */
		forms.window(
			"//forms:window[(@enabled='true')]").setFocus();
		boolean b = forms.window(
			"//forms:window[(@enabled='true')]").isMenuEnabled(
			"View|Query By Example|Enter");
		if (b) {
			forms.window(
				"//forms:window[(@enabled='true')]").selectMenu(
				"View|Query By Example|Enter");
		} else {
			forms.window(
				"//forms:window[(@enabled='true')]").activate(
				true);
			forms.window(
				"//forms:window[(@enabled='true')]").invokeSoftKey(
				"ENTER_QUERY");

		}
		if (forms.getStatusBarMessage().equalsIgnoreCase(
			"Enter a query;  press Ctrl+F11 to execute, F4 to cancel.")) {
			System.out.println("Forms are ready to run query");
		} else {
			System.out.println("Forms enter query process in not successfull");
		}

	}

	/**
	 * Cancels the query
	 * 
	 * @param Win_Name
	 * @throws Exception
	 */

	public void CancelQuery() throws Exception {

		String StatusMsg = forms.getStatusBarMessage();
		if (StatusMsg.equalsIgnoreCase("Enter a query;  press Ctrl+F11 to execute, F4 to cancel.")
				|| StatusMsg.equalsIgnoreCase("FRM-40301: Query caused no records to be retrieved. Re-enter.")) {
			/* Enter query */
			forms.window(
				"//forms:window[(@enabled='true')]").activate(
				true);
			boolean c = forms.window(
				"//forms:window[(@enabled='true')]").isMenuEnabled(
				"View|Query By Example|Cancel");
			if (c) {
				forms.window(
					"//forms:window[(@enabled='true')]").selectMenu(
					"View|Query By Example|Cancel");
			} else {
				forms.window(
					"//forms:window[(@enabled='true')]").activate(
					true);
				forms.window(
					"//forms:window[(@enabled='true')]").invokeSoftKey(
					"EXIT");
			}
		} else {
			System.out.println("Forms are not in a status to cancel query");
		}

	}

	/**
	 * Select List Item if "" is passsed for valueToselect , then it will select
	 * first item in the list
	 * 
	 * @param xPath
	 * @param valueToselect
	 * @throws AbstractScriptException
	 */
	public void webSelectListBoxWithXPath(String xPath, String valueToselect) throws Exception {
		
		System.out.println("valueToselect :"+valueToselect);
		if ("".equalsIgnoreCase(valueToselect.trim())) {
			web.selectBox(
				xPath).selectOptionByIndex(
				0);
		} else {
			// web.selectBox(xPath).selectOptionByValue(valueToselect.trim());
			web.selectBox(
				xPath).selectOptionByText(
				valueToselect.trim());
			//web.selectBox(xPath).selectOptionByValue(valueToselect.trim());
				

		}

		web.selectBox(
			xPath).fireEvent(
			"blur");
		web.selectBox(
			xPath).waitFor();
	}

	public void webSelectListBox(String selectLabel, String valueToselect) throws Exception {
		
		wEBLABELLIB.webSelectListBoxBasedOnLabel(selectLabel, valueToselect);
	}
	
	public void webSelectListBox1(String selectLabel, String valueToselect) throws Exception {
		
		System.out.println("****** Start of webSelectListBox:- Label : \"" + selectLabel + "\" valueToselect : \""
				+ valueToselect + "\"****************** ");

		boolean bFound = false;
		int selectIndex = 0;
		String windowPath = getCurrentWindowXPath();
		String selectDetails[] = selectLabel.split(",");
		String docPath = windowPath + "/web:document[@index='0']";
		int formIndex = 0;

		if (selectDetails.length > 1) {

			for (int index = 0; index < selectDetails.length; index++) {

				if (selectDetails[index].trim().startsWith(
					"document=")) {

					docPath = windowPath + "/web:document[@index='" + selectDetails[index].split("=")[1] + "']";

				} else if (index > 0 && !selectDetails[index].contains("document")
						&& !selectDetails[index].contains("form")) {

					selectIndex = Integer.parseInt(selectDetails[index]);

				} else if (selectDetails[index].trim().startsWith(
					"form=")) {

					formIndex = Integer.parseInt(selectDetails[index].split("=")[1]);
				}
			}
		}

		// Form Path
		String xPath = docPath + "/web:form[@index='" + formIndex + "']";
		this.sop("Form Path for ListBox :" + xPath);

		int similarLabelIndex = 0; // default value "0" i.e first select label in the page
		int requiredLabelIndex = 0;
		String labelName = "";

		if (selectDetails.length > 1) {
			labelName = selectDetails[0];
			requiredLabelIndex = selectIndex;
		} else {
			labelName = selectDetails[0];
		}

		for (int i = 1; web.exists(xPath + "/web:select[@index='" + i + "']"); i++) {

			DOMElement labels = web.selectBox(xPath + "/web:select[@index='" + i + "']").getPreviousSibling();

			if (labels == null) {// if select is available in td and lable is available in another td

				labels = web.selectBox(xPath + "/web:select[@index='" + i + "']").getParent().getPreviousSibling();
				
				String labelVal = labels.getAttribute("text");
				
				if(labelVal == ""){
					
				}
			}

			String labelVal = labels.getAttribute("text");

			if ((labelVal != null) && (labelVal.equals(labelName))) {

				if (similarLabelIndex == requiredLabelIndex) {
					this.sop("List Box XPath :" + xPath + "/web:select[(@index='" + i + "')and multiple mod 'False']");
					bFound = true;
					web.selectBox(
						xPath + "/web:select[(@index='" + i + "')and multiple mod 'False']").selectOptionByText(valueToselect.trim());
					web.selectBox(xPath + "/web:select[(@index='" + i + "')and multiple mod 'False']").fireEvent("blur");
					break;
				}

				similarLabelIndex++;
			}
		}

		if (!bFound) {

			reportFailure("No ListBox found with the label :" + labelName);
		}

		System.out.println("****************** End of webSelectListBox ****************** ");
	}

	public String getSysTime(String dateFormat, int... param) {
		String temp = "";
		String retString = "";
		int valuesCounter = 0, value = 0;
		Calendar cal = Calendar.getInstance();
		int valuesLength = param.length;
		String format = "";
		for (int i = 0; i < dateFormat.length(); i++) {
			if (valuesLength > valuesCounter)
				value = param[valuesCounter];
			else
				value = 0;

			if ('/' == dateFormat.charAt(i) || '-' == dateFormat.charAt(i) || ':' == dateFormat.charAt(i)
					|| '.' == dateFormat.charAt(i) || ' ' == dateFormat.charAt(i)) {
				if ("".equalsIgnoreCase(temp)) {
					System.out.println("Invalid Date format");
					return "";
				} else {
					format += getTime(
						cal,
						temp,
						value) + dateFormat.charAt(i);
					temp = "";
					valuesCounter++;
				}
			} else

				temp = temp + dateFormat.charAt(i);

		}
		if (!"".equalsIgnoreCase(temp))
			format += getTime(
				cal,
				temp,
				value);
		
		retString = new SimpleDateFormat(format).format(cal.getTime());

		return retString;
	}

	
	
	
	public void selectFile(String buttonName, String fileName) throws Exception
	{
		
		try{

            File file=new File(fileName);

            if(!file.exists())

                  fileName=new File(getVariables().get("CURR_SCRIPT_FOLDER")).getParent()+"\\Attachements\\"+fileName;

            this.sop("upload FileName"+fileName);

		}catch(Exception e){
   	   		System.out.println("File Not Found");
		}
		
		/* Document Path */
		String windowPath = getCurrentWindowXPath();

		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		int documentIndex = 0;
		int formIndex = 0;
		String fileBtnId = "";
		String fileBtnName = "";
		String formName = "";
		String expButtonName = "";

		String buttonPathDetails[] = buttonName.split(",");	

		// Get the Button Details passed from User
		for (int index = 0; index < buttonPathDetails.length; index++) {

			if (buttonPathDetails[index].trim().startsWith("document=")) {

				documentIndex = Integer.parseInt(buttonPathDetails[index].split("=")[1]);

			} else if (buttonPathDetails[index].trim().startsWith("form=")) 
			{
				String form = buttonPathDetails[index].split("=")[1];
				
				formIndex = Integer.parseInt(buttonPathDetails[index].split("=")[1]);
			}
			else if (buttonPathDetails[index].trim().startsWith("id=")) 
			{
				fileBtnId = buttonPathDetails[index].split("=")[1];
			}
			else if (buttonPathDetails[index].trim().startsWith("name=")) 
			{
				fileBtnName = buttonPathDetails[index].split("=")[1];
			}
		}

		this.sop("****** Attachment Button Details ********************* ");
		this.sop("Attach Button Id: \"" + fileBtnId + "Attach Button Name: \"" + fileBtnName + 
			"\"  Document Index: \""+ documentIndex + "\"  Form Index: \"" + formIndex + "\""+"\"  Form Name: \"" + formName + "\"");
		
		// Document Path
		String docPath = windowPath + "/web:document[@index='" + documentIndex + "']";
		
		String formPath = "";
		if(formIndex < 0){
			formPath = docPath+"/web:form[@name='" + formName + "']";
		}else{
			formPath = docPath+"/web:form[@index='" + formIndex + "']";
		}

		this.sop("Document Path :" + docPath);
      
        // XPath for Tool
		
		String xpath = "";
		
		if(!"".equalsIgnoreCase(fileBtnId) && !"".equalsIgnoreCase(fileBtnId)){
			
			xpath=formPath+ "/web:input_file[@id='"+fileBtnId+"' or @name='"+fileBtnName+"']";
			
		}else if(!"".equalsIgnoreCase(fileBtnName)){
			
			xpath=formPath+ "/web:input_file[@name='"+fileBtnName+"']";
			
		}else if("".equalsIgnoreCase(fileBtnId) && "".equalsIgnoreCase(fileBtnId)){
			
			xpath=formPath+ "/web:input_file[@id='"+fileBtnId+"']";
			
		}

	  // Click on browe button or text field
      if(web.element(xpath).exists(20, TimeUnit.SECONDS)){
          web.element(xpath).click();
      }
      
      // Wait for Upload file dialog to open
      delay(10000);
		
      // Select file from upload dialog box
		if (web.exists("/web:dialog_unknown[@text='Look &in:' and @index='0']")) {
			web.dialog("/web:dialog_unknown[@text='Look &in:' and @index='0']").setText(0,fileName);
			web.dialog("/web:dialog_unknown[@text='Look &in:' and @index='0']").clickButton(0);
		} else
			throw new Exception("GENLIB.uploadFile: Upload File Dialog not found");
		
      
	}
	
	
	public void uploadFile(String fileName) throws Exception {
		
		boolean childWindow=false;
		
		 try{

             File file=new File(fileName);

             if(!file.exists())

                   fileName=new File(getVariables().get("CURR_SCRIPT_FOLDER")).getParent()+"\\Attachements\\"+fileName;

             this.sop("upload FileName"+fileName);

       }catch(Exception e){
    	   System.out.println("File Not Found");
       }
		
		
		// web.textBox(getCurrentDocPath()+"/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_file[@id='uploadFile_oafileUpload' or @name='uploadFile_oafileUpload']").click();

       String xpath=getCurrentDocPath()
       + "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_file[@id='*_oafileUpload' or @name='*_oafileUpload']";

       

     /* String childWindXpath=getCurrentDocPath()
       +"/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_file[@id='fileName_ID*' or @name='fileName']";
*/
       String childWindXpath=getCurrentDocPath()
       + "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_file[@id='file*' or @name='file*']";

       
       if(web.textBox(xpath).exists(20, TimeUnit.SECONDS)){

             web.textBox(xpath).click();

       }else if(web.textBox(childWindXpath).exists(20, TimeUnit.SECONDS))
       {     childWindow=true;
             web.textBox(childWindXpath).click();

       }else{
    	   System.out.println("Unable to click on browse button");
       }
       
		delay(10000);
		
		System.out.println("Dialog Box Exists :"+web.exists("/web:dialog_unknown[@text='Look &in:' and @index='0']"));
		
		if (web.exists("/web:dialog_unknown[@text='Look &in:' and @index='0']")) {
			web.dialog("/web:dialog_unknown[@text='Look &in:' and @index='0']").setText(0,fileName);
			web.dialog("/web:dialog_unknown[@text='Look &in:' and @index='0']").clickButton(0);
			
		}else if(web.exists("/web:dialog_unknown[@text='File &name:' and @index='0']")){// IE9 Change -- Upload File dialog Change
            web.dialog("/web:dialog_unknown[@text='File &name:' and @index='0']").setText(0,fileName);
            web.dialog("/web:dialog_unknown[@text='File &name:' and @index='0']").clickButton(0);
            
        } else
			throw new Exception("GENLIB.uploadFile: Upload File Dialog not found");
		
		
		if(childWindow)
        {     
			 String path=getCurrentDocPath()+ "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@value='Submit']";
             
			 if(web.button(path).exists()){     
                   web.button(path).click();
                   return;
             }

             path=getCurrentDocPath()+ "/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@value='Attach']";

             if(web.button(path).exists()){     
                   web.button(path).click();
                   return;
             }
         }
	}

	
	
	public List<DOMElement> getSelectBoxBasedOnAttributes(String attrbs) throws Exception {
		List<DOMElement> elements = null;
		String docPath = getCurrentDocPath();
		String arrAttrbs[] = attrbs.split(",");

		PropertyTestList pList = new PropertyTestList();
		for (int i = 0; i < arrAttrbs.length; i++) {
			String nameValues[] = arrAttrbs[i].split("=");
			// to remove @ if its there
			if (nameValues[0].startsWith("@"))
				nameValues[0] = nameValues[0].substring(1).trim();

			// to remove " / ' from value
			if (nameValues[1].startsWith("\"") || nameValues[1].startsWith("'"))
				nameValues[1] = nameValues[1].substring(1,nameValues[1].length() - 1).trim();

			this.sop("Attrib:" + nameValues[0]);
			this.sop("Value:" + nameValues[1]);
			pList.add(nameValues[0],nameValues[1]);
		}

		this.sop("Property List for ListBox :" + pList.toString());

		elements = web.document(docPath).getElementsByTagName("select",pList);
		//this.sop("number of elements:" +elements );
		
		
		DOMSelect ele;
		if (elements != null) {
			this.sop("Number of Selects:" + elements.size());
			 ele = (DOMSelect) elements.get(0);
		} else {
			this.sop("selects are not found");
		}
		
		return elements;
	}

	public String getCurrentDocPath() throws Exception {
		String winnIndex = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();
		this.sop("Page Title:" + title);

		String winPath = "/web:window[@index='" + winnIndex + "' or @title='" + title + "']";

		String docPath = winPath + "/web:document[@index='0']";
		return docPath;

	}

	public List<DOMElement> getListBoxBasedOnLabel(String label) throws Exception {
		
		String compType = "select";
		String altLabel[] = label.split(";");
		int loc = 0;
		if (altLabel.length > 1)
			loc = Integer.parseInt(altLabel[1]);

		List<DOMElement> list = null;
		String foundType = "td";
		String winnIndex = web.getFocusedWindow().getAttribute("index");
		String docPath = getCurrentDocPath();
		
		PropertyTestList pList = new PropertyTestList();
		pList.add("text",label);

		
		List<DOMElement> tds = web.document(docPath).getElementsByTagName("td",	pList);
		//List<DOMElement> ths = web.document(docPath).getElementsByTagName("th",	pList);
		List<DOMElement> fonts = null;
		String finalTDIndex = "";
		String finalTDxPath = "";
		boolean foundTD = false;
		if (tds != null && tds.size() > 0) {
			this.sop("Found in tds");
			if (tds.size() > loc) {
				finalTDIndex = tds.get(
					loc).getAttribute(
					"index");
				foundTD = true;
			}
		} else {
			this.sop("Not Found in tds");
			
			//List<DOMElement> ths = web.document(docPath).getElementsByTagName("th",	pList);
			
			//if(ths==null ||ths.size()==0)
			{
				fonts = web.document(docPath).getElementsByTagName("font",pList);
				if (fonts != null && fonts.size() > 0) 
				{
					this.sop("Found in fonts");
					foundType = "font";
					if (fonts.size() > loc) 
					{
						finalTDIndex = fonts.get(loc).getParent().getAttribute("index");
						foundTD = true;
					}	
				}	
			}

		}

		List<DOMElement> elements = null;
		if (foundTD) {
			finalTDxPath = docPath + "/web:td[@index='" + finalTDIndex + "']";
			elements = web.element(
				finalTDxPath).getElementsByTagName(
				compType);
		}
		return elements;

	}

	public void selectListMultiValues(String labelName, String options) throws Exception {
		
		List<DOMElement> list = null;
		if (labelName.contains("@label") ||  !labelName.contains("=")) {

			list = (List<DOMElement>) getListBoxBasedOnLabel(labelName);

		} else {

			list = getSelectBoxBasedOnAttributes(labelName);
		}

		int listlen = list.size();
		this.sop("List Length :"+listlen);
		
		if (listlen < 1) {

			reportFailure("Genlib.selectListMultiValues: No Selectbox found for criteria:" + labelName);
		}
		String arr[] = options.split(",");

		for (int elmntcount = 0; elmntcount < list.size(); elmntcount++) {
			
			DOMElement element = list.get(elmntcount);

			this.sop("Element name attribute value:" + element.getAttribute("name"));

			DOMSelect sel = (DOMSelect) element;
			
			this.sop("Number of Elements in the List:" + sel.getOptions().size());
			
			if (arr.length == 1){
				sel.multiSelectOptionByText(arr[0]);
				info("Selected Item is :"+arr[0]);
			}			
			else{
				sel.multiSelectOptionByText(arr);
				info("Selected Item are :"+Arrays.asList(arr).toString());
			}
				
			return;
		}

	}

	public void setEditValueBasedOnLabel(String labelName, String value) throws Exception {

		setValueBasedOnLabel(labelName,	"input",value);
	}

	public void setValueBasedOnLabel(String labelName, String compType, String valueToSet) throws Exception {

		String RTObject_Xpath = null;
		ArrayList<DOMElement> elements = new ArrayList<DOMElement>();

		String xPath = getCurrentDocPath() + "/web:span[@text='" + labelName + "']";
		this.sop(xPath);
		web.element(xPath).waitFor(200);

		DOMElement textElement = web.element(xPath);

		/* Get the Parent Item */
		elements = (ArrayList<DOMElement>) getCompsBasedOnLabel(labelName,	compType);

		// elements=(ArrayList<DOMElement>)
		// textElement.getParent().getParent().getElementsByTagName(compType);
		this.sop("Number of fields matching " + labelName + " : " + elements.size());

		DOMElement element = elements.get(0);

		if (compType.equalsIgnoreCase("select")) {
			RTObject_Xpath = getCurrentDocPath() + "/web:select[@id='" + element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.selectBox(
				RTObject_Xpath).selectOptionByText(
				valueToSet);
		}
		if (compType.equalsIgnoreCase("textArea")) {
			this.sop(element.getAttribute("id"));
			RTObject_Xpath = getCurrentDocPath() + "/web:textArea[@id='" + element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.textArea(
				RTObject_Xpath).setText(
				valueToSet);
		}
		if (compType.equalsIgnoreCase("input")) {
			RTObject_Xpath = getCurrentDocPath() + "/web:input_text[@id='" + element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.textBox(
				RTObject_Xpath).setText(
				valueToSet);
		}
		if (compType.equalsIgnoreCase("checkbox")) {
			RTObject_Xpath = getCurrentDocPath() + "/web:input_checkbox[@id='" + element.getAttribute("id") + "']";
			this.sop("Xpath of " + compType + " is : " + RTObject_Xpath);
			web.checkBox(
				RTObject_Xpath).check(
				toBoolean(valueToSet));
		}
	}

	public void maximizeWindow(String title) throws Exception {

		if ((title != null) && !(title.equals(""))) {

			web.window(
				"/web:window[@title='" + title + "']").maximize();
		} else {

			web.getFocusedWindow().maximize();
		}

	}

	
	public String convertNumberToWords(int index){
		
		String numberToString = ""+index;
		
		switch(index){
		
		case 0:{
				numberToString = "1st ";
				break;
				}
			
		case 1:{
				numberToString = "2nd ";
				break;
			}
		case 2:{
				numberToString = "3rd ";
				break;
			}
		case 3:{
				numberToString = "4th ";
				break;
			}
		case 4:{
				numberToString = "5th ";
				break;
			}
		
		}
		
		return numberToString;
		
	}
	
	public String searchEmptyRow(String tableName, String colName) throws Exception {

		// Component Types
		String[] compTypes = {"IMG", "INPUT" };

		int rowNum = -1;

	//	initializeWebTable();

		String table[] = tableName.split(";");

		WebTable webTable = null;

		//Getting webtable Object
		if (table.length > 1) {

			webTable = new WebTable(table[0], toInt(table[1]));

		} else {

			webTable = new WebTable(table[0]);
		}
		
		// Getting Column Number
		int colNum = webTable.getColumnNumber(colName);
		
		// Getting Row Count
		int rowCount = webTable.getRowCount();
		
		// Row Start index
		int rowStartIndex = webTable.getRowStartIndex();
		
		// Actual Row Count
		rowCount = rowCount - rowStartIndex + 1;

		for (int i = 1; i < rowCount; i++) {

			// Cell Values
			HashMap<String, String> compValues = new HashMap<String, String>();

			for (int index = 0; index < compTypes.length; index++) {

				String compType = compTypes[index];
				this.sop("compType :" + compType);

				List<String> compList = webTable.getCellData(i,	colNum,	compType,"0");
				this.sop("compList Values :" + compList);

				if (compList.size() == 0) {
					compValues.put(compType,"");
				} else {
					compValues.put(compType,compList.get(0));
				}
			}

			if ("".equals(compValues.get("IMG")) || "".equals(compValues.get("INPUT"))) {
				rowNum = i;
				break;
			}

		}

		return "" + rowNum;
	}

}

	




























































