package hyperDap.base.types.dataSet;

import java.util.ArrayList;

public class NestedValueDataSet<T extends Number> extends NestedDataSet<ValueDataSet<T>> {
  protected ArrayList<ValueDataSet<T>> values;


  public NestedValueDataSet(Number base, Number step, Number nestedBase, Number nestedStep) {
    super(base, step, nestedBase, nestedStep);
    this.values = new ArrayList<ValueDataSet<T>>();
  }


}
