package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import hyperDap.base.types.value.ValuePair;

/**
 * An implementation of {@link DataSet} that holds a {@link ValuePair} as the dependentValue.
 * 
 * @author soenk
 *
 * @param <T> The type of {@link ValuePair} which must extend {@link Number}
 */
public class PairDataSet<T extends Number> extends DataSet<ValuePair<T>> {

  protected ArrayList<ValuePair<T>> values;

  public PairDataSet(Number base, Number step) {
    super(base, step);
    this.values = new ArrayList<ValuePair<T>>();
  }

}
