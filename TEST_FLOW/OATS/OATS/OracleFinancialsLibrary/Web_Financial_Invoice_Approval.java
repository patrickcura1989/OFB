
public interface Web_Financial_Invoice_Approval {
	static final String APPROVE_BUTTON = "/web:window[@index='0' or @title='Financial Invoice Approval']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Approve' or @value='Approve' or @index='0']";
	static final String REJECT_BUTTON = "/web:window[@index='0' or @title='Financial Invoice Approval']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:button[@id='Reject_uixr' or @value='Reject' or @index='6']";
	static final String NOTE_TEXT_AREA = "/web:window[@index='0' or @title='Financial Invoice Approval']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:textarea[@id='Note' or @name='Note' or @index='0']";
	static final String REJECTION_REASON_SELECTBOX = "/web:window[@index='0' or @title='Financial Invoice Approval']/web:document[@index='0']/web:form[@id='DefaultFormName' or @name='DefaultFormName' or @index='0']/web:select[@id='RejectionReason' or @name='RejectionReason']";
}
