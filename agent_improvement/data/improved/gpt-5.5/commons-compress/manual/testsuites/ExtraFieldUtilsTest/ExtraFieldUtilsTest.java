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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for {@link ExtraFieldUtils}.
 */
class ExtraFieldUtilsTest implements UnixStat {

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

    private AsiExtraField asiExtraField;
    private UnrecognizedExtraField unrecognizedExtraField;
    private byte[] mergedLocalData;
    private byte[] asiLocalData;

    @BeforeEach
    public void setUp() {
        asiExtraField = new AsiExtraField();
        asiExtraField.setMode(0755);
        asiExtraField.setDirectory(true);

        unrecognizedExtraField = new UnrecognizedExtraField();
        unrecognizedExtraField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedExtraField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedExtraField.setCentralDirectoryData(new byte[] { 0 });

        asiLocalData = asiExtraField.getLocalFileDataData();
        final byte[] dummyLocal = unrecognizedExtraField.getLocalFileDataData();
        mergedLocalData = new byte[4 + asiLocalData.length + 4 + dummyLocal.length];
        System.arraycopy(asiExtraField.getHeaderId().getBytes(), 0, mergedLocalData, 0, 2);
        System.arraycopy(asiExtraField.getLocalFileDataLength().getBytes(), 0, mergedLocalData, 2, 2);
        System.arraycopy(asiLocalData, 0, mergedLocalData, 4, asiLocalData.length);
        System.arraycopy(unrecognizedExtraField.getHeaderId().getBytes(), 0, mergedLocalData, 4 + asiLocalData.length, 2);
        System.arraycopy(unrecognizedExtraField.getLocalFileDataLength().getBytes(), 0, mergedLocalData, 4 + asiLocalData.length + 2, 2);
        System.arraycopy(dummyLocal, 0, mergedLocalData, 4 + asiLocalData.length + 4, dummyLocal.length);
    }

    /**
     * Test merge methods.
     */
    @Test
    void testMerge() {
        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { asiExtraField, unrecognizedExtraField });
        assertByteArrayEquals("local", mergedLocalData, local);

        final byte[] dummyCentral = unrecognizedExtraField.getCentralDirectoryData();
        final byte[] expectedCentralData = new byte[4 + asiLocalData.length + 4 + dummyCentral.length];
        System.arraycopy(mergedLocalData, 0, expectedCentralData, 0, 4 + asiLocalData.length + 2);
        System.arraycopy(unrecognizedExtraField.getCentralDirectoryLength().getBytes(), 0, expectedCentralData, 4 + asiLocalData.length + 2, 2);
        System.arraycopy(dummyCentral, 0, expectedCentralData, 4 + asiLocalData.length + 4, dummyCentral.length);

        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { asiExtraField, unrecognizedExtraField });
        assertByteArrayEquals("central", expectedCentralData, central);
    }

    @Test
    void testMergeWithUnparseableData() throws Exception {
        final ZipExtraField unparseableField = new UnparseableExtraFieldData();
        final byte[] headerBytes = UNRECOGNIZED_HEADER.getBytes();
        unparseableField.parseFromLocalFileData(new byte[] { headerBytes[0], headerBytes[1], 1, 0 }, 0, 4);

        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { asiExtraField, unparseableField });
        assertEquals(mergedLocalData.length - 1, local.length, "local length");
        assertByteArrayPrefixEquals("local", mergedLocalData, local);

        final byte[] unparseableCentralData = unparseableField.getCentralDirectoryData();
        final byte[] expectedCentralData = new byte[4 + asiLocalData.length + unparseableCentralData.length];
        System.arraycopy(mergedLocalData, 0, expectedCentralData, 0, 4 + asiLocalData.length + 2);
        System.arraycopy(unparseableCentralData, 0, expectedCentralData, 4 + asiLocalData.length, unparseableCentralData.length);

        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { asiExtraField, unparseableField });
        assertByteArrayEquals("central", expectedCentralData, central);
    }

    /**
     * Test parser.
     */
    @Test
    void testParse() throws Exception {
        final ZipExtraField[] ze = ExtraFieldUtils.parse(mergedLocalData);
        assertExpectedLocalFields(ze);

        final byte[] truncatedData = new byte[mergedLocalData.length - 1];
        System.arraycopy(mergedLocalData, 0, truncatedData, 0, truncatedData.length);
        final Exception e = assertThrows(Exception.class, () -> ExtraFieldUtils.parse(truncatedData), "data should be invalid");
        assertEquals("Bad extra field starting at " + (4 + asiLocalData.length) + ".  Block length of 1 bytes exceeds remaining data of 0 bytes.",
                e.getMessage(), "message");
    }

    @Test
    void testParseCentral() throws Exception {
        final ZipExtraField[] ze = ExtraFieldUtils.parse(mergedLocalData, false);
        assertExpectedCentralFields(ze);
    }

    @Test
    void testParseTurnsArrayIndexOutOfBoundsIntoZipException() {
        ExtraFieldUtils.register(AiobThrowingExtraField.class);
        final AiobThrowingExtraField f = new AiobThrowingExtraField();
        final byte[] d = new byte[4 + AiobThrowingExtraField.LENGTH];
        System.arraycopy(f.getHeaderId().getBytes(), 0, d, 0, 2);
        System.arraycopy(f.getLocalFileDataLength().getBytes(), 0, d, 2, 2);
        System.arraycopy(f.getLocalFileDataData(), 0, d, 4, AiobThrowingExtraField.LENGTH);
        final ZipException e = assertThrows(ZipException.class, () -> ExtraFieldUtils.parse(d), "data should be invalid");
        assertEquals("Failed to parse corrupt ZIP extra field of type 1000", e.getMessage(), "message");
    }

    @Test
    void testParseWithRead() throws Exception {
        ZipExtraField[] ze = ExtraFieldUtils.parse(mergedLocalData, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertExpectedLocalFields(ze);

        final byte[] truncatedData = new byte[mergedLocalData.length - 1];
        System.arraycopy(mergedLocalData, 0, truncatedData, 0, truncatedData.length);
        ze = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, ze.length, "number of fields");
        assertExpectedAsiField(ze[0]);
        assertTrue(ze[1] instanceof UnparseableExtraFieldData, "type field 2");
        assertEquals(4, ze[1].getLocalFileDataLength().getValue(), "data length field 2");
        for (int i = 0; i < 4; i++) {
            assertEquals(truncatedData[mergedLocalData.length - 5 + i], ze[1].getLocalFileDataData()[i], "byte number " + i);
        }
    }

    @Test
    void testParseWithSkip() throws Exception {
        ZipExtraField[] ze = ExtraFieldUtils.parse(mergedLocalData, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertExpectedLocalFields(ze);

        final byte[] truncatedData = new byte[mergedLocalData.length - 1];
        System.arraycopy(mergedLocalData, 0, truncatedData, 0, truncatedData.length);
        ze = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(1, ze.length, "number of fields");
        assertExpectedAsiField(ze[0]);
    }

    private void assertByteArrayEquals(final String messagePrefix, final byte[] expected, final byte[] actual) {
        assertEquals(expected.length, actual.length, messagePrefix + " length");
        assertByteArrayPrefixEquals(messagePrefix, expected, actual);
    }

    private void assertByteArrayPrefixEquals(final String messagePrefix, final byte[] expected, final byte[] actual) {
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i], messagePrefix + " byte " + i);
        }
    }

    private void assertExpectedAsiField(final ZipExtraField field) {
        assertTrue(field instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) field).getMode(), "mode field 1");
    }

    private void assertExpectedCentralFields(final ZipExtraField[] fields) {
        assertEquals(2, fields.length, "number of fields");
        assertExpectedAsiField(fields[0]);
        assertTrue(fields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, fields[1].getCentralDirectoryLength().getValue(), "data length field 2");
    }

    private void assertExpectedLocalFields(final ZipExtraField[] fields) {
        assertEquals(2, fields.length, "number of fields");
        assertExpectedAsiField(fields[0]);
        assertTrue(fields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, fields[1].getLocalFileDataLength().getValue(), "data length field 2");
    }
}
