package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_plus_Period_ISO {

    @Test
    public void test_plus_Period_ISO() {
        DiscordianDate discordianDate = DiscordianDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> discordianDate.plus(isoPeriod));
    }
}
