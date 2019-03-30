package hyperDap.base.types.dataSet;

import java.util.ArrayList;

public class NestedPairDataSet<T extends Number> extends NestedDataSet<PairDataSet<T>> {

  protected ArrayList<PairDataSet<T>> values;

  public NestedPairDataSet(Number base, Number step, Number nestedBase, Number nestedStep) {
    super(base, step, nestedBase, nestedStep);
    this.values = new ArrayList<PairDataSet<T>>();
  }

}
