package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_loop {

    @Test
    public void test_era_loop() {
        InternationalFixedEra era = InternationalFixedEra.CE;
        for (int year = 1; year < 200; year++) {
            InternationalFixedDate base = InternationalFixedChronology.INSTANCE.date(year, 1, 1);
            assertEquals(year, base.get(YEAR));
            assertEquals(era, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));
            InternationalFixedDate eraBased = InternationalFixedChronology.INSTANCE.date(era, year, 1, 1);
            assertEquals(base, eraBased);
        }
    }
}
