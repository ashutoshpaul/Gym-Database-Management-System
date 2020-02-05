
public class FeeListClass {
	
	private String id = null;
	private String name = null;
	private String membership = null;
	private String totalAmount = null;
	private String pendingAmount = null;
	private String  receiptNumber = null;
	private String  transactionID = null;
	private String dateOfPayment = null;
	private String amountPayed = null;
	
	public FeeListClass(String id , String name , String membership , String totalAmount , String pendingAmount , String receiptNumber , String transactionID , String dateOfPayment , String amountPayed) {
		this.id = id;
		this.name = name;
		this.membership = membership;
		this.totalAmount = "Rs. " + totalAmount;
		this.pendingAmount = "Rs. " + pendingAmount;
		this.receiptNumber = receiptNumber;
		this.transactionID = transactionID;
		this.dateOfPayment = dateOfPayment;
		this.amountPayed = "Rs. " + amountPayed;
	}
	
	public String getID() {
        return id;
    }
    public void setID(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMembership() {
        return membership;
    }
    public void setMembership(String membership) {
        this.membership = membership;
    }
    public String getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = "Rs. " + totalAmount;
    }
    public String getPendingAmount() {
        return pendingAmount;
    }
    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = "Rs. " + pendingAmount;
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
        this.amountPayed = "Rs. " + amountPayed;
    }
}
