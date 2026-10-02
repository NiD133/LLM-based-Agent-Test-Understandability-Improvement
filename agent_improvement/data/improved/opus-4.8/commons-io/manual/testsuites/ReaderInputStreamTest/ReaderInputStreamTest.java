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

/**
 * Tests {@link ReaderInputStream}, which adapts a character {@link java.io.Reader} into a byte {@link InputStream}
 * by encoding the characters with a given charset.
 */
class ReaderInputStreamTest {

    private static final String UTF_16 = StandardCharsets.UTF_16.name();
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** A short French phrase containing accented (multi-byte) characters, useful for exercising charset encoding. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /** The same phrase repeated, large enough to force multiple internal buffer fills. */
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    /**
     * An unpaired high surrogate. On its own it is not a valid character and is therefore handy for exercising
     * malformed-input / encoder behavior.
     */
    private static final String LONE_HIGH_SURROGATE = "\uD800";

    /** Provides (charset name, sample text) pairs for {@link #testCharsetEncoderFlush(String, String)}. */
    static Stream<Arguments> charsetData() {
        // @formatter:off
        return Stream.of(
                Arguments.of("Cp930", "Α"),
                Arguments.of("ISO_8859_1", "A"),
                Arguments.of(UTF_8, "Α"));
        // @formatter:on
    }

    private final Random random = new Random();

    /** Creates a stream over {@link #TEST_STRING} encoded as ISO-8859-1 (one byte per character). */
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
            assertEquals(0, inputStream.available(), "A closed stream should report no available bytes");
        }
    }

    @Test
    void testAvailableAfterOpen() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            // Nothing has been read yet, so nothing is buffered as available.
            assertEquals(0, inputStream.available());
            // Reading one byte fills the internal buffer; the rest of the string becomes available.
            inputStream.read();
            assertEquals(TEST_STRING.length() - 1, inputStream.available());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testBufferSmallest() throws IOException {
        final Charset charset = StandardCharsets.UTF_8;
        final int smallestBufferSize = (int) ReaderInputStream.minBufferSize(charset.newEncoder());
        // The smallest legal buffer size must still allow a read to complete (verified via both constructor and builder).
        // @formatter:off
        try (InputStream in = new ReaderInputStream(
                new StringReader(LONE_HIGH_SURROGATE),
                charset,
                smallestBufferSize)) {
            in.read();
        }
        try (InputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(LONE_HIGH_SURROGATE))
                .setCharset(charset)
                .setBufferSize(smallestBufferSize)
                .get()) {
            in.read();
        }
        // @formatter:on
    }

    @Test
    void testBufferTooSmall() {
        // Buffer sizes below the encoder's minimum requirement are rejected.
        for (final int illegalBufferSize : new int[] {-1, 0, 1}) {
            assertThrows(IllegalArgumentException.class,
                    () -> new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), StandardCharsets.UTF_8, illegalBufferSize));
        }
    }

    @ParameterizedTest
    @MethodSource("charsetData")
    void testCharsetEncoderFlush(final String charsetName, final String data) throws IOException {
        final Charset charset = Charset.forName(charsetName);
        final byte[] expected = data.getBytes(charset);
        // The encoded bytes must match String#getBytes for both the constructor and the builder.
        try (InputStream in = new ReaderInputStream(new StringReader(data), charset)) {
            assertEquals(Arrays.toString(expected), Arrays.toString(IOUtils.toByteArray(in)));
        }
        try (InputStream in = ReaderInputStream.builder().setReader(new StringReader(data)).setCharset(charset).get()) {
            assertEquals(Arrays.toString(expected), Arrays.toString(IOUtils.toByteArray(in)));
        }
    }

    /*
     * Tests https://issues.apache.org/jira/browse/IO-277
     */
    @Test
    void testCharsetMismatchInfiniteLoop() throws IOException {
        // These chars are UTF-8 bytes (0xE0 0xB2 0xA0) read as if they were characters.
        final char[] inputChars = { (char) 0xE0, (char) 0xB2, (char) 0xA0 };
        // Charset charset = Charset.forName("UTF-8"); // works
        final Charset charset = StandardCharsets.US_ASCII; // would infinite loop before IO-277 was fixed
        try (ReaderInputStream stream = new ReaderInputStream(new CharArrayReader(inputChars), charset)) {
            // The fix guarantees this completes rather than looping forever.
            IOUtils.toCharArray(stream, charset);
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testCodingError() throws IOException {
        // A lone high surrogate is an underflow (incomplete input), not an error, so reading must not throw.
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE), encoder)) {
            assertDoesNotThrow(() -> in.read());
        }
        encoder = StandardCharsets.UTF_8.newEncoder();
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharsetEncoder(encoder).get()) {
            assertDoesNotThrow(() -> in.read());
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
        // REPORT makes the encoder surface malformed input as an exception rather than replacing it.
        final CharsetEncoder encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPORT);
        // The lone surrogate followed by "aa" is malformed and must trigger a CharacterCodingException on read.
        try (InputStream in = new ReaderInputStream(new StringReader(LONE_HIGH_SURROGATE + "aa"), encoder, (int) ReaderInputStream.minBufferSize(encoder))) {
            assertThrows(CharacterCodingException.class, in::read);
        }
        try (InputStream in = ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE + "aa")).setCharsetEncoder(encoder)
                .setBufferSize((int) ReaderInputStream.minBufferSize(charset.newEncoder())).get()) {
            assertThrows(CharacterCodingException.class, in::read);
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharset() throws IOException {
        // A null Charset argument falls back to the platform default charset.
        final Charset charset = Charset.defaultCharset();
        final Charset nullCharset = null;
        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullCharset, (int) ReaderInputStream.minBufferSize(charset.newEncoder()))) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetEncoder() throws IOException {
        // A null CharsetEncoder argument falls back to the platform default charset.
        final Charset charset = Charset.defaultCharset();
        final CharsetEncoder nullEncoder = null;
        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullEncoder, (int) ReaderInputStream.minBufferSize(charset.newEncoder()))) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testConstructNullCharsetNameEncoder() throws IOException {
        // A null charset name falls back to the platform default charset, via both constructor and builder.
        final Charset charset = Charset.defaultCharset();
        final String nullCharsetName = null;
        try (ReaderInputStream in = new ReaderInputStream(new StringReader("ABC"), nullCharsetName, (int) ReaderInputStream.minBufferSize(charset.newEncoder()))) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader("ABC")).setCharset(nullCharsetName)
                .setBufferSize((int) ReaderInputStream.minBufferSize(charset.newEncoder())).get()) {
            IOUtils.toByteArray(in);
            assertEquals(Charset.defaultCharset(), in.getCharsetEncoder().charset());
        }
    }

    @Test
    void testIo803SAXException() throws IOException {
        // Parsing an empty document must fail with SAXException (not hang or throw something else).
        final StringReader reader = new StringReader("");
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setCharset(StandardCharsets.UTF_8).setReader(reader).get()) {
            final InputSource inputSource = new InputSource(inputStream);
            assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource));
        }
    }

    @Test
    void testIo803StringReaderSanityCheck() {
        // Sanity check: parsing the same empty input directly from a Reader also throws SAXException.
        final StringReader reader = new StringReader("");
        final InputSource inputSource = new InputSource(reader);
        assertThrows(SAXException.class, () -> DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource));
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertRoundTripWithBufferedReads(LARGE_TEST_STRING, UTF_8);
    }

    @Test
    void testLargeUTF8WithSingleByteRead() throws IOException {
        assertRoundTripWithSingleByteReads(LARGE_TEST_STRING, UTF_8);
    }

    @Test
    void testReadAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();
            assertThrows(IOException.class, inputStream::read, "Reading from a closed stream should throw");
        }
    }

    @Test
    void testReadEofTwice() throws IOException {
        try (ReaderInputStream reader = ReaderInputStream.builder().setCharset(StandardCharsets.UTF_8).setReader(new StringReader("123")).get()) {
            assertEquals('1', reader.read());
            assertEquals('2', reader.read());
            assertEquals('3', reader.read());
            // EOF is reported as -1 and stays -1 on subsequent reads.
            assertEquals(-1, reader.read());
            assertEquals(-1, reader.read());
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZero() throws Exception {
        final String inputString = "test";
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(inputString))) {
            assertReadZeroBehavior(inputString, inputStream);
        }
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setReader(new StringReader(inputString)).get()) {
            assertReadZeroBehavior(inputString, inputStream);
        }
    }

    /**
     * Verifies that requesting a length of 0 always returns 0, regardless of stream position.
     */
    private void assertReadZeroBehavior(final String inputString, final ReaderInputStream inputStream) throws IOException {
        final byte[] buffer = new byte[30];
        assertEquals(0, inputStream.read(buffer, 0, 0), "Requesting length 0 should read nothing");
        assertEquals(inputString.length(), inputStream.read(buffer, 0, inputString.length() + 1));
        // Should always return 0 for length == 0, even after data has been read.
        assertEquals(0, inputStream.read(buffer, 0, 0));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZeroEmptyString() throws Exception {
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(""))) {
            final byte[] buffer = new byte[30];
            // For an empty stream: length 0 reads return 0, and length > 0 reads return -1 (EOF).
            assertEquals(0, inputStream.read(buffer, 0, 0));
            assertEquals(-1, inputStream.read(buffer, 0, 1));
            assertEquals(0, inputStream.read(buffer, 0, 0));
            assertEquals(-1, inputStream.read(buffer, 0, 1));
        }
    }

    @Test
    void testResetCharset() {
        // Passing a null Charset to the builder resets it to a non-null default.
        assertNotNull(ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharset((Charset) null).getCharset());
    }

    @Test
    void testResetCharsetEncoder() {
        // Passing a null CharsetEncoder to the builder resets it to a non-null default.
        assertNotNull(ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharsetEncoder(null).getCharsetEncoder());
    }

    @Test
    void testResetCharsetName() {
        // Passing a null charset name to the builder resets it to a non-null default.
        assertNotNull(ReaderInputStream.builder().setReader(new StringReader(LONE_HIGH_SURROGATE)).setCharset((String) null).getCharset());
    }

    @Test
    void testUTF16WithSingleByteRead() throws IOException {
        assertRoundTripWithSingleByteReads(TEST_STRING, UTF_16);
    }

    @Test
    void testUTF8WithBufferedRead() throws IOException {
        assertRoundTripWithBufferedReads(TEST_STRING, UTF_8);
    }

    @Test
    void testUTF8WithSingleByteRead() throws IOException {
        assertRoundTripWithSingleByteReads(TEST_STRING, UTF_8);
    }

    /**
     * Reads the whole stream using bulk {@code read(buffer, offset, length)} calls with randomized offsets and lengths,
     * asserting that the bytes produced match {@code expected} in order.
     *
     * @param expected the bytes the stream is expected to produce.
     * @param in       the stream under test.
     */
    private void assertBytesMatchUsingBufferedReads(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[128];
        int expectedIndex = 0;
        while (true) {
            // Use a random destination offset and length to exercise partial/offset reads.
            int bufferOffset = random.nextInt(64);
            final int bufferLength = random.nextInt(64);
            int read = in.read(buffer, bufferOffset, bufferLength);
            if (read == -1) {
                assertEquals(expectedIndex, expected.length, "Should consume exactly all expected bytes before EOF");
                break;
            }
            assertTrue(read <= bufferLength, "Must not read more than requested");
            while (read > 0) {
                assertTrue(expectedIndex < expected.length);
                assertEquals(expected[expectedIndex], buffer[bufferOffset]);
                expectedIndex++;
                bufferOffset++;
                read--;
            }
        }
    }

    /**
     * Asserts that encoding {@code testString} with {@code charsetName} and reading it back in bulk yields the same
     * bytes as {@link String#getBytes(String)}, for both the constructor and the builder.
     */
    private void assertRoundTripWithBufferedReads(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertBytesMatchUsingBufferedReads(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(testString)).setCharset(charsetName).get()) {
            assertBytesMatchUsingBufferedReads(expected, in);
        }
    }

    /**
     * Asserts that encoding {@code testString} with {@code charsetName} and reading it back one byte at a time yields
     * the same bytes as {@link String#getBytes(String)}, ending with EOF.
     */
    private void assertRoundTripWithSingleByteReads(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (final byte expectedByte : expected) {
                final int read = in.read();
                // read() returns an unsigned byte (0..255) or -1; here we expect a valid byte.
                assertTrue(read >= 0);
                assertTrue(read <= 255);
                assertEquals(expectedByte, (byte) read);
            }
            assertEquals(-1, in.read(), "Stream should be at EOF after all bytes are read");
        }
    }
}
