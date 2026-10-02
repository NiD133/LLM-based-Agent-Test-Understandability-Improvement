package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_until_TemporalUnit_unsupported {

    @Test
    public void test_until_TemporalUnit_unsupported() {
        Symmetry454Date start = Symmetry454Date.of(2012, 6, 28);
        Symmetry454Date end = Symmetry454Date.of(2012, 7, 1);

        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
