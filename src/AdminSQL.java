import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminSQL {

	public void createAdminTable(Connection connection) {
		try {
			PreparedStatement create = connection.prepareStatement("CREATE TABLE `studentdatabase`.Admins ( Name VARCHAR(30), PhNo1 CHAR(10), PhNo2 varchar(10), Email VARCHAR(50) , Security_Question VARCHAR(100), Security_Answer VARCHAR(100), Password VARCHAR(30), Date_Of_Joining VARCHAR(12), Note VARCHAR(1000), PRIMARY KEY(Email));");
			create.execute();
		}catch(Exception e) {}
	}
	
	public boolean insertToAdminTable(Connection connection , String name , String phNo1 , String phNo2 , String email , String address , String question , String answer , String password , String doj) {
		try {
			PreparedStatement insert = connection.prepareStatement("INSERT INTO `studentdatabase`.`Admins` (`Name`,`PhNo1`,`PhNo2`,`Email`,`Security_Question`,`Security_Answer`,`Password`,`Date_Of_Joining`) VALUES('" + name + "','" + phNo1 + "','" + phNo2 + "','" + email + "','" + question + "','" + answer + "','" + password + "','" + doj + "');");
			insert.execute();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean attemptToLogin(Connection connection , String inputName , String password) {
		try {
			//email and password
			PreparedStatement login2 = connection.prepareStatement("SELECT `Password` FROM `studentdatabase`.`Admins` WHERE `Email` = '" + inputName + "' AND `Password` = '" + password + "';");
			ResultSet res2 = login2.executeQuery();
			while(res2.next()) {
				if(res2.getString(1).toString().equals(password)) {
					return true;
				}
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean deleteAccout(Connection connection , String email) {
		try {
			PreparedStatement delete = connection.prepareStatement("DELETE FROM `studentdatabase`.`Admins` WHERE `Email` = '" + email + "';");
			delete.execute();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean findEmail(Connection connection , String email , String question , String answer) {
		try {
			PreparedStatement findEmail = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`Admins` WHERE `Email` = '" + email + "' AND `Security_Question` = '" + question + "' AND `Security_Answer` = '" + answer + "';");
			ResultSet res = findEmail.executeQuery();
			while(res.next()) {
				if(res.getString(1).toString().equalsIgnoreCase("") == false) {
					return true;
				}
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updatePassword(Connection connection , String sentEmail , String password) {
		String email = "";
		String phNo1 = "";
		String phNo2 = "";
		
		try {
			PreparedStatement get = connection.prepareStatement("SELECT `Email`,`PhNo1`,`PhNo2` FROM `studentdatabase`.`admins` WHERE `Email` = '" + sentEmail + "';"); 
			ResultSet r = get.executeQuery();
			while(r.next()) {
				email = r.getString(1);
				phNo1 = r.getString(2);
				phNo2 = r.getString(3);
			}
		}catch(Exception e) {
			return false;
		}
		
		try {
			PreparedStatement update = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `Password` = '" + password + "' WHERE `Email` = '" + email + "';");
			update.execute();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updateData(Connection connection , String name , String phNo1 , String phNo2 , String email , String currentAdminEmail) {
		try {
			PreparedStatement up0 = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `Name` = '" + name + "' WHERE (`Email` = '" + currentAdminEmail + "');");
			up0.execute();
			
			PreparedStatement up1 = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `PhNo1` = '" + phNo1 + "' WHERE (`Email` = '" + currentAdminEmail + "');");
			up1.execute();
			
			PreparedStatement up2 = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `PhNo2` = '" + phNo2 + "' WHERE (`Email` = '" + currentAdminEmail + "');");
			up2.execute();
			
		}catch(Exception e) {}
		try {
			PreparedStatement up3 = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `Email` = '" + email + "' WHERE (`Email` = '" + currentAdminEmail + "');");
			up3.execute();
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean clearNote(Connection connection , String email) {
		try {
			PreparedStatement clearNote = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `Note` = '' WHERE (`Email` = '" + email + "');");
			clearNote.execute();
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean saveNote(Connection connection , String email , String content) {
		try {
			PreparedStatement saveNote = connection.prepareStatement("UPDATE `studentdatabase`.`admins` SET `Note` = '" + content + "' WHERE (`Email` = '" + email + "');");
			saveNote.execute();
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
