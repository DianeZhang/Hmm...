import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class UserUtil {
private int uid = -1;
	private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    
    private final String url = "jdbc:mysql://162.243.187.39:3306/Hmm";
    private final String user = "root";
    private final String password = "";

    private final String getLastLoginSql = "SELECT lastlogin FROM Users where id = ?;";
    private final String getNumNewCourseReview = "SELECT count(*) FROM CourseReviews where created > ?;";
    private final String getNumNewFacultyReview = "SELECT count(*) FROM FacultyReviews where created > ?;";
    
	public UserUtil(int uid) {
		this.uid = uid;
	}
	
	public boolean open() {
		//Start connection
		try {
			 Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(url, user, password);
	        return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String lastUpdateFromLastLogin() {
		String newRecords = "";
		
		try {
			//Get last login timestamp
			st = conn.prepareStatement(getLastLoginSql);
			st.setInt(1,uid);
			rs = st.executeQuery();
			rs.next();
			Timestamp timestamp = rs.getTimestamp("lastlogin");
			
			//Get the number of new course review
			st = conn.prepareStatement(getNumNewCourseReview);
			st.setTimestamp(1,timestamp);
			rs = st.executeQuery();
			int numOfNewCourseReviews = 0;
			if(rs.next()) {
				numOfNewCourseReviews = rs.getInt(1);
			}
			
			//Get the number of new course review
			st = conn.prepareStatement(getNumNewFacultyReview);
			st.setTimestamp(1,timestamp);
			rs = st.executeQuery();
			int numOfNewFacultyReviews = 0 ;
			if(rs.next()) {
				numOfNewFacultyReviews = rs.getInt(1);
			}
			
			//Return two numbers
			newRecords = newRecords + numOfNewCourseReviews + "\t" + numOfNewFacultyReviews + "\n";
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return newRecords;
	}
	
	/**
	 * @brief Close the database connection
	 */
	public void close() {
		try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	
}
