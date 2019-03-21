package hyperDap.base.types.dataSet;

import java.util.Collection;
import hyperDap.base.types.value.ValuePair;

/**
 * An implementation of {@link DataSet} that makes use of the
 * 
 * @author soenk
 *
 * @param <T>
 */
public class ValueDataSet<T extends Number> extends DataSet {

  public ValueDataSet(Number base, Number step) {
    super(base, step);
  }

  @Override
  public boolean contains(Object o) {

    return false;
  }

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

  @Override
  public boolean containsAll(Collection c) {
    boolean ret = true;
    for (Object o : c) {
      if (this.contains(o) == false) {
        ret = false;
        break;
      }
    }
    return ret;
  }

  @Override
  public Object[] toArray() {
    return this.values.toArray();
  }

  @Override
  public boolean removeAll(Collection c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean retainAll(Collection c) {
    // TODO Auto-generated method stub
    return false;
  }

}
