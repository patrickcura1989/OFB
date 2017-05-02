
public interface Web_ManageSites {
	static final String PAGE_NAME = "/web:window[@title='Manage Sites']" ;
    static final String DESCRIPTIVE_FLEXFIELDS = "/web:window[@title='Manage Sites']/web:document[@index='0']/web:img[@alt='Descriptive Flexfields']" ;    
	static String DESCRIPTIVE_FLEXFIELDS_ICON = "/web:window[@title='Manage Sites']/web:document[@index='0']/web:img[@alt='Descriptive Flexfields' or @index='30' or @src='https://app-layer-oracleebs-test.ds.acc.co.nz:4475/OA_MEDIA/updateicon_enabled.gif']";
	static String APPLY_BUTTON = "/web:window[@title='Manage Sites']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='applyBtn_uixr' or @value='A<SPAN class=xq style=&quot;BACKGROUND-IMAGE: none; BACKGROUND-COLOR: transparent&quot; Oracle_OS_WebDOM_ElementID=&quot;True&quot;>p</SPAN>ply' or @index='4']";
}
