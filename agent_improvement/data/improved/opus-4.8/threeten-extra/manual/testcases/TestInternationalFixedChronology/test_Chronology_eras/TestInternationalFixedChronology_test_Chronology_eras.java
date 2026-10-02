package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests the eras exposed by {@link InternationalFixedChronology}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = InternationalFixedChronology.INSTANCE.eras();

        // The International Fixed calendar defines exactly one era: CE.
        assertEquals(1, eras.size());
        assertTrue(eras.contains(InternationalFixedEra.CE));
    }
}
