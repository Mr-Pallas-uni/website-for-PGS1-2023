package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

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

        ArrayList<String> diffs16 = new ArrayList<>();
        ArrayList<String> diffs21 = new ArrayList<>();
        String dataset = "Population";
        String conditions = "";
        
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
        String status_drop2 = context.formParam("indigenous_drop2");
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

        
        String datasets_drop = context.formParam("datasets_drop");
        if (datasets_drop == null || datasets_drop == "") {
            System.out.print("no dataset");

        } else {
            String temp;
            switch(datasets_drop) {
                case "School Completion":
                    dataset = datasets_drop;
                    int schoolMin = 0;
                    int schoolMax = 0;

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
                    conditions = jdbc.getSchoolCompletion(schoolMin, schoolMax);
                    //select statement missing as it will be added in the "getDiff method"
                    System.out.print(dataset);
                    break;

                case "Non-School Completion":
                    dataset = datasets_drop;
                    boolean first = true;
                    conditions += "AND (";
                    temp = context.formParam("degree1");
                    System.out.println("temp:" + temp);
                    if (temp == null || temp == ""){
                        System.out.println("no degree1");
                    }
                    else {
                        if (first) {
                            conditions += "d1.nonSchoolBracket ='" + temp + "'";
                            first = false;
                        }
                        else {
                         conditions += " OR d1.nonSchoolBracket ='" + temp +"'";   
                        }
                    }

                    temp = context.formParam("degree2");
                    if (temp == null || temp == ""){
                        System.out.println("no degree2");
                    }
                    else {
                        if (first) {
                            conditions += "d1.nonSchoolBracket ='" + temp + "'";
                            first = false;
                        }
                        else {
                         conditions += " OR d1.nonSchoolBracket ='" + temp +"'";   
                        }

                    }

                    temp = context.formParam("degree3");
                    System.out.println("temp:" + temp);
                    if (temp == null || temp == ""){
                        System.out.println("no degree3");
                    }
                    else {
                        if (first) {
                            conditions += "d1.nonSchoolBracket ='" + temp + "'";
                            first = false;
                        }
                        else {
                         conditions += " OR d1.nonSchoolBracket ='" + temp +"'";   
                        }
                    }

                    temp = context.formParam("degree4");
                    if (temp == null || temp == ""){
                        System.out.println("no degree4");
                    }
                    else {
                        if (first) {
                            conditions += "d1.nonSchoolBracket ='" + temp + "'";
                            first = false;
                        }
                        else {
                         conditions += " OR d1.nonSchoolBracket ='" + temp +"'";   
                        }

                    }
                    temp = context.formParam("degree5");
                    if (temp == null || temp == ""){
                        System.out.println("no degree5");
                    }
                    else {
                        if (first) {
                            conditions += "d1.nonSchoolBracket ='" + temp + "'";
                            first = false;
                        }
                        else {
                         conditions += " OR d1.nonSchoolBracket ='" + temp +"'";   
                        }

                    }
                    conditions += ")";
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

            diffs16 = jdbc.getDiffChange(dataset, gender1, gender2, status1, status2, conditions);
            //diffs21 = jdbc.getDiff(dataset, gender1, gender2, status1, status2, conditions, 2021);
            model.put("diffs16", diffs16);
            //model.put("diffs21", diffs16);

        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
