package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_ofHour_int_valueTooLow {

    private static final int HOUR_BELOW_VALID_RANGE = -1;

    @Test
    public void test_ofHour_int_valueTooLow() {
        // AmPm.ofHour accepts hours 0-23; a negative hour is invalid and must throw DateTimeException
        assertThrows(DateTimeException.class, () -> AmPm.ofHour(HOUR_BELOW_VALID_RANGE));
    }
}
