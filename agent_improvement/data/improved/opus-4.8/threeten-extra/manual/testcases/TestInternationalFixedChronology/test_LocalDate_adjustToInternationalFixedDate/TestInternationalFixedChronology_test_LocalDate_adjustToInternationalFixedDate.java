package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link LocalDate} can be adjusted onto an {@link InternationalFixedDate}
 * via {@link LocalDate#with(java.time.temporal.TemporalAdjuster)}.
 * <p>
 * Adjusting any {@code LocalDate} with an International Fixed date should yield the
 * ISO date that represents the same point on the time-line as that fixed date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    @Test
    public void test_LocalDate_adjustToInternationalFixedDate() {
        // 2012-07-19 in the International Fixed calendar...
        InternationalFixedDate fixedDate = InternationalFixedDate.of(2012, 7, 19);

        // ...maps to 2012-07-06 in the ISO calendar, regardless of the starting LocalDate.
        LocalDate adjusted = LocalDate.MIN.with(fixedDate);

        assertEquals(LocalDate.of(2012, 7, 6), adjusted);
    }
}
