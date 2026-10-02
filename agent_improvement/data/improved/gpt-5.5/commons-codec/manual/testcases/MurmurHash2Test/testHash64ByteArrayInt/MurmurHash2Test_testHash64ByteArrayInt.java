package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash64ByteArrayInt {

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

    private static final long[] EXPECTED_HASHES = {
        0x4987cb15118a83d9L,
        0x28e2a79e3f0394d9L,
        0x8f4600d786fc5c05L,
        0xa09b27fea4b54af3L,
        0x25f34447525bfd1eL,
        0x32fad4c21379c7bfL,
        0x4b30b99a9d931921L,
        0x4e5dab004f936cdbL,
        0x06825c27bc96cf40L,
        0xff4bf2f8a4823905L,
        0x7f7e950c064e6367L,
        0x821ade90caaa5889L,
        0x6d28c915d791686aL,
        0x9c32649372163ba2L,
        0xd66ae956c14d5212L,
        0x38ed30ee5161200fL,
        0x9bfae0a4e613fc3cL
    };

    @Test
    void testHash64ByteArrayInt() {
        for (int exampleIndex = 0; exampleIndex < INPUTS.length; exampleIndex++) {
            final byte[] input = INPUTS[exampleIndex];
            final long hash = MurmurHash2.hash64(input, input.length);
            final long expectedHash = EXPECTED_HASHES[exampleIndex];

            if (hash != expectedHash) {
                fail(String.format(
                    "Unexpected hash64 result for example %d: 0x%016x instead of 0x%016x",
                    exampleIndex,
                    hash,
                    expectedHash));
            }
        }
    }
}
