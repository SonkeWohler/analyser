package hyperDap.generator.main;

import java.util.List;
import java.util.Map;
import java.util.Random;
import hyperDap.base.types.dataSet.DataSet;
import hyperDap.base.types.dataSet.ValueDataSet;

public class GenMain {

  /**
   * Create a new {@link ValueDataSet} made up of any number of mathematical functions combined.
   * 
   * @param functionEncodings The {@code encoding} specifying what functions should be represented.
   *        See {@link GenSegment} for details.
   * @param numberOfBiases The number of times that the value of the functions should abruptly
   *        change, affecting all subsequent values.
   * @param base The {@code base} of the {@link DataSet}.
   * @param step The {@code step} of the {@link DataSet}.
   * @param length A rough number of the data points that is to be generated.
   * @return The generated {@link ValueDataSet}
   */
  public static ValueDataSet<Double> newDataSet(List<String> functionEncodings, int numberOfBiases,
      double base, double step, int length) {
    Random rand = new Random();
    int size = functionEncodings.size();
    ValueDataSet<Double> set = new ValueDataSet<Double>(base, step, 0.1, d -> Double.valueOf(d));
    set.add(5.0);
    double lastVal;
    GenSegment generator;
    for (String encoding : functionEncodings) {
      lastVal = set.getByIndex(set.size() - 1);
      generator = new GenSegment(encoding, Double.valueOf(rand.nextInt(10)) - 4.0,
          Double.valueOf(rand.nextInt(30)) - 15.0, lastVal);
      generator.addToDoubleDataSet(set, length / size);
      if (numberOfBiases != 0) { // doesn't work yet!
        lastVal += rand.nextInt(10) - 5.0;
        numberOfBiases--;
        lastVal = set.getByIndex(set.size() - 1);
        generator = new GenSegment(encoding, Double.valueOf(rand.nextInt(10)) - 4.0,
            Double.valueOf(rand.nextInt(30)) - 15.0, lastVal);
        generator.addToDoubleDataSet(set, length / size);
      }
    }
    return set;
  }

  /**
   * Create a new {@link ValueDataSet} according to specifications.
   * <p>
   * This method provides more control than {@link #newDataSet(List, Number, double, double, int)},
   * following exactly the fractions specified in the {@code encodingMap} and generating data points
   * of the specified function up to this point.
   * 
   * @param encodingMap From {@link Double} (must be between 0 and 1) that specifies a fraction of
   *        the total length of the {@link DataSet}, to the function encoding of the type of data
   *        that should be generated up to that point.
   * @param base The {@code base} of the {@link DataSet}
   * @param step The {@code step} of the {@link DataSet}
   * @param length The number of data points in the completed {@link DataSet}
   */
  @SuppressWarnings("unused")
  private void newDataSet(Map<Double, String> encodingMap, double base, double step, int length) {

  }

}
