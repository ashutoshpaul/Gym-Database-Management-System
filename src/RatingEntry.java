import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

public class RatingEntry {

	public static boolean makeNewRatingEntry(Connection connection , String tnrID , String mbrID , String stars) {
		try {
			PreparedStatement findMBR = connection.prepareStatement("SELECT * FROM  `studentdatabase`.`rating_" + tnrID.trim() + "` WHERE `Member_ID` = '" + mbrID.trim() + "';");
			ResultSet foundResult = findMBR.executeQuery();
			boolean isMemberFound = false;
			while(foundResult.next()) {
				if(foundResult.getString(1).equals(mbrID) == true) {
					isMemberFound = true;
					break;
				}
			}
			if(isMemberFound == true) {
				PreparedStatement up1 = connection.prepareStatement("UPDATE `studentdatabase`.`rating_" + tnrID.trim() + "` SET `RATING` = '" + stars + "' WHERE (`Member_ID` = '" + mbrID + "');");
				up1.execute();
			}else {
				PreparedStatement step2 = connection.prepareStatement("INSERT INTO `studentdatabase`.`rating_" + tnrID + "` (`Member_ID`, `Rating`) VALUES ('" + mbrID + "', '" + stars + "');");
				step2.execute();
			}
		
		}catch(Exception e) {}
		
		
		try {
			
			//calculating new Rate (Average)
			String newRating = "0";
			//SELECT AVG(Price) AS AveragePrice FROM Products; 
			PreparedStatement avg = connection.prepareStatement("SELECT AVG(`Rating`) AS `AveragePrice` FROM `studentdatabase`.`rating_" + tnrID + "`;");
			ResultSet result = avg.executeQuery();
			result.next();
			newRating = result.getString(1);
			
			//updating gym_trainers table
			PreparedStatement up1 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_trainers` SET `RATING` = '" + newRating + "' WHERE (`ID` = '" + tnrID + "');");
			up1.execute();
			
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}

}
