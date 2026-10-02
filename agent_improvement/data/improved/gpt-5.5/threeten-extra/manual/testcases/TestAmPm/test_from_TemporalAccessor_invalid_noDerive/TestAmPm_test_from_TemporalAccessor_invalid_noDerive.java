package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_from_TemporalAccessor_invalid_noDerive {

    @Test
    public void test_from_TemporalAccessor_invalid_noDerive() {
        assertThrows(DateTimeException.class, () -> AmPm.from(LocalDate.of(2007, 7, 30)));
    }
}
