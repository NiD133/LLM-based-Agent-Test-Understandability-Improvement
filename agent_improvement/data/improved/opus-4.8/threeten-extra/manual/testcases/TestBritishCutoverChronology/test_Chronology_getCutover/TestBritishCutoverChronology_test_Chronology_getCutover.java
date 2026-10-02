package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BritishCutoverChronology#getCutover()}.
 */
public class TestBritishCutoverChronology_test_Chronology_getCutover {

    /**
     * The cutover date is the first day the Gregorian (ISO) calendar applies:
     * Thursday 14th September 1752.
     */
    @Test
    public void test_Chronology_getCutover() {
        LocalDate expectedCutover = LocalDate.of(1752, 9, 14);

        assertEquals(expectedCutover, BritishCutoverChronology.INSTANCE.getCutover());
    }
}
