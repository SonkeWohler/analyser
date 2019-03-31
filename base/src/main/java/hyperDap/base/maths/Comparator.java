package hyperDap.base.maths;

public abstract class Comparator {

  /**
   * Evaluate whether with the chosen precision the two values are equal, i.e. if {@code a} plus or
   * minus {@code precision} contains {@code b}.
   * 
   * @param a
   * @param b
   * @param precision
   * @return
   */
  public static boolean equalApprox(double a, double b, double precision) {
    if (b < (a - precision)) {
      return false;
    }
    if (b > (a + precision)) {
      return false;
    }
    return true;
  }

  /**
   * An encapsulation of {@link #equalApprox(double, double, double)} making use of
   * {@link Number#doubleValue()}.
   * 
   * @param a
   * @param b
   * @param precision
   * @return
   */
  public static boolean equalApprox(Number a, Number b, Number precision) {
    return Comparator.equalApprox(a.doubleValue(), b.doubleValue(), precision.doubleValue());
  }

}
