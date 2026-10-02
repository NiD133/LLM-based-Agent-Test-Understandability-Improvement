package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestHalf_test_from_TemporalAccessor {

    @Test
    public void test_from_TemporalAccessor() {
        LocalDate dateInSecondHalf = LocalDate.of(2011, 7, 6);
        LocalDateTime dateTimeInFirstHalf = LocalDateTime.of(2012, 2, 3, 12, 30);

        assertEquals(Half.H2, Half.from(dateInSecondHalf));
        assertEquals(Half.H1, Half.from(dateTimeInFirstHalf));
    }
}
