package musicanalysis.model.data;

public class POSTag {

   private String tag;
   private Integer amount;
   private Float ratio;

   public String getTag() {

      return tag;
   }

   public void setTag(String name) {

      this.tag = name;
   }

   public Float getRatio() {

      return ratio;
   }

   public void setRatio(Float ratio) {

      this.ratio = ratio;
   }

   public Integer getAmount() {
      return amount;
   }

   public void setAmount(Integer amount) {
      this.amount = amount;
   }
}
