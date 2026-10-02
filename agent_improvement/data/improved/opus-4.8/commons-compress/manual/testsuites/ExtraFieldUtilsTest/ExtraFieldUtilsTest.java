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

package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for org.apache.commons.compress.archivers.zip.ExtraFieldUtils.
 */
class ExtraFieldUtilsTest implements UnixStat {

    /**
     * A {@link ZipExtraField} whose parse methods always throw an
     * {@link ArrayIndexOutOfBoundsException}, used to verify that
     * {@link ExtraFieldUtils} turns such failures into a {@link ZipException}.
     */
    public static class AiobThrowingExtraField implements ZipExtraField {
        static final int LENGTH = 4;

        @Override
        public byte[] getCentralDirectoryData() {
            return getLocalFileDataData();
        }

        @Override
        public ZipShort getCentralDirectoryLength() {
            return getLocalFileDataLength();
        }

        @Override
        public ZipShort getHeaderId() {
            return AIOB_HEADER;
        }

        @Override
        public byte[] getLocalFileDataData() {
            return new byte[LENGTH];
        }

        @Override
        public ZipShort getLocalFileDataLength() {
            return new ZipShort(LENGTH);
        }

        @Override
        public void parseFromCentralDirectoryData(final byte[] buffer, final int offset, final int length) {
            parseFromLocalFileData(buffer, offset, length);
        }

        @Override
        public void parseFromLocalFileData(final byte[] buffer, final int offset, final int length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress.
     *
     * <p>
     * Used to be ZipShort(1) but this is the ID of the Zip64 extra field.
     * </p>
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress used for the ArrayIndexOutOfBoundsTest.
     */
    static final ZipShort AIOB_HEADER = new ZipShort(0x1000);

    /** Number of bytes used to encode an extra field's header id. */
    private static final int HEADER_ID_LENGTH = 2;

    /** Number of bytes used to encode an extra field's data-length value. */
    private static final int DATA_LENGTH_FIELD_LENGTH = 2;

    /** Size of the fixed prefix (header id + data length) preceding every extra field's payload. */
    private static final int FIELD_PREFIX_LENGTH = HEADER_ID_LENGTH + DATA_LENGTH_FIELD_LENGTH;

    /** Unix permission bits (rwxr-xr-x) configured on the AsiExtraField under test. */
    private static final int PERMISSIONS_0755 = 0755;

    /** Expected Asi mode once the directory flag is combined with {@link #PERMISSIONS_0755}: S_IFDIR | 0755. */
    private static final int EXPECTED_DIRECTORY_MODE = 040755;

    /** An AsiExtraField configured as a directory with {@link #PERMISSIONS_0755} permissions. */
    private AsiExtraField a;

    /** An unrecognized extra field carrying a single payload byte. */
    private UnrecognizedExtraField dummy;

    /** Serialized local-file-data representation of {@link #a} followed by {@link #dummy}. */
    private byte[] data;

    /** Local file data payload of {@link #a}, kept around because several tests reason about its length. */
    private byte[] aLocal;

    @BeforeEach
    public void setUp() {
        a = new AsiExtraField();
        a.setMode(PERMISSIONS_0755);
        a.setDirectory(true);

        dummy = new UnrecognizedExtraField();
        dummy.setHeaderId(UNRECOGNIZED_HEADER);
        dummy.setLocalFileDataData(new byte[] { 0 });
        dummy.setCentralDirectoryData(new byte[] { 0 });

        aLocal = a.getLocalFileDataData();
        // The local file data block is simply each field serialized as
        // [header id][data length][payload] and concatenated.
        data = concat(toLocalFileDataBytes(a), toLocalFileDataBytes(dummy));
    }

    /**
     * Test merge methods
     */
    @Test
    void testMerge() {
        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { a, dummy });
        assertArrayEquals(data, local, "merged local file data");

        final byte[] expectedCentral = concat(toCentralDirectoryBytes(a), toCentralDirectoryBytes(dummy));
        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { a, dummy });
        assertArrayEquals(expectedCentral, central, "merged central directory data");
    }

    @Test
    void testMergeWithUnparseableData() throws Exception {
        // An unparseable field stores its raw bytes verbatim (no header/length prefix is re-emitted).
        final ZipExtraField unparseable = new UnparseableExtraFieldData();
        final byte[] headerBytes = UNRECOGNIZED_HEADER.getBytes();
        unparseable.parseFromLocalFileData(new byte[] { headerBytes[0], headerBytes[1], 1, 0 }, 0, 4);

        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { a, unparseable });
        // The merged result is the normal data minus the dummy's trailing payload byte.
        assertEquals(data.length - 1, local.length, "merged local length");
        assertArrayEquals(Arrays.copyOf(data, local.length), local, "merged local file data");

        final byte[] unparseableCentral = unparseable.getCentralDirectoryData();
        final byte[] expectedCentral = concat(toCentralDirectoryBytes(a), unparseableCentral);
        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { a, unparseable });
        assertArrayEquals(expectedCentral, central, "merged central directory data");
    }

    /**
     * test parser.
     */
    @Test
    void testParse() throws Exception {
        final ZipExtraField[] ze = ExtraFieldUtils.parse(data);
        assertEquals(2, ze.length, "number of fields");
        assertFirstFieldIsDirectoryAsi(ze[0]);
        assertTrue(ze[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, ze[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Drop the dummy's trailing payload byte so its declared length no longer fits the buffer.
        final byte[] truncated = Arrays.copyOf(data, data.length - 1);
        final Exception e = assertThrows(Exception.class, () -> ExtraFieldUtils.parse(truncated), "data should be invalid");
        assertEquals("Bad extra field starting at " + (FIELD_PREFIX_LENGTH + aLocal.length)
                + ".  Block length of 1 bytes exceeds remaining data of 0 bytes.", e.getMessage(), "message");
    }

    @Test
    void testParseCentral() throws Exception {
        final ZipExtraField[] ze = ExtraFieldUtils.parse(data, false);
        assertEquals(2, ze.length, "number of fields");
        assertFirstFieldIsDirectoryAsi(ze[0]);
        assertTrue(ze[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, ze[1].getCentralDirectoryLength().getValue(), "data length field 2");
    }

    @Test
    void testParseTurnsArrayIndexOutOfBoundsIntoZipException() {
        ExtraFieldUtils.register(AiobThrowingExtraField.class);
        final AiobThrowingExtraField f = new AiobThrowingExtraField();
        final byte[] corrupt = toLocalFileDataBytes(f);
        final ZipException e = assertThrows(ZipException.class, () -> ExtraFieldUtils.parse(corrupt), "data should be invalid");
        assertEquals("Failed to parse corrupt ZIP extra field of type 1000", e.getMessage(), "message");
    }

    @Test
    void testParseWithRead() throws Exception {
        ZipExtraField[] ze = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, ze.length, "number of fields");
        assertFirstFieldIsDirectoryAsi(ze[0]);
        assertTrue(ze[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, ze[1].getLocalFileDataLength().getValue(), "data length field 2");

        // With the dummy's last payload byte removed, READ keeps the leftover bytes as an UnparseableExtraFieldData.
        final byte[] truncated = Arrays.copyOf(data, data.length - 1);
        ze = ExtraFieldUtils.parse(truncated, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, ze.length, "number of fields");
        assertFirstFieldIsDirectoryAsi(ze[0]);
        assertTrue(ze[1] instanceof UnparseableExtraFieldData, "type field 2");
        assertEquals(4, ze[1].getLocalFileDataLength().getValue(), "data length field 2");

        // The retained field holds the final 4 bytes of the truncated buffer.
        final byte[] expectedTail = Arrays.copyOfRange(truncated, truncated.length - 4, truncated.length);
        assertArrayEquals(expectedTail, ze[1].getLocalFileDataData(), "retained unparseable bytes");
    }

    @Test
    void testParseWithSkip() throws Exception {
        ZipExtraField[] ze = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(2, ze.length, "number of fields");
        assertFirstFieldIsDirectoryAsi(ze[0]);
        assertTrue(ze[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, ze[1].getLocalFileDataLength().getValue(), "data length field 2");

        // With the dummy's last payload byte removed, SKIP drops the unparseable trailing field entirely.
        final byte[] truncated = Arrays.copyOf(data, data.length - 1);
        ze = ExtraFieldUtils.parse(truncated, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(1, ze.length, "number of fields");
        assertFirstFieldIsDirectoryAsi(ze[0]);
    }

    /**
     * Asserts that the given field is the expected directory AsiExtraField, i.e. an AsiExtraField whose mode
     * is {@link #EXPECTED_DIRECTORY_MODE}.
     */
    private static void assertFirstFieldIsDirectoryAsi(final ZipExtraField field) {
        assertTrue(field instanceof AsiExtraField, "type field 1");
        assertEquals(EXPECTED_DIRECTORY_MODE, ((AsiExtraField) field).getMode(), "mode field 1");
    }

    /**
     * Serializes a field as it appears in local file data: [header id][local data length][local payload].
     */
    private static byte[] toLocalFileDataBytes(final ZipExtraField field) {
        return prefixWithHeader(field.getHeaderId(), field.getLocalFileDataLength(), field.getLocalFileDataData());
    }

    /**
     * Serializes a field as it appears in central directory data: [header id][central data length][central payload].
     */
    private static byte[] toCentralDirectoryBytes(final ZipExtraField field) {
        return prefixWithHeader(field.getHeaderId(), field.getCentralDirectoryLength(), field.getCentralDirectoryData());
    }

    /**
     * Builds a {@code [header id][data length][payload]} byte block.
     */
    private static byte[] prefixWithHeader(final ZipShort headerId, final ZipShort dataLength, final byte[] payload) {
        final byte[] block = new byte[FIELD_PREFIX_LENGTH + payload.length];
        System.arraycopy(headerId.getBytes(), 0, block, 0, HEADER_ID_LENGTH);
        System.arraycopy(dataLength.getBytes(), 0, block, HEADER_ID_LENGTH, DATA_LENGTH_FIELD_LENGTH);
        System.arraycopy(payload, 0, block, FIELD_PREFIX_LENGTH, payload.length);
        return block;
    }

    /**
     * Concatenates the given byte arrays into a single array.
     */
    private static byte[] concat(final byte[]... arrays) {
        int totalLength = 0;
        for (final byte[] array : arrays) {
            totalLength += array.length;
        }
        final byte[] result = new byte[totalLength];
        int offset = 0;
        for (final byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
