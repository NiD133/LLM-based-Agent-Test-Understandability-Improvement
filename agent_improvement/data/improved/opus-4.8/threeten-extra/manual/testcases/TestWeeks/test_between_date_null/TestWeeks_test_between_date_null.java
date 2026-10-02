package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#between} rejects a null end date.
 */
public class TestWeeks_test_between_date_null {

    @Test
    public void between_throwsNullPointerException_whenEndDateIsNull() {
        LocalDate startDate = LocalDate.now();
        Temporal nullEndDate = null;

        assertThrows(NullPointerException.class, () -> Weeks.between(startDate, nullEndDate));
    }
}
