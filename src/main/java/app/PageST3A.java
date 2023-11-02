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

        JDBCConnection jdbc = new JDBCConnection();

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("title", new String("Change in the gap"));

        String[] datasets = {"School Completion", "Non-School Completion", "Population"};
        model.put("datasets_drop", datasets);

        ArrayList<String> sexes = jdbc.getGender();
        model.put("gender_drop", sexes);
        
        ArrayList<String> inds = jdbc.getIndigenous();
        model.put("indigenous_drop", inds);
        
        String dataset = "Population";
        String datasets_drop = context.formParam("datasets_drop");
        if (datasets_drop == null || datasets_drop == "") {
            model.put("add_form_data", 0);

        } else {
            switch(datasets_drop) {
                case "School Completion":
                dataset = "School_Completion";

                case "Non-School Completion":
                dataset = "NonSchoolCompletion";
                
                case "Population":
                dataset = "Population";
            }

        }

        String gender = "f";
        String gender_drop = context.formParam("gender_drop");
        if (gender_drop == null || gender_drop == "") {
        } else {
            gender = gender_drop;
        }

        String status = "ind";
        String status_drop = context.formParam("status_drop");
        if (status_drop == null || status_drop == "") {
        } else {
            status = status_drop;
        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
