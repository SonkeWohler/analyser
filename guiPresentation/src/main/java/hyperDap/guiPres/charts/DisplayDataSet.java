package hyperDap.guiPres.charts;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DisplayDataSet extends VBox {

  private static String assertionErrorMessage = String
      .format("%s are not editable and no children may be added to it.", DisplayDataSet.class);

  // Constructors

  public DisplayDataSet() {
    super();
  }

  public DisplayDataSet(double spacing) {
    super(spacing);
  }

  public DisplayDataSet(double spacing, Node... children) throws AssertionError {
    throw new AssertionError(assertionErrorMessage);
  }

  public DisplayDataSet(Node... children) throws AssertionError {
    throw new AssertionError(assertionErrorMessage);
  }

  // public DisplayDataSet(ValueDataSet<Number> dataSet) {}

  @Override
  public ObservableList<Node> getChildren() {
    throw new AssertionError(assertionErrorMessage);
  }

}
