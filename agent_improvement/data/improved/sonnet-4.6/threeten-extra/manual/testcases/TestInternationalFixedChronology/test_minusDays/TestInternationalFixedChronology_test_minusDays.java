package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({"static-method"})
public class TestInternationalFixedChronology_test_minusDays {

    public static Object[][] data_samples() {
        return new Object[][] {
            // IFC date                             ISO date
            { InternationalFixedDate.of(1,    1,  1),  LocalDate.of(1,    1,  1)  },
            { InternationalFixedDate.of(1,    1,  2),  LocalDate.of(1,    1,  2)  },
            { InternationalFixedDate.of(1,    6, 27),  LocalDate.of(1,    6, 16)  },
            { InternationalFixedDate.of(1,    6, 28),  LocalDate.of(1,    6, 17)  },
            { InternationalFixedDate.of(1,    7,  1),  LocalDate.of(1,    6, 18)  },
            { InternationalFixedDate.of(1,    7,  2),  LocalDate.of(1,    6, 19)  },
            { InternationalFixedDate.of(1,   13, 28),  LocalDate.of(1,   12, 30)  },
            { InternationalFixedDate.of(1,   13, 27),  LocalDate.of(1,   12, 29)  },
            { InternationalFixedDate.of(1,   13, 29),  LocalDate.of(1,   12, 31)  },
            { InternationalFixedDate.of(2,    1,  1),  LocalDate.of(2,    1,  1)  },
            // year 4 is a leap year
            { InternationalFixedDate.of(4,    6, 27),  LocalDate.of(4,    6, 15)  },
            { InternationalFixedDate.of(4,    6, 28),  LocalDate.of(4,    6, 16)  },
            { InternationalFixedDate.of(4,    6, 29),  LocalDate.of(4,    6, 17)  }, // leap day
            { InternationalFixedDate.of(4,    7,  1),  LocalDate.of(4,    6, 18)  },
            { InternationalFixedDate.of(4,    7,  2),  LocalDate.of(4,    6, 19)  },
            { InternationalFixedDate.of(4,   13, 28),  LocalDate.of(4,   12, 30)  },
            { InternationalFixedDate.of(4,   13, 27),  LocalDate.of(4,   12, 29)  },
            { InternationalFixedDate.of(4,   13, 29),  LocalDate.of(4,   12, 31)  },
            { InternationalFixedDate.of(5,    1,  1),  LocalDate.of(5,    1,  1)  },
            // year 100 is NOT a leap year
            { InternationalFixedDate.of(100,  6, 27),  LocalDate.of(100,  6, 16)  },
            { InternationalFixedDate.of(100,  6, 28),  LocalDate.of(100,  6, 17)  },
            { InternationalFixedDate.of(100,  7,  1),  LocalDate.of(100,  6, 18)  },
            { InternationalFixedDate.of(100,  7,  2),  LocalDate.of(100,  6, 19)  },
            // year 400 is a leap year
            { InternationalFixedDate.of(400,  6, 27),  LocalDate.of(400,  6, 15)  },
            { InternationalFixedDate.of(400,  6, 28),  LocalDate.of(400,  6, 16)  },
            { InternationalFixedDate.of(400,  6, 29),  LocalDate.of(400,  6, 17)  }, // leap day
            { InternationalFixedDate.of(400,  7,  1),  LocalDate.of(400,  6, 18)  },
            { InternationalFixedDate.of(400,  7,  2),  LocalDate.of(400,  6, 19)  },
            // historical dates
            { InternationalFixedDate.of(1582, 9, 28),  LocalDate.of(1582,  9,  9)  },
            { InternationalFixedDate.of(1582, 10, 1),  LocalDate.of(1582,  9, 10)  },
            { InternationalFixedDate.of(1945, 10, 27), LocalDate.of(1945, 10,  6)  },
            { InternationalFixedDate.of(2012, 6, 15),  LocalDate.of(2012,  6,  3)  },
            { InternationalFixedDate.of(2012, 6, 16),  LocalDate.of(2012,  6,  4)  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(InternationalFixedDate fixed, LocalDate iso) {
        // Subtracting zero days leaves the date unchanged
        assertEquals(iso, LocalDate.from(fixed.minus(0, DAYS)));

        // Avoid going before the minimum representable date (day 35 of year 1)
        if (LocalDate.ofYearDay(1, 35).isBefore(iso)) {
            assertEquals(iso.minusDays(1),  LocalDate.from(fixed.minus(1,  DAYS)));
            assertEquals(iso.minusDays(35), LocalDate.from(fixed.minus(35, DAYS)));
        }

        // Negative amounts act as additions
        assertEquals(iso.minusDays(-1),  LocalDate.from(fixed.minus(-1,  DAYS)));
        assertEquals(iso.minusDays(-60), LocalDate.from(fixed.minus(-60, DAYS)));
    }
}
