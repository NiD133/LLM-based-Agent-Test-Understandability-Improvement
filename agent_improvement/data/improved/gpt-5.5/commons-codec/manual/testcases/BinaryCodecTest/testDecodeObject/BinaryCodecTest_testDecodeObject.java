package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testDecodeObject {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

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
    void testDecodeObject() throws Exception {
        assertDecodeObject(new byte[] { 0 }, "00000000");
        assertDecodeObject(new byte[] { BIT_0 }, "00000001");
        assertDecodeObject(new byte[] { BIT_0 | BIT_1 }, "00000011");
        assertDecodeObject(new byte[] { BIT_0 | BIT_1 | BIT_2 }, "00000111");
        assertDecodeObject(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "00001111");
        assertDecodeObject(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "00011111");
        assertDecodeObject(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 }, "00111111");
        assertDecodeObject(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 }, "01111111");
        assertDecodeObject(new byte[] { ALL_BITS }, "11111111");

        assertDecodeObject(new byte[] { ALL_BITS, 0 }, "0000000011111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 }, "0000000111111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 | BIT_1 }, "0000001111111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 }, "0000011111111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "0000111111111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "0001111111111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 }, "0011111111111111");
        assertDecodeObject(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 },
                "0111111111111111");
        assertDecodeObject(new byte[] { ALL_BITS, ALL_BITS }, "1111111111111111");
        assertDecodeObject(new byte[0], null);
    }

    private void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
        byte[] decoded = (byte[]) instance.decode(encodeMe);
        assertEquals(new String(bits), new String(decoded));

        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertEquals(new String(bits), new String(decoded));

        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertEquals(new String(bits), new String(decoded));
    }
}
