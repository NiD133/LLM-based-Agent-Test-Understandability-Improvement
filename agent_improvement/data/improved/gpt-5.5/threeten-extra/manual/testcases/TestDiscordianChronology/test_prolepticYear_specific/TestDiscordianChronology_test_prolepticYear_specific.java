package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertEquals(4, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 4));
        assertEquals(3, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 3));
        assertEquals(2, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 2));
        assertEquals(1, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 1));
    }
}
