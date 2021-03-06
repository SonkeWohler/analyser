package hyperDap.guiPres.charts;

import hyperDap.base.types.dataSet.ValueDataSet;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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

  private int counter = 0;
  private boolean displayRunning = false;

  private ValueDataSet<? extends Number> set;

  private LineChart<Number, Number> setChart;
  private LineChart<Number, Number> derivChart;
  private NumberAxis xSetAxis;
  private NumberAxis xDerivAxis;
  private NumberAxis ySetAxis;
  private NumberAxis yDerivAxis;

  private XYChart.Series<Number, Number> setSeries;
  private XYChart.Series<Number, Number> constSeries;
  private XYChart.Series<Number, Number> linearSeries;
  private XYChart.Series<Number, Number> SquareSeries;
  private XYChart.Series<Number, Number> cubicSeries;
  private XYChart.Series<Number, Number> expSeries;
  private XYChart.Series<Number, Number> sinSeries;
  private XYChart.Series<Number, Number> changeSeries;
  private XYChart.Series<Number, Number> undefinedSeries;

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
    this.xSetAxis = new NumberAxis();
    this.ySetAxis = new NumberAxis();
    this.setChart = new LineChart<>(xSetAxis, ySetAxis);

    this.addToChildren(this.setChart);

    this.setSeries = new XYChart.Series<>();
    this.setSeries.setName("DataSet");
    this.setChart.getData().add(this.setSeries);

    this.xDerivAxis = new NumberAxis();
    this.yDerivAxis = new NumberAxis();
    this.derivChart = new LineChart<>(this.xDerivAxis, this.yDerivAxis);

    this.yDerivAxis.setLowerBound(-5.0);
    this.yDerivAxis.setUpperBound(5.0);
    this.yDerivAxis.setTickUnit(1.0);
    this.yDerivAxis.setAutoRanging(false);
    this.yDerivAxis.setMinorTickVisible(false);

    this.addToChildren(this.derivChart);

    this.constSeries = new XYChart.Series<>();
    this.linearSeries = new XYChart.Series<>();
    this.SquareSeries = new XYChart.Series<>();
    this.cubicSeries = new XYChart.Series<>();
    this.expSeries = new XYChart.Series<>();
    this.sinSeries = new XYChart.Series<>();
    this.changeSeries = new XYChart.Series<>();
    this.undefinedSeries = new XYChart.Series<>();

    this.constSeries.setName("Constant: 1");
    this.linearSeries.setName("Linear: 2");
    this.SquareSeries.setName("Square: 3");
    this.cubicSeries.setName("Cubic: 4");
    this.expSeries.setName("Exponential: -1");
    this.sinSeries.setName("Trigonometric: -2");
    this.changeSeries.setName("Point of Interest: 0");
    this.undefinedSeries.setName("Undefined: -5");

    this.derivChart.getData().add(this.constSeries);
    this.derivChart.getData().add(this.linearSeries);
    this.derivChart.getData().add(this.SquareSeries);
    this.derivChart.getData().add(this.cubicSeries);
    this.derivChart.getData().add(this.expSeries);
    this.derivChart.getData().add(this.sinSeries);
    this.derivChart.getData().add(this.changeSeries);
    this.derivChart.getData().add(this.undefinedSeries);

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
   * long as no race conditions are provoked with {@link #showData()}.
   * 
   * @param dataSet
   */
  public void setDataSet(ValueDataSet<? extends Number> dataSet) {
    this.set = dataSet;
    this.showData();
  }

  /**
   * Clear the current data display and display the current data points from the currently stored
   * {@link ValueDataSet}.
   * <p>
   * If the {@link ValueDataSet} is undefined a warning is printed and no data is displayed.
   */
  @Deprecated
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
    this.setSeries.getData().clear();
    double xVal;
    int depth;
    for (int i = 0; i < this.set.size(); i++) {
      xVal = this.set.getIndependentValue(i);
      this.setSeries.getData().add(new XYChart.Data<Number, Number>(xVal,
          this.set.getByIndex(i)/* ,this.set.getDerivDepthsByIndex(i) */));
      depth = this.set.getDerivDepthsByIndex(i);
      this.switchSeries(depth).getData().add(new XYChart.Data<Number, Number>(xVal, depth));
    }
  }

  private XYChart.Series<Number, Number> switchSeries(int derivDepth) {
    XYChart.Series<Number, Number> ser;
    switch (derivDepth) {
      case 0:
        ser = this.constSeries;
        break;
      case 1:
        ser = this.linearSeries;
        break;
      case 2:
        ser = this.SquareSeries;
        break;
      case 3:
        ser = this.cubicSeries;
        break;
      case -1:
        ser = this.changeSeries;
        break;
      case -2:
        ser = this.expSeries;
        break;
      case -3:
        ser = this.sinSeries;
        break;
      default:
        ser = this.undefinedSeries;
    }
    return ser;
  }

  private void clearSeries() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        setSeries.getData().clear();
        constSeries.getData().clear();
        linearSeries.getData().clear();
        SquareSeries.getData().clear();
        cubicSeries.getData().clear();
        expSeries.getData().clear();
        sinSeries.getData().clear();
        changeSeries.getData().clear();
        undefinedSeries.getData().clear();
      }
    });
  }

  /**
   * Initiates the recursive addition of data points from the internal {@link ValueDataSet} to the
   * two displayed graphs. The {@link #runLaterCall()} and {@link #displayPoints()} methods are
   * called recursively to count through the entire {@link ValueDataSet}.
   * <p>
   * This is done successively to prevent a slow-down of the gui while
   * {@link Platform#runLater(Runnable)} is executed, which is necessary to prevent race conditions
   * in relation to JavaFX elements.
   */
  public void showData() {
    // in case showData is already in progress
    if (this.displayRunning) {
      // notify the progress to terminate
      this.displayRunning = false;
      // print warning
      System.err.println(String.format("%s aborting the running display!", DisplayDataSet.class));
      boolean first = true;
      for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
        if (first == true) {
          first = false;
          continue;
        }
        System.err.println(element.toString());
      }
      // wait for progress to terminate
      while (this.displayRunning == false) {
        // TODO timeout -> throw runtime-exception
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } else {
      System.out.println("Initiation runLater recursion to display DataSet");
    }
    // Platform.runLater for every 10 data points
    this.clearSeries();
    this.counter = 0;
    this.displayRunning = true;
    this.runLaterCall();
  }

  /**
   * Calls {@link Platform#runLater(Runnable)} to update data points in charts by calling
   * {@link #displayPoints()} without causing race conditions.
   */
  private void runLaterCall() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        System.out.println(String.format("Executing runLater for DataSet display, counter: %s/%s",
            counter, set.size()));
        displayPoints();
      }
    });
  }

  /**
   * This method should only be called by {@link Platform#runLater(Runnable)} to prevent race
   * conditions within JavaFX elements.
   * <p>
   * It adds the next 10 data points and their derivDepths to the respective graphs, before calling
   * {@link #runLaterCall()} to recursively continue he process, if necessary. A class internal
   * counter is used to count through the entire set in the recursive method invocations.
   */
  public void displayPoints() {
    // terminate if showData() was called again
    if (this.displayRunning == false) {
      this.displayRunning = true;
      return;
    }
    // only add the next 10 values, or until the end of the set
    int max = this.counter + 10;
    if (max > this.set.size()) {
      max = this.set.size();
    }
    // add values
    double xVal;
    int depth;
    while (this.counter < max) {
      // add data point to setSeries
      xVal = this.set.getIndependentValue(counter);
      if (this.set.getValidByIndex(counter) == true) {
        this.setSeries.getData()
            .add(new XYChart.Data<Number, Number>(xVal, this.set.getByIndex(counter)));
      }
      // add derivDepth to the correct series
      depth = this.set.getDerivDepthsByIndex(counter);
      try {
        this.switchSeries(depth).getData().add(new XYChart.Data<Number, Number>(xVal, depth + 1));
      } catch (NullPointerException e) {
        System.err.println(String.format("Undefined derivDepth for %s at index %s!",
            DisplayDataSet.class, counter));
        this.undefinedSeries.getData().add(new XYChart.Data<Number, Number>(xVal, -5.0));
      }
      // TODO do not show last 10 derivDepths on chart. could remove them
      // counter
      this.counter++;
    }
    // add another runLater if needed
    if (this.counter < this.set.size() && this.displayRunning == true) {
      this.runLaterCall();
    } else {
      this.displayRunning = false;
    }
  }

}
