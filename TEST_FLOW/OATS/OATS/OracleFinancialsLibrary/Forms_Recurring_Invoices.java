
public interface Forms_Recurring_Invoices {	
	static String FORM_NAME = "RECURRING_INVOICES";
	
	static String SUPPLIER_NAME = "//forms:textField[(@name='RECURRING_INVOICES_VENDOR_NAME_0')]";
	static String CALENDAR_NAME = "//forms:textField[(@name='RECURRING_INVOICES_REC_PAY_PERIOD_TYPE_0')]";
	static String NUMBER_OF_PERIODS = "//forms:textField[(@name='RECURRING_INVOICES_NUM_OF_PERIODS_0')]";
	static String FIRST_PERIOD = "//forms:textField[(@name='RECURRING_INVOICES_FIRST_PERIOD_0')]";
	static String INVOICE_NUMBER = "//forms:textField[(@name='RECURRING_INVOICES_RECURRING_PAY_NUM_0')]";
	static String RECURRING_INVOICES_DESC = "//forms:textField[(@name='RECURRING_INVOICES_DESC_FLEX_%')]";
	static String DISTRIBUTION_SET_NAME = "//forms:textField[(@name='RECURRING_INVOICES_DISTRIBUTION_SET_0')]";
	static String FIRST_AMOUNT = "//forms:textField[(@name='RECURRING_INVOICES_FIRST_AMOUNT_0')]";
	static String NUMBER_OF_INVOICES = "//forms:textField[(@name='RECURRING_INVOICES_NUMBER_INVOICES_0')]";	
	static String CREATE_RECURRING_INVOICES_BUTTON = "//forms:button[(@name='RECURRING_INVOICES_CREATE_INVOICES_BUTTON_0')]";
	static String CREATE_INVOICES_OK_BUTTON = "//forms:button[(@name='REC_INV_CONTROL_OK_BUTTON_0')]";
	
}
