import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckDuplicateEmails {

	public boolean checkIfEmailPresentInMembersTable(Connection connection , String email) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`gym_members_connectivity`;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase(email.trim()) == true) {
					return false;
				}
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean checkIfEmailPresentInTrainersTable(Connection connection , String email) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`gym_trainers_connectivity`;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase(email.trim()) == true) {
					return false;
				}
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean isNewEmail(Connection connection , String email) {
		/* of == "member": 
		 * of == "member": gym_trainers_connectivity
		 */
			
		try {
			//first check in gym_members_connectivity
			
			PreparedStatement ps = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`gym_members_connectivity`;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase(email.trim()) == true) {
					return false;
				}
			}
			
			//now check in gym_trainers_connectivity
			
			PreparedStatement ps1 = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`gym_trainers_connectivity`;");
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()) {
				if(rs1.getString(1).equalsIgnoreCase(email.trim()) == true) {
					return false;
				}
			}
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean checkIfDuplicateEmail(Connection connection , String of , String id ,  String email) {
		/* of == "member": gym_members_connectivity
		 * of == "member": gym_trainers_connectivity
		 */
		
		try {
			if(of.equalsIgnoreCase("member") == true) {
				of = "gym_members_connectivity";
			}else {
				of = "gym_trainers_connectivity";
			}
			
			PreparedStatement ps = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`" + of + "` WHERE `Email` = '" + email.trim() + "' AND `ID` != '" + id + "';");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase("") == false) {
					return false;
				}
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
