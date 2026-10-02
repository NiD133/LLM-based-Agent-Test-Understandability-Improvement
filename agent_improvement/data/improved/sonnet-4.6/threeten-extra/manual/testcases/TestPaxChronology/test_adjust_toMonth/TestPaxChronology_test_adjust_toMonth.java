package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust_toMonth {

    // PaxDate does not support adjusting to a java.time.Month (ISO month),
    // because the Pax calendar uses its own month system (1-14) that is
    // incompatible with the ISO Month enum.
    @Test
    public void test_adjust_toMonth() {
        PaxDate pax = PaxDate.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> pax.with(Month.APRIL));
    }
}
