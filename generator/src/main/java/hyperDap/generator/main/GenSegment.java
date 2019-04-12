package hyperDap.generator.main;

import java.util.ArrayList;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import hyperDap.base.types.dataSet.DataSet;
import hyperDap.base.types.dataSet.ValueDataSet;

/**
 * This class generates a section of data specific to one function.
 * <p>
 * The intended use is to initialise a {@code GenSegment} with the intended values and then retrieve
 * lists of data points from {@link #generateValues(double, int)} as needed, before leaving the
 * Object to be garbage-collected.
 * <p>
 * Each Object represents a function of the format {@code a * Func(x + b) + c}, where {@code Func}
 * is a mathematical function specified by {@code functionEncoding} at construction time. The
 * function is shifted such that it passes through the {@code intercept} at {@code x=0}, which is
 * the first value that is generated.
 * <p>
 * The {@code functionEncding} may specify: <br>
 * {@code constant} : {@code y = c} <br>
 * {@code linear} : {@code y = a * (x + b) + c} <br>
 * {@code square} : {@code y = a * (x + b)^2 +c} <br>
 * {@code cubic} : {@code y = a * (x + b)^3 + c} <br>
 * {@code exp} : {@code y = a * Math.E^(x + b) + c} <br>
 * {@code sine} : {@code y = a * sin(x + b) +c} <br>
 * <p>
 * Here {@code a} translates to the {@code scale} specified at construction, {@code b} to
 * {@code shiftX}, while {@code c} is defined at construction such that the function
 * returns @{@code intercept} for {@code x=0}.
 * 
 * @author soenk
 *
 */
public class GenSegment {

  private double a;
  private double b;
  private double c;
  private Function<Double, Double> func;

  /**
   * 
   * @param functionEnccoding
   * @param scale
   * @param shiftX
   * @param intercept
   */
  public GenSegment(String functionEnccoding, double scale, double shiftX, double intercept) {
    a = scale;
    b = shiftX;
    c = 0;
    this.defineFunction(functionEnccoding);
    c = f(0) + intercept;
  }

  /**
   * Classifies the function represented by this OObject based on {@code encoding.}
   * 
   * @param encoding
   */
  private void defineFunction(String encoding) {
    switch (encoding) {
      case "constant":
        func = x -> 0.0;
        break;
      case "linear":
        func = x -> x;
        break;
      case "square":
        func = x -> Math.pow(x, 2);
        break;
      case "cubic":
        func = x -> Math.pow(x, 3);
        break;
      case "exp":
        func = x -> Math.pow(Math.E, x);
        break;
      case "sine":
        func = x -> Math.sin(x);
        break;
      default:
        throw new IllegalArgumentException(
            String.format("'%s' is not a valid function encoding!", encoding));
    }
  }

  /**
   * Returns a single value of the function specified in this Object.
   * <p>
   * This is specified as {@code a* Function(x + b) +c}.
   * 
   * @param x The {@code xValue} to be fed into the function.
   * @return The {@code yValue} corresponding to {@code x}.
   */
  private double f(double x) {
    return a * this.func.apply(x + b) + c;
  }

  /**
   * Generate a list of data points of length {@code N}, according to pre-set specifications.
   * 
   * @param step The distance between values on the x-axis. See {@link DataSet}
   * @param N The number of data points to be generated.
   * @return An {@link ArrayList} of the generated data points.
   */
  public ArrayList<Double> generateValues(double step, int N) {
    ArrayList<Double> list = new ArrayList<Double>();
    for (Integer i = 0; i < N; i++) {
      list.add(f(i.doubleValue() * step));
    }
    return list;
  }

  /**
   * Generate the specified data points and add them to the end of {@code set}.
   * <p>
   * Calls {@link ValueDataSet#ensureCapacity(int)} before generating data.
   * <p>
   * Noise is not modelled yet.
   * 
   * @param set The {@link CalueDataSet} that the data points should be added to.
   * @param step The distance between data points on the x-axis.
   * @param N The number of data points that should be added.
   */
  public void addToDoubleDataSet(ValueDataSet<Double> set, int N) {
    set.ensureCapacity(N + set.size());
    double step = set.getStep();
    for (Integer i = 0; i < N; i++) {
      set.add(Double.valueOf(f(i.doubleValue() * step)));
    }
  }

  /**
   * Generate the specified data points and add them to the end of {@code set}.
   * <p>
   * This method requires that {@code set} has an assigned {@code convertFromDouble}
   * {@link DoubleFunction Function} assigned.
   * <p>
   * {@link ValueDataSet#ensureCapacity(int)} is called before adding data points.
   * 
   * @param set The {@link CalueDataSet} that the data points should be added to.
   * @param step The distance between data points on the x-axis.
   * @param N The number of data points that should be added.
   * @throws IllegalArgumentException If {@link ValueDataSet#hasConversionFunction()} returns
   *         {@code false}.
   */
  public void addToDataSet(ValueDataSet<? extends Number> set, int N)
      throws IllegalArgumentException {
    if (set.hasConversionFunction() == false) {
      throw new IllegalArgumentException(
          "ValueDataSet must have a convertFromDouble function defined!");
    }
    double step = set.getStep();
    set.ensureCapacity(N + set.size());
    for (Integer i = 0; i < N; i++) {
      set.add(f(i.doubleValue() * step));
    }
  }

}
