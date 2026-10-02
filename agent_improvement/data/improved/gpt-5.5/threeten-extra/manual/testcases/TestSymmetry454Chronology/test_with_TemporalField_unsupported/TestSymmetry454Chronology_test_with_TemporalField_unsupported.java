package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_with_TemporalField_unsupported {

    @Test
    public void test_with_TemporalField_unsupported() {
        Symmetry454Date date = Symmetry454Date.of(2012, 6, 28);

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> date.with(MINUTE_OF_DAY, 10));
    }
}
