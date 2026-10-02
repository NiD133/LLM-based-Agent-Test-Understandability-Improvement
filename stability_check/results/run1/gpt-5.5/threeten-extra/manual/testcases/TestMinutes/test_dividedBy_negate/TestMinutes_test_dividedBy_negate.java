package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Minutes twelveMinutes = Minutes.of(12);
        Minutes expectedQuotient = Minutes.of(-4);

        assertEquals(expectedQuotient, twelveMinutes.dividedBy(-3));
    }
}
