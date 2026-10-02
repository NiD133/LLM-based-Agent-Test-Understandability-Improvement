package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_wrongUnit_noConversion {

    @Test
    @DisplayName("Weeks.from() throws DateTimeException when the source period uses months, which cannot be converted to weeks")
    public void test_from_wrongUnit_noConversion() {
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofMonths(2)));
    }
}
