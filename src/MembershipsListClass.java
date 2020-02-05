
public class MembershipsListClass {

	private String membershipName = null;
	private String membershipDuration = null;
	private String membershipAmount = null;
	
	public MembershipsListClass(String membershipName , String membershipDuration , String membershipAmount) {
        this.membershipName = membershipName;
        this.membershipDuration = membershipDuration;
        this.membershipAmount = membershipAmount;
     }

    public String getMembershipName() {
        return membershipName;
    }
    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }
    public String getMembershipDuration() {
        return membershipDuration;
    }
    public void setMembershipDuration(String membershipDuration) {
        this.membershipDuration = membershipDuration;
    }
    public String getMembershipAmount() {
        return membershipAmount;
    }
    public void setMembershipAmount(String membershipAmount) {
        this.membershipAmount = membershipAmount;
    }
    
}
