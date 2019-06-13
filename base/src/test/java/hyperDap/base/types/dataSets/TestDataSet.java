package hyperDap.base.types.dataSets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import hyperDap.base.types.dataSet.DataSet;

public class TestDataSet {

  private class ConcreteDataSet<T> extends DataSet<T> {

    public ConcreteDataSet(double base, double step) {
      super(base, step);
    }
  }

  // index tests
  // *******************************************************************************************************************************

  @Test
  void testIndex1() {
    double base = 0.0;
    double step = 2.0;
    int index = 3;
    double xValue = 6.0;
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  @Test
  void testIndex2() {
    double base = 0.582;
    double step = 1.78305;
    int index = 3;
    double xValue = 5.93115;
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  @Test
  void testIndexMin() {
    double base = 0.0;
    double step = Double.MIN_VALUE;
    int index = 3;
    double xValue = 3 * Double.MIN_VALUE;
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
    assertEquals(Double.valueOf(set.getIndependentValue(index)), Double.valueOf(xValue));
    assertEquals(Double.valueOf(set.getIndex(xValue)), Double.valueOf(index));
  }

  @Test
  void testIndexNegative() {
    double base = 0.0;
    double step = -5.0;
    int index = 3;
    double xValue = -15.0;
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
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
    ConcreteDataSet<Double> set = new ConcreteDataSet<>(base, step);
    for (int i = 0; i <= index; i++) {
      set.add(entry);
    }
    for (Double e : set) {
      assertEquals(entry, e);
    }
  }

  // TODO other inherited methods from Collection
  // *******************************************************************************************************************************

}
