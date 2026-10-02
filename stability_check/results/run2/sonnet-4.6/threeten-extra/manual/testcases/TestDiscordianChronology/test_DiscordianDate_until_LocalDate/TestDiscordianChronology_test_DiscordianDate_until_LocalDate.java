package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link DiscordianDate#until(java.time.temporal.Temporal)} returns a zero-length period
 * when the end date is the ISO equivalent of the start Discordian date.
 */
public class TestDiscordianChronology_test_DiscordianDate_until_LocalDate {

    /**
     * Pairs of (DiscordianDate, equivalent ISO LocalDate) used to verify that
     * {@code discordian.until(iso)} returns a zero period — i.e., the two dates
     * represent exactly the same calendar instant.
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
            { DiscordianDate.of(1167, 1, 57),  LocalDate.of(1, 2, 26)     },
            { DiscordianDate.of(1167, 1, 58),  LocalDate.of(1, 2, 27)     },
            { DiscordianDate.of(1167, 1, 59),  LocalDate.of(1, 2, 28)     },
            { DiscordianDate.of(1167, 1, 60),  LocalDate.of(1, 3, 1)      },
            { DiscordianDate.of(1170, 1, 57),  LocalDate.of(4, 2, 26)     },
            { DiscordianDate.of(1170, 1, 58),  LocalDate.of(4, 2, 27)     },
            { DiscordianDate.of(1170, 1, 59),  LocalDate.of(4, 2, 28)     },
            // St. Tib's Day (leap day) in year 1170 maps to ISO February 29
            { DiscordianDate.of(1170, 0, 0),   LocalDate.of(4, 2, 29)     },
            { DiscordianDate.of(1170, 1, 60),  LocalDate.of(4, 3, 1)      },
            { DiscordianDate.of(1266, 1, 57),  LocalDate.of(100, 2, 26)   },
            { DiscordianDate.of(1266, 1, 58),  LocalDate.of(100, 2, 27)   },
            { DiscordianDate.of(1266, 1, 59),  LocalDate.of(100, 2, 28)   },
            // Year 1266 (ISO 100) is NOT a leap year (divisible by 100, not by 400)
            { DiscordianDate.of(1266, 1, 60),  LocalDate.of(100, 3, 1)    },
            { DiscordianDate.of(1266, 1, 61),  LocalDate.of(100, 3, 2)    },
            { DiscordianDate.of(1166, 5, 73),  LocalDate.of(0, 12, 31)    },
            { DiscordianDate.of(1166, 5, 72),  LocalDate.of(0, 12, 30)    },
            { DiscordianDate.of(2748, 4, 68),  LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69),  LocalDate.of(1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24),  LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40),  LocalDate.of(2012, 7, 5)   },
            { DiscordianDate.of(3178, 3, 41),  LocalDate.of(2012, 7, 6)   },
        };
    }

    /**
     * Verifies that calling {@code discordian.until(iso)} on a Discordian date and its
     * ISO equivalent returns a zero-length period, confirming correct date correspondence.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_until_LocalDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordian.until(iso));
    }
}
