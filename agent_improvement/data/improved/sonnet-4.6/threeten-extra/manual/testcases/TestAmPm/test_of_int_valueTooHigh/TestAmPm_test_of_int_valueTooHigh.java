package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_of_int_valueTooHigh {

    // AmPm.of() only accepts 0 (AM) or 1 (PM); any value above 1 must throw
    @Test
    public void test_of_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> AmPm.of(2));
    }
}
