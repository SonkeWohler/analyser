package hyperDap.generator.main;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * This class generates a section of data specific to one function.
 * <p>
 * The intended use is to initialise a {@code GenSegment} with the intended values and then retrieve
 * lists of data points from {@link #generateValues(double, int)} as needed, before leaving the
 * Object to be garbage-collected.
 * <p>
 * The class generates data points starting from {@code x=0}, advancing with {@code x=i*step}, such
 * that the first value is equal to the {@code intercept} specified at initialisation. The function
 * can be shifted in the x-axis with {@code shiftX} and scaled with {@code scale}.
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

  private double f(double x) {
    return a * this.func.apply(x + b) + c;
  }

  public ArrayList<Double> generateValues(double step, int N) {
    ArrayList<Double> list = new ArrayList<Double>();
    for (Integer i = 0; i < N; i++) {
      list.add(f(i.doubleValue() * step));
    }
    return list;
  }

}
