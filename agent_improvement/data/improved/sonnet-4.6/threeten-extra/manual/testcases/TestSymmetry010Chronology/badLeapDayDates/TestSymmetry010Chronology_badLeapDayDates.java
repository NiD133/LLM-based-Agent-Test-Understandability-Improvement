package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that creating a Symmetry010Date with day 37 in December (the leap-week slot)
 * throws DateTimeException for years that are not leap years.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_badLeapDayDates {

    /**
     * Years that do NOT qualify as Symmetry010 leap years, so December has only 30 days
     * and day 37 must be rejected.
     */
    public static Object[][] data_badLeapDates() {
        return new Object[][] { { 1 }, { 100 }, { 200 }, { 2000 } };
    }

    @ParameterizedTest
    @MethodSource("data_badLeapDates")
    public void badLeapDayDates(int year) {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(year, 12, 37));
    }
}
