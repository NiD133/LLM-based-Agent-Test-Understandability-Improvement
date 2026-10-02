package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash32ByteArrayIntInt {

    private static final int TEST_SEED = 0x71b4954d;

    /**
     * Random input data with various lengths.
     */
    private static final byte[][] INPUTS = {
            {
                    (byte) 0xed, (byte) 0x53, (byte) 0xc4, (byte) 0xa5,
                    (byte) 0x3b, (byte) 0x1b, (byte) 0xbd, (byte) 0xc2,
                    (byte) 0x52, (byte) 0x7d, (byte) 0xc3, (byte) 0xef,
                    (byte) 0x53, (byte) 0x5f, (byte) 0xae, (byte) 0x3b
            },
            {
                    (byte) 0x21, (byte) 0x65, (byte) 0x59, (byte) 0x4e,
                    (byte) 0xd8, (byte) 0x12, (byte) 0xf9, (byte) 0x05,
                    (byte) 0x80, (byte) 0xe9, (byte) 0x1e, (byte) 0xed,
                    (byte) 0xe4, (byte) 0x56, (byte) 0xbb
            },
            {
                    (byte) 0x2b, (byte) 0x02, (byte) 0xb1, (byte) 0xd0,
                    (byte) 0x3d, (byte) 0xce, (byte) 0x31, (byte) 0x3d,
                    (byte) 0x97, (byte) 0xc4, (byte) 0x91, (byte) 0x0d,
                    (byte) 0xf7, (byte) 0x17
            },
            {
                    (byte) 0x8e, (byte) 0xa7, (byte) 0x9a, (byte) 0x02,
                    (byte) 0xe8, (byte) 0xb9, (byte) 0x6a, (byte) 0xda,
                    (byte) 0x92, (byte) 0xad, (byte) 0xe9, (byte) 0x2d,
                    (byte) 0x21
            },
            {
                    (byte) 0xa9, (byte) 0x6d, (byte) 0xea, (byte) 0x77,
                    (byte) 0x06, (byte) 0xce, (byte) 0x1b, (byte) 0x85,
                    (byte) 0x48, (byte) 0x27, (byte) 0x4c, (byte) 0xfe
            },
            {
                    (byte) 0xec, (byte) 0x93, (byte) 0xa0, (byte) 0x12,
                    (byte) 0x60, (byte) 0xee, (byte) 0xc8, (byte) 0x0a,
                    (byte) 0xc5, (byte) 0x90, (byte) 0x62
            },
            {
                    (byte) 0x55, (byte) 0x6d, (byte) 0x93, (byte) 0x66,
                    (byte) 0x14, (byte) 0x6d, (byte) 0xdf, (byte) 0x00,
                    (byte) 0x58, (byte) 0x99
            },
            {
                    (byte) 0x3c, (byte) 0x72, (byte) 0x20, (byte) 0x1f,
                    (byte) 0xd2, (byte) 0x59, (byte) 0x19, (byte) 0xdb,
                    (byte) 0xa1
            },
            {
                    (byte) 0x23, (byte) 0xa8, (byte) 0xb1, (byte) 0x87,
                    (byte) 0x55, (byte) 0xf7, (byte) 0x8a, (byte) 0x4b
            },
            {
                    (byte) 0xe2, (byte) 0x42, (byte) 0x1c, (byte) 0x2d,
                    (byte) 0xc1, (byte) 0xe4, (byte) 0x3e
            },
            {
                    (byte) 0x66, (byte) 0xa6, (byte) 0xb5, (byte) 0x5a,
                    (byte) 0x74, (byte) 0xd9
            },
            {
                    (byte) 0xe8, (byte) 0x76, (byte) 0xa8, (byte) 0x90,
                    (byte) 0x76
            },
            {
                    (byte) 0xeb, (byte) 0x25, (byte) 0x3f, (byte) 0x87
            },
            {
                    (byte) 0x37, (byte) 0xa0, (byte) 0xa9
            },
            {
                    (byte) 0x5b, (byte) 0x5d
            },
            {
                    (byte) 0x7e
            },
            {}
    };

    /**
     * Murmur 32-bit hash results with the special test seed.
     */
    private static final int[] EXPECTED_HASHES_WITH_TEST_SEED = {
            0xd92e493e, 0x8b50903b, 0xc3372a7b, 0x48f07e9e,
            0x8a5e4a6e, 0x57916df4, 0xa346171f, 0x1e319c86,
            0x9e1a03cd, 0x9f973e6c, 0x2d8c77f5, 0xabed8751,
            0x296708b6, 0x24f8078b, 0x111b1553, 0xa7da1996,
            0xfe776c70
    };

    @Test
    void testHash32ByteArrayIntInt() {
        for (int i = 0; i < INPUTS.length; i++) {
            assertHash32MatchesExpectedValue(i);
        }
    }

    private static void assertHash32MatchesExpectedValue(final int exampleIndex) {
        final byte[] input = INPUTS[exampleIndex];
        final int expectedHash = EXPECTED_HASHES_WITH_TEST_SEED[exampleIndex];
        final int hash = MurmurHash2.hash32(input, input.length, TEST_SEED);

        if (hash != expectedHash) {
            fail(String.format("Unexpected hash32 result for example %d: 0x%08x instead of 0x%08x", exampleIndex, hash, expectedHash));
        }
    }
}
