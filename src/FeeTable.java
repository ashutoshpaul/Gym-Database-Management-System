import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FeeTable {

	public void createFeeTable(Connection connection) {
		try {
			PreparedStatement cr = connection.prepareStatement("CREATE TABLE `studentdatabase`.gym_fee ( ID varchar(10), Name varchar(30), Total_Amount INT , Membership_Type VARCHAR(20) , Receipt_Number VARCHAR(20) PRIMARY KEY, Transaction_ID VARCHAR(20) , Date_Of_Payment DATE ,  Pending_Amount INT , Amount_Payed INT);");
			cr.execute();
		}catch(Exception e) {}
	}

}
