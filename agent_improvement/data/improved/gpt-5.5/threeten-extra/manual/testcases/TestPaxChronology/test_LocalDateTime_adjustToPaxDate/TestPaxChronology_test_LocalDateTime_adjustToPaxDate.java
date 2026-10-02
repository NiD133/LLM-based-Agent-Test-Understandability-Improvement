package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_LocalDateTime_adjustToPaxDate {

    private static final PaxDate PAX_DATE = PaxDate.of(2012, 6, 23);
    private static final LocalDateTime ISO_DATE_TIME_AT_START_OF_DAY = LocalDateTime.of(2012, 6, 4, 0, 0);

    @Test
    public void test_LocalDateTime_adjustToPaxDate() {
        LocalDateTime test = LocalDateTime.MIN.with(PAX_DATE);

        assertEquals(ISO_DATE_TIME_AT_START_OF_DAY, test);
    }
}
