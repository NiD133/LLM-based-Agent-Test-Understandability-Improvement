package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeObjectNull {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * Mask with bit zero-based index 0 raised.
     */
    private static final int BIT_0 = 0x01;

    /**
     * Mask with bit zero-based index 1 raised.
     */
    private static final int BIT_1 = 0x02;

    /**
     * Mask with bit zero-based index 2 raised.
     */
    private static final int BIT_2 = 0x04;

    /**
     * Mask with bit zero-based index 3 raised.
     */
    private static final int BIT_3 = 0x08;

    /**
     * Mask with bit zero-based index 4 raised.
     */
    private static final int BIT_4 = 0x10;

    /**
     * Mask with bit zero-based index 5 raised.
     */
    private static final int BIT_5 = 0x20;

    /**
     * Mask with bit zero-based index 6 raised.
     */
    private static final int BIT_6 = 0x40;

    /**
     * Mask with bit zero-based index 7 raised.
     */
    private static final int BIT_7 = 0x80;

    /**
     * An instance of the binary codec.
     */
    BinaryCodec instance;

    /**
     * Utility used to assert the encoded and decoded values.
     *
     * @param bits
     *            the pre-encoded data
     * @param encodeMe
     *            data to encode and compare
     */
    void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
        byte[] decoded;
        decoded = (byte[]) instance.decode(encodeMe);
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

    /*
     * Tests for Object encode(Object)
     */
    @Test
    void testEncodeObjectNull() throws Exception {
        final Object obj = new byte[0];
        assertEquals(0, ((char[]) instance.encode(obj)).length);
    }
}
