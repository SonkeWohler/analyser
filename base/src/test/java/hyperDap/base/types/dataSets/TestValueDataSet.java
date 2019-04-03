package hyperDap.base.types.dataSets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import hyperDap.base.types.dataSet.ValueDataSet;
import hyperDap.base.types.value.ValuePair;

public class TestValueDataSet {

  @Test
  void testContainsDouble() {
    double base = 0.0;
    double step = 1.0;
    double xValue = 3.0;
    double precision = 0.5;
    Double entry = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, precision);
    set.add(xValue, entry);
    assertTrue(set.contains(xValue, entry));
    assertTrue(set.contains(xValue + 0.49999999999999, entry + 0.49999999999999));
    assertTrue(set.contains(xValue - 0.5, entry - 0.5));
    assertFalse(set.contains(10.0, entry));
    assertFalse(set.contains(xValue, 2.0));
  }

  @Test
  void testContainsPrecision() {
    double base = 0.0;
    double step = 1.0;
    double xValue = 3.0;
    double precision = 0.5;
    double xPrecision = 2.0;
    double yPrecision = 2.0;
    Double entry = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, precision);
    set.add(xValue, entry);
    assertEquals(Double.valueOf(set.getMaxIndependentValue()), Double.valueOf(xValue));
    assertTrue(set.contains(xValue, entry, xPrecision, yPrecision));
    assertTrue(set.contains(0.0, entry, xPrecision, yPrecision));
    assertTrue(set.contains(0.0, entry, 0.0, 0.0));
    assertFalse(set.contains(0.5, entry, 0.1, 0.1));
    assertFalse(set.contains(xValue, 2.0, 0.1, 0.1));
    assertFalse(set.contains(xValue, 0.0, xPrecision, yPrecision));
  }

  @Test
  void testContainsIndex() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double xValue = 3.0;
    double precision = 0.5;
    Double entry = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, precision);
    set.add(xValue, entry);
    assertTrue(set.contains(index, entry));
    assertFalse(set.contains(index + 1, entry));
    assertFalse(set.contains(index, 0.0));
  }

  @Test
  void testContainsPair() {
    double base = 0.0;
    double step = 1.0;
    double xValue = 3.0;
    double precision = 0.5;
    Double entry = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, precision);
    set.add(xValue, entry);
    assertTrue(set.contains(new ValuePair<Double>(xValue, entry)));
    assertTrue(
        set.contains(new ValuePair<Double>(xValue + 0.49999999999999, entry + 0.49999999999999)));
    assertTrue(set.contains(new ValuePair<Double>(xValue - 0.5, entry - 0.5)));
    assertFalse(set.contains(new ValuePair<Double>(10.0, entry)));
    assertFalse(set.contains(new ValuePair<Double>(xValue, 2.0)));
  }

  @Test
  void testAddPair() {
    double base = 0.0;
    double step = 1.0;
    double xValue = 3.0;
    double precision = 0.5;
    Double entry = 5.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, precision);
    set.add(new ValuePair<Double>(xValue, entry));
    assertEquals(Double.valueOf(set.getMaxIndependentValue()), Double.valueOf(xValue));
    assertTrue(set.contains(xValue, entry));
    assertTrue(set.contains(xValue + 0.49999999999999, entry + 0.49999999999999));
    assertTrue(set.contains(xValue - 0.5, entry - 0.5));
    assertFalse(set.contains(10.0, entry));
    assertFalse(set.contains(xValue, 2.0));
  }

}
