package hyperDap.base.testHelpers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import hyperDap.base.helpers.Comparator;

public class TestComparator {

  @Test
  void testEqualApprox() {
    double v1 = 5.0;
    assertTrue(Comparator.equalApprox(v1, v1, 0.0));
    assertTrue(Comparator.equalApprox(v1, v1, 0.1));
    assertTrue(Comparator.equalApprox(v1, v1 + 0.1, 0.1));
    assertTrue(Comparator.equalApprox(v1, v1 - 0.1, 0.1));
    assertFalse(Comparator.equalApprox(v1, v1 + 0.11, 0.1));
    assertFalse(Comparator.equalApprox(v1, v1 - 0.11, 0.1));
    assertFalse(Comparator.equalApprox(v1, v1 + 0.000000000000001, 0.0));
    assertFalse(Comparator.equalApprox(v1, v1 - 0.000000000000001, 0.0));
    assertTrue(Comparator.equalApprox(v1, v1 + 0.000000000000001, 0.1));
    assertTrue(Comparator.equalApprox(v1, v1 - 0.000000000000001, 0.1));
  }

}
