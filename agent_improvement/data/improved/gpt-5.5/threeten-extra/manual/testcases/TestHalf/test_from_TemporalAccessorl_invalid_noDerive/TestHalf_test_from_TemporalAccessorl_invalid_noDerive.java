package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestHalf_test_from_TemporalAccessorl_invalid_noDerive {

    @Test
    public void test_from_TemporalAccessor_invalid_noDerive() {
        LocalTime timeWithoutDateFields = LocalTime.of(12, 30);

        assertThrows(DateTimeException.class, () -> Half.from(timeWithoutDateFields));
    }
}
