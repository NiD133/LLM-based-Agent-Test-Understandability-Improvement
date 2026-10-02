package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link EnumUtils#generateBitVector(Class, Iterable)} rejects enums
 * that cannot be represented in a single {@code long}.
 */
public class EnumUtilsTest_testGenerateBitVector_longClass extends AbstractLangTest {

    /**
     * {@code TooMany} declares more than {@link Long#SIZE} (64) constants, so its
     * members cannot be packed into the 64 bits of a single {@code long}.
     * {@code generateBitVector} must therefore reject it with an
     * {@link IllegalArgumentException}, even when only a single value is supplied.
     */
    @Test
    void testGenerateBitVector_longClass() {
        assertIllegalArgumentException(
                () -> EnumUtils.generateBitVector(TooMany.class, EnumSet.of(TooMany.A1)));
    }
}
