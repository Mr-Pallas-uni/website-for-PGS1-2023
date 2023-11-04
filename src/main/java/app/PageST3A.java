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
        
        ArrayList<String> inds = new ArrayList<String>();
        inds.add("Indigenous");
        inds.add("Non-Indigenous");
        inds.add("Both");

        model.put("indigenous_drop1", inds);

        String gender = "Male";
        String gender_drop = context.formParam("gender_drop1");
        if (gender_drop == null || gender_drop == "") {
        } else {
            switch(gender_drop) {
                case "Male":
                    gender = "m";
                    break;
                case "Female":
                    gender = "f";
                    break;
                case "Both":
                    gender = "Both";
                    break;
            }
        }

        String status = "indig";
        String status_drop = context.formParam("indigenous_drop1");
        if (status_drop == null || status_drop == "") {
        } else {
            switch(status_drop) {
                case "Indigenous":
                    status = "indig";
                    break;
                case "Non-Indigenous":
                    status = "non_indig";
                    break;
                case "Both":
                    status = "Both";
                    break;

            }
        }

        String dataset = "Population";
        String conditions = "";
        String datasets_drop = context.formParam("datasets_drop");
        if (datasets_drop == null || datasets_drop == "") {
            System.out.print("lol");

        } else {
            switch(datasets_drop) {
                case "School Completion":
                    dataset = "";
                    int schoolMin = Integer.parseInt(context.formParam("minSchool_drop"));
                    int schoolMax = Integer.parseInt(context.formParam("maxSchool_drop"));

                                    //select statement missing as it will be added in the "getDiff method"

                    break;

                case "Non-School Completion":
                    dataset = "NonSchoolCompletion";
                    break;
                
                case "Population":
                    dataset = "PopChange";

                    conditions = " WHERE sex = '" + gender +"' AND age_min > " + 6 + " AND age_max < " + 50 + ";";
                    break;
            }

            jdbc.getDiff(dataset, conditions);
            
            


        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
