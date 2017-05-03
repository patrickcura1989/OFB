import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import oracle.oats.scripting.modules.basic.api.IteratingVUserScript;
import oracle.oats.scripting.modules.basic.api.ScriptService;
import oracle.oats.scripting.modules.webdom.api.elements.DOMCheckbox;
import oracle.oats.scripting.modules.webdom.api.elements.DOMElement;
import oracle.oats.scripting.modules.webdom.api.elements.DOMSelect;
import oracle.oats.scripting.modules.webdom.api.elements.DOMText;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.basic.api.*;

public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	
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
	
	public void clickLink(String linkText) throws Exception {
		
			String windowPath = getCurrentWindowXPath();
			
			int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
			
			String docPath = windowPath + "/web:document[@index='"+currentWindowIndex+"']" ;
			List<DOMElement> linkList = web.document(docPath).getElementsByTagName("a");
			
			String linkDetails [] = linkText.split("_;");
			
			for(int i=0;i<linkList.size();i++){
				
				if(linkList.get(i)!=null){
					
					if(linkList.get(i).getAttribute("text")!=null){
						
						//System.out.println("-->"+linkList.get(i).getAttribute("text").toString());
						
						if(linkList.get(i).getAttribute("text").trim().equalsIgnoreCase(linkText)){  // Clicking only specific Link
								// trim is used because some elements have trailing empty spaces.
								try{
									if(linkDetails.length>1){	
										
										linkList.get(Integer.parseInt(linkDetails[1])).click(); //Click on Specified index passed							
									}
									
									else{
										
										linkList.get(i).click();								
									}
								}
								catch(IndexOutOfBoundsException e){
									
									reportFailure("Link not found for the specified link Name"+ linkText);
									
									throw new Exception("clickLink : link not found for the specified link Name");
									
								} // end of try-Catch
						}
						
							
						
					}
				}
			}
				
		}

	public void clickSubmitButton(String buttonName) throws Exception {
		
	String windowPath = getCurrentWindowXPath();
		
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
		String docPath = windowPath + "/web:document[@index='"+currentWindowIndex+"']" ;
		List<DOMElement> buttonList = web.document(docPath).getElementsByName(buttonName);
		
		String buttonDetails [] = buttonName.split(";");
		
		for(int i=0;i<buttonList.size();i++){
			
			if(buttonList.get(i)!=null){
				
				if(buttonList.get(i).getAttribute("type")!=null){
					
					if(buttonList.get(i).getAttribute("type").equals("submit")){  // Clicking buttons of submit type
						
							try{
								if(buttonDetails.length>1){								
									buttonList.get(Integer.parseInt(buttonDetails[1])).click(); //Click on Specified index passed							
								}
								
								else{								
									buttonList.get(i).click();								
								}
							}
							catch(IndexOutOfBoundsException e){
								
								reportFailure("Button not found for the specified button Name"+ buttonName);
								
								throw new Exception("clickSubmitButton : Button not found for the specified button Name");
								
							} // end of try-Catch
					}
				}
			}
		}
			
	}

	public void clickImage(String imageName) throws Exception{
		
		String windowPath = getCurrentWindowXPath();
		
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
		String docPath = windowPath + "/web:document[@index='"+currentWindowIndex+"']" ;
		List<DOMElement> imageList = web.document(docPath).getElementsByName(imageName);
		
		String imageDetails [] = imageName.split(";");
		
		for(int i=0;i<imageList.size();i++){
			
			if(imageList.get(i)!=null){
				
				if(imageList.get(i).getAttribute("type")!=null){
					
					if(imageList.get(i).getAttribute("type").equals("image")){  // Clicking only images
						
							try{
								if(imageDetails.length>1){								
									imageList.get(Integer.parseInt(imageDetails[1])).click(); //Click on Specified index passed							
								}
								
								else{								
									imageList.get(i).click();								
								}
							}
							catch(IndexOutOfBoundsException e){
								
								reportFailure("No image found for the specified image Name"+ imageName);
								
								throw new Exception("clickImage : No image found for the specified image Name");
								
							} // end of try-Catch
					}
				}
			}
		}
		
	}

	private String getCurrentWindowXPath() throws Exception {
		
		String index = web.getFocusedWindow().getAttribute("index");
		String title = web.getFocusedWindow().getTitle();
		
		String xPath = "/web:window[@index='"+index+"' or @title='"+title+"']";
		
		return xPath;
	}

	@SuppressWarnings("unchecked")
	public void selectListBox(String name, String valueToSelect) throws Exception{
		
		String windowPath = getCurrentWindowXPath();
		
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
		String docPath = windowPath + "/web:document[@index='*']" ;
		List<DOMElement> bodyList = web.document(docPath).getElementsByTagName("body");
		DOMElement body = bodyList.get(0);
		
		 
		String imageDetails [] = name.split(";");
		
		List<DOMElement> selectList  = body.getElementsByTagName("SELECT");
		
		List<DOMElement> requiredSelectBoxList = new ArrayList<DOMElement>();
		
		for(DOMElement list : selectList){
			
			if(list.getAttribute("name").contains(imageDetails[0])){
				
				requiredSelectBoxList.add(list);
				
			}
		}
		
		try{
			if(imageDetails.length>1){
			
				DOMSelect selectbox = (DOMSelect) requiredSelectBoxList.get(Integer.parseInt(imageDetails[1]));
				
				selectbox.selectOptionByText(valueToSelect);
			
			}
			
			else{
				
				DOMSelect selectbox = (DOMSelect) requiredSelectBoxList.get(0);
				
				selectbox.selectOptionByText(valueToSelect);
				
			}}
			catch(IndexOutOfBoundsException e){
				
				reportFailure("No selectbox found for the specified selectbox Name"+ name);
				
				throw new Exception("selectListBox : No selectbox found for the specified selectbox Name");
				
			}
			
	}
	
	public void  webSetText(String name, String valueToSet) throws Exception{
		
		String windowPath = getCurrentWindowXPath();
		
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
		String docPath = windowPath + "/web:document[@index='*']" ;
		List<DOMElement> bodyList = web.document(docPath).getElementsByTagName("body");
		DOMElement body = bodyList.get(0);
		
		 
		String imageDetails [] = name.split(";");
		List<DOMElement> textFieldList  = body.getElementsByTagName("input");
		List<DOMElement> requiredTextFieldList = new ArrayList<DOMElement>();
		
		for(DOMElement textField : textFieldList){
			
			if((textField.getAttribute("type").equals("text"))&&(textField.getAttribute("name").equals(imageDetails[0]))){
				
				requiredTextFieldList.add(textField);
				
			}
		}
		System.out.println(requiredTextFieldList.size());
		try{
			if(imageDetails.length>1){
			
				DOMText textField = (DOMText) requiredTextFieldList.get(Integer.parseInt(imageDetails[1]));
				textField.setText(valueToSet);
			
			}
			
			else{
				
				DOMText textField = (DOMText) requiredTextFieldList.get(0);
				
				textField.setText(valueToSet);
				
			}}
			catch(IndexOutOfBoundsException e){
				
				reportFailure("No textField found for the specified  Name"+ name);
				
				throw new Exception("webSetText : No textField found for the specified textField Name");
				
			}
		}
	@SuppressWarnings("unchecked")
	public String[] getListBoxValue(String name ) throws Exception{
		
		String windowPath = getCurrentWindowXPath();
		
		int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
		
		String docPath = windowPath + "/web:document[@index='*']" ;
		List<DOMElement> bodyList = web.document(docPath).getElementsByTagName("body");
		DOMElement body = bodyList.get(0);
		
		 
		String imageDetails [] = name.split(";");
		
		List<DOMElement> selectList  = body.getElementsByTagName("SELECT");
		
		List<DOMElement> requiredSelectBoxList = new ArrayList<DOMElement>();
		
		for(DOMElement list : selectList){
			
			if(list.getAttribute("name").contains(imageDetails[0])){
				
				requiredSelectBoxList.add(list);
				
			}
		}
		
		try{
			if(imageDetails.length>1){
			
				DOMSelect selectbox = (DOMSelect) requiredSelectBoxList.get(Integer.parseInt(imageDetails[1]));
				
				return selectbox.getSelectedText();
			
			}
			
			else{
				
				DOMSelect selectbox = (DOMSelect) requiredSelectBoxList.get(0);
				
				return selectbox.getSelectedText();
				
			}}
			catch(IndexOutOfBoundsException e){
				
				reportFailure("No selectbox found for the specified selectbox Name"+ name);
				
				throw new Exception("selectListBox : No selectbox found for the specified selectbox Name");
				
			}

	}

	@SuppressWarnings("unchecked") 
	public void selectCheckBox(String name, String state) throws Exception{
	
	String windowPath = getCurrentWindowXPath();
	
	int currentWindowIndex = Integer.parseInt(web.window(windowPath).getAttribute("index"));
	
	String docPath = windowPath + "/web:document[@index='*']" ;
	List<DOMElement> bodyList = web.document(docPath).getElementsByTagName("body");
	DOMElement body = bodyList.get(0);
	
	 
	String imageDetails [] = name.split(";");
	List<DOMElement> checkBoxList  = body.getElementsByTagName("input");
	System.out.println(checkBoxList.size());
	List<DOMElement> requiredSelectBoxList = new ArrayList<DOMElement>();
	
	for(DOMElement checkBox : checkBoxList){
		
		if((checkBox.getAttribute("type").equals("checkbox"))&&(checkBox.getAttribute("name").equals(imageDetails[0]))){
			
			requiredSelectBoxList.add(checkBox);
			
		}
	}
	System.out.println(requiredSelectBoxList.size());
	try{
		if(imageDetails.length>1){
		
			DOMCheckbox checkbox = (DOMCheckbox) requiredSelectBoxList.get(Integer.parseInt(imageDetails[1]));
			checkbox.check(toBoolean(state));
		
		}
		
		else{
			
			DOMCheckbox checkbox = (DOMCheckbox) requiredSelectBoxList.get(0);
			
			checkbox.check(toBoolean(state));
			
		}}
		catch(IndexOutOfBoundsException e){
			
			reportFailure("No checkbox found for the specified  Name"+ name);
			
			throw new Exception("selectCheckBox : No checkbox found for the specified checkbox Name");
			
		}

}
	

}
