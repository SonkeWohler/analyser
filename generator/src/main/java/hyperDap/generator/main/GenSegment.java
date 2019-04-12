package hyperDap.generator.main;

import java.util.function.Function;

/**
 * This class generates a section of data specific to one function.
 * 
 * @author soenk
 *
 */
public class GenSegment {

  private double a;
  private double b;
  private double c;
  private Function<Double, Double> func;

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

}
