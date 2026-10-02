package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVectors extends AbstractLangTest {

    private final TrafficBitVectorCase[] trafficBitVectorCases = {
        trafficCase(0L, EnumSet.noneOf(Traffic.class)),
        trafficCase(1L, EnumSet.of(Traffic.RED)),
        trafficCase(2L, EnumSet.of(Traffic.AMBER)),
        trafficCase(3L, EnumSet.of(Traffic.RED, Traffic.AMBER)),
        trafficCase(4L, EnumSet.of(Traffic.GREEN)),
        trafficCase(5L, EnumSet.of(Traffic.RED, Traffic.GREEN)),
        trafficCase(6L, EnumSet.of(Traffic.AMBER, Traffic.GREEN)),
        trafficCase(7L, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN))
    };

    private TrafficBitVectorCase trafficCase(final long bitVector, final Object expected) {
        return new TrafficBitVectorCase(bitVector, expected);
    }

    @Test
    void testProcessBitVectors() {
        assertSingleLongTrafficBitVectors();
        assertTwoLongTrafficBitVectors();
        assertHighOrderLongsAreIgnoredForTraffic();
        assertSixtyFourthEnumConstantBitVectors();
    }

    private void assertSingleLongTrafficBitVectors() {
        for (final TrafficBitVectorCase testCase : trafficBitVectorCases) {
            assertEquals(testCase.expected, EnumUtils.processBitVectors(Traffic.class, testCase.bitVector));
        }
    }

    private void assertTwoLongTrafficBitVectors() {
        for (final TrafficBitVectorCase testCase : trafficBitVectorCases) {
            assertEquals(testCase.expected, EnumUtils.processBitVectors(Traffic.class, 0L, testCase.bitVector));
        }
    }

    private void assertHighOrderLongsAreIgnoredForTraffic() {
        for (final TrafficBitVectorCase testCase : trafficBitVectorCases) {
            assertEquals(testCase.expected, EnumUtils.processBitVectors(Traffic.class, 666L, testCase.bitVector));
        }
    }

    private void assertSixtyFourthEnumConstantBitVectors() {
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, Long.MIN_VALUE));
    }

    private final class TrafficBitVectorCase {
        private final long bitVector;
        private final Object expected;

        private TrafficBitVectorCase(final long bitVector, final Object expected) {
            this.bitVector = bitVector;
            this.expected = expected;
        }
    }

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    private enum Enum64 {
        A0,
        A1,
        A2,
        A3,
        A4,
        A5,
        A6,
        A7,
        A8,
        A9,
        A10,
        A11,
        A12,
        A13,
        A14,
        A15,
        A16,
        A17,
        A18,
        A19,
        A20,
        A21,
        A22,
        A23,
        A24,
        A25,
        A26,
        A27,
        A28,
        A29,
        A30,
        A31,
        A32,
        A33,
        A34,
        A35,
        A36,
        A37,
        A38,
        A39,
        A40,
        A41,
        A42,
        A43,
        A44,
        A45,
        A46,
        A47,
        A48,
        A49,
        A50,
        A51,
        A52,
        A53,
        A54,
        A55,
        A56,
        A57,
        A58,
        A59,
        A60,
        A61,
        A62,
        A63
    }
}
