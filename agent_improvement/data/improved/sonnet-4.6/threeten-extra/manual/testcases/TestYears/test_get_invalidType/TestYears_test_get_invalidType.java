package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

public class TestYears_test_get_invalidType {

    // Years.get() only supports ChronoUnit.YEARS; querying any other unit must throw DateTimeException.
    @Test
    public void test_get_invalidType() {
        assertThrows(DateTimeException.class, () -> Years.of(6).get(IsoFields.QUARTER_YEARS));
    }
}
