package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that, for every proleptic year, a {@link DiscordianDate} built directly
 * is equivalent to one built via the (era, year-of-era) factory, and that its
 * year-related fields report the expected values.
 *
 * <p>The Discordian chronology has a single era, {@link DiscordianEra#YOLD}, and its
 * proleptic year equals its year-of-era. So for any year {@code y}:
 * <ul>
 *   <li>{@code get(YEAR)} and {@code get(YEAR_OF_ERA)} both return {@code y};</li>
 *   <li>{@code getEra()} returns {@code YOLD};</li>
 *   <li>{@code date(y, ...)} equals {@code date(YOLD, y, ...)}.</li>
 * </ul>
 */
public class TestDiscordianChronology_test_era_loop {

    @Test
    public void date_and_eraBasedDate_areEquivalent_forEveryYear() {
        for (int year = 1; year < 200; year++) {
            DiscordianDate dateFromYear = DiscordianChronology.INSTANCE.date(year, 1, 1);

            assertEquals(year, dateFromYear.get(YEAR));
            assertEquals(DiscordianEra.YOLD, dateFromYear.getEra());
            assertEquals(year, dateFromYear.get(YEAR_OF_ERA));

            DiscordianDate dateFromEraAndYear =
                    DiscordianChronology.INSTANCE.date(DiscordianEra.YOLD, year, 1, 1);
            assertEquals(dateFromYear, dateFromEraAndYear);
        }
    }
}
