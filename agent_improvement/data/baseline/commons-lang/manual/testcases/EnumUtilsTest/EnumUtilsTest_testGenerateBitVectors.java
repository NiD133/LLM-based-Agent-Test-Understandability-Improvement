package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGenerateBitVectors() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class)), new long[] { 0L });
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED)), 1L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER)), 2L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN)), 4L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)), 3L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)), 5L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)), 6L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)), 7L);
        // 64 values Enum (to test whether no int<->long jdk conversion issue exists)
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A31)), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A32)), 1L << 32);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), Long.MIN_VALUE);
        // More than 64 values Enum
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.M2)), 1L, 0L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2)), 1L, 1L << 63);
    }
}
