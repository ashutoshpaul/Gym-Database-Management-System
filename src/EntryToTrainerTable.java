import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class EntryToTrainerTable {
	
	public static boolean deleteTrainer(Connection connection , String tnrID) {
		try {
			
			//insert to exTrainers before deleting from trainer table
			ExTrainerSQL extt = new ExTrainerSQL();
			extt.insertInExTrainersTable(connection, tnrID);
			
			try {
				PreparedStatement step0 = connection.prepareStatement("DROP TABLE `studentdatabase`.`rating_" + tnrID + "`;");
				step0.execute();
			}catch(Exception d) {}
			
			PreparedStatement step1 = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_trainers` WHERE (`ID` = '" + tnrID + "');");
			step1.execute();
			
			PreparedStatement step2 = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_trainers_connectivity` WHERE (`ID` = '" + tnrID + "');");
			step2.execute();
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean updateTrainerData(Connection connection , String modifyID , String modifyName , String modifyGender , String modifyDOB , String modifyAge , String modifyType , String modifyAddress , String modifyCity , String modifyCountry , String modifyPin , String modifyPhNo1 , String modifyPhNo2 , String modifyEmail , String modifyMembership , String modifySalary , String modifyDOJ) {
		try {
			PreparedStatement up0 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Name` = '" + modifyName + "' WHERE (`ID` = '" + modifyID + "');");
			up0.execute();
			
			PreparedStatement up1 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Gender` = '" + modifyGender + "' WHERE (`ID` = '" + modifyID + "');");
			up1.execute();
			
			PreparedStatement up2 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Salary` = '" + modifySalary + "' WHERE (`ID` = '" + modifyID + "');");
			up2.execute();
			
			PreparedStatement up3 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `DOB` = '" + modifyDOB + "' WHERE (`ID` = '" + modifyID + "');");
			up3.execute();
			
			PreparedStatement up4 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Age` = '" + modifyAge + "' WHERE (`ID` = '" + modifyID + "');");
			up4.execute();
			
			PreparedStatement up5 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `PhoneNumOne` = '" + modifyPhNo1 + "' WHERE (`ID` = '" + modifyID + "');");
			up5.execute();
			
			PreparedStatement up6 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Membership_Alloted` = '" + modifyMembership + "' WHERE (`ID` = '" + modifyID + "');");
			up6.execute();
			
			PreparedStatement up7 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Trainer_Type` = '" + modifyType + "' WHERE (`ID` = '" + modifyID + "');");
			up7.execute();
			
			PreparedStatement up8 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `Date_Of_Joining` = '" + modifyDOJ + "' WHERE (`ID` = '" + modifyID + "');");
			up8.execute();
			
			//gym_trainers_connectivity table
			PreparedStatement up9 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `PhoneNumberOne` = '" + modifyPhNo1 + "' WHERE (`ID` = '" + modifyID + "');");
			up9.execute();
			
			PreparedStatement up10 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `PhoneNumberTwo` = '" + modifyPhNo2 + "' WHERE (`ID` = '" + modifyID + "');");
			up10.execute();
			
			PreparedStatement up11 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `Email` = '" + modifyEmail + "' WHERE (`ID` = '" + modifyID + "');");
			up11.execute();
			
			PreparedStatement up12 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `Address` = '" + modifyAddress + "' WHERE (`ID` = '" + modifyID + "');");
			up12.execute();
			
			PreparedStatement up13 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `CITY` = '" + modifyCity + "' WHERE (`ID` = '" + modifyID + "');");
			up13.execute();
			
			PreparedStatement up14 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `COUNTRY` = '" + modifyCountry + "' WHERE (`ID` = '" + modifyID + "');");
			up14.execute();
			
			PreparedStatement up15 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers_connectivity` SET `PinCode` = '" + modifyPin + "' WHERE (`ID` = '" + modifyID + "');");
			up15.execute();
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static void deleteCurrentConnectivityRow(Connection connection , String id) {
		try {
			PreparedStatement ps = connection.prepareCall("DELETE FROM `studentdatabase`.`gym_trainers_connectivity` WHERE (`ID` = '" + id + "');");
			ps.execute();
		}catch(Exception e) {}
	}
	
	public static String[] loadTrainerDataFromDB(Connection connection , String trainerID) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_trainers` WHERE (`ID` = '" + trainerID + "');");
			ResultSet rs = ps.executeQuery();
			rs.next();
			String[] containsFreshData = new String[17];
			
			containsFreshData[0] = rs.getString(1);			//id
			containsFreshData[1] = rs.getString(2);			//name
			containsFreshData[2] = rs.getString(3);			//gender
			containsFreshData[3] = rs.getString(4);			//salary
			containsFreshData[4] = rs.getString(5);			//DOB
			containsFreshData[5] = rs.getString(6);			//age
			containsFreshData[6] = rs.getString(7);			//phNo
			containsFreshData[7] = rs.getString(8);			//membership
			containsFreshData[8] = rs.getString(9);			//type
			containsFreshData[9] = rs.getString(10);		//date of joining
			containsFreshData[10] = rs.getString(11);		//rating
			
			try {
				PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_trainers_connectivity` WHERE (`ID` = '" + trainerID + "');");
				ResultSet rs1 = ps1.executeQuery();
				rs1.next();
				
				containsFreshData[11] = rs1.getString(2);			//phNo2
				containsFreshData[12] = rs1.getString(3);			//email
				containsFreshData[13] = rs1.getString(4);			//address
				containsFreshData[14] = rs1.getString(5);			//city
				containsFreshData[15] = rs1.getString(6);			//country
				containsFreshData[16] = rs1.getString(7);			//pin code
				
				return(containsFreshData);
			}catch(Exception d) {}
		}catch(Exception e) {}
		
		
		String[] coal = {"null"};
		return(coal);
	}
	
	public boolean makeNewTrainerEntry(Connection connection , String newTrainerID , String newTrainerName , String selectedTrainerSex , String newTrainerSalary , String newTrainerDOB , String newTrainerAge , String newTrainerPhoneNumber , String newTrainerAltPhoneNumber , String newTrainerEmail , String newTrainerDesignation , String newTrainerAddress ,String newTrainerCity , String newTrainerNationality , String newTrainerPin , String membershipToTrainer , String dojLabel) {
		try{
			//first add to GYM_TRAINERS_CONNECTIVITY Table
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `studentdatabase`.`gym_trainers_connectivity` (`PhoneNumberOne`, `PhoneNumberTwo`, `Email`, `Address`, `CITY`, `COUNTRY`, `PinCode`, `ID`) VALUES ('" + newTrainerPhoneNumber.trim() + "', '" + newTrainerAltPhoneNumber.trim() + "', '" + newTrainerEmail.trim() + "', '" + newTrainerAddress.trim() + "', '" + newTrainerCity.trim() + "', '" + newTrainerNationality.trim() + "', '" + newTrainerPin.trim() + "', 'TNR" + newTrainerID.trim() + "');");
			ps.execute();
			try {
				StringTokenizer st = new StringTokenizer(dojLabel , "/");
				String day = st.nextToken();
				String month = st.nextToken();
				String year = st.nextToken();
				dojLabel = "";
				dojLabel = year + "-" + month + "-" + day;
				
				PreparedStatement ps2 = connection.prepareStatement("INSERT INTO `studentdatabase`.`gym_trainers` (`ID`, `Name`, `Gender`, `Salary`, `DOB`, `Age`, `PhoneNumOne`, `Membership_Alloted`, `Trainer_Type`, `Date_Of_Joining`, `RATING`) VALUES ('TNR" + newTrainerID.trim() + "', '" + newTrainerName.trim() + "', '" + selectedTrainerSex.trim() + "', '" + newTrainerSalary.trim() + "', '" + newTrainerDOB.trim() + "', '" + newTrainerAge.trim() + "', '" + newTrainerPhoneNumber.trim() + "', '" + membershipToTrainer.trim() + "', '" + newTrainerDesignation.trim() + "', '" + dojLabel.trim() + "', '" + "NULL" + "');");
				ps2.execute();
				try {
					PreparedStatement ps3 = connection.prepareStatement("CREATE TABLE `studentdatabase`.RATING_TNR" + newTrainerID + " ( Member_ID VARCHAR(20), Rating varchar(5));");
					ps3.execute();
					return true;
				}catch(Exception d) {
					return false;
				}
				
			}catch(Exception e) {
				//delete Connectivity Row
				deleteCurrentConnectivityRow(connection , "TNR" + newTrainerID);
				return false;
			}
		}catch(Exception f) {
			return false;
		}
	}

}
