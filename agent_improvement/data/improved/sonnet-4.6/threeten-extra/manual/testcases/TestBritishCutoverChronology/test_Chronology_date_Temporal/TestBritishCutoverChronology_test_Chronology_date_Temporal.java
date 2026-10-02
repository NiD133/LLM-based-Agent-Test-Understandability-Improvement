package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverChronology#date(java.time.temporal.TemporalAccessor)}
 * correctly converts ISO {@link LocalDate} values to their equivalent
 * {@link BritishCutoverDate} representations across the Julian/Gregorian cutover.
 */
public class TestBritishCutoverChronology_test_Chronology_date_Temporal {

    public static Object[][] data_samples() {
        return new Object[][] {
            // Very early dates (Julian calendar, large Julian/ISO offset)
            { BritishCutoverDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),    LocalDate.of(1, 1, 1)   },
            { BritishCutoverDate.of(1, 2, 28),   LocalDate.of(1, 2, 26)  },
            { BritishCutoverDate.of(1, 3, 1),    LocalDate.of(1, 2, 27)  },
            { BritishCutoverDate.of(1, 3, 2),    LocalDate.of(1, 2, 28)  },
            { BritishCutoverDate.of(1, 3, 3),    LocalDate.of(1, 3, 1)   },

            // Leap year boundary in year 4 (Julian leap year)
            { BritishCutoverDate.of(4, 2, 28),   LocalDate.of(4, 2, 26)  },
            { BritishCutoverDate.of(4, 2, 29),   LocalDate.of(4, 2, 27)  },
            { BritishCutoverDate.of(4, 3, 1),    LocalDate.of(4, 2, 28)  },
            { BritishCutoverDate.of(4, 3, 2),    LocalDate.of(4, 2, 29)  },
            { BritishCutoverDate.of(4, 3, 3),    LocalDate.of(4, 3, 1)   },

            // Year 100: leap in Julian but not Gregorian — offset diverges
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),  LocalDate.of(100, 3, 1)  },
            { BritishCutoverDate.of(100, 3, 3),  LocalDate.of(100, 3, 2)  },

            // Negative / zero proleptic year
            { BritishCutoverDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },

            // 1582 — when the Vatican introduced the Gregorian calendar (not yet adopted in Britain)
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // Dates approaching the British cutover (Julian still in effect)
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11)  },
            { BritishCutoverDate.of(1752, 1, 1),   LocalDate.of(1752, 1, 12)  },

            // Dates just before the cutover (still Julian)
            { BritishCutoverDate.of(1752, 9, 1),  LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2),  LocalDate.of(1752, 9, 13) },

            // Dates inside the cutover gap (3–13 Sep 1752 are "invalid" but leniently accepted)
            { BritishCutoverDate.of(1752, 9, 3),  LocalDate.of(1752, 9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },

            // First valid Gregorian date and post-cutover dates
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },

            // Modern dates (Gregorian, 1:1 mapping)
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5),   LocalDate.of(2012, 7, 5)   },
            { BritishCutoverDate.of(2012, 7, 6),   LocalDate.of(2012, 7, 6)   },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverChronology.INSTANCE.date(iso));
    }
}
