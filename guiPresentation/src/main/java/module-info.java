/**
* 
*/
/**
 * A module to demonstrate features of basic analyser implementations independently.
 * <p>
 * This is in particular used to present the course-work aspects of the software, but may be
 * extended to any further features.
 * 
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
