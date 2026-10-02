package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector extends AbstractLangTest {

    @Test
    void testGenerateBitVector() {
        // Empty set produces zero — no bits set
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class, EnumSet.noneOf(Traffic.class)));

        // Each Traffic enum constant maps to bit (1L << ordinal):
        // RED=ordinal 0 → bit 0 (value 1), AMBER=ordinal 1 → bit 1 (value 2), GREEN=ordinal 2 → bit 2 (value 4)
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED)));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER)));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.GREEN)));

        // Combinations — the result is the bitwise OR of each constant's individual bit
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)));
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)));

        // Enum64 has 64 constants (ordinals 0–63); verify that high-ordinal constants use the correct
        // bit positions without int/long truncation (ordinal 31 would be sign bit if cast to int)
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A31)));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A32)));

        // Ordinal 63 maps to bit 63, which equals Long.MIN_VALUE in two's-complement representation
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
    }
}
