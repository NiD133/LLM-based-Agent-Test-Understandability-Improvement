package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Era;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link InternationalFixedChronology#eraOf(int)} returns the
 * single valid era (CE, value 1).
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_valid {

    @Test
    public void test_era_valid() {
        Era era = InternationalFixedChronology.INSTANCE.eraOf(1);

        assertNotNull(era);
        assertEquals(1, era.getValue());
    }
}
