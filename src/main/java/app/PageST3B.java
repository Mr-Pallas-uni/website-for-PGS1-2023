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
public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    private static final String TEMPLATE = ("ST3B.html");

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        Map<String, Object> model = new HashMap<String, Object>();

        //Pass data to template
        model.put("title", new String("Finding similar LGAs"));

        ArrayList<String> conditions = jdbc.getHealthCondition();
        model.put("health_conditions", conditions);

        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Postgraduate Degree, Graduate Diploma, and Graduate Certificate Level");
        categories.add("Bachelors degree level");
        categories.add("Advanced diploma and diploma level");
        categories.add("certificate i & ii level");
        categories.add("certificate iii & iv level");
        model.put("completion_categories", categories);

        ArrayList<LGA> lgas = jdbc.getLGAs();
        model.put("lgas", lgas);
        
        //Handle lga input
        int lga_code = 0;
        if (context.formParam("lga-field-drop") != null) {
            lga_code = Integer.parseInt(context.formParam("lga-field-drop"));
        }

        //Handle lga input
        int limit = 5;
        if (context.formParam("limit") != null) {
            limit = Integer.parseInt(context.formParam("limit"));
        }

        //Handle status input
        String indig_status = "indig";
        String indig_status_condition = "";
        String indig_status_selected = context.formParam("indig-status-drop");
        if (indig_status_selected != null) {
            indig_status = indig_status_selected;
            indig_status_condition = "(indigenous_status = " + indig_status + ")";
        }

        //Handle Census year input
        int census_year_selected = 2016;
        if (context.formParam("census-year-drop") != null) {
            census_year_selected = Integer.parseInt(context.formParam("census-year-drop"));
        }

        
        //Handle population
        int min_age_selected = 0;
        if (context.formParam("min-age") != null){
            min_age_selected = Integer.parseInt(context.formParam("min-age"));
        }
        int max_age_selected = 0;
        if (context.formParam("min-age") != null){
            max_age_selected = Integer.parseInt(context.formParam("min-age"));
        }

        //Handle School completion
        int lowest_level = 0;
        int highest_level = 5;
        String condition = "";
        if(context.formParam("maxSchool_drop") != null) {
            highest_level = Integer.parseInt(context.formParam("maxSchool_drop"));
            lowest_level = Integer.parseInt(context.formParam("minSchool_drop"));
            condition = jdbc.getSchoolCompletion3B(lowest_level, highest_level);
        }

        ArrayList<ST3BData> data = new ArrayList<ST3BData>();
        String filters = "Data displayed based on filters : ";
        if (context.formParam("maxSchool_drop") == null) {
            data = new ArrayList<ST3BData>();
            filters = "No data Found on those filters";
        } else {
            data = jdbc.getLGAonSC(lga_code, indig_status, indig_status_condition, census_year_selected, condition, limit);
            filters += "Status: " + indig_status +", "+ 
                       "Census year: " + census_year_selected +", "+ 
                       "LGA : " + data.get(0).getLgaName() +", "+ 
                       "data on Top " + limit + " most similar LGA" ;
        }
        model.put("sc_data", data);
        model.put("filters", filters);

        //Handle non School completion
        // String category_selected = "bd";
        // if(context.formParam("non-school-completion-drop") != null) {
        //     category_selected = convertToNonSchoolCode(context.formParam("non-school-completion-drop"));
        // }

        // ArrayList<ST3BData> data_nsc = new ArrayList<ST3BData>();
        // String filters_nsc = "Data displayed based on filters : ";
        // if (context.formParam("non-school-completion-drop") == null) {
        //     data_nsc = new ArrayList<ST3BData>();
        //     filters_nsc = "No data Found on those filters";
        // } else {
        //     data_nsc = jdbc.getLGAonNSC(lga_code, indig_status, indig_status_condition, census_year_selected, category_selected, limit);
        //     filters_nsc += "Status: " + indig_status +", "+ 
        //                "Census year: " + census_year_selected +", "+ 
        //                "LGA : " + data_nsc.get(0).getLgaName() +", "+ 
        //                "data on Top " + limit + " most similar LGA" ;
        // }
        // model.put("data_nsc", data_nsc);
        // model.put("filters_nsc", filters_nsc);
        
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

    static String convertToNonSchoolCode(String category) {
        String result = new String();
        switch (category) {
            case "Postgraduate Degree, Graduate Diploma, and Graduate Certificate Level":
                result = "pd_gd_gc";
                break;
            case "Bachelors degree level":
                result = "bd";
                break;
            case "Advanced diploma and diploma level":
                result = "adip_dip";
                break;
            case "certificate i & ii level":
                result = "ct_i_ii";
                break;
            case "certificate iii & iv level":
                result = "ct_iii_iv";
                break;
            default:
            result = "error";
                break;
        }
        return result;
    }
}
