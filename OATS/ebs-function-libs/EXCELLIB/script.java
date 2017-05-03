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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class script extends IteratingVUserScript {
	@ScriptService oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService oracle.oats.scripting.modules.browser.api.BrowserService browser;
	@ScriptService oracle.oats.scripting.modules.functionalTest.api.FunctionalTestService ft;
	@ScriptService oracle.oats.scripting.modules.webdom.api.WebDomService web;
	@ScriptService oracle.oats.scripting.modules.applet.api.AppletService applet;
	@ScriptService oracle.oats.scripting.modules.formsFT.api.FormsService forms;
	
	protected 	XSSFWorkbook 	wb = null;
	int columnRow = 1;
	String filePath = "";
	FileInputStream fis = null;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-mmm-yyyy");
	
	public void initialize() throws Exception {
	}

	/**
	 * Add code to be executed each iteration for this virtual user.
	 */
	public void run() throws Exception {

	}

	public void finish() throws Exception {
	}
	
	public void getExcel(String filePath) throws Exception
	{
		process_sheet(filePath,this.columnRow);
	}
	
	public void getExcel(String filePath, int columnRow) throws Exception
	{
		process_sheet(filePath, columnRow);
	}
	
	 /*
     * Opens the Sheet , 
     * Get Total Number of Sheets, 
     * Creates List sheetcolumnsarr Each element contains  list of columns 
     */
    private void process_sheet(String filePath, int columnRow)
    {
    	try
    	{    	

    	  FileInputStream fis   =  	new FileInputStream(filePath); 
          wb 					= 	new XSSFWorkbook(fis);
          
          this.columnRow 		= columnRow-1;
          this.filePath 		= filePath;
		  //style 				= 	wb.createCellStyle();

    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Constructing Workbook");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    }
	
    
    public void closeExcel()
    {
    	try
    	{   
    		wb 					= null;
    		this.filePath 		= "";
    		
    		

    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Constructing Workbook");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    }
    
    /*
     * Returns  name of sheets in the work book
     */
    public ArrayList<String> getSheetNames() throws Exception
    {
    	ArrayList<String> sheets = new ArrayList<String>();
    	
    	try
    	{	
    		//Sheet Count
    		int sheet_count =  wb.getNumberOfSheets();
    		
    		//Sheet Names
    		for(int i=0;i< wb.getNumberOfSheets();i++)
    	    {
    			sheets.add(wb.getSheetAt(i).getSheetName());
    	    }
    		
    		info("Excel Sheets :"+sheets.toString());
    		
    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Getting Sheet Names");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    	
		return sheets;
    }
    
    
    

    /*
     * returns the number of column in a particular sheet
     */
    public ArrayList<String> getColumns(String sheetname)
    { 
    	return getColumns(sheetname,this.columnRow);
    }
    
    
    /*
     * returns the number of column in a particular sheet
     */
    public ArrayList<String> getColumns(String sheetname, int columnRow)
    { 
    	ArrayList<String> columns = new ArrayList<String>();

    	try
    	{
    		XSSFRow row  = 	wb.getSheet(sheetname).getRow(columnRow);
    		
    		int column_count =  row.getPhysicalNumberOfCells(); 
    		
    		for(int c = 0;c<column_count;c++)
	           {
    				XSSFCell cell  				= 	row.getCell(c);
	   		    	
    				XSSFRichTextString   value 	=   cell.getRichStringCellValue(); 
	   		    	
	   		    	String colValue = value.getString();
	   		    	
	   		    	String[] colAttributes  = colValue.split(","); //Column Attributes means: Column Name, Foreign Key, SequenceField
	   		    	
	   		    	columns.add(colAttributes[0]);	               
	           }
    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Accessing Sheet");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    	return columns;
        
    }
    
    
    
    /*
     * returns the number of column in a particular sheet
     */
    public int getColumnCount(String sheetname)
    { 
    	return getColumnCount(sheetname,this.columnRow);
    }
    
    
    /*
     * returns the number of column in a particular sheet
     */
    public int getColumnCount(String sheetname, int columnRow)
    { 
    	int column_count =0;
    	try
    	{
    		XSSFRow row  = 	wb.getSheet(sheetname).getRow(columnRow);
    		column_count =  row.getPhysicalNumberOfCells();            	
    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Accessing Sheet");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    	return column_count;
        
    }
    
    
    /*
     * returns the number of rows in a particular sheet
     */
    public int getRowCount(String sheetname) 
	{
    	int row_count =0;
    	try
    	{
    		return  	wb.getSheet(sheetname).getPhysicalNumberOfRows();
    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Accessing Sheet");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    	return row_count;
    }
    
    /*
     *Used Internally to get the column index of a Column name in a sheet
     */
    private int getColumnindex(String sheetname,String Column) throws Exception
    {
    	ArrayList<String> columns = new ArrayList<String>();
    	
    	columns = getColumns(sheetname);
    	
    	int columnIndex = columns.indexOf(Column);
    	
    	//info("Column :"+Column+"	columnIndex :"+columnIndex);
    	
        return columnIndex;
    }
    
    /*
     * Override of get value , Returns the cell data by specifying the row index and colum name of a particular sheet
     */
    public String getvalue(String sheetname,int row, String columnname) throws Exception
    {    
        return getvalue(sheetname,row, getColumnindex(sheetname,columnname) );
    }
    
    /*
     * Returns the Value of cell Pass Sheet Name , Row Index and Column Index
     */
    public String getvalue(String sheetname,int rw,int cl)
    {
    	
    	String vl = "";
    	
	    try
	    {   
	    	//info("rowNumber :"+rw+"	colNumber :"+cl);
	    	
	    	XSSFSheet sheet = wb.getSheet(sheetname);
	    	
	    	XSSFRow  row   = 	sheet.getRow(rw);
	    	
	    	XSSFCell cell  = 	row.getCell(cl);
		    
		    if( cell != null ) 
		    	return getCellData(cell);

	    }
    	catch (Exception e)
    	{
    		 System.out.println("\nException Occurred While Accessing Cell Data");
    		 System.out.println("\nException : "+e );
    		 e.printStackTrace();
    	}
    	
		return vl;
    }
    
    /*
     * Overridden Function of : public void setvalue(String sheetname,int rw,int cl ,String data)
     * Write a new Value or Override the existing value of a  particular cell in a particular sheet
     * Specify Sheet Name ,Row Index and Column Name
     */
    
    public void setvalue(String sheetname,int row, String columnname,String data) throws Exception
    {    
    	boolean beforeTextAsInt = columnname.matches("\\d");
    	
    	if(beforeTextAsInt){
    		setvalue(sheetname,row, Integer.parseInt(columnname),data);
    	}else{
    	      setvalue(sheetname,row, getColumnindex(sheetname,columnname),data);
    	}
    }
    
    /* Write a new Value or Override the existing value of a  particular cell in a particular sheet
     * Specify Sheet Name ,Row Index and Column Index
     */
    public void setvalue(String sheetname,int rw,int cl ,String data)
    {
    	

	    try
	    {     
	    	/*info("Sheet Name :"+sheetname);
	    	info("Row Number/Name :"+rw);
	    	info("Column Number :"+cl);
	    	info("Data :"+data);*/
	    	
	    	XSSFSheet sheet = wb.getSheet(sheetname);
	    	
	    	XSSFRow  row   = 	sheet.getRow(rw);

	    	if(row == null){
	    		row = sheet.createRow(rw);
	    	}
	    	
	    	XSSFCell cell  = 	row.createCell(cl);
		    
		    cell.setCellValue(data);
		    
			FileOutputStream fos	= new FileOutputStream(filePath);
			
			wb.write(fos);
			
			fos.close();
	    }
    	catch (Exception e)
    	{
    		 System.out.println("\nException Occurred While Writing Cell Data");
    		 System.out.println("\nException : "+e );
    		 e.printStackTrace();
    	}

    }
    
    
    /*
     * Returns a List of Records, Implementing Parent Child Relationship here
     */
    public HashMap<String,String> getRowData(String sheetname,String keycolumn,String keyvalue)
    {
    	HashMap<String,String> rowData = new HashMap<String,String>();
    	
    	try
    	{   
    		XSSFSheet sheet = wb.getSheet(sheetname);
    		
    		//Row Count
    		int rowCount = getRowCount(sheetname);
    		
    		//Column Count
    		int colCount = getColumnCount(sheetname);
    		
    		ArrayList<String> columns = getColumns(sheetname);
    		
    		info("rowCount :"+rowCount+"	colCount :"+colCount+"	columnRow :"+columnRow);
    		
    	    for(int rowIndex=columnRow+1; rowIndex < rowCount; rowIndex++){
    	    	
    	    	String value = getvalue(sheetname, rowIndex, keycolumn);
    	    	
    	    	//info("value :"+value);
    	    	
    	    	if(value.equals(keyvalue))
    	        {
    	    		for(int colIndex=2; colIndex < colCount; colIndex++){
        	    		
    	    			String cellData = getvalue(sheetname, rowIndex, colIndex);
    	    			
    	    			if(!"".equalsIgnoreCase(cellData)){
    	    				rowData.put(columns.get(colIndex), cellData);
    	    			}
    	    			
        	    	}
    	        }
    	    }
    		    		    		   
    	}
    	catch (Exception e)
    	{
    		 System.out.println("Exception Occurred While Getting Data from Sheet");
    		 System.out.println("Exception : "+e );
    		 e.printStackTrace();
    	}
    	
		return rowData;
    }
    
  
    
    /*
     * Implementation for getting data from cell
     */
    private String getCellData(XSSFCell cell)
    {
    	String data ="";
    	
    	cell.setCellType(XSSFCell.CELL_TYPE_STRING);
    	
    	try
    	{

	    	switch (cell.getCellType()) 
	    	{
	    		case XSSFCell.CELL_TYPE_NUMERIC :
	    	
	    			if(DateUtil.isCellDateFormatted(cell)) 
	    				return sdf.format( cell.getDateCellValue()).toString();	                                       	    				   			
	    			else 
	    				return new Double(cell.getNumericCellValue()).toString();	   
	    			
	    		case XSSFCell.CELL_TYPE_STRING  :	    
	    			
	    				return cell.getRichStringCellValue().getString();	
	    				
	    		case XSSFCell.CELL_TYPE_BOOLEAN :	  
	    			
	    			   return new Boolean(cell.getBooleanCellValue()).toString();	

	    		case XSSFCell.CELL_TYPE_BLANK   :	 
	    			
						System.out.print("Blank : "+	cell.getErrorCellValue());	
						return data;
	    		case XSSFCell.CELL_TYPE_ERROR   :
						System.out.print("Error");	
    					return data;
	    		case XSSFCell.CELL_TYPE_FORMULA :	 
						System.out.print("Formula");	
						return data;
	    		default:
	    				return data;

	    	}  	
    	}
    	catch(Exception e)
    	{
    		System.out.println("\nException Occurred While Retreiving data based on Cell Format Type");
   		 	System.out.println("\nException : "+e );
   		 	e.printStackTrace();
    	}
    	
    	return data;
    }
}
