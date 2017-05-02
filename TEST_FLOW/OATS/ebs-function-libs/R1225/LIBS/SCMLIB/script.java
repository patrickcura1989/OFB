import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.oats.scripting.modules.basic.api.Arg;
import oracle.oats.scripting.modules.basic.api.FunctionLibrary;
import oracle.oats.scripting.modules.basic.api.IteratingVUserScript;
import oracle.oats.scripting.modules.basic.api.ScriptService;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.formsFT.common.api.elements.TextField;
import oracle.oats.scripting.modules.webdom.api.elements.DOMElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.applet.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.formsFT.api.*;
import lib.*;

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	@FunctionLibrary("GENLIB") lib.ebsqafwk.GENLIB gENLIB;
	@FunctionLibrary("WEBTABLELIB") lib.ebsqafwk.WEBTABLELIB wEBTABLELIB;
	public void initialize() throws Exception {
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {
	}

	public void finish() throws Exception {
	}
	
	public void selectDeselectAllAssignments(String action) throws Exception {

        boolean bAction = toBoolean(action);
        int row=0;
        int maxLines=12;
        while(forms.checkBox("//forms:checkBox[(@name='STG_SELECTION_FOLDER_ENABLED_FLAG_"+row+"')]").isEnabled()){

        	  forms.checkBox("//forms:checkBox[(@name='STG_SELECTION_FOLDER_ENABLED_FLAG_"+row+"')]").check(bAction);
   
               if(row<maxLines)
                	  row++;  
               else{
            	   row=11;
            	   forms.textField("//forms:textField[(@name='STG_SELECTION_FOLDER_SEQUENCE_NUMBER_"+row+"')]").invokeSoftKey("NEXT_FIELD");
            	   String value=forms.textField("//forms:textField[(@name='STG_SELECTION_FOLDER_SEQUENCE_NUMBER_"+row+"')]").getText();
            	   if(value==null ||value.isEmpty()){
            		   forms.window("//forms:window[(@name='APPTREE')]").selectMenu("Edit|Delete");
            		   break;
            	   }
               }
        }

       reportPassed("Successfully selected/deselected the assignments");
	}
	
	public String getNextSequenceNumber() throws AbstractScriptException{
		
		int row=0;
		int maxLines=12;
		int maxValue=0;
		while(forms.checkBox("//forms:checkBox[(@name='STG_SELECTION_FOLDER_ENABLED_FLAG_"+row+"')]").isEnabled()){
			String temp=forms.textField("//forms:textField[(@name='STG_SELECTION_FOLDER_SEQUENCE_NUMBER_"+row+"')]").getText();
			try{
				System.out.println(temp);
				if(maxValue<Integer.parseInt(temp.trim())){
					maxValue=Integer.parseInt(temp.trim());
				}
			}catch(Exception e){
				
			}
			if(row<maxLines)
				row++;  
			else{
				row=11;
				forms.textField("//forms:textField[(@name='STG_SELECTION_FOLDER_SEQUENCE_NUMBER_"+row+"')]").invokeSoftKey("NEXT_FIELD");
				String value=forms.textField("//forms:textField[(@name='STG_SELECTION_FOLDER_SEQUENCE_NUMBER_"+row+"')]").getText();
				if(value==null ||value.isEmpty()){
					forms.window("//forms:window[(@name='APPTREE')]").selectMenu("Edit|Delete");
					break;
				}
			}
		}
		
		maxValue = maxValue + 1;
		
		return maxValue+"";
	}
	
	/**
	  * Create Time Card for work Order
	  * 
	  * @param day
	  * @param startTime
	  * @param endTime
	  * @throws Exception
	  */
	 public void setTimeCard(String day, String startTime,String endTime) throws Exception{
		
		 // Get the today's date with day as"Mon, Feb 03"
		String date = getWeekDateFormat(gENLIB.getSysTime("DEFAULT","DD-MMM-YYYY hh:mm:ss",0,0,0,0,0,0),"dd-MMM-yyyy HH:mm:ss");
		//System.out.println("Day with date :"+date);
		
		date = date.replaceAll(",", "\\\\,");
		//System.out.println("date :"+date);
			
		beginStep("iterate");
		{
		   String setLine=gENLIB.getProperty("setLine");//"0";
		   
		   gENLIB.setProperty("setLine",setLine);
		   List <String[]> action14199 = new ArrayList<String[]>();
		   
		   //Enter data if day value is provided
		   if(day != null && !"".equalsIgnoreCase(day)){
			   action14199.add( new String[]{"SETTEXT","EDIT",date,date,"","",day});
		   }
		   
		   if(startTime != null && !"".equalsIgnoreCase(startTime)){
			   action14199.add( new String[]{"SETTEXT","EDIT",date+";1",date,"","",startTime});
		   }
		   
		   if(endTime != null && !"".equalsIgnoreCase(endTime)){
			   action14199.add( new String[]{"SETTEXT","EDIT",date+";2",date,"","",endTime});
		   }
		   
		   setLine=wEBTABLELIB.tableactions("Work Order",setLine,action14199);//,form=2
		   
		 }endStep("iterate");	
	 }
	 
	 /**
	  * Select Current Week Period
	  * 
	  * @throws Exception
	  */
	 public void selectTimeCardPeriod(String dateTime, String dateTimeFormat, String noOfWeeks)throws Exception{
		
		 String currentWeek = "0";
		 boolean noOfWeeksAsInt = noOfWeeks.matches("\\d");
		 
		if(noOfWeeksAsInt){
			currentWeek = getCurrentWeek(gENLIB.getSysTime("DEFAULT","DD-MMM-YYYY hh:mm:ss",0,0,0,0,0,0),"dd-MMM-yyyy HH:mm:ss",noOfWeeks);
		}else{
			currentWeek = noOfWeeks;
		}
		
		//List item to select from list
		System.out.println("Item to select from list :"+currentWeek);
			
		//Applicable for window titles 'Recent Timecards:*','Time Entry:*'
		String listPath = "/web:window[@title='*Time*']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:select[(@name='TimecardPeriodList' or @index='0') and multiple mod 'False']";
		
		for(;;){
			
			DOMElement listElement = null;
			boolean listItmeFound = false;
			
			List<DOMElement> listItems = web.selectBox(listPath).getOptions();
			
			for(int index=0; index < listItems.size(); index++){
					
				listElement = listItems.get(index);
				String listItem = listElement.getAttribute("text");
				System.out.println("list Item"+index+":"+listItem);
				
				listItmeFound = gENLIB.compareStrings(currentWeek+".*",listItem);
					
				if(listItmeFound){
					web.selectBox(listPath).selectOptionByText(listItem);
					web.selectBox(listPath).fireEvent("onselect");
					Thread.sleep(5000);
					break;
				}
			}
			
			info("list Item Found :"+listItmeFound);
			//System.out.println("listItems.size() :"+listItems.size());
			
			if(!listItmeFound){
				info("list Size:"+listItems.size());
				web.selectBox(listPath).selectOptionByIndex(listItems.size()-1);
				web.selectBox(listPath).fireEvent("onselect");
				web.window("/web:window[@title='*Time*']").waitForPage(100);
				web.selectBox(listPath).waitFor();
			}else{
				break;
			}
		}//End for(;;)
	 }
	
	public String getCurrentWeek(String time,String format,String nextWeek){
     String result="";
     
            try{
                  DateFormat formatter = new SimpleDateFormat(format.trim());
                  Date date = formatter.parse(time);
                  Calendar cal=Calendar.getInstance();
                  int offset=1;//off set for sunday
                  cal.setTime(date);
                  int iNextWeek=Integer.parseInt(nextWeek);
                  cal.add(Calendar.DATE, iNextWeek*7);
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
                  Date sun=cal.getTime();
                  cal.add(Calendar.DATE, -6);
                  Date mon=cal.getTime();
                  DateFormat formatText = new SimpleDateFormat("MMMM dd, yyyy");
                  result=formatText.format(mon)+" - "+formatText.format(sun);
     }catch(Exception e){
            e.printStackTrace();
     }
     return result;
	}
	
	public  String getWeekDateFormat(String time,String format){
     String returnDateformat="EEE, MMM dd";
     String result="";
     try{
            DateFormat formatter = new SimpleDateFormat(format.trim());
            Date date = formatter.parse(time);
            SimpleDateFormat returnFormat = new SimpleDateFormat(returnDateformat.trim());
            result=returnFormat.format(date);
     }catch(Exception e){
            e.printStackTrace();
     }
     return result;
}
	
	
	/**

     * calculates failure data based on parameter passed in logical column in the component.

     * it takes MTBF (Days),MTBF (Days)(Rounded),MTTR(Days),MTTR(Days)(Rounded),Frequency (per Day),TBF(Days),TBF(Days)(Rounded)

     * TTR(Days),TTR(Days)(Rounded)

     * @param logical

     * @param paramData

     * MTBF (Days) --should pass TBF values

     * MTBF (Days)(Rounded)--should pass actual MTBF

     * MTTR(Days)--should pass all TTR values

     * MTTR(Days)(Rounded)--should pass actual MTTR

     * Frequency (per Day)--should pass actual MTBF

     * TBF(Days)-- should pass actual start date of work order 1 and actual start date of work order2

     * TBF(Days)(Rounded)--should pass actual TBF value

     * TTR(Days)--should pass actual end date of work order1 and actual start date of work order2

     * TTR(Days)(Rounded)--should pass actual TTR value

     * @param format--requires only when calculating TBF and TTR

     * @return

     * @throws AbstractScriptException

     */

    

     public String calculateFailureData(String logical,String paramData,String format) throws AbstractScriptException{

    	 	/*info("logical :"+logical);
    	 	info("paramData :"+paramData);
    	 	info("format :"+format);*/
           Double result = null;

           logical=logical.toUpperCase();

           String tbfArr[]=paramData.split(",");
           
           format=correctDateFormat(format);
           
           try{

           if(logical.startsWith("MTBF")){

                 if(logical.indexOf("ROUNDED")!=-1){

                       Double tRound=Double.parseDouble(tbfArr[0]);

                       result=  getRound(tRound, 3);

                 }else{

                       Double tbfSum=new Double(0.0);

                       for(int i=0;i<tbfArr.length;i++){

                             tbfSum=tbfSum+Double.parseDouble(tbfArr[i]);
                       }
                       result=tbfSum/tbfArr.length;
                 }

           }

           if(logical.startsWith("MTTR")){

                 if(logical.indexOf("ROUNDED")!=-1){

                       Double tRound=Double.parseDouble(tbfArr[0]);

                       result=  getRound(tRound, 1);

                 }else{

                       Double ttrSum=new Double(0.0);

                       for(int i=0;i<tbfArr.length;i++){

                             ttrSum=ttrSum+Double.parseDouble(tbfArr[i]);

                       }

                       result=ttrSum/tbfArr.length;

                 }

           }

           if(logical.startsWith("FREQUENCY")){

//                 if(logical.indexOf("ROUNDED")!=-1){
//
//                       Double mtbf=Double.parseDouble(tbfArr[0]);
//
//                       result=  getRound(mtbf, 3);
//
//                 }else{

                       Double tRound=Double.parseDouble(tbfArr[0]);

                       result=1/tRound;
                       result=  getRound(result, 3);
//                 }

           }

           if(logical.startsWith("TBF")){
                 if(logical.indexOf("ROUNDED")!=-1){
                       Double tbf=Double.parseDouble(tbfArr[0]);
                       result=  getRound(tbf, 1);
                 }else{
                	 
                       Double tbf=compareDates(tbfArr[0], tbfArr[1], format);

                       result=tbf/(24*60*60);//result in days
                       
                       System.out.println("tbf:"+tbf);
                       
                 }
          }

           if(logical.startsWith("TTR")){

                 if(logical.indexOf("ROUNDED")!=-1){

                       Double ttr=Double.parseDouble(tbfArr[0]);

                       result=  getRound(ttr, 1);

                 }else{

                      

                       Double ttr=compareDates(tbfArr[0], tbfArr[1], format);

                       result=ttr/(60*60);//result in hours

                 }

           }

           }catch(Exception e){

//               System.err.println("Invalid parameters are passed");

                 reportFailure("Invalid parameters are passed");

           }

           return result.toString();

     }

     /**

     * Compares two dates and return difference in seconds

     * @param sFromDate

     * @param sToDate

     * @param format

     * @return

     * @throws AbstractScriptException

     */

     private  Double compareDates(String sFromDate,String sToDate,String format) throws AbstractScriptException{

           Double result;

           System.out.println("sFromDate :"+sFromDate);
           System.out.println("sToDate :"+sToDate);
           System.out.println("format :"+format);
           
           try{
                 SimpleDateFormat formatter = new SimpleDateFormat(format.trim());
                 Date fromDate = formatter.parse(sFromDate);

                 Date toDate = formatter.parse(sToDate);
                 //in milliseconds

                 long diff = fromDate.getTime() - toDate.getTime();

                 double dDiff=Double.parseDouble(""+diff);

                 result =dDiff/(1000);

    

           }catch(Exception e){

//               System.err.println("Invalid parameters are supplied");

                 reportFailure("Invalid parameters are passed");

                 return null;

           }

           return result;

     }

     
     private String correctDateFormat(String Format){
         String formatResult="";
         
         formatResult=Format;
         if(formatResult.indexOf("YYYY")!=-1){
                formatResult=formatResult.replace("YYYY", "yyyy");
         }
         if(formatResult.indexOf("YY")!=-1){
                formatResult=formatResult.replace("YY", "yy");
                
         }
         if(formatResult.indexOf("DD")!=-1){
                formatResult=formatResult.replace("DD", "dd");
                
         }
         if(formatResult.indexOf("HH")!=-1){
                formatResult=formatResult.replace("HH", "hh");
                
         }if(formatResult.indexOf("SS")!=-1){
                formatResult=formatResult.replace("SS", "ss");
                
         }
         return formatResult;
  }

     
     
     
     
     /**

     * Converts the value into rounded value for given decimals

     * @param value

     * @param decimals

     * @return

     */

     private Double getRound(Double value,int decimals){

           double dec=1.0;

           for(int i=1;i<=decimals;i++){

                 dec=dec*10;

           }

           return  Math.round( value.doubleValue() * dec ) / dec;

          

     }	
	/**

     * Verifying Copy order

     * @param msg

     * @param startingNum

     * @param count

     * @throws AbstractScriptException

     */

     public void verifyCopyOrder(String msg,String startingNum,String count) throws AbstractScriptException{

           int maxVisibleLines=6;

           int index=0;

           int iCount=Integer.parseInt(count);

           int iStartNum=Integer.parseInt(startingNum);

           int counter=0;

           try{
        	   //start bug 1056
        	   if(forms.window("//forms:window[(@name='MESSAGE_UI')]").isMenuEnabled("View|Record|First"))

                   forms.window("//forms:window[(@name='MESSAGE_UI')]").selectMenu("View|Record|First");

             if(forms.choiceBox("//forms:choiceBox").exists(5,TimeUnit.SECONDS))

                   forms.choiceBox("//forms:choiceBox").clickButton("Continue");
      	   //end bug 1056

           }catch(Exception e){}

           for( counter=0;counter<iCount;){

                 String temp=msg.replaceAll("<<NUM>>", ""+(iStartNum+counter));

                 System.out.println("verifing msg"+temp);

                 String message=forms.textField("//forms:textField[(@name='UI_MESSAGE_MESSAGE_"+index+"')]").getText();

                 boolean flag=gENLIB.compareStrings(temp, message);

                 System.out.println("Actual MSG:"+message+"\nstatus :"+flag);

                 if(flag){

                       counter++;

                 }

                 forms.textField("//forms:textField[(@name='UI_MESSAGE_MESSAGE_"+index+"')]").invokeSoftKey("DOWN");
                 
                 //****start of bug1056********************
                 if(forms.alertDialog("//forms:alertDialog").exists(5, TimeUnit.SECONDS))
                 {
                       forms.alertDialog("//forms:alertDialog").clickYes();
                       break;
                 }
                 //**********END of bug 1056*****                 

                 String status=forms.getStatusBarMessage();

                 if(status!=null&&status.contains("cannot create records"))break;

                 if(index<maxVisibleLines)index++;



           }

           if(counter==iCount){

                 reportPassed("Verification success :"+msg+"found"+count+"times");

           }else

                 reportFailure("Verification failed :"+msg+"found"+counter+"times");
           
           //start bug 1056
           if(forms.alertDialog("//forms:alertDialog").exists(5, TimeUnit.SECONDS))

               forms.alertDialog("//forms:alertDialog").clickYes();
           //end bug 1056
             

     }	
	public void setEditValueBasedOnLabel(String labelWithIndex, String value) throws Exception {

		gENLIB.webSetTextBasedOnLabel(labelWithIndex, value);
		
	}
	
	public void ProcessingConstraints(String Entity,String Processing_Constraints,String Validation,String isEnable) throws Exception{
		
		String operation = Processing_Constraints.split(",")[0];
		String attribute = Processing_Constraints.split(",")[1];
		
		if(operation.equalsIgnoreCase("update")){
			
			updateProcessingConstraints(Entity, Processing_Constraints, Validation, isEnable);
		}
		
	}
	
	
	public void updateProcessingConstraints(String Entity,String Processing_Constraints,String Validation,String isEnable) throws Exception{

		/** 
		 * Querying the entity 
		 * */
		forms.textField("//forms:textField[(@name='ENTITY_ENTITY_DISPLAY_NAME_0')]").setFocus();
		forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Enter");
		forms.textField("//forms:textField[(@name='ENTITY_ENTITY_DISPLAY_NAME_0')]").setText(Entity);
		forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Run");
		forms.textField("//forms:textField[(@name='CONSTRAINTS_COLUMN_DISPLAY_NAME_0')]").setFocus();

		/** Processing constrains are passed as comma separated values it is being splited to get values 
		 * 
		 * action =    Used to Select the "Opertaion" like Create, Update, Delete
		 * displayName = Used to Enter "Attribute"
		 * 
		 * */

		String operation = Processing_Constraints.split(",")[0];
		String attribute = Processing_Constraints.split(",")[1];


		/**
		 *  Querying with processing constraints  
		 *  
		 * */

		forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Enter");
		forms.list("//forms:list[(@name='CONSTRAINTS_CONSTRAINED_OPERATION_0')]").selectItem(operation);
		forms.textField("//forms:textField[(@name='CONSTRAINTS_COLUMN_DISPLAY_NAME_0')]").setText(attribute );
		forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Run");
		think(20);
		int i=0,j=0;

		int count=0;

		/** If records found finding the count of records*/

		if(forms.getStatusBarMessage().indexOf("Query caused no records")==-1){
			try{
				String recordInfo=forms.getStatusBarRecordInfo();
				count=Integer.parseInt(recordInfo.substring(recordInfo.indexOf("/")+1).trim());
			}catch(Exception e){
				forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Record|Last");
				String recordInfo=forms.getStatusBarRecordInfo();
				count=Integer.parseInt(recordInfo.substring(recordInfo.indexOf("/")+1).trim());
				forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Record|First");
				e.printStackTrace();
			}
		}
		System.out.println("Table record info"+forms.getStatusBarRecordInfo());
		for(int k=0;k<count;k++){
			String sEntity=Validation.split(",")[0];
			String RECORD_SET=Validation.split(",")[1];
			String VALIDATION_TMPLT=Validation.split(",")[2];

			forms.textField("//forms:textField[(@name='CONSTRAINTS_COLUMN_DISPLAY_NAME_"+i+"')]").setFocus();
			boolean status=forms.checkBox("//forms:checkBox[(@name='CONSTRAINTS_SYSTEM_FLAG_"+i+"')]").isSelected();
			if(status){if(i<2)i++;continue;}
			/** Querying the validations */
			forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_VALIDATION_ENTITY_DISPLAY_NAME_0')]").setFocus();
			forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Enter");
			forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_VALIDATION_ENTITY_DISPLAY_NAME_0')]").setText(sEntity);
			forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_RECORD_SET_DISPLAY_NAME_0')]").setText(RECORD_SET);
			forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_VALIDATION_TMPLT_DISPLAY_NAME_0')]").setText(VALIDATION_TMPLT);
			forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Run");
			think(20);
			j=0;
			int iCount=0;
			/** If records found finding the count of records*/
			if(forms.getStatusBarMessage().indexOf("Query caused no records")==-1){
				try{
				String recordInfo=forms.getStatusBarRecordInfo();
				iCount=Integer.parseInt(recordInfo.substring(recordInfo.indexOf("/")+1).trim());
				}catch(Exception e){}
			}else{
				/** Canceling the previous requests so that next requests can be submitted */
				forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Cancel");
			}

			for(int l=0;l<iCount;l++){
				boolean isChecked=false;
				if(isEnable.equalsIgnoreCase("true")||isEnable.equalsIgnoreCase("yes")||isEnable.equalsIgnoreCase("on"))
					isChecked=true;
				forms.checkBox("//forms:checkBox[(@name='CONSTRAINT_CONDITIONS_ENABLED_FLAG_"+j+"')]").check(isChecked);
				{
					think(5.736);
				}
				forms.checkBox("//forms:checkBox[(@name='CONSTRAINT_CONDITIONS_ENABLED_FLAG_"+j+"')]").invokeSoftKey("COMMIT");
				{
					think(0.203);
				}
				String mess2=forms.statusBar("//forms:statusBar").getText();
				System.out.println("Record Status:::"+mess2);
				if(j<2)j++;
			}
			if(i<2)i++;
		}
	}
	
	public void function1(String Entity,String Processing_Constraints,String Validation,String isEnable ) throws Exception{

        /** 
         * Querying the entity 
         * */
        forms.textField("//forms:textField[(@name='ENTITY_ENTITY_DISPLAY_NAME_0')]").setFocus();
        
        forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Enter");

        forms.textField("//forms:textField[(@name='ENTITY_ENTITY_DISPLAY_NAME_0')]").setText(Entity);

        forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Run");

        forms.textField("//forms:textField[(@name='CONSTRAINTS_COLUMN_DISPLAY_NAME_0')]").setFocus();

        
        /** Processing constrains are passed as comma separated values it is being splited to get values 
         * 
         * action =    Used to Select the "Opertaion" like Create, Update, Delete
         * displayName = Used to Enter "Attribute"
         * 
         * */

        String operation = Processing_Constraints.split(",")[0];
        String attribute = Processing_Constraints.split(",")[1];

        
        /**
         *  Querying with processing constraints  
         *  
         * */

        forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Enter");
        
        forms.list("//forms:list[(@name='CONSTRAINTS_CONSTRAINED_OPERATION_0')]").selectItem(operation);
        
        forms.textField("//forms:textField[(@name='CONSTRAINTS_COLUMN_DISPLAY_NAME_0')]").setText(attribute );
        
        forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Run");

        think(20);

        int i=0,j=0;

        int count=0;

        /** If records found finding the count of records*/

        if(forms.getStatusBarMessage().indexOf("Query caused no records")==-1){

              try{

              String recordInfo=forms.getStatusBarRecordInfo();

             

              count=Integer.parseInt(recordInfo.substring(recordInfo.indexOf("/")+1).trim());

              }catch(Exception e){

                    e.printStackTrace();

              }

        }

       

        System.out.println("Table record info"+forms.getStatusBarRecordInfo());

        for(int k=0;k<count;k++){

             

                         

              String sEntity=Validation.split(",")[0];

              String RECORD_SET=Validation.split(",")[1];

              String VALIDATION_TMPLT=Validation.split(",")[2];

              forms.textField("//forms:textField[(@name='CONSTRAINTS_COLUMN_DISPLAY_NAME_"+k+"')]").setFocus();

              /** Querying the validations */

              forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_VALIDATION_ENTITY_DISPLAY_NAME_0')]").setFocus();

             

              forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Enter");

             

              forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_VALIDATION_ENTITY_DISPLAY_NAME_0')]").setText(sEntity);

             

              forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_RECORD_SET_DISPLAY_NAME_0')]").setText(RECORD_SET);

             

              forms.textField("//forms:textField[(@name='CONSTRAINT_CONDITIONS_VALIDATION_TMPLT_DISPLAY_NAME_0')]").setText(VALIDATION_TMPLT);

             

              forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Run");

              think(20);

              j=0;

              int iCount=0;

              /** If records found finding the count of records*/

              if(forms.getStatusBarMessage().indexOf("Query caused no records")==-1){

                    try{

                    String recordInfo=forms.getStatusBarRecordInfo();

                    iCount=Integer.parseInt(recordInfo.substring(recordInfo.indexOf("/")+1).trim());

                    }catch(Exception e){}

              }else{

                    /** Canceling the previous requests so that next requests can be submitted */

                    forms.window("//forms:window[(@name='CONSTRAINTS_MAIN')]").selectMenu("View|Query By Example|Cancel");

              }



              for(int l=0;l<iCount;l++){

                    boolean isChecked=false;

                    if(isEnable.equalsIgnoreCase("true")||isEnable.equalsIgnoreCase("yes")||isEnable.equalsIgnoreCase("on"))

                          isChecked=true;

                    forms.checkBox("//forms:checkBox[(@name='CONSTRAINT_CONDITIONS_ENABLED_FLAG_"+j+"')]").check(isChecked);

                    {

                          think(5.736);

                    }

                    forms.checkBox("//forms:checkBox[(@name='CONSTRAINT_CONDITIONS_ENABLED_FLAG_"+j+"')]").invokeSoftKey("COMMIT");

                    {

                          think(0.203);

                    }

             

                   

                    String mess2=forms.statusBar("//forms:statusBar").getText();

                    System.out.println("Record Status:::"+mess2);

                    if(j<2)j++;

              }

              if(i<2)i++;

        }

       

  }
	
	
	
	/*
	 * Function : 
	 * written by : subramanyam 
	 * Date: 17-Apr-2013
	 */
	public void unexpectedPopUp() throws Exception
	{
			
			System.out.println(gENLIB.getActiveFormWindowName());
			Thread.sleep(15000); 
			if(forms.alertDialog("//forms:alertDialog").exists())
			{
					
				forms.alertDialog("//forms:alertDialog").clickYes();
				
			}else if(forms.choiceBox("//forms:choiceBox").exists(10, TimeUnit.SECONDS))
			{						  
				
			    forms.choiceBox("//forms:choiceBox").activate(true);
				forms.choiceBox("//forms:choiceBox").clickButton("OK");
			}
			else{
				System.out.println("No Pop Up Found!!");
			}
					
	}	
	
	/**
	 * By Leo, Given on: 8Apr2013
	 * @param frmOrg
	 * @param endOrg
	 * @param flowType
	 * @param endDate
	 * @throws Exception
	 */
	public void disableInterCompanyRecord(String frmOrg, String endOrg, String flowType, String endDate) throws Exception 
	{
		
		// Query Record
		forms.window("//forms:window[(@name='TRX_FLOW')]").selectMenu("View|Find...");
		
		forms.textField("//forms:textField[(@name='TRANSACTION_FLOW_QF_START_ORG_NAME_0')]").setText(frmOrg);
		
		forms.textField("//forms:textField[(@name='TRANSACTION_FLOW_QF_END_ORG_NAME_0')]").setText(endOrg);
		
		forms.list("//forms:list[(@name='TRANSACTION_FLOW_QF_FLOW_TYPE_0')]").selectItem(flowType);
		
		forms.checkBox("//forms:checkBox[(@name='TRANSACTION_FLOW_QF_ACTIVE_FLOW_FLAG_0')]").check(true);
		
		forms.button("//forms:button[(@name='TRANSACTION_FLOW_QF_FIND_0')]").click();
		
		forms.window("//forms:window[(@name='TRX_FLOW')]").selectMenu("View|Record|Last");
		
		String[] records =  forms.getStatusBarRecordInfo().split("/");
		
		int recCount = Integer.parseInt(records[1]);
		
		if (recCount < 1) {
			info("No record found");
			return;
		}else {
			info("Number of record is : " + recCount);
		}
		
		int currentRow = recCount - 1;
		if (currentRow > 5) {
			currentRow = 4;
		}		
		
		// Remove Record
		while(currentRow >= 0) {			
			forms.textField("//forms:textField[(@name='TRANSACTION_FLOW_HEADER_END_DATE_" + currentRow + "')]").setText(endDate);
			
			forms.window("//forms:window[(@name='TRX_FLOW')]").selectMenu("File|Save");
			
			if (currentRow > 0) {
				forms.textField("//forms:textField[(@name='TRANSACTION_FLOW_HEADER_START_ORG_NAME_" + currentRow + "')]").invokeSoftKey("UP");
			}
			
			currentRow --;
		}		
		
	}	
	
	
	
	public void setTextInDualField(String atti,String label,String value)throws Exception{

        

        String xpath="";

        if(atti.startsWith("//forms"))

              xpath=atti;

        else

              xpath="//forms:textField[(@name='"+atti.trim().replaceAll("\"", "").replaceAll("'", "")+"')]";

       

        forms.textField(xpath).openDialog();

        think(20);

        if(forms.flexWindow("//forms:flexWindow").exists()){

              String labels[]=label.split(",");

              value=value.replaceAll("\\,", "~~");

              String values[]=value.split(",");

              for(int i=0;i<labels.length;i++){

                    forms.flexWindow("//forms:flexWindow").setText(labels[i], "", values[i].replaceAll("~~", ","));

              }

              System.out.println("SetText in flex window");

              forms.flexWindow("//forms:flexWindow").clickOk();

              System.out.println("Clicked Ok");

        }else{

              if(forms.choiceBox("//forms:choiceBox").exists())

                    forms.choiceBox("//forms:choiceBox").clickButton("Yes");

              gENLIB.formSelectLOV("", value);

        }

       

       

  }	
	public void selectDayInMonth(String day) throws Exception {
		
		
		//forms:textField[(@name='B_CAL_DATES_CELL3_0')]
		
		day= day.trim();
		String []multipleDays= day.split(";");
		
		int index = 0;
		if(multipleDays.length>1){
			index = toInt(multipleDays[1]);
			
		}
		List<TextField> textFieldsList = new ArrayList<TextField>();
		
		int startDate= -1;
		int endDate = -1;
		for(int i = 1; i< 42; i++){
			String actualday = null;
			 if(forms.textField("//forms:textField[(@name='B_CAL_DATES_CELL"+i+"_0')]").exists())
				 {
				 actualday = forms.textField("//forms:textField[(@name='B_CAL_DATES_CELL"+i+"_0')]").getText();
				 
				 }
			
			if(actualday!=null){
				
				if(actualday.equals("1")){
					
					System.err.println(i);
					if(startDate<0){
						
					startDate = i;
					
					}
					else{
						
						endDate = i-1;
						
					}
				}
				
			}
			
		}
		if(endDate < startDate){
			
			reportFailure( " selectDayInMonth: Startdate cell is greater than End date cell " );
		}
		
		int cell = startDate+toInt(day)-1;
		
		
	
		forms.textField("//forms:textField[(@name='B_CAL_DATES_CELL"+cell+"_0')]").setFocus();
		forms.textField("//forms:textField[(@name='B_CAL_DATES_CELL"+cell+"_0')]").openDialog();
		forms.textField("//forms:textField[(@name='B_CAL_DATES_CELL"+cell+"_0')]").click();
		
		
		
		
	}
	
	public String getInstancesCount(String instanceString) {
		

		
		String instances[] = instanceString.split(",");
		
		return ""+instances.length;
	}
	
	public List<String> getInstances(String instanceString) {
		

		
		String instances[] = instanceString.split(",");
		
		return Arrays.asList(instances);
	}
	
	public String  getExpenditureGroup(String index) throws Exception {
		
		int expGrpIndex = toInt(index.trim())-1;
		
		
		String path ="/web:window[@index='"+web.getFocusedWindow().getAttribute("index")+"']/web:document[@index='0']/web:pre[@text='*' and @index='0']";
		
		String output = web.element(path).getAttribute("text");
		
		int startIndex = output.indexOf("Expenditure Group");
		int endIndex = output.indexOf("Total Expenditure Groups Created");
		
		String substring = output.substring(startIndex, endIndex);
		substring = 	substring.replace("Expenditure Group", "");
		substring = 	substring.replaceAll("Ending Date", "");
		substring = 	substring.replaceAll("Number of Expenditures", "");
		
		substring = 	substring.replaceAll("-", "");
		substring = 	substring.replaceAll("\n", "");
		substring = 	substring.replaceAll("  ", "");
		substring = 	substring.replaceAll("\t", "");
		/*substring = 	substring.replace("Expenditure Group", "");
		substring = 	substring.replaceAll("Ending Date", "");
		substring = 	substring.replaceAll("Number of Expenditures", "");*/
		substring = 	substring.replaceAll(substring.substring(substring.indexOf("Sum"),substring.length() ),"");
		System.err.println(substring);
		
		String [] mylist = substring.trim().split(" ");
		
		List<String> expgrpList = Arrays.asList(mylist);
		
		if(expGrpIndex>0){
			
			return expgrpList.get(expGrpIndex+2);
		}else{
			
			return expgrpList.get(0);
		}
	}
	public String extractRequestID() throws AbstractScriptException{
		
		String id = "";
		
		String requestIdString = forms.textField("//forms:textField[(@name='MESSAGE_DETAIL_0')]").getText();
		
		info("requestIdString :"+requestIdString);
		
		String requestID = requestIdString.substring(requestIdString.indexOf("REQ_IDS=")+8,requestIdString.indexOf(")"));
		info("requestID :"+requestID);
		
		
		String interfaceString = requestIdString.substring(requestIdString.indexOf("request ")+8,requestIdString.indexOf(" has"));
		info("interfaceString :"+interfaceString);
		
		if(!"".equals(requestID) && requestID != null){
			id = id + requestIdString.trim()+","+interfaceString;
		}
		
		return id;
		
	}
	
	/**
	 * Clear current record, select clear record option from menu
	 * @throws Exception
	 * @author fsui
	 */
	public void clearRecord()throws Exception{

		forms.window("//forms:window[(@enabled='true')]").selectMenu("Edit|Clear|Record");

	}
	
	
	/**
	 * Delete current record, select delete option from menu
	 * @throws Exception
	 * @author fsui
	 */
	public void deleteRecord()throws Exception{

		forms.window("//forms:window[(@enabled='true')]").selectMenu("Folder|Delete...");

	}
	
	/**
	 * Select all records in current form
	 * @throws Exception
	 * @author fsui
	 */
	public void selectAllRecords()throws Exception{
		forms.window("//forms:window[(@enabled='true')]").selectMenu("Edit|Select All");
	}
	
	
	
	/**
	 * Trim the string's space and return it
	 * @param value
	 * @return
	 * @throws Exception
	 * @author fsui
	 */
	public String strTrim(String value)throws Exception
	{

		String strTrim= value.trim();
		return strTrim; 

	}
	
	
	/**
	 * logout application and close all browsers
	 * @throws Exception
	 * @author fsui
	 */
	public void logOut() throws Exception{

		if(web.link("/web:window[(@enabled='true')]/web:document[@index='0']/web:a[@text='Logout']").exists())
		{
			web.link("/web:window[(@enabled='true')]/web:document[@index='0']/web:a[@text='Logout']").click();
		}
		
		browser.closeAllBrowsers();
		
	}
	
	/**
	 * Shows all the fields passed into it in the form of an array. 
	 * @param fieldNames An array containing all the fields to be displayed.
	 * @throws Exception
	 */
	public void showAllFields(String[] fieldNames) throws Exception{
		
		// loop to show required field
		for(int i = 0; i < fieldNames.length; i++)
		{			
			boolean isShown = showField(fieldNames[i]);
			if(isShown == false)
			{
				info("Field: " + fieldNames[i] + " Is Already Displayed");
			}
		}
	}
	
	/**
	 * Shows a field
	 * @param fieldName
	 * @throws Exception
	 * @return true if successful and false for fails

	 */
	public boolean showField(String fieldName)throws Exception{
		
		boolean isShown = false;
		
		String val="";
		//Select Show Field option from menu
		forms.window("//forms:window[(@enabled='true')]").selectMenu("Folder|Show Field...");

		Thread.sleep(2000);
		
		if(forms.listOfValues("//forms:listOfValues").exists()){
			forms.listOfValues("//forms:listOfValues").find(fieldName + "%");
			String itemCount = forms.listOfValues( "//forms:listOfValues").getAttribute("itemCount").toString();
			if(itemCount.equalsIgnoreCase("0"))
			{
				info("No items found");
				forms.listOfValues("//forms:listOfValues").clickCancel();
			}
			else
			{
				val=forms.listOfValues("//forms:listOfValues").getText(1);
				forms.listOfValues("//forms:listOfValues").select(val); 
				isShown = true;			
			}
		}
		else if(forms.choiceBox("//forms:choiceBox").getAlertMessage().equalsIgnoreCase("There are no additional fields to show for this region.")){
			info("There are no additional fields to show for this region.");
			forms.choiceBox("//forms:choiceBox").clickButton("OK");
		}
		return isShown;
	}
	
	/**
	 * Open Accouting Periods up to current month for an org
	 * Change organization must be done before using this function
	 * @throws Exception
	 */
	public void openInvAccoutingPeroids() throws Exception {
		String currMonth = "";
		String status;
		String period;
		int row = 9; // start checking from the very bottom row, as it is always "Open"
		boolean periodOpened = false;
				
		currMonth = new java.text.SimpleDateFormat("MMM", Locale.US).format(new Date()) + "-" + new java.text.SimpleDateFormat("yy", Locale.US).format(new Date());
		info("Cuurent Month: " + currMonth);
		
		forms.window("//forms:window[(@name='PERIODS')]").activate(true);
		forms.textField("//forms:textField[(@name='PERIODS_STATUS_9')]").click(); think(1);
		
		while (periodOpened == false) {
			status = forms.textField("//forms:textField[(@name='PERIODS_STATUS_" + row + "')]").getText();
			period = forms.textField("//forms:textField[(@name='PERIODS_PERIOD_NAME_" + row + "')]").getText();
			
			if (status.equalsIgnoreCase("Future")) {
				
				forms.button("//forms:button[(@name='PERIODS_CONTROL_CHANGE_BUTTON_0')]").click();
				forms.choiceBox("//forms:choiceBox").clickButton("OK");think(1);
				
				// Update status
				status = forms.textField("//forms:textField[(@name='PERIODS_STATUS_" + row + "')]").getText();
			}
			
			if (status.equalsIgnoreCase("Open") && period.equalsIgnoreCase(currMonth)) {
				// curr month opened
				periodOpened = true;
			}
			else {
				forms.textField("//forms:textField[(@name='PERIODS_STATUS_" + row + "')]").invokeSoftKey("UP");
				row--;
				if (row < 0) row = 0;
			}
		} // end of while
		
		forms.window("//forms:window[(@name='PERIODS')]").selectMenu("File|Close Form");
		
		collapseNavigator();
	}
	
	/**
	 * Collapse trees within navigator
	 * @throws Exception
	 */
	public void collapseNavigator() throws Exception {
		forms.window("//forms:window[(@name='NAVIGATOR')]").activate(true);		
		forms.button("//forms:button[(@name='NAV_CONTROLS_COLLAPSE_ALL_0')]").click();
	}
	
	/**
	 * Set component value given the label
	 * @param labelName label of the component
	 * @param compType component type
	 * @param valueToSet value to set
	 * @throws Exception
	 */
	public void setValueBasedOnLabel(String labelName, String compType, String valueToSet)throws Exception{
		 
		 String RTObject_Xpath = null; 
		 List<DOMElement> elements = new ArrayList<DOMElement>(); 
		 String xPath = "/web:window[@index='0']/web:document[@index='0']/web:span[@text='" + labelName + "']"; 
		 info(xPath);
		 web.element(xPath).waitFor(200);
		 
		 DOMElement textElement = web.element(xPath);
		 
		 /* Get the Parent Item*/
		 elements = getCompsBasedOnLabel(labelName, compType);
		 //elements=(ArrayList<DOMElement>) textElement.getParent().getParent().getElementsByTagName(compType);
		 System.out.println("Number of fields matching " + labelName + " : " + elements.size());
		 
		 DOMElement element = elements.get(0);
		 
		 if (compType.equalsIgnoreCase("select")) {
			 RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:select[@id='" + element.getAttribute("id")+"']";
			 System.out.println("Xpath of " + compType + " is : " + RTObject_Xpath);
			 web.selectBox(RTObject_Xpath).selectOptionByText(valueToSet);
		 }
		 if (compType.equalsIgnoreCase("textArea")){
			 info(element.getAttribute("id"));
			 RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:textArea[@id='" + element.getAttribute("id") + "']";
			 System.out.println("Xpath of " + compType + " is : " + RTObject_Xpath);
			 web.textArea(RTObject_Xpath).setText(valueToSet);
		 }
		 if (compType.equalsIgnoreCase("input")){
			 RTObject_Xpath = "/web:window[@index='0']/web:document[@index='0']/web:input_text[@id='" + element.getAttribute("id") + "']";
			 System.out.println("Xpath of " + compType + " is : " + RTObject_Xpath);
			 web.textBox(RTObject_Xpath).setText(valueToSet);
		 }
		 
		 if (compType.equalsIgnoreCase("checkbox")){
			 RTObject_Xpath="/web:window[@index='0']/web:document[@index='0']/web:input_checkbox[@id='"+element.getAttribute("id") + "']";
			 System.out.println("Xpath of " + compType + " is : " + RTObject_Xpath);
			 web.textBox(RTObject_Xpath).setText(valueToSet);
		 }
	}
	
	/**
	 * Get component belongs to a label
	 * @param label the component's label
	 * @param compType component type
	 * @return the component list with the label
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked") 
	public List<DOMElement> getCompsBasedOnLabel(String label, String compType) throws Exception {
		
		String winIndex = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();
		
		String winPath = "/web:window[@index='" + winIndex + "' or @title='" + title + "']";
		
		String xPath = winPath+"/web:document[@index='0']/web:td[@text='" + label + "']";
		
		String tdIndex = web.element(xPath).getAttribute("index");
		//System.out.println("tdIndex :" + tdIndex);
		
		int finalTdIndex = Integer.parseInt(tdIndex) + 2;
		//System.out.println("finalIndex :" + finalTdIndex);

		String finalTdXPath = winPath + "/web:document[@index='0']/web:td[@index='" + finalTdIndex + "']";
		
		List<DOMElement> elements =  web.element(finalTdXPath).getElementsByTagName(compType);
		
		return elements;
	}
	
	/**
	 * Format given date string into the formated date according to the new format
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public String formatDate (String date,String format)throws Exception
	{
		info("format date");  
		Map<String, String> months;
		String DateArray[];
		String val	=	"";

		months = new HashMap<String, String>();

		months.put("01", "JAN");
		months.put("1", "JAN");
		months.put("02", "FEB");
		months.put("2", "FEB");
		months.put("03", "MAR");
		months.put("3", "MAR");
		months.put("04", "APR");
		months.put("4", "APR");
		months.put("05", "MAY");
		months.put("5", "MAY");
		months.put("06", "JUN");
		months.put("6", "JUN");
		months.put("07", "JUL");
		months.put("7", "JUL");
		months.put("08", "AUG");
		months.put("8", "AUG");
		months.put("09", "SEP");
		months.put("9", "SEP");
		months.put("10", "OCT");
		months.put("11", "NOV");
		months.put("12", "DEC");



		DateArray =date.split("-");


		//1.Format "DD-MON-YYYY" 
		if (format.equalsIgnoreCase("DD-MON-YYYY"))
		{
			if (date.contains("/"))
			{
				DateArray =date.split("/");	
			}
			
			int len=(DateArray[2]).length();
			if (len<3)
			{
				DateArray[2]= "20"+DateArray[2];
			}
			//Commented by spotnuru , this is throwing error, contact me before un commenting
			//-----START-------
			/*
			if (isNumber(DateArray[1]).equalsIgnoreCase("String"))
			{
				val = (DateArray[0] + "-" + (DateArray[1]) + "-" + (DateArray[2]));
				System.out.println(val);	
			}
			else
			{
				val = (DateArray[0] + "-" +  months.get(DateArray[1]) + "-" + (DateArray[2]));
				System.out.println(val);
			}
			*/
			//-----END-------
			return (val);

		}
		//2.Format "DD-MM-YY"
		else if (format.equalsIgnoreCase("DD-MM-YY"))
		{

			val = (DateArray[0] + "-" +  months.get(DateArray[1]) + "-" + (DateArray[2].substring(2,4)));
			System.out.println(val);
			return(val);

		}
		//3.Format "MM/DD/YY"
		else if (format.equalsIgnoreCase("MM/DD/YY"))
		{
			val =  (months.get(DateArray[1])+ "/" + DateArray[0] + "/" + (DateArray[2].substring(2,4)));
			System.out.println(val);
			return(val);

		}
		//4.Format "MM/DD/YYYY"
		else if (format.equalsIgnoreCase("MM/DD/YYYY"))
		{
			val =  (months.get(DateArray[1])+"/"+ DateArray[0] + "/" + DateArray[2]);
			System.out.println(val);
			return(val);

		}
		//5.Format "DD/MM/YY"
		else if (format.equalsIgnoreCase("DD/MM/YY"))
		{

			val =  (DateArray[0] + "/" + months.get(DateArray[1])+ "/" + DateArray[2].substring(2,4));
			System.out.println(val);
			return(val);

		}
		//6.Format "DD/MM/YYYY"
		else if (format.equalsIgnoreCase("DD/MM/YYYY"))
		{
			val =  (DateArray[0] + "/" + months.get(DateArray[1])+ "/" + DateArray[2]);
			System.out.println(val);
			return(val);

		}
		//7.Format "YYYY-MM-DD"
		else if (format.equalsIgnoreCase("YYYY-MM-DD"))
		{
			DateArray =date.split("-");
			val =  (DateArray[2]+"-"+ months.get(DateArray[1])+ "-" + DateArray[0]);
			System.out.println(val);
			return(val);

		}
		return "not a valid format";
	}
	
	
	/**
	 * Replace oldStr with newStr in str
	 * @param str 
	 * @param oldStr 
	 * @param newStr
	 * @return replaced string
	 * @throws Exception
	 */
	public String replaceString(String str, String oldStr, String newStr)throws Exception
	{

		return str.replace(oldStr, newStr);

	}
	
	/**
	 * Get substring by giving the start index and end index
	 * @str parent string
	 * @param start
	 * @param end
	 * @return
	 */
	public String getSubStringByIndex(String str, int start, int end) {
				
		return str.substring(start, end);
	}
	
	/**
	 * Get substring by giving the left and right string
	 * @param str parent string
	 * @param left left string besides the substring
	 * @param right right string besides the substring
	 * @return
	 */
	public String getSubStringByText(String str, String left, String right) {
		String subStr = null;
		
		int leftIndex = str.indexOf(left);
		int rightIndex = str.indexOf(right);
		
		subStr = str.substring(leftIndex, rightIndex).replace(left, "").trim();
		
		return subStr;		
	}
	
	/**
	 * Select 'more...' option when select org, find the org and select
	 * @param org
	 * @author cchao
	 */
	public void selectOrgMore(@Arg("org") String org) throws Exception {	
		think(5);
		String index = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();		
		String xPath = "/web:window[@index='"+index+"' or @title='"+title+"']";		
		beginStep("Search and Select List of Values", 0);
		{
			web.selectBox(xPath+"/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName']/web:select[(@id='*OrgLovChoice*' or @name='*OrgLovChoice*') and multiple mod 'False']").selectOptionByText("More...");
		}
		endStep();
		
		think(10);
		index = web.getFocusedWindow().getAttribute("index");
		title = web.getFocusedWindow().getTitle();		
		xPath = "/web:window[@index='"+index+"' or @title='"+title+"']";		
		
		beginStep("Enter Text and Click Go", 0);
		{
			web.selectBox(xPath+"/web:document[@index='1']/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:select[(@id='categoryChoice' or @name='categoryChoice' or @index='0') and multiple mod 'False']").selectOptionByIndex(0);
			web.textBox(xPath+"/web:document[@index='1']/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:input_text[@name='searchText' or @index='0']").click();
			web.textBox(xPath+"/web:document[@index='1']/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:input_text[@name='searchText' or @index='0']").setText(org);
			web.button(xPath+"/web:document[@index='1']/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:button[@value='Go' or @index='2']").click();
		}
		endStep();
		beginStep("Select the Searched Record", 0);
		{
			web.radioButton(xPath+"/web:document[@index='1']/web:form[@id='_LOVResFrm' or @name='_LOVResFrm' or @index='0']/web:input_radio[@id='N1:N8:0' or (@name='N1:selected' and @value='0') or @index='0']").select();
			web.button(xPath+"/web:document[@index='1']/web:button[@value='Select' or @index='4']").click();
		}
		endStep();
	}
	
	/**
	 * Verify choice box message againgst expectedValue
	 * @param expectedValue expected message against which to be verifyed
	 * @return true if matched, false not matched
	 * @author lehan
	 * @throws Exception
	 */
	public boolean formVerifyChoiceBox(String expectedValue) throws Exception {
		String text = "";
		boolean verify = true;
		
		if (forms.choiceBox( "//forms:choiceBox").exists()){

			text = forms.choiceBox( "//forms:choiceBox").getAlertMessage();
			
			if (text.equals(expectedValue)) {
				verify = true;
				info("The text matches with the expected value");
			}
			else {
				verify = false;
				reportFailure("The text doesn't match with the expected value");
			}
		}
		
		return verify;
	}
	
	/**
	 * keep performing action on actionObj util verifyObj match expectedValue
	 * @param actionObj form object type
	 * @param actionXPath action object xpath
	 * @param verifyObject verify object type
	 * @param verifyXpath verify object xpath
	 * @param expectedValue expected value to wait
	 * @throws Exception
	 * Supported object types: button, menu, statusBar, edit
	 */
	public void formWaitForRecord(String actionObj, String actionXPath, String verifyObj, String verifyXpath, String expectedValue) throws Exception {
		if (actionObj.equalsIgnoreCase("button")) {
			int count = 0;
			
			while (count < 100) {
				forms.button("//forms:button[(@name='" + actionXPath + "')]").click();
				
				if (verifyObj.equalsIgnoreCase("edit")) {
					String txt = forms.textField("//forms:textField[(@name='" + verifyXpath + "')]").getText();
					
					if (txt.equals(expectedValue)) {
						info("Expected value found, exit.");
						break;
					}
				}
				
				if (verifyObj.equalsIgnoreCase("statusBar")) {
					String status = forms.getStatusBarMessage();
					
					if (status.equals(expectedValue)) {
						info("Expected value found, exit.");
						break;
					}
				}
				
				if (verifyObj.equalsIgnoreCase("button")) {
					boolean enabled = forms.button("//forms:button[(@name='" + verifyXpath + "')]").isEnabled();				
					if (enabled) {
						info("Expected button enabled, exit.");
						break;
					}
				}
				
				think(5);
				count++;
			}
			
			if (count >= 100) {
				warn("Time out waiting for value: " + expectedValue);
			}
		}
		
		if (actionObj.equalsIgnoreCase("menu")) {
			int count = 0;
			
			String activeFormName = "";
			
			for (int i = 0; i < forms.getAllOpenWindows().length; i++) {
				if (forms.getAllOpenWindows()[i].isActive()) {
					activeFormName = forms.getAllOpenWindows()[i].getName();
					break;
				}
			}
			while (count < 100) {
								
				
				forms.window("activeFormName").selectMenu(actionXPath);
				
				if (verifyObj.equalsIgnoreCase("edit")) {
					String txt = forms.textField("//forms:textField[(@name='" + verifyXpath + "')]").getText();
					
					if (txt.equals(expectedValue)) {
						info("Expected value found, exit");
						break;
					}
				}
				
				if (verifyObj.equalsIgnoreCase("statusBar")) {
					String status = forms.getStatusBarMessage();
					
					if (status.equals(expectedValue)) {
						info("Expected value found, exit");
						break;
					}
				}
				
				if (verifyObj.equalsIgnoreCase("button")) {
					boolean enabled = forms.button("//forms:button[(@name='" + verifyXpath + "')]").isEnabled();				
					if (enabled) {
						info("Expected button enabled, exit.");
						break;
					}
				}
				
				think(5);
				count++;
			}
			
			if (count >= 100) {
				warn("Time out waiting for value: " + expectedValue);
			}
		}
	}
	
	/**
	 * Copy all text from focused window, and check if exepctedValue existes in the text copied
	 * @param expectedValue value to check
	 * @return true if contains the expectedValue
	 * @throws Exception
	 */
	public boolean formVerifyWindowText(String expectedValue) throws Exception {
		boolean verified = false;
		
		// Copy text from web window or the application window
	    info("Trying to Copy text");
        Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();			            
        
        beginStep("Clear clipboard");
        {
        	try {
        		clipboard.setContents(new Transferable() {
        	        public DataFlavor[] getTransferDataFlavors() {
        	          return new DataFlavor[0];
        	        }

        	        public boolean isDataFlavorSupported(DataFlavor flavor) {
        	          return false;
        	        }

        	        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        	          throw new UnsupportedFlavorException(flavor);
        	        }
        	      }, null);
        	}
        	catch (IllegalStateException e) {} 
        }
        endStep();
        
        info("Select All text");
        ft.typeKeys("<CTRL-A>");
        Thread.sleep(10000);
        info("Copy text");
        ft.typeKeys("<CTRL-C>");           
        Thread.sleep(20000);
        String Content = "";
        
        clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();     
        Transferable contents = clipboard.getContents(null);
        
        boolean isTransferableText =(  (contents != null) &&  contents.isDataFlavorSupported(DataFlavor.stringFlavor))  ;
        
        if ( isTransferableText ) {                      
            Content = (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        else {
            System.err.println("Error: Not found content");
        }
        //System.out.println("Content: " + Content);
        
        if (Content.contains(expectedValue)) {
        	verified = true;
        }
        else {
        	verified = false;
        }
        
        return verified;
	}
	
	/**
	 * Get table cell value by given first cell value and row, column number 
	 * @param tableName first cell value of the table
	 * @param row The row of the table cell, starting from 1.
	 * @param col The column of the table cell, starting from 1.
	 * @return the value of the table cell.
	 * @throws Exception
	 */
	public String webGetCellData(String tableName, int row, int col) throws Exception {
		
		String activeWin = web.getFocusedWindow().getTitle();
		
		String value = web.table("/web:window[@title='" + activeWin + "']/web:document/web:table[@firstTableCell='" + tableName + "']").getCell(row, col);
		
		return value;
	}
	
	/**
	 * Verify element's attribute value against expected attribute value
	 * @param xpath in the format: web:a[@text='Logout']
	 * @param attr attribute name of the element
	 * @param expectedValue expectd attribute value of the element
	 * @return true is attribute value is matched
	 * @throws Exception
	 */
	public boolean webVerifyAttributes(String xpath, String attr, String expectedValue) throws Exception {
		boolean verified = false;
		
		if (web.element("/web:window[(@enabled='true')]/web:document/" + xpath).getAttribute(attr).equals(expectedValue)) {
			verified = true;
			
			info("The text matches with the expected value");
		}
		else {
			warn("The text doesn't match with the expected value");
		}
		
		return verified;
	}
		
	/**
	 * Verify element's attribute value against expected attribute value
	 * @param tag element tag type
	 * @param name name of the element
	 * @param attr attribute name
	 * @param expectedValue expected attribute value
	 * @return true if attribute value is matched
	 * @throws Exception
	 */
	public boolean formVerifyAttributes(String tag, String name, String attr, String expectedValue) throws Exception {
		boolean verified = false;
		
		if (tag.equalsIgnoreCase("button")) {
			if (forms.button("//forms:button[(@name='" + name + "')]").getAttribute(attr).equals(expectedValue)) {
				verified = true;
				
				info("The text matches with the expected value");
			}
			else {
				warn("The text doesn't match with the expected value");
			}
		}
		else if (tag.equalsIgnoreCase("editBox")) {
			if (forms.editBox("//forms:editBox[(@name='" + name + "')]").getAttribute(attr).equals(expectedValue)) {
				verified = true;
				
				info("The text matches with the expected value");
			}
			else {
				warn("The text doesn't match with the expected value");
			}
		}
		else if (tag.equalsIgnoreCase("checkBox")) {
			if (forms.checkBox("//forms:checkBox[(@name='" + name + "')]").getAttribute(attr).equals(expectedValue)) {
				verified = true;
				
				info("The text matches with the expected value");
			}
			else {
				warn("The text doesn't match with the expected value");
			}
		}
		
		
		return verified;
	}
	
	/**
	 * Verify form menu status, enabled and existed
	 * @param menu menu path
	 * @param attr existed or enabled
	 * @param expectedValue true or false
	 * @return true if status matched
	 * @throws Exception
	 */
	public boolean formVerifyMenu(String menu, String attr, String expectedValue) throws Exception {
		boolean verified = false;
		
		if (attr.equalsIgnoreCase("existed")) {
			boolean status = forms.window("//forms:window[(@enabled='true')]").menuExists(menu);
			
			if (status == Boolean.valueOf(expectedValue)) {
				info("The text matches with the expected value");
			}
			else {
				warn("The text doesn't match with the expected value");
			}
		}
		else if (attr.equalsIgnoreCase("enabled")) {
			boolean status = forms.window("//forms:window[(@enabled='true')]").isMenuEnabled(menu);
			
			if (status == Boolean.valueOf(expectedValue)) {
				info("The text matches with the expected value");
			}
			else {
				warn("The text doesn't match with the expected value");
			}
		}
		
		return verified;
	}
	
	/**
	 * sign E-signatures
	 * @param signer
	 * @param password
	 * @throws AbstractScriptException
	 */
	public void signESignatures(String signer, String password) throws AbstractScriptException{
		if(forms.choiceBox("//forms:choiceBox").exists()&&forms.choiceBox("//forms:choiceBox").getAlertMessage().contains("HTML")){
//			web.window("/web:window[@index='2'or @title='Electronic Records and Signatures: List Of Signers']").waitForPage(null);
			
			String[] username=signer.split(",");
			String[] psw=password.split(",");
			
			for(int i=0; i<username.length;i++){
				web.image("/web:window[@index='2' or @title='Electronic Records and Signatures: List Of Signers']/web:document[@index='0']/web:img[@alt='Authenticate'or @src='*esign_enabled.gif']").click();
//				web.window("/web:window[@index='2' or @title='Notification Details']").waitForPage(null);
				web.selectBox("/web:window[@index='2' or @title='Notification Details']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:select[(@id='N61' or @name='NRR3' or @index='2') and multiple mod 'False']").selectOptionByText("Yes");
				web.button("/web:window[@index='2' or @title='Notification Details']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@value='Approve']").click();
				
//				web.window("/web:window[@index='2' or @title='Notification Signing Page']").waitForPage(null);
				web.textBox("/web:window[@index='2' or @title='Notification Signing Page']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='N37' or @name='oracle_security_digsig_sigData3']").setText(username[i]);
				web.textBox("/web:window[@index='2' or @title='Notification Signing Page']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_password[@id='N38' or @name='oracle_security_digsig_sigData2']").setPassword(psw[i]);
				web.button("/web:window[@index='2' or @title='Notification Signing Page']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@value='Sign']").click();
//				web.window("/web:window[@index='2' or @title='Electronic Records and Signatures: List Of Signers']").waitForPage(null);
				
				if(i==username.length-1&&!web.button("/web:window[@index='2' or @title='Electronic Records and Signatures: List Of Signers']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='EdrDone_uixr' or @value='A<SPAN class=xq>p</SPAN>ply' or @index='6']").exists()){
					String activeWin = web.getFocusedWindow().getTitle();
					int rowCount = web.table("/web:window[@title='" + activeWin + "']/web:document/web:table[@firstTableCell='Sequence']").getRowCount();
					StringBuffer name=new StringBuffer();
					for(int j=username.length; j<rowCount;j++){
						name.append(web.table("/web:window[@title='" + activeWin + "']/web:document/web:table[@firstTableCell='Sequence']").getCell(j, 1));
						name.append(",");
					}
					
					warn("The approve flow is updated. Still need "+ name.toString()+" to approve");
				}
			}
			
			web.button("/web:window[@index='2' or @title='Electronic Records and Signatures: List Of Signers']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='EdrDone_uixr']").click();
			
			beginStep("Confirmation");
			{
//				web.window("/web:window[@index='2' or @title='Confirmation']").waitForPage(null);
				web.button(521, "/web:window[@index='2' or @title='Confirmation']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@value='<SPAN class=xq>O</SPAN>K']").click();
			}
			endStep();
			beginStep("Note");
			{
				forms.choiceBox("//forms:choiceBox").clickButton("OK");
			}
			endStep();
		
		}
			
	}
	public String getShipConfirmReqIds(String content,String before,String after)throws Exception
	{
		String msg	=	content;
		String requests="";
		int fromIndex=msg.indexOf(before);
		if(before.isEmpty())fromIndex=0;
		String temp=msg.substring(fromIndex);
		int toIndex=temp.indexOf(after);
		if(after.isEmpty())toIndex=temp.length();
		temp=temp.substring(0,toIndex);
		Pattern p =
			Pattern.compile("[0-9]+", Pattern.MULTILINE);
		Matcher match = p.matcher(temp);
		while(match.find())
			requests+=match.group()+",";
   
        return requests.substring(0,requests.length()-1);

		
		/*
		String ret	=	"";
		String temp =	content.substring(content.indexOf("(REQ_IDS=")+"(REQ_IDS=".length()); 
		temp		=	temp.substring(0,temp.indexOf(")"));
		temp		=	temp.replace(" ", "");
		ret	=	temp;
		return ret;
		*/
		
	}	
	public String getShipConfirmReqIds(String content)throws Exception
	{
		/*
		String requests="";
		int fromIndex=msg.indexOf(before);
		if(before.isEmpty())fromIndex=0;
		String temp=msg.substring(fromIndex);
		int toIndex=temp.indexOf(after);
		if(after.isEmpty())toIndex=temp.length();
		temp=temp.substring(0,toIndex);
		Pattern p =
			Pattern.compile("[0-9]+", Pattern.MULTILINE);
		Matcher match = p.matcher(temp);
		while(match.find())
			requests+=match.group()+",";
   
        return requests.substring(0,requests.length()-1);

		*/
		
		String ret	=	"";
		String temp =	content.substring(content.indexOf("(REQ_IDS=")+"(REQ_IDS=".length()); 
		temp		=	temp.substring(0,temp.indexOf(")"));
		temp		=	temp.replace(" ", "");
		ret	=	temp;
		return ret;
		
	}
	public String getDeliveryNumber(String content)
	{
		String ret	=	"";
		String temp =	content.substring(content.indexOf("delivery")+"delivery".length()); 
		temp		=	temp.substring(0,temp.indexOf("."));
		temp		=	temp.replace(" ", "");
		ret	=	temp;
		return ret;		
	}
	public String getTripStopReqId(String content)
	{
		String ret	=	"";
		String temp =	content.substring(content.indexOf("Stop request")+"Stop request".length()); 
		temp		=	temp.substring(0,temp.indexOf("has been"));
		temp		=	temp.replace(" ", "");
		ret	=	temp;
		return ret;			
	}
	
	/*public String getLPNNumber(String From,String Index)
	{
		System.out.println("************ Start : getLPNNumber ****************");
		String ret	=	"";
		boolean isnumeric	=	true;
		StringBuffer temp 	=	new StringBuffer(From);
		
		temp				=	temp.reverse();
		String tempS	=	new String(temp);
		System.out.println("tempS:"+ tempS);
		char chars[] = new String(temp).toCharArray();
		int d	=	0;
		StringBuffer fromSB	=	new StringBuffer();
		for(d = 0; d < chars.length; d++)
		{
		 System.out.println("Char at "+ d+ ":" + chars[d]);	
         isnumeric &= Character.isDigit(chars[d]);
         System.out.println("isnumeric "+ isnumeric);	
         if(!isnumeric)
         {	        	 
        	 break;
         }
         fromSB.append(chars[d]);
                 
		}
		System.out.println("fromSB "+ fromSB);
		//System.out.println(fromSB.reverse() + ",Index"+ From.indexOf(fromSB.reverse().toString())); 
		String actNum	=	fromSB.reverse().toString();
		System.out.println("actNum "+ actNum);
		//System.out.println(fromSB.reverse() + ",Index"+ From.indexOf(actNum));
		String before = From.substring(0,From.indexOf(actNum));
		System.out.println("before "+ before);
		int from	=	Integer.parseInt(actNum);
		//System.out.println("Value:"+from);
		System.out.println("from "+ from);
		int retVal	=	from + Integer.parseInt(Index);
		System.out.println("retVal "+ retVal);
		
		System.out.println("************End:  getLPNNumber ****************");
		
		return new String(before+retVal+"");
		
	}*/
	
	
	/**
	 *  Adding a number to the integer part available in "From" field
	 *  Ex: 
	 *  
	 *  1) From : str0000, index: 5  then return : str0005
	 *  2) From : str026, index: 5  then return : str031
	 *  3) From : str526, index: 5  then return : str531
	 *  
	 * @param From
	 * @param Index
	 * @return
	 */
	public String getLPNNumber(String From,String Index)
	{
		System.out.println("************ Start : getLPNNumber ****************");
		
		boolean isnumeric	=	true;
		StringBuffer splitted_From	=	new StringBuffer();
		String integer_From = new String();
		String To_String = new String();
		
		StringBuffer temp 	=	new StringBuffer(From);
		char chars[] = new String(temp).toCharArray();
		
		for(int charIndex = 0; charIndex < chars.length; charIndex++)
		{
			//System.out.println("Char at "+ charIndex+ ":" + chars[charIndex]);	
         
			isnumeric = Character.isDigit(chars[charIndex]);
         
			//System.out.println("isnumeric "+ isnumeric);	
			if(isnumeric)
			{	 
				integer_From = From.substring(splitted_From.length());
				break;
			}else{
				splitted_From.append(chars[charIndex]); // Getting String from From
			}     
		}
		
		System.out.println("String Part :"+splitted_From+"    integer Part :"+integer_From);
		
		// Finding length of the Integer Part
		int Integer_From_Length = integer_From.length();
		
		// Adding the Number i.e index after separation
		int Integer_Ater_Adding = Integer.parseInt(integer_From) +  Integer.parseInt(Index);
		
		// Finding length of the Integer Part after adding the index
		int Integer_After_Lenth = (""+Integer_Ater_Adding).length();
		
		if(Integer_From_Length == Integer_After_Lenth){
			To_String = splitted_From + ""+Integer_Ater_Adding;
		}else if(Integer_From_Length > Integer_After_Lenth){
			// Adding "0"'s before the main integer to match the count
			int difference_Digits = Integer_From_Length-Integer_After_Lenth;
			
			String digits = "";
			for(int index=1; index <= difference_Digits ; index++){
				digits = digits+"0";
			}
			
			To_String = splitted_From + digits + Integer_Ater_Adding;
		}else{
			System.out.println("Invalid String (Crosses boundaries, if i add any number) :"+integer_From);
			
		}
		
		System.out.println("************ Start : getLPNNumber ****************");
		
		return To_String;
	}
	
	
	public  String getLPNNameFromLog()  throws Exception
	{
		String lpname="";
		String  index= web.getFocusedWindow().getAttribute("index");
		
		String path="web:window[@index='"+index+"']/web:document[@index='0']";
		
		List<DOMElement> list=	web.document(path).getElementsByTagName("body");
		info("size :"+list.size());
		
		String bodyContent=list.get(0).getAttribute("innertext");
		
		info("bodyContent :"+bodyContent);
		
		if(bodyContent.indexOf("Created LPN name")==-1)
		{
			info("Unable to find LPN Name in Output LOG");
			
			return lpname;
		}
		
		int startIndex=bodyContent.indexOf("Created LPN name")+"Created LPN name".length();
		
		int endIndex=bodyContent.indexOf("lpn_cnt=1");
		
		lpname=bodyContent.substring(startIndex, endIndex).trim();
		
		info("LPN Name in Output LOG :"+lpname);
		
		return lpname;	
	 }
	
	
	public String  verifyLabelContextXMLData(String  textFieldAttributeName,String variableNames, String variableValues) throws Exception
	  {
		  String textFieldXmlData=forms.textField(	"//forms:textField[(@name='"+textFieldAttributeName+"')]").getText();
		  if (textFieldXmlData==null)
		  {
			  info("Unable to find Form text field , AttributeName: "+textFieldAttributeName);
			  return "false";
		  }
		  
		   String labelsXmldata =textFieldXmlData.substring(textFieldXmlData.indexOf("<labels"), textFieldXmlData.length());
		   Map<String, String > actualVariableNameValueMap=new HashMap<String, String>();
		   Map<String, String > expectedVariableNameValueMap=new HashMap<String, String>();
		   String variableName="";
		   String  variableValue="";
		   String  verifyCheck="true";
		   
		   DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		   DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		   Document document = docBuilder.parse( new InputSource(new StringReader(labelsXmldata)));  
		   document.getDocumentElement ().normalize ();
		   
		   info("Root element of the doc is " + document.getDocumentElement().getNodeName());
		   NodeList listOfLabelnodes = document.getElementsByTagName("label");
		   info("Label tag count"+ listOfLabelnodes.getLength());
	       
		   for(int s=0; s<listOfLabelnodes.getLength() ; s++)
		   {
	        	Node firstLabelNode = listOfLabelnodes.item(s);
	  
	        	Element labelElement = (Element)firstLabelNode;
	        	NodeList listOfVaraiblenodes = labelElement.getElementsByTagName("variable");
	        	info(" variables tag Count "+ listOfVaraiblenodes.getLength());
	        	
	        	for(int varCount=0; varCount<listOfVaraiblenodes.getLength();varCount++)
	        	{
	        		Element varElement = (Element)listOfVaraiblenodes.item(varCount);
	        		NodeList varElementValue = varElement.getChildNodes();
	        		if(varElementValue.item(0)!=null)
	        		{
	        			variableValue=varElementValue.item(0).getNodeValue();
	        			System.out.println(" value : " + 
	        				varElementValue.item(0).getNodeValue());
	
	        		}
	        		else
	        		{
	        			//System.out.println(" value : " + varElementValue.item(0));
	        			variableValue="";
	        		}
	        		
	        		//System.out.println("Name attribute value : " + varElement.getAttribute("name"));
	        		variableName=varElement.getAttribute("name");
	        		actualVariableNameValueMap.put(variableName, variableValue);
	    		
	        	}
	  
	    
		   }
  
    
		   String[] variableNamesArray= variableNames.split(",");
		   String[] variableValuessArray= variableValues.split(",");
		   if(variableNamesArray.length !=variableValuessArray.length)
		   {
			   info("VariableNames and VariableValues are  not  matched  Please  provide  proper Data");
			   return  "false";
		   }
		   
		   for(int nameCount=0; nameCount<variableNamesArray.length;nameCount++)
		   {
			   expectedVariableNameValueMap.put(variableNamesArray[nameCount],variableValuessArray[nameCount]);
		   }
		   
		   Iterator expectedVariableNameValueMapIterator=expectedVariableNameValueMap.keySet().iterator();
		   
		   while(expectedVariableNameValueMapIterator.hasNext())
		   {
			   variableName=(String)expectedVariableNameValueMapIterator.next();
    	
			   info ("Expected variable name : "+variableName  + " Expected variableValue : "+expectedVariableNameValueMap.get(variableName));
			   info ("Actual variable name   : "+variableName  + " Actual variableValue   : "+actualVariableNameValueMap.get(variableName));
			   
			   if(expectedVariableNameValueMap.get(variableName).equals(actualVariableNameValueMap.get(variableName).trim()))
				   info("Expected variable value and  actual variable value is matched.");
			   else{
				   verifyCheck="false";
				   info("Expected variable value and  actual variable value is not matched.");
			   }
			   info("");
		   }
				  
		   
		   return verifyCheck;
  }
	
	
	
	public boolean isNumber(String string) throws IllegalArgumentException
	{
	   boolean isnumeric = false;

	   if (string != null && !string.equals(""))
	   {
	      isnumeric = true;
	      char chars[] = string.toCharArray();

	      for(int d = 0; d < chars.length; d++)
	      {
	         isnumeric &= Character.isDigit(chars[d]);

	         if(!isnumeric)
	         break;
	      }
	   }
	   return isnumeric;
	}
	
	
	
}
