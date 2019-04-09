package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import java.util.List;
import hyperDap.base.helpers.Comparator;
import hyperDap.base.helpers.Tangenter;
import hyperDap.base.types.value.ValuePair;

/**
 * An implementation of {@link DataSet} that holds {@link Number} Objects as the dependentValue.
 * <p>
 * This implementation and its subclasses are collections of values, as opposed to
 * {@link NestedDataSet} subclasses, and feature methods to retrieve and manipulate these values
 * efficiently.
 * 
 * @author soenk
 *
 * @param <T>
 */
public class ValueDataSet<T extends Number> extends ValidityDataSet<T> {

  ArrayList<T> values;
  /**
   * A record of how precise this DataSet is. Used to assess whether two yValues are equal or not.
   */
  protected final double yPrecision;

  private ArrayList<Integer> derivDepths;

  public ValueDataSet(Number base, Number step, Number yPrecision) {
    super(base, step);
    this.values = new ArrayList<T>();
    this.yPrecision = yPrecision.doubleValue();
    this.derivDepths = new ArrayList<Integer>();
  }

  // helpers
  // ****************************************************************************************

  public void calcDerivDepths() {
    this.derivDepths = Tangenter.calcDerivDepth(this);
  }

  // write
  // ****************************************************************************************

  /**
   * Encapsulation of {@link #add(double, Object)} using {@link ValuePair} input.
   * 
   * @param valuePair Data that is to be unboxed to add an entry.
   */
  public void add(ValuePair<T> valuePair) {
    double xValue = valuePair.getX().doubleValue();
    T yValue = valuePair.getY();
    this.add(xValue, yValue);
  }

  // getters
  // ***************************************************************************************

  /**
   * Returns the depths to which a trace by trace derivative for this value is not zero.
   * 
   * @param index
   * @return The number of derivatives that are not zero, can be {@link Integer#MAX_VALUE} to
   *         represent infinity and negative when this value could not be calculated normally.
   * @throws IndexOutOfBoundsException When there is no such value.
   */
  public int getDerivDepthsByIndex(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= this.size()) {
      throw new IndexOutOfBoundsException();
    }
    if (index >= this.derivDepths.size()) {
      this.calcDerivDepths();
    }
    return this.derivDepths.get(index);
  }

  /**
   * Returns the depths to which a trace by trace derivative for this value is not zero.
   * 
   * @param xValue
   * @return The number of derivatives that are not zero, can be {@link Integer#MAX_VALUE} to
   *         represent infinity and negative when this value could not be calculated normally.
   * @throws IndexOutOfBoundsException When there is no such value.
   */
  public int getDerivDepth(double xValue) throws IndexOutOfBoundsException {
    return this.getDerivDepthsByIndex(this.getIndex(xValue));
  }

  /**
   * {@link Number} encapsulation of {@link #getDerivDepth(double)}.
   * 
   * @param xValue
   * @return The number of derivatives that are not zero, can be {@link Integer#MAX_VALUE} to
   *         represent infinity and negative when this value could not be calculated normally.
   * @throws IndexOutOfBoundsException When there is no such value.
   */
  public int getDerivDepth(Number xValue) throws IndexOutOfBoundsException {
    return this.getDerivDepth(xValue.doubleValue());
  }

  // contains
  // ****************************************************************************************

  /**
   * Test if this DataSet contains the specified value at this index.
   * <p>
   * <<<<<<< HEAD Only checks for exactly this entry, for checking for a range of values around a
   * specific independent value use {@link #contains(double, double)}. ======= Only checks for
   * exactly this entry, for checking for a value close to this one see
   * {@link #contains(double, double, double, double)}. >>>>>>> master
   * <p>
   * If the index is out of bounds false is returned.
   * 
   * @param index The index where this value is expected.
   * @param value The value that should be contained.
   * @return {@code true} if this @{@code yValue} is stored under this {@code index}.
   */
  public boolean contains(int index, Number value) {
    try {
      return Comparator.equalApprox(value.doubleValue(), this.getByIndex(index).doubleValue(),
          this.yPrecision);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Check whether the requested x-y values are represented in this DataSet, given the desired
   * precisions.
   * 
   * @param xValue
   * @param yValue
   * @param xPrecision
   * @param yPrecision
   * @return {@code true} if this @{@code yValue} is stored under this {@code xValue}.
   */
  public boolean contains(double xValue, double yValue, double xPrecision, double yPrecision) {
    int index = this.getIndex(xValue);
    double indexValue = this.getIndependentValue(index);
    if (Comparator.equalApprox(xValue, indexValue, xPrecision) == false) {
      return false;
    }
    List<Integer> list = new ArrayList<Integer>(); // a list of the indices that should be checked
    list.add(index);
    int i = index - 1;
    while (Comparator.equalApprox(xValue, this.getIndependentValue(i), xPrecision)) {
      list.add(i);
      i--;
    }
    i = index + 1;
    while (Comparator.equalApprox(xValue, this.getIndependentValue(i), xPrecision)) {
      list.add(i);
      i++;
    }
    for (int j : list) {
      try {
        if (Comparator.equalApprox(yValue, this.getByIndex(j).doubleValue(), yPrecision) == true) {
          return true;
        }
      } catch (IndexOutOfBoundsException e) {

      }
    }
    return false;
  }

  /**
   * Check whether the requested x-y values are contained within this DataSet, within the default
   * precisions defined by the DataSet (yPrecision and step).
   * 
   * @param xValue The independent value defining the index/indices to be checked.
   * @param yValue The dependent value that should be contained at the checked indices.
   * @param yPrecision The precision within which the yValue will be considered equal to that
   *        contained at the checked indices.
   * @return {@code true} if this @{@code yValue} is stored under this {@code xValue}.
   */
  public boolean contains(double xValue, double yValue) {
    return this.contains(xValue, yValue, 0.5 * this.step, this.yPrecision);
  }

  /**
   * Check whether this {@link ValuePair} is represented in this DataSet.
   * <p>
   * Makes use of {@link #contains(double, double)};
   * 
   * @param valuePair A pair of the independent and dependent values to be checked.
   * @return {@code true} if this pair is conatained, {@code false} otherwise.
   */
  public boolean contains(ValuePair<? extends Number> valuePair) {
    return this.contains(valuePair.getX().doubleValue(), valuePair.getY().doubleValue());
  }

  //
  // ****************************************************************************************


}
