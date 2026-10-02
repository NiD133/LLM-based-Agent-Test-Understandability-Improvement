package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash64ByteArrayIntInt {

    private static final int TEST_SEED = 0x344d1f5c;

    /**
     * Random input data with lengths from 16 bytes down to 0 bytes.
     */
    private static final byte[][] HASH_INPUTS = {
        { (byte) 0xed, (byte) 0x53, (byte) 0xc4, (byte) 0xa5, (byte) 0x3b, (byte) 0x1b, (byte) 0xbd, (byte) 0xc2, (byte) 0x52, (byte) 0x7d, (byte) 0xc3, (byte) 0xef, (byte) 0x53, (byte) 0x5f, (byte) 0xae, (byte) 0x3b },
        { (byte) 0x21, (byte) 0x65, (byte) 0x59, (byte) 0x4e, (byte) 0xd8, (byte) 0x12, (byte) 0xf9, (byte) 0x05, (byte) 0x80, (byte) 0xe9, (byte) 0x1e, (byte) 0xed, (byte) 0xe4, (byte) 0x56, (byte) 0xbb },
        { (byte) 0x2b, (byte) 0x02, (byte) 0xb1, (byte) 0xd0, (byte) 0x3d, (byte) 0xce, (byte) 0x31, (byte) 0x3d, (byte) 0x97, (byte) 0xc4, (byte) 0x91, (byte) 0x0d, (byte) 0xf7, (byte) 0x17 },
        { (byte) 0x8e, (byte) 0xa7, (byte) 0x9a, (byte) 0x02, (byte) 0xe8, (byte) 0xb9, (byte) 0x6a, (byte) 0xda, (byte) 0x92, (byte) 0xad, (byte) 0xe9, (byte) 0x2d, (byte) 0x21 },
        { (byte) 0xa9, (byte) 0x6d, (byte) 0xea, (byte) 0x77, (byte) 0x06, (byte) 0xce, (byte) 0x1b, (byte) 0x85, (byte) 0x48, (byte) 0x27, (byte) 0x4c, (byte) 0xfe },
        { (byte) 0xec, (byte) 0x93, (byte) 0xa0, (byte) 0x12, (byte) 0x60, (byte) 0xee, (byte) 0xc8, (byte) 0x0a, (byte) 0xc5, (byte) 0x90, (byte) 0x62 },
        { (byte) 0x55, (byte) 0x6d, (byte) 0x93, (byte) 0x66, (byte) 0x14, (byte) 0x6d, (byte) 0xdf, (byte) 0x00, (byte) 0x58, (byte) 0x99 },
        { (byte) 0x3c, (byte) 0x72, (byte) 0x20, (byte) 0x1f, (byte) 0xd2, (byte) 0x59, (byte) 0x19, (byte) 0xdb, (byte) 0xa1 },
        { (byte) 0x23, (byte) 0xa8, (byte) 0xb1, (byte) 0x87, (byte) 0x55, (byte) 0xf7, (byte) 0x8a, (byte) 0x4b },
        { (byte) 0xe2, (byte) 0x42, (byte) 0x1c, (byte) 0x2d, (byte) 0xc1, (byte) 0xe4, (byte) 0x3e },
        { (byte) 0x66, (byte) 0xa6, (byte) 0xb5, (byte) 0x5a, (byte) 0x74, (byte) 0xd9 },
        { (byte) 0xe8, (byte) 0x76, (byte) 0xa8, (byte) 0x90, (byte) 0x76 },
        { (byte) 0xeb, (byte) 0x25, (byte) 0x3f, (byte) 0x87 },
        { (byte) 0x37, (byte) 0xa0, (byte) 0xa9 },
        { (byte) 0x5b, (byte) 0x5d },
        { (byte) 0x7e },
        {}
    };

    /**
     * Expected Murmur 64-bit hash results for {@link #HASH_INPUTS} using {@link #TEST_SEED}.
     */
    private static final long[] EXPECTED_HASHES_WITH_TEST_SEED = {
        0x0822b1481a92e97bL,
        0xf8a9223fef0822ddL,
        0x4b49e56affae3a89L,
        0xc970296e32e1d1c1L,
        0xe2f9f88789f1b08fL,
        0x2b0459d9b4c10c61L,
        0x377e97ea9197ee89L,
        0xd2ccad460751e0e7L,
        0xff162ca8d6da8c47L,
        0xf12e051405769857L,
        0xdabba41293d5b035L,
        0xacf326b0bb690d0eL,
        0x0617f431bc1a8e04L,
        0x15b81f28d576e1b2L,
        0x28c1fe59e4f8e5baL,
        0x694dd315c9354ca9L,
        0xa97052a8f088ae6cL
    };

    @Test
    void testHash64ByteArrayIntInt() {
        for (int i = 0; i < HASH_INPUTS.length; i++) {
            assertExpectedHashForExample(i);
        }
    }

    private static void assertExpectedHashForExample(final int exampleIndex) {
        final byte[] input = HASH_INPUTS[exampleIndex];
        final long hash = MurmurHash2.hash64(input, input.length, TEST_SEED);
        final long expectedHash = EXPECTED_HASHES_WITH_TEST_SEED[exampleIndex];

        if (hash != expectedHash) {
            fail(String.format("Unexpected hash64 result for example %d: 0x%016x instead of 0x%016x", exampleIndex, hash, expectedHash));
        }
    }
}
