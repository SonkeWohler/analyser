package hyperDap.base.types.dataSet;

import java.util.ArrayList;
import hyperDap.base.types.value.ValuePair;

/**
 * A {@link NestedDataSet} that holds {@link PairDataSet PairDataSets} and offers functionality to
 * better access and manipulate these {@link ValuePair ValuePairs}.
 * <p>
 * Currently WIP.
 * 
 * @category WIP
 * 
 * @author soenk
 *
 * @param <T> Defines {@link PairDataSet}{@code <T>} and must be a subclass of {@link Number}.
 */

public class NestedPairDataSet<T extends Number> extends NestedDataSet<PairDataSet<T>> {

  protected ArrayList<PairDataSet<T>> values;

  public NestedPairDataSet(Number base, Number step, Number nestedBase, Number nestedStep) {
    super(base, step, nestedBase, nestedStep);
    this.values = new ArrayList<PairDataSet<T>>();
  }

  // TODO

}
