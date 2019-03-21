package hyperDap.base.types.dataSet;

import java.util.ArrayList;

/**
 * Superclass for series of Values that come in a pair of Independent variable to dependent variable
 * (a value pair), where the independent value is used to determine the index of the dependent value
 * within the list of values.
 * <p>
 * This Collection is used to store independent value that are exactly spaced out by {@link step},
 * or where the exact spacing does not matter. At assignment the independent value is only retained
 * in the index within the list, and resulting casting errors etc. may lead to discreptancies
 * between the intended value and the actual values.
 * <p>
 * Retrieving by the independent variable is performed by calculating the corresponding index using
 * the {@link #base} and {@link #step} fields, such that
 * {@code independent value= base + index*step}.
 * 
 * @author soenk
 *
 * @param <T> The type of values stored in this DataSet
 */
public class DataSet<T> {

  private final double base;
  private final double step;
  private ArrayList<T> values;

  public DataSet(Number base, Number step) {
    this.base = base.doubleValue();
    this.step = step.doubleValue();
    values = new ArrayList<T>();
  }

  //
  // *************************************************************************************

  private T initialisationValue() {
    return null;
  }

  private T initialisationValue(T value1, T value2) {
    return null;
  }

  //
  // *************************************************************************************

  /**
   * Call {@link #set(int, Object)} on the index corresponding to this {@code independentValue} wtih
   * {@code index= (independentValue-base)/step}.
   * 
   * @param independentValue The independent value this value should be associated with, used to
   *        calculate its index.
   * @param value The value that is to be added.
   */
  public void set(double independentValue, T value) {
    independentValue = (independentValue - this.base) / this.step;
    int index = (int) independentValue;
    this.set(index, value);
  }

  /**
   * Add a value at a specific index. If this value already exists it is replaced. If it does not
   * the list of values is extended to include the required index, with the values between the last
   * and this new one being initialised to the default value specified by
   * {@link #initialisationValue(Object, Object)}.
   * 
   * @param index The index at which the value is to be added.
   * @param value The value that is to be added.
   */
  public void set(int index, T value) {
    try {
      this.values.set(index, value);
    } catch (IndexOutOfBoundsException e) {
      this.values.ensureCapacity(index);
      int lastIndex = this.values.size() - 1;
      T initValue = this.initialisationValue(value, this.values.get(lastIndex));
      for (int i = lastIndex + 1; i < index; i++) {
        this.values.add(initValue); // the index of this value will be i
      }
      this.values.add(value);
    }
  }

  /**
   * Add a new value at the end of the DataSet and return the independent value it will be
   * associated with.
   * 
   * @param value The dependent value to be added.
   * @return The independent value that will be associated with this value
   */
  public double add(T value) {
    this.values.add(value);
    double ret = this.values.size() - 1;
    ret = this.base + ret * this.step;
    return ret;
  }

  /**
   * A shortened version of {@link #add(Object)} which does not calculate the associated independent
   * value. It should complete slightly faster than the aforementioned method.
   * 
   * @param valueThe dependent value to be added.
   */
  public void quickAdd(T value) {
    this.values.add(value);
  }

  //
  // *************************************************************************************

  /**
   * returns the dependent value from the index corresponding to {@code independentValue}.
   * <p>
   * In accordance with DataSet specifications the index that is retrieved is the integer cast value
   * of {@code (independentValue-base)/step}.
   * 
   * @param independentValue The independent value associated with the required value.
   * @return The dependent Value stored at {@code index=(int) (independentValue-base)/step}.
   */
  public T get(Number independentValue) {
    double index = independentValue.doubleValue() - base;
    index = index / step;
    return this.getByIndex((int) index);
  }

  public T get(double independentValue) {
    double index = independentValue - base;
    index = index / step;
    return this.getByIndex((int) index);
  }

  public T getByIndex(int index) {
    return this.values.get(index);
  }

  /**
   * This main method is only for testing purposes and should be removed when development is
   * complete.
   * 
   * @param args
   */
  public static void main(String[] args) {

  }

}
