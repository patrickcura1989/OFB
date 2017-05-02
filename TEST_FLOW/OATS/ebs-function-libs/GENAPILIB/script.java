import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.browser.api.*;
import oracle.oats.scripting.modules.functionalTest.api.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.webdom.api.*;
import oracle.oats.scripting.modules.formsFT.api.*;
import oracle.oats.scripting.modules.applet.api.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Types;

import javax.swing.UIManager;

import lib.*;


public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	@FunctionLibrary("EXCELLIB") lib.ebsqafwk.EXCELLIB eXCELLIB;
	@FunctionLibrary("GENLIB") lib.ebsqafwk.GENLIB gENLIB;
	
	String 				DRIVER 				= "oracle.jdbc.driver.OracleDriver";

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
	 * This method prompts the user for the DB Connection Details. It creates the input box
	 * and then user enters the details for the DB. The details are set as session  variables 
	 * which will be available throughout the session. To get the details user would have to 
	 * create the object of this class and call this method. This method does not return anything
	 * and this is why it is set as return type void.
	 * <p>
	 *     Call for this method can be done as follows:<br>
	 *     EBSLibrary.oracle_prompt_sql()
	 * <p>    
	 * To access the session variables user would need to do following
	 * <p>	   
	 * 	getVariables().get("env_dbq");<br>
	 *	getVariables().get("env_dsn);<br>
	 *	getVariables().get("env_user");<br>
	 *	getVariables().get("env_pw");<br>
	 *	getVariables().get("env_server");<br>
	 *	getVariables().get("env_port");<br>
	 * <p>
	 */
	public void oracle_prompt_sql() throws Exception
	{
		String db_host = "";
		String db_instance_name = "";
		String db_port = "";
		String db_user_name = "";
		String db_password = "";
			
		
		/*Getting the user input for DB details */
		db_host 			= oracle_input_dialog("Please enter database \"Host\" for SQL connection");
		db_instance_name 	= oracle_input_dialog("Please enter database \"Instance Name\" for SQL connection");
		db_port 			= oracle_input_dialog("Please enter database \"Port Number\" for SQL connection");
		db_user_name 		= oracle_input_dialog("Please enter database \"Username\" for SQL connection");
		db_password 		= oracle_input_dialog("Please enter database \"Password\" for SQL connection");
		
		System.out.println("db_host1:"+db_host+"test");
		
		if(db_host == null || "".equals(db_host)){
			
			db_host = gENLIB.getProperty("DB_HOST");
			System.out.println("db_host:"+db_host);
		}
		
		if(db_instance_name == null || "".equals(db_instance_name)){
			
			db_instance_name = gENLIB.getProperty("DB_INSTANCE_NAME");
			System.out.println("db_instance_name:"+db_instance_name);
		}
		
		if(db_port == null || "".equals(db_port)){
			
			db_port = gENLIB.getProperty("DB_PORT");
			System.out.println("db_port:"+db_port);
		}
		
		if(db_user_name == null || "".equals(db_user_name)){
			
			db_user_name = gENLIB.getProperty("DB_USER_NAME");
			System.out.println("db_user_name:"+db_user_name);
		}
		
		if(db_password == null || "".equals(db_password)){
			
			db_password = gENLIB.getProperty("DB_PASSWORD");
			System.out.println("db_password:"+db_password);
		}
		
		/*Setting the values to the environmental variables*/
		getVariables().set("DB_HOST", db_host);
		getVariables().set("DB_PORT", db_port);
		getVariables().set("DB_INSTANCE_NAME", db_instance_name);
		getVariables().set("DB_USER_NAME", db_user_name);
		getVariables().set("DB_PASSWORD", db_password);
		
		//Storing data in INI file
		gENLIB.setProperty("DB_HOST", db_host);
		gENLIB.setProperty("DB_PORT", db_port);
		gENLIB.setProperty("DB_INSTANCE_NAME", db_instance_name);
		gENLIB.setProperty("DB_USER_NAME", db_user_name);
		gENLIB.setProperty("DB_PASSWORD", db_password);
	}
	
	
	/**
	 * Connecting to Database
	 * 		Host, Port and Instant Name are stored in the global variables
	 * 
	 * @throws Exception
	 */
	public void connectDB() throws Exception
	{
		
		try
		{ 
			String	CONNECTION_STRING 	= "jdbc:oracle:thin:@"+gENLIB.getProperty("DB_HOST")+":"+gENLIB.getProperty("DB_PORT")+":"+gENLIB.getProperty("DB_INSTANCE_NAME");
			
			//Execution of Anonymous Block
			Connection connection 	= (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if(connection == null){
				
				info("connecting DB");
				
				Class.forName(DRIVER);
				
				Connection con  = DriverManager.getConnection(CONNECTION_STRING, gENLIB.getProperty("DB_USER_NAME"), gENLIB.getProperty("DB_PASSWORD"));
				
				con.setAutoCommit(false);
				
				getVariables().setAsObject("CONNECTION_OBJ", con);
				
				info("Connection Established with \"HOST:\""+gENLIB.getProperty("DB_HOST")+"**\"PORT:\""+gENLIB.getProperty("DB_PORT")+"**\"INSTANCE_NAME:\""+gENLIB.getProperty("DB_INSTANCE_NAME"));
				
				System.out.println("*** Method: EstablishConnection   -- Status : Connected");
			}
		}
		catch(Exception e)
		{
			reportFailure("Unable to Load Driver "+e+"    Method: EstablishConnection");
			
		}
	}
	
	/**
	 * Execute a script
	 * 
	 * @param anonymousBlock
	 * 			Anonymous Block
	 * @return
	 * @throws Exception
	 */
	public String executeScript(String anonymousBlock) throws Exception
	{
		info("Executing of Anonymous Block Started..");
		
		//Output varaible data after execution of Anonymous Block
		String scriptOutput ="";
		
		try
		{	
			createPLSQLAnonymousBlock(anonymousBlock);
			
			
			//Connect DB
			connectDB();
			
			//Clob object for storing output data
			Clob clob = null;
			
			
			//Check if anonymous block is empty or not
			if(anonymousBlock == null || anonymousBlock.isEmpty())
			{
				reportFailure("Unable to execute Anonymous block as it is either empty or null.");
				return "";
			}
			

				
			//Execution of Anonymous Block
			Connection connection 	= (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			//if Connection is null, then connect to DB
			if(connection == null){
				connectDB();
			}
			
			//Enable output DBMS_OUTPUT
			enableOutput(50000);
			
			CallableStatement cs 	= connection.prepareCall(anonymousBlock);	// Prepared Call
			cs.registerOutParameter(1, Types.CLOB);	    						// Registering Output Parameters
			cs.execute();	    												// Execution of Anonymous Blcok
			
	
			
			info("Anonymous Block Executed Successfully.");
			
			info("Reading output data from output variables.");
			//Parsing Clob Object
			StringBuilder 	sb 		= 	new StringBuilder();
			clob 					= 	cs.getClob(1); //Get First Parameteri.e CLOB object from Register output Parameter list
			Reader 			reader	= 	clob.getCharacterStream();
			BufferedReader 	br 		= 	new BufferedReader(reader);
			
			// Reading from BufferReader and store it in String Builder
			int line_no;
			while(-1 != (line_no = br.read()))
			{
			    sb.append((char)line_no);
			}			
			//Close Buffer Reader
			br.close();
			
			//Conver String Builder data to String object
			scriptOutput =  sb.toString();
			
			//Close Callable Statement
			cs.close();
			

					
		}
		catch(Exception e)
		{
			reportFailure("Exception During Execution of Anonymous block is : "+e);
			e.printStackTrace();
		}
		
		return scriptOutput;				
	}
	
	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public void commit() throws Exception
	{
		try
		{
			Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if (connection.isClosed()){
				reportFailure("Unable to commit the transaction as connection is closed.");
			}else{
				connection.commit();
			}
		}
		catch(Exception e)
		{
			reportFailure("Exception in method commit() is : "+e);
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public void verifyData(String expectedData, String actualData, String Context) throws Exception
	{
		try
		{
			if (compareStrings(expectedData, actualData)){
				info("Verified "+Context+" (Expected data: \""+expectedData+"\" and Actual data: \""+actualData+"\") successfully.");
			}else{
				reportFailure("Unable to verify "+Context+" (Expected data: \""+expectedData+"\" with Actual data: \""+actualData+"\").");
			}
		}
		catch(Exception e)
		{
			reportFailure("Exception in method PLSQL VerifyData() is : "+e);
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	public void conditionalCommit(String expectedValue, String actualValue) throws Exception
	{
		info("Conditional Commit: Expected Return Status:\""+expectedValue+"\" *** Actual Return Status:\""+actualValue+"\"");
		
		//Conditional Transaction can be done this way 
		if(compareStrings(expectedValue, actualValue))
		{
			commitTransaction();
			info("Expected Return Status:\""+expectedValue+"\" is matched with Actual Return Status:\""+actualValue+"\". Hence Transaction Committed.");
		}		
		else
		{
			rollbackTransaction();
			info("Expected Return Status:\""+expectedValue+"\" is not matched with Actual Return Status:\""+actualValue+"\". Hence Transaction RollBack.");
		}
	}
	
	
	/**
	 * This method is used to close an existing database connection		 		
	 */
	public void closeConnection() throws Exception
	{
		try
		{
			Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if (connection.isClosed())
			{
				reportFailure("Unable to closed the db connection as connection is already closed.");
			}else
			{
				connection.close();
			}
		}
		catch(Exception e)
		{
			reportFailure("Exception in method closeConnection() is : "+e);
			e.printStackTrace();
		}
	}
	
		
	/**
	 * This method is used to parse the Scipt output and converts the output to Key-Value pair of PLSQL Variable Name and Value
	 * 	
	 * @param scriptOutput  Script Output returned through CLOB Protocol		   		 
	 */
	public void parseOutputVariables(String scriptOutput) throws Exception
	{
		try
		{				
			//Checking whether scriptouyput is empty or null
			if(scriptOutput == null || scriptOutput.isEmpty())
			{
				info("There is no data in the scriptOutput variable for parsing.");	
				return;
			}

			//Checking whether scriptouyput is empty or null
			if(scriptOutput.indexOf("~~~")== -1 )
			{
				info("There is no data in the scriptOutput variable for parsing.");	
				return;
			}			
			//Split the string using "~~~ (;)" as delimiter			
			StringTokenizer st	 	= new StringTokenizer(scriptOutput,  "~~~"); 
			
			
			if(! (st.countTokens() >= 0) )
			{
				info("PLSQLEngine : No Output Parameter  Exception");	
				return;
			}
			
			info("Procedure Output Data:");
			
			while (st.hasMoreElements()) 
			{							
				String token = (String) st.nextElement();  		//Get Next element
				token = token.trim();							//Remove leading and trailing  spaces							
				if(token.indexOf(":")!= -1 )
				{
					String variableName = token.substring(0,token.indexOf(":")).trim();
					String variableData   = token.substring(token.indexOf(":")+1, token.length()).trim();								
					info(String.format("%-50s \t:\t %-100s" ,variableName, variableData));
					
					gENLIB.setProperty(variableName, variableData);
					//info(variableName+":"+variableData);
					//getVariables().set(variableName, variableData);					
				}
			}
			
			//Show Output
		    showOutput();
			
			//Disable OUTPUT
			disableOutput();
												
		}
		catch(Exception e)
		{
			reportFailure("Exception in the Method parseOutputVariables() is : "+e);
			e.printStackTrace();
		}
	}
	
	
	public String GetErrorHandlingBlock(){
		
		String errorHandlingProcdure = ""+
		
		"\nPROCEDURE GET_MESSAGES (L_MSG_COUNT NUMBER) IS														\n"+
	    "		OUT_MESSAGE_INDEX NUMBER;																	\n"+
	    "		OUT_MESSAGE_DATA  VARCHAR2(20000);															\n"+
	    "		X_MESSAGE_LIST          ERROR_HANDLER.ERROR_TBL_TYPE;										\n"+
	    "		BEGIN																						\n"+
	    "		DBMS_OUTPUT.PUT_LINE('\nMessage Count :  '||L_MSG_COUNT);										\n"+
	    "		FOR I IN 1 .. L_MSG_COUNT LOOP																\n"+
	    "		FND_MSG_PUB.GET					      														\n"+
	    "		(																							\n"+
	    "						P_MSG_INDEX     => I,														\n"+
	    "						P_ENCODED       => 'F',														\n"+
	    "						P_DATA       	=> OUT_MESSAGE_DATA,										\n"+
	    "						P_MSG_INDEX_OUT => OUT_MESSAGE_INDEX										\n"+
	    "		);																							\n"+
	    "		DBMS_OUTPUT.PUT_LINE('Message  ('||I||') : '||OUT_MESSAGE_DATA);						\n"+
	    "		END LOOP;																					\n"+				    
	    "--   Another way of getting error messages (used by PIM Product - using ERROR HANDLER --			\n"+  
	    "     --------------------------------------------------------------------------					\n"+
	    "		ERROR_HANDLER.GET_MESSAGE_LIST(X_MESSAGE_LIST=>X_MESSAGE_LIST);								\n"+
	    "		FOR I IN 1..X_MESSAGE_LIST.COUNT LOOP														\n"+
	    "		DBMS_OUTPUT.PUT_LINE('Error Message  ('||I||') : '||X_MESSAGE_LIST(I).MESSAGE_TEXT);		\n"+
	    "		END LOOP;																					\n"+	
	   // "		ROLLBACK;		  																			\n"+
	    "		END GET_MESSAGES;\n";
		
		return errorHandlingProcdure;
		
	}
	
	/**
	 * This method is used to select some values from a database table and assign then to openscript variables		 		
	 */	
	public void selectQuery(String tableName,HashMap<String,String> columns, ArrayList<String[]> query_conditions) throws Exception
	{
		try
		{	
			//Connect DB
			connectDB();
			
			ArrayList<String[]>		conditions 	 = new ArrayList<String[]>(query_conditions);	//(ArrayList) ;			
			HashMap<String,Integer> columnTypes  = getTableColumnTypes(tableName);				// Get Datatypes of All Columns in the Table
			HashMap<String,String>  columnNames  = columns;										// Key Value Pair of Column to be Selected  , Global Variable Name to which the value to be assigned		
            String  fieldName 	  = "";
            String  variableName  = "";
            Integer	dataType	  = null;
            String  rsData		  = "";

						
			//Execute Select Query

            String 		selectQuery   	= buildSelectQuery(tableName,columnNames,conditions);		//Build Select Query						
			info("**selectQuery**:"+selectQuery);
            
            Connection 	connection 		= (Connection)getVariables().getAsObject("CONNECTION_OBJ"); //Get Connection
			Statement 	statement		= connection.createStatement();			
			
			//Execute Query
			ResultSet 	rs 				= statement.executeQuery(selectQuery);
			
			info("Select Query Executed : "+selectQuery);
			
	        if (rs.next()) 
	        {
	        	for(Entry<String, String> entry : columnNames.entrySet())
	        	{
	                fieldName 	  = entry.getKey();	//Get Field Name/Column Name from Hashmap
	                variableName  = entry.getValue();	               
	                fieldName	  = fieldName.toUpperCase();
	                	                
	                if (! columnTypes.containsKey(fieldName))	//Check if the column exists in Table
	                {
	                	reportFailure("Field Name Does not exist in Table");
	                	return;
	                }
	                else
	                {
	                	dataType = columnTypes.get(fieldName);	// Get Data Type of Column              	
	                	
	                	switch (dataType) 
	                	{
	                    	case java.sql.Types.CHAR:
	                    	case java.sql.Types.VARCHAR:
	                    	case java.sql.Types.NCHAR:
	                    	case java.sql.Types.NVARCHAR:
	                    	case java.sql.Types.LONGVARCHAR:
	                    			                    		
	                    		rsData = rs.getString(fieldName);
	                    		break;
	                    		
	                    	case java.sql.Types.INTEGER:
	                    		
	                    		rsData =  Integer.toString(rs.getInt(fieldName));	                    			                    
	                    		break;

	                    	case java.sql.Types.NUMERIC:
	                    		
	                    		rsData = rs.getBigDecimal(fieldName).toString();	                    		
	                    		break;

	                    	case java.sql.Types.TIMESTAMP:
	                    	case java.sql.Types.DATE:
	                    	case java.sql.Types.TIME:	                    		
	                    		
	                    		rsData = rs.getDate(fieldName).toString();	                    		
	                    		break;
	                    		
	                    	case java.sql.Types.BIT:
	                    	case java.sql.Types.BOOLEAN:
	                    		
	                    		rsData = new Boolean(rs.getBoolean(fieldName)).toString();	                    		               	
	                    		break;	

	                    }//End Switch	               
	                }//End Else
	                
	                //Setting value in data.properties file and global variables
	                gENLIB.setProperty(variableName,rsData);
	                
	                info("variableName:"+variableName+"  Value:"+rsData);
	                
                	dataType  		= 0;
                	fieldName 		= "";
                	variableName 	= "";
	             
	        	}//End Number of Columns               	                	                	              
	        }
	        else
	        {
	        	reportFailure("No Records Retrieved ");
	        	return;
	        }
		}
		catch(Exception e)
		{
			reportFailure("Unable to Execute Select Statement "+e+"    Method: selectQuery");
		}

		
	}
	
	/**
	 * This method is used to insert data into an interface table from an excel sheet		 		
	 */
	public void insertTable(String tableName,String sheetName) throws Exception
	{
		//Excel Path
		String excelPath  = "";		
		int rowStartIndex = 3; // Third Row in the Excel where actual data starts
		int colRow        = 2; // Second Row in the Excel i.e Column Names
		
		try
		{	
			//Connect DB
			connectDB();
			
			Connection 	connection 	= (Connection)getVariables().getAsObject("CONNECTION_OBJ"); //Get Connection
			
			//Get the Script path
			excelPath = getScriptDataPath();
			info("Excel Path:"+excelPath);
			
			eXCELLIB.getExcel(excelPath, colRow);
			
			//Get Column Types for all the column in the specified table
			HashMap<String,Integer> tableColumnTypes = getTableColumnTypes(tableName);// Get Datatypes of All Columns in the Table
			//info("tableColumnTypes:"+tableColumnTypes.toString());
			
			//Excel Row Count
			int rowCount 	=  eXCELLIB.getRowCount(sheetName);	
			info("*****************sheetName:"+sheetName+"  rowCount:"+rowCount+"**********************");
			
			//Insert Each Row
			for(int rowIndex = rowStartIndex, primaryKeyIndex=1; rowIndex <= rowCount; rowIndex ++,primaryKeyIndex++)
			{	
				//HashMap<ColumnName,Value>
				HashMap<String,String> rowData = new HashMap<String,String>();
				
				//Get specific Row data from excel
				//Row data will have only only those column which have a values in the specific row
				rowData = eXCELLIB.getRowData(sheetName, "Primary_Key", primaryKeyIndex+"");
				info("Row Number:"+primaryKeyIndex+" -- rowData:"+rowData.toString());
				
				if(rowData.size() == 0){// This will happen when only primary key is passed.
					info("There is no data in the sheet\""+sheetName+"\" to insert into Database table.");
					continue;
				}
				
				//Build Query
				String insertQuery = buildInsertQuery(tableName, tableColumnTypes, rowData);		//Prepared Statement Creation
				
				//Create PreparedStament Object as insert tableName (col1,col2) values (?,?);
				PreparedStatement 	pst	= null;
				pst 					= connection.prepareStatement(insertQuery);	
				
				//Insert Values in to PreparedStatment based on values provided
				int colValIndex=1;
				for(String key: rowData.keySet()){
		            
					//Column Name
					String dataColumnName = key;
					
					//Value
					String dataColumnValue = rowData.get(key);
					
					//Column Data Type
					Integer colDataType = tableColumnTypes.get(dataColumnName);
					
					//info("colValIndex:"+colValIndex+"  dataColumnValue:"+dataColumnValue+"  colDataType:"+colDataType);
					
					//Set the Cell value based on Type
					prepareValueBasedOnColType(pst,colValIndex, dataColumnValue, colDataType);	
					
					
					colValIndex++;

		        }//End of For loop : RowData KeySet
				
				
				//Display Query
				//info(pst.toString());
				
				//Execute Query
				int insertCount = pst.executeUpdate();
				
    			if(insertCount > 0 )
    			{
    				commitTransaction();
    				info("Row number "+primaryKeyIndex+ " inserted successfully.");
    			}
    			
			}//End of For loop : Number of Rows in the Excel
				
		}
		catch(Exception e)
		{
			reportFailure("Unable to Insert Data Exeption : "+e+"    Method: insertData");
		}
		
	}
	

	private String buildInsertQuery(String tableName,HashMap<String,Integer> tableColumnTypes, HashMap<String,String> rowData)throws Exception
	{
		String insertQuery = "INSERT INTO "+tableName + " ( ";
		
		String columnsPart 	=	"";	//Columns Part
		String placeHolders = 	""; //Values Part
		
		//Iterate through each key(Column) available in row and check the the column exists in main table or not
		for(String key: rowData.keySet()){
            
			//Column Name
			String dataColumnName = key;
			
			//Value
			String dataColumnValue = rowData.get(key);
				
			if (tableColumnTypes.containsKey(dataColumnName.toUpperCase()))	//Check if the column exists in Table
            {	
    			columnsPart  += " "+dataColumnName+" ,";
    			placeHolders += " ? ,";
            }				

        }
		

		columnsPart = columnsPart.trim();
		if(columnsPart.endsWith(",")) columnsPart = columnsPart.substring(0,columnsPart.length()-1);
		
		placeHolders = placeHolders.trim();
		if(placeHolders.endsWith(",")) placeHolders = placeHolders.substring(0,placeHolders.length()-1);
		
		insertQuery += columnsPart+" ) VALUES ( "+placeHolders+ " ) ";
			
		info("Insert Query : "+insertQuery);
		
		return insertQuery; 
		
	}
	
	
	private void prepareValueBasedOnColType(PreparedStatement pst, int dbColIndex, String columnValue, int colDataType) throws Exception
	{
		//System.out.println("columnValue :"+columnValue+" colDataType :"+colDataType);
		String cellValue = columnValue;
		
		if(cellValue.equals("#NULL")){
			cellValue = "";
		}else if(cellValue.equals("#EMPTY")){
			cellValue = "";
		}else if(cellValue.equals("#SPACE")){
			cellValue = " ";
		}else if(cellValue.startsWith("{{") && cellValue.endsWith("}}")){
			cellValue = eval(cellValue);
			//System.out.println("cellValue :"+cellValue);
		}else if(cellValue.startsWith("#SYSDATE")){
			
			int days = 0;
			
			if(cellValue.contains("(") && cellValue.contains(")")){
				
				cellValue = cellValue.substring(cellValue.indexOf("(")+1,cellValue.indexOf(")")).trim();
				//System.out.println("Date:"+cellValue);
				days = Integer.parseInt(cellValue);
				
			}
			
			cellValue = gENLIB.getSysDate("DEFAULT","dd-MMM-yyyy",days);
			
		}else if(cellValue.startsWith("#SEQUENCE")){
			
			String sequence = cellValue.substring(cellValue.indexOf("(")+1,cellValue.indexOf(")")).trim();
			
			//System.out.println("Sequence :"+sequence);
			String[] seqDetails = sequence.split(",");
			cellValue = getSequence(seqDetails[0]);
			
			if(seqDetails.length == 2){
				
				System.out.println("Sequence :"+seqDetails[0]+" Sequence Value:"+cellValue+" SeqVariable:"+seqDetails[1]);
				
				gENLIB.setProperty(seqDetails[1], cellValue);
			}else{
				System.out.println("Sequence :"+sequence+" Sequence Value:"+cellValue);
			}
			
		}
		
    	switch (colDataType) 
    	{
        	case java.sql.Types.CHAR:
        	case java.sql.Types.VARCHAR:
        	case java.sql.Types.NCHAR:
        	case java.sql.Types.NVARCHAR:
        	case java.sql.Types.LONGVARCHAR:
        			                    	
        		
        		if(cellValue == null || cellValue.equals(""))     	pst.setNull		(dbColIndex,colDataType); 
        		else 												pst.setString	(dbColIndex,cellValue);
        		break;
        		
        	case java.sql.Types.INTEGER:
        			                    		                    			                   
        		if(cellValue == null || cellValue.equals("") )     	pst.setNull		(dbColIndex,colDataType); 
        		else 												pst.setInt		(dbColIndex,Integer.parseInt(cellValue));
        		break;
        		
        	case java.sql.Types.NUMERIC:
        		
                    if(cellValue.indexOf(",")!= -1) cellValue.replaceAll(",", "");
        		if(cellValue == null || cellValue.equals("") )       pst.setNull		 (dbColIndex,colDataType); 
        		else 												pst.setBigDecimal(dbColIndex, new BigDecimal(cellValue));
        		break;
        		
        	case java.sql.Types.TIMESTAMP:
        	case java.sql.Types.DATE:
        	case java.sql.Types.TIME:	   
        		
        		if(cellValue == null || cellValue.equals("") )      pst.setNull	(dbColIndex,colDataType);   
        		else 											   pst.setDate	(dbColIndex,new java.sql.Date(new java.util.Date(cellValue).getTime()));	                    			                    			                    
        		break;
        		
        	case java.sql.Types.BIT:
        	case java.sql.Types.BOOLEAN:
        		
        		if(cellValue == null || cellValue.equals("") )       pst.setNull	  (dbColIndex,colDataType);   
        		else 												pst.setBoolean(dbColIndex,new Boolean(cellValue));	                    			                    		                    		              
        		break;	

        }// End Switch	   
       
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

	//*******************************************************************************************************************************************************		
	//************													PRIVATE METHODS									*****************************************
	//******************************************************************************************************************************************************* 
	
	private HashMap<String,Integer> getTableColumnTypes(String tableName) throws Exception
	{
		HashMap<String,Integer> columnType = new HashMap<String,Integer>();
		
		try
		{
			Connection con 			= (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			Statement 	st 			= con.createStatement();
			ResultSet 	rs 			= st.executeQuery("SELECT * FROM "+tableName.toUpperCase());
			ResultSetMetaData rsmd 	= rs.getMetaData();
			int count 				= rsmd.getColumnCount();
			String  colName 		= "";
			Integer colType 		= null;
			
			for (int i=1; i <= count; i++) 
			{                                           
				colName = rsmd.getColumnName(i).trim();
				colType = rsmd.getColumnType(i);	
				columnType.put(colName.toUpperCase(), colType);
			} 			
			st.close();
			rs.close();
			
		}
		catch(Exception e)
		{
			reportFailure("Exception in method commitTransaction() is : "+e);
			e.printStackTrace();
		}
		return  columnType;
	}
	
	
	private String buildSelectQuery(String tableName, HashMap<String,String> columnNames, ArrayList<String[]> conditions) throws Exception
	{
		String 		selectQuery 	= "SELECT ";
		
		boolean		multiCondtions	= true;
		
		HashMap<String,String> operators = new HashMap<String,String>();
		operators.put("EQUAL", "=");
		operators.put("NOT EQUAL", "<>");
		operators.put("GREATER THAN", ">");
		operators.put("GREATER THAN OR EQUAL", ">=");
		operators.put("LESS THAN", "<");
		operators.put("LESS THAN OR EQUAL", "<=");
		operators.put("LIKE", "LIKE");
		operators.put("IS NULL", "IS NULL");
		operators.put("IS NOT NULL", "IS NOT NULL");
		
		try
		{
			
            // Current Query : SELECT columnName1,columnName2,
			for ( String columnName : columnNames.keySet() )
			{
				selectQuery += columnName + ",";
			}
			
			// Current Query : SELECT columnName1,columnName2
			selectQuery = selectQuery.trim();
			if(selectQuery.endsWith(",")) 
				selectQuery = selectQuery.substring(0,selectQuery.length()-1);
			
			// Current Query : SELECT columnName1,columnName2 FROM tableName 
			selectQuery += " FROM "+tableName+" ";
			
			if(conditions.size() >0 )
			{	
				// Current Query : SELECT columnName1,columnName2 FROM tableName WHERE 
				selectQuery += " WHERE ";
				
	            for(int index =0; index < conditions.size(); index++)
	            {
	            	String[] condition = (String[]) conditions.get(index);	
	            	
	            	String		actualValue 	= "";
	        		String		expectedValue 	= "";
	        		String		operator		= "";
	        		String		connector		= "";
	        		
	            	if(condition.length != 4 ) 
	            	{
	            		reportFailure("Invalid where clause format");
	            		return "";
	            	}
	            	else
	            	{
	        			actualValue 	= condition [0].trim();	        			
	        			operator		= condition [1].trim();
	        			expectedValue 	= condition [2].trim();
	        			connector		= condition [3].trim();
	        			
	        			if(connector == null || connector.equals(""))
	        			{
	        				selectQuery += actualValue+" "+operators.get(operator)+" '"+expectedValue+"' " ;
	        			}
	        			else
	        			{
	        				selectQuery += " "+actualValue +" "+operators.get(operator)+" '"+expectedValue+"' "+connector+" " ;
	        			}
	        			
	        			//info(selectQuery);
	        			      		
	            	}
	            }
			}
							
		}
		catch(Exception e)
		{
			reportFailure("Unable to Build Where Clause Exception : "+e);
			e.printStackTrace();
		}
		
		selectQuery = selectQuery.trim();
		
		return selectQuery;	
		
	}
	

	/**
	 * This method is used to commit a transaction done throug  executeScript method		 		
	 */
	private void commitTransaction() throws Exception
	{
		try
		{
			Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if (connection.isClosed()){
				reportFailure("Unable to commit the transaction as connection is closed.");
			}else{
				connection.commit();
			}
		}
		catch(Exception e)
		{
			reportFailure("Exception in method commitTransaction() is : "+e);
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * This method is used to rollback a transaction done throug  executeScript method		 		
	 */
	private void rollbackTransaction() throws Exception
	{
		try
		{	
			Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if (connection.isClosed()){
				reportFailure("Unable to rollbak the transaction as connection is closed.");
			}else{
				connection.rollback();
			}
		}
		catch(Exception e)
		{
			reportFailure("Exception in method rollbackTransaction() is : "+e);
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * This method is used to enable DBMS Output 		 		
	 * @param bufferSize  Specify the required buffer size		   
	 */
	public void enableOutput( int bufferSize )
	{
		try
		{		
			Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if (connection.isClosed())
			{
				reportFailure("Unable to Enable Output as connection is closed.");									
			}
			else
			{	
				String opEnableString		= "BEGIN 	DBMS_OUTPUT.ENABLE(?); 			END;";
				CallableStatement enableCS  = 	connection.prepareCall( opEnableString);
				enableCS.setInt( 1, bufferSize );
				enableCS.executeUpdate();							
			}					
		}
		catch(Exception e)
		{
			System.out.println("Exception in method enableOutput() is : "+e);
			e.printStackTrace();
		}
			
	}
	
	/**
	 * This method is used to display  DBMS Output 		 		 	  
	 */
	public void showOutput() throws Exception
	{	
			//System.out.println("************** Start: Show Ouput **********************");
			int 		readStatus 	=	0;
			String dbmsOut 		=	"";
			try
			{
				Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");	
				//if (con.isClosed()) throw new PLSQLEngineException("PLSQLEngine : Connection Closed  Exception");
				if (connection.isClosed())
				{
					reportFailure("Unable to Show Output as connection is closed.");									
				}
				
				CallableStatement cs 	= connection.prepareCall("BEGIN 	DBMS_OUTPUT.GET_LINE(?,?); 	END;");	// Prepared Call
				cs.registerOutParameter(1, java.sql.Types.VARCHAR );
				cs.registerOutParameter(2, java.sql.Types.NUMERIC );// Registering Output Parameters
					
				String outString= "";
				do 
				{
					cs.execute();
					outString       =	    cs.getString(1);							
					readStatus 	= 		cs.getInt(2);		
					if(outString != null ) dbmsOut		+= outString+"\n";
				
				}while (readStatus == 0);
				
				System.out.println(dbmsOut);

			}
			catch(Exception e)
			{
					System.out.println("Exception : "+e);
					e.printStackTrace();
			}
			
			//System.out.println("************** End: Ouput **********************");


	}
		
	/**
	 * This method is used to disable and close DBMS Output  		 		 	  
	 */
	public void disableOutput()
	{
		try
		{
			Connection connection = (Connection)getVariables().getAsObject("CONNECTION_OBJ");
			
			if (connection.isClosed()){
				reportFailure("Unable to Disable Output as connection is closed.");	
			}
			
			String opDisableString	= "BEGIN 	DBMS_OUTPUT.DISABLE; 				END;";
			CallableStatement disableCS 	= 	connection.prepareCall( opDisableString );
			disableCS.executeUpdate();
			
			/*if(disableCS.isClosed()) throw new PLSQLEngineException(" Unable to Show Output Exception ");		
			else disableCS.executeUpdate();
			
			if(showCS.isClosed()) throw new PLSQLEngineException(" Unable to Show Output Exception ");		
			else showCS.close();					
		
			if(disableCS.isClosed()) throw new PLSQLEngineException(" Unable to Show Output Exception ");		
			else disableCS.close();	
		
			if(enableCS.isClosed()) throw new PLSQLEngineException(" Unable to Show Output Exception ");		
			else enableCS.close();*/	
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in method disableOutput() is : "+e);
			e.printStackTrace();
		}		    	
	}
	
	private String getScriptDataPath()throws Exception
	{	
		        
        //Data Folder Path (D:\Office\OPEN SCRIPT\TestScripts\OI_PurchaseOrder\data\)
        String dataFolderPath 			= "";
        
        //Data Folder Path (D:\Office\OPEN SCRIPT\TestScripts\OI_PurchaseOrder\Attachments\)
        String attachmentsFolderPath 	= "";
        
        // Variales Folder Path, which is available in the FlowName > Data folder
        String variablesFilePath = "";

        // Script Name
        String scriptName = "";
        
        String excelFilePath = "";
        
		// Get Current Script folder
        //scriptFolder :D:\Office\OPEN SCRIPT\TestScripts\FlowName\ScriptName\
        String scriptFolder = getVariables().get("CURR_SCRIPT_FOLDER");
        //info("scriptFolder :"+scriptFolder);
        

        if(scriptFolder==null ||scriptFolder.isEmpty()){
              javax.swing.JOptionPane.showMessageDialog(null, "Variable CURR_SCRIPT_FOLDER is not set propery please refer with MASTERDRIVE");
        }

        // File Object
        File f = new File(scriptFolder);
        
		
        if(f.exists()){
        	
        	scriptName				= f.getName();	
            dataFolderPath 			= f.getParentFile()+"\\data\\";
    		attachmentsFolderPath 	= f.getParentFile()+"\\Attachments\\";
    		variablesFilePath 		= f.getParentFile()+"\\data\\variables.properties";
        	
    		//info("scriptName:"+scriptName);
    		//info("dataFolderPath:"+dataFolderPath);
    		//info("attachmentsFolderPath:"+attachmentsFolderPath);
    		//info("variablesFilePath:"+variablesFilePath);
    		
    		String excelAttachmentsFilePath = attachmentsFolderPath+scriptName+".xlsx";
    		String excelDataFilePath 		= dataFolderPath+scriptName+".xlsx";
    		//info("excelAttachmentsFilePath:"+excelAttachmentsFilePath);
    		
    		// File Object
            File excelAttachmentFile = new File(excelAttachmentsFilePath);
            File excelDataFile = new File(excelDataFilePath);
            
            if(excelAttachmentFile.exists()){
            	excelFilePath = excelAttachmentsFilePath;
            	info("Excel Available in Attahments Folder:"+excelFilePath);
            	
            }else if(excelDataFile.exists()){
            	excelFilePath = excelDataFilePath;
            	info("Excel Available in data Folder:"+excelFilePath);
            }
    		
           
        }else{
              return "";
        }
        
		return excelFilePath;		
	}
	
	
	/**
	 * This method is used to select some values from a database table and assign then to openscript variables		 		
	 */	
	private String getSequence(String sequence) throws Exception
	{
		String  rsData		  = "";
		
		try
		{	
			//Connect DB
			connectDB();
			
			//Execute Select Query

            String 		selectQuery   	= "SELECT "+sequence+" AS SEQUENCE_NO FROM DUAL";						
			//info("**selectQuery**:"+selectQuery);
            
            Connection 	connection 		= (Connection)getVariables().getAsObject("CONNECTION_OBJ"); //Get Connection
			Statement 	statement		= connection.createStatement();			
			
			//Execute Query
			ResultSet 	rs 				= statement.executeQuery(selectQuery);
			
			//info("Select Query Executed : "+selectQuery);
			
	        if (rs.next()) 
	        {
	        	rsData = rs.getBigDecimal("SEQUENCE_NO").toString();	                    		
        		
	        	//System.out.println("Sequence Value :"+rsData);
	        }
/*	        else
	        {
	        	reportFailure("No Records Retrieved ");
	        	return;
	        }*/
	        
	        
		}
		catch(Exception e)
		{
			reportFailure("Unable to Execute Select Statement "+e+"    Method: selectQuery");
		}

		return rsData;
	}
	
	/**
	 * User can use this method within the script. This method creates the input box.<br>
	 * Returns the string keyed by user and if nothing is keyed in, it returns empty string.
	 *    
	 */
	private String oracle_input_dialog(String msg) throws Exception
	{
		UIManager.setLookAndFeel(
	            UIManager.getCrossPlatformLookAndFeelClassName());

		String str = javax.swing.JOptionPane.showInputDialog(null, msg+":", null, 1);
		if(str!=null)
			return str;
		else
			return "";
	}
	
	private boolean compareStrings(String expectedString, String actualString) {

		boolean stringMatched = false;

		Pattern pattern = Pattern.compile(expectedString);
		Matcher matcher = pattern.matcher(actualString);

		//System.out.println("expectedString :" + expectedString);
		//System.out.println("actualString :" + actualString);
		if (matcher.matches()) {
			stringMatched = true;
		}

		return stringMatched;
	}
	
	public void createPLSQLAnonymousBlock(String block) throws Exception
	{
		try{
			
			String scriptName = "";
			String anonymousBlockName = "";
			
			block = block.replaceAll(";", ";\n");
			
			// Get Current Script folder
	        String scriptFolder	= getVariables().get("CURR_SCRIPT_FOLDER");
	        
	        File scriptFile = new File(scriptFolder);
	        
	        if(scriptFile.exists()){
	        	scriptName = scriptFile.getName();
	        	//info("scriptName :"+scriptFile.getName());
	        }
			
	        
	        anonymousBlockName = scriptFile.getParent()+"\\data\\"+scriptName+".sql";
	        info("file Path:"+anonymousBlockName);
	        
	      //this.sop("variablesFilePath :"+variablesFilePath);
	        File blockFile = new File(anonymousBlockName);
	        
	        
	        if(blockFile.exists())
	        {
	        	blockFile.delete();
	        }
	        	blockFile.createNewFile();
	        
	        
	        info("File Name :"+blockFile.getName());
	        
	        FileWriter fileWritter = new FileWriter(blockFile);
	        
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        
	        bufferWritter.write(""+block);
	        
	        bufferWritter.close();
	        
	        fileWritter.close();
	    
	       // System.out.println("Done");
			
		}catch(Exception e){
			
			reportFailure("Exception During Creation of Sql file for Anonymous block is : "+e);
			
			e.printStackTrace();
		}
	}
	
}
