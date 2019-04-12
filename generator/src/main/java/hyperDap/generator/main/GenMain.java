package hyperDap.generator.main;

import java.util.List;
import java.util.Map;
import hyperDap.base.types.dataSet.DataSet;
import hyperDap.base.types.dataSet.ValueDataSet;

public class GenMain {



  public void newDataSet(List<String> functionEncodings, Number numberOfBiases, double base,
      double step, int length) {
    ValueDataSet<Double> set = new ValueDataSet<Double>(base, step, 0.1);
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
