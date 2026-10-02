package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToAsciiString {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    private static final byte NO_BITS = 0;
    private static final byte LOWEST_ONE_BIT = BIT_0;
    private static final byte LOWEST_TWO_BITS = BIT_0 | BIT_1;
    private static final byte LOWEST_THREE_BITS = BIT_0 | BIT_1 | BIT_2;
    private static final byte LOWEST_FOUR_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3;
    private static final byte LOWEST_FIVE_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
    private static final byte LOWEST_SIX_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
    private static final byte LOWEST_SEVEN_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    BinaryCodec instance;

    void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
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

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    void testToAsciiString() {
        assertToAsciiString("00000000", new byte[] { NO_BITS });
        assertToAsciiString("00000001", new byte[] { LOWEST_ONE_BIT });
        assertToAsciiString("00000011", new byte[] { LOWEST_TWO_BITS });
        assertToAsciiString("00000111", new byte[] { LOWEST_THREE_BITS });
        assertToAsciiString("00001111", new byte[] { LOWEST_FOUR_BITS });
        assertToAsciiString("00011111", new byte[] { LOWEST_FIVE_BITS });
        assertToAsciiString("00111111", new byte[] { LOWEST_SIX_BITS });
        assertToAsciiString("01111111", new byte[] { LOWEST_SEVEN_BITS });
        assertToAsciiString("11111111", new byte[] { ALL_BITS });

        assertToAsciiString("0000000000000000", new byte[] { NO_BITS, NO_BITS });
        assertToAsciiString("0000000000000001", new byte[] { LOWEST_ONE_BIT, NO_BITS });
        assertToAsciiString("0000000000000011", new byte[] { LOWEST_TWO_BITS, NO_BITS });
        assertToAsciiString("0000000000000111", new byte[] { LOWEST_THREE_BITS, NO_BITS });
        assertToAsciiString("0000000000001111", new byte[] { LOWEST_FOUR_BITS, NO_BITS });
        assertToAsciiString("0000000000011111", new byte[] { LOWEST_FIVE_BITS, NO_BITS });
        assertToAsciiString("0000000000111111", new byte[] { LOWEST_SIX_BITS, NO_BITS });
        assertToAsciiString("0000000001111111", new byte[] { LOWEST_SEVEN_BITS, NO_BITS });
        assertToAsciiString("0000000011111111", new byte[] { ALL_BITS, NO_BITS });

        assertToAsciiString("0000000111111111", new byte[] { ALL_BITS, LOWEST_ONE_BIT });
        assertToAsciiString("0000001111111111", new byte[] { ALL_BITS, LOWEST_TWO_BITS });
        assertToAsciiString("0000011111111111", new byte[] { ALL_BITS, LOWEST_THREE_BITS });
        assertToAsciiString("0000111111111111", new byte[] { ALL_BITS, LOWEST_FOUR_BITS });
        assertToAsciiString("0001111111111111", new byte[] { ALL_BITS, LOWEST_FIVE_BITS });
        assertToAsciiString("0011111111111111", new byte[] { ALL_BITS, LOWEST_SIX_BITS });
        assertToAsciiString("0111111111111111", new byte[] { ALL_BITS, LOWEST_SEVEN_BITS });
        assertToAsciiString("1111111111111111", new byte[] { ALL_BITS, ALL_BITS });
        assertToAsciiString("", null);
    }

    private void assertToAsciiString(final String expected, final byte[] bits) {
        assertEquals(expected, BinaryCodec.toAsciiString(bits));
    }
}
