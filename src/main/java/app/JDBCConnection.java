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

    public ArrayList<LGADiff> getDiff(String dataset, String gender1, String gender2, String status1, String status2, String conditions, int year) {

        ArrayList<LGADiff> LGADiffs = new ArrayList<>();
        String dataset1 = getDataset(dataset, gender1, status1);
        String dataset2 = getDataset(dataset, gender2, status2);
        String dataJoin = "";
        switch(dataset) {
                    case "School Completion":
                        dataJoin = "SchoolCompletion";
                        break;
                    case "Non-School Completion":
                        dataJoin = "nonSchoolBracket";
                        break;
                    case "Population":
                        dataJoin = "age_min";
                        break;
                    case "Long-Term Health Conditions":
                        dataJoin = "Condition";
                        break;
                }
                
        String sexCond1 = getSexCond(gender1, "d1");
        String statCond1 = getStatCond(status1, "d1");

        String sexCond2 = getSexCond(gender2, "d2");
        String statCond2 = getStatCond(status2, "d2");

        
        

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = 
            "SELECT d1.name, SUM(d1.count) d1_count, SUM(d2.count) d2_count, ABS(100*((1.0*SUM(d2.count) / SUM(d1.count)) -1)) AS gap\r\n" + //
            "FROM " + dataset1 +" d1 JOIN " + dataset2 + " d2\r\n" + //
            "ON (d1.lga_code = d2.lga_code\r\n" + //
            "AND d1." + dataJoin + " = d2." + dataJoin +"\r\n" + //
            "AND d1.lga_year = d2.lga_year)\r\n" + //
            "WHERE d1.lga_year = "+ year +"\r\n" + //
            sexCond1 +" \r\n" + //
            sexCond2 +" \r\n" + //
            statCond1 +" \r\n" + //
            statCond2 +" \r\n" + //
            conditions +"\r\n" + //
            "GROUP BY d1.lga_code\r\n" + //
            "ORDER BY gap DESC;";
            System.out.println("executing" + query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String name  = results.getString("name");
                int d1_count = results.getInt("d1_count");
                int d2_count = results.getInt("d2_count");
                int gap = results.getInt("gap");

                LGADiff lga = new LGADiff(name, d1_count, d2_count, gap);
                LGADiffs.add(lga);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGADiffs;
    }

    public ArrayList<LGADiff> getDiffChange(String dataset, String gender1, String gender2, String status1, String status2, String conditions) {

        ArrayList<LGADiff> LGADiffs = new ArrayList<>();
        String dataset1 = getDataset(dataset, gender1, status1);
        String dataset2 = getDataset(dataset, gender2, status2);
        boolean isSex1 = true;
        boolean isStatus1 = true;
        boolean isSex2 = true;
        boolean isStatus2 = true;

        String dataJoin = "";
        switch(dataset) {
                    case "School Completion":
                        dataJoin = "SchoolCompletion";
                        break;
                    case "Non-School Completion":
                        dataJoin = "nonSchoolBracket";
                        break;
                    case "Population":
                        dataJoin = "age_min";
                        break;
                    case "Long-Term Health Conditions":
                        dataJoin = "Condition";
                        break;
                }
                
        String sexCond1 = getSexCond(gender1, "d1");
        String statCond1 = getStatCond(status1, "d1");
        if(gender1.equals("Both")) {
            isSex1 = false;
        }
        if(status1.equals("Both")) {
            isStatus1 = false;
        }

        String sexCond2 = getSexCond(gender2, "d2");
        String statCond2 = getStatCond(status2, "d2");
        if(gender2.equals("Both")) {
            isSex2 = false;
        }
        if(status2.equals("Both")) {
            isStatus2 = false;
        }

        
        

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = 
            "SELECT d1.name, SUM(d1.count) - SUM(d2.count) AS gap16, SUM(d3.count) - SUM(d4.count) AS gap21, ROUND(\r\n" + //
                    "                                                                                                            ABS(((1.0 *(SUM(d3.count) - SUM(d4.count))\r\n" + //
                    "                                                                                                                 /  \r\n" + //
                    "                                                                                                             (SUM(d1.count) - SUM(d2.count))\r\n" + //
                    "                                                                                                             ) - 1)*100),2\r\n" + //
                    "                                                                                                             ) AS gapChange\r\n" + //
                    "FROM "+ dataset1 +" d1 JOIN " + dataset2 + " d2\r\n" + //
                    "ON (d1.lga_code = d2.lga_code\r\n" + //
                    "AND d1." + dataJoin + " = d2." + dataJoin +"\r\n" + //
                    "AND d1.lga_year = d2.lga_year)\r\n" + //
                    "\r\n" + //
                    "JOIN " + dataset1 + " d3 \r\n" + //
                    "ON (d3.lga_code = d1.lga_code\r\n" + //
                    "AND d1." + dataJoin + " = d3." + dataJoin +"\r\n";
                if (isSex1) {
                    query += "AND d1.sex = d3.sex\r\n";
                }
                if(isStatus1) {
                    query += "AND d1.indigenous_status = d3.indigenous_status\r\n";
                }
                query +=    ")\r\n" + //
                    "JOIN "+ dataset2 + " d4\r\n" + //
                    "ON (d2.lga_code = d4.lga_code\r\n" + //
                    "AND d2." + dataJoin + " = d4." + dataJoin +"\r\n";
                    if (isSex2) {
                    query += "AND d2.sex = d4.sex\r\n";
                }
                if(isStatus2) {
                    query += "AND d2.indigenous_status = d4.indigenous_status\r\n";
                }
                query +=    ")\r\n" + //
                    "WHERE d1.lga_year = 2016\r\n" + //
                    "AND d3.lga_year = 2021\r\n" + //
                    "AND d4.lga_year = 2021\r\n" + //
                    sexCond1 +" \r\n" + //
                    sexCond2 +" \r\n" + //
                    statCond1 +" \r\n" + //
                    statCond2 +" \r\n" + //
                    conditions +"\r\n" + //
                    "GROUP BY d1.lga_code ORDER BY gapChange DESC;";
            System.out.println("executing" + query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String name  = results.getString("name");
                int d1_count = results.getInt("gap16");
                int d2_count = results.getInt("gap21");
                float gap = results.getFloat("gapChange");

                LGADiff lga = new LGADiff(name, d1_count, d2_count, gap);
                LGADiffs.add(lga);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGADiffs;
    }

//level 3 a methods
    public String getDataset(String datasetRaw, String gender, String status) {
        System.out.println("rawdataset:" + datasetRaw);
        System.out.println("rawgender:" + gender);
        System.out.println("rawstatus:" + status);

        
        String dataset = "error";

        if (gender.equals("Both")) {
            if (status.equals("Both")) {
                //both both
                switch(datasetRaw) {
                    case "School Completion":
                        dataset = "Sch_Both_Both";
                        break;
                    case "Non-School Completion":
                        dataset = "NSch_Both_Both";
                        break;
                    case "Population":
                        dataset = "pops_Both_Both";
                        break;
                    case "Long-Term Health Conditions":
                        dataset = "LTHCs_Both_Both";
                        break;
                }
            }
            else {
                //both either
                switch(datasetRaw) {
                    case "School Completion":
                        dataset = "Sch_S_Both";
                        break;
                    case "Non-School Completion":
                        dataset = "NSch_S_Both";
                        break;
                    case "Population":
                        dataset = "pops_S_Both";
                        break;
                    case "Long-Term Health Conditions":
                        dataset = "LTHCs_S_Both";
                        break;
                }
            }
        }
        else if (status.equals("Both")) {
            // either both

            switch(datasetRaw) {
                    case "School Completion":
                        dataset = "Sch_I_Both";
                        break;
                    case "Non-School Completion":
                        dataset = "NSch_I_Both";
                        break;
                    case "Population":
                        dataset = "pops_I_Both";
                        break;
                    case "Long-Term Health Conditions":
                        dataset = "LTHCs_I_Both";
                        break;
                }
        }
        else {
            //either either
            switch(datasetRaw) {
                    case "School Completion":
                        dataset = "Sch_name";
                        break;
                    case "Non-School Completion":
                        dataset = "NSch_name";
                        break;
                    case "Population":
                        dataset = "Pops_name";
                        break;
                    case "Long-Term Health Conditions":
                        dataset = "LTHCs_name";
                        break;
                }
        }
        return dataset;
    }

    public String getSexCond(String gender, String datasetOrder) {
        String conditions = "";
        if (gender.equals("Both")) {
        }
        else {
            conditions = "AND " + datasetOrder +".sex ='" + gender + "'";
        }
        return conditions;
    }

    public String getStatCond(String status, String datasetOrder) {
        String conditions = "";
        if (status.equals("Both")) {
        }
        else {
            conditions = "AND " + datasetOrder +".indigenous_status ='" + status + "'";
        }
        return conditions;
    }
    
    public String getSchoolCompletion( int min, int max) {
        //for quick and easy mapping.
        String schoolCategory[] = {
            "did_not_go_to_school",
            "y8_below",
            "y9_equivalent",
            "y10_equivalent",
            "y11_equivalent",
            "y12_equivalent"
         };

         String conditions = "AND (";
         for (int i = min; i < max + 1; i++) {
            if (i == min) {
                conditions += "d1.SchoolCompletion = '" + schoolCategory[i] + "' ";
            }
            else {
                conditions += "OR d1.SchoolCompletion = '" + schoolCategory[i] + "' ";
            }
            
         }
         conditions += ")";
        return conditions;
    }

    public String getCountJoins(String sex, String status) {
        String conditions = "";
        if (sex.equals("Both") && status.equals("Both")) {

        }
        else if (sex.equals("Both")) {
            conditions = "AND d1.indigenous_status = d2.indigenous_status\r\n";
        }
        else if (status.equals("Both")) {
            conditions = "AND d1.sex = d2.sex\r\n";
        }
        else {
            conditions = "AND d1.indigenous_status = d2.indigenous_status\r\n" + //
                "AND d1.sex = d2.sex";
        }
        return conditions;
    }
    //level 2 methods

    public String getCountGroups (String sex, String status) {
        String conditions = "";
        if (sex.equals("Both") && status.equals("Both")) {

        }
        else if (sex.equals("Both")) {
            conditions = ", d1.indigenous_status";
        }
        else if (status.equals("Both")) {
            conditions = ", d1.sex";
        }
        else {
            conditions = ", d1.indigenous_status, d1.sex";
        }
        return conditions;
    }
    
    public ArrayList<LGACount> getCount(String datasetraw, String gender, String status, String conditions, boolean isstate, boolean proportional, String ordercat, boolean ascending) {

        ArrayList<LGACount> LGACounts = new ArrayList<>();
        String dataset = getDataset(datasetraw, gender, status);
              
        String sexCond = getSexCond(gender, "d1");
        String statCond = getStatCond(status, "d1");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            String query;
            if (proportional) {
                query = "SELECT d1.name, d1.lga_code, ROUND(100 * (1.0*SUM(d1.count) / d2.count),2) AS count\r\n" + //
                "FROM "+ dataset +" d1 JOIN ";
                if (isstate) {
                    query += "state_pops_ALL d2 ON (\r\n" + //
                    "d1.stateCode = d2.stateCode\r\n";
                }
                else {
                    query += "pops_ALL d2 ON (\r\n" + //
                    "d1.lga_code = d2.lga_code\r\n";
                }
                query += "AND d1.lga_year = d2.lga_year)";
            } else {
                query = 
            "SELECT Name, lga_code, SUM(count)\r\n" + //
            " AS count\r\n" + //
            "FROM " + dataset + " d1";
            }
            query +=
            " WHERE d1.lga_year = "+ 2021 +"\r\n" + //
            sexCond +" \r\n" + //
            statCond +" \r\n" + //
            conditions +"\r\n" + //
            "GROUP BY d1.lga_code, d1.lga_year" + getCountGroups(gender, status) + "\r\n" + //
            "ORDER BY " + ordercat;
            if (ascending) {
                query += " ASC;";
            }
            else {
                query += " DESC;";
            }
            System.out.println("executing" + query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String name  = results.getString("Name");
                int code = results.getInt("lga_code");
                float count = results.getFloat("count");

                LGACount lga = new LGACount(name, code, count);
                LGACounts.add(lga);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return LGACounts;
    }


    //

// level 3b methods

    public ArrayList<String> getHealthCondition() {
        ArrayList<String> conditions = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT(condition) AS condition\r\n" + //
                        "FROM LTHCs;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String condition = results.getString("condition");

                // Add the lga object to the array
                conditions.add(condition);
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
        return conditions;
    }

    public String getSchoolCompletion3B( int min, int max) {
        //for quick and easy mapping.
        String schoolCategory[] = {
            "did_not_go_to_school",
            "y8_below",
            "y9_equivalent",
            "y10_equivalent",
            "y11_equivalent",
            "y12_equivalent"
         };

         String conditions = "AND (";
         for (int i = min; i < max + 1; i++) {
            if (i == min) {
                conditions += "SchoolCompletion = '" + schoolCategory[i] + "' ";
            }
            else {
                conditions += "OR SchoolCompletion = '" + schoolCategory[i] + "' ";
            }
            
         }
         conditions += ")";
        return conditions;
    }

    public ArrayList<LGA> getLGAs() {
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
            String query = "SELECT lga_code, lga_year, name, StateCode FROM LGAs\r\n" + //
                           "GROUP BY lga_code, name;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("lga_code");
                String name  = results.getString("name");
                int year = results.getInt("lga_year");

                // Create a LGA Object
                LGA lga = new LGA(code, name, year);

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

    public LGA getLGAbyCode(int code) {
        // Create the ArrayList of LGA objects to return
        LGA lga = new LGA(code, "none", 0000);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga_code, lga_year, name FROM LGAs\r\n" + //
                           "WHERE lga_code = " + code + ";";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int lga_code     = results.getInt("lga_code");
                String name  = results.getString("name");
                int year = results.getInt("lga_year");

                // Create a LGA Object
                lga = new LGA(lga_code, name, year);

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
        return lga;
    }

    public ArrayList<ST3BData> getLGAonSC(int code, String status, String status_condition, int year, String condition, int limit) {
        ArrayList<ST3BData> similarLGAs = new ArrayList<ST3BData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;


        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT *\r\n" + //
                            "FROM Sch_S_Both\r\n" + //
                            "WHERE " + status_condition + "\r\n" + //
                            "      AND lga_year = " + year + "\r\n" + //
                            "      " + condition + "\r\n" + //
                            "ORDER BY ABS(count - (SELECT count \r\n" + //
                            "                      FROM Sch_S_Both \r\n" + //
                            "                      WHERE lga_code = " + code + "\r\n" + //
                            "                      AND " + status_condition + " \r\n" + //
                            "                      AND lga_year = " + year + "\r\n" + //
                            "                      " + condition + "))\r\n" + //
                            "LIMIT " + limit + ";";
            
            // Get Result
            System.out.println(query);
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int lga_code = results.getInt("lga_code");
                int lga_year = results.getInt("lga_year");
                String lga_name = results.getString("name");
                String type = results.getString("SchoolCompletion");
                int count = results.getInt("count");
                
                LGA newLGA = new LGA(lga_code, lga_name, lga_year);
                ST3BData similarLga = new ST3BData(newLGA, type, status, count);
                // Add the lga object to the array
                similarLGAs.add(similarLga);
                System.out.println(similarLGAs.size());
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
        return similarLGAs;
    }

    public ArrayList<ST3BData> getLGAonNSC(int code, String status, String status_condition, int year, String category, int limit) {
        ArrayList<ST3BData> similarLGAs = new ArrayList<ST3BData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;


        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT *\r\n" + //
                            "FROM NSch_S_Both\r\n" + //
                            "WHERE " + status_condition + "\r\n" + //
                            "      AND lga_year = " + year + "\r\n" + //
                            "      AND nonschoolBracket = '" + category + "'\r\n" + //
                            "ORDER BY ABS(count - (SELECT count \r\n" + //
                            "                      FROM NSch_S_Both \r\n" + //
                            "                      WHERE lga_code = " + code + "\r\n" + //
                            "                      AND " + status_condition + " \r\n" + //
                            "                      AND lga_year = " + year + "\r\n" + //
                            "                      AND nonschoolBracket = '" + category + "'))\r\n" + //
                            "LIMIT " + limit + ";";
            
            // Get Result
            System.out.println(query);
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int lga_code = results.getInt("lga_code");
                int lga_year = results.getInt("lga_year");
                String lga_name = results.getString("name");
                String type = results.getString("nonschoolBracket");
                int count = results.getInt("count");
                
                LGA newLGA = new LGA(lga_code, lga_name, lga_year);
                ST3BData similarLga = new ST3BData(newLGA, type, status, count);
                // Add the lga object to the array
                similarLGAs.add(similarLga);
                System.out.println(similarLGAs.size());
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
        return similarLGAs;
    }

    public ArrayList<ST3BData> getLGAonPop(int code, String status, int minAge, int maxAge, int year, int limit) {
        ArrayList<ST3BData> similarLGAs = new ArrayList<ST3BData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;
        String Status_condition = "";
        switch (status) {
            case "'indig'":
                Status_condition = " = 'indig'";
                break;
            case "'non_indig'":
            Status_condition = " = 'non_indig'";
                break;
            case "'indig' OR indigenous_status = 'non_indig'" :
            Status_condition = " != 'indig_stat_not_stated'";
        }

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query =  "SELECT name, lga_code, lga_year, SUM(count)\r\n" + //
                            "FROM Pops_S_Both\r\n" + //
                            "WHERE (indigenous_status " + Status_condition +" \r\n" + //
                            "    AND lga_year = " + year + "\r\n" + //
                            "    AND age_min >= " + minAge + "\r\n" + //
                            "    AND age_max <= " + maxAge + ")\r\n" + //
                            "GROUP BY lga_code, name, lga_year\r\n" + //
                            "ORDER BY ABS(SUM(count) - (SELECT SUM(count)\r\n" + //
                            "                     FROM Pops_S_Both\r\n" + //
                            "                     WHERE (lga_code = " + code + "\r\n" + //
                            "                        AND indigenous_status " + Status_condition + " \r\n" + //
                            "                        AND lga_year = " + year + "\r\n" + //
                            "                        AND age_min >= " + minAge + "\r\n" + //
                            "                        AND age_max <= " + maxAge + ")\r\n" + //
                            "                     GROUP BY lga_code, name, lga_year))\r\n" + //
                            "LIMIT " + limit +";";
            
            // Get Result
            System.out.println(query);
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int lga_code = results.getInt("lga_code");
                int lga_year = results.getInt("lga_year");
                String lga_name = results.getString("name");
                String type = "population of people between " + minAge + " and " + maxAge;
                int count = results.getInt("SUM(count)");
                
                LGA newLGA = new LGA(lga_code, lga_name, lga_year);
                ST3BData similarLga = new ST3BData(newLGA, type, status, count);
                // Add the lga object to the array
                similarLGAs.add(similarLga);
                System.out.println(similarLGAs.size());
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
        return similarLGAs;
    }
    
}



