public interface Web_Site {
	static String NAME = "/web:window[@title='Site*']";
	static String ADDRESS_LINE_3 = "/web:window[@index='2' or @title='Site*']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='HzAddressStyleFlex3' or @name='HzAddressStyleFlex3' or @index='4']";
	static String SAVE_BUTTON = "/web:window[@index='2' or @title='Site*']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Save' or @value='<SPAN class=xq Oracle_OS_WebDOM_ElementID=&quot;True&quot;>S</SPAN>ave' or @index='1']";
	static String APPLY_BUTTON = "/web:window[@index='2' or @title='Site*']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Apply' or @value='A<SPAN class=xq Oracle_OS_WebDOM_ElementID=&quot;True&quot;>p</SPAN>ply' or @index='2']";
}
