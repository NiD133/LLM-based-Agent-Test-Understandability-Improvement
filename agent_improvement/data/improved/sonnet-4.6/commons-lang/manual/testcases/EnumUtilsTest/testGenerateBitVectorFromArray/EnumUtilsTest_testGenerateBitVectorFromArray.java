package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectorFromArray extends AbstractLangTest {

    // Traffic ordinals: RED=0 → bit 0 (1L), AMBER=1 → bit 1 (2L), GREEN=2 → bit 2 (4L)

    @Test
    void testGenerateBitVector_noValues_returnsZero() {
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class));
    }

    @Test
    void testGenerateBitVector_singleValue_returnsSingleBit() {
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED));   // 1 << 0
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER)); // 1 << 1
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, Traffic.GREEN)); // 1 << 2
    }

    @Test
    void testGenerateBitVector_multipleValues_returnsCombinedBits() {
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER));             // bits 0+1
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.GREEN));             // bits 0+2
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER, Traffic.GREEN));           // bits 1+2
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN)); // bits 0+1+2
    }

    @Test
    void testGenerateBitVector_duplicateValues_treatedAsPresent() {
        // A duplicate enum value is silently folded; result equals the de-duplicated form
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));
    }

    @Test
    void testGenerateBitVector_highOrdinals_noIntToLongConversionIssue() {
        // Ordinals >= 31 must use long shifts (1L <<), not int shifts ((int)1 <<), to avoid sign-extension bugs
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, Enum64.A31));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, Enum64.A32));
        // ordinal 63 sets the sign bit of a long, which equals Long.MIN_VALUE
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }
}
