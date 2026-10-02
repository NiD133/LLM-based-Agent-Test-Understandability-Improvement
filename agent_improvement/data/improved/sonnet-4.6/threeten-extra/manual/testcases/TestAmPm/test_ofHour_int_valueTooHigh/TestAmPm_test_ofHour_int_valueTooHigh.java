package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_ofHour_int_valueTooHigh {

    @Test
    @DisplayName("ofHour throws DateTimeException when hour-of-day exceeds valid maximum of 23")
    public void test_ofHour_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> AmPm.ofHour(24));
    }
}
