package hyperDap.generator.presInterface;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import hyperDap.base.types.dataSet.ValueDataSet;
import hyperDap.generator.main.GenMain;

/**
 * This class is used to make use of generator functionality from the {@link guiPresentation}
 * module.
 * 
 * @author soenk
 *
 */
public class PresGenerator {

  public static ValueDataSet<Double> generate(Map<String, Double> map) {
    double base = map.remove("base");
    double step = map.remove("step");
    int length = map.remove("length").intValue();
    int biasNumber = 0;
    ArrayList<String> encodings = new ArrayList<>();
    if (map.remove("bias") != null) {
      Random rand = new Random();
      biasNumber = rand.nextInt(length / 20);
    }
    // TODO encodings
    return GenMain.newDataSet(encodings, biasNumber, base, step, length);
  }

}
