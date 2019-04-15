package hyperDap.guiPres.charts;

import hyperDap.base.types.dataSet.ValueDataSet;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

/**
 * This display element is used to show the values of a {@link ValueDataSet} using a
 * {@link XYChart}.
 * <p>
 * It retains a reference to the {@link ValueDataSet} that it displays, allowing the set to be
 * altered externally, as long as {@link #show()} is not executed at the same time.
 * 
 * @author soenk
 *
 */
public class DisplayDataSet extends VBox {

  private static String assertionErrorMessage = String
      .format("%s are not editable and no children may be added to it.", DisplayDataSet.class);

  private ValueDataSet<? extends Number> set;

  // Constructors

  public DisplayDataSet() {
    super();
    // TODO
  }

  public DisplayDataSet(double spacing) {
    super(spacing);
    // TODO
  }

  public DisplayDataSet(double spacing, Node... children) throws AssertionError {
    throw new AssertionError(assertionErrorMessage);
  }

  public DisplayDataSet(Node... children) throws AssertionError {
    throw new AssertionError(assertionErrorMessage);
  }

  public DisplayDataSet(ValueDataSet<Number> dataSet) {
    this.set = dataSet;
    this.show();
    // TODO
  }

  @Override
  public ObservableList<Node> getChildren() {
    throw new AssertionError(assertionErrorMessage);
  }

  public void setDataSet(ValueDataSet<? extends Number> dataSet) {
    this.set = dataSet;
    this.show();
  }

  public void show() {
    if (this.set == null) {
      System.err.println(String.format("%s.set is undefined!", DisplayDataSet.class));
      boolean first = true;
      for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
        if (first == true) {
          first = false;
          continue;
        }
        System.err.println(element.toString());
      }
      return;
    }
    // TODO
    // remove all data points and replace them with the current set.
    // use extraValue to display derivDepth?
  }

}
