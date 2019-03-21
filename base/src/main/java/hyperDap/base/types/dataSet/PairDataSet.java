package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import hyperDap.base.types.value.ValuePair;

public class PairDataSet<T extends Number> extends DataSet {

  public PairDataSet(Number base, Number step) {
    super(base, step);
    this.values = new ArrayList<ValuePair<T>>();
  }

}
