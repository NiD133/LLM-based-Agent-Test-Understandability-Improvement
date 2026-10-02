package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InternationalFixedChronology#eraOf(int)}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        // The era value 1 maps to the only era of this chronology: CE.
        assertEquals(InternationalFixedEra.CE, InternationalFixedChronology.INSTANCE.eraOf(1));
    }
}
