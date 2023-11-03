package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class LGADiff {;

   // LGA Name
   private String name;

   private int diff16;

   private int diff21;

   private int change1621_perc;

   /**
    * Create an LGA and set the fields
    */
   public LGADiff(String name, int diff16, int diff21, int change1621_perc) {
      this.name = name;
      this.change1621_perc = change1621_perc;
      this.diff16 = diff16;
      this.diff21 = diff21;
   }

}
