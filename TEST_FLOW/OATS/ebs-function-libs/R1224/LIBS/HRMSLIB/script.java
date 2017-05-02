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
		
		String currentWeek = getCurrentWeek(gENLIB.getSysTime("DEFAULT","DD-MMM-YYYY hh:mm:ss",0,0,0,0,0,0),"dd-MMM-yyyy HH:mm:ss","0");
		System.out.println("Current Week :"+currentWeek);
			
		String listPath = "/web:window[@title='Recent Timecards:*']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:select[(@name='TimecardPeriodList' or @index='0') and multiple mod 'False']";
		
		List<DOMElement> listItems = web.selectBox(listPath).getOptions();
			
		DOMElement listElement = null;
		
		for(int index=0; index < listItems.size(); index++){
				
			listElement = listItems.get(index);
			String listItem = listElement.getAttribute("text");
			boolean listItmeFound = gENLIB.compareStrings(currentWeek+".*",listItem);
				
			if(listItmeFound){
				web.selectBox(listPath).selectOptionByText(listItem);
				web.selectBox(listPath).fireEvent("onselect");
				Thread.sleep(5000);
				break;
			}
		}	
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

}
