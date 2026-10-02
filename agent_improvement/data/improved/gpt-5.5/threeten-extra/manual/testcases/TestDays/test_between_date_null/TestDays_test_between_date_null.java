package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestDays_test_between_date_null {

    @Test
    public void test_between_date_null() {
        Temporal missingEndDate = null;

        assertThrows(
                NullPointerException.class,
                () -> Days.between(LocalDate.now(), missingEndDate));
    }
}
