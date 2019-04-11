package hyperDap.guiPres.views.honoursMainView;

import hyperDap.guiPres.fxEncapsulation.GUIMainForFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
  Button executeButton;
  @FXML
  Button exitButton;

  /**
   * Constructor.
   * <p>
   * Remember that JavaFX elements can only be accessed later, in {@link #initialize()}
   */
  public HonoursMainController() {
    System.out.println("HonoursMainController has been instantiated.");

  }

  /**
   * Called after JavaFX elements have been initialised and can be accessed by the
   * {@code Controller}.
   */
  public void initialize() {

  }

  // fx buttons
  // **************************************************************************************************************************

  public void terminate() {
    this.main.terminate();
  }

  public void execute() {
    // TODO
  }

  // for GUIMain
  // **************************************************************************************************************************

  public void giveGUIMain(GUIMainForFX guiMain) {
    this.main = guiMain;
  }

}
