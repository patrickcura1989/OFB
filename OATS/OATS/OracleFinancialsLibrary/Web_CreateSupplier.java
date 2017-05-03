
public interface Web_CreateSupplier {

	static final String ORGANIZATION_NAME = "/web:window[@title='Create Supplier']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:input_text[@id='organization_name']";
	static final String SUPPLIER_NUMBER = "/web:window[@title='Create Supplier']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:input_text[@id='suppNum']" ;
	static final String APPLY_BUTTON= "/web:window[@title='Create Supplier']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='applyBtn' or @value='A<SPAN class=xq Oracle_OS_WebDOM_ElementID=&quot;True&quot;>p</SPAN>ply' or @index='1']" ;
}
