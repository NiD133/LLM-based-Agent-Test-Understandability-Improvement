package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link InternationalFixedChronology#dateEpochDay(long)} reconstructs the
 * correct {@link InternationalFixedDate} from an ISO epoch-day value.
 *
 * <p>Each sample pairs an International Fixed Calendar (IFC) date with the equivalent ISO
 * {@link LocalDate}. Because both calendars share the same epoch-day numbering, converting the
 * ISO date to an epoch day and feeding it to {@code dateEpochDay} must yield the paired IFC date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_InternationalFixedDate_chronology_dateEpochDay {

    /**
     * Sample IFC date / equivalent ISO date pairs spanning ordinary years, leap years
     * (with their Leap Day) and Year Day, across a wide range of years.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { InternationalFixedDate.of(1, 1, 1), LocalDate.of(1, 1, 1) },
            { InternationalFixedDate.of(1, 1, 2), LocalDate.of(1, 1, 2) },
            { InternationalFixedDate.of(1, 6, 27), LocalDate.of(1, 6, 16) },
            { InternationalFixedDate.of(1, 6, 28), LocalDate.of(1, 6, 17) },
            { InternationalFixedDate.of(1, 7, 1), LocalDate.of(1, 6, 18) },
            { InternationalFixedDate.of(1, 7, 2), LocalDate.of(1, 6, 19) },
            { InternationalFixedDate.of(1, 13, 28), LocalDate.of(1, 12, 30) },
            { InternationalFixedDate.of(1, 13, 27), LocalDate.of(1, 12, 29) },
            { InternationalFixedDate.of(1, 13, 29), LocalDate.of(1, 12, 31) },
            { InternationalFixedDate.of(2, 1, 1), LocalDate.of(2, 1, 1) },
            { InternationalFixedDate.of(4, 6, 27), LocalDate.of(4, 6, 15) },
            { InternationalFixedDate.of(4, 6, 28), LocalDate.of(4, 6, 16) },
            { InternationalFixedDate.of(4, 6, 29), LocalDate.of(4, 6, 17) },
            { InternationalFixedDate.of(4, 7, 1), LocalDate.of(4, 6, 18) },
            { InternationalFixedDate.of(4, 7, 2), LocalDate.of(4, 6, 19) },
            { InternationalFixedDate.of(4, 13, 28), LocalDate.of(4, 12, 30) },
            { InternationalFixedDate.of(4, 13, 27), LocalDate.of(4, 12, 29) },
            { InternationalFixedDate.of(4, 13, 29), LocalDate.of(4, 12, 31) },
            { InternationalFixedDate.of(5, 1, 1), LocalDate.of(5, 1, 1) },
            { InternationalFixedDate.of(100, 6, 27), LocalDate.of(100, 6, 16) },
            { InternationalFixedDate.of(100, 6, 28), LocalDate.of(100, 6, 17) },
            { InternationalFixedDate.of(100, 7, 1), LocalDate.of(100, 6, 18) },
            { InternationalFixedDate.of(100, 7, 2), LocalDate.of(100, 6, 19) },
            { InternationalFixedDate.of(400, 6, 27), LocalDate.of(400, 6, 15) },
            { InternationalFixedDate.of(400, 6, 28), LocalDate.of(400, 6, 16) },
            { InternationalFixedDate.of(400, 6, 29), LocalDate.of(400, 6, 17) },
            { InternationalFixedDate.of(400, 7, 1), LocalDate.of(400, 6, 18) },
            { InternationalFixedDate.of(400, 7, 2), LocalDate.of(400, 6, 19) },
            { InternationalFixedDate.of(1582, 9, 28), LocalDate.of(1582, 9, 9) },
            { InternationalFixedDate.of(1582, 10, 1), LocalDate.of(1582, 9, 10) },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10, 6) },
            { InternationalFixedDate.of(2012, 6, 15), LocalDate.of(2012, 6, 3) },
            { InternationalFixedDate.of(2012, 6, 16), LocalDate.of(2012, 6, 4) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_InternationalFixedDate_chronology_dateEpochDay(InternationalFixedDate expectedFixedDate, LocalDate isoEquivalent) {
        long epochDay = isoEquivalent.toEpochDay();

        assertEquals(expectedFixedDate, InternationalFixedChronology.INSTANCE.dateEpochDay(epochDay));
    }
}
