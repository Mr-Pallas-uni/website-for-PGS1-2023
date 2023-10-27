package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TeamMemberProcessor {
    private static final String DATABASE = "jdbc:sqlite:database/vtp.db";

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
         System.out.println("Inserting State data");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         Statement statement = connection.createStatement();
         for (int i = 0; i < memberID.length; i++) {
            String query = "INSERT into States VALUES ("
                                + "'" + memberID[i] + "',"
                                + "'" + memberfNames[i] + "',"
                                + "'" + memberlNames[i] + "',"
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
}
