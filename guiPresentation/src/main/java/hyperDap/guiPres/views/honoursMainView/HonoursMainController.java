package hyperDap.guiPres.views.honoursMainView;

import java.util.HashMap;
import java.util.Map;
import hyperDap.base.types.dataSet.ValueDataSet;
import hyperDap.guiPres.charts.DisplayDataSet;
import hyperDap.guiPres.fxEncapsulation.GUIMainForFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
  Label functionErrorLabel;

  @FXML
  VBox graphBox;

  @FXML
  Button executeButton;
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

    this.didiMap = new HashMap<CheckBox, String>();
    this.didiMap.put(didi1, "constant");
    this.didiMap.put(didi2, "linear");
    this.didiMap.put(didi3, "square");
    this.didiMap.put(didi4, "cubic");
    this.didiMap.put(didi5, "exp");
    this.didiMap.put(didi6, "sine");
    this.didiMap.put(didi7, "bias");
    this.didiMap.put(didi8, "gap");

    this.setChart = new DisplayDataSet();
    this.graphBox.getChildren().add(this.setChart);

  }

  // fx buttons
  // **************************************************************************************************************************

  public void terminate() {
    this.main.terminate();
  }

  public void execute() {
    Map<String, Double> map = new HashMap<String, Double>();
    double temp;

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
      temp = Double.valueOf(this.lengthField.getText()).intValue();
      if (temp < 10) {
        this.lengthField.setText("");
        this.lengthField.setPromptText("This must be above 10");
        return;
      }
      map.put("length", temp);
    } catch (NumberFormatException e) {
      this.lengthField.setPromptText("This must be a number e.g. '15'");
      this.lengthField.setText("");
      return;
    }

    temp = 0;
    for (CheckBox didi : didiMap.keySet()) {
      if (didi.isSelected() == true) {
        map.put(this.didiMap.get(didi), 1.0);
        temp = temp + 1.0;
      }
    }
    if (temp == 0.0) {
      map.put("constant", 1.0);
    }
    temp = temp * 10;
    if ((map.get("length").doubleValue() / temp) < 1.0) {
      this.functionErrorLabel.setText("The data set is too short for this many functions.");
      return;
    }

    this.main.execute(map);
  }

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
