package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#with(LocalDate)}, which adjusts a BritishCutoverDate
 * to match the calendar date represented by the given ISO LocalDate.
 *
 * <p>Key behaviour under test:
 * <ul>
 *   <li>ISO dates that fall in the Julian–Gregorian gap (Sep 3–13 1752) map to the
 *       first valid post-gap date (Sep 14 1752 → BritishCutover Sep 14).</li>
 *   <li>ISO dates before the cutover (Sep 14 1752) map back through the 11-day offset.</li>
 *   <li>ISO dates after the cutover carry over unchanged.</li>
 * </ul>
 */
public class TestBritishCutoverChronology_test_adjust_LocalDate {

    /**
     * Test data: (input BritishCutoverDate, ISO LocalDate to adjust with, expected BritishCutoverDate).
     *
     * <p>Scenario groups:
     * <ol>
     *   <li>ISO date lands before the cutover (Sep 12 1752) – both Julian-era and
     *       post-gap starting-point produce the same pre-cutover British date.</li>
     *   <li>ISO date lands exactly on the cutover (Sep 14 1752) – result is Sep 14.</li>
     *   <li>Modern post-cutover date – result is identical to the ISO date.</li>
     * </ol>
     */
    public static Object[][] data_withLocalDate() {
        return new Object[][] {
            // ISO Sep 12 1752 is before the cutover → maps to British Sep 1 1752 (Julian)
            { BritishCutoverDate.of(1752, 9,  2), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9,  1) },
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9,  1) },

            // ISO Sep 14 1752 is exactly the first Gregorian date → maps to British Sep 14 1752
            { BritishCutoverDate.of(1752, 9,  2), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1752, 9, 15), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14) },

            // Post-cutover ISO date → British date equals ISO date
            { BritishCutoverDate.of(2012, 2, 23), LocalDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 23) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withLocalDate")
    public void test_adjust_LocalDate(BritishCutoverDate input, LocalDate local, BritishCutoverDate expected) {
        BritishCutoverDate result = input.with(local);
        assertEquals(expected, result);
    }
}
