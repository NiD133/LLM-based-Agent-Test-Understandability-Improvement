package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testTriplets {

    @Test
    void testTriplets() {
        // Encoding triplet {0, 0, b} for b in 0..15 produces "0000" + the uppercase hex digit for b
        String[] expectedHexValues = {
            "000000", "000001", "000002", "000003",
            "000004", "000005", "000006", "000007",
            "000008", "000009", "00000A", "00000B",
            "00000C", "00000D", "00000E", "00000F"
        };

        for (int b = 0; b <= 15; b++) {
            byte[] input = {(byte) 0, (byte) 0, (byte) b};
            assertEquals(expectedHexValues[b], new String(new Base16().encode(input)),
                "Encoding triplet {0, 0, " + b + "}");
        }
    }
}
