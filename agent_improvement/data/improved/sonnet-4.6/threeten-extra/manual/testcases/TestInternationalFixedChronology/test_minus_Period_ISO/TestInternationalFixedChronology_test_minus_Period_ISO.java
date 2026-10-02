package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_Period_ISO {

    /**
     * Subtracting an ISO Period from an InternationalFixedDate must throw DateTimeException
     * because ISO periods are incompatible with the International Fixed chronology.
     */
    @Test
    public void test_minus_Period_ISO() {
        InternationalFixedDate date = InternationalFixedDate.of(2014, 5, 26);
        assertThrows(DateTimeException.class, () -> date.minus(Period.ofMonths(2)));
    }
}
