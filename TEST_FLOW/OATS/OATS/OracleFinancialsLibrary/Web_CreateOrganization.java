
public interface Web_CreateOrganization {
	static String ORGANIZATION_NAME = "/web:window[@index='2' or @title='Create Organization']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='organization_name' or @name='organization_name' or @index='0']";
	static String PARTY_NUMBER = "/web:window[@index='2' or @title='Create Organization']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='registry_id' or @name='registry_id' or @index='3']";
	static String ACCOUNT_NUMBER = "/web:window[@index='2' or @title='Create Organization']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='AcctNum' or @name='AcctNum' or @index='15']";
	static String ADDRESS_LINE_1 = "/web:window[@index='2' or @title='Create Organization']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='HzAddressStyleFlex1' or @name='HzAddressStyleFlex1' or @index='23']";
	static String SAVE_AND_ADD_DETAILS_BUTTON = "/web:window[@index='2' or @title='Create Organization']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='saveDtlBut_uixr' or @value='Save And Add Details' or @index='4']";
	static String REFERENCE = "/web:window[@index='2' or @title='Create Organization']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='Insert' or @name='Insert' or @index='18']";	
}
