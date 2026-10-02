package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.ArrayFill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Base58Test_testTestVectors {

    private static final int BOUND = 10_000;

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static void assertArrayEqualsAt(final byte[] data, final byte[] dec, final int i) {
        final AtomicInteger counter = new AtomicInteger(i);
        assertArrayEquals(data, dec, () -> String.format("Failed for length %,d: %s", counter.get(), Arrays.toString(data)));
    }

    private final Random random = new Random();

    @Test
    void testTestVectors() {
        final String content = "Hello World!";
        final String content1 = "The quick brown fox jumps over the lazy dog.";
        // Use long to preserve the full 48-bit value
        final long content2 = 0x0000287fb4cdL;
        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(content));
        final byte[] encodedBytes1 = new Base58().encode(StringUtils.getBytesUtf8(content1));
        final byte[] content2Bytes = ByteBuffer.allocate(8).putLong(content2).array();
        final byte[] content2Trimmed = new byte[6];
        System.arraycopy(content2Bytes, 2, content2Trimmed, 0, 6);
        final byte[] encodedBytes2 = new Base58().encode(content2Trimmed);
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        final String encodedContent1 = StringUtils.newStringUtf8(encodedBytes1);
        final String encodedContent2 = StringUtils.newStringUtf8(encodedBytes2);
        assertEquals("2NEpo7TZRRrLZSi2U", encodedContent, "encoding hello world");
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", encodedContent1);
        assertEquals("11233QC4", encodedContent2, "encoding 0x0000287fb4cd");
        final byte[] decodedBytes = new Base58().decode(encodedBytes);
        final byte[] decodedBytes1 = new Base58().decode(encodedBytes1);
        final byte[] decodedBytes2 = new Base58().decode(encodedBytes2);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        final String decodedContent1 = StringUtils.newStringUtf8(decodedBytes1);
        assertEquals(content, decodedContent, "decoding hello world");
        assertEquals(content1, decodedContent1);
        assertArrayEquals(content2Trimmed, decodedBytes2, "decoding 0x0000287fb4cd");
    }
}
