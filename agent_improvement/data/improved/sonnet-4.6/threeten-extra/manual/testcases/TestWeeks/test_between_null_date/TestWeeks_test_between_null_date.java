package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWeeks_test_between_null_date {

    @Test
    @DisplayName("between() throws NullPointerException when the start date is null")
    public void test_between_null_date() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Weeks.between((Temporal) null, LocalDate.now()));
    }
}
