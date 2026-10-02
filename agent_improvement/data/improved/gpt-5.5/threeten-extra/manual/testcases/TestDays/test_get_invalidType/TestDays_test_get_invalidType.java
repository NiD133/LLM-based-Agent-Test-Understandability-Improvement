package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

public class TestDays_test_get_invalidType {

    @Test
    public void test_get_invalidType() {
        Days sixDays = Days.of(6);

        assertThrows(DateTimeException.class, () -> sixDays.get(IsoFields.QUARTER_YEARS));
    }
}
