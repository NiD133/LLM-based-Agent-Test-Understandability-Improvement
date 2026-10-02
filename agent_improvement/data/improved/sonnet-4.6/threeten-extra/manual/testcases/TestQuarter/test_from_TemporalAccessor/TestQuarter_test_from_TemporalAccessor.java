package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_TemporalAccessor {

    // Quarter boundaries:
    //   Q1 = January  – March
    //   Q2 = April    – June
    //   Q3 = July     – September
    //   Q4 = October  – December

    @Test
    public void test_from_TemporalAccessor() {
        // June 6, 2011 is in Q2 (April–June); Quarter.from() must accept a LocalDate
        LocalDate dateInQ2 = LocalDate.of(2011, 6, 6);
        assertEquals(Quarter.Q2, Quarter.from(dateInQ2));

        // February 3, 2012 at 12:30 is in Q1 (January–March); Quarter.from() must accept a LocalDateTime
        LocalDateTime dateTimeInQ1 = LocalDateTime.of(2012, 2, 3, 12, 30);
        assertEquals(Quarter.Q1, Quarter.from(dateTimeInQ1));
    }
}
