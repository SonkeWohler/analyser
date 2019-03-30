package hyperDap.base.types.dataSet;

import java.util.ArrayList;

/**
 * An implementation of {@link DataSet} that allows nesting several DataSets, used as the superclass
 * to nested DataSet classes.
 * 
 * @author soenk
 *
 * @param <T> The type that the nested DataSet<T> use.
 */
public class NestedDataSet<Set extends DataSet<?>> extends DataSet<Set> {

  protected final Number nestedBase;
  protected final Number nestedStep;

  public NestedDataSet(Number base, Number step, Number nestedBase, Number nestedStep) {
    super(base, step);
    this.nestedBase = nestedBase;
    this.nestedStep = nestedStep;
    this.values = new ArrayList<Set>();
  }

}
