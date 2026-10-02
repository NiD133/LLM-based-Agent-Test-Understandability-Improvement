import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.threeten.extra.chrono.JulianChronology;
import org.threeten.extra.chrono.JulianDate;
import org.threeten.extra.chrono.JulianEra;

/**
 * Verifies how {@link JulianChronology} maps a proleptic year to its era and
 * year-of-era, and that a date built from (era, year-of-era) matches the date
 * built directly from the proleptic year.
 */
public class TestJulianChronology_test_era_loop {

    @Test
    public void test_era_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            JulianDate dateFromYear = JulianChronology.INSTANCE.date(prolepticYear, 1, 1);

            // The date should report back the proleptic year it was built from.
            assertEquals(prolepticYear, dateFromYear.get(YEAR));

            // Years 0 and earlier fall in the BC era; positive years are AD.
            JulianEra expectedEra = (prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(expectedEra, dateFromYear.getEra());

            // Within BC the year-of-era counts backwards (1 - prolepticYear);
            // within AD it equals the proleptic year.
            int expectedYearOfEra = (prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear);
            assertEquals(expectedYearOfEra, dateFromYear.get(YEAR_OF_ERA));

            // Building the same date from (era, year-of-era) must yield an equal date.
            JulianDate dateFromEra = JulianChronology.INSTANCE.date(expectedEra, expectedYearOfEra, 1, 1);
            assertEquals(dateFromYear, dateFromEra);
        }
    }
}
