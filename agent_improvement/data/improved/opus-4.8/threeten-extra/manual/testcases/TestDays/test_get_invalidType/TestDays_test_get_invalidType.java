package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#get(java.time.temporal.TemporalUnit)} rejects a
 * unit it does not support.
 * <p>
 * {@code Days} only supports the {@code DAYS} unit, so requesting any other
 * unit (here {@link IsoFields#QUARTER_YEARS}) must fail with a
 * {@link DateTimeException}.
 */
public class TestDays_test_get_invalidType {

    @Test
    public void get_withUnsupportedUnit_throwsDateTimeException() {
        Days sixDays = Days.of(6);

        assertThrows(DateTimeException.class, () -> sixDays.get(IsoFields.QUARTER_YEARS));
    }
}
