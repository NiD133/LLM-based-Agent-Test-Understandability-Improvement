package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_wrongUnit_remainder {

    @Test
    public void test_from_wrongUnit_remainder() {
        // 3 days cannot be expressed as a whole number of weeks (remainder != 0)
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofDays(3)));
    }
}
