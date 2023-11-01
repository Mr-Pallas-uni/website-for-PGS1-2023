package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonaProcessor {

   // MODIFY these to load/store to/from the correct locations
   private static final String DATABASE = "jdbc:sqlite:database/vtp.db";
   //helper method to insert persona attributes
   static void processAttributes(String name, String[] attr, String[] needs, String[] goals, String[] skills, Statement statement) {
      try {
         for (int i = 0; i < attr.length; i++) {
            String query = "INSERT into PersonaAttributes VALUES ("
                                    + "'" + name + "',"
                                    + "'attributes',"
                                    + "'" + attr[i] + "'"
                                    + ")";
            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

         for (int i = 0; i < needs.length; i++) {
            String query = "INSERT into PersonaAttributes VALUES ("
                                    + "'" + name + "',"
                                    + "'needs',"
                                    + "'" + needs[i] + "'"
                                    + ")";
            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

         for (int i = 0; i < goals.length; i++) {
            String query = "INSERT into PersonaAttributes VALUES ("
                                    + "'" + name + "',"
                                    + "'goals',"
                                    + "'" + goals[i] + "'"
                                    + ")";
            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

         for (int i = 0; i < skills.length; i++) {
            String query = "INSERT into PersonaAttributes VALUES ("
                                    + "'" + name + "',"
                                    + "'needs',"
                                    + "'" + skills[i] + "'"
                                    + ")";
            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main (String[] args) {
      
      //Personae and attributes

      String PerName1 = "Abed Muhammed";
      String PerImagePath1 = "src/main/resources/images/Persona_1_image.jpg";
      String PerImageCredit1 = "Photo by bruce mars on Unsplash";
      String PerAttr1[] = {
         "32 yrs old, male",
         "lives in Mildura in the state of Victoria",
         "married with 3 children (under 18)",
         "works as an assistant manager in local farm",
         "a former migrant who recently received his Australian Citizenship",
         "Critical thinker, sceptical of biases in information",
      };
      String PerNeeds1[] = {
         "An outline of the data presented",
         "What topics the website covers ",
         "How the website works",
         "The number of people the website has data of"
      };
      String PerGoals1[] = {
         "To gain an understanding of the gap between Indigenous and Non-Indigenous in order to decide his vote in the upcoming referendum",
         "Find comprehensive, unbiased information on the gap",
      };
//       proficient with browsing the internet on his laptop. 

      String PerSkills1[] = {
         "proficient with browsing the internet on his laptop",
         "Has limited knowledge of analysing data but has used simple graphs, tables and charts of monthly revenues in his job as an assistant manager",
      };

      String PerName2 = "Daniela Becker";
      String PerImagePath2 = "src/main/resources/images/Persona_2_image.jpg";
      String PerAttr2[] = {
         """
         Daniela is a 46-year-old customer sales representative for a pharmaceuticals company in Sydney.
         Ever since she was young, Daniela has enjoyed helping others;
         in high school she would help teachers and other students where she could.
         in university, she began tutoring the struggling students in her finance course,
         one of which is now her husband. Daniela's 3 kids mean everything to her,
         and she consider herself very fortunate to have been able to give her children the lives they have.
         With the referendum looming, Daniela has decided to learn a little bit more about the indigenous population
         where she currently lives, Sydney, and where she was born, Dubbo,
         and how these places compare to the rest of Australia.
         Her youngest child has asthma and her oldest has an engineering doctorate, 
         so she wants to find some statistics on these things as well as herself. 
         """,
         "46 years old female",
         "born in Dubbo; NSW - moved to Sydney",
         "Married, 3 children (24, 20, 16)",
         "customer service representative",
         "likes to help people",
         "open minded",
         "a little bit of an early adopter",
      };
      String PerNeeds2[] = {
         "Raw and proportional values for the amount of indigenous people with asthma in Sydney, Dubbo and NSW.",
         "Ranking of the health of indigenous people in Sydney, Dubbo and NSW",
         "Percentage of Sydney's Population aged 45-50 ",
         "Raw and proportional values for the level of year 12 or equivalent schooling completion in Sydney and NSW",
         "Ranking of the level of year 12 or equivalent school completion of indigenous people of Sydney and ranking of NSW within Australia",
         "Percentage of indigenous people that get doctorates",
      };
      String PerGoals2[] = {
         "Compare the health of the Sydney and Dubbo indigenous populations to her health and to each other",
         "Compare the overall health of NSW to herself",
         "Check the proportion of school completion for indigenous Australians in Sydney ",
         "See how NSW compares to the rest of Australia in terms of education",
      };
      //, . 
      String PerSkills2[] = {
         "Strong spreadsheet skills from her financial work",
         "some understanding of medical terminology from working for a pharmaceuticals company",
         "basic indigenous knowledge from regularly reading news articles on the web and life experience",
      };
      String PerName3 = "Ollie Ramsey Jr";
      String PerImagePath3 = "src/main/resources/images/Persona_3_image.jpg";
      String PerImageCredit3 = "Photo by Andrea Piacquadio: https://www.pexels.com/photo/photo-of-man-holding-a-book-927022/";
      String PerAttr3[] = {
         "25 years old, male",
         "Born in Western Australia, moved to Melbourne to study data science, moved back home after, now lives in Armadale Western Australia",
         "Single",
         "Recently got a junior position as a data analyst for a Data Analytics and Business Intelligence Firm based in Perth, WA",
         "recently graduated from RMIT with a Bachelor of Data Science",
      }; 
      String PerNeeds3[] = {
         "a website that provides a user friendly and robust website that presents information in an unbiased manner",
         "the website needs to be fast, complete and abide by the common patterns used in data driven sites",
         "A feature that allows him to choose a category of data (dataset) and see the gap between indigenous and non- indigenous Australian from 2016 or 2021 datasets in all the LGAs",
         "He would like to able to filter the data on many categories and the site should be able to compute the improvements and declines for each LGA from 2016 to 2021 and clearly highlight LGAs with the most significant changes",
         "As part of his assignment, he would also need to be able to select a certain LGA and find a number of similar LGAs based on a category of data (e.g. health). It would be helpful if the search result was presented from most similar to least similar LGA",
      };
      String PerGoals3[] = {
         "As part of his first work project, he needs to deeply explore the gap between indigenous and non-indigenous Australians and presents his findings in a company meeting",
         "Produce a report on the gap in Perth across multiple categories, comparing it to the best and worst of each category",
      };
      String PerSkills3[] = {
         "He has used simple and complex data analyses tools and are comfortable with using graphs/charts and data tables",
         "As part of his data science degree he has used many types of websites and likes to be able to quickly figure out how a site works and get his work done",
      };
      
      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         System.out.println("Inserting persona and Attribute");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         Statement statement = connection.createStatement();

         String query1 = "INSERT into Personas VALUES ("
                              + "'" + PerName1 + "',"
                              + "'" + PerImagePath1 + "',"
                              + "'" + PerImageCredit1 + "',"
                              + ")";
         
                              // Execute the INSERT
         System.out.println("Executing: " + query1);
         statement.execute(query1);

         String query2 = "INSERT into Personas VALUES ("
                              + "'" + PerName2 + "',"
                              + "'" + PerImagePath2 + "',"
                              + ")";

         System.out.println("Executing: " + query2);
         statement.execute(query2);      
         
         String query3 = "INSERT into Personas VALUES ("
                              + "'" + PerName3 + "',"
                              + "'" + PerImagePath3 + "',"
                              + "'" + PerImageCredit3 + "',"
                              + ")";
         
                              // Execute the INSERT
         System.out.println("Executing: " + query3);
         statement.execute(query3);

      //Inserting Persona Attributes
         processAttributes(PerName1, PerAttr1, PerNeeds1, PerGoals1, PerSkills1, statement);
         processAttributes(PerName2, PerAttr2, PerNeeds2, PerGoals2, PerSkills2, statement);
         processAttributes(PerName3, PerAttr3, PerNeeds3, PerGoals3, PerSkills3, statement);
      }
       catch (Exception e) {
         e.printStackTrace();
      }      
   }
}
