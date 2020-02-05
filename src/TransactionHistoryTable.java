
public class TransactionHistoryTable {
	
	private String dateOfPayment = null;
	private String amountPayed = null;
	private String receiptNumber  = null;
	private String transactionID = null;
	
	public TransactionHistoryTable(String dateOfPayment , String amountPayed , String receiptNumber , String transactionID) {
		this.dateOfPayment = dateOfPayment;
		this.amountPayed = amountPayed;
		this.receiptNumber = receiptNumber;
		this.transactionID = transactionID;
	}
	
	public String getReceiptNumber() {
        return receiptNumber;
    }
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    public String getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    public String getDateOfPayment() {
        return dateOfPayment;
    }
    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
    public String getAmountPayed() {
        return amountPayed;
    }
    public void setAmountPayed(String amountPayed) {
        this.amountPayed = amountPayed;
    }
}
