package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#between(Temporal, Temporal)} rejects a null start date.
 */
public class TestYears_test_between_null_date {

    @Test
    public void between_throwsNullPointerException_whenStartDateIsNull() {
        Temporal nullStartDate = null;
        Temporal endDate = LocalDate.now();

        assertThrows(
                NullPointerException.class,
                () -> Years.between(nullStartDate, endDate));
    }
}
