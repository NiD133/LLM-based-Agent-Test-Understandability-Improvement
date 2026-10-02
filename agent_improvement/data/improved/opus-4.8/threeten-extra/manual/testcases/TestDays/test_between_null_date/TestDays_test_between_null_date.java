package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#between(Temporal, Temporal)} rejects a null start date.
 */
public class TestDays_test_between_null_date {

    @Test
    public void between_nullStartDate_throwsNullPointerException() {
        Temporal nullStartDate = null;
        LocalDate endDate = LocalDate.now();

        assertThrows(
                NullPointerException.class,
                () -> Days.between(nullStartDate, endDate));
    }
}
