package hyperDap.base.types.value;

/**
 * An immutable pair of number values.
 * 
 * @author soenk
 *
 * @param <T>
 */
public class ValuePair<T extends Number> {

  private final T xValue; // independentValue
  private final T yValue; // dependentValue

  public ValuePair(T independentValue, T dependentValue) {
    this.xValue = independentValue;
    this.yValue = dependentValue;
  }

  public T getX() {
    return this.xValue;
  }

  public T getY() {
    return this.yValue;
  }

}
