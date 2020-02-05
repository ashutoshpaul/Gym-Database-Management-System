
public class FilterTrainerClass {
	
	private String trainerID = null;
	private String trainerName = null;
	private String phoneNumber = null;
	private String salary = null;
	
    public FilterTrainerClass(String trainerID , String trainerName , String phoneNumber , String salary) {
        this.trainerID = trainerID;
        this.trainerName = trainerName;
        this.phoneNumber = phoneNumber;
        this.salary = "Rs. " + salary;
    }

    public String getMemberID() {
        return trainerID;
    }

    public void setMemberID(String trainerID) {
        this.trainerID = trainerID;
    }
    public String getMemberName() {
        return trainerName;
    }

    public void setMemberName(String trainerName) {
        this.trainerName = trainerName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = "Rs. " + salary;
    }
    
}
