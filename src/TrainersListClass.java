public class TrainersListClass {
	
	private String trainerID = null;
	private String trainerName = null;
	
	public TrainersListClass(String trainerID , String trainerName) {
        this.trainerID = trainerID;
        this.trainerName = trainerName;
    }
	
	public String getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
    
}
