package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies the valid value ranges that {@link PaxChronology} reports for its
 * supported date fields.
 *
 * <p>The Pax calendar normally has 13 months of 28 days, but every leap year
 * inserts a 14th month ("Pax") of 7 days. This makes several field ranges vary
 * between a common year and a leap year, which is why the expected ranges below
 * use the {@code (min, largestMin, max)} or {@code (min, smallestMax, max)}
 * forms rather than a simple {@code (min, max)}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        // Days of the week always run 1..7.
        assertEquals(ValueRange.of(1, 7), PaxChronology.INSTANCE.range(DAY_OF_WEEK));

        // Day-of-month is 1..28 for normal months, but the leap month "Pax" has
        // only 7 days, so the smallest possible maximum is 7.
        assertEquals(ValueRange.of(1, 7, 28), PaxChronology.INSTANCE.range(DAY_OF_MONTH));

        // Day-of-year is 1..364 in a common year (13 * 28) and up to 371 in a
        // leap year (13 * 28 + 7).
        assertEquals(ValueRange.of(1, 364, 371), PaxChronology.INSTANCE.range(DAY_OF_YEAR));

        // Month-of-year is 1..13 in a common year and 1..14 in a leap year.
        assertEquals(ValueRange.of(1, 13, 14), PaxChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
