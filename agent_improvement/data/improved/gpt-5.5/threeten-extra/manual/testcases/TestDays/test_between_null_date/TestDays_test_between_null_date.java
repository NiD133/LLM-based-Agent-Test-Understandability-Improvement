package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestDays_test_between_null_date {

    @Test
    public void test_between_null_date() {
        assertThrows(NullPointerException.class, () -> Days.between((Temporal) null, LocalDate.now()));
    }
}
