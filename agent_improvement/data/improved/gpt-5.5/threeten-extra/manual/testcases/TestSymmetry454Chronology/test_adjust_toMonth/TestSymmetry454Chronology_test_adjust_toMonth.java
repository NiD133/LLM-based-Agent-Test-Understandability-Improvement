package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        Symmetry454Date sym454 = Symmetry454Date.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> sym454.with(Month.APRIL));
    }
}
