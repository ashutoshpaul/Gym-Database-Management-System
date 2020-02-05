
public class MembersListClass {

	private String memberID = null;
	private String memberName = null;
	private String memberMembership = null;
	
    public MembersListClass(String memberID , String memberName , String memberMembership) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberMembership = memberMembership;
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
    public String getMemberMembership() {
        return memberMembership;
    }

    public void setMemberMembership(String memberMembership) {
        this.memberMembership = memberMembership;
    }
}
