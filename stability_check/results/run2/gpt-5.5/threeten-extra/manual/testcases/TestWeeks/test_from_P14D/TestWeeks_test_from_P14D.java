package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P14D {

    @Test
    public void test_from_P14D() {
        Weeks expectedWeeks = Weeks.of(2);
        Weeks actualWeeks = Weeks.from(Period.ofDays(14));

        assertEquals(expectedWeeks, actualWeeks);
    }
}
