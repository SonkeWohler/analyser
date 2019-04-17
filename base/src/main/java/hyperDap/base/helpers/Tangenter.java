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

  private static double precision = 0.001;
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
   * Used to adjust the precision of {@link #tangentApprox(double, double, double)}, which by
   * default is set to 0.001.
   * 
   * @param precision The new precision used.
   */
  public static void setPrecision(double precision) {
    Tangenter.precision = precision;
  }

  /**
   * Gives the value of the precision currently used in
   * {@link #tangentApprox(double, double, double)}.
   * 
   * @return The current value of precision.
   */
  public static double getPrecision() {
    return precision;
  }

  /**
   * Calculates the slope of the tangent between two points with {@code yValues} {@code y1} and
   * {@code y2} that are a distance {@code step} apart in their {@code xValues}. If the two values
   * are too close, based on {@link Comparator#equalApprox(double, double, double)}, the slope will
   * be approximated to zero. The precision argument for
   * {@link Comparator#equalApprox(double, double, double)} can be adjusted in
   * {@link #setPrecision(double)}.
   * 
   * @param step The difference in {@code xValue} between the values.
   * @param y1 The first of the values with a lower {@code xValue}.
   * @param y2 The second of the values with the higher {@code xValue}
   * @return Zero if {@code y1} and {@code y2} are equal within the set precision, the slope of the
   *         tangent between them otherwise.
   */
  public static double tangentApprox(double step, double y1, double y2) {
    if (Comparator.equalApprox(y1, y2, precision)) {
      return 0.0;
    }
    return tangentSimple(step, y1, y2);
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
   * Calls {@link #calcDerivDepth(ValueDataSet, int) calcDerivDepth(ValueDataSet, 10)}.
   * 
   * @param dataset
   * @return
   */
  public static ArrayList<Integer> calcDerivDepth(ValueDataSet<? extends Number> dataset) {
    return Tangenter.calcDerivDepth(dataset, 10);
  }

  /**
   * Calls {@link #calcDerivDepth(ValueDataSet, int, boolean) calcDerivDepth(ValueDataSet, int,
   * true)}.
   * 
   * @param dataset
   * @param maxDepth
   * @return
   */
  public static ArrayList<Integer> calcDerivDepth(ValueDataSet<? extends Number> dataset,
      int maxDepth) {
    return calcDerivDepth(dataset, maxDepth, true);
  }

  /**
   * Calculates and returns the depth of derivative ({@code derivDepth}) for {@code dataset}.
   * <p>
   * The {@code derivDepth} is the number of times a trace derivative (the tangent between two
   * points, see {@link #tangentSimple(double, double, double)}) is NOT zero. For each point this
   * indicates the degree of the polynomial the data is representing, if it is polynomial. If not
   * the {@code derivDepth} will be assigned {@link Integer#MAX_VALUE} until further analysis, to
   * represent infinity. This will also be assigned if the derivDepth would be larger than
   * {@code maxDepth - 1}.
   * <p>
   * If {@code doInfiniteDepths} is {@code true} any {@link Integer#MAX_VALUE} {@code derivDepth}
   * values will be further analysed and assigned {@code -2} if exponential, {@code -3} for
   * trigonometric and {@code -5} otherwise, with the correct change values of {@code -1} also
   * assigned.
   * 
   * @param dataset The {@link ValueDataSSet} that is to be analysed.
   * @param maxDepth The maximum depth to which the derivative should be calculated.
   *        {@code derivDepth} larger than this will be assigned {@link Integer#MAX_VALUE},
   *        representing infinity.
   * @param doInfiniteDepths Whether infinite derivDepths should be further analysed to exponential,
   *        trigonometric etc. (={@code true}) or not (={@code false}).
   * @return An {@link ArrayList ArrayList<Integer>} of the {@code derivDepth} for each value of
   *         {@code dataset}. Note that the last {@code maxDepth} values may be inaccurate.
   */
  public static ArrayList<Integer> calcDerivDepth(ValueDataSet<? extends Number> dataset,
      int maxDepth, boolean doInfiniteDepths) {
    int size = dataset.size();
    ArrayList<Integer> depths = new ArrayList<Integer>(size);
    double[][] derivs = new double[size][maxDepth];
    // calculate trace by trace derivatives
    calcDerivs(derivs, dataset);
    // count derivDepth
    countDerivDepths(derivs, depths);
    // detect and mark points of change
    detectDepthChanges(derivs, depths);
    // further analysis
    if (doInfiniteDepths == true) {
      // TODO
    }
    // TODO smooth over the last maxDepth elements
    // finished
    return depths;
  }

  /**
   * This method populates the derivative matrix used in {@link #calcDerivDepth(ValueDataSet, int)}.
   * 
   * @category helper
   * @param derivs A reference to the initialised derivative matrix.
   * @param set The {@link ValueDataSet} that is to be analysed.
   * @see ValueDataSet
   */
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
        derivs[i][j] = tangentApprox(step, derivs[i][j - 1], derivs[i + 1][j - 1]);
      }
    }
  }

  /**
   * This method counts the depth of the derivatives in the derivative matrix, used in
   * {@link #calcDerivDepth(ValueDataSet, int)}.
   * 
   * @category helper
   * @param derivs
   * @param depths
   * @see ValueDataSet
   */
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

  /**
   * This method uses the derivative depths over the derivative matrix, used in
   * {@link #calcDerivDepth(ValueDataSet, int)}, to detect changes iin the {@link ValueDataSet} that
   * is being analysed.
   * 
   * @category helper
   * @param derivs
   * @param depths
   * @see ValueDataSet
   */
  private static void detectDepthChanges(double[][] derivs, ArrayList<Integer> depths) {
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

  private void checkInfs(ValueDataSet<? extends Number> set, ArrayList<Integer> depths,
      int maxDepth) {
    int size = depths.size();
    boolean checking = false;
    int startI = 0;
    int endI = 0;
    int depth;
    for (int i = 0; i < size; i++) {
      if (depths.get(i) == Integer.MAX_VALUE) {
        // depth is undefined until we know otherwise
        set.setDerivDepth(i, -5);
        // begin tracking a potential segment.
        if (checking == false) {
          startI = i;
          checking = true;
        }
      } else if (checking == true) {
        // end of segment
        endI = i;
        checking = false;
        if (endI - startI < maxDepth) {
          continue;
        }
        // TODO check
      }
    }
  }

  private void checkForFunctions(ValueDataSet<? extends Number> set, ArrayList<Integer> depths,
      int startI, int endI, int maxDepth) {
    double val;
    double smallest = Double.MIN_VALUE;
    ArrayList<Double> values = new ArrayList<>();
    for (int i = startI; i < endI; i++) {
      val = set.getByIndex(i).doubleValue();
      if (val < smallest) {
        smallest = val;
      }
      values.add(val);
    }
    // if there are negative values, move all values up such that they are all positive
    // this is required to prevent NaN or infinity values when taking the logarithm
    // it does not affect the derivative values beyond possible floating point errors
    if (smallest <= 0) {
      for (int i = 0; i < values.size(); i++) {
        values.set(i, values.get(i) + smallest + Double.MIN_VALUE);
      }
    }
    ValueDataSet<Double> otherSet = new ValueDataSet<>(set.getBase(), set.getStep(),
        set.getPrecision(), d -> Double.valueOf(d));

    // --- Exponential ---
    // add values to data set and calculate derivDepth
    for (Double element : values) {
      otherSet.add(Math.log(element));
    }
    ArrayList<Integer> list = calcDerivDepth(otherSet, maxDepth, false); // prevent infinite
                                                                         // recursion
    int depth;
    Integer otherStartI = null;
    for (int i = 0; i < list.size(); i++) {
      depth = list.get(i);
      if (depth == Integer.MAX_VALUE) {
        // track if not exponential for further analysis
        if (otherStartI == null) {
          otherStartI = i;
        }
      } else if (depth == 1) {
        // mark as exponential
        depths.set(i + startI, -2);
        if (otherStartI != null) {
          // if was tracking then mark change
          depths.set(i + startI - 1, -1);
          // TODO trig
          otherStartI = null;
        }
      } else {
        // else transfer value over (e.g. change within exponential or bias)
        depths.set(i + startI, depth);
        if (otherStartI != null) {
          // if was tracking then mark change
          depths.set(i + startI - 1, -1);
          // TODO trig
          otherStartI = null;
        }

      }
    }
    // mark the change
    depths.set(endI - 1, -1);
  }

}
