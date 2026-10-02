package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

public class TestYears_test_between_null_date {

    @Test
    public void test_between_null_date() {
        LocalDate endDateExclusive = LocalDate.now();

        //noinspection DataFlowIssue - testing nulls
        assertThrows(
                NullPointerException.class,
                () -> Years.between((Temporal) null, endDateExclusive));
    }
}
