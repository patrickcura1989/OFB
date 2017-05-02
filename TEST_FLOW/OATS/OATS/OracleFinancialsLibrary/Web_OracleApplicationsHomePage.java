
public interface Web_OracleApplicationsHomePage {
	static final String MAIN_MENU = "/web:window[@title='Oracle Applications Home Page']/web:document[@index='0']/web:table[@summary='respTable']" ;
	static final String TABLE_SORT_DESCENDING_IMAGE = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:img[@alt='Sorted in descending order' or @src='*cdsort-1.gif']";
	static final String TABLE_SORT_ASCENDING_IMAGE = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:img[@alt='Sorted in ascending order' or @src='*casort-1.gif']";
	
	static final String ACC_PAYABLES_USER = "/web:window[@title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='ACC Payables User']" ;
	static final String ACC_PAYABLES_INVOICES = "/web:window[@title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Invoices']" ;
	static final String ACC_PAYABLES_ENTRY = "/web:window[@title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Entry']" ;
	static final String ACC_PAYABLES_INVOICES_LINK = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Invoices' or @href=\"javascript:launchForm(&apos;" + Constant.ORACLE_BASE_URL + "/OA_HTML/RF.jsp?function_id=1026&resp_id=50295&resp_appl_id=200&security_group_id=0&lang_code=US&oas=pzaWUzUSrqHMHiybolQzsw..&apos;)\" or @index='26']" ;
	
	static final String ACC_BSC_LEVY_PAYMENT = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='ACC BSC Levy Payment' or @href='##' or @index='7']" ;
	static final String COLLECTIONS = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Collections' or @href=\"javascript:launchForm(&apos;" + Constant.ORACLE_BASE_URL + "/OA_HTML/RF.jsp?function_id=11234&resp_id=50471&resp_appl_id=695&security_group_id=0&lang_code=US&oas=3mRiKLttzOsb6TPT08_Z5A..&apos;)\" or @index='9']";
	
	static final String CORP_PAYABLES_TEAM_LEADER = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='CORP Payables Team Leader' or @href='#' or @index='27']";
	static final String CORP_PAYABLES_TEAM_LEADER_INVOICES = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Invoices']";
	static final String CORP_PAYABLES_TEAM_LEADER_ENTRY = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Entry']";
	static final String CORP_PAYABLES_TEAM_LEADER_INVOICES_LINK = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Invoices' or @href=\"javascript:launchForm(&apos;" + Constant.ORACLE_BASE_URL + "/OA_HTML/RF.jsp?function_id=1026&resp_id=50434&resp_appl_id=200&security_group_id=0&lang_code=US&oas=chi7wdagWOqkZdZCGUfazQ..&apos;)\"]";
	static final String LOGOUT_LINK = "/web:window[@index='0' or @title='Oracle Applications Home Page']/web:document[@index='0']/web:a[@text='Logout' or @href='*/OA_HTML/OALogout.jsp?menu=Y' or @index='3']";
}
