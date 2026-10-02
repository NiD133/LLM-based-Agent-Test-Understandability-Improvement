package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#between(Temporal, Temporal)} rejects a null end date.
 */
public class TestDays_test_between_date_null {

    @Test
    public void between_throwsNullPointerException_whenEndDateIsNull() {
        LocalDate startDate = LocalDate.now();
        Temporal nullEndDate = null;

        assertThrows(
                NullPointerException.class,
                () -> Days.between(startDate, nullEndDate));
    }
}
