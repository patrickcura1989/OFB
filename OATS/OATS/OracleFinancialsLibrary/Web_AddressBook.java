public interface Web_AddressBook extends Web_AbstractWin{
	static String UPDATE_ADDRESS = "/web:window[@title='Address Book']/web:document[@index='0']/web:img[@alt='Update' or @index='26' or @src='*/OA_MEDIA/updateicon_enabled.gif']";
	static String VERIFY_UPDATED_ADDRESS = "/web:window[@title='Address Book']/web:document[@index='0']/web:td[@text='*%*']";
	static String CREATE_BUTTON = "/web:window[@title='Address Book']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Create' or @value='Create' or @index='2']";
	static String PAYMENT_DETAILS_LINK = "/web:window[@title='Address Book']/web:document[@index='0']/web:a[@text='Payment Details' or @href='javascript:_submitNav(&apos;DefaultFormName&apos;,&apos;https%3A//app-layer-oracleebs-test.ds.acc.co.nz%3A4475/OA_HTML/OA.jsp%3Fpage%3D/oracle/apps/pos/sbd/webui/ByrPayPG%26retainAM%3DY%26_ti%3D2077358494%26oapc%3D17%26OAMC%3D79928_91_0%26menu%3DY%26oaMenuLevel%3D4%26oas%3Dl0LEMCrR4wcCYwvcujqsyQ..&apos;)' or @index='19']";
	static String MANAGE_SITES_ICON = "/web:window[@title='Address Book']/web:document[@index='0']/web:img[@alt='Manage Site' or @index='27' or @src='https://app-layer-oracleebs-test.ds.acc.co.nz:4475/OA_MEDIA/detailsicon_enabled.gif']";
	static String HOME_LINK = "/web:window[@title='Manage Sites']/web:document[@index='0']/web:a[@text='Home' or @href='javascript:_submitNav(&apos;DefaultFormName&apos;,&apos;https%3A//app-layer-oracleebs-test.ds.acc.co.nz%3A4475/OA_HTML/OA.jsp%3F_rc%3DFNDPORTALRELEASEAM%26_ri%3D0%26retainAM%3DN%26_ti%3D1739595887%26oapc%3D33%26OAMC%3D79928_7_0%26menu%3DY%26oas%3DtEPTvT-e9UErmMmUKbNtAA..&apos;)' or @index='3']";
	static String PAYMENT_DETAILS = "/web:window[@title='Address Book']/web:document[@index='0']/web:a[@text='Payment Details']";
	static String MANAGE_SITE = "/web:window[@title='Address Book']/web:document[@index='0']/web:img[@alt='Manage Site']" ;
}
