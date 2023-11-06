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

   private int count1;

   private int count2;

   private float difference;

   /**
    * Create an LGA and set the fields
    */
   public LGADiff(String name, int count1, int count2, float diff) {
      this.name = name;
      this.count1 = count1;
      this.count2 = count2;
      this.difference = diff;
   }

   public int getCount1() {
       return count1;
   }
   public int getCount2() {
       return count2;
   }
   public String getName() {
       return name;
   }
   public float getDifference() {
       return difference;
   }

}
