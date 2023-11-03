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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    private static final String TEMPLATE = ("mission.html");

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("title", new String("Our Mission"));

        ArrayList<Persona> personas = jdbc.getPersona();
        model.put("persona_1", personas.get(0));
        model.put("persona_2", personas.get(1));
        model.put("persona_3", personas.get(2));

        ArrayList<String> attr_1 = jdbc.getPersonaAttribute(personas.get(0).getName(), "attributes");
        model.put("attr_1", attr_1);
        ArrayList<String> need_1 = jdbc.getPersonaAttribute(personas.get(0).getName(), "needs");
        model.put("need_1", need_1);
        ArrayList<String> goal_1 = jdbc.getPersonaAttribute(personas.get(0).getName(), "goals");
        model.put("goal_1", goal_1);
        ArrayList<String> skill_1 = jdbc.getPersonaAttribute(personas.get(0).getName(), "skills");
        model.put("skill_1", skill_1);

        ArrayList<String> attr_2 = jdbc.getPersonaAttribute(personas.get(1).getName(), "attributes");
        model.put("attr_2", attr_2);
        ArrayList<String> need_2 = jdbc.getPersonaAttribute(personas.get(1).getName(), "needs");
        model.put("need_2", need_2);
        ArrayList<String> goal_2 = jdbc.getPersonaAttribute(personas.get(1).getName(), "goals");
        model.put("goal_2", goal_2);
        ArrayList<String> skill_2 = jdbc.getPersonaAttribute(personas.get(1).getName(), "skills");
        model.put("skill_2", skill_2);

        ArrayList<String> attr_3 = jdbc.getPersonaAttribute(personas.get(2).getName(), "attributes");
        model.put("attr_3", attr_3);
        ArrayList<String> need_3 = jdbc.getPersonaAttribute(personas.get(2).getName(), "needs");
        model.put("need_3", need_3);
        ArrayList<String> goal_3 = jdbc.getPersonaAttribute(personas.get(2).getName(), "goals");
        model.put("goal_3", goal_3);
        ArrayList<String> skill_3 = jdbc.getPersonaAttribute(personas.get(2).getName(), "skills");
        model.put("skill_3", skill_3);
        
        Map<String, String> team = jdbc.getTeam();
        model.put("team", team);
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }
}
