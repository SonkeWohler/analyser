package hyperDap.base.testHelpers;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import hyperDap.base.helpers.Tangenter;
import hyperDap.base.types.dataSet.ValueDataSet;

public class TestCalcDerivDepth {

  // simple polynomials
  // *******************************************************************************************************************

  @Test
  void constant() {
    double value = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(value);
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      assertEquals(0, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void linear() {
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + i);
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(1, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void square() {
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, 2));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(2, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void cubic() {
    int power = 3;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void quad() {
    int power = 4;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void polynom5() {
    int power = 5;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void polynom6() {
    int power = 6;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  // @Test
  void polynom7() {
    int power = 7;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  // @Test
  void polynom8() {
    int power = 8;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  // @Test // here the maxDepth is reached and Integer.MAX_VALUE is assigned
  void polynom9() {
    int power = 9;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(Integer.MAX_VALUE, set.getDerivDepthsByIndex(i));
    }
  }

  // changes
  // ********************************************************************************************************************

  @Test
  void constantToLinear() {
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0);
    }
    for (int i = 1; i < 51; i++) {
      set.add(5.0 + i);
    }
    set.calcDerivDepths();
    // System.out.println("\nderivDepths:");
    for (int i = 0; i < 49; i++) {
      // System.out.println(String.format("%s: %s", i, set.getDerivDepthsByIndex(i)));
      assertEquals(0, set.getDerivDepthsByIndex(i));
    }
    // System.out.println(String.format("%s: %s", 49, set.getDerivDepthsByIndex(49)));
    assertEquals(-1, set.getDerivDepthsByIndex(49));
    for (int i = 50; i < set.size() - 10; i++) {
      // System.out.println(String.format("%s: %s", i, set.getDerivDepthsByIndex(i)));
      assertEquals(1, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void constantToSquare() {
    int power = 2;
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0);
    }
    for (int i = 1; i < 51; i++) {
      set.add(5.0 + Math.pow(i, power));
    }
    set.calcDerivDepths();
    // System.out.println("\nderivDepths:");
    for (int i = 0; i < 49; i++) {
      // System.out.println(String.format("%s: %s", i, set.getDerivDepthsByIndex(i)));
      assertEquals(0, set.getDerivDepthsByIndex(i));
    }
    // System.out.println(String.format("%s: %s", 49, set.getDerivDepthsByIndex(49)));
    assertEquals(-1, set.getDerivDepthsByIndex(49));
    for (int i = 50; i < set.size() - 10; i++) {
      // System.out.println(String.format("%s: %s", i, set.getDerivDepthsByIndex(i)));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  @Test
  void biasInConstant() {
    ValueDataSet<Double> set = new ValueDataSet<Double>(0, 1, 0.5);
    for (int i = 0; i < 50; i++) {
      set.add(5.0);
    }
    for (int i = 1; i < 51; i++) {
      set.add(10.0);
    }
    set.calcDerivDepths();
    // System.out.println("\nderivDepths:");
    for (int i = 0; i < 49; i++) {
      // System.out.println(String.format("%s: %s", i, set.getDerivDepthsByIndex(i)));
      assertEquals(0, set.getDerivDepthsByIndex(i));
    }
    // System.out.println(String.format("%s: %s", 49, set.getDerivDepthsByIndex(49)));
    assertEquals(-1, set.getDerivDepthsByIndex(49));
    for (int i = 50; i < set.size() - 10; i++) {
      // System.out.println(String.format("%s: %s", i, set.getDerivDepthsByIndex(i)));
      assertEquals(0, set.getDerivDepthsByIndex(i));
    }
  }

  // further polynomial
  // **********************************************************************************************************************

  @Test
  void sqareWithinConstant() {
    int power = 2;
    double base = 0;
    double step = 1;
    double value = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<Double>(base, step, 0.1);
    for (int i = 0; i < 30; i++) {
      set.add(value);
    }
    double temp = 0;
    for (int i = 0; i < 10; i++) {
      temp = value + Math.pow(base + i * step, power);
      set.add(temp);
    }
    for (int i = 0; i < 30; i++) {
      set.add(temp);
    }
    for (int i = 0; i < set.size() - 10; i++) {
      System.out
          .println(String.format("%s => %s", set.getByIndex(i), set.getDerivDepthsByIndex(i)));
    }
  }

  @Test
  void squareOverZero() {
    int power = 2;
    double base = -10;
    double step = 0.1;
    ValueDataSet<Double> set = new ValueDataSet<Double>(base, step, 0.1);
    for (int i = 0; i < 500; i++) {
      set.add(Math.pow(base + i * step, power));
    }
    for (int i = 0; i < set.size() - 10; i++) {
      // System.out.println(set.getDerivDepthsByIndex(i));
      assertEquals(power, set.getDerivDepthsByIndex(i));
    }
  }

  /**
   * This demonstrates a floating point error in {@link Tangenter} when using
   * {@link Tangenter#tangentSimple(double,double,double)}.
   */
  // @Test
  void squareOverZeroBIG() {
    BigDecimal step = BigDecimal.valueOf(0.1);
    BigDecimal base = BigDecimal.valueOf(-10);
    ValueDataSet<BigDecimal> set =
        new ValueDataSet<BigDecimal>(base.doubleValue(), step.doubleValue(), 0.1);

    BigDecimal value;
    for (int i = 0; i < 500; i++) {
      value = step.multiply(BigDecimal.valueOf(i));
      value = value.add(base);
      value = value.pow(2);
      set.add(value);
    }
    for (int i = 0; i < 500; i++) {
      System.out
          .println(String.format("%s => %s", set.getByIndex(i), set.getDerivDepthsByIndex(i)));
    }

  }

}
// end of class
