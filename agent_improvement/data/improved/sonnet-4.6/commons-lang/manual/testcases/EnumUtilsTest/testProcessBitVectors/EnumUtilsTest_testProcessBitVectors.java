package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EnumUtils#processBitVectors(Class, long...)} which reconstructs
 * an {@link EnumSet} from a multi-word bit-vector representation.
 *
 * <p>For the three-value {@code Traffic} enum each ordinal maps to one bit:</p>
 * <pre>
 *   Traffic.RED   = ordinal 0 → bit 0 (long value 1)
 *   Traffic.AMBER = ordinal 1 → bit 1 (long value 2)
 *   Traffic.GREEN = ordinal 2 → bit 2 (long value 4)
 * </pre>
 * When multiple {@code long}s are supplied the rightmost is the least-significant word;
 * bits in words beyond the enum's size are silently ignored.
 */
public class EnumUtilsTest_testProcessBitVectors extends AbstractLangTest {

    // Bit-mask constants for the Traffic enum (ordinals 0, 1, 2)
    private static final long BIT_NONE  = 0b000L; // 0 – no member set
    private static final long BIT_RED   = 0b001L; // 1 – Traffic.RED   (ordinal 0)
    private static final long BIT_AMBER = 0b010L; // 2 – Traffic.AMBER (ordinal 1)
    private static final long BIT_GREEN = 0b100L; // 4 – Traffic.GREEN (ordinal 2)

    @Test
    void testProcessBitVectors_singleWord_allCombinationsOfTrafficEnum() {
        // All 8 combinations of three bits covering every possible Traffic subset
        assertEquals(EnumSet.noneOf(Traffic.class),
                EnumUtils.processBitVectors(Traffic.class, BIT_NONE));
        assertEquals(EnumSet.of(Traffic.RED),
                EnumUtils.processBitVectors(Traffic.class, BIT_RED));
        assertEquals(EnumSet.of(Traffic.AMBER),
                EnumUtils.processBitVectors(Traffic.class, BIT_AMBER));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER),
                EnumUtils.processBitVectors(Traffic.class, BIT_RED | BIT_AMBER));
        assertEquals(EnumSet.of(Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, BIT_RED | BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, BIT_AMBER | BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, BIT_RED | BIT_AMBER | BIT_GREEN));
    }

    @Test
    void testProcessBitVectors_twoWords_leadingZeroWordHasNoEffect() {
        // When the high-order word is 0L it carries no bits; results must equal the single-word case.
        assertEquals(EnumSet.noneOf(Traffic.class),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_NONE));
        assertEquals(EnumSet.of(Traffic.RED),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_RED));
        assertEquals(EnumSet.of(Traffic.AMBER),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_AMBER));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_RED | BIT_AMBER));
        assertEquals(EnumSet.of(Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_RED | BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_AMBER | BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, 0L, BIT_RED | BIT_AMBER | BIT_GREEN));
    }

    @Test
    void testProcessBitVectors_twoWords_highOrderBitsBeyondEnumSizeAreIgnored() {
        // 666L in the high-order word maps to ordinals >= 64 which do not exist in the
        // three-value Traffic enum, so those bits must be silently discarded.
        final long irrelevantHighBits = 666L;
        assertEquals(EnumSet.noneOf(Traffic.class),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_NONE));
        assertEquals(EnumSet.of(Traffic.RED),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_RED));
        assertEquals(EnumSet.of(Traffic.AMBER),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_AMBER));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_RED | BIT_AMBER));
        assertEquals(EnumSet.of(Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_RED | BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_AMBER | BIT_GREEN));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
                EnumUtils.processBitVectors(Traffic.class, irrelevantHighBits, BIT_RED | BIT_AMBER | BIT_GREEN));
    }

    @Test
    void testProcessBitVectors_64ValueEnum_handlesOrdinalsAbove31AndSignBitCorrectly() {
        // Enum64 has 64 members (A0..A63). These cases guard against int/long shift
        // truncation bugs: ordinal 31 is the last bit safe for an int shift, ordinal 32
        // requires a long shift, and ordinal 63 coincides with Long.MIN_VALUE's sign bit.
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, 1L << 63));
        // 1L << 63 == Long.MIN_VALUE; both expressions must decode to the same member
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, Long.MIN_VALUE));
    }
}
