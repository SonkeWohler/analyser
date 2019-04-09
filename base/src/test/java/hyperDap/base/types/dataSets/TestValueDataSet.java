package hyperDap.base.types.dataSets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import hyperDap.base.types.dataSet.ValueDataSet;
import hyperDap.base.types.value.ValuePair;

public class TestValueDataSet {

  private double precisionGlobal = 0.5;

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

  // from DataSet
  // *************************************************************************************************************

  // index tests
  // *******************************************************************************************************************************

  @Test
  void testIndex1() {
    double base = 0.0;
    double step = 2.0;
    int index = 3;
    double xValue = 6.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  @Test
  void testIndex2() {
    double base = 0.582;
    double step = 1.78305;
    int index = 3;
    double xValue = 5.93115;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  @Test
  void testIndexMin() {
    double base = 0.0;
    double step = Double.MIN_VALUE;
    int index = 3;
    double xValue = 3 * Double.MIN_VALUE;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  @Test
  void testIndexNegative() {
    double base = 0.0;
    double step = -5.0;
    int index = 3;
    double xValue = -15.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  // add
  // *******************************************************************************************************************************
  // also using the getter methods

  @Test
  void testAddBasic() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double xValue = 3.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    for (int i = 0; i <= index; i++) {
      set.add(entry);
    }
    assertEquals(set.getByIndex(index), entry);
    assertEquals(set.get(xValue), entry);
    assertEquals(set.get(xValue + 0.499999999999999), entry);
    assertEquals(set.get(xValue - 0.5), entry);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(-1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(-1);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(xValue + 1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(index + 1);
    });
    assertEquals(Integer.valueOf(set.size()), Integer.valueOf(4));
  }

  @Test
  void testAddQuick() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double xValue = 3.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    for (int i = 0; i <= index; i++) {
      set.quickAdd(entry);
    }
    assertEquals(set.getByIndex(index), entry);
    assertEquals(set.get(xValue), entry);
    assertEquals(set.get(xValue + 0.499999999999999), entry);
    assertEquals(set.get(xValue - 0.5), entry);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(-1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(-1);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(xValue + 1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(index + 1);
    });
    assertEquals(Integer.valueOf(set.size()), Integer.valueOf(4));
  }

  @Test
  void testAddValue() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double[] xValues = {0.0, 1.0, 2.0, 3.0};
    double xValue = 3.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    for (int i = 0; i <= index; i++) {
      assertEquals(Double.valueOf(set.addValue(entry)), Double.valueOf(xValues[i]));
    }
    assertEquals(set.getByIndex(index), entry);
    assertEquals(set.get(xValue), entry);
    assertEquals(set.get(xValue + 0.499999999999999), entry);
    assertEquals(set.get(xValue - 0.5), entry);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(-1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(-1);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(xValue + 1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(index + 1);
    });
    assertEquals(Integer.valueOf(set.size()), Integer.valueOf(4));
  }

  @Test
  void testAddByIndex() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double xValue = 3.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    set.add(index, entry);
    assertEquals(set.getByIndex(index), entry);
    assertEquals(set.get(xValue), entry);
    assertEquals(set.get(xValue + 0.499999999999999), entry);
    assertEquals(set.get(xValue - 0.5), entry);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(-1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(-1);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(xValue + 1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(index + 1);
    });
    assertEquals(Integer.valueOf(set.size()), Integer.valueOf(4));
  }

  @Test
  void testAddByValue() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double xValue = 3.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    set.add(xValue, entry);
    assertEquals(set.getByIndex(index), entry);
    assertEquals(set.get(xValue), entry);
    assertEquals(set.get(xValue + 0.499999999999999), entry);
    assertEquals(set.get(xValue - 0.5), entry);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(-1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(-1);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(xValue + 1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(index + 1);
    });
    assertEquals(Integer.valueOf(set.size()), Integer.valueOf(4));
  }

  @Test
  void testAddByValueApprox() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    double xValue = 3.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    set.add(xValue + .499999999999999, entry);
    assertEquals(set.getByIndex(index), entry);
    assertEquals(set.get(xValue), entry);
    assertEquals(set.get(xValue + 0.499999999999999), entry);
    assertEquals(set.get(xValue - 0.5), entry);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(-1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(-1);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(xValue + 1.0);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.get(index + 1);
    });
    assertEquals(Integer.valueOf(set.size()), Integer.valueOf(4));
  }

  @Test
  void addNegative() {
    double base = 0.0;
    double step = 1.0;
    Double entry = 3.0;
    // double xValue = 3s3x.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.add(-1, entry);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.add(-1.0, entry);
    });
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.add(-0.5000000000000001, entry);
    });
  }

  // empty
  // *******************************************************************************************************************************

  @Test
  void testEmpty() {
    double base = 0.0;
    double step = 1.0;
    Double entry = 3.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    assertEquals(set.size(), 0);
    assertTrue(set.isEmpty());
    assertThrows(IndexOutOfBoundsException.class, () -> {
      set.getByIndex(0);
    });
    set.add(entry);
    assertEquals(set.size(), 1);
    assertFalse(set.isEmpty());
    set.getByIndex(0);
  }

  @Test
  void testClear() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    Double entry = 3.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    for (int i = 0; i <= index; i++) {
      set.add(entry);
    }
    assertFalse(set.isEmpty());
    set.clear();
    assertTrue(set.isEmpty());
  }

  // iterator
  // *******************************************************************************************************************************

  @Test
  void testIterator() {
    double base = 0.0;
    double step = 1.0;
    int index = 3;
    Double entry = 3.0;
    ValueDataSet<Double> set = new ValueDataSet<>(base, step, this.precisionGlobal);
    for (int i = 0; i <= index; i++) {
      set.add(entry);
    }
    for (Double e : set) {
      assertEquals(entry, e);
    }
  }


}
