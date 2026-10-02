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
     * A ZipExtraField implementation that always throws ArrayIndexOutOfBoundsException when
     * parseFromLocalFileData is called, used to verify that ExtraFieldUtils wraps such
     * exceptions in a ZipException.
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

    /** Number of bytes used to encode a header ID in the extra field wire format. */
    private static final int HEADER_ID_BYTES = 2;

    /** Number of bytes used to encode the data length in the extra field wire format. */
    private static final int DATA_LENGTH_BYTES = 2;

    /**
     * Total overhead in bytes added before each extra field's payload:
     * header-ID (2 bytes) + data-length (2 bytes).
     */
    private static final int EXTRA_FIELD_HEADER_SIZE = HEADER_ID_BYTES + DATA_LENGTH_BYTES;

    /** An AsiExtraField configured as a directory with Unix permissions rwxr-xr-x (octal 0755). */
    private AsiExtraField asiField;

    /** An extra field whose header ID is not recognised by Commons Compress. */
    private UnrecognizedExtraField unrecognizedField;

    /**
     * Combined serialised local-file-data byte array for {@link #asiField} followed by
     * {@link #unrecognizedField}, as it would appear in a ZIP local file header.
     */
    private byte[] data;

    /** Raw local-file-data bytes produced by {@link #asiField}, cached to avoid repeated calls. */
    private byte[] asiFieldLocalData;

    /**
     * Builds the test fixtures:
     * <ul>
     *   <li>{@link #asiField} – an ASI extra field for a directory with mode 0755</li>
     *   <li>{@link #unrecognizedField} – an unrecognised extra field with one data byte</li>
     *   <li>{@link #data} – the two fields serialised back-to-back in local-file-data format</li>
     * </ul>
     */
    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        asiFieldLocalData = asiField.getLocalFileDataData();
        final byte[] unrecognizedLocalData = unrecognizedField.getLocalFileDataData();

        // Layout: [asiField header (4 bytes)][asiField payload][unrecognizedField header (4 bytes)][unrecognizedField payload]
        data = new byte[EXTRA_FIELD_HEADER_SIZE + asiFieldLocalData.length
                      + EXTRA_FIELD_HEADER_SIZE + unrecognizedLocalData.length];

        int pos = 0;
        System.arraycopy(asiField.getHeaderId().getBytes(), 0, data, pos, HEADER_ID_BYTES);
        pos += HEADER_ID_BYTES;
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, data, pos, DATA_LENGTH_BYTES);
        pos += DATA_LENGTH_BYTES;
        System.arraycopy(asiFieldLocalData, 0, data, pos, asiFieldLocalData.length);
        pos += asiFieldLocalData.length;

        System.arraycopy(unrecognizedField.getHeaderId().getBytes(), 0, data, pos, HEADER_ID_BYTES);
        pos += HEADER_ID_BYTES;
        System.arraycopy(unrecognizedField.getLocalFileDataLength().getBytes(), 0, data, pos, DATA_LENGTH_BYTES);
        pos += DATA_LENGTH_BYTES;
        System.arraycopy(unrecognizedLocalData, 0, data, pos, unrecognizedLocalData.length);
    }

    /**
     * Verifies that mergeLocalFileDataData and mergeCentralDirectoryData serialise a
     * mix of known and unrecognised extra fields into the correct byte sequences.
     * The central-directory merge uses each field's central-directory length, which may
     * differ from the local-file-data length.
     */
    @Test
    void testMerge() {
        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { asiField, unrecognizedField });
        assertArrayEquals(data, local, "merged local file data");

        // Build expected central-directory byte array.
        // The unrecognized field's central-directory length may differ from its local-file-data length,
        // so we use getCentralDirectoryLength() rather than copying from the local data fixture.
        final int asiBlockSize = EXTRA_FIELD_HEADER_SIZE + asiFieldLocalData.length;
        final byte[] unrecognizedCentral = unrecognizedField.getCentralDirectoryData();
        final byte[] expectedCentral = new byte[asiBlockSize + EXTRA_FIELD_HEADER_SIZE + unrecognizedCentral.length];
        System.arraycopy(data, 0, expectedCentral, 0, asiBlockSize + HEADER_ID_BYTES);
        System.arraycopy(unrecognizedField.getCentralDirectoryLength().getBytes(), 0,
                         expectedCentral, asiBlockSize + HEADER_ID_BYTES, DATA_LENGTH_BYTES);
        System.arraycopy(unrecognizedCentral, 0,
                         expectedCentral, asiBlockSize + EXTRA_FIELD_HEADER_SIZE, unrecognizedCentral.length);

        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { asiField, unrecognizedField });
        assertArrayEquals(expectedCentral, central, "merged central directory data");
    }

    /**
     * Verifies merge behaviour when the last element is an UnparseableExtraFieldData.
     * Such fields are written without the 4-byte header overhead in both local and central merge.
     */
    @Test
    void testMergeWithUnparseableData() throws Exception {
        final UnparseableExtraFieldData unparseableField = new UnparseableExtraFieldData();
        final byte[] headerIdBytes = UNRECOGNIZED_HEADER.getBytes();
        unparseableField.parseFromLocalFileData(new byte[] { headerIdBytes[0], headerIdBytes[1], 1, 0 }, 0, 4);

        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { asiField, unparseableField });
        // The unparseable field contributes its raw bytes with no 4-byte header overhead,
        // so the merged result is one byte shorter than the local-data fixture (which has a dummy payload byte).
        assertEquals(data.length - 1, local.length, "local length");
        assertArrayEquals(Arrays.copyOf(data, local.length), local, "merged local file data with unparseable field");

        // Build expected central-directory bytes: asiField block followed directly by the unparseable field's central data.
        final int asiBlockSize = EXTRA_FIELD_HEADER_SIZE + asiFieldLocalData.length;
        final byte[] unparseableCentral = unparseableField.getCentralDirectoryData();
        final byte[] expectedCentral = new byte[asiBlockSize + unparseableCentral.length];
        System.arraycopy(data, 0, expectedCentral, 0, asiBlockSize);
        System.arraycopy(unparseableCentral, 0, expectedCentral, asiBlockSize, unparseableCentral.length);

        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { asiField, unparseableField });
        assertArrayEquals(expectedCentral, central, "merged central directory data with unparseable field");
    }

    /**
     * Verifies that parse(byte[]) correctly recognises an AsiExtraField and an
     * UnrecognizedExtraField, and throws an exception when the data is truncated.
     */
    @Test
    void testParse() throws Exception {
        final ZipExtraField[] extraFields = ExtraFieldUtils.parse(data);
        assertEquals(2, extraFields.length, "number of fields");
        assertTrue(extraFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) extraFields[0]).getMode(), "mode field 1");
        assertTrue(extraFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, extraFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Truncate the last byte so the second field's payload is missing.
        final byte[] truncatedData = Arrays.copyOf(data, data.length - 1);
        final int asiBlockSize = EXTRA_FIELD_HEADER_SIZE + asiFieldLocalData.length;
        final Exception e = assertThrows(Exception.class, () -> ExtraFieldUtils.parse(truncatedData), "data should be invalid");
        assertEquals("Bad extra field starting at " + asiBlockSize + ".  Block length of 1 bytes exceeds remaining data of 0 bytes.", e.getMessage(),
                "message");
    }

    /**
     * Verifies that parse(byte[], false) parses extra fields from central-directory data,
     * reporting central-directory lengths rather than local-file-data lengths.
     */
    @Test
    void testParseCentral() throws Exception {
        final ZipExtraField[] extraFields = ExtraFieldUtils.parse(data, false);
        assertEquals(2, extraFields.length, "number of fields");
        assertTrue(extraFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) extraFields[0]).getMode(), "mode field 1");
        assertTrue(extraFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, extraFields[1].getCentralDirectoryLength().getValue(), "data length field 2");
    }

    /**
     * Verifies that an ArrayIndexOutOfBoundsException thrown by a field's parseFromLocalFileData
     * is wrapped in a ZipException with a message identifying the corrupt field type.
     */
    @Test
    void testParseTurnsArrayIndexOutOfBoundsIntoZipException() {
        ExtraFieldUtils.register(AiobThrowingExtraField.class);
        final AiobThrowingExtraField aiobField = new AiobThrowingExtraField();

        // Build a valid byte buffer for the AIOB field so the parse attempt reaches parseFromLocalFileData.
        final byte[] fieldData = new byte[EXTRA_FIELD_HEADER_SIZE + AiobThrowingExtraField.LENGTH];
        int pos = 0;
        System.arraycopy(aiobField.getHeaderId().getBytes(), 0, fieldData, pos, HEADER_ID_BYTES);
        pos += HEADER_ID_BYTES;
        System.arraycopy(aiobField.getLocalFileDataLength().getBytes(), 0, fieldData, pos, DATA_LENGTH_BYTES);
        pos += DATA_LENGTH_BYTES;
        System.arraycopy(aiobField.getLocalFileDataData(), 0, fieldData, pos, AiobThrowingExtraField.LENGTH);

        final ZipException e = assertThrows(ZipException.class, () -> ExtraFieldUtils.parse(fieldData), "data should be invalid");
        assertEquals("Failed to parse corrupt ZIP extra field of type 1000", e.getMessage(), "message");
    }

    /**
     * Verifies parse with UnparseableExtraField.READ: valid data yields the normal fields,
     * and truncated data causes the truncated block to be preserved as an UnparseableExtraFieldData.
     */
    @Test
    void testParseWithRead() throws Exception {
        ZipExtraField[] extraFields = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, extraFields.length, "number of fields");
        assertTrue(extraFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) extraFields[0]).getMode(), "mode field 1");
        assertTrue(extraFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, extraFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Truncate the last byte; the second field (header+length only, no payload) becomes unparseable.
        final byte[] truncatedData = Arrays.copyOf(data, data.length - 1);
        extraFields = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, extraFields.length, "number of fields");
        assertTrue(extraFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) extraFields[0]).getMode(), "mode field 1");
        assertTrue(extraFields[1] instanceof UnparseableExtraFieldData, "type field 2");
        assertEquals(4, extraFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // The unparseable field stores the 4 raw bytes starting at the second field's position in truncatedData.
        final int asiBlockSize = EXTRA_FIELD_HEADER_SIZE + asiFieldLocalData.length;
        assertArrayEquals(
            Arrays.copyOfRange(truncatedData, asiBlockSize, asiBlockSize + 4),
            extraFields[1].getLocalFileDataData(),
            "unparseable extra field raw bytes"
        );
    }

    /**
     * Verifies parse with UnparseableExtraField.SKIP: valid data yields the normal fields,
     * and truncated data silently drops the truncated block so only the valid field remains.
     */
    @Test
    void testParseWithSkip() throws Exception {
        ZipExtraField[] extraFields = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(2, extraFields.length, "number of fields");
        assertTrue(extraFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) extraFields[0]).getMode(), "mode field 1");
        assertTrue(extraFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, extraFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // With SKIP, the truncated second field is silently discarded.
        final byte[] truncatedData = Arrays.copyOf(data, data.length - 1);
        extraFields = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(1, extraFields.length, "number of fields");
        assertTrue(extraFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) extraFields[0]).getMode(), "mode field 1");
    }
}
