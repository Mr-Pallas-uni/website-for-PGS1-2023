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
public class PageST2 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2.html";

    private static final String TEMPLATE = ("ST2.html");

    @Override
    public void handle(Context context) throws Exception {
        
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
        
        ArrayList<String> inds = new ArrayList<String>();
        inds.add("Indigenous");
        inds.add("Non-Indigenous");
        inds.add("Both");

        model.put("indigenous_drop1", inds);

        ArrayList<String> healthConds = jdbc.getHealthCondition();
        model.put("health_conditions", healthConds);

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

        String status1 = "indig";
        String status_drop = context.formParam("indigenous_drop1");
        if (status_drop == null || status_drop == "") {
        } else {
            switch(status_drop) {
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
        String sortCategory = "count";
        String sortCategoryRaw = context.formParam("cat");
        if (sortCategoryRaw == null || sortCategoryRaw == "") {
        } else {
            switch(sortCategoryRaw) {
                case "name":
                    sortCategory = "d1.name";
                    break;
                case "code":
                    sortCategory = "d1.lga_code";
                    break;
                case "count":
                    sortCategory = "count";
                    break;
            }
        }

        Boolean ASC = false;
        String ASCDESC = context.formParam("ASCDESC");
        if (ASCDESC == null || ASCDESC == "") {
        } else {
            switch(ASCDESC) {
                case "ASC":
                    ASC = true;
                    break;
                case "DESC":
                    ASC = false;;
                    break;
                case "count":
                    sortCategory = "count";
                    break;

            }
        }

        Boolean isState = false;
        String isStateraw = context.formParam("stateLGA");
        if (isStateraw == null || isStateraw == "") {
        } else {
            switch(isStateraw) {
                case "States":
                    isState = true;
                    break;
                case "LGAs":
                    isState = false;
                    break;
            }
        }

        Boolean isProp = false;
        String isPropRaw = context.formParam("Prop");
        System.out.print("prop raw: " + isPropRaw);
        if (isPropRaw == null || isPropRaw == "") {
            model.put("proportional", new String (""));
        } else {
            if (isPropRaw.equals("proportional")) {
                isProp = true;
                 model.put("proportional", new String (" (%)"));
            }
        }

        String datasets_drop = context.formParam("datasets_drop");
        if (datasets_drop == null || datasets_drop == "") {
            System.out.print("no dataset");

        } else {
            String temp = "";
            boolean first = true;
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
                    first = true;
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
                case "Long-Term Health Conditions":
                    dataset = datasets_drop;
                    first = true;
                    conditions += "AND (";
                    for (int i = 0; i < healthConds.size(); i++) {
                    temp = context.formParam(healthConds.get(i));
                        if (temp == null || temp == ""){
                            System.out.println("no " + healthConds.get(i));
                        }
                        else {
                            if (first) {
                                conditions += "d1.condition ='" + temp + "'";
                                first = false;
                            }
                            else {
                                conditions += " OR d1.condition ='" + temp +"'";   
                            }
                        }
                    }
                    
                    conditions += ")";
                                                System.out.print(dataset);

                break;
            
            }
            ArrayList<LGACount> data21 = jdbc.getCount(dataset, gender1, status1, conditions, isState, isProp, sortCategory, ASC);
            String userSelected ="you selected: " + dataset + "," + gender1 +  ", " + status1 + ", Where " + conditions;
            model.put("userSelected", userSelected);
            //model.put("data16", data16);
            model.put("data21", data21);

        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
