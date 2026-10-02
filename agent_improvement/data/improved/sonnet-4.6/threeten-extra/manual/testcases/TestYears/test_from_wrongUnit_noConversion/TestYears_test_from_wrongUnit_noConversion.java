package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_wrongUnit_noConversion {

    // Days cannot be converted to a whole number of years, so Years.from must reject them.
    @Test
    public void test_from_wrongUnit_noConversion() {
        assertThrows(DateTimeException.class, () -> Years.from(Period.ofDays(2)));
    }
}
