package app;

public class Persona {

   // Persona Name
   private String name;

   // LGA Year
   private String image;

   private String imageCredit;

   /**
    * Create a Persona and set the fields
    */
   public Persona(String name, String image, String imageCredit) {
      this.name = name;
      this.image = image;
      this.imageCredit = imageCredit;
   }

   public String getImage() {
      return image;
   }

   public String getName() {
      return name;
   }

   public String getCredit() {
      return imageCredit;
   }

}
