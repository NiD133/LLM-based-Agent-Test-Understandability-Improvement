package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(DiscordianEra.YOLD, DiscordianChronology.INSTANCE.eraOf(1));
    }
}
