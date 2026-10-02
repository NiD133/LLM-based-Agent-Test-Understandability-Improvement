package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeObject {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;
    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    private BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    void testEncodeObject() throws Exception {
        final EncodingCase[] oneByteCases = {
                new EncodingCase(new byte[1], "00000000"),
                new EncodingCase(new byte[] { BIT_0 }, "00000001"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 }, "00000011"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 }, "00000111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "00001111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "00011111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 }, "00111111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 }, "01111111"),
                new EncodingCase(new byte[] { ALL_BITS }, "11111111")
        };

        final EncodingCase[] twoByteCases = {
                new EncodingCase(new byte[2], "0000000000000000"),
                new EncodingCase(new byte[] { BIT_0, 0 }, "0000000000000001"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1, 0 }, "0000000000000011"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2, 0 }, "0000000000000111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3, 0 }, "0000000000001111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0 }, "0000000000011111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0 }, "0000000000111111"),
                new EncodingCase(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0 },
                        "0000000001111111"),
                new EncodingCase(new byte[] { ALL_BITS, 0 }, "0000000011111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 }, "0000000111111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 | BIT_1 }, "0000001111111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 }, "0000011111111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "0000111111111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 },
                        "0001111111111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 },
                        "0011111111111111"),
                new EncodingCase(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 },
                        "0111111111111111"),
                new EncodingCase(new byte[] { ALL_BITS, ALL_BITS }, "1111111111111111")
        };

        assertEncodesObjectCases(oneByteCases);
        assertEncodesObjectCases(twoByteCases);
    }

    private void assertEncodesObjectCases(final EncodingCase[] cases) throws Exception {
        for (final EncodingCase testCase : cases) {
            assertEncodesObjectAs(testCase.rawBytes, testCase.expectedEncoding);
        }
    }

    private void assertEncodesObjectAs(final byte[] rawBytes, final String expectedEncoding) throws Exception {
        final String encoded = new String((char[]) instance.encode((Object) rawBytes));
        assertEquals(expectedEncoding, encoded);
    }

    private static final class EncodingCase {

        private final byte[] rawBytes;
        private final String expectedEncoding;

        private EncodingCase(final byte[] rawBytes, final String expectedEncoding) {
            this.rawBytes = rawBytes;
            this.expectedEncoding = expectedEncoding;
        }
    }
}
