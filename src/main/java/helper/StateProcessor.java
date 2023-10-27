package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StateProcessor {
    private static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    public static void main (String[] args) {
      
    String stateNames[] = {
        "New South Wales",
        "Victoria",
        "Queensland",
        "South Australia",
        "Western Australia",
        "Tasmania",
        "Australian Capital Territory",
        "Other Australian Territory"
    };

    int stateCodes[] = {
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
    };

    String stateAbreviation[] = {
        "NSW",
        "VIC",
        "QLD",
        "SA",
        "WA",
        "TAS",
        "ACT",
        "Other",
    };
      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         System.out.println("Inserting State data");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         Statement statement = connection.createStatement();
         for (int i = 0; i < stateCodes.length; i++) {
            String query = "INSERT into States VALUES ("
                                + "'" + stateCodes[i] + "',"
                                + "'" + stateNames[i] + "',"
                                + "'" + stateAbreviation[i] + "',"
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
