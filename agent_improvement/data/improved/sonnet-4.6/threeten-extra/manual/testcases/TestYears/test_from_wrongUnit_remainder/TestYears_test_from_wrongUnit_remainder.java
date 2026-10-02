package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestYears_test_from_wrongUnit_remainder {

    @Test
    @DisplayName("Years.from() throws DateTimeException when the period has a non-zero remainder after conversion to years (e.g. 3 months cannot be expressed as a whole number of years)")
    public void test_from_wrongUnit_remainder() {
        assertThrows(DateTimeException.class, () -> Years.from(Period.ofMonths(3)));
    }
}
