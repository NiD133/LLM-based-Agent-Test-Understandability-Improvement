package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVectors extends AbstractLangTest {

    /**
     * Asserts that decoding the given bit-vector {@code values} for {@link Traffic}
     * produces exactly the {@code expected} set of constants.
     *
     * <p>The {@code values} are forwarded verbatim to the varargs method, so a single
     * argument exercises the single-vector path and two arguments exercise the
     * multi-vector path.</p>
     */
    private void assertTrafficBits(final EnumSet<Traffic> expected, final long... values) {
        assertEquals(expected, EnumUtils.processBitVectors(Traffic.class, values));
    }

    @Test
    void testProcessBitVectors() {
        // Single bit-vector: each bit (1, 2, 4) maps to one Traffic constant.
        assertTrafficBits(EnumSet.noneOf(Traffic.class), 0L);
        assertTrafficBits(EnumSet.of(Traffic.RED), 1L);
        assertTrafficBits(EnumSet.of(Traffic.AMBER), 2L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.AMBER), 3L);
        assertTrafficBits(EnumSet.of(Traffic.GREEN), 4L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.GREEN), 5L);
        assertTrafficBits(EnumSet.of(Traffic.AMBER, Traffic.GREEN), 6L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);

        // Two bit-vectors: the leading 0L is the (empty) high-order word; the
        // low-order word carries the same bits as above.
        assertTrafficBits(EnumSet.noneOf(Traffic.class), 0L, 0L);
        assertTrafficBits(EnumSet.of(Traffic.RED), 0L, 1L);
        assertTrafficBits(EnumSet.of(Traffic.AMBER), 0L, 2L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.AMBER), 0L, 3L);
        assertTrafficBits(EnumSet.of(Traffic.GREEN), 0L, 4L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.GREEN), 0L, 5L);
        assertTrafficBits(EnumSet.of(Traffic.AMBER, Traffic.GREEN), 0L, 6L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), 0L, 7L);

        // High-order word holds bits beyond the enum's range (666L); they must be
        // ignored, so results match the low-order word alone.
        assertTrafficBits(EnumSet.noneOf(Traffic.class), 666L, 0L);
        assertTrafficBits(EnumSet.of(Traffic.RED), 666L, 1L);
        assertTrafficBits(EnumSet.of(Traffic.AMBER), 666L, 2L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.AMBER), 666L, 3L);
        assertTrafficBits(EnumSet.of(Traffic.GREEN), 666L, 4L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.GREEN), 666L, 5L);
        assertTrafficBits(EnumSet.of(Traffic.AMBER, Traffic.GREEN), 666L, 6L);
        assertTrafficBits(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), 666L, 7L);

        // 64-constant enum: confirm bits 31, 32 and 63 decode correctly with no
        // int<->long conversion issues. Long.MIN_VALUE is bit 63 (the sign bit).
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, Long.MIN_VALUE));
    }
}
