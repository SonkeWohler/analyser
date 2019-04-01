package hyperDap.base.types.value;

/**
 * An immutable pair of number values. While there is no programmatic relation between the two
 * values, they are assumed to be related usually as a yValue dependent on an xValue, as if
 * representing a single point on a one dimensional function..
 * 
 * @author soenk
 *
 * @param <T> The subclass of {@link Number} that the values should be stored in.
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

  /**
   * Checks whether another Object {@code o} has <b>exactly</b> the same values as this instance.
   * <p>
   * Currently only applies to {@link ValuePair} instances, with arrays of the form
   * {@code <xValue,yValue>} and {@link java.util.Collection Collections} of this form are
   * considered for the future.
   * 
   */
  @Override
  public boolean equals(Object o) {
    ValuePair<T> pair;
    try {
      pair = this.castToThis(o);
    } catch (ClassCastException e) {
      return false; // Could add option for {xValue,yValue} array or collection.
    }
    if (pair.getX().equals(this.xValue) == false) {
      return false;
    }
    if (pair.getY().equals(this.yValue) == false) {
      return false;
    }
    return true;
  }

  /**
   * Helper method to cast Objects to {@link ValuePair} of the same type {@code <T>} as this
   * instance
   * 
   * @param o
   * @return
   */
  @SuppressWarnings("unchecked")
  private ValuePair<T> castToThis(Object o) {
    return (ValuePair<T>) o;
  }
}
