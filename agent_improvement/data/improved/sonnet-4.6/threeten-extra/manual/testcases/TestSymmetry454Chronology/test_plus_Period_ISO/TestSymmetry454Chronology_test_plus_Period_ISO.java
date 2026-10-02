package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_Period_ISO {

    // Adding an ISO Period to a Symmetry454Date must throw DateTimeException
    // because ISO periods (which carry chronology-specific semantics) cannot be
    // applied to a date that belongs to a different calendar system.
    @Test
    public void test_plus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}
