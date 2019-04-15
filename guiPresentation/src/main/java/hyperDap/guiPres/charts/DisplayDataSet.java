package hyperDap.guiPres.charts;

import hyperDap.base.types.dataSet.ValueDataSet;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

/**
 * This display element is used to show the values of a {@link ValueDataSet} using a
 * {@link LineChart}.
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

  private LineChart<Number, Number> setChart;
  private XYChart.Series<Number, Number> series;
  private ValueAxis<Number> xAxis;
  private ValueAxis<Number> yAxis;

  // Constructors
  // **************************************************************************************************************************

  public DisplayDataSet() {
    super();
    this.setUp();
  }

  public DisplayDataSet(double spacing) {
    super(spacing);
    this.setUp();
  }

  public DisplayDataSet(double spacing, Node... children) throws AssertionError {
    throw new AssertionError(assertionErrorMessage);
  }

  public DisplayDataSet(Node... children) throws AssertionError {
    throw new AssertionError(assertionErrorMessage);
  }

  public DisplayDataSet(ValueDataSet<Number> dataSet) {
    this.set = dataSet;
    this.setUp();
    this.show();
  }

  /**
   * This setup is performed independently of the constructor that is called.
   * 
   * @category helper
   * @category constructor
   */
  private void setUp() {
    this.xAxis = new NumberAxis();
    this.yAxis = new NumberAxis();
    this.setChart = new LineChart<>(xAxis, yAxis);

    this.series = new XYChart.Series<>();
    this.series.setName("DataSet");
    this.setChart.getData().add(this.series);

    this.addToChildren(this.setChart);

    // TODO add derivDepth chart
    // TODO size chart better
  }

  // setters
  // *************************************************************************************************************************

  /**
   * A private helper that allows internally editing children {@link Node Nodes} without exposing
   * this to external classes.
   * 
   * @param node The {@link Node} that is to be added.
   * @category helper
   */
  private void addToChildren(Node node) {
    super.getChildren().add(node);
  }

  /**
   * Allows setting or altering the {@link ValueDataSet} that is displayed in this chart.
   * <p>
   * If a reference to this set is retained it can be edited without using this method again, as
   * long as no race conditions are provoked with {@link #show()}.
   * 
   * @param dataSet
   */
  public void setDataSet(ValueDataSet<? extends Number> dataSet) {
    this.set = dataSet;
    this.show();
  }

  /**
   * Clear the current data display and display the current data points from the currently stored
   * {@link ValueDataSet}.
   * <p>
   * If the {@link ValueDataSet} is undefined a warning is printed and no data is displayed.
   */
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

    this.set.calcDerivDepths();
    this.series.getData().clear();
    for (int i = 0; i < this.set.size(); i++) {
      this.series.getData().add(new XYChart.Data<Number, Number>(this.set.getIndependentValue(i),
          this.set.getByIndex(i)/* ,this.set.getDerivDepthsByIndex(i) */));
    }
    // TODO do the same to derivDepthChart
  }

}
