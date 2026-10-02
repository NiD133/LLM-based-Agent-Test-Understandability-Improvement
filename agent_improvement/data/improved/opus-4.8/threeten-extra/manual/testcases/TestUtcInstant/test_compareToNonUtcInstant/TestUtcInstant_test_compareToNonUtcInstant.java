package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#compareTo} rejects values that are not
 * {@code UtcInstant} instances.
 */
public class TestUtcInstant_test_compareToNonUtcInstant {

    /**
     * {@code UtcInstant} implements {@link Comparable}, so its raw
     * {@code compareTo(Object)} entry point must reject any argument that is
     * not itself a {@code UtcInstant} by throwing {@link ClassCastException}.
     */
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void test_compareToNonUtcInstant() {
        Comparable utcInstant = UtcInstant.ofModifiedJulianDay(0L, 2);

        assertThrows(ClassCastException.class, () -> utcInstant.compareTo(new Object()));
    }
}
