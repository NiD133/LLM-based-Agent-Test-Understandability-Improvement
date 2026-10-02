package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseTurnsArrayIndexOutOfBoundsIntoZipException implements UnixStat {

    private static final int EXTRA_FIELD_HEADER_LENGTH = 4;

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

    private AsiExtraField a;

    private UnrecognizedExtraField dummy;

    private byte[] data;

    private byte[] aLocal;

    @BeforeEach
    public void setUp() {
        a = new AsiExtraField();
        a.setMode(0755);
        a.setDirectory(true);
        dummy = new UnrecognizedExtraField();
        dummy.setHeaderId(UNRECOGNIZED_HEADER);
        dummy.setLocalFileDataData(new byte[] { 0 });
        dummy.setCentralDirectoryData(new byte[] { 0 });
        aLocal = a.getLocalFileDataData();
        final byte[] dummyLocal = dummy.getLocalFileDataData();
        data = new byte[EXTRA_FIELD_HEADER_LENGTH + aLocal.length + EXTRA_FIELD_HEADER_LENGTH + dummyLocal.length];
        System.arraycopy(a.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(aLocal, 0, data, EXTRA_FIELD_HEADER_LENGTH, aLocal.length);
        System.arraycopy(dummy.getHeaderId().getBytes(), 0, data, EXTRA_FIELD_HEADER_LENGTH + aLocal.length, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, data, EXTRA_FIELD_HEADER_LENGTH + aLocal.length + 2, 2);
        System.arraycopy(dummyLocal, 0, data, EXTRA_FIELD_HEADER_LENGTH + aLocal.length + EXTRA_FIELD_HEADER_LENGTH, dummyLocal.length);
    }

    @Test
    void testParseTurnsArrayIndexOutOfBoundsIntoZipException() {
        ExtraFieldUtils.register(AiobThrowingExtraField.class);

        final AiobThrowingExtraField throwingField = new AiobThrowingExtraField();
        final byte[] corruptExtraFieldData = serializeLocalExtraField(throwingField);

        final ZipException exception = assertThrows(ZipException.class, () -> ExtraFieldUtils.parse(corruptExtraFieldData), "data should be invalid");
        assertEquals("Failed to parse corrupt ZIP extra field of type 1000", exception.getMessage(), "message");
    }

    private byte[] serializeLocalExtraField(final AiobThrowingExtraField field) {
        final byte[] localFileData = new byte[EXTRA_FIELD_HEADER_LENGTH + AiobThrowingExtraField.LENGTH];
        System.arraycopy(field.getHeaderId().getBytes(), 0, localFileData, 0, 2);
        System.arraycopy(field.getLocalFileDataLength().getBytes(), 0, localFileData, 2, 2);
        System.arraycopy(field.getLocalFileDataData(), 0, localFileData, EXTRA_FIELD_HEADER_LENGTH, AiobThrowingExtraField.LENGTH);
        return localFileData;
    }

    public static class AiobThrowingExtraField implements ZipExtraField {

        private static final int LENGTH = 1;

        @Override
        public ZipShort getHeaderId() {
            return AIOB_HEADER;
        }

        @Override
        public ZipShort getLocalFileDataLength() {
            return new ZipShort(LENGTH);
        }

        @Override
        public ZipShort getCentralDirectoryLength() {
            return getLocalFileDataLength();
        }

        @Override
        public byte[] getLocalFileDataData() {
            return new byte[LENGTH];
        }

        @Override
        public byte[] getCentralDirectoryData() {
            return getLocalFileDataData();
        }

        @Override
        public void parseFromLocalFileData(final byte[] data, final int offset, final int length) throws ZipException {
            throw new ArrayIndexOutOfBoundsException();
        }

        @Override
        public void parseFromCentralDirectoryData(final byte[] data, final int offset, final int length) throws ZipException {
            parseFromLocalFileData(data, offset, length);
        }
    }
}
