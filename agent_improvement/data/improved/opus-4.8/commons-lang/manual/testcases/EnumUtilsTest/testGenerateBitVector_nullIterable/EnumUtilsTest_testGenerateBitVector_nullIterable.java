package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link EnumUtils#generateBitVector(Class, Iterable)} rejects a
 * {@code null} {@link Iterable} of values.
 */
public class EnumUtilsTest_testGenerateBitVector_nullIterable extends AbstractLangTest {

    @Test
    void testGenerateBitVector_nullIterable() {
        // A null Iterable of enum values must trigger a NullPointerException.
        final Iterable<Traffic> nullValues = null;

        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, nullValues));
    }
}
