package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestSymmetry454Chronology_test_minus_Period_ISO {

    // Symmetry454Date rejects ISO Period because ISO months have no fixed day-length
    // in the Sym454 calendar; only ChronoPeriod of the same chronology is accepted.
    @Test
    public void test_minus_Period_ISO() {
        assertThrows(DateTimeException.class,
                () -> Symmetry454Date.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
