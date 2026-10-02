package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#between(Temporal, Temporal)} throws
 * {@link NullPointerException} when the end date argument is {@code null}.
 */
public class TestDays_test_between_date_null {

    @Test
    public void test_between_date_null() {
        LocalDate startDate = LocalDate.now();
        Temporal nullEndDate = null;

        assertThrows(NullPointerException.class, () -> Days.between(startDate, nullEndDate));
    }
}
