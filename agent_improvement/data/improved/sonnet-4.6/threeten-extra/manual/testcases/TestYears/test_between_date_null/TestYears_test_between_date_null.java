package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestYears_test_between_date_null {

    /**
     * Verifies that {@link Years#between(Temporal, Temporal)} throws
     * {@link NullPointerException} when the end date argument is null.
     */
    @Test
    public void test_between_date_null() {
        LocalDate startDate = LocalDate.now();
        assertThrows(NullPointerException.class, () -> Years.between(startDate, (Temporal) null));
    }
}
