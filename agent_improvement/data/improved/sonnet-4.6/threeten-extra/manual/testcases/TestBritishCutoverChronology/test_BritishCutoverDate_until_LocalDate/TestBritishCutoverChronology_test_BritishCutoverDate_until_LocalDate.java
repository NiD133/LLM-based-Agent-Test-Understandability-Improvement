package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that BritishCutoverDate.until(LocalDate) returns a zero period when
 * the target LocalDate is the ISO-equivalent of the BritishCutoverDate.
 * The two dates represent the same instant in time, so the elapsed period is zero.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_until_LocalDate {

    /**
     * Pairs of (BritishCutoverDate, equivalent ISO LocalDate).
     * The BritishCutoverDate before the 1752 cutover follows the Julian calendar,
     * so it differs from its ISO equivalent by a growing number of days.
     * After the cutover (14 Sep 1752 onward) dates align 1-to-1 with ISO.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early Julian dates — two-day offset vs ISO
            { BritishCutoverDate.of(1, 1, 1),     LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),     LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),     LocalDate.of(1, 1, 1)   },
            { BritishCutoverDate.of(1, 2, 28),    LocalDate.of(1, 2, 26)  },
            { BritishCutoverDate.of(1, 3, 1),     LocalDate.of(1, 2, 27)  },
            { BritishCutoverDate.of(1, 3, 2),     LocalDate.of(1, 2, 28)  },
            { BritishCutoverDate.of(1, 3, 3),     LocalDate.of(1, 3, 1)   },
            // Around year 4 leap year (Julian has leap, offset stays)
            { BritishCutoverDate.of(4, 2, 28),    LocalDate.of(4, 2, 26)  },
            { BritishCutoverDate.of(4, 2, 29),    LocalDate.of(4, 2, 27)  },
            { BritishCutoverDate.of(4, 3, 1),     LocalDate.of(4, 2, 28)  },
            { BritishCutoverDate.of(4, 3, 2),     LocalDate.of(4, 2, 29)  },
            { BritishCutoverDate.of(4, 3, 3),     LocalDate.of(4, 3, 1)   },
            // Year 100: Julian is a leap year, Gregorian is not — offset grows by 1
            { BritishCutoverDate.of(100, 2, 28),  LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29),  LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),   LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),   LocalDate.of(100, 3, 1)  },
            { BritishCutoverDate.of(100, 3, 3),   LocalDate.of(100, 3, 2)  },
            // Negative (BC) years
            { BritishCutoverDate.of(0, 12, 31),   LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30),   LocalDate.of(0, 12, 28) },
            // Around the 1582 Gregorian introduction (offset is 10 days at this point)
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            // Approaching the British cutover (offset is 11 days)
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11)  },
            { BritishCutoverDate.of(1752, 1, 1),   LocalDate.of(1752, 1, 12)  },
            // Just before the cutover gap (2 Sep 1752 is the last Julian day)
            { BritishCutoverDate.of(1752, 9, 1),   LocalDate.of(1752, 9, 12)  },
            { BritishCutoverDate.of(1752, 9, 2),   LocalDate.of(1752, 9, 13)  },
            // Dates inside the cutover gap — leniently accepted as Julian, shifted by 11
            { BritishCutoverDate.of(1752, 9, 3),   LocalDate.of(1752, 9, 14)  }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13),  LocalDate.of(1752, 9, 24)  },
            // From 14 Sep 1752 onward dates are Gregorian: 1-to-1 with ISO
            { BritishCutoverDate.of(1752, 9, 14),  LocalDate.of(1752, 9, 14)  },
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5),   LocalDate.of(2012, 7, 5)   },
            { BritishCutoverDate.of(2012, 7, 6),   LocalDate.of(2012, 7, 6)   },
        };
    }

    /**
     * Verifies that calling until(iso) on a BritishCutoverDate returns a zero period
     * when {@code iso} is the ISO-equivalent of that date. The two dates denote the
     * same point in time, so no elapsed period exists between them.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_until_LocalDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(BritishCutoverChronology.INSTANCE.period(0, 0, 0), cutover.until(iso));
    }
}
