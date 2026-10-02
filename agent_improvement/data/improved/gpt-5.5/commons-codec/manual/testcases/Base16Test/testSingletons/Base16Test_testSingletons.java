package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testSingletons {

    private static final String[] EXPECTED_SINGLE_BYTE_ENCODINGS = {
            "00", "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "0A", "0B", "0C", "0D", "0E", "0F",
            "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "1A", "1B", "1C", "1D", "1E", "1F",
            "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "2A", "2B", "2C", "2D", "2E", "2F",
            "30", "31", "32", "33", "34", "35", "36", "37",
            "38", "39", "3A", "3B", "3C", "3D", "3E", "3F",
            "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
            "50", "51", "52", "53", "54", "55", "56", "57",
            "58", "59", "5A", "5B", "5C", "5D", "5E", "5F",
            "60", "61", "62", "63", "64", "65", "66", "67",
            "68"
    };

    @Test
    void testSingletons() {
        for (int value = 0; value < EXPECTED_SINGLE_BYTE_ENCODINGS.length; value++) {
            assertSingleByteEncoding(value, EXPECTED_SINGLE_BYTE_ENCODINGS[value]);
        }

        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }

    private void assertSingleByteEncoding(final int value, final String expectedEncoding) {
        assertEquals(expectedEncoding, new String(new Base16().encode(new byte[] { (byte) value })));
    }
}
