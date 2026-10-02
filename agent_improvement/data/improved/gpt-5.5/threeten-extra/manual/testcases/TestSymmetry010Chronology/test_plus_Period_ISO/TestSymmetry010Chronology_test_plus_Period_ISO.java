package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestSymmetry010Chronology_test_plus_Period_ISO {

    @Test
    public void test_plus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}
