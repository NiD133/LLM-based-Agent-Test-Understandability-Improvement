package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#plus(java.time.temporal.TemporalAmount)} rejects
 * a time-based amount, since a {@code Duration} cannot be converted to whole years.
 */
public class TestYears_test_plus_TemporalAmount_Duration {

    @Test
    public void plus_durationAmount_throwsBecauseNotConvertibleToYears() {
        Years oneYear = Years.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneYear.plus(twoHours));
    }
}
