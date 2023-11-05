package app;

public class ST3BData {
    
   private LGA lga;
   private String type;
   private int data;

   public ST3BData(LGA lga, String type, int data) {
      this.lga = lga;
      this.type = type;
      this.data = data;
   }

   public String getLgaName() {
      return lga.getName();
   }

   public int getLgaCode() {
      return lga.getCode();
   }

   public String getDataType() {
      return type;
   }

   public int getData() {
      return data;
   }

}
