package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link BritishCutoverDate#until} correctly counts elapsed days
 * when the end point is an ISO {@link LocalDate}.
 *
 * <p>The British calendar skipped 11 days in September 1752 (Wednesday 2nd was
 * followed by Thursday 14th), so the Julian–ISO offset varies depending on
 * whether the date is before or after the cutover.  Each sample pair
 * {@code (cutover, iso)} represents the same calendar instant expressed in both
 * systems, making it straightforward to assert that shifting the ISO side by N
 * days produces exactly N days of distance.
 */
public class TestBritishCutoverChronology_test_until_DAYS {

    /**
     * Pairs of equivalent dates: a {@link BritishCutoverDate} and the
     * corresponding ISO {@link LocalDate} that falls on the same real day.
     *
     * <p>Coverage spans:
     * <ul>
     *   <li>Deep pre-cutover Julian dates (year 0–100), where the Julian/ISO
     *       offset is 2 days behind ISO.</li>
     *   <li>The 1582 Vatican cutover (offset grows to 10 days).</li>
     *   <li>The 1752 British cutover month, including the 11-day gap
     *       (Sep 3–13 never existed; they are accepted leniently).</li>
     *   <li>Post-cutover Gregorian dates where Julian = ISO.</li>
     * </ul>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Deep pre-cutover: Julian is 2 days behind ISO ---
            { BritishCutoverDate.of(1, 1, 1),       LocalDate.of(0, 12, 30)  },
            { BritishCutoverDate.of(1, 1, 2),       LocalDate.of(0, 12, 31)  },
            { BritishCutoverDate.of(1, 1, 3),       LocalDate.of(1, 1, 1)    },
            { BritishCutoverDate.of(1, 2, 28),      LocalDate.of(1, 2, 26)   },
            { BritishCutoverDate.of(1, 3, 1),       LocalDate.of(1, 2, 27)   },
            { BritishCutoverDate.of(1, 3, 2),       LocalDate.of(1, 2, 28)   },
            { BritishCutoverDate.of(1, 3, 3),       LocalDate.of(1, 3, 1)    },
            // Julian leap-year (divisible by 4) still applies in year 4
            { BritishCutoverDate.of(4, 2, 28),      LocalDate.of(4, 2, 26)   },
            { BritishCutoverDate.of(4, 2, 29),      LocalDate.of(4, 2, 27)   },
            { BritishCutoverDate.of(4, 3, 1),       LocalDate.of(4, 2, 28)   },
            { BritishCutoverDate.of(4, 3, 2),       LocalDate.of(4, 2, 29)   },
            { BritishCutoverDate.of(4, 3, 3),       LocalDate.of(4, 3, 1)    },
            // Year 100 is a Julian leap year but not a Gregorian one
            { BritishCutoverDate.of(100, 2, 28),    LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29),    LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),     LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),     LocalDate.of(100, 3, 1)  },
            { BritishCutoverDate.of(100, 3, 3),     LocalDate.of(100, 3, 2)  },
            // Year 0 (proleptic BC 1)
            { BritishCutoverDate.of(0, 12, 31),     LocalDate.of(0, 12, 29)  },
            { BritishCutoverDate.of(0, 12, 30),     LocalDate.of(0, 12, 28)  },
            // --- Vatican cutover 1582: offset grew to 10 days ---
            { BritishCutoverDate.of(1582, 10, 4),   LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5),   LocalDate.of(1582, 10, 15) },
            // --- Approaching the 1752 British cutover: offset is 11 days ---
            { BritishCutoverDate.of(1751, 12, 20),  LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31),  LocalDate.of(1752, 1, 11)  },
            { BritishCutoverDate.of(1752, 1, 1),    LocalDate.of(1752, 1, 12)  },
            // Sep 1–2 1752: last Julian days before the 11-day gap
            { BritishCutoverDate.of(1752, 9, 1),    LocalDate.of(1752, 9, 12)  },
            { BritishCutoverDate.of(1752, 9, 2),    LocalDate.of(1752, 9, 13)  },
            // Sep 3–13 1752 never existed; leniently treated as Julian + 11 offset
            { BritishCutoverDate.of(1752, 9, 3),    LocalDate.of(1752, 9, 14)  }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13),   LocalDate.of(1752, 9, 24)  },
            // Sep 14 1752: first Gregorian day — both systems agree from here on
            { BritishCutoverDate.of(1752, 9, 14),   LocalDate.of(1752, 9, 14)  },
            // --- Post-cutover: Julian == ISO ---
            { BritishCutoverDate.of(1945, 11, 12),  LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5),    LocalDate.of(2012, 7, 5)   },
            { BritishCutoverDate.of(2012, 7, 6),    LocalDate.of(2012, 7, 6)   },
        };
    }

    /**
     * Shifting the ISO equivalent date by N days must yield exactly N days
     * when measured from the corresponding {@link BritishCutoverDate}.
     * Negative shifts produce negative day counts (the end is before the start).
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(0,   cutover.until(iso.plusDays(0),   DAYS));
        assertEquals(1,   cutover.until(iso.plusDays(1),   DAYS));
        assertEquals(35,  cutover.until(iso.plusDays(35),  DAYS));
        assertEquals(-40, cutover.until(iso.minusDays(40), DAYS));
    }
}
