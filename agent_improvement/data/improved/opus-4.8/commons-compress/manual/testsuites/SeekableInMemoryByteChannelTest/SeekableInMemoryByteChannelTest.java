/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests {@link SeekableInMemoryByteChannel}.
 * <p>
 * The tests are grouped by behaviour:
 * </p>
 * <ul>
 *   <li>reading existing content,</li>
 *   <li>writing content,</li>
 *   <li>moving the position,</li>
 *   <li>truncating the channel,</li>
 *   <li>and the contract obligations of a closed channel.</li>
 * </ul>
 */
class SeekableInMemoryByteChannelTest {

    /** Sample payload shared by most tests; nine bytes of ASCII text. */
    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /**
     * Capacity allocated by the no-argument constructor (see {@code IOUtils.DEFAULT_BUFFER_SIZE}); an empty channel grows to this size on the first write.
     */
    private static final int DEFAULT_BUFFER_SIZE = 8_192;

    // -----------------------------------------------------------------------
    // Reading
    // -----------------------------------------------------------------------

    @Test
    void testReadContentsProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);

            final int readCount = channel.read(readBuffer);

            assertEquals(testData.length, readCount, "the whole payload should be read");
            assertArrayEquals(testData, readBuffer.array());
            assertEquals(testData.length, channel.position(), "the position should advance past the data");
        }
    }

    @Test
    void testReadContentsWhenBiggerBufferSupplied() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // The buffer is one byte larger than the data, so only the data is read.
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length + 1);

            final int readCount = channel.read(readBuffer);

            assertEquals(testData.length, readCount, "only as many bytes as exist should be read");
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
            assertEquals(testData.length, channel.position());
        }
    }

    @Test
    void testReadDataFromSetPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(4);

            // "Some data" -> reading 4 bytes starting at index 5 yields "data".
            channel.position(5L);
            final int readCount = channel.read(readBuffer);

            assertEquals(4L, readCount);
            assertEquals("data", new String(readBuffer.array(), StandardCharsets.UTF_8));
            assertEquals(testData.length, channel.position());
        }
    }

    /*
     * <q>Setting the position to a value that is greater than the current size is legal but does not change the size of the entity. A later attempt to read
     * bytes at such a position will immediately return an end-of-file indication</q>
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    void testReadingFromAPositionAfterEndReturnsEOF(final int size) throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(size)) {
            final int position = 2;
            channel.position(position);
            assertEquals(position, channel.position());

            final ByteBuffer readBuffer = ByteBuffer.allocate(5);
            // Reading past the end yields EOF (-1); otherwise the remaining bytes are returned.
            final int expectedReadCount = position >= size ? -1 : size - position;
            assertEquals(expectedReadCount, channel.read(readBuffer));
        }
    }

    @Test
    void testSignalEOFWhenPositionAtTheEnd() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);

            // Place the position one byte beyond the end of the data.
            channel.position(testData.length + 1);
            final int readCount = channel.read(readBuffer);

            assertEquals(0L, readBuffer.position(), "no bytes should be transferred into the buffer");
            assertEquals(-1, readCount, "reading past the end signals EOF");
            assertEquals(-1, channel.read(readBuffer), "a repeated read still signals EOF");
        }
    }

    // -----------------------------------------------------------------------
    // Writing
    // -----------------------------------------------------------------------

    @Test
    void testWriteDataProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer inData = ByteBuffer.wrap(testData);

            final int writeCount = channel.write(inData);

            assertEquals(testData.length, writeCount);
            assertEquals(testData.length, channel.position());
            assertArrayEquals(testData, Arrays.copyOf(channel.array(), (int) channel.position()));
        }
    }

    @Test
    void testWriteDataProperlyAfterPositionSet() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer inData = ByteBuffer.wrap(testData);
            // Writing the payload at position 5 leaves the first 5 bytes intact, then appends the full payload.
            final ByteBuffer expectedData = ByteBuffer.allocate(testData.length + 5).put(testData, 0, 5).put(testData);

            channel.position(5L);
            final int writeCount = channel.write(inData);

            assertEquals(testData.length, writeCount);
            assertArrayEquals(expectedData.array(), Arrays.copyOf(channel.array(), (int) channel.size()));
            assertEquals(testData.length + 5, channel.position());
        }
    }

    /*
     * <q>Setting the position to a value that is greater than the current size is legal but does not change the size of the entity. A later attempt to write
     * bytes at such a position will cause the entity to grow to accommodate the new bytes; the values of any bytes between the previous end-of-file and the
     * newly-written bytes are unspecified.</q>
     */
    @Test
    void testWritingToAPositionAfterEndGrowsChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.position(2);
            assertEquals(2, channel.position());

            final ByteBuffer inData = ByteBuffer.wrap(testData);
            assertEquals(testData.length, channel.write(inData));
            // The empty channel's backing buffer grows to its default capacity.
            assertEquals(DEFAULT_BUFFER_SIZE, channel.size());

            // The bytes written at position 2 can be read back unchanged.
            channel.position(2);
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            channel.read(readBuffer);
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }

    @Test
    void testThrowWhenSettingIncorrectPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer buffer = ByteBuffer.allocate(1);

            // Write a single byte so the channel has a size of 1.
            channel.write(buffer);
            assertEquals(1, channel.position());

            // A position one past the size is legal; reading there returns EOF.
            channel.position(channel.size() + 1);
            assertEquals(channel.size() + 1, channel.position());
            assertEquals(-1, channel.read(buffer));

            // A position beyond Integer.MAX_VALUE is also legal; reading returns EOF but writing fails.
            channel.position(Integer.MAX_VALUE + 1L);
            assertEquals(Integer.MAX_VALUE + 1L, channel.position());
            assertEquals(-1, channel.read(buffer));
            assertThrows(IOException.class, () -> channel.write(buffer));

            // Only negative positions are rejected.
            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Long.MIN_VALUE));
        }
    }

    // -----------------------------------------------------------------------
    // Positioning
    // -----------------------------------------------------------------------

    @Test
    void testSetProperPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            // position(long) is fluent and returns the channel, so position() reads back the value just set.
            final long posAtFour = channel.position(4L).position();
            final long posAtTheEnd = channel.position(testData.length).position();
            final long posPastTheEnd = channel.position(testData.length + 1L).position();

            assertEquals(4L, posAtFour);
            assertEquals(channel.size(), posAtTheEnd);
            assertEquals(testData.length + 1L, posPastTheEnd);
        }
    }

    /*
     * <q>IOException - If the new position is negative</q>
     */
    @Test
    void testThrowsIOExceptionWhenPositionIsSetToANegativeValue() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
        }
    }

    // -----------------------------------------------------------------------
    // Truncating
    // -----------------------------------------------------------------------

    @Test
    void testTruncateContentsProperly() throws ClosedChannelException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.truncate(4);

            final byte[] remaining = Arrays.copyOf(channel.array(), (int) channel.size());
            assertEquals("Some", new String(remaining, StandardCharsets.UTF_8));
        }
    }

    @Test
    void testSetProperPositionOnTruncate() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(testData.length);
            channel.truncate(4L);

            // Truncating below the current position pulls the position down to the new size.
            assertEquals(4L, channel.position());
            assertEquals(4L, channel.size());
        }
    }

    /*
     * <q> In either case, if the current position is greater than the given size then it is set to that size.</q>
     */
    @Test
    void testTruncateDoesntChangeSmallPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(1);
            channel.truncate(testData.length - 1);

            assertEquals(testData.length - 1, channel.size());
            // The position (1) is below the new size, so it is left unchanged.
            assertEquals(1, channel.position());
        }
    }

    /*
     * <q> In either case, if the current position is greater than the given size then it is set to that size.</q>
     */
    @Test
    void testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(2 * testData.length);
            // The requested size exceeds the current size, so the size stays put...
            channel.truncate(testData.length + 1);

            assertEquals(testData.length, channel.size());
            // ...but the position is clamped down to the requested (larger-than-size) value.
            assertEquals(testData.length + 1, channel.position());
        }
    }

    /*
     * <q> In either case, if the current position is greater than the given size then it is set to that size.</q>
     */
    @Test
    void testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(2 * testData.length);
            // Requested size equals the current size, so the size is unchanged...
            channel.truncate(testData.length);

            assertEquals(testData.length, channel.size());
            // ...while the out-of-range position is clamped down to that size.
            assertEquals(testData.length, channel.position());
        }
    }

    /*
     * <q> In either case, if the current position is greater than the given size then it is set to that size.</q>
     */
    @Test
    void testTruncateMovesPositionWhenShrinkingBeyondPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(4);
            channel.truncate(3);

            assertEquals(3, channel.size());
            // Shrinking below the position pulls the position down with the size.
            assertEquals(3, channel.position());
        }
    }

    /*
     * <q>If the given size is greater than or equal to the current size then the entity is not modified.</q>
     */
    @Test
    void testTruncateToBiggerSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            assertEquals(testData.length, channel.size());

            channel.truncate(testData.length + 1);

            assertEquals(testData.length, channel.size(), "growing via truncate is a no-op");
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer));
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }

    /*
     * <q>If the given size is greater than or equal to the current size then the entity is not modified.</q>
     */
    @Test
    void testTruncateToCurrentSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            assertEquals(testData.length, channel.size());

            channel.truncate(testData.length);

            assertEquals(testData.length, channel.size(), "truncating to the current size is a no-op");
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer));
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }

    @Test
    void testThrowWhenTruncatingToIncorrectSize() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer buffer = ByteBuffer.allocate(1);

            // Truncating to a size at or above the current size never shrinks the data.
            channel.truncate(channel.size() + 1);
            assertEquals(1, channel.read(buffer));
            channel.truncate(Integer.MAX_VALUE + 1L);
            assertEquals(0, channel.read(buffer));

            // Only negative sizes are rejected.
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Long.MIN_VALUE));
        }
    }

    /*
     * <q>IllegalArgumentException - If the new position is negative</q>
     */
    @Test
    void testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
        }
    }

    // -----------------------------------------------------------------------
    // Closing and closed-channel contract
    //
    // Contract Tests added in response to https://issues.apache.org/jira/browse/COMPRESS-499
    // https://docs.oracle.com/javase/8/docs/api/java/io/Closeable.html#close()
    // -----------------------------------------------------------------------

    /*
     * <q>If the stream is already closed then invoking this method has no effect.</q>
     */
    @Test
    void testCloseIsIdempotent() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertFalse(channel.isOpen());
            // Closing a second time has no effect and does not reopen the channel.
            channel.close();
            assertFalse(channel.isOpen());
        }
    }

    @Test
    void testThrowExceptionOnReadingClosedChannel() {
        final SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.close();
        assertThrows(ClosedChannelException.class, () -> channel.read(ByteBuffer.allocate(1)));
    }

    @Test
    void testThrowExceptionOnWritingToClosedChannel() {
        final SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.close();
        assertThrows(ClosedChannelException.class, () -> channel.write(ByteBuffer.allocate(1)));
    }

    /*
     * <q>ClosedChannelException - If this channel is closed</q>
     * https://docs.oracle.com/javase/8/docs/api/java/nio/channels/SeekableByteChannel.html#position(long)
     */
    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertThrows(ClosedChannelException.class, channel::position);
        }
    }

    /*
     * <q>ClosedChannelException - If this channel is closed</q>
     */
    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsSetOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertThrows(ClosedChannelException.class, () -> channel.position(0));
        }
    }

    /*
     * <q>ClosedChannelException - If this channel is closed</q>
     */
    @Test
    void testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertThrows(ClosedChannelException.class, channel::size);
        }
    }

    /*
     * <q>ClosedChannelException - If this channel is closed</q>
     */
    @Test
    void testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertThrows(ClosedChannelException.class, () -> channel.truncate(0));
        }
    }
}
