package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestMinutes_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        // Dividing by a negative divisor should negate the quotient: 12 / -3 = -4
        Minutes twelveMinutes = Minutes.of(12);
        assertEquals(Minutes.of(-4), twelveMinutes.dividedBy(-3));
    }
}
