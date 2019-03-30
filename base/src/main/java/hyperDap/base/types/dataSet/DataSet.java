package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
 * {@code independent value= base + index*step}. +-
 * 
 * @author soenk
 *
 * @param <T> The type of values stored in this DataSet
 */
public abstract class DataSet<T> implements Collection<T> {

  protected final double base;
  protected final double step;
  protected ArrayList<T> values;

  public DataSet(Number base, Number step) {
    this.base = base.doubleValue();
    this.step = step.doubleValue();
    values = new ArrayList<T>();
  }

  //
  // *************************************************************************************

  public int getIndex(double independentValue) {
    independentValue = (independentValue - this.base) / this.step;
    int index = (int) independentValue;
    return index;
  }

  public double getIndependentValue(int index) {
    double independentValue = this.base + index * this.step;
    return independentValue;
  }

  public double getMaxIndependentValue() {
    return this.getIndependentValue(this.values.size() - 1);
  }

  //
  // *************************************************************************************

  /**
   * Used within {@link #set(int, Object)} to initialise elements at intermediate indices to a
   * sensible default value.
   * <p>
   * Should be overwritten by subclasses.
   * 
   * @return A default value for elements of this DataSet.
   */
  @SuppressWarnings("unused")
  private T initialisationValue() {
    return null;
  }

  /**
   * Used within {@link #set(int, Object)} to initialise elements at intermediate indices to a
   * sensible value based on the two surrounding values (the currently last and the newly added
   * one).
   * <p>
   * Should be overwritten by subclasses.
   * 
   * @param value1 The currently last value in {@link #values}
   * @param value2 The value that should be added at the desired index.
   * @return A default value for elements of this DataSet.
   */
  private T initialisationValue(T value1, T value2) {
    return null;
  }

  @SuppressWarnings("unchecked")
  private T castToT(Object o) throws ClassCastException {
    return (T) o;
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
    this.set(this.getIndex(independentValue), value);
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
   * {@inheritDoc}
   */
  @Override
  public boolean add(Object e) {
    T e2;
    try {
      e2 = this.castToT(e);
    } catch (ClassCastException exception) {
      return false;
    }
    T value = e2;
    return this.values.add(value);
  }

  /**
   * Add a new value at the end of the DataSet and return the independent value it will be
   * associated with.
   * 
   * @param value The dependent value to be added.
   * @return The independent value that will be associated with this value
   */
  public double addValue(T value) {
    this.values.add(value);
    return this.getIndependentValue(this.values.size() - 1);
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
    return this.getByIndex(this.getIndex(independentValue.doubleValue()));
  }

  public T get(double independentValue) {
    return this.getByIndex(this.getIndex(independentValue));
  }

  public T getByIndex(int index) {
    return this.values.get(index);
  }

  //
  // ****************************************************************************************

  @Override
  public int size() {
    return this.values.size();
  }

  @Override
  public boolean isEmpty() {
    return this.values.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return this.values.contains(o);
  }

  //
  // ****************************************************************************************

  @Override
  public boolean containsAll(Collection<?> c) {
    boolean ret = true;
    for (Object o : c) {
      if (this.contains(o) == false) {
        ret = false;
        break;
      }
    }
    return ret;
  }

  //
  // ****************************************************************************************

  @Override
  public void clear() {
    this.values.clear();
  }

  //
  // ****************************************************************************************

  @Override
  public Iterator<T> iterator() {
    // TODO return value pair
    return this.values.iterator();
  }

  @Override
  public Object[] toArray() {
    return this.values.toArray();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object[] toArray(Object[] a) {
    return this.values.toArray(a);
  }

  //
  // ****************************************************************************************



  /**
   * This main method is only for testing purposes and should be removed when development is
   * complete.
   * 
   * @param args
   */
  public static void main(String[] args) {

  }

  @Override
  public boolean remove(Object o) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

}
