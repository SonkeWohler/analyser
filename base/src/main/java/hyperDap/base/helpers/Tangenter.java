package hyperDap.base.helpers;

import hyperDap.base.types.value.ValuePair;

/**
 * A helper class that allows finding tangents on data points.
 * 
 * @author soenk
 *
 */

public final class Tangenter {

  private Tangenter() {}

  /**
   * Calculate the tangent between two values, given the distance between them.
   * 
   * @param step The distance between the two xValues
   * @param y1 The first value (with a lower xValue)
   * @param y2 The second value (with the higher xValue)
   * @return The value of the tangent in this point.
   */
  public static double tangentSimple(double step, double y1, double y2) {
    return (y2 - y1) / step;
  }

  /**
   * Calculate the tangent between two x-y data points.
   * 
   * @param v1
   * @param v2
   * @return The value of the tangent between the two points.
   */
  public static double tangentSinmple(ValuePair<? extends Number> v1,
      ValuePair<? extends Number> v2) {
    double x1 = v1.getX().doubleValue();
    double x2 = v2.getX().doubleValue();
    double y1;
    double y2;

    if (x1 < x2) {
      y1 = v1.getY().doubleValue();
      y2 = v2.getY().doubleValue();
    } else {
      y1 = x1;
      x1 = x2;
      x2 = y1;
      y1 = v2.getY().doubleValue();
      y2 = v1.getY().doubleValue();
    }

    return Tangenter.tangentSimple(x2 - x1, y1, y2);
  }

  /**
   * Calculate the tangent between two x-y data points. The order of points does not matter.
   * <p>
   * This implementation calculates the derivative data point, that is its return is an x-y data
   * point of the derivative data set of the original data set that {@code v1} and {@code v2} belong
   * to.
   * 
   * @param v1
   * @param v2
   * @return A {@link ValuePair} of type {@link Double} representing the derivative data point.
   */
  public static ValuePair<Double> tangent(ValuePair<? extends Number> v1,
      ValuePair<? extends Number> v2) {
    return new ValuePair<Double>(Double.valueOf(v1.getX().doubleValue()),
        Tangenter.tangentSinmple(v1, v2));
  }



}