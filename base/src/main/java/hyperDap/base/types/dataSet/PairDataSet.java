package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import hyperDap.base.types.value.ValuePair;

/**
 * An implementation of {@link ValueDataSet} that holds a {@link ValuePair} as the dependentValue.
 * <p>
 * It should be used when independent values must be recorded exactly and are evenly distributed,
 * but are not consistently equidistant.
 * <p>
 * If values are equidistant consider using {@link ValueDataSet}. If they are not evenly distributed
 * attempt to find a HashMap implementation of {@link DataSet}.
 * 
 * @author soenk
 *
 * @param <T> The type of {@link ValuePair} which must extend {@link Number}
 */
public class PairDataSet<T extends Number> extends ValueDataSet<T> {

  protected ArrayList<ValuePair<T>> values;

  public PairDataSet(Number base, Number step, Number yPrecision) {
    super(base, step, yPrecision);
    this.values = new ArrayList<ValuePair<T>>();
  }

}
