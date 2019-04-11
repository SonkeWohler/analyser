package hyperDap.guiPres.application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the core class of the {@link guiPresentation} module.
 * 
 * @see javafx.application.Application
 * @see <a href=
 *      "https://stackoverflow.com/questions/33881046/how-to-connect-fx-controller-with-main-app">
 *      this StackOverflow post<a/>
 * @see #start(Stage)
 * 
 * @author soenk
 *
 */
public final class GUIMain extends Application {

  private static GUIMain instance;

  /**
   * Create a unique {@code GUIMain} instance if it does not already exist.
   * 
   * @return The only allowed instance of {@code GUIMain}
   * 
   * @see #GUIMain()
   */
  public synchronized static GUIMain newGUIMain() {
    if (instance == null) {
      Application.launch(GUIMain.class);
    }
    while (instance == null) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return instance;
  }

  /**
   * Gain access to an instance of {@code GUIMain}
   * 
   * @return The only allowed instance of {@code GUIMain}
   * 
   * @see #newGUIMain()
   * @see #GUIMain()
   */
  public synchronized static GUIMain getGUIMain() {
    if (instance == null) {
      return newGUIMain();
    }
    return instance;
  }

  /**
   * Only one instance of {@code GUIMain} is allowed, which is managed by {@link #newGUIMain()} and
   * {@link #getGUIMain()}
   * <p>
   * This is required due to the forced encapsulation of JavaFX, see <a href=
   * "https://stackoverflow.com/questions/33881046/how-to-connect-fx-controller-with-main-app">this
   * StackOverflow post<a/>
   */
  private GUIMain() {
    if (instance == null) {
      instance = this;
    } else {
      throw new AssertionError(
          String.format("%s has already been instantiated! Only one allowed.", GUIMain.class));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub

  }

  // main ********************* main ********************** main *********************** main

  public static void main(String[] args) {

  }

}
