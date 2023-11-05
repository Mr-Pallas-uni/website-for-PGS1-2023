package app;

public class ST3BData {
    
   private LGA lga;
   private String type;
   private String status;
   private int data;

   public ST3BData(LGA lga, String type, String status, int data) {
      this.lga = lga;
      this.type = type;
      this.data = data;
      this.status = status;
   }

   public String getLgaName() {
      return lga.getName();
   }

   public int getLgaCode() {
      return lga.getCode();
   }

   public int getLgaYear( ) {
      return lga.getYear();
   }

   public String getDataType() {
      return type;
   }

   public String getStatus() {
      return status;
   }

   public int getData() {
      return data;
   }

}
