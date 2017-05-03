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

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	String windowName				= null;
	String docPath 					= null;
	String formPath 				= null;
	String tablePath 				= null;
	String tableAttribute 			= null;
	
	int rowCount					= 0;
	List<DOMElement> rowElements 	= new ArrayList<DOMElement>();
	
	String[] componentTypes 		= {"link","button","text","image"};
	String[] componentTags  		= {"a","button","span","img"};
	String[] componentAttributes  	= {"innerText,text","value,text","innertext","alt"};
	
	HashMap<String,String> components = new HashMap<String,String>();//Component Types, Component Tags
	HashMap<String,String> componentsWithAttributes = new HashMap<String,String>();
	@FunctionLibrary("GENLIB") lib.ebsqafwk.GENLIB gENLIB;
	@FunctionLibrary("WEBLABELLIB") lib.ebsqafwk.WEBLABELLIB wEBLABELLIB;
	
	
	public void initialize() throws Exception {
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}

	public void finish() throws Exception {
	}
	
	private HashMap<String,String> convertActionsToHashMap(String[] singleAction){
		
		HashMap<String,String> action = new HashMap<String,String>();
		
		//Keyword ,object ,caption ,logical name, outputvar, function Name, params
		int actionFieldsLength = singleAction.length;
		
		action.put("keyword", singleAction[0].toUpperCase());
		action.put("object", singleAction[1].toUpperCase());
		action.put("displayname", singleAction[2]); 
		action.put("logicalname", singleAction[3]);
		action.put("outputvar", singleAction[4]);
		action.put("funcname", singleAction[5]);
		
		if(!"".equals(singleAction[3])){
			
			boolean numberExists = singleAction[3].matches("\\d*");
			
			if(numberExists){
				action.put("displayname", singleAction[3]); 
			}
		}
			
		if(actionFieldsLength > 6){
			
			for(int index=1,actionIndex=6; actionIndex < actionFieldsLength; index++, actionIndex++){
				action.put("value"+index, singleAction[actionIndex]);
			}
		}
		
		return action;
	}
	
	
	private HashMap<String,String> convertPropertiesToHashMap(String cellProperties){
		
		HashMap<String,String> attributesMap = new HashMap<String,String>();
		
		String[] attributes = cellProperties.split("~~");
		//System.out.println("attributes:"+attributes.length);
		
		for(int index=0; index < attributes.length; index++){
			
			String[] attrWithValue = attributes[index].split(":::");
			
			//System.out.println("attrWithValue[0]:"+attrWithValue[0]);
			//System.out.println("attrWithValue[1]:"+attrWithValue[1]);
			attributesMap.put(attrWithValue[0], attrWithValue[1]);
		}
		
		return attributesMap;
	}
	
	public String tableactions(String tableName, List <String[]> actions, HashMap<String, String> searchColumns)throws Exception {
		
		return tableactions(tableName, searchColumns, actions);
	}

	public String tableactions(String tableName, HashMap<String, String> searchColumns, List <String[]> actions)throws Exception {

		System.out.println("***************** Start of tableactions with Search Columns ********************");
		
		getWebTableObject(tableName);
		
		int rowNumber = findRowNumber(searchColumns);
		
		rowNumber = rowNumber - 1;
		
		tableactions(tableName, rowNumber+"", actions);
		
		System.out.println("***************** End of tableactions with Search Columns ********************");
		
		return ""+rowNumber;//
	}
	
	public String  tableactions(String tableName, String rowNumber , List <String[]> actions)throws Exception {

		System.out.println("***************** Start of tableactions with Row Number ********************");
		
		//this.sop("rowNumber :"+rowNumber);
		
		int rowNum = toInt(rowNumber);
		
		
		
		for(int i=0; i<actions.size();i++){
			
			if(rowElements.size() == 0){
				getWebTableObject(tableName);
			}
			
			// Get the Actions
			String []singleAction = actions.get(i);
			
			// Convert to HashMap
			HashMap<String,String> action = convertActionsToHashMap(singleAction);
			
			if(action.get("keyword").equals("SETTEXT")){
				
				if(action.get("object").equals("EDIT")){ // Set TextBox
					
					//this.setEditValue(tableName, rowNum, action);
				}
				else if(action.get("object").equals("TEXTAREA")){ // Set TextArea
					
					//this.setTextArea(tableName, rowNum, action);
				}
				else{ 
					reportFailure("Invalid Action for SETTEXT"+action.get("object"));
				}
			}
			else if(action.get("keyword").equals("CLICK")){
				
				if(action.get("object").equals("IMAGE")){
					
					clickImage(tableName, rowNum, action);
					/*String logicalName = action.get("logicalname");
					
					if(logicalName.contains("imgname=")){
						String altTag = logicalName.split("=")[1];
						altTag = altTag.replaceAll("'", "").trim();
						info("altTag="+altTag);
						action.put("logicalname", altTag);
						
						//this.clickImageByName(tableName, rowNum, action);
					}else{
						//this.clickImage(tableName, rowNum, action);
					}*/
					
				}else if(action.get("object").equals("LINK")){
					
					clickLink(tableName, rowNum, action);
					
					/*String logicalName = action.get("logicalname");
					
					if(logicalName.contains("imgname=")){
						String altTag = logicalName.split("=")[1];
						altTag = altTag.replaceAll("'", "").trim();
						info("altTag="+altTag);
						action.put("logicalname", altTag);
						
						//this.clickLinkByName(tableName, rowNum, action);
						
					}else{
						//this.clickLink(tableName, rowNum, action);
					}*/
					
				}else if(action.get("object").equals("BUTTON")){
					
					this.clickButton(tableName, rowNum, action);
					
				}
				else
				{
					reportFailure("Invalid Action for Click "+action.get("object"));
				}
			}else if(action.get("keyword").equals("VERIFY")){
				
				if(action.get("object").equals("EDIT")){
					
					/*String editValue = this.getEditValue(tableName, rowNum, action);
					
					this.sop("Edit Value in Cell:"+editValue);
					
					if((editValue!=null)&&(compareStrings(action.get("value1"),editValue))){
						
						info("Actual Value \""+action.get("value1")+ "\" is matched with Expectd Value is \""+editValue+"\"");
						//info("EditBox value :"+editValue+" is as expected");
						
					}else{
						
						reportFailure("Expected Value:"+action.get("value1")+ " where as Actual Value is :"+editValue);
					}*/
				}else if(action.get("object").equals("TEXT")){
					
					//Expected Data
					String expected = action.get("value1");
					
					//Actual Data in the row
					List<String> compTypes =  new ArrayList<String>();
					compTypes.add("span");
					compTypes.add("innertext");
					compTypes.add("text");
					List<String> rowData = new ArrayList<String>();
					rowData = getRowData(rowNum,compTypes);
					
					System.out.println("Row Data at Row Number "+(rowNum+1)+" is :"+rowData.toString());
					
					/* Verification of Data in the Specified Row*/
					boolean itemAvailable = false;
					for(int cellItemIndex=0; cellItemIndex<rowData.size();cellItemIndex++){
						if(compareStrings(expected,rowData.get(cellItemIndex))){
							itemAvailable = true;
							break;
						}
					}
					
					if(itemAvailable){
						info("Expectd Value \""+expected+"\" is present in the Item Row data "+rowData.toString());
					}else{
						reportFailure("Expectd Value \""+expected+"\" is not present in the Item Row data "+rowData.toString());
					}
				}else if(action.get("object").equals("LINK")){
					
					//Expected Data
					String expected = action.get("value1");
					
					//Actual Data in the row
					List<String> compTypes =  new ArrayList<String>();
					compTypes.add("a");
					List<String> rowData = new ArrayList<String>();
					rowData = getRowData(rowNum,compTypes);
					
					System.out.println("Row Data at Row Number "+(rowNum+1)+" is :"+rowData.toString());
					
					/* Verification of Data in the Specified Row*/
					boolean itemAvailable = false;
					for(int cellItemIndex=0; cellItemIndex<rowData.size();cellItemIndex++){
						if(compareStrings(expected,rowData.get(cellItemIndex))){
							itemAvailable = true;
							break;
						}
					}
					
					if(itemAvailable){
						info("Expectd Value \""+expected+"\" is present in the Item Row data "+rowData.toString());
					}else{
						reportFailure("Expectd Value \""+expected+"\" is not present in the Item Row data "+rowData.toString());
					}
				}
				
			}else if(action.get("keyword").equals("FUNCTIONCALL")){
				
				if(action.get("object").equals("GENLIB")){
						
					if(action.get("funcname").equalsIgnoreCase("webVerifyTextBasedOnLabel")){
						
						wEBLABELLIB.webVerifyTextBasedOnLabel(rowElements.get(rowNum), action.get("displayname"), action.get("value1"));
						
					}else if(action.get("funcname").equalsIgnoreCase("webClickDynamicLink")){
						
						clickLink(rowNum, action.get("value1"));
						
					}else if(action.get("funcname").equalsIgnoreCase("webClickLink")){
						
						clickLink(rowNum, action.get("displayname"));
						
					}else if(action.get("funcname").equalsIgnoreCase("webSetTextBasedOnLabel")){
						
						wEBLABELLIB.webSetTextBasedOnLabel(rowElements.get(rowNum), action.get("displayname"), action.get("value1"));	
					}
					else
					{
						reportFailure("Invalid value for FUNCTIONCALL: GENLIB"+action.get("displayname"));
					}
				}else if(action.get("object").equals("CRMLIB")){
						
				}
				else
				{
					//System.err.println("Inside else");
					reportFailure("Invalid value for FUNCTIONCALL"+action.get("object"));
				}
			
			}
			
		}
		
		return "";
	}
	
	/**
	 *  Gets Table Object
	 * @param tableID
	 * 			-- It looks like "Select" or "Select;1" or "Select;1,document=0,form=0"
	 * @return
	 * @throws Exception
	 */
	public void getWebTableObject(String tableID) throws Exception {
		
		//System.out.println("webTable :"+webTable);
			
		int documentIndex = 0;
		int formIndex = 0;
		int tableIndex = 0;
		String listId = "";
		String listName = "";
		String tablePath = "";
		String expTableAttribute = "";
		String listPath = "";	

		String tableDetails[] = tableID.split(",");

		// Get the Table Details passed from User
		for (int index = 0; index <tableDetails.length; index++) {

			if (tableDetails[index].trim().startsWith("document=")) {

				documentIndex = Integer.parseInt(tableDetails[index].split("=")[1]);

			} else if (tableDetails[index].trim().startsWith("form=")) {

				formIndex = Integer.parseInt(tableDetails[index].split("=")[1]);
				
			} else if (tableDetails[index].toLowerCase().trim().startsWith("listid=")) {

				listId = tableDetails[index].split("=")[1];
				
			} else if (tableDetails[index].toLowerCase().trim().startsWith("listname=")) {

				listName = tableDetails[index].split("=")[1];
				
			} else if (tableDetails[index].toLowerCase().trim().startsWith("listpath=")) {

				String listDetails = tableDetails[index].split("=")[1];
				
				if(listDetails.equalsIgnoreCase("null")){
					listPath = null;
				}else{
					listPath = listDetails;
				}
				
				
			}else if (!tableDetails[index].contains("document=") && !tableDetails[index].contains("form=")) {

				String[] tableNameDetails = tableDetails[index].split(";");

				// Similar Table Index
				if (tableNameDetails.length > 1) {
					tableIndex = Integer.parseInt(tableNameDetails[1]);
				}

				// Table Name
				expTableAttribute = tableNameDetails[0];

			} 
		}	
			
		info("****** Table Details ********************* ");
		info("Table Attribute: \"" + expTableAttribute+ "\"  Table Index: \"" + tableIndex+ "\"  Document Index: \""
				+ documentIndex+ "\"  Form Index: \"" + formIndex + "\"");	
			
		if(listPath == null){
			//webTable = new WebTable(expTableName,tableIndex, documentIndex, formIndex,null,200);
			
		}else if("".equalsIgnoreCase(listPath)){
			
			String tableListPath = "";
			
			if(!"".equalsIgnoreCase(listId) && "".equalsIgnoreCase(listName)){
				tableListPath = "/web:select[(@id='"+listId+"') and multiple mod 'False']";
			}
			
			if("".equalsIgnoreCase(listId) && !"".equalsIgnoreCase(listName)){
				tableListPath = "/web:select[(@name='"+listName+"') and multiple mod 'False']";
			}
			
			initializeWebTable(expTableAttribute,tableIndex, documentIndex, formIndex,tableListPath,200);
		
		}else
		{
			initializeWebTable(expTableAttribute,tableIndex, documentIndex, formIndex,listPath,200);
		}	
			
	}
	
	public void initializeWebTable(String tableAttribute, int tableIndex, int documentIndex, int formIndex,String listPath, int syncTime) throws Exception {
		
		this.tableAttribute = tableAttribute;
		
		//this.tableIndex = Integer.toString(tableIndex);
		
		//this.listPath = listPath;
		
		/* Initialize Sync Time */
		//this.SYNCTIME = syncTime;
		
		/* Document Path */
		String windowPath = getCurrentWindowXPath();
		
		//System.out.println("windowPath :"+windowPath);
		/* Current Window XPath */
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
		/* Current Window Name*/
		windowName = web.window(windowPath).getAttribute("title");
		
		/* Doc Path */
		docPath = windowPath + "/web:document[@index='"+documentIndex+"']";
		
		/* Form Path*/
		formPath = docPath + "/web:form[@index='"+formIndex+"']";
		
		/* Table Path*/
		if(tableAttribute.contains("id=")){
			tablePath = docPath+"/web:table[@id='"+tableAttribute.split("=")[1]+"']";
		}else if(tableAttribute.contains("name=")){
			tablePath = docPath+"/web:table[@name='"+tableAttribute.split("=")[1]+"']";
		}else if(tableAttribute.contains("summary=")){
			tablePath = docPath+"/web:table[@summary='"+tableAttribute.split("=")[1]+"']";
		}
		
		web.table(tablePath).waitFor();
		
		System.out.println("tablePath :"+tablePath);
		
		// Moving Component Types & its attributes to HashMap
		for(int compTypeIndex=0; compTypeIndex < componentTypes.length; compTypeIndex++){
			components.put(componentTypes[compTypeIndex], componentTags[compTypeIndex]);
			componentsWithAttributes.put(componentTypes[compTypeIndex], componentAttributes[compTypeIndex]);
		}
		
		//Initialize Table
		getRowElements();
		
	}
	
	
	public List<DOMElement> getRowElements() throws Exception
	{
		List<DOMElement> tableChildren = web.table(tablePath).getChildren();
		
		info("Table Children Count :"+tableChildren.size());
		
		for(int tableChildrenIndex=0; tableChildrenIndex < tableChildren.size(); tableChildrenIndex++){
			
			String tagName = tableChildren.get(tableChildrenIndex).getTag();
			
			info("tagName :"+tagName);
			
			if(tagName != null && tagName.equalsIgnoreCase("tbody")){
				rowElements = tableChildren.get(tableChildrenIndex).getChildren();
				break;
			}
		}
		
		rowCount = rowElements.size();
		
		info("Row Count :"+rowElements.size());
		
		return rowElements;
	}
	
	/**
	 * Click the button when the button name is passed from component
	 * 
	 * @param tableName
	 * @param rowNumber
	 * @param actions
	 * @throws Exception
	 */
	public void clickButton(String tableName, int rowNumber, HashMap<String,String> actions) throws Exception{
		
		DOMElement buttonElement = null;
		
		String expectedButtonName = actions.get("displayname");
		
		buttonElement = getCellElement(rowNumber, "button", expectedButtonName,0);
		
		if(buttonElement != null){
			buttonElement.focus();
			buttonElement.click();
			buttonElement.fireEvent("blur");
			info("Clicked the button \""+expectedButtonName+"\" in the row :"+rowNumber);
		}else{
			reportFailure("Button \""+expectedButtonName+"\" is not available in the row :"+rowNumber);
		}
	}
	
	
	/**
	 * Click the Image when the alt image is passed from component
	 *  
	 * @param tableName
	 * @param rowNumber
	 * @param actions
	 * @throws Exception
	 */
	public void clickImage(String tableName, int rowNumber, HashMap<String,String> actions) throws Exception{
		
		DOMElement imageElement = null;
		
		String expectedImageName = actions.get("displayname");
		
		imageElement = getCellElement(rowNumber, "image", expectedImageName,0);
		
		if(imageElement != null){
			imageElement.focus();
			imageElement.click();
			info("Clicked the Image \""+expectedImageName+"\" in the row :"+rowNumber);
		}else{
			reportFailure("Image \""+imageElement+"\" is not available in the row :"+rowNumber);
		}
	}
	
	/**
	 * Click the Link when the button name is passed from component
	 *  
	 * @param tableName
	 * @param rowNumber
	 * @param actions
	 * @throws Exception
	 */
	public void clickLink(String tableName, int rowNumber, HashMap<String,String> actions) throws Exception{
		
		DOMElement linkElement = null;
		
		String expectedLinkName = actions.get("displayname");
		
		linkElement = getCellElement(rowNumber, "link", expectedLinkName,0);
		
		if(linkElement != null){
			linkElement.focus();
			linkElement.click();
			linkElement.fireEvent("blur");
			info("Clicked the link \""+expectedLinkName+"\" in the row :"+rowNumber);
		}else{
			reportFailure("Link \""+linkElement+"\" is not available in the row :"+rowNumber);
		}
	}
	
	/**
	 * Click the link when the link name is passed from test data
	 * 
	 * @param rowNumber
	 * @param expectedLinkValue
	 * @throws Exception
	 */
	public void clickLink(int rowNumber, String expectedLinkValue) throws Exception{
		
		DOMElement linkElement = null;
		
		linkElement = getCellElement(rowNumber, "link", expectedLinkValue,0);
		
		if(linkElement != null){
			linkElement.focus();
			linkElement.click();
			linkElement.fireEvent("blur");
		}else{
			reportFailure("Link \""+expectedLinkValue+"\" is not available in the row :"+rowNumber);
		}
		
	}
	
	
	
	public int findRowNumber(HashMap<String, String> searchColumns) throws Exception{
		
		this.info("*** Getting Row Number ***********");
		
		
		String colNames [] = getKeysFromHashMap(searchColumns);
		String values [] =getValuesFromHashMap(searchColumns);
		
		int rowNum = getRowNumber(colNames, values)+1;
		
		return rowNum;

	}
	
	
	/**
	 *  Get Row Number
	 *  
	 * @param colNames
	 * @param rowValues
	 * @return
	 * @throws Exception
	 */
	public int getRowNumber(String[] colNames, String[] rowValues) throws Exception{
		
		
		/* Initialize Variables*/
		int rowNum = -1;
		boolean rowExists = false;
		int listIndex = 0;
		boolean listboxExists = false;
		
		info("Expected Column Names : "+Arrays.asList(colNames).toString());
		info("Expected Row Values : "+Arrays.asList(rowValues).toString());
		
		System.out.println("****************** Start of Row Num ************************ ");
		
		int rowNumber = -1;
		boolean itemFound = false;
		
		/* Get Row Count*/
		
		int rowCount = this.rowCount;
		int rowStartIndex = 0;
		
		/* Navigate through each row for getting cell value 
		 * rowIndex start with "0" ()
		 * */
		for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
			
			List<String> rowData = new ArrayList<String>();
			
			rowData = getRowData(rowIndex);
			
			System.out.println("Row Data at Row Number "+(rowIndex+1)+" is :"+rowData.toString());
			
			/* Verification of Data in the Specified Row*/
			for(int rowValueIndex = 0; rowValueIndex < rowValues.length ; rowValueIndex++){
				
				boolean itemAvailable = false;
				for(int cellItemIndex=0; cellItemIndex<rowData.size();cellItemIndex++){
					if(compareStrings(rowValues[rowValueIndex],rowData.get(cellItemIndex))){
						itemAvailable = true;
						break;
					}
				}
				if(!itemAvailable)
				{
					break;
				}
				
				if(rowValueIndex == (rowValues.length - 1)){
					itemFound = true;
				}
				
			}
			
			/* If Item is not found , Comes out of the For and then return -1 as row number
			 * If Item found , then returns the RowNumber;
			 * */
			if(itemFound){
				rowNumber = rowIndex;
				break;
			}
		}
		
		info("Row Number in the Table is : "+(rowNumber+1));
		
		return rowNumber;
	}
	
	
	public List<String> getRowData(int rowNumber) throws Exception{
		
		//String[] componentTags  		= {"a","button"};
		//String[] componentAttributes  	= {"innerText,text","value,text"};
		
		List<String> cellData = new ArrayList<String>();
		
		for(int compIndex=0; compIndex < componentTags.length; compIndex++){
			
			// Get comp elements within specified row
			List<DOMElement> compElements = rowElements.get(rowNumber).getElementsByTagName(componentTags[compIndex]);
			
			for(int compTypeIndex=0; compTypeIndex < compElements.size();compTypeIndex++){
				
				
				// Get the comp attribute for each component within a row
				DOMElement tempCompElement = compElements.get(compTypeIndex);
				
				String[] compAttributes = componentAttributes[compIndex].split(",");
				
				// Get the Attribute list
				List<String> compAttributeValues = new ArrayList<String>();
				for(int attributeIndex=0; attributeIndex < compAttributes.length;  attributeIndex++){
					
					String compValue = tempCompElement.getAttribute(compAttributes[attributeIndex]);
					
					if(compValue != null){
						cellData.add(compValue);
					}
					
				}
				
			}
		}
		
		return cellData;
	}
	
	public List<String> getRowData(int rowNumber, List<String> componentTags) throws Exception{
		
		//String[] componentTags  		= {"a","button"};
		//String[] componentAttributes  	= {"innerText,text","value,text"};
		
		List<String> cellData = new ArrayList<String>();
		
		for(int compIndex=0; compIndex < componentTags.size(); compIndex++){
			
			// Get comp elements within specified row
			List<DOMElement> compElements = rowElements.get(rowNumber).getElementsByTagName(componentTags.get(compIndex));
			
			for(int compTypeIndex=0; compTypeIndex < compElements.size();compTypeIndex++){
				
				
				// Get the comp attribute for each component within a row
				DOMElement tempCompElement = compElements.get(compTypeIndex);
				
				String[] compAttributes = componentAttributes[compIndex].split(",");
				
				// Get the Attribute list
				List<String> compAttributeValues = new ArrayList<String>();
				for(int attributeIndex=0; attributeIndex < compAttributes.length;  attributeIndex++){
					
					String compValue = tempCompElement.getAttribute(compAttributes[attributeIndex]);
					
					if(compValue != null){
						cellData.add(compValue);
					}
					
				}
				
			}
		}
		
		return cellData;
	}	
	
	
	public DOMElement getCellElement(int rowNumber, String compType, String expectedComponentValue, int compSequence) throws Exception
	{
		// Get comp elements within specified row
		List<DOMElement> compElements = rowElements.get(rowNumber).getElementsByTagName(components.get(compType));
		System.out.println("No of elements with tag \""+compType+"\" is :"+compElements.size());
		
		DOMElement compElement = null;
		
		if(expectedComponentValue != null){
			
			for(int compIndex=0; compIndex < compElements.size();compIndex++){
				
				boolean valueMatched = false;
				
				// Get the comp attribute for each component within a row
				DOMElement tempCompElement = compElements.get(compIndex);
				
				String[] compAttributes = componentsWithAttributes.get(compType).split(",");
				
				// Get the Attribute list
				List<String> compAttributeValues = new ArrayList<String>();
				for(int attributeIndex=0; attributeIndex < compAttributes.length;  attributeIndex++){
					
					String compValue = tempCompElement.getAttribute(compAttributes[attributeIndex]);
					
					if(compValue != null){
						compAttributeValues.add(compValue);
					}
					
				}
				
				System.out.println("Attributes values :"+compAttributeValues.toString());
				// Verification of item in the list
				for(int index=0; index < compAttributeValues.size(); index++){
					
					// verify the component text is matched with expected value
					if(compAttributeValues.get(index).equalsIgnoreCase(expectedComponentValue)){
						valueMatched = true;
						break;
					}
				}
				
				// Break the loop once value is matched
				if(valueMatched){
					compElement = tempCompElement;
					break;
				}
			}
		}else{
			compElement = compElements.get(compSequence);
		}
		
		return compElement;
	}
	
	
	
	
	
	public String getCurrentWindowXPath() throws Exception {
		
		String index = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();
		
		String xPath = "/web:window[@index='"+index+"' or @title='"+title+"']";
		
		return xPath;
	}
	
	public String getCurrentWindowTitle() throws Exception {
		
		String title = web.getFocusedWindow().getTitle();
		
		return title;
	}
	
	public boolean compareStrings(String expectedString, String actualString){
		
		boolean stringMatched = false;
		Pattern pattern = Pattern.compile(expectedString);
		Matcher matcher = pattern.matcher(actualString.trim());

       if (matcher.matches()) {
       	stringMatched = true;
       } 
       
		return stringMatched;
	}
	
	public String [] getKeysFromHashMapValuesAreList(HashMap< String,List<String>> searchColumns){
		
		String colVal [] =(String[])searchColumns.keySet().toArray(new String[searchColumns.keySet().size()]);
		 
		return colVal;
	}
	
	public String [] getKeysFromHashMap(HashMap<String, String> searchColumns){
		
		String colVal [] =(String[])searchColumns.keySet().toArray(new String[searchColumns.keySet().size()]);
		 
		return colVal;
	}
	
	public String [] getValuesFromHashMap(HashMap<String, String> searchColumns){
		return (String[])searchColumns.values().toArray(new String[searchColumns.keySet().size()]);
		
	}
	
	@SuppressWarnings("unchecked") 
	public List<String> [] getValuesFromHashMapValuesAreList(HashMap< String,List<String>> searchColumns){
		
		return ( List<String>[])searchColumns.values().toArray(new ArrayList[searchColumns.keySet().size()]);
		
	}
}
