package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_between_date_null {

    @Test
    public void test_between_date_null() {
        // Weeks.between must reject a null end date with NullPointerException
        assertThrows(NullPointerException.class, () -> Weeks.between(LocalDate.now(), (Temporal) null));
    }
}
