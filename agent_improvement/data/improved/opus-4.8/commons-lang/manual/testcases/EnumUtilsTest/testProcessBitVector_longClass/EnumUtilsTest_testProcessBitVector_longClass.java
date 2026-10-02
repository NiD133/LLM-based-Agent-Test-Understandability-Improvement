package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#processBitVector(Class, long)} when the enum cannot be
 * represented as a {@code long} bit vector.
 */
public class EnumUtilsTest_testProcessBitVector_longClass extends AbstractLangTest {

    /**
     * {@code processBitVector} stores each enum constant in a single bit of a {@code long},
     * so it supports at most {@link Long#SIZE} (64) constants. {@code TooMany} declares 65
     * constants, which exceeds that limit and must be rejected with an
     * {@link IllegalArgumentException}, regardless of the bit-vector value supplied.
     */
    @Test
    void testProcessBitVector_longClass() {
        assertIllegalArgumentException(() -> EnumUtils.processBitVector(TooMany.class, 0L));
    }
}
