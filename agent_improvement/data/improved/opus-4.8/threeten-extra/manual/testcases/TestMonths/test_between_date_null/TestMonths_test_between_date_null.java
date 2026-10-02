package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#between(Temporal, Temporal)} rejects a null end date.
 */
public class TestMonths_test_between_date_null {

    @Test
    public void between_withNullEndDate_throwsNullPointerException() {
        LocalDate startDate = LocalDate.now();
        Temporal nullEndDate = null;

        assertThrows(
                NullPointerException.class,
                () -> Months.between(startDate, nullEndDate));
    }
}
