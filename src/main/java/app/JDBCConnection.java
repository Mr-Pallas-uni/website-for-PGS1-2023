package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs2016() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2016'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("code");
                String name  = results.getString("name");

                // Create a LGA Object
                LGA lga = new LGA(code, name, 2016);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    // TODO: Add your required methods here
    public ArrayList<State> getStates(int year) {
        ArrayList<State> states = new ArrayList<State>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT s.StateName, SUM(count) AS count FROM\r\n" + //
                    "LGAs l \r\n" + //
                    "JOIN states s ON l.stateCode = s.StateCode\r\n" + //
                    "JOIN population p ON p.lga_code = l.lga_code WHERE l.lga_year = '" + year +"' AND p.lga_year = " + year + "\r\n" + //
                    "GROUP BY s.StateCode;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String name  = results.getString("StateName");
                int count = results.getInt("count");

                // Add the lga object to the array
                State state = new State(name, count, year);
                states.add(state);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return states;
    }

    public int countLGAs(int year) {
        int count = -1;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT COUNT(*) c FROM LGAs WHERE lga_year = '" + year +"';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            count  = results.getInt("c");

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return count;
    }

    public int countPop(int year) {
        int count = -1;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(count) s FROM population WHERE lga_year = '" + year +"';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            count  = results.getInt("s");

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return count;
    }
    
    public Map<String, String> getTeam() {
        Map<String, String> team = new HashMap<String, String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM teammembers;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            String member = "1";
            while (results.next()) {
                String id  = results.getString("MemberID");
                String fName  = results.getString("FirstName");
                String lName  = results.getString("LastName");

                // Add to the map
                team.put("id_" + member, id);
                team.put("fname_" + member, fName);
                team.put("lname_" + member, lName);

                member = "2";
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return team;
    }

    public ArrayList<String> getPersonaAttribute(String PersonaName, String type) {
        ArrayList<String> attributes = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT Description FROM personaAttributes \r\n"  +
                            "WHERE PerName = '" + PersonaName +"' AND AttributeType = '" + type + "';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String attribute  = results.getString("Description");

                // Add the lga object to the array
                attributes.add(attribute);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return attributes;
    }

    public ArrayList<Persona> getPersona() {
        ArrayList<Persona> personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM personas;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String name  = results.getString("PerName");
                String image  = results.getString("perImagePath");
                String credit  = results.getString("PerImageCredit");

                // Add the lga object to the array
                Persona persona = new Persona(name, image, credit);
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return personas;
    }
    public ArrayList<LGADiff> getDiff(String dataset, String conditions) {

        ArrayList<LGADiff> LGADiffs = new ArrayList<LGADiff>();

        // Connection connection = null;

        // try {
        //     connection = DriverManager.getConnection(JDBCConnection.DATABASE);

        //     Statement statement = connection.createStatement();
        //     statement.setQueryTimeout(30);

        //     String query = "SELECT lgas1621.name, sum(c16) AS d16, sum(c21) d21, 100 * sum(totalDiff)/ sum(c16) AS perChange1621 FROM "
        //      + dataset + " NATURAL JOIN lgas1621" + conditions ;
            
        //     ResultSet results = statement.executeQuery(query);

        //     while (results.next()) {
        //         String name  = results.getString("name");
        //         int d16 = results.getInt("d16");
        //         int d21 = results.getInt("d21");

        //     }

        //     statement.close();
        // } catch (SQLException e) {
        //     System.err.println(e.getMessage());
        // } finally {
        //     try {
        //         if (connection != null) {
        //             connection.close();
        //         }
        //     } catch (SQLException e) {
        //         System.err.println(e.getMessage());
        //     }
        // }
        return LGADiffs;
    }

//level 3 a methods
//
//

// level 3b methods
//
//
}



