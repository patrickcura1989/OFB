
public interface Web_PaymentProcessRequests {
   static final String PAGE_NAME = "/web:window[@title='*']" ;	   
   static final String PAYMENT_DATE = "/web:window[@title='Payment Process Requests']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:input_text[@id='SearchPayDate']" ;
   static final String GO_BUTTON = "/web:window[@title='Payment Process Requests']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:button[@value='Go']" ;
   static final String REFRESH_STATUS = "/web:window[@title='Payment Process Requests']/web:document[@index='0']/web:form[@id='DefaultFormName']/web:button[@id='ResultsTable:Refresh:*']" ;
   static final String REMOVE_FROM_REQUEST = "" ;
   static final String SUBMIT_BUTTON = "" ;
   static final String REQUEST_LINK = "/web:window[@title='Payment Process Requests']/web:document[@index='0']/web:a[@text='%']";
   static final String TERMINATE_REQUEST = "" ;
   static final String START_ACTION = "" ;
   
}
