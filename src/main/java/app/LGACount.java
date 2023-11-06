package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class LGACount {
   // LGA Code
   private int code;

   // LGA Name
   private String name;

   // LGA Year
   private float population;

   /**
    * Create an LGA and set the fields
    */
   public LGACount(String name, int code, float population) {
      this.code = code;
      this.name = name;
      this.population = population;
   }

   public int getCode() {
      return code;
   }

   public String getName() {
      return name;
   }

   public float getPop() {
      return population;
   }

}
