import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExTrainerSQL {
	
	public void createExTrainersTable(Connection connection) {
		try {
			PreparedStatement cr = connection.prepareStatement("CREATE TABLE `studentdatabase`.ex_trainers ( Name VARCHAR(30), Address VARCHAR(200), Gender VARCHAR(15) , Designation VARCHAR(30), City VARCHAR(100) , PinCode VARCHAR(20) , Contact_Number_1 VARCHAR(13) , Contact_Number_2 VARCHAR(13) , Email VARCHAR(50) , Salary INT , Date_Of_Joining VARCHAR(12) , Date_Of_Resignation VARCHAR(12));");
			cr.execute();
		}catch(Exception e) {}
	}
	
	public boolean deleteParticualEntry(Connection connection , String name ,String address) {
		try {
			PreparedStatement p = connection.prepareStatement("DELETE FROM `studentdatabase`.`ex_trainers` WHERE `Name` = '" + name + "' AND `Address` = '" + address + "';");
			p.execute();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean dropTable(Connection connection) {
		try {
			PreparedStatement p = connection.prepareStatement("DROP TABLE `studentdatabase`.`ex_trainers`;");
			p.execute();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public void insertInExTrainersTable(Connection connection , String tnrID) {
		String name = "" , address = "" , gender = "" , designation = "" , city = "" , pinCode = "" , phNo1 = "" , phNo2 = "" , email = "" , salary = "" , doj = "";
		String dol = "";
		try {
			//retrieve data
			PreparedStatement ret0 = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_trainers` WHERE `ID` = '" + tnrID + "';");
			ResultSet r0 = ret0.executeQuery();
			r0.next();
			
			name = r0.getString(2);
			gender = r0.getString(3);
			salary = r0.getString(4);
			phNo1 = r0.getString(7);
			designation = r0.getString(9);
			doj = r0.getString(10);
			
			PreparedStatement ret1 = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_trainers_connectivity` WHERE `ID` = '" + tnrID + "';");
			ResultSet r1 = ret1.executeQuery();
			r1.next();
			
			phNo2 = r1.getString(2);
			email = r1.getString(3);
			address = r1.getString(4);
			city = r1.getString(5);
			pinCode = r1.getString(7);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			
			dol = dateFormat.format(date).toString();	//Example: 16/11/2019
			
		}catch(Exception e) {}
		
		try {
			PreparedStatement ins = connection.prepareStatement("INSERT INTO `studentdatabase`.`ex_trainers` (`Name` , `Address` , `Gender` , `Designation` , `City` , `PinCode` , `Contact_Number_1` , `Contact_Number_2` , `Email` , `Salary` , `Date_Of_Joining` , `Date_Of_Resignation`) VALUES('" + name + "' , '" + address + "' , '" + gender + "' , '" + designation + "' , '" + city + "' , '" + pinCode + "' , '" + phNo1 + "' , '" + phNo2 + "' , '" + email + "' , '" + salary + "' , '" + doj + "' , '" + dol + "');");
			ins.execute();
		}catch(Exception e) {}
	}
	
}
