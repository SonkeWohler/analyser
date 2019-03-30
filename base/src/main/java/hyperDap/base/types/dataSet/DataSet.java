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

  /**
   * Default constructor.
   * 
   * @param base
   * @param step
   */
  public DataSet(Number base, Number step) {
    this.base = base.doubleValue();
    this.step = step.doubleValue();
    values = new ArrayList<T>();
  }

  // helper category
  // *************************************************************************************

  /**
   * Calculate the index associated with this independent value.
   * <p>
   * Calculated as index=({@code independentValue}- {@link #base} ) / {@link #step}.
   * 
   * @category helper
   * @param independentValue
   * @return
   */
  public int getIndex(double independentValue) {
    independentValue = (independentValue - this.base) / this.step;
    int index = (int) independentValue;
    return index;
  }

  /**
   * Calculate the independent value associated with the requested index.
   * <p>
   * calculated as return= {@link #base} + {@code index} * {@link #step}.
   * 
   * @category helper
   * @param index The requested index.
   * @return The independent value associated with {@code index}
   */
  public double getIndependentValue(int index) {
    double independentValue = this.base + index * this.step;
    return independentValue;
  }

  /**
   * Returns the largest independent value that still maps to an entry.
   * 
   * @category helper
   * @return The independent value associated with the last index of {@link #values}.
   */
  public double getMaxIndependentValue() {
    return this.getIndependentValue(this.values.size() - 1);
  }

  /**
   * Used within {@link #add(int, Object)} to initialise elements at intermediate indices to a
   * sensible default value.
   * <p>
   * Should be overwritten by subclasses.
   * 
   * @category helper
   * @return A default value for elements of this DataSet.
   */
  @SuppressWarnings("unused")
  private T initialisationValue() {
    return null;
  }

  /**
   * Used within {@link #add(int, Object)} to initialise elements at intermediate indices to a
   * sensible value based on the two surrounding values (the currently last and the newly added
   * one).
   * <p>
   * Should be overwritten by subclasses.
   * 
   * @category helper
   * @param value1 The currently last value in {@link #values}
   * @param value2 The value that should be added at the desired index.
   * @return A default value for elements of this DataSet.
   */
  private T initialisationValue(T value1, T value2) {
    return null;
  }

  /**
   * Helper method to cast from other objects to T without triggering warnings.
   * 
   * @category helper
   * @param o The Object of any type that should be cast to type {@code T}.
   * @return The cast of {@code o} if this is possible.
   * @throws ClassCastException Thrown if there is no legal cast from {@code o} to {@code T}.
   */
  @SuppressWarnings("unchecked")
  private T castToT(Object o) throws ClassCastException {
    return (T) o;
  }

  // Writing/setters
  // *************************************************************************************

  /**
   * Call {@link #add(int, Object)} on the index corresponding to this {@code independentValue} wtih
   * {@code index= (independentValue-base)/step}.
   * 
   * @category writing
   * @param independentValue The independent value this value should be associated with, used to
   *        calculate its index.
   * @param value The value that is to be added.
   */
  public void add(double independentValue, T value) {
    this.add(this.getIndex(independentValue), value);
  }

  /**
   * Add a value at a specific index. If this value already exists it is replaced. If it does not
   * the list of values is extended to include the required index, with the values between the last
   * and this new one being initialised to the default value specified by
   * {@link #initialisationValue(Object, Object).
   * 
   * @category writing
   * @param index The index at which the value is to be added.
   * @param value The value that is to be added.
   */
  public void add(int index, T value) {
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
   * 
   * @category writing
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
   * @category writing
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
   * @category writing
   * @param valueThe dependent value to be added.
   */
  public void quickAdd(T value) {
    this.values.add(value);
  }

  // reading/getters
  // *************************************************************************************

  /**
   * Returns the dependent value from the index corresponding to {@code independentValue}.
   * <p>
   * In accordance with DataSet specifications the index that is retrieved is the integer cast value
   * of {@code (independentValue-base)/step}.
   * 
   * @category reading
   * @param independentValue The independent value associated with the required value.
   * @return The dependent Value stored at {@code index=(int) (independentValue-base)/step}.
   */
  public T get(Number independentValue) {
    return this.getByIndex(this.getIndex(independentValue.doubleValue()));
  }

  /**
   * Returns the dependent value from the index corresponding to {@code independentValue}.
   * <p>
   * In accordance with DataSet specifications the index that is retrieved is the integer cast value
   * of {@code (independentValue-base)/step}.
   * 
   * @category reading
   * @param independentValue Used by {@link #getIndex(double)} to calculate the index of the desired
   *        element.
   * @return The value corresponding to the calculated index.
   */
  public T get(double independentValue) {
    return this.getByIndex(this.getIndex(independentValue));
  }

  /**
   * Get an entry by the index in {@link #values}.
   * 
   * @category reading
   * @param index
   * @return
   */
  public T getByIndex(int index) {
    return this.values.get(index);
  }

  // other inheritances from Collection
  // ****************************************************************************************

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public int size() {
    return this.values.size();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public void clear() {
    this.values.clear();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public boolean isEmpty() {
    return this.values.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public Iterator<T> iterator() {
    return this.values.iterator();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public boolean contains(Object o) {
    return this.values.contains(o);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
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

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public Object[] toArray() {
    return this.values.toArray();
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object[] toArray(Object[] a) {
    return this.values.toArray(a);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   */
  @Override
  public boolean remove(Object o) {
    return this.values.remove(o);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public boolean addAll(Collection<? extends T> c) {
    return this.values.addAll(c);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    return this.values.removeAll(c);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Is applied to {@link #values}.
   * 
   * @category fromCollection
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    return this.values.retainAll(c);
  }

  // main for testing
  // ****************************************************************************************

  /**
   * This main method is only for testing purposes and should be removed when development is
   * complete.
   * 
   * @param args
   */
  public static void main(String[] args) {

  }

}
