package musicanalysis.model.common;

public class Pair<T, U> {

   private T v1;
   private U v2;

   public Pair(final T v1, final U v2) {

      this.v1 = v1;
      this.v2 = v2;
   }

   public T getFirst() {

      return v1;
   }

   public U getSecond() {

      return v2;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Pair<?, ?> pair = (Pair<?, ?>) o;

      if (v1 != null ? !v1.equals(pair.v1) : pair.v1 != null) return false;
      return v2 != null ? v2.equals(pair.v2) : pair.v2 == null;
   }

   @Override
   public int hashCode() {
      int result = v1 != null ? v1.hashCode() : 0;
      result = 31 * result + (v2 != null ? v2.hashCode() : 0);
      return result;
   }
}
