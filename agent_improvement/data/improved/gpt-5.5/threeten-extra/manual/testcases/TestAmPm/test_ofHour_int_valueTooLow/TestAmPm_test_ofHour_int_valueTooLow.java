package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_ofHour_int_valueTooLow {

    @Test
    public void test_ofHour_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> AmPm.ofHour(-1));
    }
}
