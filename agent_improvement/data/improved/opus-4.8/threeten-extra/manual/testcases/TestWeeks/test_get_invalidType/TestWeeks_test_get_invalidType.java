package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#get} rejects temporal units other than WEEKS.
 */
public class TestWeeks_test_get_invalidType {

    @Test
    public void get_withUnsupportedUnit_throwsDateTimeException() {
        Weeks sixWeeks = Weeks.of(6);

        assertThrows(DateTimeException.class, () -> sixWeeks.get(IsoFields.QUARTER_YEARS));
    }
}
