
import groovy.util.Node;
import groovy.util.NodeList;
import groovy.xml.MarkupBuilder;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.bouncycastle.util.encoders.Base64;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;
import com.predic8.wstool.creator.RequestTemplateCreator;
import com.predic8.wstool.creator.SOARequestCreator;

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
import system.DateTime;
import lib.*;
@SuppressWarnings("unused")

public class script extends IteratingVUserScript {
                @ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
                @ScriptService oracle.oats.scripting.modules.http.api.HTTPService http;
                @ScriptService oracle.oats.scripting.modules.webService.api.WSService ws;
                String productHomePath="";
                String productURLVariableName="";
                String InfoDecription="";
                boolean gBlnReportDBLog_RunStatus = true;
                String gStrReportDBLog_Scenarios = "";
                //multi list related variables -start
                int multiListCSVCurrentCount=0;
                int multiListCSVTotalCount=0;
                int parentXMLNodeIndex=-1;
                String parentPKXpath="";
                String childFKXpath="";
                int minimumListSizeForComplexMultiList=0;
                boolean xmlPartOfComplexMultiList=false;
                String integrationURL="";
                Element SelectedParentFromPreviousCSV;
                boolean singleElementXML=false;//added to sgg test harness with single elements.
                //multi list related variables -End
                Document docco = new Document(new Element("testRoot"));

                HashMap <String,Integer> DatabankRowIdTracker = new HashMap<String,Integer>();
                HashMap <Integer,String> DatabankPkXpathTracker = new HashMap<Integer,String>();
                HashMap <Integer,String> DatabankFkXpathTracker = new HashMap<Integer,String>();
                HashMap <String,Element> DatabankPkElementCSVTracker = new HashMap<String,Element>();
                int fkRefValueForCurrentRow=-1;
                @FunctionLibrary("OUTSPCORELIB") lib.oracle.outsp.core.OUTSPCORELIB oUTSPCORELIB;
                
                //HashMap <String,String> DatabankMultiListRowsTracker = new HashMap<String,String>();


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


                public  void initalizeSetUp(String outputFileName,String runTimePath,String strScriptPath)
                {
                                try
                                {
                                                String environmentPropFile=new File(getScriptPackage().getScriptPath()).getParentFile().getAbsolutePath()+"\\..\\..\\..\\etc\\configuration.properties";
                                                File f = new File(environmentPropFile);
                                                LoadGlobalVariablesFromPropertiesFile(environmentPropFile); 
                                                System.out.println("File Name in scenario:: "+outputFileName);
                                                CreateFile(outputFileName);

                                                //Setup Databank Path
                                                String strScriptPathList[] = strScriptPath.split("\\\\");
                                                String tmpStr = "";
                                                for(int i =0;i <(strScriptPathList.length-1);i++)
                                                {
                                                                tmpStr += strScriptPathList[i];
                                                                tmpStr += "\\";
                                                }
                                                getVariables().set("gStrScriptPath", tmpStr);
                                                getVariables().set("gStrDataBankPath",getVariables().get("gStrScriptPath")+"DataBank");
                                }
                                catch(Exception ex)
                                {
                                                System.out.println("Exception in initializing the setup "+ex.getMessage());
                                }

                } 

                public void LoadGlobalVariablesFromPropertiesFile(String strFilePath) throws Exception
                {
                                try{

                                                Properties propertiesFile = new Properties();
                                                propertiesFile.load(new FileInputStream(strFilePath));            
                                                String key;
                                                Enumeration e = propertiesFile.propertyNames();
                                                while (e.hasMoreElements())
                                                {
                                                                key = (String)e.nextElement();
                                                                getVariables().set(key, propertiesFile.getProperty(key).trim());

                                                                System.out.println(key+" "+propertiesFile.getProperty(key));            
                                                }
                                }
                                catch(Exception e)
                                {
                                                System.out.println("Exception in initializing the setup "+e.getMessage());
                                }
                }

                /**
                * Logs Results Output into specified file path
                * 
                 * @param FilePath
                * @param Description
                */
                public void logComments(String outputfilename,String Description, String aStrTestStatus)
                throws Exception {

                                if (aStrTestStatus.trim() == "")
                                                aStrTestStatus = "Info";

                                utilities.getFileService().appendStringToFile(
                                                                outputfilename,
                                                                "\r\n" + Description + "\t\t\t" + aStrTestStatus + "\r\n");

                                aStrTestStatus = ":" + aStrTestStatus;

                                // info(Description + aStrTestStatus);
                                getStepResult().addComment(
                                                                Description + aStrTestStatus);
                }

                /**
                * Logs Results Output into specified file path - overloaded to log comment without status
                * 
                 * @param FilePath
                * @param Description
                */
                public void logComments(String outputfilename,String Description)
                throws Exception {


                                String aStrTestStatus = "Info";

                                utilities.getFileService().appendStringToFile(
                                                                outputfilename,
                                                                "\r\n" + Description + "\t\t\t" + aStrTestStatus + "\r\n");

                                aStrTestStatus = ":" + aStrTestStatus;

                                // info(Description + aStrTestStatus);
                                getStepResult().addComment(
                                                                Description + aStrTestStatus);
                }

                /**
                * Creates file. If there is any existing then deletes it an creates a new
                * file.
                * 
                 * @param FileName --  filename
                */
                public void CreateFile(String FileName) throws Exception {

                                try {

                                                String str = getVariables().get("gStrOutputFilePath")+"\\"+FileName+".log";
                                                File lObjFile = new File(getVariables().get("gStrOutputFilePath")+"\\"+FileName+".log");

                                                if (lObjFile.exists()) {
                                                                lObjFile.delete();
                                                }
                                                //info("cREATING FILE"+lObjFile);
                                                lObjFile.createNewFile();
                                                getVariables().set("gStrOutputFileName",getVariables().get("gStrOutputFilePath")+"\\"+FileName+".log");
                                } catch (Exception e) {
                                                info("Exception in CreateFile : " + e.getMessage());
                                }

                }


                public void CreateWSRequest() throws Exception{
        String XAI="";
        try {
              
              getVariables().set("gStrWebServiceRequestXML","");
              
              //remove trailing & leading blank spaces from wsurl
             // Bug 20086820 - WHITE SPACES SHOULD BE TRUNCATED FROM WEBSERVICE NAME AND TRANSACTION TYPES. 
              getVariables().set("WSNAME",getVariables().get("WSNAME").trim());
              getVariables().set("gStrTransactionType", getVariables().get("gStrTransactionType").trim());
              
              
              
              String DatabankName =   getVariables().get("WSDataBankName"); 
            //added for integration flows solution. - Start
                                                                if(DatabankName.contains("\\")||DatabankName.contains("/"))
                                                                {
                                                                                DatabankName = DatabankName.split("[/\\]")[1];
                                                                                getVariables().set("WSDataBankName",DatabankName);
                                                                }
                                                                //added for integration flows solution. - End
              
              
              InfoDecription = "Starting Webservice Request Creation...";
              logComments(getVariables().get("gStrOutputFileName"),InfoDecription);
              
                          
              

              String WSDLURL = getCompleteXAIURLFormat(getVariables().get("WSNAME"));
              
        
                    InfoDecription = "Complete web service URL used for transaction is :"+WSDLURL;
                    logComments(getVariables().get("gStrOutputFileName"),InfoDecription);
                    
              
        
              
              convertWSDLToXML(WSDLURL,getVariables().get("WSNAME"));
              
              String lstrXAIPath=getVariables().get("gStrXSDFiles")+getVariables().get("WSNAME")+"_"+getVariables().get("gStrTransactionType")+".xml";
                                
              XAI = (String) readXmlSchema(lstrXAIPath);
              
              XAI = processXAIUsingDatabankWithXpath(XAI, DatabankName);

              info("XAI Used for  transaction : " + XAI);
              
              } catch (Exception e) {
              
              
              InfoDecription = "An Exception has occurred in the OUTSK XAI Processing function(OUTSK_WSDL_Req_Resp) :"+e.getMessage().toString();
              info(InfoDecription);
              
              }
        getVariables().set("gStrWebServiceRequestXML", XAI);
        
  }




                /**
                * Creates complete WSDL URL
                * 
                 * @param WSDLName
                */
                public String getCompleteXAIURLFormat(String WebServiceName) throws Exception {

                                String URL = "";

                                try {

                                                //added for integration flows solution. - Start
                                                if(WebServiceName.contains("/")||WebServiceName.contains("\\"))
                                                {
                                                                
                                                                if(WebServiceName.contains("/"))
                                                                {
                                                                  URL = getVariables().get(WebServiceName.split("[/]")[0])+"/"+WebServiceName.split("[/]")[1];
                                                                      getVariables().set("WSNAME",WebServiceName.split("[/]")[1]);
                                                                   productURLVariableName=WebServiceName.split("[/]")[0];
                                                                }
                                                                if(WebServiceName.contains("\\"))
                                                                {
                                                                    URL = getVariables().get(WebServiceName.split("[\\]")[0])+"/"+WebServiceName.split("[\\]")[1];
                                                                    getVariables().set("WSNAME",WebServiceName.split("[\\]")[1]);
                                                                    productURLVariableName=WebServiceName.split("[\\]")[0];
                                                                }
                                                                
                                                               
                                                                
                                                                integrationURL=URL;
                                                                //info("integrationURL"+integrationURL);
                                                }else
                                                                //added for integration flows solution. - End
                                                {

                                                URL = getVariables().get("gStrApplicationURL") + getVariables().get("gStrApplicationXAIServerPath") + WebServiceName;
                                                }
                                                return URL;

                                } catch (Exception e) {
                                                info("Exception in GetCompleteXAIURLFormat : " + e.getMessage());
                                                return URL;
                                }

                }

                public void convertWSDLToXML(String aStrWSDLURL,String aStrWSDLName) throws Exception
    {
          
          
          
          try
    {
                  
                  File toBeDeletedFile = new File(getVariables().get("gStrXSDFiles")+"/wsdlparse.txt");

              toBeDeletedFile.delete();
              
          URL url;
          URLConnection urlConn;
          DataOutputStream printout;
          DataInputStream input;
          FileInputStream input1;

          String str = "";
          int flag=1;
          WSDLParser parser = null;
          Definitions wsdl = null;
                      
    String lStrCompleteWSDLLink=aStrWSDLURL+"?WSDL";
    
 
    info("complete WSDL URL used for transaction "+lStrCompleteWSDLLink);
   
    String lStrXAIFilepath = "";

       
        
          if (lStrCompleteWSDLLink.contains("https"))
          {
                  HostnameVerifier hv = new HostnameVerifier()
                      {
                            public boolean verify(String arg0, SSLSession arg1) {
                                  // TODO Auto-generated method stub
                                 // System.out.println("Warning: URL Host: " + arg0 + " vs. "                                                      + arg1.getPeerHost());
                                  return true;
                            }
                      };
                      HttpsURLConnection.setDefaultHostnameVerifier(hv);

                     
                      
                      System.setProperty( "javax.net.ssl.trustStore", getVariables().get("gStrJavaKeyStorePath")+"\\cacerts" );
                      System.setProperty( "javax.net.ssl.trustStorePassword", getVariables().get("gStrJavaKeyStorePwd") );
                      
                      url = new URL(lStrCompleteWSDLLink);
                      urlConn = url.openConnection();
                      urlConn.setDoInput(true);
                      Object object;
                      urlConn.setUseCaches(false);

                      urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                      input = new DataInputStream(urlConn.getInputStream());
                      
                      
                      StringBuffer inputLine = new StringBuffer();

                      String tmp;

                      while ((tmp = input.readLine()) != null) {

                          inputLine.append(tmp);                       

                      }

                                              

                      FileWriter outFile = new FileWriter(getVariables().get("gStrXSDFiles")+"/wsdlparse.txt", true);

                      PrintWriter out = new PrintWriter(outFile);    

                      

                                  

                      out.print(inputLine.toString().trim());

                      out.close();

                      outFile.close();

                     

                      InputStream targetStream = new FileInputStream(getVariables().get("gStrXSDFiles")+"/wsdlparse.txt");

                      input1 = (FileInputStream) targetStream;

                      
                      
                      
                      
                      parser = new WSDLParser();
                    wsdl = parser.parse(input1);

                    targetStream.close();

                   

                    toBeDeletedFile = new File(getVariables().get("gStrXSDFiles")+"/wsdlparse.txt");

                    toBeDeletedFile.delete();       

          }
          else
          {
                parser = new WSDLParser();
              wsdl = parser.parse(lStrCompleteWSDLLink);   
          }
                if(wsdl != null)
       {
                      StringWriter writer = new StringWriter();
                SOARequestCreator creator = new SOARequestCreator(wsdl,writer,new MarkupBuilder(writer));
    for (Service service : wsdl.getServices()) {
    int Count = 1;
      for (Port port : service.getPorts()) {
          com.predic8.wsdl.Binding binding = port.getBinding();
          PortType portType = binding.getPortType();
          for (Operation op : portType.getOperations()) {
              creator.setCreator(new RequestTemplateCreator());
              creator.createRequest(port.getName(), op.getName(), binding.getName());
              lStrXAIFilepath = getVariables().get("gStrXSDFiles")+"/temp.xml";
          //    System.out.println(writer);
           //   System.out.println("Service Nameeee :::" +op.getName());                
              File lObjFile = new File(lStrXAIFilepath);
              if (lObjFile.exists()) {
                            lObjFile.delete();
                      }
              PrintWriter Pwriter = new PrintWriter(lStrXAIFilepath, "UTF-8");
              Pwriter.println(writer);                  
              Pwriter.close();    
              writer.getBuffer().setLength(0);
              getVariables().set("gStrWSDLOpNAme",op.getName());
              formatXAI(lStrXAIFilepath,op.getName(),aStrWSDLName);
       }
    }
    }
     }
    }              
    catch(Exception e){
          
          e.printStackTrace();
    }
    
}


                public void formatXAI(String aStrXAIFilepath,String operationName, String aStrWSDLName) throws Exception
    {                 
          FileInputStream fstream = new FileInputStream(aStrXAIFilepath);
          BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
          String strLine;
          
          String resultOutputFilePath=getVariables().get("gStrXSDFiles");
          String lStrXAIFilepath="";
          
           if(!aStrWSDLName.equalsIgnoreCase(operationName))
            lStrXAIFilepath = resultOutputFilePath+aStrWSDLName+"_"+operationName+".xml";
          else
                lStrXAIFilepath = resultOutputFilePath+aStrWSDLName+"_"+getVariables().get("gStrTransactionType")+".xml";
        //  info("lStrXAIFilepath"+lStrXAIFilepath);
                
           File lObjFile = new File(lStrXAIFilepath);

                if (lObjFile.exists()) {
                      lObjFile.delete();
                }
          PrintWriter Pwriter = new PrintWriter(lStrXAIFilepath, "UTF-8");
          while ((strLine = br.readLine()) != null) {
                
                 strLine = strLine.replaceAll("dateTimeTagFormat=\'xsd:strict\'", "");
                      strLine = strLine.replaceAll("\\'\\?\\?\\?\\'","\"{{gStrTransactionType}}\"");
                      strLine = strLine.replaceAll("\\?XXX\\?","{{gStrTransactionType}}");
                      strLine = strLine.replaceAll(">.*</", "></");
                if (strLine.contains("<!--"))
                      continue;
                Pwriter.println(strLine);                
           }
          Pwriter.close();  
    }
    
                
    

                public String readXmlSchema(String file) {
                                try {

                                                // Read the XML file
                                                FileInputStream schema = new FileInputStream(file);
                                                DataInputStream data = new DataInputStream(schema);
                                                BufferedReader reader = new BufferedReader(new InputStreamReader(data));
                                                String xai = "";
                                                String strLine;
                                                while ((strLine = reader.readLine()) != null) {
                                                                xai = xai + strLine;
                                                }
                                                return xai;
                                } catch (Exception e) {
                                                return null;
                                }
                }



                public String processXAIUsingDatabankWithXpath(String XAIString,String DatabankName) throws Exception {
        
        String FinalXAI = "";   
        getVariables().set("gStrXAIString",XAIString);
        
  
        
        try {
            
              
              String strHeaderNameSpace = XAIString.substring(XAIString.indexOf("<")+1,XAIString.indexOf(":"));
              
              String [] Xaiparts = XAIString.split("<"+strHeaderNameSpace+":Body>");
              
              final String XAIHeader = Xaiparts[0]+"<"+strHeaderNameSpace+":Body>";
              
              String XAIBody = Xaiparts[(Xaiparts.length)-1];
        
              
              String strNameSpace = XAIBody.substring(XAIBody.indexOf("<")+1,XAIBody.indexOf(":"));
        
              XAIBody  = XAIBody.substring(XAIBody.indexOf("<"),XAIBody.length()-1);
              
              String XAIWSDLName  = XAIBody.substring(XAIBody.indexOf(":")+1,XAIBody.indexOf(' '));
             
              String [] XAIFooterArray =  XAIString.split("</"+strNameSpace+":"+XAIWSDLName+">");
              String XAIFooter="";
              
              if(XAIFooterArray.length>=2){
               XAIFooter = "</"+strNameSpace+":"+XAIWSDLName+">" + XAIFooterArray[1];
              }else{
            	  singleElementXML=true;
            	  XAIFooter="</"+strHeaderNameSpace+":Body></"+strHeaderNameSpace+":Envelope>";
              }
            
                                            
              String formattedXAI = XAIHeader + "\n"+ "<"+XAIBody.substring(XAIBody.indexOf("<")+1,XAIBody.indexOf(">"))+">\n";
             
              String completeHeader ="";
              String operationName="";
              String nsUrl="";
              String operationNameSpace ="";
              if(singleElementXML){// added to handle single element xmls with attributes
            	  
            	  completeHeader = XAIHeader;
            	   operationNameSpace = XAIBody.substring(XAIBody.indexOf("<") + 1, XAIBody.indexOf(":"));
            	  
            	  
            	  String[] nameSpaceURL=XAIBody.split("xmlns:"+operationNameSpace+"='");
            	  nsUrl=nameSpaceURL[1].split("'")[0];
            	  
            	   operationName=XAIBody.substring(XAIBody.indexOf("<"+operationNameSpace) + operationNameSpace.length()+2, XAIBody.indexOf(" "));
            	  
            	 
              }else{
              
               completeHeader = XAIHeader + "\n"+ "<"+XAIBody.substring(XAIBody.indexOf("<")+1,XAIBody.indexOf(">"))+">\n";;
              }
              
              String documentString="";
              
              //Check if other databank parts exist
              int DBNameEndIndex=DatabankName.toLowerCase().indexOf("_part");
              
              if(DBNameEndIndex!=-1) //if component has multiple csvs
              {
              String DBNameToCheck=DatabankName.substring(0, DBNameEndIndex);
              String dataBankPath=getVariables().get("gStrDataBankPath");
             // info("DBNameToCheck in dataBankPath"+dataBankPath+"/"+DBNameToCheck);
              File folder = new File(dataBankPath);
              File[] listOfFiles = folder.listFiles();
              int i = 0,x=0;
                  for (i=0; i < listOfFiles.length;i++) {
                    if (listOfFiles[i].isFile()) {
                                
                      if(listOfFiles[i].getName().endsWith("csv") && listOfFiles[i].getName().toLowerCase().contains((DBNameToCheck+"_part").toLowerCase()))
                      {
                                //  info("Found a csv "+listOfFiles[i].getName());
                                  x++; //increment to count the total csvs with the same name.
                      }
                    } else if (listOfFiles[i].isDirectory()) {
                    //  info("Directory " + listOfFiles[i].getName());
                    }
                  }
                  multiListCSVTotalCount=x;
                  xmlPartOfComplexMultiList=true;
                // info("total csvs are"+multiListCSVTotalCount);
                  //call processDatabank for each databank that exists else call processDatabank with provided databank name
                  for(int j=1;j<=multiListCSVTotalCount;j++)
                  {
                                  multiListCSVCurrentCount=j;
                                  minimumListSizeForComplexMultiList=0;
                                 // info("calling csv file for processing "+DBNameToCheck+"_part"+j);
                                  processDatabank(DBNameToCheck+"_part"+j);
                                
                  }
                
                
                   }else{
                  xmlPartOfComplexMultiList=false;
                  processDatabank(DatabankName);
                 
                  
              }
               documentString = getDocumentString().toString();
             // info("documentString"+documentString);  
          
            
              
              docco = null;
                                docco = new Document(new Element("testRoot"));
              
              
                 formattedXAI +=  XAIFooter;
              FinalXAI = formattedXAI;
              FinalXAI="";
              
              
              if(singleElementXML){
            	  String[] reFormatXML=documentString.split(operationName);
            	  documentString=reFormatXML[0]+operationNameSpace+":"+operationName+" xmlns:"+operationNameSpace+"='"+nsUrl+"' "+reFormatXML[1];
            	  singleElementXML=false;
              }
              
              FinalXAI=completeHeader+documentString+XAIFooter;
             // info("formattedXAI is :: "+ (completeHeader+documentString+XAIFooter));
                                
                             
              }
              catch(Exception e)
              {
                    
              }
              
              return FinalXAI;
  }


                
                
                public void processDatabank(String DatabankName) throws Exception
                {
                                DatabankFkXpathTracker.clear(); //clear out the fkref counter for a given csv
                
                                  info("csv file is "+getVariables().get("gStrDataBankPath")+"\\"+DatabankName+".csv");
          
          BufferedReader br = new BufferedReader(new FileReader(getVariables().get("gStrDataBankPath")+"\\"+DatabankName+".csv"));
          
          String strCoumnNames = br.readLine(); 
          
         
          
          String [] strColumns = strCoumnNames.split(",");
                                
                                  int recCount=getDatabank(DatabankName).getDatabankRecordCount();
          int CurrRecId = 1;
          int resetListRec=0;
          if(DatabankRowIdTracker.get(DatabankName)!= null)
                CurrRecId = DatabankRowIdTracker.get(DatabankName);
          else
                getDatabank(DatabankName).getNextDatabankRecord();
          
          info("started dbprocessing:+ CurrRecId"+CurrRecId+"::"+recCount);
          boolean lBlnFirstRec=true;
          for (int rec = CurrRecId; rec <= recCount; rec++)
          {
                  //info("processing record "+rec);
                boolean csvPartOfMultiList=false;
                parentXMLNodeIndex=-1;
                      DatabankRowIdTracker.put(DatabankName, rec);
                     
                      if(!lBlnFirstRec)
                            getDatabank(DatabankName).getNextDatabankRecord();
                      
                    
                     /* if(getVariables().get("db."+DatabankName+".DB_RowIdentifier").trim().length() != 0 && !DatabankMultiListRowsTracker.isEmpty() && DatabankMultiListRowsTracker.get(DatabankName).equalsIgnoreCase(getVariables().get("db."+DatabankName+".DB_RowIdentifier")))
                                                                  
                      {
                                
                                  csvPartOfMultiList=true;
                      }*/
                     
                  /*    if(getVariables().get("db."+DatabankName+".DB_RowIdentifier").trim().length() != 0)
                      DatabankMultiListRowsTracker.put(DatabankName,getVariables().get("db."+DatabankName+".DB_RowIdentifier"));
                     */
                      
                      if(getVariables().get("db."+DatabankName+".DB_RowIdentifier").trim().length()!=0){
                                  
                                 // System.out.println("in iffff +" + getVariables().get("db."+DatabankName+".DB_RowIdentifier"));
                                  resetListRec=1;
                      }else{
                                 // System.out.println("in elseeee :: "+ getVariables().get("db."+DatabankName+".DB_RowIdentifier"));
                                  resetListRec++;
                      }

                      if (getVariables().get("db."+DatabankName+".DB_RowIdentifier").trim().length() != 0 && !lBlnFirstRec)
                      {
                                 
                            break;
                      }
                      
                      
                      
                      
                      ///the below for loop should be called only in case of complex multi list parsing to find the minimum level of xpath
                      if(xmlPartOfComplexMultiList)
                      {
                      for (int j =3; j < strColumns.length; j ++)
                      {
                                  
                                 String[] columnName=strColumns[j].split("/");
                      if(minimumListSizeForComplexMultiList==0 || minimumListSizeForComplexMultiList>columnName.length)
                                                minimumListSizeForComplexMultiList=columnName.length-1;
                      }
                      
                      //rearrange columns if fkref and pkref are not the first and second columns.
                      int fkRefIndex=0;
                                  int pkRefIndex=0;
                      for (int j =1; j < strColumns.length; j++)
                      {
                                  
                                  if(strColumns[j].equalsIgnoreCase("fkRef"))
                                  {
                                                  fkRefIndex=j;
                                  }
                                  if(strColumns[j].equalsIgnoreCase("pkRef"))
                                  {
                                                  pkRefIndex=j;
                                  }
                                 
                                  
                      }
                      
                                  if(fkRefIndex!=1){
                                                  
                                                  String tempColumn=strColumns[1];
                                                  strColumns[1]=strColumns[fkRefIndex];
                                                  strColumns[fkRefIndex]=tempColumn;
                                  }
                                  if(pkRefIndex!=2){
                                                  
                                                  String tempColumn=strColumns[2];
                                                  strColumns[2]=strColumns[pkRefIndex];
                                                  strColumns[pkRefIndex]=tempColumn;
                                  }
                      
                      }
                      
                for (int j =1; j < strColumns.length; j++)
                {
                                //info("NEW Column being called is"+strColumns.length);
                                //info("NEW Column being called is"+strColumns[j]);
                     String columnName="";
                      String value = getVariables().get("db." + DatabankName + "." + strColumns[j].replaceAll("\"", ""));         
         
                      if(strColumns[j].equalsIgnoreCase("pkref") || strColumns[j].equalsIgnoreCase("fkref")) // added this to append the complete xpath to fkref & pkref value so that multi child mapping can be maintained with its parent.
                      {
                                 
                                  columnName=strColumns[3].substring(0,strColumns[3].lastIndexOf("/"))+"/"+strColumns[j];
                      }else{
                                  columnName=strColumns[j];
                      }
                     
                                                                                // Support for re-runnable:
                                                                                // Help: User need to pass data as "?data" for generating random value. Random string of leanth 5 will we appended to the data
                                                                                // value provided by user.
                                                                                // User also can provide the required lenght in that case data should be provided as "nLenght?data" without any space
                                                                                if(value.length()>1 && (value.toLowerCase().substring(0,1).matches("[0-9]")) && (value.charAt(1)=='?')){
                                                                                                String s = String.valueOf(value.charAt(0));
                                                                                                int i = Integer.parseInt(String.valueOf(value.charAt(0)));
                                                                                                value = value + oUTSPCORELIB.randomStringWithGivenRange(i,i);
                                                                                                value = value.substring(2);
                                                                                                }

                                                                                if(value.toLowerCase().startsWith("?"))                                                
                                                                                {
                                                                                                value = value.replace("?", "");
                                                                                                value = value + oUTSPCORELIB.randomStringWithGivenRange(5,5);                         
                                                                                }
                                                                                
                      if(value.toLowerCase().startsWith("var_"))                                                
                      {
                            value = value.substring(4);
                            value = "{{"+value+"}}";                                    
                      }
                      if (! columnName.contains("/") && !columnName.contains("@"))                    
                      {                                               
                            if (value.length() != 0 && !value.toLowerCase().equals("#empty"))
                            {
                                  singleElementHandler(columnName.replaceAll("\"", ""),value);
                            }
                      }
                      else
                      {     
                            if (value.length() != 0 && !value.toLowerCase().equals("#empty"))
                            {
                                //info("calling multi child element handler with column name"+columnName+" and value "+value+" rec "+rec);
                                  multiChildElementHandler(columnName.replaceAll("\"", ""),value, resetListRec);
                            }                                   
                      }
                }
            //    info("end of columns for loop");
                lBlnFirstRec = false;
                
          }
        //  info("end of total records for loop");
          
         
                                
                                
                                
                }
                
                public String constructChildElementBlock(String []strArr,String strNameSpace,String value) throws Exception
                {
                                String strChildElementBlock="";
                                for (int i =0 ; i < strArr.length ; i++)
                                {                 
                                                if (i != (strArr.length)-1)
                                                {
                                                                if (value.length() != 0)
                                                                                strChildElementBlock += "<"+strNameSpace+":"+strArr[i]+">\n";
                                                }
                                                else
                                                {
                                                                if (value.length() != 0)
                                                                                strChildElementBlock += "<"+strNameSpace+":"+strArr[i]+">"+value/*+"</"+strNameSpace+":"+strArr[i]+">"*/;
                                                }
                                }           
                                for (int i = strArr.length-1 ; i >= 0  ; i--)
                                {     
                                                if (value.length() != 0)
                                                                strChildElementBlock += "</"+strNameSpace+":"+strArr[i]+">\n";                        
                                }
                                return strChildElementBlock;
                }

                public void singleElementHandler(String strElement, String Value)
                throws Exception {
                                String[] finalStrElement=strElement.split("@");
                                String childelemntblock = "";
                                String[] list = {};
                                try {
                                                if(finalStrElement.length<2)
                                                {

                                                                docco.getRootElement().addContent(
                                                                                                new Element(finalStrElement[0]).setText(Value));
                                                }
                                                else{

                                                                docco.getRootElement().addContent(
                                                                                                new Element(finalStrElement[0]).setAttribute(finalStrElement[1].trim(), Value));
                                                }
                                                String documentString = new XMLOutputter().outputString(docco);
                                                //info("StringElement::Attribute::Value::"+finalStrElement[0]+"::"+finalStrElement[1]+"::"+Value);
                                                //info("XML String Manufacturing:: " + documentString);
                                } catch (Exception io) {
                                                System.out.println(io.getMessage());
                                }
                }

                public String getDocumentString() throws AbstractScriptException{
                                
                                
                                
                                String documentString = new XMLOutputter().outputString(docco);
                //            info("Final String before :: " + documentString); 
                                documentString=getScript("WSREMOVEBLANKNODES").callFunction("filterElements",documentString,"fkRef").toString();//remove the nodes with name fkRef
                                documentString=getScript("WSREMOVEBLANKNODES").callFunction("filterElements",documentString,"pkRef").toString();//remove the nodes with name pkRef
                                
                                documentString= getScript("WSREMOVEBLANKNODES").callFunction("removeEmptyNodes",documentString).toString();

                                //info("Final String after:: " + documentString);
                                documentString=documentString.replace("<testRoot>", "").replace("</testRoot>", "");
                                /*docco = null;
                                docco = new Document(new Element("testRoot"));*/
                                return documentString.split("\n")[1];
                }

                @SuppressWarnings("unchecked")
                public void multiChildElementHandler(String strElement, String Value,
                                                Integer rowIndex) throws Exception {
                             
                                String[] elementAttribute=new String[50];
                                String[] elementList=new String[50];
                                String OriginalStrElement=strElement;
                                if(strElement.contains("@"))
                                {
                                                elementAttribute=strElement.split("@");
                                                strElement=elementAttribute[0]+"/"+elementAttribute[1];
                                                
                                                elementList = strElement.split("/");
                                                
                                }
                                else
                                {
                                                elementList=strElement.split("/");
                                }
                                
                                rowIndex = rowIndex-1;
                                
                                Element currentElement = docco.getRootElement();
                                Element rootElement = docco.getRootElement();
                                String documentString = new XMLOutputter().outputString(docco);
                
                
                                
                                if(xmlPartOfComplexMultiList){
                                                int currentIndex=0;
                                               Element multiListParentElement;
                                //                info("#####################################################################################################################################");
                                //            info("multiListCSVTotalCount "+multiListCSVTotalCount+"::multsfiListCSVCurrentCount "+multiListCSVCurrentCount);
                                                //info("xpath passed is "+strElement+"with value "+Value);
                                                if(multiListCSVTotalCount>0 && multiListCSVCurrentCount<=multiListCSVTotalCount && multiListCSVCurrentCount!=1) // for 1st row of 2nd csv
                                                {
                                                                
                                                                if(multiListCSVTotalCount>0 && strElement.contains("fkRef")) //get the corresponding value of pkref element from previous csv using this current CSVs fk ref
                                                                {
                                                                                int temp=multiListCSVCurrentCount-1;
                                                                                //info("multiListCSVCurrentCount"+temp+"::"+Integer.parseInt(Value));
                                                                                
                                                                                SelectedParentFromPreviousCSV=DatabankPkElementCSVTracker.get(temp+":"+Integer.parseInt(Value));
                                                                //            info("Getting parent element of previous csv using current fkref"+SelectedParentFromPreviousCSV);
                                                                                
                                                                                // info("rowIndex and value are "+rowIndex+"PP"+Value);
                                                                                DatabankFkXpathTracker.put(rowIndex,Value);
                                                                                fkRefValueForCurrentRow=Integer.parseInt(Value);
                                                                }
                                                                //count the total number of times the same fkref occured in the csv. This is the row index for multi list.
                                                                int l=0;
                                                                int k=0;
                                                                // info("DatabankFkXpathTracker.size()"+DatabankFkXpathTracker.size());
                                                                while(l<DatabankFkXpathTracker.size()){
                                                                
                                                                                int fkRefValue=Integer.parseInt(DatabankFkXpathTracker.get(l));
                                                                                l++;
                                                                                //info("fkRefValue vs Value"+fkRefValue+"VS"+fkRefValueForCurrentRow);
                                                                                if(fkRefValue==fkRefValueForCurrentRow)
                                                                                {
                                                                                                k++; 
                                                                                                 
                                                                                 }
                                                                }
                                                                
                                                 rowIndex=k-1;
                                                
                                                
                                                                Element selectedParent=SelectedParentFromPreviousCSV;
                                                
                                                                for (String element : elementList) { //move to the last available node in the xml for the retrived parent which matches the element list nodes.
                                                                                if (selectedParent.getChild(element) != null)
                                                                                {
                                                                                
                                                                                                selectedParent=selectedParent.getChild(element);
                                                                                                
                                                                                }
                                                                                
                                                                }
                                                                
                                                                currentElement=selectedParent;
                                                                int existingElementsInXML=0;
                                                                while(selectedParent.getParent() != null) //calculate current index by traversing back to the parent.
                                                                {
                                                                                existingElementsInXML++;
                                                                                selectedParent=selectedParent.getParent();
                                                                }
                                                                currentIndex=existingElementsInXML; 
                                                                
                                                                
                                                                
                                                                
                                                                if (currentIndex == elementList.length) {
                                                                                
                                                                                
                                                                                
                                                                                for (int i=elementList.length;i>minimumListSizeForComplexMultiList;i--)
                                                                                {
                                                                                                
                                                                                                currentIndex--;
                                                                                                currentElement=currentElement.getParent();
                                                                                                
                                                                                }
                                                                                
                                                                                
                                                                                
                                                                                
                                                                                Element newElement = new Element(elementList[currentIndex-1]);
                                                                                
                                                                                Element listParent = currentElement.getParent();
                                                                //            info("New Element being created is "+elementList[currentIndex-1]+" to be added under parent "+listParent);
                                                                //            System.out.println("Children List: "+ listParent.getChildren(elementList[currentIndex - 1]).size()+" and Row Index Passed is: "+rowIndex);
                                                                                if (rowIndex <= listParent.getChildren(
                                                                                                                elementList[currentIndex-1]).size() - 1) {
                                                                                                selectedParent = (Element) listParent.getChildren(
                                                                                                                                elementList[currentIndex-1]).get(rowIndex);
                                                                                //            info("FOR 2nd row in 2nd csv the selected parent is"+selectedParent);
                                                                                                currentElement = selectedParent;
                                                                                } else {

                                                                                                currentElement = listParent.addContent(newElement);
                                                                                                /*System.out.println("Creating Parent Content: "
                                                                                                                                + elementList[currentIndex - 1]);*/

                                                                                                currentElement = newElement;

                                                                                }
                                                                                
                                                                                                                                
                                                                }
                                                                
                                                                
                                                                
                                                                
                                                                
                                                }
                                                
                                                
                                                else{// for 1st csv
                                                                
                                                
                                                
                                                for (String element : elementList) {
                                                //            info("Element list element"+element+"::"+currentElement);
                                                                if (currentElement.getChild(element) != null) {
                                                                                currentElement = currentElement.getChild(element);
                                                                } else {
                                                                                break;
                                                                }
                                                                currentIndex++;
                                                }
                                                //info("currentIndex is "+currentIndex+" for xpath"+strElement+" element "+currentElement);
                                                
                                                /*if(multiListCSVTotalCount>0 && strElement.contains("fkRef"))
                                                
                                                {
                                                                DatabankFkXpathTracker.put(multiListCSVCurrentCount,strElement);
                                                }
                                                if(multiListCSVTotalCount>0 && strElement.contains("pkRef"))
                                                {
                                                                DatabankPkXpathTracker.put(multiListCSVCurrentCount,strElement);
                                                }*/
                                                //info("document String is "+documentString);
                                                
                
                                                
                                                if (currentIndex == elementList.length) {
                                                                
                                                                
                                                                
                                                                for (int i=elementList.length;i>minimumListSizeForComplexMultiList;i--)
                                                                {
                                                                                
                                                                                currentIndex--;
                                                                                currentElement=currentElement.getParent();
                                                                                
                                                                }
                                                                
                                                                //info("current index post realign "+currentIndex+"-----"+currentElement);
                                                                
                                                                
                                                                Element newElement = new Element(elementList[currentIndex-1]);
                                                //            info("New Element being created is "+elementList[currentIndex-1]);
                                                                Element listParent = currentElement.getParent();

                                                               // System.out.println("Children List: "+ listParent.getChildren(elementList[currentIndex - 1]).size()+" and Row Index Passed is: "+rowIndex);
                                                                if (rowIndex <= listParent.getChildren(
                                                                                                elementList[currentIndex-1]).size() - 1) {
                                                                                Element selectedParent = (Element) listParent.getChildren(
                                                                                                                elementList[currentIndex-1]).get(rowIndex);
                                                                //            info("FOR 2nd row in 2nd csv the selected parent is"+selectedParent);
                                                                                currentElement = selectedParent;
                                                                } else {

                                                                                currentElement = listParent.addContent(newElement);
                                                                                /*System.out.println("Creating Parent Content: "
                                                                                                                + elementList[currentIndex - 1]);*/

                                                                                currentElement = newElement;

                                                                }
                                                                
                                                                                                                
                                                }
                                                }
                                                
                                
                                                for (int i = currentIndex; i < elementList.length; i++) {
                                                                //info("adding new element "+elementList[i]+" to current parent element "+currentElement);
                                                                currentElement.addContent(new Element(elementList[i]));
                                                
                                                                currentElement = currentElement.getChild(elementList[i]);
                                                                
                                                                
                                                }
                                                
                                                //after the nodes are added to the xml...get the node for the pkref parent and store it in hashmap along with the pkref value and the csv number.
                                                
                                                if(multiListCSVTotalCount>0 && strElement.contains("pkRef"))
                                                {
                                                                //info("*******************************************************************");
                                                                
                                                                //info("inserting values into hashmap"+Value+"----"+currentElement.getParent()+"::::"+multiListCSVCurrentCount);
                                                                //info("*******************************************************************");
                                                                
                                                                
                                                                
                                                                
                                                                
                                                                
                                                                DatabankPkElementCSVTracker.put(multiListCSVCurrentCount+":"+Integer.parseInt(Value),currentElement.getParent());
                                                                
                                                                                                }
                                                
                                                
                                }
                                
                                else
                                {
                                                
                                               // info("document String is "+documentString);
                                                int currentIndex = 0;
                                                for (String element : elementList) {
                                                                
                                                                if (currentElement.getChild(element) != null) {
                                                                                currentElement = currentElement.getChild(element);
                                                                } else {
                                                                                break;
                                                                }
                                                                currentIndex++;
                                                }
                                               // info("currentIndex is "+currentIndex+" for xpath"+strElement+" element "+currentElement);
                                
                                                if (currentIndex == elementList.length) {
                                                                currentIndex = currentIndex - 1;
                                                                Element newElement = new Element(elementList[currentIndex - 1]);
                                                                Element listParent = currentElement.getParent().getParent();

                                                                // System.out.println("Children List: "+ listParent.getChildren(elementList[currentIndex - 1]).size()+" and Row Index Passed is: "+rowIndex);
                                                                if (rowIndex <= listParent.getChildren(
                                                                                                elementList[currentIndex - 1]).size() - 1) {
                                                                                Element selectedParent = (Element) listParent.getChildren(
                                                                                                                elementList[currentIndex - 1]).get(rowIndex);
                                                                               /* System.out.println("Selected parent: "
                                                                                                                + selectedParent.getName());*/
                                                                                currentElement = selectedParent;
                                                                } else {

                                                                                currentElement = listParent.addContent(newElement);
                                                                                /*System.out.println("Creating Parent Content: "
                                                                                                                + elementList[currentIndex - 1]);*/

                                                                                currentElement = newElement;

                                                                }
                                                }
                                                for (int i = currentIndex; i < elementList.length; i++) {

                                                                currentElement.addContent(new Element(elementList[i]));
                                                                currentElement = currentElement.getChild(elementList[i]);
                                                }
                                                
                                                
                                }
                                
                                

                                if(!OriginalStrElement.contains("@"))
                                {
                                                
                                                currentElement.setText(Value);
                                                //info("Setting vlaue on the element "+currentElement+"]]]]"+Value);
                                }
                                else{
                                                try{
                                                                
                                                                currentElement.getParent().setAttribute(elementAttribute[1].trim(), Value);

                                                }catch (Exception e)
                                                {
                                                                info("Exception in setting attribute value "+e.getMessage());
                                                }
                                }
                                
                                
                                
                }

                
                


                public void ProcessWSRequest() throws Exception
                {


                                try{

                                                // ---------------------------------------------
                                                InfoDecription = "Starting the webservices Transaction. Sending  Request...";
                                                logComments(getVariables().get("gStrOutputFileName"),InfoDecription);

                                                beginStep("Transaction Using XAI");
                                                {     
                                                String requestUserName=getVariables().get("gStrApplicationUserName");
                                                String requestPassword=getVariables().get("gStrApplicationUserPassword");
                                          
                                                                String WDSLURL="";
                                                                if(!integrationURL.equals(""))
                                                                {
                                                                                
                                                                                WDSLURL=integrationURL;
                                                                                integrationURL="";
                                                                                if(!productURLVariableName.equals(""))
                                                                                {
                                                                                      if(getVariables().get(productURLVariableName+"_gStrApplicationUserName")!=null)
                                                                                    {
                                                                                          requestUserName=getVariables().get(productURLVariableName+"_gStrApplicationUserName");
                                                                                   }
                                                                                      if(getVariables().get(productURLVariableName+"_gStrApplicationUserPassword")!=null)
                                                                                    {
                                                                                          requestPassword=getVariables().get(productURLVariableName+"_gStrApplicationUserPassword");
                                                                                    }
                                                                                    
                                                                                }
                                                                }
                                                                else{
                                                                WDSLURL=getCompleteXAIURLFormat(getVariables().get("WSNAME"));
                                                                }
                                                                
                                                                if(requestUserName.equals("#EMPTY"))
                                                                {
                                                                  ws.post(2,WDSLURL, getVariables().get("gStrWebServiceRequestXML")  , http.headers(http.header("Content-Type","text/xml;charset=UTF-8",Header.HeaderAction.Modify)), true, null, null);

                                                                  
                                                                }else{
                                                                  byte[] encAuthBytes=Base64.encode((requestUserName+":"+requestPassword).getBytes());
                                                                  String encAuth=new String(encAuthBytes);
                                                                  String authHeaderValue="Basic: "+encAuth;
                                                                    ws.addSecurityAttachments(WDSLURL, ws.security(requestUserName,requestPassword, true,false, false), null);
                                                          
                                                                    ws.post(2, WDSLURL, getVariables().get("gStrWebServiceRequestXML")      , http.headers(http.header("Content-Type", "text/xml;charset=UTF-8",Header.HeaderAction.Modify), http.header("SOAPAction", WDSLURL,Header.HeaderAction.Modify),http.header("Authorization",authHeaderValue,Header.HeaderAction.Modify)), true, null, null);
                                                      
                                                                }
                                                                         
                                                }
                                                endStep();

                                                // ---------------------------------------------
                                                InfoDecription = "Completed the webservices Transaction. Response has been recevied";
                                                logComments(getVariables().get("gStrOutputFileName"),InfoDecription);
                                                info("Response received is "+http.getLastResponseContents());






                                } catch (Exception e) {


                                                InfoDecription = "An Exception has occurred in the OUTSK XAI Processing function(ProcessWSRequest) :"+e.getMessage().toString();
                                                info(InfoDecription);

                                }
                }






                public void generateAndSendReport() throws Exception {

                                try {
                                                info("Entered into generate and send email section.");
                                                String lStrFinalMessage = "";


                                                // -------------------------------------------------

                                                String outputFilePath = "";

                                                String lStrSubject = "";

                                                lStrSubject = "OATSOU Automated Test Results Report";

                                                // =====================================================

                                                // -------------------------------------

                                                File fLogDir = new File(getVariables().get("gStrOutputFilePath"));
                                                //info("log file directory "+fLogDir);
                                                if (!fLogDir.exists())
                                                                return;

                                                // Get all BCS files
                                                File[] fLogs = fLogDir.listFiles();

                                                for (int i = 0; i < fLogs.length; i++) {
                                                                //info("log files "+fLogs[i].getAbsolutePath());
                                                                File CurrFile = new File(fLogs[i].getAbsolutePath());
                                                                String strName = CurrFile.getName();

                                                                //info("Current file name : " + strName);
                                                                //info("email content is"+outputFilePath);
                                                                if (strName.endsWith(".log")) {
                                                                                outputFilePath = getVariables().get("gStrOutputFilePath")+"\\"+strName;
                                                                                lStrFinalMessage = lStrFinalMessage
                                                                                + buildMessageBody(
                                                                                                                "Execution Result for Scenario",
                                                                                                                outputFilePath);


                                                                }
                                                }

                                                // ***************** Send Email ********************

                                                if (lStrFinalMessage.trim() == "") {
                                                                return;
                                                }

                                                // **************************************************

                                                // +" - Executed on : "+ DateTime.get_Now().toString();

                                               if (lStrFinalMessage.trim() != "") {

                                                                String TableHeaderText = "";
                                                                String UIURL = "";
                                                                String Current_Testing_Environment = "";

                                                                UIURL = getVariables().get("gStrApplicationURL");
                                                                Current_Testing_Environment = getVariables().get("gStrEnvironmentName");

                                                                TableHeaderText = "OATSOU Automated test results report - ";
                                                                TableHeaderText += "Executed on : "
                                                                                + DateTime.get_Now().toString();

                                                                TableHeaderText += "<br/> <h4> ( Executed by : "
                                                                                + InetAddress.getLocalHost().getHostName() + " ) </h4>";

                                                                // ---------------------------------------

                                                                TableHeaderText += "<table>";
                                                                // -----------------------------------------------------

                                                                TableHeaderText += "<tr>";

                                                                TableHeaderText += "<td align='left' style='color:#000000;'>";
                                                                TableHeaderText += "This Automated mail is powered by  : ";
                                                                TableHeaderText += " <b> OATSOU Framework</b> ";
                                                                TableHeaderText += "</td>";

                                                                TableHeaderText += "</tr>";

                                                                // -----------------------------------------------------

                                                                TableHeaderText += "<tr>";

                                                                TableHeaderText += "<td align='left' style='color:#000000;'>";
                                                                TableHeaderText += "Current Testing Environment : ";
                                                                TableHeaderText += " <a href='" + UIURL + "'>"
                                                                + Current_Testing_Environment + "</a> ";
                                                                TableHeaderText += "</td>";

                                                                TableHeaderText += "</tr>";

                                                                // -----------------------------------------------------



                                                                TableHeaderText += "</table>";

                                                                // ---------------------------------------

                                                                String lStrBodyHead = createFinalHtmlEmailBodyTable("",
                                                                                                TableHeaderText, false);

                                                                lStrFinalMessage = lStrBodyHead + lStrFinalMessage;

                                                                // -------------------------------------



                                                                String SMTP_HOST_NAME = getVariables().get("gStrSMTP_HOST_NAME");
                                                                String SMTP_PORT = getVariables().get("gStrSMTP_PORT");
                                                                String TO_EMAIL_RECIPIENTS =getVariables().get("gStrTO_EMAIL_RECIPIENTS");

                                                                // -------------------------------------

                                                                lStrSubject = lStrSubject + " - " + Current_Testing_Environment
                                                                + " - Executed M/c "
                                                                + InetAddress.getLocalHost().getHostName();

                                                                sendMail(TO_EMAIL_RECIPIENTS, lStrSubject, lStrFinalMessage,
                                                                                                "", SMTP_HOST_NAME, SMTP_PORT);
                                                }

                                } catch (Exception e) {
                                                // TODO: handle exception
                                                info(e.getMessage().toString());
                                }

                }

                /**
                * It Creates email message body based on scenario
                */
                public String buildMessageBody(String aStrScenarioReportHeader,
                                                String outputFilePath) {

                                String lStrFinalMessage = "";

                                try {

                                                gBlnReportDBLog_RunStatus = true;

                                                String lStrRowsData = "";
                                                //info("BUILDING message body "+outputFilePath);
                                                lStrRowsData = readFileAndCreateRowsData(outputFilePath);
                                                //info("built message body "+lStrRowsData);
                                                if (lStrRowsData.trim() != "") {
                                                                lStrFinalMessage = lStrFinalMessage
                                                                + createFinalHtmlEmailBodyTable(lStrRowsData,
                                                                                                aStrScenarioReportHeader, true);
                                                }

                                                if (lStrFinalMessage != "") {
                                                                gStrReportDBLog_Scenarios = getScenarioNameFromFilePath(outputFilePath);
                                                                //    logReportInDataBase();
                                                }

                                                return lStrFinalMessage;

                                } catch (Exception e) {

                                                return lStrFinalMessage;

                                }

                }

                public String getScenarioNameFromFilePath(String outputFilePath) {

                                String lStrName = "";

                                try {

                                                if (outputFilePath.trim() == "")
                                                                return "";

                                                lStrName = outputFilePath.trim().toUpperCase().replace(".LOG", "")
                                                .split("RESULT_")[1];

                                                info(lStrName);

                                                return lStrName;

                                } catch (Exception e) {

                                                return lStrName;

                                }

                }

                /**
                * Reads output File And Creates html email body Rows Data
                * 
                 * @param filePath
                */
                public String readFileAndCreateRowsData(String filePath) throws Exception {

                                String lStrFinalRowsData = "";

                                try {
                                                info("filepath sent is "+filePath);
                                                File file = new File(filePath);

                                                if (!file.exists()){

                                                                info("file doesnt exits");
                                                                return lStrFinalRowsData;
                                                }

                                                FileInputStream fStream = new FileInputStream(filePath);
                                                DataInputStream dataInput = new DataInputStream(fStream);
                                                BufferedReader breader = new BufferedReader(new InputStreamReader(
                                                                                dataInput));

                                                String lStrLine = "";
                                                int lIntSNo = 0;
                                                //    info("line is"+breader.readLine());
                                                while ((lStrLine = breader.readLine()) != null) {
                                                                //info("line is"+breader.readLine());
                                                                if (lStrLine.trim().length() < 1)
                                                                                continue;

                                                                // *******************************

                                                                String lStrTempRowData = "";

                                                                String lStrSNo = "";
                                                                String lStrResult = "";
                                                                String lStrTestStep = "";
                                                                String lStrTestData = "";

                                                                // -------------------------------

                                                                lIntSNo += 1;

                                                                lStrSNo += "<td align='center'>";
                                                                lStrSNo += lIntSNo;
                                                                lStrSNo += "</td>";

                                                                // -------------------------------

                                                                char[] lArrChr = lStrLine.toCharArray();
                                                                boolean lBlnTabCharFound = false;

                                                                String lStrFinalLine = "";

                                                                for (int lIntChrIndex = 0; lIntChrIndex < lArrChr.length; lIntChrIndex++) {

                                                                                if (lArrChr[lIntChrIndex] == '\t') {
                                                                                                if (!lBlnTabCharFound) {
                                                                                                                lStrFinalLine += lArrChr[lIntChrIndex];
                                                                                                                lBlnTabCharFound = true;
                                                                                                }

                                                                                } else {

                                                                                                lStrFinalLine += lArrChr[lIntChrIndex];
                                                                                }
                                                                }

                                                                lStrLine = lStrFinalLine;

                                                                String[] lStrArrTemp = lStrLine.split("\t");

                                                                // info("Line :::" + lStrLine.trim());

                                                                if (lStrArrTemp != null) {

                                                                                lStrTestStep += "<td>";
                                                                                lStrTestStep += lStrArrTemp[0].trim();
                                                                                lStrTestStep += "</td>";

                                                                                if (lStrArrTemp[1].trim().toUpperCase().contains("PASSED")) {

                                                                                                lStrResult += "<td align='center' style='color: #FFFFFF;'  bgcolor='#006400'>";
                                                                                                lStrResult += lStrArrTemp[1].trim();
                                                                                                lStrResult += "</td>";

                                                                                }

                                                                                else if (lStrArrTemp[1].trim().toUpperCase().contains(
                                                                                "INFO")) {

                                                                                                lStrResult += "<td align='center' style='color: #C1CDCD;'  bgcolor='#F8F8FF'>";
                                                                                                lStrResult += lStrArrTemp[1].trim();
                                                                                                lStrResult += "</td>";

                                                                                } else if (lStrArrTemp[1].trim().toUpperCase().contains(
                                                                                "DUPLICATE")) {

                                                                                                lStrResult += "<td align='center' style='color: #FFFFFF;'  bgcolor='#1874CD'>";
                                                                                                lStrResult += lStrArrTemp[1].trim();
                                                                                                lStrResult += "</td>";

                                                                                }

                                                                                else {

                                                                                                gBlnReportDBLog_RunStatus = false;

                                                                                                lStrResult += "<td align='center' style='color: #FFFFFF;'  bgcolor='#CD0000'>";
                                                                                                lStrResult += lStrArrTemp[1].trim();
                                                                                                lStrResult += "</td>";
                                                                                }

                                                                }

                                                                // -------------------------------

                                                                String lStrBuildDataTemp = "";

                                                                if (lStrTestStep.contains("::")) {

                                                                                lStrArrTemp = null;
                                                                                lStrArrTemp = lStrTestStep.split("::");

                                                                                lStrTestData = lStrArrTemp[1].trim();
                                                                                lStrBuildDataTemp = lStrArrTemp[0].trim();

                                                                                lStrArrTemp = null;
                                                                                lStrArrTemp = lStrBuildDataTemp.split(" ");

                                                                                lStrBuildDataTemp = lStrArrTemp[lStrArrTemp.length - 1]
                                                                                                                .trim()
                                                                                                                + "::" + lStrTestData;
                                                                }

                                                                if ((lStrBuildDataTemp.trim() == "")||(lStrBuildDataTemp.contains("is:http")))
                                                                                lStrBuildDataTemp = "-- NA --";

                                                                lStrTestData = "<td>";
                                                                lStrTestData += lStrBuildDataTemp.trim();
                                                                lStrTestData += "</td>";

                                                                // -------------------------------

                                                                lStrTempRowData = "<tr>";
                                                                lStrTempRowData += lStrSNo + lStrTestStep + lStrTestData
                                                                + lStrResult;
                                                                lStrTempRowData += "</tr>";

                                                                // *******************************

                                                                if (lStrTempRowData.trim() != "")
                                                                                lStrFinalRowsData += lStrTempRowData;
                                                }
                                                //info("RETURNED ROWS ARE "+lStrFinalRowsData);
                                                return lStrFinalRowsData;

                                } catch (Exception e) {
                                                // TODO: handle exception
                                                info("EXCEPTION IS "+e.getMessage());
                                                return lStrFinalRowsData;
                                }

                }

                /**
                * Created html Email BodyT able
                * 
                 * @param RowsData
                * @param TableHeaderText
                */
                public String createFinalHtmlEmailBodyTable(String RowsData,
                                                String TableHeaderText, Boolean IsEmailBodyMessagePart)
                throws Exception {

                                String lStrFinalMessage = "";
                                //info("Creating final html body ");
                                try {

                                                // TableHeaderText = TableHeaderText + " - Automated Test Results";
                                                // TableHeaderText += "<br/>";
                                                // TableHeaderText += "<h4>Executed on : "
                                                // + DateTime.get_Now().toString() + "</h4>";

                                                String lStrTableBorder = "0";

                                                if (IsEmailBodyMessagePart) {
                                                                lStrTableBorder = "4";
                                                }

                                                lStrFinalMessage = "<html><body>";

                                                lStrFinalMessage += "<STYLE TYPE='text/css'>TD{ font-size: 11pt;}Th{ font-size: 13.5pt;}</STYLE>";

                                                lStrFinalMessage += "<Table cellspacing='4' cellpadding='4' border='"
                                                                + lStrTableBorder
                                                                + "' style='color: #292421' align='center' WIDTH='75%'  >";

                                                String lStrTrColor = "#008B8B";

                                                if (IsEmailBodyMessagePart) {
                                                                lStrTrColor = "#0000FF"; // Brown
                                                }

                                                // lStrFinalMessage += "<tr align='center' style='color: "+
                                                // lStrTrColor + "'><TH COLSPAN='4'><BR><H2>"+ TableHeaderText +
                                                // "</H2></TH></tr>";
                                                lStrFinalMessage += "<tr align='center' style='color: "
                                                                + lStrTrColor + "'><TH COLSPAN='4'><H3>" + TableHeaderText
                                                                + "</H3></TH></tr>";

                                                if (IsEmailBodyMessagePart) {

                                                                lStrFinalMessage += "<tr align='center'  style='font-weight: bold;color: #DC143C'><TH>S No</TH><TH>Test Step</TH><TH>Test Data</TH><TH>Result</TH></tr>";

                                                                // *************************************************

                                                                lStrFinalMessage += RowsData;

                                                                // *************************************************

                                                                lStrFinalMessage += "</tr>";
                                                }

                                                lStrFinalMessage += "</table>";
                                                lStrFinalMessage += "</body></html>";
                                                lStrFinalMessage += "<br/>";
                                                //info("Created final html body "+lStrFinalMessage);
                                                return lStrFinalMessage;

                                } catch (Exception e) {
                                                // TODO: handle exception
                                                //info("Exceptioncaught "+e.getMessage());
                                                return lStrFinalMessage;
                                }
                }

                public boolean sendGenericMail(String aStrRecipients, String aStrSubject,
                                                String aStrMessage, String SMTP_HOST_NAME, String SMTP_PORT)
                throws AbstractScriptException {

                                try {

                                                String lStrSubject = "";
                                                String lStrFinalMessage = "";

                                                // -------------------------------------

                                                //info("Entered into generic mail section");

                                                // -------------------------------------

                                                // lStrFinalMessage = "<html><body>";
                                                //
                                                // lStrFinalMessage +=
                                                // "<STYLE TYPE=text/css'>TD{ font-size: 11pt;}Th{ font-size: 13.5pt;}</STYLE>";
                                                //
                                                // lStrFinalMessage +=
                                                // "<Table cellspacing='4' cellpadding='4' border='4' style='color: #292421' align='center' WIDTH='75%'  >";
                                                //
                                                // String lStrTrColor = "#008B8B";
                                                //
                                                // lStrFinalMessage +=
                                                // "<tr align='center'  style='font-weight: bold;color: #DC143C'>";
                                                //
                                                // // *************************************************
                                                //
                                                // lStrFinalMessage += "<td align='center'>";
                                                lStrFinalMessage += aStrMessage;
                                                // lStrFinalMessage += "</td>";
                                                //
                                                // // *************************************************
                                                //
                                                // lStrFinalMessage += "</tr>";
                                                //                
                                                //
                                                // lStrFinalMessage += "</table>";
                                                // lStrFinalMessage += "</body></html>";
                                                // lStrFinalMessage += "<br/>";

                                                // -------------------------------------

                                                lStrSubject = aStrSubject + " - Executed M/c "
                                                + InetAddress.getLocalHost().getHostName();

                                                // -------------------------------------

                                                sendMail(aStrRecipients, lStrSubject, lStrFinalMessage, "",
                                                                                SMTP_HOST_NAME, SMTP_PORT);

                                                return true;
                                } catch (Exception e) {
                                                fail("Exception in  sendGenericMail " + e.getMessage());
                                                return false;
                                }
                }

                /**
                * To Send Mail
                * 
                 * @param aStrRecipients
                * @param aStrSubject
                * @param aStrMessage
                * @param aStrFrom
                */
                public void sendMail(String aStrRecipients, String aStrSubject,
                                                String aStrMessage, String aStrFrom, String SMTP_HOST_NAME,
                                                String SMTP_PORT) throws Exception {

                                boolean debug = false;

                                /*
                                * String SMTP_HOST_NAME = "internal-mail-router.oracle.com"; String
                                * SMTP_PORT = "25"; // default port is 465 !
                                */
                                // Set the host smtp address
                                Properties props = System.getProperties();

                                props.put("mail.smtp.host", SMTP_HOST_NAME);
                                props.put("mail.smtp.port", SMTP_PORT);

                                Session lObjSession = Session.getDefaultInstance(props, null);

                                Message lObjMsg = new MimeMessage(lObjSession);

                                // ------------------------------------------------

                                if (aStrFrom == null)
                                                aStrFrom = "noreply_OUTSKMailSender@oracle.com";
                                else if (aStrFrom.trim() == "")
                                                aStrFrom = "noreply_OUTSKMailSender@oracle.com";

                                // set the from and to address
                                InternetAddress lObjInternetAddressFrom = new InternetAddress(aStrFrom);

                                lObjMsg.setFrom(lObjInternetAddressFrom);

                                // ------------------------------------------------

                                InternetAddress[] lObjInternetAddressTo = null;

                                if (aStrRecipients.contains(";")) {

                                                String[] lArrStrRecipients = aStrRecipients.split(";");

                                                if (lArrStrRecipients == null) {
                                                                info("Please provide recipient address list in this format.  aaa@123.com;bbb@123.com... etc");
                                                                return;
                                                }

                                                int lIntToCount = lArrStrRecipients.length;

                                                lObjInternetAddressTo = new InternetAddress[lIntToCount];

                                                for (int lIntIndex = 0; lIntIndex < lIntToCount; lIntIndex++) {
                                                                String lStrToMailIDTemp = lArrStrRecipients[lIntIndex].trim();

                                                                if (lStrToMailIDTemp.trim() == "")
                                                                                continue;

                                                                if (!validateEmail(lStrToMailIDTemp)) {
                                                                                info("Invalid recipient email ID(s).");
                                                                                return;
                                                                }

                                                                lObjInternetAddressTo[lIntIndex] = new InternetAddress(
                                                                                                lStrToMailIDTemp);
                                                }

                                } else {

                                                if (!validateEmail(aStrRecipients)) {
                                                                info("Invalid recipient email ID(s).");
                                                                return;
                                                }

                                                lObjInternetAddressTo = new InternetAddress[1];
                                                lObjInternetAddressTo[0] = new InternetAddress(aStrRecipients
                                                                                .trim());
                                }

                                lObjMsg.addRecipients(Message.RecipientType.TO, lObjInternetAddressTo);

                                // ------------------------------------------------

                                MimeBodyPart messageBodyPart = new MimeBodyPart();
                                // messageBodyPart.setText(aStrMessage);
                                messageBodyPart.setContent(aStrMessage, "text/html");

                                Multipart multipart = new MimeMultipart();
                                multipart.addBodyPart(messageBodyPart);

                                try {

                                                Object lObjAttachFilePath = null;
                                                if (lObjAttachFilePath != null) {
                                                                String lStrAttachFilePath = lObjAttachFilePath.toString();
                                                                if (lStrAttachFilePath.trim() != "") {
                                                                                if ((new File(lStrAttachFilePath)).exists()) {
                                                                                                //info("Entered into Attachment part");
                                                                                                messageBodyPart = new MimeBodyPart();
                                                                                                DataSource source = new FileDataSource(
                                                                                                                                lStrAttachFilePath);
                                                                                                messageBodyPart.setDataHandler(new DataHandler(source));
                                                                                                messageBodyPart.setFileName(lStrAttachFilePath);
                                                                                                multipart.addBodyPart(messageBodyPart);
                                                                                }
                                                                }
                                                }

                                } catch (Exception e) {

                                }

                                // ------------------------------------------------

                                // Setting the Subject and Content Type
                                lObjMsg.setSubject(aStrSubject);
                                // lObjMsg.setContent(aStrMessage, "text/html");
                                lObjMsg.setContent(multipart);
                                lObjMsg.setSentDate(new Date());

                                try {

                                                Transport.send(lObjMsg);
                                                //info("Mail sent.");

                                                /*    String lBlnSVNCheckFlag = getScript("OUTSK_Global_Constants")
                              .callFunction("getSVN_AutomationTestResults_loggingFlag")
                              .toString();

                  if (lBlnSVNCheckFlag.equalsIgnoreCase("true")) {
                        info("Checking in message into SVN.");
                        getScript("MWM_SVN_Process").callFunction("checkinReportToSVN",
                                    aStrSubject, aStrMessage);
                        info("Done with Checking in message into SVN.");
                  }
                                                */
                                } catch (Exception e) {
                                                // TODO: handle exception
                                                info(e.getMessage().toString());
                                }
                }

                public boolean validateEmail(String sEmail) {
                                try {
                                                String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                                                Pattern pattern;
                                                Matcher matcher;

                                                pattern = Pattern.compile(EMAIL_PATTERN);
                                                matcher = pattern.matcher(sEmail);
                                                return matcher.matches();
                                } catch (Exception e) {
                                                // TODO: handle exception
                                                return false;
                                }

                }
}
