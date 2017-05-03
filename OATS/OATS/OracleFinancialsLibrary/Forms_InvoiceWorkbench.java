
public interface Forms_InvoiceWorkbench {
	static final String FORM_NAME = "INVOICES_SUM_FOLDER_WINDOW";
	static final String TYPE = "//forms:textField[(@name='INV_SUM_FOLDER_INVOICE_TYPE_%')]";
	
	 static final String VENDOR_NAME = "//forms:textField[(@name='INV_SUM_FOLDER_VENDOR_NAME_%')]";
	 static final String INVOICE_DATE = "//forms:textField[(@name='INV_SUM_FOLDER_INVOICE_DATE_%')]";
	 static final String INVOICE_NUM = "//forms:textField[(@name='INV_SUM_FOLDER_INVOICE_NUM_%')]";
	 static final String INVOICE_AMOUNT = "//forms:textField[(@name='INV_SUM_FOLDER_INVOICE_AMOUNT_DSP_%')]";	    		 	    		
	 static final String TAX_AMOUNT = "//forms:textField[(@name='INV_SUM_FOLDER_TOTAL_TAX_AMOUNT_DSP_%')]";
	 static final String INVOICE_WORKFLOW_INFO = "//forms:textField[(@name='INV_SUM_FOLDER_DF_%')]";
	 static final String SUPPLIER_SITE = "//forms:textField[(@name='INV_SUM_FOLDER_VENDOR_SITE_CODE_%')]";
	 
	 static final String GENERAL_HOLDS = "//forms:textField[(@name='INV_SUM_FOLDER_HOLDS_COUNT_DISPLAY_0')]";
	 
	 static final String LINE = "//forms:textField[(@name='LINE_SUM_FOLDER_LINE_NUMBER_%')]";
	 static final String LINE_AMOUNT = "//forms:textField[(@name='LINE_SUM_FOLDER_AMOUNT_DISP_%')]";
	 static final String LINE_TYPE = "//forms:textField[(@name='LINE_SUM_FOLDER_LINE_TYPE_%')]";
	 static final String LINE_TAX_REGIME = "//forms:textField[(@name='LINE_SUM_FOLDER_TAX_REGIME_CODE_%')]";
	 static final String LINE_TAX = "//forms:textField[(@name='LINE_SUM_FOLDER_TAX_%')]";
	 static final String LINE_TAX_STATUS_CODE = "//forms:textField[(@name='LINE_SUM_FOLDER_TAX_STATUS_CODE_%')]";
	 static final String LINE_TAX_RATE_NAME = "//forms:textField[(@name='LINE_SUM_FOLDER_TAX_RATE_CODE_%')]";
	 
	 static final String HOLDS_HOLD_NAME = "//forms:textField[(@name='AP_HOLDS_HOLD_NAME_%')]";
	 static final String HOLDS_RELEASE_NAME = "//forms:textField[(@name='AP_HOLDS_RELEASE_NAME_0')]";
	 
	 static final String DISTRIBUTIONS = "//forms:button[(@name='LINE_SUM_CONTROL_DISTRIBUTIONS_0')]";	
	 static final String ACTIONS = "//forms:button[(@name='INV_SUM_CONTROL_ACTIONS_0')]";
	 
	 static final String SUPPLIER_NAME = "//forms:textField[(@name='INV_SUM_FOLDER_VENDOR_NAME_%')]";
	 static final String RELEASE_REASON = "//forms:textField[(@name='AP_HOLDS_RELEASE_REASON_%')]" ;
	 
	 static final String General_1_TAB = "1 General";
     static final String VIEW_4_PAYMENTS = "4 View Payments";	  
	 static final String LINES_2_TAB = "2 Lines";
	 static final String HOLDS_3_TAB = "3 Holds";
	 
	 static final String AMOUNT_PAID = "//forms:textField[(@name='INV_SUM_FOLDER_INV_CURR_AMOUNT_PAID_0')]" ;
	 static final String PAYMENT_OVERVIEW = "//forms:button[(@name='CONTROL_INV_PAY_PAYMENT_OVERVIEW_BUTTON_0')]" ;
}
