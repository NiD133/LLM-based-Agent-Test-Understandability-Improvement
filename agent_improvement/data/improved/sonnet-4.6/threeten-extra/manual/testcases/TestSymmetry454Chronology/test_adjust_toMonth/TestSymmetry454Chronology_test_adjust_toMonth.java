package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a Symmetry454Date to a java.time.Month is unsupported.
 *
 * The Symmetry454 calendar does not understand ISO-calendar Month enum values,
 * so calling with(Month) must throw DateTimeException.
 */
@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        // Symmetry454 dates cannot be adjusted using ISO Month enum values
        Symmetry454Date date = Symmetry454Date.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
