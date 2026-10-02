package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash32ByteArrayInt {

    /**
     * Random input byte arrays of decreasing lengths (16 bytes down to 0 bytes).
     * Each array exercises a different code path in the 32-bit hash function
     * (full 4-byte blocks plus 0–3 tail bytes, including the empty-input edge case).
     */
    static final byte[][] input = {
        { (byte) 0xed, (byte) 0x53, (byte) 0xc4, (byte) 0xa5, (byte) 0x3b, (byte) 0x1b,
          (byte) 0xbd, (byte) 0xc2, (byte) 0x52, (byte) 0x7d, (byte) 0xc3, (byte) 0xef,
          (byte) 0x53, (byte) 0x5f, (byte) 0xae, (byte) 0x3b },   // 16 bytes (4 full blocks, 0 tail)
        { (byte) 0x21, (byte) 0x65, (byte) 0x59, (byte) 0x4e, (byte) 0xd8, (byte) 0x12,
          (byte) 0xf9, (byte) 0x05, (byte) 0x80, (byte) 0xe9, (byte) 0x1e, (byte) 0xed,
          (byte) 0xe4, (byte) 0x56, (byte) 0xbb },                 // 15 bytes (3 full blocks, 3 tail)
        { (byte) 0x2b, (byte) 0x02, (byte) 0xb1, (byte) 0xd0, (byte) 0x3d, (byte) 0xce,
          (byte) 0x31, (byte) 0x3d, (byte) 0x97, (byte) 0xc4, (byte) 0x91, (byte) 0x0d,
          (byte) 0xf7, (byte) 0x17 },                              // 14 bytes (3 full blocks, 2 tail)
        { (byte) 0x8e, (byte) 0xa7, (byte) 0x9a, (byte) 0x02, (byte) 0xe8, (byte) 0xb9,
          (byte) 0x6a, (byte) 0xda, (byte) 0x92, (byte) 0xad, (byte) 0xe9, (byte) 0x2d,
          (byte) 0x21 },                                           // 13 bytes (3 full blocks, 1 tail)
        { (byte) 0xa9, (byte) 0x6d, (byte) 0xea, (byte) 0x77, (byte) 0x06, (byte) 0xce,
          (byte) 0x1b, (byte) 0x85, (byte) 0x48, (byte) 0x27, (byte) 0x4c, (byte) 0xfe }, // 12 bytes
        { (byte) 0xec, (byte) 0x93, (byte) 0xa0, (byte) 0x12, (byte) 0x60, (byte) 0xee,
          (byte) 0xc8, (byte) 0x0a, (byte) 0xc5, (byte) 0x90, (byte) 0x62 },              // 11 bytes
        { (byte) 0x55, (byte) 0x6d, (byte) 0x93, (byte) 0x66, (byte) 0x14, (byte) 0x6d,
          (byte) 0xdf, (byte) 0x00, (byte) 0x58, (byte) 0x99 },                           // 10 bytes
        { (byte) 0x3c, (byte) 0x72, (byte) 0x20, (byte) 0x1f, (byte) 0xd2, (byte) 0x59,
          (byte) 0x19, (byte) 0xdb, (byte) 0xa1 },                                        //  9 bytes
        { (byte) 0x23, (byte) 0xa8, (byte) 0xb1, (byte) 0x87, (byte) 0x55, (byte) 0xf7,
          (byte) 0x8a, (byte) 0x4b },                                                      //  8 bytes
        { (byte) 0xe2, (byte) 0x42, (byte) 0x1c, (byte) 0x2d, (byte) 0xc1, (byte) 0xe4,
          (byte) 0x3e },                                                                    //  7 bytes
        { (byte) 0x66, (byte) 0xa6, (byte) 0xb5, (byte) 0x5a, (byte) 0x74, (byte) 0xd9 }, //  6 bytes
        { (byte) 0xe8, (byte) 0x76, (byte) 0xa8, (byte) 0x90, (byte) 0x76 },              //  5 bytes
        { (byte) 0xeb, (byte) 0x25, (byte) 0x3f, (byte) 0x87 },                           //  4 bytes (1 full block, 0 tail)
        { (byte) 0x37, (byte) 0xa0, (byte) 0xa9 },                                        //  3 bytes (0 full blocks, 3 tail)
        { (byte) 0x5b, (byte) 0x5d },                                                      //  2 bytes (0 full blocks, 2 tail)
        { (byte) 0x7e },                                                                    //  1 byte  (0 full blocks, 1 tail)
        {}                                                                                  //  0 bytes (empty input edge case)
    };

    /**
     * Expected 32-bit MurmurHash2 results using the default library seed (0x9747b28c),
     * one entry per input array above (same ordering).
     */
    static final int[] results32_standard = {
        0x96814fb3, // input[0]  – 16 bytes
        0x485dcaba, // input[1]  – 15 bytes
        0x331dc4ae, // input[2]  – 14 bytes
        0xc6a7bf2f, // input[3]  – 13 bytes
        0xcdf35de0, // input[4]  – 12 bytes
        0xd9dec7cc, // input[5]  – 11 bytes
        0x63a7318a, // input[6]  – 10 bytes
        0xd0d3c2de, // input[7]  –  9 bytes
        0x90923aef, // input[8]  –  8 bytes
        0xaf35c1e2, // input[9]  –  7 bytes
        0x735377b2, // input[10] –  6 bytes
        0x366c98f3, // input[11] –  5 bytes
        0x9c48ee29, // input[12] –  4 bytes
        0x0b615790, // input[13] –  3 bytes
        0xb4308ac1, // input[14] –  2 bytes
        0xec98125a, // input[15] –  1 byte
        0x106e08d9, // input[16] –  0 bytes
    };

    /**
     * Verifies that {@code MurmurHash2.hash32(byte[], int)} produces the correct
     * 32-bit hash for every test vector, covering all tail-byte lengths (0–3)
     * as well as the empty-input edge case.
     */
    @Test
    void testHash32ByteArrayInt() {
        for (int i = 0; i < input.length; i++) {
            final int actual = MurmurHash2.hash32(input[i], input[i].length);
            assertEquals(results32_standard[i], actual,
                String.format("hash32 mismatch for input[%d] (%d bytes): "
                    + "expected 0x%08x but got 0x%08x",
                    i, input[i].length, results32_standard[i], actual));
        }
    }
}
