package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#plus(java.time.temporal.TemporalAmount)} when the amount
 * to add cannot be expressed as a whole number of years.
 */
public class TestYears_test_plus_TemporalAmount_PeriodMonths {

    /**
     * A {@code Period} measured in months has no exact conversion to whole years,
     * so adding it to a {@code Years} value must fail with a {@link DateTimeException}.
     */
    @Test
    public void plus_periodOfMonths_throwsBecauseNotConvertibleToWholeYears() {
        Years oneYear = Years.of(1);
        Period twoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> oneYear.plus(twoMonths));
    }
}
