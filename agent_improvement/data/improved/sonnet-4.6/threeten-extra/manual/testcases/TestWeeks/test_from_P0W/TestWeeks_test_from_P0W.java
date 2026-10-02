package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P0W {

    // "P0W" is the ISO-8601 duration string for zero weeks; Period.ofWeeks(0) parses to that value.
    @Test
    public void test_from_P0W() {
        Weeks zeroWeeksFromPeriod = Weeks.from(Period.ofWeeks(0));
        Weeks zeroWeeksDirectly = Weeks.of(0);
        assertEquals(zeroWeeksDirectly, zeroWeeksFromPeriod);
    }
}
