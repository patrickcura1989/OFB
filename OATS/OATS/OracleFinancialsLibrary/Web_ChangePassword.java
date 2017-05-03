
public interface Web_ChangePassword {
	static String CURRENT_PASSWORD = "/web:window[@index='0' or @title='Change Password']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_password[@id='password' or @name='password' or @index='0']";
	static String NEW_PASSWORD = "/web:window[@index='0' or @title='Change Password']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_password[@id='newPassword' or @name='newPassword' or @index='1']";
	static String NEW_PASSWORD_AGAIN = "/web:window[@index='0' or @title='Change Password']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:input_password[@id='newPassword2' or @name='newPassword2' or @index='2']";
	static String SUBMIT_BUTTON = "/web:window[@index='0' or @title='Change Password']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Submit' or @value='Submit' or @index='0']";
	
}
