package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_Period_ISO {

    // Adding an ISO Period (which uses months/years in the Gregorian sense) to an
    // InternationalFixedDate is not supported and must throw DateTimeException,
    // because ISO periods are not convertible to the International Fixed calendar.
    @Test
    public void test_plus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}
