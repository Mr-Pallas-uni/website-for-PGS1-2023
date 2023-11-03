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

        ArrayList<String> sexes = jdbc.getGender();
        model.put("gender_drop", sexes);
        
        ArrayList<String> inds = jdbc.getIndigenous();
        model.put("indigenous_drop", inds);

        String gender = "f";
        String gender_drop = context.formParam("gender_drop");
        if (gender_drop == null || gender_drop == "") {
        } else {
            gender = gender_drop;
        }

        String status = "indig";
        String status_drop = context.formParam("indigenous_drop");
        if (status_drop == null || status_drop == "") {
        } else {
            status = status_drop;
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
