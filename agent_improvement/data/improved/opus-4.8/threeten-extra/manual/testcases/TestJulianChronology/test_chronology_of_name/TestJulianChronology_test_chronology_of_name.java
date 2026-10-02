package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Julian chronology can be looked up by its name and that the
 * resolved {@link Chronology} exposes the expected identifying metadata.
 */
public class TestJulianChronology_test_chronology_of_name {

    /**
     * Looking up the chronology by the id "Julian" should return the Julian
     * chronology singleton, which reports "Julian" as its id and "julian" as its
     * calendar type.
     */
    @Test
    public void test_chronology_of_name() {
        Chronology julianChronology = Chronology.of("Julian");

        assertNotNull(julianChronology);
        assertEquals(JulianChronology.INSTANCE, julianChronology);
        assertEquals("Julian", julianChronology.getId());
        assertEquals("julian", julianChronology.getCalendarType());
    }
}
