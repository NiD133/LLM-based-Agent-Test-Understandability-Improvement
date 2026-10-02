package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TestWeeks_test_between_null_date {

    @Test
    public void test_between_null_date() {
        Executable betweenWithNullStartDate =
                () -> Weeks.between((Temporal) null, LocalDate.now());

        assertThrows(NullPointerException.class, betweenWithNullStartDate);
    }
}
