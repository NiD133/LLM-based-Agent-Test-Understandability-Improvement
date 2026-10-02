package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#between(Temporal, Temporal)} rejects a null start date.
 */
public class TestWeeks_test_between_null_date {

    @Test
    public void between_withNullStartDate_throwsNullPointerException() {
        Temporal nullStartDate = null;
        LocalDate endDate = LocalDate.now();

        assertThrows(NullPointerException.class, () -> Weeks.between(nullStartDate, endDate));
    }
}
