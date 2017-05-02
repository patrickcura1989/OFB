import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import oracle.oats.scripting.modules.basic.api.*;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.webService.api.*;
import oracle.oats.scripting.modules.webService.api.WSService.*;
import oracle.oats.scripting.modules.utilities.api.*;
import oracle.oats.scripting.modules.utilities.api.sql.*;
import oracle.oats.scripting.modules.utilities.api.xml.*;
import oracle.oats.scripting.modules.utilities.api.file.*;
import oracle.oats.scripting.modules.http.api.*;
import oracle.oats.scripting.modules.http.api.HTTPService.*;

//@SuppressWarnings("unused")
public class script extends IteratingVUserScript {
	@ScriptService
	oracle.oats.scripting.modules.utilities.api.UtilitiesService utilities;
	@ScriptService
	oracle.oats.scripting.modules.http.api.HTTPService http;
	@ScriptService
	oracle.oats.scripting.modules.webService.api.WSService ws;

	Connection ConnDB = null;
	Statement StmtCheck = null;
	ResultSet Rsltset = null;

	public void runBatchFile(String fileName) throws Exception {
		// String line;
		Process p;

		try {
			Runtime r = Runtime.getRuntime();
			p = r.exec("cmd /c start " + fileName);
			p.waitFor();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void killBatchFile(String processName) throws Exception {
		Process p;
		try {

			p = Runtime.getRuntime().exec("taskkill /F /IM " + processName);
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCurrentTimeInMilliSeconds() throws Exception {
		long currentTime = System.currentTimeMillis();
		return String.valueOf(currentTime);
	}

	private int rand(int lo, int hi) {
		Random rn = new Random();

		int n = hi - lo + 1;
		int i = rn.nextInt() % n;
		if (i < 0)
			i = -i;
		return lo + i;
	}

	public String randomStringWithGivenRange(int lo, int hi) {
		int n = rand(lo, hi);
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte) rand('a', 'z');
		return new String(b, 0);
	}

	public String randomString() {
		return randomStringWithGivenRange(5, 25);
	}

	public String compare2Strings(String String_A, String String_B)
	throws Exception {
		if (!String_A.equals(String_B)) {
			return String.valueOf(false);
		} else {
			return String.valueOf(true);
		}
	}

	public String randomNumberUsingDateTime() {
		String RandomNumber = "";

		TimeZone tz = TimeZone.getTimeZone("CST");
		Calendar mbCal = new GregorianCalendar(tz);
		mbCal.setTimeInMillis(new Date().getTime());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));

		Date date = cal.getTime();
		SimpleDateFormat newdate = new SimpleDateFormat("MMddHHmmss");
		RandomNumber = newdate.format(date);
		System.out.println(RandomNumber);

		return RandomNumber;
	}

	public String getCurrentDateTimeWithGivenDateFormat(String dFormat) {
		DateFormat dateFormat = new SimpleDateFormat(dFormat);
		Calendar cal = Calendar.getInstance();
		return (dateFormat.format(cal.getTime()));
	}

	public String getDateDiffInSecsWithGivenDateFormat(String dateStart, String dateStop,
			String dFormat) throws AbstractScriptException {

		SimpleDateFormat format = new SimpleDateFormat(dFormat);

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = (Date) format.parse(dateStart);
			d2 = (Date) format.parse(dateStop);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000;

		return String.valueOf(diffSeconds);
	}

	public String getAdjustedTimeWithGivenDateTime(String dateTime, String offset, String dFormat) {
		System.out.println("in returnDateTime");
		String year1 = dateTime.substring(0, 4);
		int year = Integer.parseInt(year1);
		String month1 = dateTime.substring(5, 7);
		int month = Integer.parseInt(month1);
		month = month - 1;
		String date1 = dateTime.substring(8, 10);
		int date = Integer.parseInt(date1);

		String hours1 = dateTime.substring(11, 13);
		int hours = Integer.parseInt(hours1);
		String minutes1 = dateTime.substring(14, 16);
		int minutes = Integer.parseInt(minutes1);
		String seconds1 = dateTime.substring(17, 19);
		int secs = Integer.parseInt(seconds1);

		Calendar datetime = Calendar.getInstance();
		datetime.set(year, month, date, hours, minutes, secs);

		if (offset.contains(":")) {
			if ((offset.split(":")).length == 3) {

				int offsethours = Integer.parseInt(offset.split(":")[0]);
				datetime.add(Calendar.HOUR, offsethours);
				int offsetMins = Integer.parseInt(offset.split(":")[1]);
				datetime.add(Calendar.MINUTE, offsetMins);
				int offsetsecs = Integer.parseInt(offset.split(":")[2]);
				datetime.add(Calendar.SECOND, offsetsecs);
			} else {
				int offsethours = Integer.parseInt(offset.split(":")[0]);
				datetime.add(Calendar.HOUR, offsethours);
				int offsetMins = Integer.parseInt(offset.split(":")[1]);
				datetime.add(Calendar.MINUTE, offsetMins);
			}

		} else {
			int offset1 = Integer.parseInt(offset);

			datetime.add(Calendar.HOUR, offset1);
		}

		SimpleDateFormat dateformatter = new SimpleDateFormat(dFormat);

		String dateFinal = dateformatter.format(datetime.getTime());

		String tempstr = dateFinal.substring(0, 11);

		String tempstr1 = dateFinal.substring(13, dateFinal.length());

		String tempstr2 = dateFinal.substring(11, 13);

		int tempint1 = Integer.parseInt(tempstr2);

		String datefinal1;

		if (tempint1 == 24)

		{
			datefinal1 = tempstr + "00" + tempstr1;

		} else
			datefinal1 = dateFinal;

		return datefinal1;

	}

	/**
	 * The function returns the Date and Time after adding the specified offset
	 * to current date and current time in the specified date time format. This
	 * is a case where the user has inputs to the function as date and time
	 * separately rather than datetime as a single variable as is the case with
	 * previous function
	 * 
	 * @param cuDate
	 *            ,cuTime,offset,dFormat
	 * @return String Author: 
	 */
	public String getAdjustedTimeWithCurrentDateTime(String offset, String dFormat) throws Exception

	{
		String cuDate=serverDate();
		String cuTime=serverTime();

		String year1 = cuDate.substring(0, 4);
		int year = Integer.parseInt(year1);
		String month1 = cuDate.substring(5, 7);
		int month = Integer.parseInt(month1);
		month = month - 1;
		String date1 = cuDate.substring(8, 10);
		int date = Integer.parseInt(date1);

		String hours1 = cuTime.substring(0, 2);
		int hours = Integer.parseInt(hours1);
		String minutes1 = cuTime.substring(3, 5);
		int minutes = Integer.parseInt(minutes1);
		// String seconds1 = cuTime.substring(6, 8);

		Calendar datetime = Calendar.getInstance();
		datetime.set(year, month, date, hours, minutes);

		if (offset.contains(":")) {

			int offsethours = Integer.parseInt(offset.split(":")[0]);
			datetime.add(Calendar.HOUR, offsethours);
			int offsetMins = Integer.parseInt(offset.split(":")[1]);
			datetime.add(Calendar.MINUTE, offsetMins);

		} else {
			int offset1 = Integer.parseInt(offset);

			datetime.add(Calendar.HOUR, offset1);
		}

		SimpleDateFormat dateformatter = new SimpleDateFormat(dFormat);

		String dateFinal = dateformatter.format(datetime.getTime());

		String tempstr = dateFinal.substring(0, 11);

		String tempstr1 = dateFinal.substring(13, dateFinal.length());

		String tempstr2 = dateFinal.substring(11, 13);

		int tempint1 = Integer.parseInt(tempstr2);

		String datefinal1;

		if (tempint1 == 24)

		{
			datefinal1 = tempstr + "00" + tempstr1;

		} else
			datefinal1 = dateFinal;

		return datefinal1;
	}
	public String getAdjustedTimeWithGivenDateAndTime(String cuDate,String cuTime,String offset, String dFormat) throws Exception

	{
		String year1 = cuDate.substring(0, 4);
		int year = Integer.parseInt(year1);
		String month1 = cuDate.substring(5, 7);
		int month = Integer.parseInt(month1);
		month = month - 1;
		String date1 = cuDate.substring(8, 10);
		int date = Integer.parseInt(date1);

		String hours1 = cuTime.substring(0, 2);
		int hours = Integer.parseInt(hours1);
		String minutes1 = cuTime.substring(3, 5);
		int minutes = Integer.parseInt(minutes1);
		// String seconds1 = cuTime.substring(6, 8);

		Calendar datetime = Calendar.getInstance();
		datetime.set(year, month, date, hours, minutes);

		if (offset.contains(":")) {

			int offsethours = Integer.parseInt(offset.split(":")[0]);
			datetime.add(Calendar.HOUR, offsethours);
			int offsetMins = Integer.parseInt(offset.split(":")[1]);
			datetime.add(Calendar.MINUTE, offsetMins);

		} else {
			int offset1 = Integer.parseInt(offset);

			datetime.add(Calendar.HOUR, offset1);
		}

		SimpleDateFormat dateformatter = new SimpleDateFormat(dFormat);

		String dateFinal = dateformatter.format(datetime.getTime());

		String tempstr = dateFinal.substring(0, 11);

		String tempstr1 = dateFinal.substring(13, dateFinal.length());

		String tempstr2 = dateFinal.substring(11, 13);

		int tempint1 = Integer.parseInt(tempstr2);

		String datefinal1;

		if (tempint1 == 24)

		{
			datefinal1 = tempstr + "00" + tempstr1;

		} else
			datefinal1 = dateFinal;

		return datefinal1;
	}

	/**
	 * The function adds number of days to the current date and returns it with
	 * the specified format.
	 * 
	 * @param days
	 *            ,dFormat
	 * @return String Author:  
	 */
	public String addDaysToCurrentDateWithGivenFormat(String noOfDays, String dFormat) throws Exception {
		String upDate = "";

		try {
			int gmonth, gday, gyear;
			int days =Integer.parseInt(noOfDays);

			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gmonth = gregorianCalendar.get(GregorianCalendar.MONTH);
			gday = gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH);
			gyear = gregorianCalendar.get(GregorianCalendar.YEAR);
			GregorianCalendar currentDate = new GregorianCalendar(gyear,
					gmonth, gday);
			SimpleDateFormat sdf = new SimpleDateFormat(dFormat);
			currentDate.setLenient(false);
			currentDate.add(Calendar.DAY_OF_YEAR, days);
			upDate = sdf.format(currentDate.getTime());

		} catch (Exception e) {
			fail("Exception in addDaysToCurrentDateWithGivenFormat : " + e.getMessage());
		}
		return upDate;
	}

	public String serverDate() throws AbstractScriptException {

		String finalServerDate = "";
		String strDate = "";

		try {

			String Query = "select TO_CHAR(sysdate,'DD-MM-YYYY HH:MI:SS') FROM DUAL";

			ResultSet lObjResultSet = executeSQLQry(Query);

			if (lObjResultSet != null) {
				while (lObjResultSet.next()) {

					strDate = lObjResultSet.getString(1);
					break;

				}

			}
			closeConnection();
			info("Date time at fnc_serverDate : " + strDate);

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(strDate.substring(6, 10)));
			cal.set(Calendar.MONTH,
					(Integer.parseInt(strDate.substring(3, 5))) - 1);
			cal.set(Calendar.DAY_OF_MONTH,
					Integer.parseInt(strDate.substring(0, 2)));
			cal.set(Calendar.HOUR_OF_DAY,
					Integer.parseInt(strDate.substring(11, 13)));
			cal.set(Calendar.MINUTE,
					Integer.parseInt(strDate.substring(14, 16)));
			cal.set(Calendar.SECOND,
					Integer.parseInt(strDate.substring(17, 19)));

			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			cal1.set(Calendar.MONTH, 2);
			cal1.set(Calendar.WEEK_OF_MONTH, 2);
			cal1.set(Calendar.DAY_OF_WEEK, 1);

			cal2.set(Calendar.MONTH, 10);
			cal2.set(Calendar.WEEK_OF_MONTH, 1);
			cal2.set(Calendar.DAY_OF_WEEK, 1);

			Date date = cal.getTime();

			SimpleDateFormat newdate = new SimpleDateFormat("yyyy-MM-dd");

			finalServerDate = newdate.format(date.getTime()).toString();
			return finalServerDate;

		} catch (Exception e) {
			fail(e.getMessage());

			return finalServerDate;
		}
	}


	/**
	 * Executes SQL and returns record set
	 * 
	 * @param Query
	 */
	public ResultSet executeSQLQry(String Query) throws Exception {

		ConnDB = null;
		StmtCheck = null;
		Rsltset = null;

		try {

			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");

			} catch (Exception e) {
				fail("Exception in ExecuteSQLQry funciton " + e.getMessage());
				return null;
			}


			String ConnectionString = getVariables().get("gStrApplicationDBConnectionString");
			String DBUsername = getVariables().get("gStrApplicationDBUsername");
			String DBPassword = getVariables().get("gStrApplicationDBPassword");

			ConnDB = DriverManager.getConnection(ConnectionString, DBUsername,
					DBPassword);

			StmtCheck = ConnDB.createStatement();

			Rsltset = StmtCheck.executeQuery(Query);

			/*StringBuilder builder = new StringBuilder();
			int columnCount = Rsltset.getMetaData().getColumnCount();

			while (Rsltset.next()) {
			    for (int i = 0; i < columnCount; i++) {
			        builder.append(Rsltset.getString(i + 1));
			        if ( i!= columnCount-1)
			        	builder.append(",");		  
			    }
			    builder.append("\n");
			}			

			String resultSetAsString = builder.toString();	
			int i = resultSetAsString.lastIndexOf("\n");
			String updatedString = resultSetAsString.substring(i);*/
			return Rsltset;

		} catch (Exception e) {
			fail("Exception in ExecuteSQLQry function " + e.getMessage());
			return null;
		} finally {

		}

	}

	/**
	 * Executes SQL and returns record set
	 * 
	 * @param Query
	 */
	public ResultSet executeSQLQryWithGivenDBDetails(String Query,
			String ConnectionString ,String DBUsername,String DBPassword) throws Exception {

		ConnDB = null;
		StmtCheck = null;
		Rsltset = null;

		try {

			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");

			} catch (Exception e) {
				fail("Exception in ExecuteSQLQry funciton " + e.getMessage());
				return null;
			}


			ConnDB = DriverManager.getConnection(ConnectionString, DBUsername,
					DBPassword);

			StmtCheck = ConnDB.createStatement();

			Rsltset = StmtCheck.executeQuery(Query);


			/*StringBuilder builder = new StringBuilder();
			int columnCount = Rsltset.getMetaData().getColumnCount();

			while (Rsltset.next()) {
			    for (int i = 0; i < columnCount; i++) {
			        builder.append(Rsltset.getString(i + 1));
			        if ( i!= columnCount-1)
			        	builder.append(",");		  
			    }
			    builder.append("\r\n");
			}
			String resultSetAsString = builder.toString();	*/

			return Rsltset;

		} catch (Exception e) {
			fail("Exception in ExecuteSQLQry function " + e.getMessage());
			return null;
		} finally {

		}

	}

	public String serverTime() throws AbstractScriptException {

		String finalServerTime = "";

		String strDate = "";

		try {

			String Query = "select TO_CHAR(sysdate,'DD-MM-YYYY HH24:MI:SS') FROM DUAL";

			ResultSet lObjResultSet = executeSQLQry(Query);

			if (lObjResultSet != null) {
				while (lObjResultSet.next()) {

					strDate = lObjResultSet.getString(1);
					break;

				}

			}
			closeConnection();
			info("Date time at fnc_serverTime : " + strDate);

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(strDate.substring(6, 10)));
			cal.set(Calendar.MONTH,
					(Integer.parseInt(strDate.substring(3, 5))) - 1);
			cal.set(Calendar.DAY_OF_MONTH,
					Integer.parseInt(strDate.substring(0, 2)));
			cal.set(Calendar.HOUR_OF_DAY,
					Integer.parseInt(strDate.substring(11, 13)));
			cal.set(Calendar.MINUTE,
					Integer.parseInt(strDate.substring(14, 16)));
			cal.set(Calendar.SECOND,
					Integer.parseInt(strDate.substring(17, 19)));

			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			cal1.set(Calendar.MONTH, 2);
			cal1.set(Calendar.WEEK_OF_MONTH, 2);
			cal1.set(Calendar.DAY_OF_WEEK, 1);

			cal2.set(Calendar.MONTH, 10);
			cal2.set(Calendar.WEEK_OF_MONTH, 1);
			cal2.set(Calendar.DAY_OF_WEEK, 1);

			Date date = cal.getTime();

			SimpleDateFormat newdate = new SimpleDateFormat("HH.mm.ss");
			finalServerTime = newdate.format(date).toString();

			return finalServerTime;

		} catch (Exception e) {
			fail(e.getMessage());
			return finalServerTime;
		}
	}


	public void waitForTime(String strWaitTimeInMinutes)
	throws AbstractScriptException {

		try {
			float waitTimeInMinutes= Float.valueOf(strWaitTimeInMinutes);

			long startTime = System.currentTimeMillis();
			while (getWaitConditionState(startTime, waitTimeInMinutes)) {
			}

		} catch (Exception e) {
			fail("Exception in waitForTime : " + e.getMessage());

		}

	}

	/**
	 * Verify if batch is running in the last x mins
	 * 
	 */
	public String verifyLastBatchRun(String Batch_CD, String strMaXTimeToCheck)
	throws Exception {

		try {
			float MaXTimeToCheck= Float.valueOf(strMaXTimeToCheck);
			String Query = "";
			String Query_stat = "";
			String result = "";
			String result_stat = "";
			long startTime = System.currentTimeMillis();

			while (getWaitConditionState(startTime, MaXTimeToCheck)) {

				info("CHECKING FOR THE BATCH");
				Query = "SELECT BATCH_JOB_ID FROM CI_BATCH_JOB WHERE batch_start_dttm>=SYSDATE-10/86400 AND BATCH_JOB_STAT_FLG='ST' AND batch_cd ='"
					+ Batch_CD + "'";

				ResultSet lObjResultSet = executeSQLQry(Query);

				if (lObjResultSet != null) {
					while (lObjResultSet.next()) {

						result = lObjResultSet.getString(1);
						info("batch id of" + Batch_CD + " is:" + result);

						while (getWaitConditionState(startTime, MaXTimeToCheck)) {
							Query_stat = "SELECT BATCH_JOB_STAT_FLG FROM CI_BATCH_JOB WHERE BATCH_JOB_ID='"
								+ result + "'";
							ResultSet lObjResultSet_stat = executeSQLQry(Query_stat);
							if (lObjResultSet_stat != null) {
								while (lObjResultSet_stat.next()) {
									result_stat = lObjResultSet_stat
									.getString(1);
									info("batch status of batch id" + result
											+ " is:" + result_stat);
									if (result_stat.equalsIgnoreCase("ED")) {
										closeConnection();
										return String.valueOf(true);
									}

								}
							}

						}
					}

				}
			}

			closeConnection();
			return String.valueOf(false);
		} catch (Exception e) {
			fail("Exception in verifyLastBatchRun : "
					+ e.getMessage().toString());
			closeConnection();
			return String.valueOf(false);
		}

	}

	// added for creation of capacity templates strt time and end time
	public String getCurrentOffsetTime(String cuDate, String cuTime,
			String offset,String timeFormat) throws Exception

			{

		String year1 = cuDate.substring(0, 4);
		int year = Integer.parseInt(year1);
		String month1 = cuDate.substring(5, 7);
		int month = Integer.parseInt(month1);
		month = month - 1;
		String date1 = cuDate.substring(8, 10);
		int date = Integer.parseInt(date1);

		String hours1 = cuTime.substring(0, 2);
		int hours = Integer.parseInt(hours1);
		String minutes1 = cuTime.substring(3, 5);
		int minutes = Integer.parseInt(minutes1);
		String seconds1 = cuTime.substring(6, 8);

		Calendar datetime = Calendar.getInstance();
		datetime.set(year, month, date, hours, minutes);

		if (offset.contains(":")) {

			int offsethours = Integer.parseInt(offset.split(":")[0]);
			if (offsethours != 0)
				datetime.add(Calendar.HOUR, offsethours);
			int offsetMins = Integer.parseInt(offset.split(":")[1]);
			if (offsetMins != 0)
				datetime.add(Calendar.MINUTE, offsetMins);

		} else {
			int offset1 = Integer.parseInt(offset);

			datetime.add(Calendar.HOUR, offset1);
		}

		info("updated datetime before formating " + datetime.toString());

		SimpleDateFormat dateformatter = new SimpleDateFormat(timeFormat);

		String dateFinal = dateformatter.format(datetime.getTime());

		String tempstr = dateFinal.substring(11, dateFinal.length());

		String tempstr1 = dateFinal.substring(13, dateFinal.length());

		String tempstr2 = dateFinal.substring(11, 13);

		int tempint1 = Integer.parseInt(tempstr2);

		String datefinal1;

		if (tempint1 == 24)

		{
			datefinal1 = "00" + tempstr1;

		} else
			datefinal1 = tempstr;

		return datefinal1;
			}
	public String addDaysToAGivenDate(String date, String noOfDays)
	throws Exception {

		try {

			// Start date
			int numOfDays=Integer.parseInt(noOfDays);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(date));
			c.add(Calendar.DATE, numOfDays); // number of days to add
			String upDate = sdf.format(c.getTime()); // dt is now the new date
			return upDate;

		} catch (Exception e) {
			fail("Exception in addDaysToaDate : " + e.getMessage().toString());
			return "";
		}

	}



	public String randomNumber() throws Exception {

		String randomNumber = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
			Date date = new Date();
			randomNumber = dateFormat.format(date);
			return randomNumber;
		} catch (Exception e) {
			fail(e.getMessage());
			return randomNumber;
		}
	}

	/**
	 * Gets wait condition state. This funciton should be called in while loop
	 * 
	 * @param StartTime
	 *            ,TimeInMinutes
	 * @return boolean Author:  
	 * 
	 */

	public void createFile(String FilePath) throws Exception {

		try {
			info("Creating file" + FilePath);
			File lObjFile = new File(FilePath);

			if (lObjFile.exists()) {
				lObjFile.delete();
			}

			lObjFile.createNewFile();

		} catch (Exception e) {
			info("Exception in CreateFile : " + e.getMessage());
		}

	}

	private boolean getWaitConditionState(long StartTime, float TimeInMinutes)
	throws Exception {

		try {

			if ((System.currentTimeMillis() - StartTime) < TimeInMinutes * 60 * 1000) {
				return true;
			}

			return false;

		} catch (Exception e) {
			fail("Exception in GetWaitConditionState funciton "
					+ e.getMessage());
			return false;
		}

	}

	public String compare2Files(String strFileName_A, String strFileName_B)
	throws Exception {
		String[] Lines = utilities.getFileService().readLines(strFileName_A);
		List<String> arrayListExpected = Arrays.asList(Lines);
		System.out.println(arrayListExpected);
		Lines = utilities.getFileService().readLines(strFileName_B);
		List<String> arrayListActual = Arrays.asList(Lines);
		System.out.println(arrayListActual);
		if (!arrayListActual.equals(arrayListExpected)) {
			return String.valueOf(false);
		} else {
			return String.valueOf(true);
		}
	}

	/**
	 * Function to copy files from source to destination
	 * 
	 * @param srcFile
	 *            ,destFile
	 * @returnType void Author:  
	 */

	public void copyFile(String srcFilePath, String destFilePath) throws IOException {

		File srcFile=new File(srcFilePath);
		File destFile=new File(destFilePath);
		InputStream oInStream = new FileInputStream(srcFile);
		OutputStream oOutStream = new FileOutputStream(destFile, true);

		destFile.delete();
		// Transfer bytes from in to out
		byte[] oBytes = new byte[1024];
		int nLength;
		BufferedInputStream oBuffInputStream = new BufferedInputStream(
				oInStream);
		while ((nLength = oBuffInputStream.read(oBytes)) > 0) {
			oOutStream.write(oBytes, 0, nLength);
		}
		oInStream.close();
		oOutStream.close();
	}

	/**
	 * Function to delete the files
	 * 
	 * @param file
	 * @returnType void Author:  
	 */
	public void deleteFile(String filePath) throws IOException {
		File file=new File(filePath);
		if (file.exists())
			file.delete();

	}

	/**
	 * Executes SQL query for update query
	 * 
	 * @param Query
	 *            ,dbDetails
	 * @return boolean Author:  
	 */
	public String executeSQLQryUpdate(String Query, String ConnectionString ,String DBUsername,String DBPassword)
	throws Exception {

		try {

			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");

			} catch (Exception e) {
				fail("Exception in ExecuteSQLQry funciton " + e.getMessage());
				return String.valueOf(false);				
			}
			Connection ConnDB = null;
			Statement StmtCheck = null;

			ConnDB = DriverManager.getConnection(ConnectionString, DBUsername,
					DBPassword);

			StmtCheck = ConnDB.createStatement();

			int lIntResult = StmtCheck.executeUpdate(Query);

			if (lIntResult > 0)
				return String.valueOf(true);

			return String.valueOf(false);

		} catch (Exception e) {
			fail("Exception in ExecuteSQLQry function " + e.getMessage());
			return String.valueOf(false);
		}

	}

	/**
	 * The function returns the count of distinct objects in a specific column
	 * in a table
	 * 
	 * @param tableName
	 *            , columnName,condition, dbDetails
	 * @return int Author:  
	 */
	public String getDistinctObjects(String tableName, String columnName,
			String condition, String ConnectionString ,String DBUsername,String DBPassword) throws Exception {

		try {

			String Query = "";

			int test = 0;
			Query = "select count(distinct(" + columnName + ")) from "
			+ tableName + " where " + condition;
			info("sql query used is " + Query);
			ResultSet lObjResultSet = executeSQLQryWithGivenDBDetails(Query, ConnectionString ,DBUsername, DBPassword);

			if (lObjResultSet != null) {

				while (lObjResultSet.next()) {
					test = Integer.parseInt(lObjResultSet.getString(1));
				}
				return String.valueOf(test);

			} else {
				return String.valueOf(test);
			}

		} catch (Exception e) {
			fail("Exception in getDistinctObjects function " + e.getMessage());
			return "0";
		}

	}

	/**
	 * 
	 * The function returns the count of M1 objects in a specific table
	 * 
	 * @param tableName
	 *            , condition, dbDetails
	 * @return int Author:  
	 */

	public String setVariableValueUsingListIndex(String listVariableName,String index) throws AbstractScriptException{
		int indexValue=Integer.parseInt(index);
		String[] valuesArray=listVariableName.split(",");
		if(indexValue>0 && indexValue <= valuesArray.length)
		{                         
			return valuesArray[indexValue-1];
		}
		else
		{
			return "";
		}
	} 


	public void closeConnection(){
		try {
			if(Rsltset!=null)Rsltset.close();
			if(StmtCheck!=null)StmtCheck.close();
			if(ConnDB!=null)ConnDB.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// fixed bug 20177576

	public String executeSQLQryForSingleRecord(String Query,String recId) throws Exception {

		ConnDB = null;
		StmtCheck = null;
		Rsltset = null;
		String result=null;

		try {

			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");

			} catch (Exception e) {
				fail("Exception in ExecuteSQLQry funciton " + e.getMessage());
				return null;
			}


			String ConnectionString = getVariables().get("gStrApplicationDBConnectionString");
			String DBUsername = getVariables().get("gStrApplicationDBUsername");
			String DBPassword = getVariables().get("gStrApplicationDBPassword");

			ConnDB = DriverManager.getConnection(ConnectionString, DBUsername,
					DBPassword);

			StmtCheck = ConnDB.createStatement();

			Rsltset = StmtCheck.executeQuery(Query);

			if(Rsltset.next())
				return Rsltset.getString(recId);
			else
				return result;




		} catch (Exception e) {
			fail("Exception in ExecuteSQLQry function " + e.getMessage());
			return null;
		} finally {

		}

	}

	public String appendStrings(String strValue1, String strValue2, String strValue3, String strValue4, String strValue5, String strValue6) throws Exception
	{
		String[] arrString={strValue1,strValue2,strValue3,strValue4,strValue5,strValue6};
		String finalString = "";
		for(int i=0;i<arrString.length;i++)
		{
			if(arrString[i] != null && arrString[i].length()!=0)
				finalString += arrString[i];
		}
		return finalString;
	}

	public String getCurrentMonth() throws AbstractScriptException 
	{
		String strMonth = "";
		try{
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			int intMonth = gregorianCalendar.get(GregorianCalendar.MONTH)+1;
			strMonth = String.valueOf(intMonth);
			if (intMonth <10)
				return "0"+strMonth;
			else
				return strMonth;
		}
		catch (Exception e){
			fail("Exception in getting the current month " + e.getMessage());
			return null;
		}           
	}



	public void initialize() throws Exception {
	}

	public void run() throws Exception {
	}

	public void finish() throws Exception {
	}

}
