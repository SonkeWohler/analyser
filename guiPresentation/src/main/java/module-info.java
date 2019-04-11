/**
* 
*/
/**
 * @author soenk
 *
 */
module guiPresentation {
  exports hyperDap.guiPres.application;

  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;

  opens hyperDap.guiPres.application to javafx.graphics, javafx.fxml;
}
