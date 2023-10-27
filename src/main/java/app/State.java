package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class State {

   // LGA Name
   private String name;

   // LGA Year
   private int year;

   private int population;

   /**
    * Create an LGA and set the fields
    */
   public State(String name, int population, int year) {
      this.name = name;
      this.year = year;
      this.population = population;
   }

   public int getPop() {
      return population;
   }

   public String getName() {
      return name;
   }

   public int getYear() {
      return year;
   }

}
