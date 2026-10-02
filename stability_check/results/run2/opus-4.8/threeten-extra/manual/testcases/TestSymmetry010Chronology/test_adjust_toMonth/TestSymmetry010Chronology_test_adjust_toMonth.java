package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_adjust_toMonth {

    /**
     * Adjusting a Symmetry010 date with an ISO {@link Month} is not supported,
     * because the ISO month is not a valid field for the Symmetry010 calendar,
     * so {@code with(Month)} must reject it with a {@link DateTimeException}.
     */
    @Test
    public void test_adjust_toMonth() {
        Symmetry010Date date = Symmetry010Date.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
