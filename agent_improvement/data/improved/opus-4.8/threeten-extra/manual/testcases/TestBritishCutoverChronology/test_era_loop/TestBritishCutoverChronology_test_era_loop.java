package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_era_loop {

    /**
     * Walks every proleptic year from -200 to 199 and checks that the era view of
     * a British-cutover date is self-consistent:
     * <ul>
     *   <li>years 0 and below belong to the BC era, positive years to the AD era;</li>
     *   <li>the year-of-era counts backwards through BC (1 - year) and matches the
     *       proleptic year in AD;</li>
     *   <li>building the same date from (era, year-of-era) yields the original date.</li>
     * </ul>
     */
    @Test
    public void test_era_loop() {
        for (int year = -200; year < 200; year++) {
            BritishCutoverDate base = BritishCutoverChronology.INSTANCE.date(year, 1, 1);
            assertEquals(year, base.get(YEAR));

            JulianEra expectedEra = (year <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(expectedEra, base.getEra());

            int expectedYearOfEra = (year <= 0 ? 1 - year : year);
            assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

            BritishCutoverDate eraBased =
                BritishCutoverChronology.INSTANCE.date(expectedEra, expectedYearOfEra, 1, 1);
            assertEquals(base, eraBased);
        }
    }
}
