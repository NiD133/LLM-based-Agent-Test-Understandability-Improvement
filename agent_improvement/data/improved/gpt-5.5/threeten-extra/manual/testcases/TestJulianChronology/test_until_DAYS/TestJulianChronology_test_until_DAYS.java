package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_until_DAYS {

    private static final long SAME_DAY = 0;
    private static final long NEXT_DAY = 1;
    private static final long THIRTY_FIVE_DAYS_LATER = 35;
    private static final long FORTY_DAYS_EARLIER = -40;

    public static Object[][] data_samples() {
        return new Object[][] {
                { JulianDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
                { JulianDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
                { JulianDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
                { JulianDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
                { JulianDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
                { JulianDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
                { JulianDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },
                { JulianDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
                { JulianDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
                { JulianDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
                { JulianDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
                { JulianDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },
                { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
                { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
                { JulianDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
                { JulianDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
                { JulianDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },
                { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
                { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },
                { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
                { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
                { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
                { JulianDate.of(2012, 6, 22), LocalDate.of(2012, 7, 5) },
                { JulianDate.of(2012, 6, 23), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(JulianDate julian, LocalDate iso) {
        assertDaysUntil(julian, iso.plusDays(0), SAME_DAY);
        assertDaysUntil(julian, iso.plusDays(1), NEXT_DAY);
        assertDaysUntil(julian, iso.plusDays(35), THIRTY_FIVE_DAYS_LATER);
        assertDaysUntil(julian, iso.minusDays(40), FORTY_DAYS_EARLIER);
    }

    private void assertDaysUntil(JulianDate start, LocalDate end, long expectedDays) {
        assertEquals(expectedDays, start.until(end, DAYS));
    }
}
