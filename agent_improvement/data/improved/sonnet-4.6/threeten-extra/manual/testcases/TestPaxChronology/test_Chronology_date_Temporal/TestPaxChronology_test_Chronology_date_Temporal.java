package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link PaxChronology#date(java.time.temporal.TemporalAccessor)} correctly converts
 * ISO {@link LocalDate} values to the equivalent {@link PaxDate} in the Pax calendar system.
 */
@SuppressWarnings({"static-method"})
public class TestPaxChronology_test_Chronology_date_Temporal {

    /**
     * Sample pairs of (PaxDate, equivalent ISO LocalDate) used to verify round-trip conversion.
     *
     * <p>The Pax calendar epoch is 0001-01-01 (Pax) == 0000-12-31 (ISO). Each row is
     * {@code {paxDate, isoDate}} where converting the ISO date via
     * {@code PaxChronology.INSTANCE.date(isoDate)} must yield the given Pax date.
     *
     * <p>Cases are grouped by theme:
     * <ul>
     *   <li>Early CE dates near the epoch</li>
     *   <li>Leap-year boundaries (year 6 has a 14th "Pax" month)</li>
     *   <li>Century boundaries (year 399/400/401)</li>
     *   <li>Historical dates (1582, 1945, 2012)</li>
     *   <li>BCE (negative proleptic year) dates including leap-year boundaries</li>
     * </ul>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Early CE dates near the Pax epoch ---
            { PaxDate.of(1,  1,  1),  LocalDate.of(   0, 12, 31) },
            { PaxDate.of(1,  1,  2),  LocalDate.of(   1,  1,  1) },
            { PaxDate.of(1,  1,  3),  LocalDate.of(   1,  1,  2) },
            { PaxDate.of(1,  1, 28),  LocalDate.of(   1,  1, 27) },
            { PaxDate.of(1,  2,  1),  LocalDate.of(   1,  1, 28) },
            { PaxDate.of(1,  2,  2),  LocalDate.of(   1,  1, 29) },
            { PaxDate.of(1,  2,  3),  LocalDate.of(   1,  1, 30) },

            // --- Year 6: leap year with 14-month Pax calendar ---
            { PaxDate.of(6, 13,  6),  LocalDate.of(   6, 12,  1) },
            { PaxDate.of(6, 13,  7),  LocalDate.of(   6, 12,  2) },
            { PaxDate.of(6, 14,  1),  LocalDate.of(   6, 12,  3) },
            { PaxDate.of(6, 14,  2),  LocalDate.of(   6, 12,  4) },
            { PaxDate.of(6, 14,  3),  LocalDate.of(   6, 12,  5) },
            { PaxDate.of(6, 14, 27),  LocalDate.of(   6, 12, 29) },
            { PaxDate.of(6, 14, 28),  LocalDate.of(   6, 12, 30) },
            { PaxDate.of(7,  1,  1),  LocalDate.of(   6, 12, 31) },
            { PaxDate.of(7,  1,  2),  LocalDate.of(   7,  1,  1) },

            // --- Century boundary: year 399 (leap) and 400 (not leap), 401 ---
            { PaxDate.of(399, 13,  6),  LocalDate.of( 399, 12,  3) },
            { PaxDate.of(399, 13,  7),  LocalDate.of( 399, 12,  4) },
            { PaxDate.of(399, 14,  1),  LocalDate.of( 399, 12,  5) },
            { PaxDate.of(399, 14,  2),  LocalDate.of( 399, 12,  6) },
            { PaxDate.of(399, 14,  3),  LocalDate.of( 399, 12,  7) },
            { PaxDate.of(400, 13, 27),  LocalDate.of( 400, 12, 29) },
            { PaxDate.of(400, 13, 28),  LocalDate.of( 400, 12, 30) },
            { PaxDate.of(401,  1,  1),  LocalDate.of( 400, 12, 31) },
            { PaxDate.of(401,  1,  2),  LocalDate.of( 401,  1,  1) },
            { PaxDate.of(401,  1,  3),  LocalDate.of( 401,  1,  2) },

            // --- Year 0 (leap): last days of that Pax year ---
            { PaxDate.of(0, 13, 28),  LocalDate.of(   0, 12, 30) },
            { PaxDate.of(0, 13, 27),  LocalDate.of(   0, 12, 29) },

            // --- Historical dates ---
            { PaxDate.of(1582, 10,  5),  LocalDate.of(1582,  9,  9) },
            { PaxDate.of(1582, 10,  6),  LocalDate.of(1582,  9, 10) },
            { PaxDate.of(1945, 10, 28),  LocalDate.of(1945, 10,  6) },
            { PaxDate.of(2012,  6, 23),  LocalDate.of(2012,  6,  4) },
            { PaxDate.of(2012,  6, 24),  LocalDate.of(2012,  6,  5) },

            // --- BCE dates (negative proleptic years) ---
            { PaxDate.of(  -6,  1,  1),  LocalDate.of(  -6,  1,  2) },
            { PaxDate.of(  -6, 13,  6),  LocalDate.of(  -6, 12,  9) },
            { PaxDate.of(  -6, 13,  7),  LocalDate.of(  -6, 12, 10) },
            { PaxDate.of(  -6, 14,  1),  LocalDate.of(  -6, 12, 11) },
            { PaxDate.of(  -6, 14,  2),  LocalDate.of(  -6, 12, 12) },
            { PaxDate.of(  -6, 14, 27),  LocalDate.of(  -5,  1,  6) },
            { PaxDate.of(  -6, 14, 28),  LocalDate.of(  -5,  1,  7) },
            { PaxDate.of(  -5,  1,  1),  LocalDate.of(  -5,  1,  8) },
            { PaxDate.of(  -5,  1,  2),  LocalDate.of(  -5,  1,  9) },
            { PaxDate.of( -99,  1,  1),  LocalDate.of( -99,  1,  6) },
            { PaxDate.of( -99, 13,  6),  LocalDate.of( -99, 12, 13) },
            { PaxDate.of( -99, 13,  7),  LocalDate.of( -99, 12, 14) },
            { PaxDate.of( -99, 14,  1),  LocalDate.of( -99, 12, 15) },
            { PaxDate.of( -99, 14,  2),  LocalDate.of( -99, 12, 16) },
            { PaxDate.of(-100,  1,  1),  LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100, 13,  6),  LocalDate.of(-100, 12,  7) },
            { PaxDate.of(-100, 13,  7),  LocalDate.of(-100, 12,  8) },
            { PaxDate.of(-100, 14,  1),  LocalDate.of(-100, 12,  9) },
            { PaxDate.of(-100, 14,  2),  LocalDate.of(-100, 12, 10) },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} via
     * {@link PaxChronology#date(java.time.temporal.TemporalAccessor)} produces the expected
     * {@link PaxDate}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(PaxDate pax, LocalDate iso) {
        assertEquals(pax, PaxChronology.INSTANCE.date(iso));
    }
}
