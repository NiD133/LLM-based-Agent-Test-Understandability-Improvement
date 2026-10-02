package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust_toLocalDate {

    /**
     * Adjusting a PaxDate with an ISO LocalDate should convert that ISO date
     * into the equivalent PaxDate. Here the ISO date 2012-07-06 corresponds to
     * the Pax date 2012-07-27.
     */
    @Test
    public void test_adjust_toLocalDate() {
        PaxDate paxDate = PaxDate.of(2000, 1, 4);

        PaxDate adjusted = paxDate.with(LocalDate.of(2012, 7, 6));

        assertEquals(PaxDate.of(2012, 7, 27), adjusted);
    }
}
