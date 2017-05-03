import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.formsFT.api.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lib.*;

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	@ScriptService oracle.oats.scripting.modules.datatable.api.DataTableService datatable;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	
	@FunctionLibrary("GENLIB") lib.ebsqafwk.GENLIB gENLIB;
	@FunctionLibrary("OracleFinancialsLibrary") lib.com.acc.OracleFinancialsLibrary oRACLEFINANCIALSLIBRARY;
	@FunctionLibrary("WEBTABLELIB") lib.ebsqafwk.WEBTABLELIB wEBTABLELIB;
	
	public void initialize() throws Exception 
	{	
		//Initializing all required objects
		browser.launch();
                getVariables().set("CURR_SCRIPT_FOLDER",getScriptPackage().getScriptPath().replace(getScriptPackage().getScriptName()+".jwg",""));
	}
	

			  
     
     /**
      * Add code to be executed each iteration for this virtual user.
      */

	public void run() throws Exception 
	{	
		
		
	
		 beginStep("00_test");{ 

 getVariables().set("WSDataBankName","00_test");
		 }endStep("00_test");

		 beginStep("00_test1");{ 

 getVariables().set("WSDataBankName","00_test1");
			oRACLEFINANCIALSLIBRARY.forms_setTextField(getVar("{{outputP1}}"),"","");
		 }endStep("00_test1");

	
	
	}
	public void finish()throws Exception{
}
	

			public String getVar(String key)

{

                try

                                          {

                key=key.substring(2, key.length()-2);

                String value="";

                value=getVariables().get(key);

                if(value==null)

                value=gENLIB.getProperty(key);

 

                return value;

                                          }

                catch(Exception e){

                return null;

                }

}
}
