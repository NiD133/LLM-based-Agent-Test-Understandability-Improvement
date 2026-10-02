package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_TemporalAccessor {

    @Test
    public void test_from_TemporalAccessor() {
        LocalDate dateInSecondQuarter = LocalDate.of(2011, 6, 6);
        LocalDateTime dateTimeInFirstQuarter = LocalDateTime.of(2012, 2, 3, 12, 30);

        assertEquals(Quarter.Q2, Quarter.from(dateInSecondQuarter));
        assertEquals(Quarter.Q1, Quarter.from(dateTimeInFirstQuarter));
    }
}
