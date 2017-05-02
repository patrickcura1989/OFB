
import java.io.IOException;
import java.io.StringReader;

import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.http.api.*;
import oracle.oats.scripting.modules.http.api.HTTPService.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webService.api.*;
import oracle.oats.scripting.modules.webService.api.WSService.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
@SuppressWarnings("unused")

public class script extends IteratingVUserScript {
      @ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
      @ScriptService oracle.oats.scripting.modules.http.api.HTTPService http;
      @ScriptService oracle.oats.scripting.modules.webService.api.WSService ws;

      public void initialize() throws Exception {
      }

      /**
      * Add code to be executed each iteration for this virtual user.
      */
      public void run() throws Exception {

      }

      /**
      * Add any cleanup code or perform any operations after all iterations are performed.
      */
      public void finish() throws Exception {

      }

      public void elementNotNull(String responseTag) throws Exception{        
            String InfoDecription="";
            
            if(responseTag.contains("@"))
            {
                  
             String attributeValue = getResponseXMLforAttribute(responseTag);
             
             if(attributeValue.equals(""))
                  {
                        InfoDecription = "The tag "+responseTag+" does not exist in the Response. Value is blank";
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                       

                  }else{
                        InfoDecription = "The tag "+responseTag+" containing the value "+attributeValue+" is present in the response. The value is not null";
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                                                                 
                  }     
                  
            }else{
            
                  if(!responseTag.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                  
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response. Value is blank";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                       

                        }else{
                              InfoDecription = "The tag "+responseTag+" containing the value "+getVariables().get("responseTagValue")+" is present in the response. The value is not null";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                                                                 
                        }                             
                  }                 
            
                  }
            
            
            
      }

      public void elementIsNull(String responseTag) throws Exception{         
            String InfoDecription="";
            if(responseTag.contains("@"))
            {
                  
             String attributeValue = getResponseXMLforAttribute(responseTag);
             
             if(attributeValue.equals(""))
                  {
                        InfoDecription = "The tag "+responseTag+" does not exist in the Response. Value is blank";
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                       

                  }else{
                        InfoDecription = "The tag "+responseTag+" containing the value "+getVariables().get("responseTagValue")+" is present in the response. The value is not null";
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                                                 
                  }     
                  
            }else{
            
            
            
            if(!responseTag.equals(""))
            {
                  http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,true, Source.Html, 0); // negative scenario should be true..check other cases.
                  
                  if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                  {
                        InfoDecription = "The tag "+responseTag+" does not exist in the Response. Value is blank";
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                       

                  }else{
                        InfoDecription = "The tag "+responseTag+" containing the value "+getVariables().get("responseTagValue")+" is present in the response. The value is not null";
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                                                 
                  }                             
            }                 
      }
      }

      public void elementValueEquals(String responseTag,String expectedValue) throws Exception{

            if(responseTag.contains("@"))
            {
                  attributeElementValueEquals( responseTag, expectedValue);
            }
            else{
            String InfoDecription="";                 
            if(!responseTag.equals(""))
            {           
                  if(!expectedValue.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              getVariables().get("responseTagValue").toString().split("");
                              
                              if(expectedValue.contains("||")) 
                              {
                                    boolean matched=false;
                                    String[] expectedValues=expectedValue.split("||");
                                    for(int i=0;i<expectedValues.length;i++)
                                    {
                                          if(expectedValues[i].equalsIgnoreCase(getVariables().get("responseTagValue")))
                                                matched=true;
                                          if(matched)
                                                break;

                                    }
                                    if(matched)
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value is as expected and response value is "+getVariables().get("responseTagValue");
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                                    }
                                    else
                                    {
                                          InfoDecription =  "The Response element's ("+responseTag+") value is NOT as expected and Response value is "+getVariables().get("responseTagValue");
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                                    }


                              }
                              else
                              {                                               
                                    if(getVariables().get("responseTagValue").equalsIgnoreCase(expectedValue))
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value is as expected and response value is "+getVariables().get("responseTagValue");
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                                    }
                                    else
                                    {
                                          InfoDecription =  "The Response element's ("+responseTag+") value is NOT as expected and Response value is "+getVariables().get("responseTagValue")+" and the expected value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                                    }
                              }
                        }
                  }                 
            }
            }
      }

      public void elementValueNotEquals(String responseTag,String expectedValue) throws Exception{

            String InfoDecription="";     
            
            if(responseTag.contains("@"))
            {
                  attributeElementValueNotEquals( responseTag, expectedValue);
            }
            else{
            
            if(!responseTag.equals(""))
            {           
                  if(!expectedValue.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {

                              if(expectedValue.contains("&&")) 
                              {
                                    boolean matched=false;
                                    String[] expectedValues=expectedValue.split("&&");
                                    for(int i=0;i<expectedValues.length;i++)
                                    {
                                          if(expectedValues[i].equalsIgnoreCase(getVariables().get("responseTagValue")))
                                                matched=true;
                                          if(matched)
                                                break;

                                    }
                                    if(!matched)
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT equal to any of the expected values. The returned value is "+getVariables().get("responseTagValue");
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                                    }
                                    else
                                    {
                                          InfoDecription =  "The Response element's ("+responseTag+") value: "+getVariables().get("responseTagValue")+"  is equal to one of the expected value";
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                                    }

                              }
                              else
                              {                             
                                    if(getVariables().get("responseTagValue").equalsIgnoreCase(expectedValue))
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is same as expected value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                                    }
                                    else
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT same as expected value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                                   
                                    }
                              }
                        }
                  }                 
            }
            }
      }

      public void elementValueGreaterThan(String responseTag,String valueToCompare) throws Exception{

            String InfoDecription="";
            
            if(responseTag.contains("@"))
            {
                  attributeElementValueGreaterThan(responseTag, valueToCompare);
            }
            else{
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(getVariables().get("responseTagValue")) > Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is greater than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT greater than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }
            }
      }

      public void elementValueGreaterThanEqualTo(String responseTag,String valueToCompare) throws Exception{

            String InfoDecription="";
            if(responseTag.contains("@"))
            {
                  attributeElementValueGreaterThanEqualTo(responseTag, valueToCompare);
            }
            else{
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(getVariables().get("responseTagValue")) >= Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is as expected";
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT greater than or equal to the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }     
            }
      }

      public void elementValueLesserThan(String responseTag,String valueToCompare) throws Exception{

            String InfoDecription="";
            if(responseTag.contains("@"))
            {
                  attributeElementValueLesserThan(responseTag, valueToCompare);
            }
            else{
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(getVariables().get("responseTagValue")) < Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is lesser than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT lesser than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }     
            }
      }

      public void elementValueLesserThanEqualTo(String responseTag,String valueToCompare) throws Exception{

            String InfoDecription="";
            
            
            if(responseTag.contains("@"))
            {
                  attributeElementValueLesserThanEqualTo(responseTag, valueToCompare);
            }
            else{
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                        if(getVariables().get("responseTagValue").equalsIgnoreCase("{{responseTagValue}}"))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(getVariables().get("responseTagValue")) <= Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is as expected";
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT lesser than or equal to the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }           
            }
      }

      public void elementContains(String responseTag,String valueToBeChecked) throws Exception{

            String InfoDecription="";     
            if(responseTag.contains("@"))
            {
                  attributeElementContains(responseTag, valueToBeChecked);
            }
            else{
            if(!responseTag.equals(""))
            {           
                  http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                  if(valueToBeChecked.contains("||")) 
                  {
                        boolean matched=false;
                        String[] expectedValues=valueToBeChecked.split("||");
                        for(int i=0;i<expectedValues.length;i++)
                        {
                              if(getVariables().get("responseTagValue").toLowerCase().contains(expectedValues[i].toLowerCase()))
                                    matched=true;
                              if(matched)
                                    break;

                        }
                        if(matched)
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value contains one of the expected value ";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value does NOT contain any of the expected value, Response value is "+getVariables().get("responseTagValue");                         
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                        }

                  }
                  else{

                        if(getVariables().get("responseTagValue").toLowerCase().contains(valueToBeChecked.toLowerCase()))
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value contains the expected value "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value does NOT contain the expected value, Response value is "+getVariables().get("responseTagValue")+" and the value to be present in the response is "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                        }
                  }
            }           
            }
      }

      public void elementNotContains(String responseTag,String valueToBeChecked) throws Exception{

            String InfoDecription="";
            
            if(responseTag.contains("@"))
            {
                  attributeElementNotContains(responseTag, valueToBeChecked);
            }
            else{
            if(!responseTag.equals(""))
            {           
                  http.solve("responseTagValue", "<"+responseTag+">(.+?)</"+responseTag+">", null,false, Source.Html, 0);
                  if(valueToBeChecked.contains("&&")) 
                  {
                        boolean matched=false;
                        String[] expectedValues=valueToBeChecked.split("&&");
                        for(int i=0;i<expectedValues.length;i++)
                        {
                              if(getVariables().get("responseTagValue").toLowerCase().contains(expectedValues[i].toLowerCase()))
                                    matched=true;
                              if(matched)
                                    break;

                        }
                        if(!matched)
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value does not contain any of the expected value ";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value contains one of the expected value, Response value is "+getVariables().get("responseTagValue");                         
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                        }

                  }
                  else{


                        if(getVariables().get("responseTagValue").toLowerCase().contains(valueToBeChecked.toLowerCase()))
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value contains the expected value "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value does NOT contain the expected value, Response value is "+getVariables().get("responseTagValue")+" and the value to be present in the response is "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                                   
                        }                             
                  }                 
            }
            }


      }

      public void ResponseNotContains(String value)throws Exception{
            String matchedValue="";
            String InfoDecription="";
            if(!value.equals(""))
            {                 

                  if(value.contains("&&")) 
                  {
                        boolean matched=false;
                        String[] expectedValues=value.split("&&");
                        for(int i=0;i<expectedValues.length;i++)
                        {
                              if(http.getLastResponseContents().toLowerCase().contains(expectedValues[i].toLowerCase()))
                                    matched=true;
                              if(matched){
                                    matchedValue=expectedValues[i];
                                    break;
                              }

                        }
                        if(!matched)
                        {
                              InfoDecription = "The response does not contain any of the provided values";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                        }
                        else
                        {
                              InfoDecription =  "The response contains one of the provided values "+matchedValue;                          
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                        }

                  }
                  else{
                        if(http.getLastResponseContents().toLowerCase().contains(value.toLowerCase()))
                        {
                              InfoDecription = "The response contains value "+value;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              InfoDecription = "The response does not contain the value "+value;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                 
                        }           
                  } 
            }
      }

      public void responseContains(String value)throws Exception{
            String matchedValue="";
            String InfoDecription="";
            if(!value.equals(""))
            {                 if(value.contains("&&")) 
            {
                  boolean matched=false;
                  String[] expectedValues=value.split("&&");
                  for(int i=0;i<expectedValues.length;i++)
                  {
                        if(http.getLastResponseContents().toLowerCase().contains(expectedValues[i].toLowerCase()))
                              matched=true;
                        if(matched){
                              matchedValue=expectedValues[i];
                              break;
                        }

                  }
                  if(matched)
                  {
                        InfoDecription = "The response contains one of the provided values "+matchedValue;               
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                  }
                  else
                  {
                        InfoDecription =  "The response does not contain any of the provided values";                
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                  }

            }
            else{
                  if(http.getLastResponseContents().toLowerCase().contains(value.toLowerCase()))
                  {
                        InfoDecription = "The response contains value "+value;
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                  }
                  else
                  {
                        InfoDecription = "The response does not contain the value "+value;
                        getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                 
                  }           
            } 

            }


      }
      
      private String getResponseXMLforAttribute(String nodeNameWithAttribute) throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
       // http.solve("responseXML", "<(.+?)body>(.+?)</(.+?)body>", null,false, Source.Html, 0);
        info("inside the func getResponseXMLforAttribute"+http.getLastResponseContents().toString());
        
        
        
        Document document = db.parse(new InputSource(new StringReader((http.getLastResponseContents().toString()))));
        info("before splitting string");
        
        String[] tagItems=nodeNameWithAttribute.split("@");
        info("after splitting string");
        if(nodeNameWithAttribute.contains("/"))
        {
        	info("calling new func inside  the func getResponseXMLforAttribute");
            return retrieveAttributeValue(document,tagItems[0],tagItems[1]);
             
        }else{
        info("inside else");
        NodeList nodeList = document.getElementsByTagName(tagItems[0]);
        String attributeValue="";
        for(int x=0,size= nodeList.getLength(); x<size; x++) {
            System.out.println(nodeList.item(x).getAttributes().getNamedItem(tagItems[1]).getNodeValue());
            if(x>0)
            attributeValue=attributeValue+","+nodeList.item(x).getAttributes().getNamedItem(tagItems[1]).getNodeValue();
            else
            attributeValue=nodeList.item(x).getAttributes().getNamedItem(tagItems[1]).getNodeValue();
         }
        return attributeValue;
        }
    }
      
      
      private String retrieveAttributeValue(Document document, String xpath, String attribute) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, AbstractScriptException {
          XPath xPath = XPathFactory.newInstance().newXPath();
          info("inside the func retrieveAttributeValue with xpath and attribute"+xpath+","+attribute);
          NodeList result = (NodeList) xPath.evaluate(xpath + "/@" + attribute, document, XPathConstants.NODESET);
          String attrValue="";
          for(int i=0; i<result.getLength();i++){
          if(i==0)attrValue=result.item(i).getNodeValue();
          else
            attrValue=attrValue+","+result.item(i).getNodeValue();
          }
          return attrValue;
      }
      
      
      
      
      
      
      public String setVariableFromResponseFunc(String elementXpath) throws Exception
      {
            String elementValue="";
            info("CALLED this func with xpath"+elementXpath);
            if(elementXpath.contains("@"))
                  elementValue=getResponseXMLforAttribute(elementXpath);
            else
            {
                  http.solve("responseElementValue","<"+elementXpath+">(.+?)</"+elementXpath+">", null, false, Source.Html,null);
                  String[] responseElementValueArray=getVariables().getAll("responseElementValue");
                  if(responseElementValueArray!=null && responseElementValueArray.length>0)
                  {
                        elementValue=responseElementValueArray[0];
                        for(int i=1;i<responseElementValueArray.length;i++)
                              elementValue=elementValue+","+responseElementValueArray[i];
                  }
                  else
                  {
                        elementValue="";
                  }
                  
            }
            info("Element value being returned is "+elementValue);
            return elementValue;
            
      }
      
      
      
      

      

      
      private void attributeElementValueEquals( String responseTag,String expectedValue) throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";                 
            if(!responseTag.equals(""))
            {           
                  if(!expectedValue.equals(""))
                  {
                        
                        if(responseValues.equalsIgnoreCase(""))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(expectedValue.contains("||")) 
                              {
                                    boolean matched=false;
                                    String[] expectedValues=expectedValue.split("||");
                                    for(int i=0;i<expectedValues.length;i++)
                                    {
                                          if((responseValues.contains(expectedValues[i])&& responseValues.contains(",")) || responseValues.equals(expectedValues[i]))
                                                matched=true;
                                          if(matched)
                                                break;

                                    }
                                    if(matched)
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value is as expected and response value is "+getVariables().get("responseTagValue");
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                                    }
                                    else
                                    {
                                          InfoDecription =  "The Response element's ("+responseTag+") value is NOT as expected and Response value is "+getVariables().get("responseTagValue");
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                                    }


                              }
                              else
                              {                                               
                                    if(responseValues.contains(expectedValue)||responseValues.equals(expectedValue))
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value is as expected and response value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                                    }
                                    else
                                    {
                                          InfoDecription =  "The Response element's ("+responseTag+") value is NOT as expected and Response value is "+responseValues+" and the expected value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                                    }
                              }
                        }
                  }                 
            }
            
      }
      
      
      
      private void attributeElementValueNotEquals( String responseTag,String expectedValue) throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";                 
            if(!responseTag.equals(""))
            {           
                  if(!expectedValue.equals(""))
                  {
                        
                        if(responseValues.equalsIgnoreCase(""))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(expectedValue.contains("&&")) 
                              {
                                    boolean matched=false;
                                    String[] expectedValues=expectedValue.split("&&");
                                    for(int i=0;i<expectedValues.length;i++)
                                    {
                                          if((responseValues.contains(expectedValues[i])&& responseValues.contains(",")) || responseValues.equals(expectedValues[i]))
                                                matched=true;
                                          if(matched)
                                                break;

                                    }
                                    if(!matched)
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value: "+getVariables().get("responseTagValue")+" is NOT equal to any of the expected values. The returned value is "+responseValues;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                                    }
                                    else
                                    {
                                          InfoDecription =  "The Response element's ("+responseTag+") value: "+responseValues+"  is equal to one of the expected value";
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                                    }

                              }
                              else
                              {     
                                    if((responseValues.contains(expectedValue)&& responseValues.contains(",")) || responseValues.equals(expectedValue))
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is same as expected value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                                    }
                                    else
                                    {
                                          InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is NOT same as expected value is "+expectedValue;
                                          getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                                   
                                    }
                              }
                              
                              
                        }
                  }                 
            }
            
      }
      
      
      
      
      private void attributeElementValueGreaterThan(String responseTag,String valueToCompare) throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        
                        if(responseValues.equalsIgnoreCase(""))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(responseValues) > Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is greater than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is NOT greater than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }
            
            
            
      }
      
      private void attributeElementValueGreaterThanEqualTo(String responseTag,String valueToCompare) throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        
                        if(responseValues.equalsIgnoreCase(""))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(responseValues) >= Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is as expected";
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is NOT greater than or equal to the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }     
      }
      
      private void attributeElementValueLesserThan(String responseTag,String valueToCompare) throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        
                        if(responseValues.equalsIgnoreCase(""))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(responseValues) < Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is lesser than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is NOT lesser than the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }     
            
      }
      
      
      private void attributeElementValueLesserThanEqualTo(String responseTag,String valueToCompare) throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";
            
            if(!responseTag.equals(""))
            {           
                  if(!valueToCompare.equals(""))
                  {
                        
                        if(valueToCompare.equalsIgnoreCase(""))
                        {
                              InfoDecription = "The tag "+responseTag+" does not exist in the Response.";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              if(Integer.parseInt(responseValues) <= Integer.parseInt(valueToCompare))
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is as expected";
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                              }
                              else
                              {
                                    InfoDecription = "The Response element's ( "+responseTag+" ) value: "+responseValues+" is NOT lesser than or equal to the expected value is "+valueToCompare;
                                    getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                              }
                        }
                  }
            }           
            
            
      }
      
      private void attributeElementContains(String responseTag,String valueToBeChecked)throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";
            
            if(!responseTag.equals(""))
            {           
                  
                  if(valueToBeChecked.contains("||")) 
                  {
                        boolean matched=false;
                        String[] expectedValues=valueToBeChecked.split("||");
                        for(int i=0;i<expectedValues.length;i++)
                        {
                              if(responseValues.toLowerCase().contains(expectedValues[i].toLowerCase()))
                                    matched=true;
                              if(matched)
                                    break;

                        }
                        if(matched)
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value contains one of the expected value ";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value does NOT contain any of the expected value, Response value is "+responseValues;                             
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                        }

                  }
                  else{

                        if(responseValues.toLowerCase().contains(valueToBeChecked.toLowerCase()))
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value contains the expected value "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                             
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value does NOT contain the expected value, Response value is "+responseValues+" and the value to be present in the response is "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   
                        }
                  }
            }           
            
      }
      
      
      
      private void attributeElementNotContains(String responseTag,String valueToBeChecked)throws Exception
      {
            String responseValues=getResponseXMLforAttribute(responseTag);
            String InfoDecription="";
            
            
            if(!responseTag.equals(""))
            {           
                  
                  if(valueToBeChecked.contains("&&")) 
                  {
                        boolean matched=false;
                        String[] expectedValues=valueToBeChecked.split("&&");
                        for(int i=0;i<expectedValues.length;i++)
                        {
                              if(responseValues.toLowerCase().contains(expectedValues[i].toLowerCase()))
                                    matched=true;
                              if(matched)
                                    break;

                        }
                        if(!matched)
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value does not contain any of the expected value ";
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");     
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value contains one of the expected value, Response value is "+responseValues;                         
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                                   

                        }

                  }
                  else{


                        if(responseValues.toLowerCase().contains(valueToBeChecked.toLowerCase()))
                        {
                              InfoDecription = "The Response element's ( "+responseTag+" ) value contains the expected value "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Failed");                             
                        }
                        else
                        {
                              InfoDecription =  "The Response element's ("+responseTag+") value does NOT contain the expected value, Response value is "+responseValues+" and the value to be present in the response is "+valueToBeChecked;
                              getScript("WSCOMMONLIB").callFunction("logComments", getVariables().get("gStrOutputFileName"),InfoDecription, "Passed");                                   
                        }                             
                  }                 
            }
            
      }

}