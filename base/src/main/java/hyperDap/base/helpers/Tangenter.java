package hyperDap.base.helpers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import hyperDap.base.types.dataSet.ValueDataSet;
import hyperDap.base.types.value.ValuePair;

/**
 * A helper class that allows finding tangents on data points.
 * 
 * @author soenk
 *
 */

public final class Tangenter {

  private static int bigDecimalPrecision = 10;
  private static MathContext standardContext =
      new MathContext(bigDecimalPrecision, RoundingMode.HALF_UP);

  /**
   * Private constructor to prevent implementing this class.
   */
  private Tangenter() {
    throw new AssertionError("No helper class instances for anyone!");
  }

  /**
   * An exact implementation of {@link #tangentSimple(double, double, double)} to calculate the
   * tangent between two points, making use of {@link BigDecimal}.
   * 
   * @param step The difference in {@code xValue} between the values.
   * @param y1 The first of the values with a lower {@code xValue}.
   * @param y2 The second of the values with the higher {@code xValue}
   * @return The slope of the tangent between the points.
   */
  public static double tangentExact(double step, double y1, double y2) {
    BigDecimal val1 = new BigDecimal(y1, standardContext);
    BigDecimal val2 = new BigDecimal(y2, standardContext);
    val1 = val2.subtract(val1);
    val2 = new BigDecimal(step, standardContext);
    val1 = val1.divide(val2, standardContext);
    return val1.doubleValue();
  }

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

  /**
   * Calls {@link #calcDerivDepth(ValueDataSet, 10)}.
   * 
   * @param dataset
   * @return
   */
  public static ArrayList<Integer> calcDerivDepth(ValueDataSet<? extends Number> dataset) {
    return Tangenter.calcDerivDepth(dataset, 10);
  }

  /**
   * Calculates and returns the depth of derivative ({@code derivDepth}) for {@code dataset}.
   * <p>
   * The {@code derivDepth} is the number of times a trace derivative (the tangent between two
   * points, see {@link #tangentSimple(double, double, double)}) is NOT zero. For each point this
   * indicates the degree of the polynomial the data is representing, if it is polynomial. If not
   * the {@code derivDepth} will be assigned {@link Integer#MAX_VALUE} to represent infinity. This
   * will also be assigned if the derivDepth would be larger than {@code maxDepth - 1}.
   * 
   * @param dataset The {@link ValueDataSSet} that is to be analysed.
   * @param maxDepth The maximum depth to which the derivative should be calculated.
   *        {@code derivDepth} larger than this will be assigned {@link Integer#MAX_VALUE},
   *        representing infinity.
   * @return An {@link ArrayList ArrayList<Integer>} of the {@code derivDepth} for each value of
   *         {@code dataset} up to its {@code size - maxDepth}.
   */
  public static ArrayList<Integer> calcDerivDepth(ValueDataSet<? extends Number> dataset,
      int maxDepth) {
    int size = dataset.size();
    ArrayList<Integer> depths = new ArrayList<Integer>(size);
    double[][] derivs = new double[size][maxDepth];
    // calculate trace by trace derivatives
    calcDerivs(derivs, dataset);
    // count derivDepth
    countDerivDepths(derivs, depths);
    // detect and mark points of change
    detectDepthChanges_1(derivs, depths);
    // finished
    return depths;
  }

  private static void calcDerivs(double[][] derivs, ValueDataSet<? extends Number> set) {
    int size = derivs.length;
    int maxDepth = derivs[0].length;
    double step = set.getStep();
    int X; // this variable helps ensure that tangents are calculated left to right on the x-axis
    if (step > 0) {
      X = 0;
    } else {
      X = size - 1;
    }
    derivs[0][0] = set.getByIndex(X).doubleValue();
    for (int k = 1; k < size - 1; k++) {
      derivs[k][0] = set.getByIndex(Math.abs(X - k)).doubleValue();
      for (int i = k - 1, j = 1; i >= 0 && j < maxDepth; i--, j++) {
        derivs[i][j] = tangentExact(step, derivs[i][j - 1], derivs[i + 1][j - 1]);
      }
    }
  }

  private static void countDerivDepths(double[][] derivs, ArrayList<Integer> depths) {
    int maxDepth = derivs[0].length;
    int size = derivs.length;
    int depth;
    for (int i = 0; i < size; i++) {
      depth = Integer.MAX_VALUE;
      for (int j = 1; j < maxDepth; j++) {
        if (derivs[i][j] == 0) {
          depth = j - 1;
          break;
        }
      }
      depths.add(depth);
    }
  }

  private static void detectDepthChanges_1(double[][] derivs, ArrayList<Integer> depths) {
    int maxDepth = derivs[0].length;
    int size = derivs.length;
    boolean tracking = false;
    int depth;
    int depthTemp = 0;
    for (int i = 0; i < size; i++) {
      depth = depths.get(i);
      if (tracking == true) {
        if (depth != -1) {
          depths.set(i, depthTemp);
        }
      } else {
        if (derivs[i][maxDepth - 1] != 0 && depth < maxDepth - 1) {
          depthTemp = depth;
          depths.set(i + maxDepth - 2, -1);
          tracking = true;
        } else {
          // tracking = false;
        }
      }
      if (depth == -1) {
        tracking = false;
      }
    }
  }

  private static void detectDepthChanges_2(double[][] derivs, ArrayList<Integer> deppths) {

  }

}
