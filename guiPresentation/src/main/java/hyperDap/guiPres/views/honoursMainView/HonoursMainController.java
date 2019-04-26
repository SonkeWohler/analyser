package hyperDap.guiPres.views.honoursMainView;

import java.util.HashMap;
import java.util.Map;
import hyperDap.base.types.dataSet.ValueDataSet;
import hyperDap.guiPres.charts.DisplayDataSet;
import hyperDap.guiPres.fxEncapsulation.GUIMainForFX;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * This is the {@code Controller} to the honoursMainView, to demonstrate application features at the
 * completion of the author's Honours Project.
 * 
 * @author soenk
 *
 */
public class HonoursMainController {

  GUIMainForFX main;

  @FXML
  TextField baseField;
  @FXML
  Button baseRandButton;
  @FXML
  TextField stepField;
  @FXML
  Button stepRandButton;
  @FXML
  TextField lengthField;
  @FXML
  Button lengthRandButton;
  @FXML
  TextField precisionField;
  @FXML
  Button precisionRandomField;

  @FXML
  CheckBox didi1;
  @FXML
  CheckBox didi2;
  @FXML
  CheckBox didi3;
  @FXML
  CheckBox didi4;
  @FXML
  CheckBox didi5;
  @FXML
  CheckBox didi6;
  @FXML
  CheckBox didi7;
  @FXML
  CheckBox didi8;

  Map<CheckBox, String> didiMap;

  @FXML
  VBox graphBox;

  @FXML
  Button executeButton;
  @FXML
  Button executeButton2;
  @FXML
  Button exitButton;

  private DisplayDataSet setChart;

  /**
   * Constructor.
   * <p>
   * Remember that JavaFX elements can only be accessed later, in {@link #initialize()}
   */
  public HonoursMainController() {
    System.out.println(String.format("%s has been instantiated", HonoursMainController.class));
  }

  /**
   * Called after JavaFX elements have been initialised and can be accessed by the
   * {@code Controller}.
   */
  public void initialize() {

    // for reading in whcih functions should be plotted
    this.didiMap = new HashMap<CheckBox, String>();
    this.didiMap.put(didi1, "constant");
    this.didiMap.put(didi2, "linear");
    this.didiMap.put(didi3, "square");
    this.didiMap.put(didi4, "cubic");
    this.didiMap.put(didi5, "exp");
    this.didiMap.put(didi6, "sine");
    this.didiMap.put(didi7, "bias");
    this.didiMap.put(didi8, "noise");

    // the graphs used for display
    this.setChart = new DisplayDataSet();
    this.graphBox.getChildren().add(this.setChart);

    // a boolean property to help unfocus at startup
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);
    this.baseField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue && firstTime.get()) {
        firstTime.set(false);
        this.baseField.getParent().requestFocus();
      }
    });

  }

  // fx interface
  // **************************************************************************************************************************

  public void terminate() {
    this.main.terminate();
  }

  public void execute() {
    Map<String, Double> map = new HashMap<String, Double>();
    Double temp;

    try {
      temp = Double.valueOf(this.baseField.getText());
      map.put("base", temp);
    } catch (NumberFormatException e) {
      this.baseField.setPromptText("This must be a number e.g. '5.0'");
      this.baseField.setText("");
      return;
    }
    try {
      temp = Double.valueOf(this.stepField.getText());
      map.put("step", temp);
    } catch (NumberFormatException e) {
      this.stepField.setPromptText("This must be a number e.g. '5.0'");
      this.stepField.setText("");
      return;
    }
    try {
      temp = Integer.valueOf(this.lengthField.getText()).doubleValue();
      if (temp < 10) {
        this.lengthField.setText("");
        this.lengthField.setPromptText("This must be above 10");
        return;
      }
      map.put("length", temp);
    } catch (NumberFormatException e) {
      this.lengthField.setPromptText("This must be a number e.g. '20'");
      this.lengthField.setText("");
      return;
    }
    try {
      temp = Double.valueOf(this.precisionField.getText());
      map.put("precision", temp);
    } catch (NumberFormatException e) {
      this.precisionField.setPromptText("This must be a number e.g. '0.001'");
      this.precisionField.setText("");
      return;
    }

    temp = 0.0;
    for (CheckBox didi : didiMap.keySet()) {
      if (didi.isSelected() == true) {
        map.put(this.didiMap.get(didi), 1.0);
        temp = temp + 1.0;
      }
    }
    if (temp == 0.0) {
      map.put("constant", 1.0);
    }
    if ((map.get("length").doubleValue() / temp) - 2.0 < 10.00) {
      temp = temp * 12.0;
      this.lengthField.setPromptText(
          String.format("Must have at least %s points for these functions", temp.intValue()));
      this.lengthField.setText("");
      return;
    }

    this.main.execute(map);
  }

  public void baseDefault() {
    this.baseField.setText("0.0");
  }

  public void stepDefault() {
    this.stepField.setText("1.0");
  }

  public void lengthDefault() {
    this.lengthField.setText("50");
  }

  public void precisionDefault() {
    this.precisionField.setText("0.001");
  }

  // public void setPrecision() {
  // Double precision;
  // try {
  // precision = Double.parseDouble(this.precisionField.getText());
  // } catch (NumberFormatException ne) {
  // this.precisionField.setPromptText("Invalid Number Format!");
  // this.precisionField.setText("");
  // return;
  // } catch (NullPointerException e) {
  // return;
  // }
  // Tangenter.setPrecision(precision);
  // this.precisionField.setPromptText("Adjust Precision");
  // this.precisionField.setText("");
  // System.out
  // .println(String.format("User set new pprecision of %s in %s!", precision, Tangenter.class));
  // }

  // for GUIMain
  // **************************************************************************************************************************

  public void giveGUIMain(GUIMainForFX guiMain) {
    this.main = guiMain;
  }

  public void displayDataSet(ValueDataSet<? extends Number> dataSet) {
    System.out.println("Displaying new DataSet");
    this.setChart.setDataSet(dataSet);
    // this.setChart.showData();
  }

}
