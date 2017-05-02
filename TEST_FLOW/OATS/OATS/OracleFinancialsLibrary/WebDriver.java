

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.utilities.api.Table;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.webdom.api.elements.DOMDocument;
import oracle.oats.scripting.modules.webdom.api.elements.DOMElement;
import oracle.oats.scripting.modules.webdom.api.elements.DOMSelect;
import oracle.oats.scripting.modules.webdom.api.elements.DOMTable;
import oracle.oats.scripting.modules.webdom.api.elements.DOMText;
import oracle.oats.scripting.modules.webdom.api.elements.accessibilityElements.AccElement;

public class WebDriver {

	/**
	 * @param args
	 */
	
	private static String title = "Oracle Applications Home Page" ;
	private static String web_prefix = "/web:window[@title='$']" ;
	private static String mainWin = "/web:window[@index='0']" ;
	private static String login_title = "/web:window[@title='Login']" ;
	

	
	public static void web_login (IteratingVUserScript script, WebDomService web, String url, String userName ,String pwd) throws Exception	{
		web.window(mainWin)
			.navigate(
				url);
		web.window(login_title).waitForPage(Constant.WAIT) ;			
		web.textBox(			
			login_title + Constant.DOM_ID + "/web:form[@index='0']/web:input_text[@id='unamebean']").setText(userName);																			
		web.textBox(login_title + Constant.DOM_ID + "/web:form[@index='0']/web:input_password[@id='pwdbean']").setPassword(pwd);
											
		web.button(		
			login_title + Constant.DOM_ID + "/web:form[@index='0']/web:button[@id='SubmitButton']").click();		
		web.window(web_prefix.replace("$", title)).waitForPage(Constant.WAIT) ;		
		Common.sleep(2, TimeUnit.SECONDS) ;
	}
	
	
	
	
	public static void web_login_change_password (IteratingVUserScript script, WebDomService web, String url, String userName ,String pwd) throws Exception	{
		web.window(mainWin)
			.navigate(
				url);
		web.window(login_title).waitForPage(Constant.WAIT) ;			
		web.textBox(			
			login_title + Constant.DOM_ID + "/web:form[@index='0']/web:input_text[@id='unamebean']").setText(userName);																			
		web.textBox(login_title + Constant.DOM_ID + "/web:form[@index='0']/web:input_password[@id='pwdbean']").setPassword(pwd);
											
		web.button(		
			login_title + Constant.DOM_ID + "/web:form[@index='0']/web:button[@id='SubmitButton']").click();
		web.window(web_prefix.replace("$", "Change Password")).waitForPage(Constant.WAIT) ;		
	}
	
	public static void web_clickLink (IteratingVUserScript script, WebDomService web, String xpath) throws Exception { 
		web.link(xpath).click();
		//synchronized		
	}
	
	public static void web_clickTextBox (IteratingVUserScript script, WebDomService web, String xpath) throws Exception { 
		web.textBox(xpath).click();
		//synchronized		
	}
	
	public static void web_waitForBrowserReady (IteratingVUserScript script, WebDomService web , String xpath, int timeout) throws Exception{
		while (timeout > 0 && !"complete".equals(web.window(xpath).getReadyState())) { 
			 Common.sleep(timeout, TimeUnit.SECONDS);
		}
	}
	
	public static void web_setTextBox (IteratingVUserScript script, WebDomService web, String xpath, String value) throws Exception { 
		DOMText text = web.textBox(xpath);
		text.click();
		text.setText(value);
		//synchronized		
	}
	
	public static void web_selectBoxSelectOption (IteratingVUserScript script, WebDomService web, String xpath, String value) throws Exception { 
		DOMSelect text = web.selectBox(xpath);		
		text.selectOptionByText(value);
		//synchronized		
	}
	
	public static void web_clickButton (IteratingVUserScript script, WebDomService web, String xpath) throws Exception { 
		web.button(xpath).click();
		//synchronized		
	}
	
	public static void web_clickCheckbox (IteratingVUserScript script, WebDomService web, String xpath) throws Exception {
		web.checkBox(xpath).click();
	}
	
	public static void web_clickImage (IteratingVUserScript script, WebDomService web, String xpath) throws Exception { 
		web.image(xpath).click();
		//synchronized		
	}	
	
	public static String web_getElementText (IteratingVUserScript script, WebDomService web, String xpath)throws AbstractScriptException{ 
		DOMElement ele = web.element(xpath);
		return ele.getDisplayText();		
	}
	
	public static void web_waitForWindowsExists (IteratingVUserScript script, WebDomService web, String xpath, int timeout) throws AbstractScriptException{
		script.getLogger().info("wait for page :  " + xpath) ;
		web.window(xpath).waitForPage(timeout) ;
	}
	
	
	public static void web_clickItem (IteratingVUserScript script, WebDomService web, String xpath, String tag) throws Exception  {		
		DOMElement ele = web.element(xpath).getParent();	
	    
		for (Object e : ele.getElementsByTagName("img") ) {
			if (e instanceof DOMElement && ((DOMElement)e).getAttribute("alt") != null && ((DOMElement)e).getAttribute("alt").contains(tag)) {				
				((DOMElement)e).click() ;
				Common.sleep(3, TimeUnit.SECONDS) ;
				return ;
			}
		}				
	
	}
	
	
	public static void web_ExpandTreeItem (IteratingVUserScript script, WebDomService web, String xpath) throws Exception{
		script.getLogger().info("expand item :  " + xpath) ;
		web_clickItem (script,web, xpath, "expand") ;
	}
	
	
	public static void web_CollapseTreeItem (IteratingVUserScript script, WebDomService web, String xpath) throws Exception{
		script.getLogger().info("collapse item :  " + xpath) ;
		web_clickItem (script,web, xpath, "collapse") ;
	}
	
	public static void web_viewRepositoryManagementItem (IteratingVUserScript script, WebDomService web, String xpath) throws Exception{
		script.getLogger().info("view item :  " + xpath) ;
		web_clickItem(script,web,xpath,"View the report") ;
	}

	public static boolean web_elementExists (IteratingVUserScript script, WebDomService web, String xpath) throws Exception{
		DOMElement ele = web.element(xpath);
		return ele.exists();	
	}
	
	public static DOMElement web_getTable(IteratingVUserScript script, WebDomService web, String windowTitle, String tableAttribute, String attributeValue) throws Exception{
		String tag = "/web:window[@title='%']/web:document[@index='0']".replace("%", windowTitle) ;
        DOMDocument dom = web.document(tag) ;        
        List<DOMElement> all = dom.getElementsByTagName("table") ;                
        for (DOMElement d : all) {
        	//System.out.println("table : " + d.getAttribute(tableAttribute));
        	if (attributeValue.equals(d.getAttribute(tableAttribute))) {
        		return d;
        	}
        }               
		return null;	
	}	
	
	public static DOMElement web_getTableRow(IteratingVUserScript script, WebDomService web, DOMElement table, int rowIndex) throws Exception{
        List<DOMElement> rows = table.getElementsByTagName("tr") ;
        return rows.get(rowIndex);        
	}
	
	public static DOMElement web_getLastTableRow(IteratingVUserScript script, WebDomService web, DOMElement table) throws Exception{
        List<DOMElement> rows = table.getElementsByTagName("tr") ;
        return rows.get(rows.size()-1);        
	}
		
	public static DOMElement web_getTDElement(IteratingVUserScript script, WebDomService web, DOMElement row, int tdIndex) throws Exception{
        List<DOMElement> tds = row.getElementsByTagName("td") ;
        return tds.get(tdIndex);        
	}
	
	public static DOMElement web_getTDLink(IteratingVUserScript script, WebDomService web, DOMElement td) throws Exception{
        List<DOMElement> tds = td.getElementsByTagName("a") ;
        return tds.get(0);        
	}
	
	public static void web_refreshPaymentProcessrequest (IteratingVUserScript script, WebDomService web, String req_id,String status, int timeout) throws Exception{		
		int wait = 10 ;
		String path= "/web:window[@title='Payment Process Requests']/web:document[@index='0']/web:a[@text='%']".replace("%", "req_id") ;
		int offset = 6 ;
		boolean exists = false ;
		while (timeout > 0 && ! (exists = web.link(path).exists())) {		
			web_clickButton(script, web, Web_PaymentProcessRequests.REFRESH_STATUS);
			Common.sleep(wait, TimeUnit.SECONDS) ;
			timeout -= wait;
		}
		if (!exists) script.fail("after " + timeout +  " the request is not ready") ;
		
		int index = Integer.parseInt(web.link(path).getAttribute("index")) ;
		exists = false ;
		path = "/web:window[@title='Payment Process Requests']/web:document[@index='0']/web:td[@index='%']".replace("%", String.valueOf(index));
		while (timeout > 0 && !exists){
			exists =  web.element(path).exists() && status.equals(web.element(path));
		}
		if (!exists) script.fail("after " + timeout +  " the request is not ready") ;
	}
	
	public static void web_clickTDLinkInTable(IteratingVUserScript script, WebDomService web, String windowTitle, String tableAttribute, String attributeValue, int rowIndex, int tdIndex) throws Exception{
		DOMElement table = web_getTable(script, web, windowTitle, tableAttribute, attributeValue);
		DOMElement row = web_getTableRow(script, web, table, rowIndex);
		DOMElement td = web_getTDElement(script, web, row, tdIndex);
		web_getTDLink(script, web, td).click();
	}

	public static void web_clickLastTDLinkInHomePageTable(IteratingVUserScript script, WebDomService web, String windowTitle, String tableAttribute, String attributeValue) throws Exception{
		DOMElement table = web_getTable(script, web, windowTitle, tableAttribute, attributeValue);	
		DOMElement row = web_getLastTableRow(script, web, table);		
		DOMElement td = web_getTDElement(script, web, row, 2);
		web_getTDLink(script, web, td).click();
	}
	
	public static void web_clickLatestTDLinkInHomePageTable(IteratingVUserScript script, WebDomService web, String windowTitle, String tableAttribute, String attributeValue) throws Exception{
		if(web.image(Web_OracleApplicationsHomePage.TABLE_SORT_ASCENDING_IMAGE).exists())
			web_clickImage(script, web, Web_OracleApplicationsHomePage.TABLE_SORT_ASCENDING_IMAGE);
		DOMElement table = web_getTable(script, web, windowTitle, tableAttribute, attributeValue);		
		DOMElement row = web_getTableRow(script, web, table, 1);
		DOMElement td = web_getTDElement(script, web, row, 2);
		web_getTDLink(script, web, td).click();
	}
	
	public static String web_getLatestTDLinkInHomePageTable(IteratingVUserScript script, WebDomService web, String windowTitle, String tableAttribute, String attributeValue) throws Exception{
		if(web.image(Web_OracleApplicationsHomePage.TABLE_SORT_ASCENDING_IMAGE).exists())
			web_clickImage(script, web, Web_OracleApplicationsHomePage.TABLE_SORT_ASCENDING_IMAGE);
		DOMElement table = web_getTable(script, web, windowTitle, tableAttribute, attributeValue);		
		DOMElement row = web_getTableRow(script, web, table, 1);
		DOMElement td = web_getTDElement(script, web, row, 2);
		return web_getTDLink(script, web, td).getDisplayText();
	}
	
	public static boolean web_findLinkByColText (IteratingVUserScript script, WebDomService web, String windowTitle, String tableAttribute, String attributeValue, int col, String expected) throws Exception{
		if(web.image(Web_OracleApplicationsHomePage.TABLE_SORT_ASCENDING_IMAGE).exists())
			web_clickImage(script, web, Web_OracleApplicationsHomePage.TABLE_SORT_ASCENDING_IMAGE);
		HashSet<String> set = new HashSet<String> ();
		DOMElement table = web_getTable(script, web, windowTitle, tableAttribute, attributeValue);
		List<DOMElement> rows = table.getElementsByTagName("tr") ;
		for (DOMElement row : rows) {
			try {
				DOMElement td = web_getTDElement(script, web, row, col);
				System.out.println(web_getTDLink(script, web, td).getDisplayText());
				if ( web_getTDLink(script, web, td).getDisplayText().contains(expected)) return true ;
			} catch (Exception ignore) {}
		}
		return false ;
	}
	
	public static void web_navigateToLink (IteratingVUserScript script, WebDomService web, String path) throws Exception{		 
		  DOMTable table =  web.table(Web_OracleApplicationsHomePage.MAIN_MENU) ;		  		  		   		  
		  String [] all_path = path.split("\\|") ;         		 
	      dfs(table, all_path, 0) ;	  		  		  
	}
	
	public static void web_setTextArea (IteratingVUserScript script, WebDomService web, String path, String value) throws Exception{		 
		  web.textArea(path).setText(value);  		  		  
	}
	
	public static void web_assertOuputDisplayed (IteratingVUserScript script, WebDomService web) throws Exception{		 
		DOMDocument dom = web.document("/web:window[@title='*FNDWRR.exe*']/web:document[@index='0']/web:body[@index='0']"); 		  		  
		dom.assertText("web_assertOuputDisplayed", "*Report*", Source.DisplayContent, TextPresence.PassIfPresent, MatchOption.WildcardEntireString);
	}
	
	public static void web_assertPageContainsText (IteratingVUserScript script, WebDomService web, String title, String text) throws Exception{		 
		DOMDocument dom = web.document("/web:window[@title='%']/web:document[@index='0']/web:body[@index='0']".replace("%", title)); 		  		  
		dom.assertText("web_assertOuputDisplayed", "*%*".replace("%", text), Source.DisplayContent, TextPresence.PassIfPresent, MatchOption.WildcardEntireString);
	}
	
	public static void dfs (DOMElement root, String [] path, int level) throws Exception{
		if (path.length == level) return ;				
		List<DOMElement> all_li = root.getElementsByTagName("li") ;
		DOMElement cur = null ;
		DOMElement a = null ;
		
		for (DOMElement li : all_li) 
		{		   		   
		   List<DOMElement> links = li.getElementsByTagName("a") ;
		   if (links.size() == 0) continue ;
		   a = links.get(0) ;		   
		   System.out.println(a.getDisplayText());
		   if (!path[level].equals(a.getDisplayText())) continue ;
		   cur = li ;
		   if (a != null) break;		   
		}		
		a.click() ;		
		Common.sleep(2, TimeUnit.SECONDS);
		dfs (cur, path, level + 1) ;				
	}
	
}
