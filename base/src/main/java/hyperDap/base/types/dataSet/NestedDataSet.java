package hyperDap.base.types.dataSet;

import java.util.ArrayList;

/**
 * An implementation of {@link DataSet} that allows nesting several {@link DataSet} instances.
 * 
 * @author soenk
 *
 * @param <T> The type that the nested DataSet<T> use.
 */
public class NestedDataSet<T> extends DataSet {

  protected final Number nestedBase;
  protected final Number nestedStep;

  public NestedDataSet(Number base, Number step, Number nestedBase, Number nestedStep) {
    super(base, step);
    this.nestedBase = nestedBase;
    this.nestedStep = nestedStep;
    this.values = new ArrayList<DataSet<T>>();
  }

}
