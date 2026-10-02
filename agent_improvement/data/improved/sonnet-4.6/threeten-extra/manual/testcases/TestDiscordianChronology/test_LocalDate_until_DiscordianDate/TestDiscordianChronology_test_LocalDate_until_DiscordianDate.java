package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#until(java.time.temporal.Temporal)} returns {@link Period#ZERO}
 * when the target is a {@link DiscordianDate} representing the same calendar day as the ISO date.
 * This verifies cross-chronology date equality via the temporal {@code until} API.
 */
public class TestDiscordianChronology_test_LocalDate_until_DiscordianDate {

    /**
     * Pairs of (DiscordianDate, LocalDate) that represent the same point in time.
     * Each row maps a Discordian date to its ISO equivalent so that the period
     * between them is zero when computed with {@code LocalDate#until}.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1),      LocalDate.of(-1164, 1, 1)  },
            { DiscordianDate.of(166, 1, 1),    LocalDate.of(-1000, 1, 1)  },
            { DiscordianDate.of(1156, 1, 1),   LocalDate.of(-10, 1, 1)    },
            { DiscordianDate.of(1166, 1, 1),   LocalDate.of(0, 1, 1)      },
            { DiscordianDate.of(1167, 1, 1),   LocalDate.of(1, 1, 1)      },
            { DiscordianDate.of(1167, 1, 2),   LocalDate.of(1, 1, 2)      },
            { DiscordianDate.of(1167, 1, 3),   LocalDate.of(1, 1, 3)      },
            // Discordian month 1 days spanning into ISO February
            { DiscordianDate.of(1167, 1, 57),  LocalDate.of(1, 2, 26)     },
            { DiscordianDate.of(1167, 1, 58),  LocalDate.of(1, 2, 27)     },
            { DiscordianDate.of(1167, 1, 59),  LocalDate.of(1, 2, 28)     },
            { DiscordianDate.of(1167, 1, 60),  LocalDate.of(1, 3, 1)      },
            // Leap year: St. Tib's Day (month=0, day=0) aligns with Feb 29
            { DiscordianDate.of(1170, 1, 57),  LocalDate.of(4, 2, 26)     },
            { DiscordianDate.of(1170, 1, 58),  LocalDate.of(4, 2, 27)     },
            { DiscordianDate.of(1170, 1, 59),  LocalDate.of(4, 2, 28)     },
            { DiscordianDate.of(1170, 0, 0),   LocalDate.of(4, 2, 29)     },  // St. Tib's Day
            { DiscordianDate.of(1170, 1, 60),  LocalDate.of(4, 3, 1)      },
            // Century year (100 AD) — not a leap year under Gregorian rules
            { DiscordianDate.of(1266, 1, 57),  LocalDate.of(100, 2, 26)   },
            { DiscordianDate.of(1266, 1, 58),  LocalDate.of(100, 2, 27)   },
            { DiscordianDate.of(1266, 1, 59),  LocalDate.of(100, 2, 28)   },
            { DiscordianDate.of(1266, 1, 60),  LocalDate.of(100, 3, 1)    },
            { DiscordianDate.of(1266, 1, 61),  LocalDate.of(100, 3, 2)    },
            // End of year 0 (ISO)
            { DiscordianDate.of(1166, 5, 73),  LocalDate.of(0, 12, 31)    },
            { DiscordianDate.of(1166, 5, 72),  LocalDate.of(0, 12, 30)    },
            // Historical dates: Gregorian calendar adoption boundary (Oct 1582)
            { DiscordianDate.of(2748, 4, 68),  LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69),  LocalDate.of(1582, 10, 15) },
            // Modern dates
            { DiscordianDate.of(3111, 5, 24),  LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40),  LocalDate.of(2012, 7, 5)   },
            { DiscordianDate.of(3178, 3, 41),  LocalDate.of(2012, 7, 6)   },
        };
    }

    /**
     * Verifies that calling {@code iso.until(discordian)} returns {@link Period#ZERO}
     * for date pairs that represent the same day in both calendars.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(discordian));
    }
}
