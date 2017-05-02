
public interface Web_Interface_Files {
	static String MAIN_TITLE = "/web:window[@title='Index of*']";
	static String BULK_NOTES = MAIN_TITLE + "/web:document[@index='0' or @name='myWindow']/web:a[@text='bulk_notes/' or @index='12']";	
	static String TO_DO = MAIN_TITLE + "/web:document[@index='0' or @name='myWindow']/web:a[@text='todo/' or @index='8']";
	static String CSV_FILE = MAIN_TITLE + "/web:document[@index='0' or @name='myWindow']/web:a[@text='*.csv'or @index='5']";
	static String STATEMENTS_CE_INTERFACE = MAIN_TITLE + "/web:document[@index='0' or @name='myWindow']/web:a[@text='statements_ce_interface/']";
	static String INBOUND = MAIN_TITLE + "/web:document[@index='0' or @name='myWindow']/web:a[@text='inbound/']";
	static String TXT_FILE = MAIN_TITLE + "/web:document[@index='0' or @name='myWindow']/web:a[@text='*.txt']";
}
