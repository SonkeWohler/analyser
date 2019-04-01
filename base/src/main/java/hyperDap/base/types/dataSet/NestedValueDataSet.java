package hyperDap.base.types.dataSet;

import java.util.ArrayList;

/**
 * A {@link NestedDataSet} that contains specifically {@link ValueDataSet ValueDataSets} and offers
 * functionality to better access and manipulate values in these Sets.
 * <p>
 * Currently WIP.
 * 
 * @category WIP
 * 
 * @author soenk
 *
 * @param <T> Defines {@link ValueDataSet}{@code <T>} and must be a subtype of {@link Number}.
 */

public class NestedValueDataSet<T extends Number> extends NestedDataSet<ValueDataSet<T>> {

  protected ArrayList<ValueDataSet<T>> values;

  public NestedValueDataSet(Number base, Number step, Number nestedBase, Number nestedStep) {
    super(base, step, nestedBase, nestedStep);
    this.values = new ArrayList<ValueDataSet<T>>();
  }

  // TODO


}
