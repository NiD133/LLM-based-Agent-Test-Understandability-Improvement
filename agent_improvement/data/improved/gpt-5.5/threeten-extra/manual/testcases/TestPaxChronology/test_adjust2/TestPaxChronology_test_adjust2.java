package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_adjust2 {

    @Test
    public void test_adjust2() {
        PaxDate base = PaxDate.of(2012, 13, 2);

        PaxDate test = base.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(PaxDate.of(2012, 13, 7), test);
    }
}
