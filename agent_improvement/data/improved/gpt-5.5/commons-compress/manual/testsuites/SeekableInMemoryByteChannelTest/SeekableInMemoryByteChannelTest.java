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
 */
class SeekableInMemoryByteChannelTest {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testCloseIsIdempotent() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();
            assertFalse(channel.isOpen());

            channel.close();
            assertFalse(channel.isOpen());
        }
    }

    @Test
    void testReadContentsProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);

            final int readCount = channel.read(readBuffer);

            assertEquals(testData.length, readCount);
            assertArrayEquals(testData, readBuffer.array());
            assertEquals(testData.length, channel.position());
        }
    }

    @Test
    void testReadContentsWhenBiggerBufferSupplied() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length + 1);

            final int readCount = channel.read(readBuffer);

            assertEquals(testData.length, readCount);
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
            assertEquals(testData.length, channel.position());
        }
    }

    @Test
    void testReadDataFromSetPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(4);

            channel.position(5L);
            final int readCount = channel.read(readBuffer);

            assertEquals(4L, readCount);
            assertEquals("data", new String(readBuffer.array(), StandardCharsets.UTF_8));
            assertEquals(testData.length, channel.position());
        }
    }

    /*
     * Setting the position past the current size is legal. Reading there reports EOF without changing the size.
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    void testReadingFromAPositionAfterEndReturnsEOF(final int size) throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(size)) {
            final int position = 2;
            channel.position(position);
            assertEquals(position, channel.position());

            final int readSize = 5;
            final ByteBuffer readBuffer = ByteBuffer.allocate(readSize);
            assertEquals(position >= size ? -1 : size - position, channel.read(readBuffer));
        }
    }

    @Test
    void testSetProperPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final long posAtFour = channel.position(4L).position();
            final long posAtTheEnd = channel.position(testData.length).position();
            final long posPastTheEnd = channel.position(testData.length + 1L).position();

            assertEquals(4L, posAtFour);
            assertEquals(channel.size(), posAtTheEnd);
            assertEquals(testData.length + 1L, posPastTheEnd);
        }
    }

    @Test
    void testSetProperPositionOnTruncate() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(testData.length);
            channel.truncate(4L);

            assertEquals(4L, channel.position());
            assertEquals(4L, channel.size());
        }
    }

    @Test
    void testSignalEOFWhenPositionAtTheEnd() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);

            channel.position(testData.length + 1);
            final int readCount = channel.read(readBuffer);

            assertEquals(0L, readBuffer.position());
            assertEquals(-1, readCount);
            assertEquals(-1, channel.read(readBuffer));
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

    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, channel::position);
        }
    }

    @Test
    void testThrowsClosedChannelExceptionWhenPositionIsSetOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, () -> channel.position(0));
        }
    }

    @Test
    void testThrowsClosedChannelExceptionWhenSizeIsReadOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, channel::size);
        }
    }

    @Test
    void testThrowsClosedChannelExceptionWhenTruncateIsCalledOnClosedChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.close();

            assertThrows(ClosedChannelException.class, () -> channel.truncate(0));
        }
    }

    @Test
    void testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
        }
    }

    @Test
    void testThrowsIOExceptionWhenPositionIsSetToANegativeValue() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
        }
    }

    @Test
    void testThrowWhenSettingIncorrectPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer buffer = ByteBuffer.allocate(1);

            channel.write(buffer);
            assertEquals(1, channel.position());

            channel.position(channel.size() + 1);
            assertEquals(channel.size() + 1, channel.position());
            assertEquals(-1, channel.read(buffer));

            channel.position(Integer.MAX_VALUE + 1L);
            assertEquals(Integer.MAX_VALUE + 1L, channel.position());
            assertEquals(-1, channel.read(buffer));
            assertThrows(IOException.class, () -> channel.write(buffer));

            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Long.MIN_VALUE));
        }
    }

    @Test
    void testThrowWhenTruncatingToIncorrectSize() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer buffer = ByteBuffer.allocate(1);

            channel.truncate(channel.size() + 1);
            assertEquals(1, channel.read(buffer));

            channel.truncate(Integer.MAX_VALUE + 1L);
            assertEquals(0, channel.read(buffer));

            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Long.MIN_VALUE));
        }
    }

    @Test
    void testTruncateContentsProperly() throws ClosedChannelException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.truncate(4);

            final byte[] bytes = Arrays.copyOf(channel.array(), (int) channel.size());
            assertEquals("Some", new String(bytes, StandardCharsets.UTF_8));
        }
    }

    @Test
    void testTruncateDoesntChangeSmallPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(1);
            channel.truncate(testData.length - 1);

            assertEquals(testData.length - 1, channel.size());
            assertEquals(1, channel.position());
        }
    }

    @Test
    void testTruncateMovesPositionWhenNewSizeIsBiggerThanSizeAndPositionIsEvenBigger() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(2 * testData.length);
            channel.truncate(testData.length + 1);

            assertEquals(testData.length, channel.size());
            assertEquals(testData.length + 1, channel.position());
        }
    }

    @Test
    void testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(2 * testData.length);
            channel.truncate(testData.length);

            assertEquals(testData.length, channel.size());
            assertEquals(testData.length, channel.position());
        }
    }

    @Test
    void testTruncateMovesPositionWhenShrinkingBeyondPosition() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            channel.position(4);
            channel.truncate(3);

            assertEquals(3, channel.size());
            assertEquals(3, channel.position());
        }
    }

    @Test
    void testTruncateToBiggerSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            assertEquals(testData.length, channel.size());

            channel.truncate(testData.length + 1);

            assertEquals(testData.length, channel.size());
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer));
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }

    @Test
    void testTruncateToCurrentSizeDoesntChangeAnything() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(testData)) {
            assertEquals(testData.length, channel.size());

            channel.truncate(testData.length);

            assertEquals(testData.length, channel.size());
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            assertEquals(testData.length, channel.read(readBuffer));
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }

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
            final ByteBuffer expectedData = ByteBuffer.allocate(testData.length + 5).put(testData, 0, 5).put(testData);

            channel.position(5L);
            final int writeCount = channel.write(inData);

            assertEquals(testData.length, writeCount);
            assertArrayEquals(expectedData.array(), Arrays.copyOf(channel.array(), (int) channel.size()));
            assertEquals(testData.length + 5, channel.position());
        }
    }

    /*
     * Writing after EOF grows the channel and preserves the data at the requested position.
     */
    @Test
    void testWritingToAPositionAfterEndGrowsChannel() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            channel.position(2);
            assertEquals(2, channel.position());

            final ByteBuffer inData = ByteBuffer.wrap(testData);
            assertEquals(testData.length, channel.write(inData));
            assertEquals(8_192, channel.size());

            channel.position(2);
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            channel.read(readBuffer);
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }
}
