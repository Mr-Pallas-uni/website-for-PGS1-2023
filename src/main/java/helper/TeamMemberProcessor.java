package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TeamMemberProcessor {
    private static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    //Insert Member method
    static void insertMembers (String[] id, String[] firstName, String[] lastName, Connection connection) {
        try {
            for (int i = 0; i < id.length; i++) {
                Statement statement = connection.createStatement();
                String query = "INSERT into TeamMembers VALUES ("
                                + "'" + id[i] + "',"
                                + "'" + firstName[i] + "',"
                                + "'" + lastName[i] + "'"
                                + ")";
            
                                // Execute the INSERT
                System.out.println("Executing: " + query);
                statement.execute(query);
            }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
    }
    public static void main (String[] args) {
      
    String memberfNames[] = {
        "Joshua",
        "Murtaza",
    };
    String memberlNames[] = {
        "Piscioneri",
        "Mohseni",
    };
    String memberID[] = {
        "S4005841",
        "S4004041"
    };
      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         System.out.println("Inserting team member data");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);
         //inserting members
         insertMembers(memberID, memberfNames, memberlNames, connection);

      }
       catch (Exception e) {
         e.printStackTrace();
      }      
   }
}
