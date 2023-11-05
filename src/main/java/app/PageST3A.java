package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    private static final String TEMPLATE = ("ST3A.html");

    @Override
    public void handle(Context context) throws Exception {

        //for quick and easy mapping.
        String schoolCategory[] = {
            "did_not_go_to_school",
            "y8_below",
            "y9_equivalent",
            "y10_equivalent",
            "y11_equivalent",
            "y12_equivalent"
         };

        
        JDBCConnection jdbc = new JDBCConnection();

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("title", new String("Change in the gap"));

        ArrayList<String> sexes = new ArrayList<String>();
        sexes.add("Male");
        sexes.add("Female");
        sexes.add("Both");

        model.put("gender_drop1", sexes);
        model.put("gender_drop2", sexes);
        
        ArrayList<String> inds = new ArrayList<String>();
        inds.add("Indigenous");
        inds.add("Non-Indigenous");
        inds.add("Both");

        model.put("indigenous_drop1", inds);
        model.put("indigenous_drop2", inds);

        String gender1 = "m";
        String gender_drop = context.formParam("gender_drop1");
        if (gender_drop == null || gender_drop == "") {
        } else {
            switch(gender_drop) {
                case "Male":
                    gender1 = "m";
                    break;
                case "Female":
                    gender1 = "f";
                    break;
                case "Both":
                    gender1 = "Both";
                    break;
            }
        }

        String gender2 = "m";
        String gender_drop2 = context.formParam("gender_drop2");
        if (gender_drop2 == null || gender_drop2 == "") {
        } else {
            switch(gender_drop2) {
                case "Male":
                    gender2 = "m";
                    break;
                case "Female":
                    gender2 = "f";
                    break;
                case "Both":
                    gender2 = "Both";
                    break;
            }
        }

        String status1 = "indig";
        String status_drop1 = context.formParam("indigenous_drop1");
        if (status_drop1 == null || status_drop1 == "") {
        } else {
            switch(status_drop1) {
                case "Indigenous":
                    status1 = "indig";
                    break;
                case "Non-Indigenous":
                    status1 = "non_indig";
                    break;
                case "Both":
                    status1 = "Both";
                    break;

            }
        }

        String status2 = "indig";
        String status_drop2 = context.formParam("indigenous_drop1");
        if (status_drop2 == null || status_drop2 == "") {
        } else {
            switch(status_drop2) {
                case "Indigenous":
                    status2 = "indig";
                    break;
                case "Non-Indigenous":
                    status2 = "non_indig";
                    break;
                case "Both":
                    status2 = "Both";
                    break;

            }
        }
        ArrayList<String> diffs16 = new ArrayList<>();
        String dataset = "Population";
        String conditions = "";
        String datasets_drop = context.formParam("datasets_drop");
        if (datasets_drop == null || datasets_drop == "") {
            System.out.print("no dataset");

        } else {
            String temp;
            switch(datasets_drop) {
                case "School Completion":
                    dataset = datasets_drop;
                    int schoolMin;
                    int schoolMax;

                    temp = context.formParam("minSchool_drop");
                    System.out.println("temp:" + temp);
                    if (temp == null || temp == ""){
                        System.out.println("no min");
                    }
                    else {
                        schoolMin = Integer.parseInt(temp);
                        System.out.println(schoolMin);
                    }

                    temp = context.formParam("maxSchool_drop");
                    if (temp == null || temp == ""){
                        System.out.println("no max");
                    }
                    else {
                        schoolMax = Integer.parseInt(temp);
                        System.out.println(schoolMax);

                    }

                    //select statement missing as it will be added in the "getDiff method"
                    System.out.print(dataset);
                    break;

                case "Non-School Completion":
                    dataset = datasets_drop;
                                                    System.out.print(dataset);

                    break;
                
                case "Population":
                    dataset = datasets_drop;

                    int ageMin;
                    int ageMax;

                    temp = context.formParam("min");
                    System.out.println("temp:" + temp);
                    if (temp == null || temp == ""){
                        System.out.println("no min");
                    }
                    else {
                        ageMin = Integer.parseInt(temp);
                        conditions += " AND d1.age_min > " + ageMin;
                        System.out.println("ageMin" +ageMin);
                    }

                    temp = context.formParam("max");
                    System.out.println("temp:" + temp);
                    if (temp == null || temp == ""){
                        System.out.println("no max");
                    }
                    else {
                        ageMax = Integer.parseInt(temp);
                        conditions += " AND d1.age_max < " + ageMax;
                        System.out.println("ageMax" + ageMax);

                    }
                    
                                                    System.out.print(dataset);

                    break;
            
            }

            diffs16 = jdbc.getDiff(dataset, gender1, gender2, status1, status2, conditions);
            model.put("diffs16", diffs16);
        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
