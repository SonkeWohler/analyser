package hyperDap.base.types.dataSet;

import java.util.ArrayList;

public class NestedDataSet<T extends DataSet> extends DataSet {

  public NestedDataSet(Number base, Number step) {
    super(base, step);
    this.values = new ArrayList<T>();
  }

}
