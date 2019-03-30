package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import hyperDap.base.types.value.ValuePair;

/**
 * An implementation of {@link DataSet} that holds Numbers as the dependentValue.
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

  public ValueDataSet(Number base, Number step) {
    super(base, step);
    this.values = new ArrayList<T>();
  }

  //
  // ****************************************************************************************

  public boolean contains(Number value) {
    return this.values.contains(value);
  }

  public boolean contains(ValuePair<Number> valuePair) {
    boolean ret = true;
    if (this.values.contains(valuePair.getY()) == false) {
      ret = false;
    }
    if (!(valuePair.getX().doubleValue() < this.base
        || valuePair.getX().doubleValue() > this.getMaxIndependentValue())) {
      ret = false;
    }
    return ret;
  }

  //
  // ****************************************************************************************


}
