package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonaProcessor {

   // MODIFY these to load/store to/from the correct locations
   private static final String DATABASE = "jdbc:sqlite:database/vtp.db";

   public static void main (String[] args) {
      
      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         System.out.println("Inserting persona details");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         

      }
       catch (Exception e) {
         e.printStackTrace();
      }      
   }
}
