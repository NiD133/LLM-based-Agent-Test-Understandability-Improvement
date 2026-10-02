package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_Period_ISO {

    @Test
    public void test_minus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
