
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.formsFT.common.api.elements.AbstractWindow;
import oracle.oats.scripting.modules.formsFT.common.api.elements.BlockScroller;
import oracle.oats.scripting.modules.formsFT.common.api.elements.CheckBox;
import oracle.oats.scripting.modules.formsFT.common.api.elements.ChoiceBox;
import oracle.oats.scripting.modules.formsFT.common.api.elements.FlexWindow;
import oracle.oats.scripting.modules.formsFT.common.api.elements.List;
import oracle.oats.scripting.modules.formsFT.common.api.elements.ListOfValues;
import oracle.oats.scripting.modules.formsFT.common.api.elements.RadioButton;
import oracle.oats.scripting.modules.formsFT.common.api.elements.TextField;
import oracle.oats.scripting.modules.formsFT.common.api.elements.TreeList;
import oracle.oats.scripting.modules.formsFT.common.api.elements.Window;
import oracle.oats.scripting.modules.webdom.api.*;

public class Forms {

	private static HashMap<String,String> map;
	
	static{
	     map = new HashMap<String,String> (){	    	
	    	 {	    		 	    		

	    	 }
	     };	
	}
	
	public static void forms_save(IteratingVUserScript script, FormsService forms, String windowTitle) throws AbstractScriptException{	
		forms.window("//forms:window[(@name='%')]".replace("%", windowTitle)).clickToolBarButton("Save");
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_textFieldInvokeKey(IteratingVUserScript script, FormsService forms, String path, String key) throws AbstractScriptException{	
		forms.textField(path).invokeSoftKey(key);
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_savePath(IteratingVUserScript script, FormsService forms, String path) throws AbstractScriptException{	
		forms.window(path).clickToolBarButton("Save");
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_query_mode(IteratingVUserScript script, FormsService forms, String path) throws AbstractScriptException{	
		Window main	= forms.window(path);						
		clickMenuItem (main,"View|Query By Example|Enter",20) ;
	}
	
	public static void forms_find(IteratingVUserScript script, FormsService forms, String windowName) throws AbstractScriptException{	
		Window main	= forms.window("//forms:window[(@name='%')]".replace("%", windowName));							
		main.clickToolBarButton("Find...");
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_run(IteratingVUserScript script, FormsService forms, String path) throws AbstractScriptException{	
		Window main	= forms.window(path);										
		clickMenuItem (main,"View|Query By Example|Run",20) ;
	}
	
	public static void forms_requests(IteratingVUserScript script, FormsService forms, String path) throws AbstractScriptException{
		Window main = forms.window(path);
		clickMenuItem (main, "View|Requests", 20);
	}
	
	private static void clickMenuItem (Window main, String path, int timeout) throws AbstractScriptException {
		main.selectMenu(path);				
		while (main.isMenuEnabled(path) && timeout > 0) {
			Common.sleep(1, TimeUnit.SECONDS) ;
			timeout--;
		}		
	}
	
	public static void forms_setTextFieldFocus(IteratingVUserScript script, FormsService forms, String colName, int index) throws AbstractScriptException{		
		String path = ((index==-1) ? colName : colName.replace("%", Integer.toString(index)));	
		System.out.println(path);
	    TextField textField = forms.textField(path) ;
	    textField.setFocus();
	    Common.sleep(1, TimeUnit.SECONDS);
	}
	
	public static void forms_setFlexWindow (IteratingVUserScript script, FormsService forms, String field, String val) throws AbstractScriptException{	     
		FlexWindow flex = forms.flexWindow(Constant.FLEX_WINDOW);
		flex.setText(field, "", val);
	}
	
	
	public static void forms_setFlexWindowTextField (IteratingVUserScript script, FormsService forms, String field, String col,String val) throws AbstractScriptException{
		FlexWindow flex = forms.flexWindow(Constant.FLEX_WINDOW);
		flex.setText(field, col, val) ;
	}
	 
	

	public static void forms_flexWindowClickOK  (IteratingVUserScript script, FormsService forms) throws AbstractScriptException{
		forms.flexWindow(Constant.FLEX_WINDOW).clickOk();
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_flexWindowClickCancel  (IteratingVUserScript script, FormsService forms) throws AbstractScriptException{
		forms.flexWindow(Constant.FLEX_WINDOW).clickCancel();
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_setTextField(IteratingVUserScript script, FormsService forms, String colName, int index, String val, boolean key, String keyCombination) throws AbstractScriptException{     	     	    
		String path = ((index==-1) ? colName : colName.replace("%", Integer.toString(index)));
	    TextField textField = forms.textField(path) ;	     
	    textField.click();
	    Common.sleep(1, TimeUnit.SECONDS);
	    textField.setText(val) ;
	    if (key) {
	    	textField.invokeSoftKey(keyCombination) ;
	    }
	}
	
	public static void forms_setTextFieldToNextLine (IteratingVUserScript script, FormsService forms, String colName, String val, boolean key, String keyCombination) throws AbstractScriptException{
		BlockScroller bar  = forms.blockScroller("//forms:blockScroller[(@barIndex='0')]") ;
		int index = bar.getHeight();
		forms_setTextField (script,forms, colName, index,  val,  key,  keyCombination);
	}
	
	public static void forms_clickTextField(IteratingVUserScript script, FormsService forms, String colName, int index) throws AbstractScriptException{	     	     
	     String path = colName.replace("%", Integer.toString(index)) ;
	     TextField textField = forms.textField(path) ;
	     textField.click();
	     Common.sleep(1, TimeUnit.SECONDS);	     
	}
	
	public static void forms_clickButton(IteratingVUserScript script, FormsService forms, String btnName) throws AbstractScriptException{
		String path = map.containsKey(btnName) ? map.get(btnName): btnName ;
		forms.button(path).click();
		Common.sleep(2, TimeUnit.SECONDS);
	}
	
	public static void forms_waitForWindowExists (IteratingVUserScript script, FormsService forms, String windowTitle, int second) throws AbstractScriptException {
		String xpath = "//forms:window[(@name='$')]" ;
		Window win = forms.window(xpath.replace("$", windowTitle));
		Wait.await(script).window(win).interval(1000).atMost(second * 1000).untilExists();   		
     	win.activate(true);     		     	
	}
	
	
	public static void forms_waitForWindowsExistsByTitle (IteratingVUserScript script, final FormsService forms, final String windowTitle, int timeout) throws AbstractScriptException{		 		 
		  Wait.await(script).Condition(new Condition() {
			@Override 
			public boolean isReady() {
				 try {
					 AbstractWindow [] aw = forms.getAllOpenWindows() ;
					 for (AbstractWindow a : aw ) {
						 if (a.getTitle().contains(windowTitle)) {
							 return true ;
						 }
					  }
				 } catch (Exception e){return false ;}
				 return false ;
			}}).interval(1000).atMost(timeout * 1000).waitForConditionReady();	
	}
	
	public static void forms_waitForFlexWindowsExists (IteratingVUserScript script, FormsService forms, String name, int timeout) throws AbstractScriptException{
		String title = name.length() == 0 ? "Flex Window" : name ;
		script.getLogger().debug("waiting for window : " + title + " exists") ;
		FlexWindow win = forms.flexWindow("//forms:flexWindow") ;
		Wait.await(script).window(win).interval(1000).atMost(timeout * 1000).untilExists();					
	}
	
	public static void forms_waitForWindowNotExists (IteratingVUserScript script, FormsService forms, String windowTitle, int second) throws AbstractScriptException {
		String xpath = "//forms:window[(@name='$')]" ;
		Window win = forms.window(xpath.replace("$", windowTitle));
		Wait.await(script).window(win).interval(1000).atMost(second * 1000).untilNotExists() ;	     	     		     
	}
	
	
	public static String forms_postJournalsByName (IteratingVUserScript script, FormsService forms, String name, String val)  throws AbstractScriptException {		  		 
		  BlockScroller bar  = forms.blockScroller("//forms:blockScroller[(@barIndex='0')]") ;
		  //find the batch
		  int index = findRowByText (forms, bar, "//forms:textField[(@name='BATCHES_NAME_$')]", name, 9) ;
		  script.getLogger().debug("the item index is " + index) ;
		  stopScriptIfFail (script, index == -1, "cannot found batch which name is " + name);		  
		  CheckBox check = forms.checkBox("//forms:checkBox[(@name='BATCHES_POST_$')]".replace("$", String.valueOf(index))) ;		  	
		  check.check(true) ;
		  forms_clickButton(script, forms ,Forms_Post_Journals_ACC.POST_BTN);		  
		  ChoiceBox box = forms.choiceBox("//forms:choiceBox") ;
		  Wait.await(script).window(box).interval(1000).atMost(60 * 1000).untilExists();
		  String msg = box.getAlertMessage() ;		
		  script.getLogger().debug(msg);
		  //val is the label name
		  box.clickButton(val);		  
		  return requestID (msg);
	}
	
	
     private static String requestID (String msg){
	   	  int i = msg.length() - 1;
		  while (i>= 0 && !Character.isDigit(msg.charAt(i))) i--;		  
		  if (i < 0) return "" ; 
		  StringBuilder sb = new StringBuilder ();
		  while (i >= 0 && Character.isDigit(msg.charAt(i))) {
			  sb.append(msg.charAt(i)) ;
			  i--;
		  }		  		  
	      return sb.reverse().toString() ;    	 
     }
	
    public static int forms_findItemInJournalsByBatchName (FormsService forms, String name) throws AbstractScriptException {    
    	BlockScroller bar = forms.blockScroller("//forms:blockScroller[(@barIndex='1')]") ;
    	String path = "//forms:textField[(@name='JOURNALS_BATCH_NAME_$')]" ;
    	return findRowByText(forms, bar, path, name, 12) ;
    }
     
	public static int findRowByText(FormsService forms, BlockScroller bar, String rowPath, String keyWord,  int totalRow) throws AbstractScriptException{
		int i = 0 ;
		String path = rowPath ;
		while (i < totalRow){
			String text = forms.textField(path.replace("$", String.valueOf(i))).getText();			
			if (text.contains(keyWord)) return i ;			
			path = rowPath ; 
			i++;
		}	
		int current = totalRow - 1 ;
		path = rowPath ; 
		while (bar.getValue() + totalRow < bar.getMaximum()) {
			bar.scrollDown() ;
			Common.sleep(500, TimeUnit.MILLISECONDS) ;
			String text = forms.textField(path.replace("$", String.valueOf(current))).getText() ;
			if (text.contains(keyWord)) return current ;
			path = rowPath ; 			
		}
		return - 1 ;
	}
	
	public static void stopScriptIfFail (IteratingVUserScript script , boolean status, String message) throws AbstractScriptException{
		if (status) {
			script.fail(message) ;
		}
	}
	
	public static void forms_closeWindow(IteratingVUserScript script, FormsService forms, String windowTitle) throws AbstractScriptException {
		String xpath = "//forms:window[(@name='$')]" ;				
 		forms.window(xpath.replace("$", windowTitle)).close(); 		 		
 		Common.sleep(5, TimeUnit.SECONDS);
	}
	
    public static void forms_close_w (IteratingVUserScript script, FormsService forms, String title) throws AbstractScriptException{
    	AbstractWindow [] aws = forms.getAllOpenWindows() ;
    	for (AbstractWindow aw : aws) {
    		if (aw instanceof Window && title.equals(aw.getName())) {
    		      Window win =  (Window) aw ;
    		      win.close() ;    		          		      
    		      break ;
    		}    		
    	}
    	while (forms.getAllOpenWindows().length == aws.length) {
    		Common.sleep(1, TimeUnit.SECONDS);
    	}
    }
    
    
    
    public static void forms_waitForOracleFormsReady(IteratingVUserScript script, FormsService forms, int count , int timeout, int interval)throws AbstractScriptException{
    	AbstractWindow [] aws = null ;
    	while (timeout > 0 && ((aws = forms.getAllOpenWindows()).length < count)) {
    		Common.sleep(interval, TimeUnit.SECONDS) ;
    		timeout -= interval;
    	}
    	if (aws.length < count) throw new TimeOutException();
    	for (AbstractWindow aw : aws) {
    		if (aw instanceof Window && aw.isActive()) {
    		      Window win =  (Window) aw ;
    		      win.close() ;    		          		      
    		      break ;
    		}    		
    	}    
    }
    
    public static void forms_close_active_window (IteratingVUserScript script, FormsService forms) throws AbstractScriptException{
    	AbstractWindow [] aws = forms.getAllOpenWindows() ;
    	for (AbstractWindow aw : aws) {
    		if (aw instanceof Window && aw.isActive()) {
    		      Window win =  (Window) aw ;
    		      win.close() ;    		          		      
    		      break ;
    		}    		
    	}
    	while (forms.getAllOpenWindows().length == aws.length) {
    		Common.sleep(1, TimeUnit.SECONDS);
    	}
    }
	
    public static String [] forms_getOpenWindowsTitle (IteratingVUserScript script, FormsService forms) throws AbstractScriptException{
    	AbstractWindow [] wins = forms.getAllOpenWindows() ;
    	String [] titles = new String [wins.length] ;
    	for (int i = 0 ; i < wins.length ; ++i) {
    		titles[i] = wins[i].getName();
    	}
    	return titles ;
    }
    
	public static void forms_invokeResponsibility (IteratingVUserScript script, FormsService forms) throws AbstractScriptException{
		String path = "File|Switch Responsibility..." ;
		Common.sleep(3, TimeUnit.SECONDS) ;
		Window main	= forms.window(Constant.MAIN_WINDOW);				
		forms_checkWindowStatus (script, forms, main, 60) ;				
		main.selectMenu(path);			
		ListOfValues lv = forms.listOfValues("//forms:listOfValues") ;
		forms_checkWindowStatus (script, forms, lv, 60) ;	
	}
	
	public static void forms_selectResponsibility (IteratingVUserScript script, FormsService forms, String responsibility) throws AbstractScriptException{	
		forms.listOfValues("//forms:listOfValues").select(responsibility);
	}
	
	public static void forms_spreadTableClickLink(IteratingVUserScript script, FormsService forms, String path, int row, int col) throws AbstractScriptException
	{
		forms.spreadTable(path).clickLink(row, col);

	}
	
	public static void forms_spreadTableFocusRow(IteratingVUserScript script, FormsService forms, String path, int row) throws AbstractScriptException
	{
		forms.spreadTable(path).focusRow(row);
	}
			
	
	public static void forms_selectNavigator_function (IteratingVUserScript script, FormsService forms, String path) throws AbstractScriptException{
		TreeList list = forms.treeList("//forms:treeList[(@name='NAVIGATOR_LIST_0')]") ; 
		list.selectItem(path);		
		//list.focusItem(path);
		Common.sleep(1, TimeUnit.SECONDS);
	}
	
	public static void forms_selectNavigator_Path (IteratingVUserScript script, FormsService forms, String path) throws AbstractScriptException {
		TreeList list = forms.treeList("//forms:treeList[(@name='NAVIGATOR_LIST_0')]") ;
		String [] items = path.split("\\|") ;
		StringBuilder sb = new StringBuilder ();
		for (String item : items) {			
			if (sb.length() != 0) {
			  sb.append("|") ;	
			}
			sb.append(item) ;
			list.selectItem(sb.toString()) ;
		    Common.sleep(1, TimeUnit.SECONDS);
		}		
	}
	
	
			
	public static void forms_checkWindowStatus (IteratingVUserScript script, FormsService forms, AbstractWindow win, int timeout) throws AbstractScriptException{
		Common.sleep(2, TimeUnit.SECONDS) ;
		Wait.await(script).window(win).interval(1000).atMost(timeout * 1000).untilExists();
		boolean enabled = false ;
		while (!(enabled = win.isEnabled())&& timeout-- > 0) {
	        Common.sleep(1, TimeUnit.SECONDS) ;			        
		}
		if (!enabled) throw new RuntimeException ("windows is not enabled");
		win.setFocus();
		win.activate(true);
	}

	public static void forms_clickTab(IteratingVUserScript script, FormsService forms, String container, String title) throws AbstractScriptException{		
		forms.tab(Constant.FORMS_TAB.replace("%", container)).select(title);
		Common.sleep(5, TimeUnit.SECONDS);
	}
	
	public static String forms_getText (IteratingVUserScript script, FormsService forms, String path)throws AbstractScriptException{
		return forms.textField(path).getText() ;
	}
	
	public static boolean forms_validateTextFieldExists (IteratingVUserScript script, FormsService forms, String path)throws AbstractScriptException{
		return forms.textField(path).exists() ;
	}
	
	public static boolean forms_getTextFieldEditable (IteratingVUserScript script, FormsService forms, String colName, int index)throws AbstractScriptException{
		String path = colName.replace("%", Integer.toString(index));	    
	    TextField textField = forms.textField(path) ;
	    System.out.println("----->"+textField.isEditable());
	    System.out.println(textField.isEnabled());
	    System.out.println(textField.isShowing());
	    System.out.println(textField.isVisible());
		return textField.isEditable() ;
	}
	
	public static boolean forms_checkBoxGetValue (IteratingVUserScript script, FormsService forms, String path)throws AbstractScriptException{
		return forms.checkBox(path).isSelected();
	}
	
	public static void forms_tickCheckBox (IteratingVUserScript script, FormsService forms, String path, boolean checked)throws AbstractScriptException{
		forms.checkBox(path).check(checked);
	}
	
	public static void forms_waitForStatusChanged(IteratingVUserScript script, FormsService forms, String path,
			int timeout, int interval, String expectedVal) throws AbstractScriptException {
		forms_waitForStatusChangedDoNotCloseWindow(script, forms, path, timeout, interval, expectedVal);
		forms_close_w(script,forms,"JOBS");
	}
	
	public static void forms_waitForStatusChangedDoNotCloseWindow(IteratingVUserScript script, FormsService forms, String path,
			int timeout, int interval, String expectedVal) throws AbstractScriptException {
		path = map.containsKey(path) ? map.get(path): path ;
		script.getLogger().debug("checking status") ;
		boolean changed = false;		
		while (timeout > 0) {			
			try {				
				if ((changed = expectedVal.equals(forms_getText(script,	forms,path))))break;
				Common.sleep(interval, TimeUnit.SECONDS);
				forms_clickButton(script,forms,	"//forms:button[(@name='JOBS_REFRESH_0')]");							    
			} catch (Exception e) {
				e.printStackTrace() ;
			}
			timeout -= interval;
		}			
		if (!changed) throw new RuntimeException("after : " + timeout + "seconds status is not changed to " + expectedVal);
	}
	
	
	
	public static String forms_getSubmitRequestIDByIndex (IteratingVUserScript script, FormsService forms, String path, int index) throws AbstractScriptException{		
		return forms.textField(path.replace("$", String.valueOf(index))).getText() ;
	}
	
	public static void forms_waitForStatusChangedByRequestID (IteratingVUserScript script, FormsService forms, String path , String requestID, int timeout, int interval, String expectedVa) throws AbstractScriptException{		  
		  forms_clickButton(script,forms,	"//forms:button[(@name='JOBS_CHANGE_QUERY_0')]");	
		  Wait.await(script).window(forms.window("//forms:window[(@name='JOBS_QF')]")).interval(1000).atMost(120 * 1000).untilExists() ;
		  forms_selectRadioButton (script,forms,"JOBS_QF_WHICH_JOBS_SPECIFIC_JOBS_0");
		  forms.textField("//forms:textField[(@name='JOBS_QF_REQUEST_ID_0')]").setText(requestID) ;
		  forms_clickButton(script,forms,	"//forms:button[(@name='JOBS_QF_FIND_0')]");
		  Wait.await(script).window(forms.window("//forms:window[(@name='JOBS_QF')]")).interval(1000).atMost(120 * 1000).untilNotExists();
		  forms_waitForStatusChangedDoNotCloseWindow (script, forms, path, timeout,interval,expectedVa) ;		  		 
	}
	
	public static void forms_waitForStatusChangedByName (IteratingVUserScript script, FormsService forms, String path , String name, int timeout, int interval, String expectedVa) throws AbstractScriptException{
		  forms_findRequestByName(script, forms,name);
		  forms_waitForStatusChangedDoNotCloseWindow (script, forms, path, timeout,interval,expectedVa) ;
	}
	
	public static String forms_findRequestByName (IteratingVUserScript script, FormsService forms, String name) throws AbstractScriptException{
		  forms_clickButton(script,forms,	"//forms:button[(@name='JOBS_CHANGE_QUERY_0')]");
		  Wait.await(script).window(forms.window("//forms:window[(@name='JOBS_QF')]")).interval(1000).atMost(120 * 1000).untilExists() ;		  
		  forms_selectRadioButton (script,forms,"JOBS_QF_WHICH_JOBS_SPECIFIC_JOBS_0");
		  forms_clickButton(script,forms,"//forms:button[(@name='JOBS_QF_CLEAR_0')]");
		  forms.textField("//forms:textField[(@name='JOBS_QF_PROGRAM_0')]").setText(name) ;
		  forms_clickButton(script,forms,	"//forms:button[(@name='JOBS_QF_FIND_0')]");
		  Wait.await(script).window(forms.window("//forms:window[(@name='JOBS_QF')]")).interval(1000).atMost(120 * 1000).untilNotExists();
		  return forms.textField("//forms:textField[(@name='JOBS_REQUEST_ID_0')]").getText() ;
	}
	
	public static void forms_activateMainWindow (IteratingVUserScript script, FormsService forms) throws AbstractScriptException{		
		forms.window(Constant.MAIN_WINDOW).activate(true);		
	}
	
	public static void forms_textFieldOpenDialog(IteratingVUserScript script, FormsService forms, String colName, int index) throws AbstractScriptException{		
	    String path = colName.replace("%", Integer.toString(index)) ;	    
	    TextField textField = forms.textField(path) ;
	    textField.openDialog();
	    Common.sleep(1, TimeUnit.SECONDS);				
	}
	
	public static void forms_flexWindowOpenDialog(IteratingVUserScript script, FormsService forms, String colName) throws AbstractScriptException{		
		forms.flexWindow("//forms:flexWindow").openDialog(colName, "");
	    Common.sleep(1, TimeUnit.SECONDS);				
	}
	
	public static void forms_activateWindow(IteratingVUserScript script, FormsService forms, String title) throws AbstractScriptException{		
		Window main	= forms.window("//forms:window[(@name='%')]".replace("%", title));
		main.activate(true);
	    Common.sleep(2, TimeUnit.SECONDS);				
	}
	
	public static void forms_selectItemInListOfValues(IteratingVUserScript script, FormsService forms, String item) throws AbstractScriptException{		
		forms.listOfValues("//forms:listOfValues").select(item);
	    Common.sleep(2, TimeUnit.SECONDS);				
	}
	
	public static void forms_selectFirstItemInListOfValues(IteratingVUserScript script, FormsService forms) throws AbstractScriptException{		
		forms.listOfValues("//forms:listOfValues").choose(1);
	    Common.sleep(2, TimeUnit.SECONDS);				
	}
	
	public static void forms_selecttemInListOfValues(IteratingVUserScript script, FormsService forms, String item) throws AbstractScriptException{		
		forms.listOfValues("//forms:listOfValues").select(item);
	    Common.sleep(2, TimeUnit.SECONDS);				
	}
		
	public static void forms_selectItemInList(IteratingVUserScript script, FormsService forms, String listName, String item) throws AbstractScriptException{		
		List list = forms.list("//forms:list[(@name='%')]".replace("%", listName));
		list.selectItem(item);
	    Common.sleep(2, TimeUnit.SECONDS);				
	}
	
	public static void forms_choiceBox_clickButton(IteratingVUserScript script, FormsService forms, String btnName) throws AbstractScriptException{
		ChoiceBox box = forms.choiceBox("//forms:choiceBox") ;
		if (box.exists()) {
			 box.clickButton(btnName);
			 Common.sleep(2, TimeUnit.SECONDS);
		}			    				
	}
	
	public static void forms_selectRadioButton(IteratingVUserScript script, FormsService forms, String name) throws AbstractScriptException{
		RadioButton radio = forms.radioButton("//forms:radioButton[(@name='%')]".replace("%", name));
		radio.select();		
	    Common.sleep(2, TimeUnit.SECONDS);
	}
	
	
}

