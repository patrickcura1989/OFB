
public interface Web_CustomerAccount {
	static String SAVE_BUTTON = "/web:window[@index='2' or @title='Customer Account']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Save_uixr' or @value='<SPAN class=xq Oracle_OS_WebDOM_ElementID=&quot;True&quot;>S</SPAN>ave' or @index='6']";
	static String VIEW_DETAILS = "/web:window[@index='2' or @title='Customer Account']/web:document[@index='0']/web:img[@alt='Details Enabled' or @index='88' or @src='https://app-layer-oracleebs-test.ds.acc.co.nz:4476/OA_MEDIA/detailsicon_enabled.gif']";
	static String ADDRESS_1 = "/web:window[@index='2' or @title='Customer Account']/web:document[@index='0']/web:span[@id='AcctSitesTable:FormattedAddress:0' or @index='58']";
	static String CHANGES_SAVED = "/web:window[@index='2' or @title='Customer Account']/web:document[@index='0']/web:div[@text='Your changes have been saved' or @index='9']";	
}
