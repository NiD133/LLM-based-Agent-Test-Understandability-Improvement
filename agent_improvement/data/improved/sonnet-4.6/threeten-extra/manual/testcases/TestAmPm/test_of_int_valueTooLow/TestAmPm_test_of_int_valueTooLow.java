package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_of_int_valueTooLow {

    @Test
    public void test_of_int_valueTooLow() {
        // AmPm.of() only accepts 0 (AM) or 1 (PM); -1 is below the valid range
        assertThrows(DateTimeException.class, () -> AmPm.of(-1));
    }
}
