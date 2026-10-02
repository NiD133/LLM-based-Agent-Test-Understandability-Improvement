package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testTriplets {

    private static final TripletEncoding[] ZERO_ZERO_VALUE_ENCODINGS = {
            new TripletEncoding("000000", new byte[] { (byte) 0, (byte) 0, (byte) 0 }),
            new TripletEncoding("000001", new byte[] { (byte) 0, (byte) 0, (byte) 1 }),
            new TripletEncoding("000002", new byte[] { (byte) 0, (byte) 0, (byte) 2 }),
            new TripletEncoding("000003", new byte[] { (byte) 0, (byte) 0, (byte) 3 }),
            new TripletEncoding("000004", new byte[] { (byte) 0, (byte) 0, (byte) 4 }),
            new TripletEncoding("000005", new byte[] { (byte) 0, (byte) 0, (byte) 5 }),
            new TripletEncoding("000006", new byte[] { (byte) 0, (byte) 0, (byte) 6 }),
            new TripletEncoding("000007", new byte[] { (byte) 0, (byte) 0, (byte) 7 }),
            new TripletEncoding("000008", new byte[] { (byte) 0, (byte) 0, (byte) 8 }),
            new TripletEncoding("000009", new byte[] { (byte) 0, (byte) 0, (byte) 9 }),
            new TripletEncoding("00000A", new byte[] { (byte) 0, (byte) 0, (byte) 10 }),
            new TripletEncoding("00000B", new byte[] { (byte) 0, (byte) 0, (byte) 11 }),
            new TripletEncoding("00000C", new byte[] { (byte) 0, (byte) 0, (byte) 12 }),
            new TripletEncoding("00000D", new byte[] { (byte) 0, (byte) 0, (byte) 13 }),
            new TripletEncoding("00000E", new byte[] { (byte) 0, (byte) 0, (byte) 14 }),
            new TripletEncoding("00000F", new byte[] { (byte) 0, (byte) 0, (byte) 15 }),
    };

    @Test
    void testTriplets() {
        for (final TripletEncoding encoding : ZERO_ZERO_VALUE_ENCODINGS) {
            assertTripletEncodesToExpectedHex(encoding);
        }
    }

    private void assertTripletEncodesToExpectedHex(final TripletEncoding encoding) {
        assertEquals(encoding.expectedHex, new String(new Base16().encode(encoding.triplet)));
    }

    private static final class TripletEncoding {
        private final String expectedHex;
        private final byte[] triplet;

        private TripletEncoding(final String expectedHex, final byte[] triplet) {
            this.expectedHex = expectedHex;
            this.triplet = triplet;
        }
    }
}
