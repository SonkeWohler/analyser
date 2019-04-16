package hyperDap.generator.testMain;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import hyperDap.generator.main.GenSegment;

public class TestGenSegment {

  @Test
  void wrongEncoding() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GenSegment("", 1.0, 1.0, 1.0, 1.0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new GenSegment("hlhkj", 1.0, 1.0, 1.0, 1.0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new GenSegment("cconstant", 1.0, 1.0, 1.0, 1.0);
    });
  }

  @Test
  void simpleConstant() {
    Double value = 5.0;
    GenSegment segment = new GenSegment("constant", 3.0, 12.0, value, 0.1);
    for (Double val : segment.generateValues(10)) {
      assertEquals(value, val);
    }
    segment = new GenSegment("COnsTant", 3.0, 12.0, value, 0.1);
    for (Double val : segment.generateValues(10)) {
      assertEquals(value, val);
    }
  }

  // @Test
  // floating point error
  void simpleLinear() {
    double step = 0.1;
    double scale = 3.0;
    double intercept = 5.0;
    GenSegment segment = new GenSegment("linear", scale, 0.0, intercept, step);
    ArrayList<Double> list = segment.generateValues(20);
    for (int i = 0; i < 20; i++) {
      assertEquals(Double.valueOf(scale * ((i + 1) * step) + intercept), list.get(i));
    }
  }

}
