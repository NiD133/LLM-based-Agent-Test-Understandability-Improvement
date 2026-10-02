package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    /**
     * Adjusting a {@link LocalDate} with an {@link InternationalFixedDate} should
     * yield the ISO {@code LocalDate} that represents the same day on the timeline.
     * Here the International Fixed date 2012/07/19 corresponds to the ISO date
     * 2012-07-06.
     */
    @Test
    public void test_LocalDate_adjustToInternationalFixedDate() {
        InternationalFixedDate fixedDate = InternationalFixedDate.of(2012, 7, 19);

        LocalDate adjusted = LocalDate.MIN.with(fixedDate);

        assertEquals(LocalDate.of(2012, 7, 6), adjusted);
    }
}
