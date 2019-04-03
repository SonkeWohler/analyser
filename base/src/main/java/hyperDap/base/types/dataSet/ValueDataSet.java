package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import java.util.List;
import hyperDap.base.helpers.Comparator;
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
public class ValueDataSet<T extends Number> extends DataSet<T> {

  ArrayList<T> values;
  /**
   * A record of how precise this DataSet is. Used to assess whether two yValues are equal or not.
   */
  protected final double yPrecision;

  public ValueDataSet(Number base, Number step, Number yPrecision) {
    super(base, step);
    this.values = new ArrayList<T>();
    this.yPrecision = yPrecision.doubleValue();
  }

  // write
  // ****************************************************************************************

  public void add(ValuePair<T> valuePair) {
    double xValue = valuePair.getX().doubleValue();
    T yValue = valuePair.getY();
    this.add(xValue, yValue);
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
   * @return {@code true} if this value is at this index, {@code false} otherwise.
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
   * @return
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
   * @return
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
