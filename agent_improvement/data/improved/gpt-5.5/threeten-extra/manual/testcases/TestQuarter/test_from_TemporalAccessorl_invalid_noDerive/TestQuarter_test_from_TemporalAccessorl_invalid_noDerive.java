package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_TemporalAccessorl_invalid_noDerive {

    @Test
    public void test_from_TemporalAccessorl_invalid_noDerive() {
        assertThrows(DateTimeException.class, () -> Quarter.from(LocalTime.of(12, 30)));
    }
}
