package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies that the seven extra days appended to December of a Symmetry010 leap
 * year are all reported as belonging to the leap week.
 *
 * <p>2015 is a Symmetry010 leap year, so its December has 37 days. The last
 * seven of those days (31 through 37) make up the leap week.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_leapWeek {

    private static final int LEAP_YEAR = 2015;
    private static final int DECEMBER = 12;

    @ParameterizedTest(name = "{0} Dec {1} is in the leap week")
    @ValueSource(ints = { 31, 32, 33, 34, 35, 36, 37 })
    public void test_leapWeek(int dayOfMonth) {
        Symmetry010Date date = Symmetry010Date.of(LEAP_YEAR, DECEMBER, dayOfMonth);

        assertTrue(date.isLeapWeek(),
                "Dec " + dayOfMonth + " of leap year " + LEAP_YEAR + " should be in the leap week");
    }
}
