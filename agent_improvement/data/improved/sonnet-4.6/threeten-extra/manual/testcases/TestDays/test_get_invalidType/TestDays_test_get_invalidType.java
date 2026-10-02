package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

public class TestDays_test_get_invalidType {

    @Test
    public void test_get_invalidType() {
        // Days only supports ChronoUnit.DAYS; querying a non-day unit must throw
        assertThrows(DateTimeException.class, () -> Days.of(6).get(IsoFields.QUARTER_YEARS));
    }
}
