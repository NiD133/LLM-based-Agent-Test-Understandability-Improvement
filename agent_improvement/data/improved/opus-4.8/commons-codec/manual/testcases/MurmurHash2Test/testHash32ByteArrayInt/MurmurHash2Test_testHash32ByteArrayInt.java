package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MurmurHash2#hash32(byte[], int)} against a set of known-good
 * 32-bit hashes computed with the library's default seed.
 */
public class MurmurHash2Test_testHash32ByteArrayInt {

    /**
     * Random byte arrays of varying length used as hash inputs. Each entry is
     * paired by index with the expected hash in {@link #EXPECTED_HASHES_32}.
     */
    private static final byte[][] INPUTS = {
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
     * Expected 32-bit hashes for the {@link #INPUTS}, using the default seed.
     */
    private static final int[] EXPECTED_HASHES_32 = {
        0x96814fb3, 0x485dcaba, 0x331dc4ae, 0xc6a7bf2f, 0xcdf35de0,
        0xd9dec7cc, 0x63a7318a, 0xd0d3c2de, 0x90923aef, 0xaf35c1e2,
        0x735377b2, 0x366c98f3, 0x9c48ee29, 0x0b615790, 0xb4308ac1,
        0xec98125a, 0x106e08d9
    };

    @Test
    void testHash32ByteArrayInt() {
        for (int i = 0; i < INPUTS.length; i++) {
            final int index = i;
            final int actualHash = MurmurHash2.hash32(INPUTS[index], INPUTS[index].length);
            assertEquals(EXPECTED_HASHES_32[index], actualHash,
                () -> String.format("Unexpected hash32 result for input index %d", index));
        }
    }
}
