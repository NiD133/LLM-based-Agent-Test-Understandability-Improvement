package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P2W {

    @Test
    public void test_from_P2W() {
        Weeks expectedWeeks = Weeks.of(2);
        Weeks convertedWeeks = Weeks.from(Period.ofWeeks(2));

        assertEquals(expectedWeeks, convertedWeeks);
    }
}
