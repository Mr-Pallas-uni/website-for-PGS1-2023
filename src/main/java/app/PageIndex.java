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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    private static final String TEMPLATE = ("index.html");

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("title", new String("Welcome to our site."));

        ArrayList<State> states16 = jdbc.getStates(2016);
        int lgas16 = jdbc.countLGAs(2016);
        int pop16 = jdbc.countPop(2016);

        model.put("pop16", pop16);
        model.put("LGAs16", lgas16);
        model.put("states16", states16);
        

        ArrayList<State> states21 = jdbc.getStates(2021);
        int lgas21 = jdbc.countLGAs(2021);
        int pop21 = jdbc.countPop(2021);

        model.put("pop21", pop21);
        model.put("LGAs21", lgas21);
        model.put("states21", states21);
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
