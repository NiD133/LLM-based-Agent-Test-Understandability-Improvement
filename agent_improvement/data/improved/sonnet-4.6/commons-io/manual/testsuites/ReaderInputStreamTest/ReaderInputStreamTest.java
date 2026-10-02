/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class ReaderInputStreamTest {

    private static final String UTF_16 = StandardCharsets.UTF_16.name();
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    // A lone high surrogate that has no paired low surrogate; used to test boundary/underflow behaviour.
    private static final String LONE_HIGH_SURROGATE = "\uD800";

    // A lone surrogate followed by regular ASCII chars; used to trigger CharacterCodingException with REPORT action.
    private static final String SURROGATE_WITH_ASCII_SUFFIX = "\uD800aa";

    // Simple ASCII string used in null-charset constructor tests.
    private static final String ASCII_TEST_INPUT = "ABC";

    // Simple digit string used in EOF-behaviour tests.
    private static final String DIGIT_TEST_INPUT = "123";

    // Simple ASCII word used in read-zero tests.
    private static final String SHORT_TEST_INPUT = "test";

    static Stream<Arguments> charsetData() {
        // @formatter:off
        return Stream.of(
                Arguments.of("Cp930", "Α"),
                Arguments.of("ISO_8859_1", "A"),
                Arguments.of(UTF_8, "Α"));
        // @formatter:on
    }

    private final Random random = new Random();

    private ReaderInputStream createInputStream() throws IOException {
        // @formatter:off
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
        // @formatter:on
    }

    @Test
    void testAvailableAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();
            assertEquals(0, inputStream.available());
        }
    }

    @Test
    void testAvailableAfterOpen() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            // Nothing read yet — available() may block, so it returns 0 before the first read.
            assertEquals(0, inputStream.available());
            // After one byte is read, the rest of the buffered bytes are immediately available.
            inputStream.read();
            assertEquals(TEST_STRING.length() - 1, inputStream.available());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testBufferSmallest() throws IOException {
        final Charset charset = StandardCharsets.UTF_8;
        // Use the minimum allowed buffer size to verify the stream does not hang or error.
        // Tests both the direct constructor and the builder API.
        final int minBufSize = (int) ReaderInputStream.minBufferSize(charset.newEncoder());
        try (InputStream in = new ReaderInputStream(
                new StringReader(LONE_HIGH_SURROGATE),
                charset,
                minBufSize)) {
            in.read();
        }
        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(LONE_HIGH_SURROGATE))
                .setCharset(charset)
                .setBufferSize(minBufSize)
                .get()) {
            in.read();
        }
        // @formatter:on
    }

    @Test
    void testBufferTooSmall() {
        assertThrows(IllegalArgumentException.class, () -> new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), StandardCharsets.UTF_8, -1));
        assertThrows(IllegalArgumentException.class, () -> new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), StandardCharsets.UTF_8, 0));
        assertThrows(IllegalArgumentException.class, () -> new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), StandardCharsets.UTF_8, 1));
    }

    @ParameterizedTest
    @MethodSource("charsetData")
    void testCharsetEncoderFlush(final String charsetName, final String data) throws IOException {
        final Charset charset = Charset.forName(charsetName);
        final byte[] expected = data.getBytes(charset);
        // Verify that encoder flush is triggered correctly for both constructor and builder.
        try (InputStream in = new ReaderInputStream(new StringReader(data), charset)) {
            assertEquals(Arrays.toString(expected), Arrays.toString(IOUtils.toByteArray(in)));
        }
        try (InputStream in = ReaderInputStream.builder().setReader(new StringReader(data)).setCharset(charset).get()) {
            assertEquals(Arrays.toString(expected), Arrays.toString(IOUtils.toByteArray(in)));
        }
    }

    /*
     * Tests https://issues.apache.org/jira/browse/IO-277
     * Chars 0xE0, 0xB2, 0xA0 are valid UTF-8 bytes but are not valid US-ASCII characters.
     * With US-ASCII encoding, the mismatch previously caused an infinite loop — this test
     * guards against that regression.
     */
    @Test
    void testCharsetMismatchInfiniteLoop() throws IOException {
        final char[] inputChars = { (char) 0xE0, (char) 0xB2, (char) 0xA0 };
        // Charset charset = Charset.forName("UTF-8"); // works
        final Charset charset = StandardCharsets.US_ASCII; // infinite loop
        try (ReaderInputStream stream = new ReaderInputStream(new CharArrayReader(inputChars), charset)) {
            IOUtils.toCharArray(stream, charset);
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingError() throws IOException {
        // A lone surrogate (\uD800) is an underflow condition, not a hard coding error,
        // so read() must not throw even with a strict encoder.
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), encoder)) {
            // Does not throws an exception because the input is an underflow and not an error
            assertDoesNotThrow(() -> in.read());
            // assertThrows(IllegalStateException.class, () -> in.read());
        }
        encoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharsetEncoder(encoder).get()) {
            // TODO WIP
            assertDoesNotThrow(() -> in.read());
            // assertThrows(IllegalStateException.class, () -> in.read());
        }
    }

    /**
     * Tests IO-717 to avoid infinite loops.
     *
     * ReaderInputStream does not throw exception with {@link CodingErrorAction#REPORT}.
     */
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingErrorAction() throws IOException {
        final Charset charset = StandardCharsets.UTF_8;
        // REPORT action means the encoder must throw on unencodable input rather than silently replacing it.
        final CharsetEncoder encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPORT);
        final int minBufSize = (int) ReaderInputStream.minBufferSize(encoder);
        try (InputStream in = new ReaderInputStream(new StringReader(SURROGATE_WITH_ASCII_SUFFIX), encoder, minBufSize)) {
            assertThrows(CharacterCodingException.class, in::read);
        }
        try (InputStream in = ReaderInputStream.builder().setReader(new StringReader(SURROGATE_WITH_ASCII_SUFFIX)).setCharsetEncoder(encoder)
                .setBufferSize((int) ReaderInputStream.minBufferSize(charset.newEncoder())).get()) {
            assertThrows(CharacterCodingException.class, in::read);
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharset() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final int minBufSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());
        // Passing null as Charset should fall back to the JVM default charset.
        final Charset nullCharset = null;
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(ASCII_TEST_INPUT), nullCharset, minBufSize)) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetEncoder() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final int minBufSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());
        // Passing null as CharsetEncoder should fall back to the JVM default charset encoder.
        final CharsetEncoder nullEncoder = null;
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(ASCII_TEST_INPUT), nullEncoder, minBufSize)) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetNameEncoder() throws IOException {
        final Charset defaultCharset = Charset.defaultCharset();
        final int minBufSize = (int) ReaderInputStream.minBufferSize(defaultCharset.newEncoder());
        // Passing null as charset name should fall back to the JVM default charset.
        final String nullCharsetName = null;
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(ASCII_TEST_INPUT), nullCharsetName, minBufSize)) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(ASCII_TEST_INPUT)).setCharset(nullCharsetName)
                .setBufferSize(minBufSize).get()) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }

    @Test
    void testIo803SAXException() throws IOException {
        final StringReader reader = new StringReader("");
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setCharset(StandardCharsets.UTF_8).setReader(reader).get()) {
            final InputSource inputSource = new InputSource(inputStream);
            assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource));
        }
    }

    @Test
    void testIo803StringReaderSanityCheck() {
        // Sanity check: using a plain StringReader also causes SAXException on an empty document.
        final StringReader reader = new StringReader("");
        final InputSource inputSource = new InputSource(reader);
        assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource));
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        testWithBufferedRead(LARGE_TEST_STRING, UTF_8);
    }

    @Test
    void testLargeUTF8WithSingleByteRead() throws IOException {
        testWithSingleByteRead(LARGE_TEST_STRING, UTF_8);
    }

    @Test
    void testReadAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();
            assertThrows(IOException.class, inputStream::read);
        }
    }

    @Test
    void testReadEofTwice() throws IOException {
        // Calling read() after EOF must consistently return -1, not throw or reset.
        try (ReaderInputStream reader = ReaderInputStream.builder().setCharset(StandardCharsets.UTF_8).setReader(new StringReader(DIGIT_TEST_INPUT)).get()) {
            assertEquals('1', reader.read());
            assertEquals('2', reader.read());
            assertEquals('3', reader.read());
            assertEquals(-1, reader.read());
            assertEquals(-1, reader.read());
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZero() throws Exception {
        final String testInput = SHORT_TEST_INPUT;
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(testInput))) {
            testReadZero(testInput, inputStream);
        }
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setReader(new StringReader(testInput)).get()) {
            testReadZero(testInput, inputStream);
        }
    }

    /**
     * Verifies that read(byte[], offset, 0) always returns 0 regardless of stream position,
     * and that a subsequent normal read returns the expected number of bytes.
     */
    private void testReadZero(final String testInput, final ReaderInputStream inputStream) throws IOException {
        final byte[] bytes = new byte[30];
        assertEquals(0, inputStream.read(bytes, 0, 0));
        assertEquals(testInput.length(), inputStream.read(bytes, 0, testInput.length() + 1));
        // Should always return 0 for length == 0
        assertEquals(0, inputStream.read(bytes, 0, 0));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZeroEmptyString() throws Exception {
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(""))) {
            final byte[] bytes = new byte[30];
            // Should always return 0 for length == 0
            assertEquals(0, inputStream.read(bytes, 0, 0));
            assertEquals(-1, inputStream.read(bytes, 0, 1));
            assertEquals(0, inputStream.read(bytes, 0, 0));
            assertEquals(-1, inputStream.read(bytes, 0, 1));
        }
    }

    @Test
    void testResetCharset() {
        // Setting a null Charset via the builder must still produce a non-null fallback charset.
        assertNotNull(ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharset((Charset) null).getCharset());
    }

    @Test
    void testResetCharsetEncoder() {
        // Setting a null CharsetEncoder via the builder must still produce a non-null fallback encoder.
        assertNotNull(ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharsetEncoder(null).getCharsetEncoder());
    }

    @Test
    void testResetCharsetName() {
        // Setting a null charset name via the builder must still produce a non-null fallback charset.
        assertNotNull(ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharset((String) null).getCharset());
    }

    @Test
    void testUTF16WithSingleByteRead() throws IOException {
        testWithSingleByteRead(TEST_STRING, UTF_16);
    }

    @Test
    void testUTF8WithBufferedRead() throws IOException {
        testWithBufferedRead(TEST_STRING, UTF_8);
    }

    @Test
    void testUTF8WithSingleByteRead() throws IOException {
        testWithSingleByteRead(TEST_STRING, UTF_8);
    }

    /**
     * Reads from {@code in} using randomly sized chunks and verifies each byte matches {@code expected}.
     * The random offsets and lengths exercise boundary conditions in the stream's internal buffer management.
     */
    private void testWithBufferedRead(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[128];
        int offset = 0;
        while (true) {
            int bufferOffset = random.nextInt(64);
            final int bufferLength = random.nextInt(64);
            int read = in.read(buffer, bufferOffset, bufferLength);
            if (read == -1) {
                assertEquals(offset, expected.length);
                break;
            }
            assertTrue(read <= bufferLength);
            while (read > 0) {
                assertTrue(offset < expected.length);
                assertEquals(expected[offset], buffer[bufferOffset]);
                offset++;
                bufferOffset++;
                read--;
            }
        }
    }

    private void testWithBufferedRead(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            testWithBufferedRead(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(testString)).setCharset(charsetName).get()) {
            testWithBufferedRead(expected, in);
        }
    }

    private void testWithSingleByteRead(final String testString, final String charsetName) throws IOException {
        final byte[] bytes = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (final byte b : bytes) {
                final int read = in.read();
                assertTrue(read >= 0);
                assertTrue(read <= 255);
                assertEquals(b, (byte) read);
            }
            assertEquals(-1, in.read());
        }
    }
}
