package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#lengthOfYear()} by constructing the first day
 * of each year and checking the total number of days in that year.
 *
 * <p>Key cases:
 * <ul>
 *   <li>Julian leap years (every 4th year, including centuries) apply up to and
 *       including 1752.</li>
 *   <li>1752 is the British cutover year: 11 days were skipped in September, so
 *       the year contains only 355 days.</li>
 *   <li>Gregorian leap rules (century years NOT leap unless divisible by 400)
 *       apply from 1753 onward.</li>
 * </ul>
 */
public class TestBritishCutoverChronology_test_lengthOfYear_atStart {

    /**
     * Provides (proleptic year, expected length-of-year) pairs.
     *
     * <p>The data is grouped by the rule being exercised:
     * <ol>
     *   <li>Proleptic BCE years / early years under Julian rules.</li>
     *   <li>Pre-cutover Julian leap centuries (1500–1752).</li>
     *   <li>The cutover year 1752 itself (355 days).</li>
     *   <li>Post-cutover Gregorian leap rules (1753 onward).</li>
     * </ol>
     */
    public static Object[][] data_lengthOfYear() {
        return new Object[][] {
            // --- Proleptic BCE / early years (Julian rules) ---
            { -101, 365 },  // not a Julian leap year
            { -100, 366 },  // Julian leap: divisible by 4
            {  -99, 365 },
            {   -1, 365 },
            {    0, 366 },  // Julian leap (year 0 = 1 BC)

            // --- Pre-cutover years under Julian rules ---
            {  100, 366 },  // Julian leap: century years ARE leap under Julian
            { 1500, 366 },  // Julian leap century
            { 1600, 366 },  // Julian leap century
            { 1700, 366 },  // Julian leap century (Julian rule still applies)
            { 1748, 366 },  // Julian leap: divisible by 4
            { 1749, 365 },
            { 1750, 365 },
            { 1751, 365 },  // non-leap year immediately before cutover

            // --- Cutover year ---
            { 1752, 355 },  // 11 days removed from September during Julian→Gregorian cutover

            // --- Post-cutover years under Gregorian rules ---
            { 1753, 365 },
            { 1800, 365 },  // Gregorian rule: century NOT divisible by 400 → not a leap year
            { 1900, 365 },  // same
            { 1901, 365 },
            { 1902, 365 },
            { 1903, 365 },
            { 1904, 366 },  // Gregorian leap: divisible by 4 and not a century
            { 2000, 366 },  // Gregorian leap: divisible by 400
            { 2100, 365 },  // Gregorian rule: century NOT divisible by 400 → not a leap year
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfYear")
    public void test_lengthOfYear_atStart(int year, int length) {
        assertEquals(length, BritishCutoverDate.of(year, 1, 1).lengthOfYear());
    }
}
