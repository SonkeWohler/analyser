package hyperDap.base.types.valueTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import hyperDap.base.types.value.ValuePair;


public class TestValuePair {

  @Test
  void testDoubleBasic() {
    double x = -5.0;
    double y = 8.3;
    ValuePair<Double> pair = new ValuePair<Double>(x, y);
    assertEquals(pair.getX(), Double.valueOf(x));
    assertEquals(pair.getY(), Double.valueOf(y));
  }

  @Test
  void testDoubleMax() {
    Double x = Double.MAX_VALUE;
    Double y = -Double.MAX_VALUE;
    ValuePair<Double> pair = new ValuePair<Double>(x, y);
    assertEquals(pair.getX(), Double.valueOf(x));
    assertEquals(pair.getY(), Double.valueOf(y));
  }

  @Test
  void testDoubleMin() {
    Double x = Double.MIN_VALUE;
    Double y = -Double.MIN_VALUE;
    ValuePair<Double> pair = new ValuePair<Double>(x, y);
    assertEquals(pair.getX(), Double.valueOf(x));
    assertEquals(pair.getY(), Double.valueOf(y));
  }

  @Test
  void testEqualsPair() {
    Double x = -5.0;
    Double y = 8.3;
    ValuePair<Double> pair1 = new ValuePair<Double>(x, y);
    ValuePair<Double> pair2 = new ValuePair<>(x, y);
    assertTrue(pair1.equals(pair2));
    assertNotSame(pair1, pair2);
  }

  @Test
  void testNotEqualsPair() {
    Double x = -5.0;
    Double y = 8.3;
    ValuePair<Double> pair1 = new ValuePair<Double>(x, y);
    ValuePair<Double> pair2 = new ValuePair<Double>(4.0, y);
    ValuePair<Double> pair3 = new ValuePair<Double>(x, 4.0);
    assertFalse(pair1.equals(pair2));
    assertFalse(pair1.equals(pair3));
  }

}
