package hyperDap.guiPres.application;

/**
 * 
 * @author soenk
 *
 */
public final class GUIMain {

  private static GUIMain instance;

  public synchronized static GUIMain newGUIMain() {
    if (instance == null) {
      // TODO
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

  public synchronized static GUIMain getGUIMain() {
    if (instance==null) {
      return newGUIMain();
    }
    return instance;
  }

  private GUIMain() {
    if (instance == null) {
      instance = this;
    } else {
      throw new AssertionError(
          String.format("%s has already been instantiated! Only one allowed.", GUIMain.class));
    }
  }

  // main ********************* main ********************** main *********************** main

  public static void main(String[] args) {

  }

}
