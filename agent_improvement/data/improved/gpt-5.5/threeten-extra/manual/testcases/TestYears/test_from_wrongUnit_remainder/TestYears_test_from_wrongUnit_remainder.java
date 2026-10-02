package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_wrongUnit_remainder {

    @Test
    public void test_from_wrongUnit_remainder() {
        Period monthsWithYearRemainder = Period.ofMonths(3);

        assertThrows(DateTimeException.class, () -> Years.from(monthsWithYearRemainder));
    }
}
