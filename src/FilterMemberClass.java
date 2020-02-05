
public class FilterMemberClass {
	
	private String memberID = null;
	private String memberName = null;
	private String phoneNumber = null;
	private String amount = null;
	
    public FilterMemberClass(String memberID , String memberName , String phoneNumber , String amount) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.amount = "Rs. " + amount;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = "Rs. " + amount;
    }
    
}
