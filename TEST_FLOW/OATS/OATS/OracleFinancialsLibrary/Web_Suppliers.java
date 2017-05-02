
public interface Web_Suppliers {
	static final String PAGE_NAME = "/web:window[@title='Suppliers']" ;
	static String SUPPLIER_NUMBER = "/web:window[@index='2' or @title='Suppliers']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_text[@id='SearchSuppNum' or @name='SearchSuppNum' or @index='1']";
	static String GO_BUTTON = "/web:window[@index='2' or @title='Suppliers']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='GoButton' or @value='Go' or @index='1']";
	static String CREATE_SUPPLIER_BUTTON = "/web:window[@index='0' or @title='Suppliers']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='supCreatBtn' or @value=\"\r\n<SCRIPT>function _uixspu(f,v,e,s,pt,p,o){if(!o)o=new Object();o.event=(e)?e:&apos;update&apos;;if(s)o.source=s;if(p){o.partial=&apos;true&apos;;if(pt)o.partialTargets=pt;_submitPartialChange(f,v,o);}else submitForm(f,v,o);}</SCRIPT>\r\nCreate Supplier\" or @index='0']";
	static final String SUPPLIER_NAME = "/web:window[@title='Suppliers']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:input_text[@id='SearchSuppName']" ;
	static final String UPDATE_BUTTON = "/web:window[@title='Suppliers']/web:document[@index='0']/web:img[@index='" + "%" +"']" ;

}
