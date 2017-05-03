
public interface Web_UpdatePaymentDetails {
	static String ELECTRONIC_DEFAULT_CHECKBOX = "/web:window[@title='Payment Details']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_checkbox[@id='PmtMthdAssignmentsRN:Defaultitem:2' or @name='PmtMthdAssignmentsRN:Defaultitem:2' or @index='2']";
	static String SEPARATE_REMITTANCE_ADVICE_TAB = "/web:window[@title='Payment Details']/web:document[@index='0']/web:a[@text='Separate Remittance Advice Delivery'or @index='18']";
	static String DELIVERY_METHOD = "/web:window[@title='Payment Details']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:select[(@id='DlvryMthds' or @name='DlvryMthds' or @index='0') and multiple mod 'False']";
	static String SEPARATE_REMITTANCE_ADVICE_TAB_EMAIL = "/web:window[@title='Payment Details']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:input_text[@id='email']";
	static String SAVE_BUTTON = "/web:window[@title='Payment Details']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Apply_uixr' or @value='<SPAN class=xq Oracle_OS_WebDOM_ElementID=&quot;True&quot;>S</SPAN>ave' or @index='4']";
	

}
