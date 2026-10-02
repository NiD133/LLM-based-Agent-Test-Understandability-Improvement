package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#processBitVector(Class, long)}, which decodes a {@code long}
 * bit vector back into the {@link EnumSet} of enum constants it represents.
 *
 * <p>Each enum constant maps to the bit at its ordinal position: ordinal 0 -&gt; bit 0
 * (value 1), ordinal 1 -&gt; bit 1 (value 2), and so on.</p>
 */
public class EnumUtilsTest_testProcessBitVector extends AbstractLangTest {

    @Test
    void testProcessBitVector() {
        // Traffic has three constants: RED (ordinal 0, bit 1), AMBER (ordinal 1, bit 2),
        // GREEN (ordinal 2, bit 4). Exercise every combination from the bit vector 0..7.
        assertEquals(EnumSet.noneOf(Traffic.class),
                EnumUtils.processBitVector(Traffic.class, 0L));
        assertEquals(EnumSet.of(Traffic.RED),
                EnumUtils.processBitVector(Traffic.class, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER),
                EnumUtils.processBitVector(Traffic.class, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER),
                EnumUtils.processBitVector(Traffic.class, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN),
                EnumUtils.processBitVector(Traffic.class, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN),
                EnumUtils.processBitVector(Traffic.class, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVector(Traffic.class, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVector(Traffic.class, 7L));

        // Enum64 has 64 constants, so the highest bits use the full width of a long.
        // This guards against any int<->long conversion issue when shifting near bit 63.
        assertEquals(EnumSet.of(Enum64.A31),
                EnumUtils.processBitVector(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32),
                EnumUtils.processBitVector(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63),
                EnumUtils.processBitVector(Enum64.class, 1L << 63));
        // Long.MIN_VALUE has only the sign bit (bit 63) set, so it equals 1L << 63.
        assertEquals(EnumSet.of(Enum64.A63),
                EnumUtils.processBitVector(Enum64.class, Long.MIN_VALUE));
    }
}
