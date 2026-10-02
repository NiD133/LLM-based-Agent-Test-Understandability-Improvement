package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.zip.ZipException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ExtraFieldUtils#parse(byte[])} converts the
 * {@link ArrayIndexOutOfBoundsException} thrown by a faulty extra-field
 * implementation into a {@link ZipException} carrying a descriptive message.
 */
public class ExtraFieldUtilsTest_testParseTurnsArrayIndexOutOfBoundsIntoZipException implements UnixStat {

    /** Number of header bytes preceding an extra field's payload: 2-byte header id + 2-byte length. */
    private static final int EXTRA_FIELD_HEADER_BYTES = 4;

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

    /**
     * Builds a sample local-file-data buffer holding one recognized (ASI) and one
     * unrecognized extra field. This is the shared fixture of the original test
     * suite; it is retained here so the test runs against the same setup.
     */
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
        data = new byte[4 + aLocal.length + 4 + dummyLocal.length];
        System.arraycopy(a.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(aLocal, 0, data, 4, aLocal.length);
        System.arraycopy(dummy.getHeaderId().getBytes(), 0, data, 4 + aLocal.length, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, data, 4 + aLocal.length + 2, 2);
        System.arraycopy(dummyLocal, 0, data, 4 + aLocal.length + 4, dummyLocal.length);
    }

    @Test
    void testParseTurnsArrayIndexOutOfBoundsIntoZipException() {
        // Register an extra field whose parse routine deliberately reads out of bounds.
        ExtraFieldUtils.register(AiobThrowingExtraField.class);
        final AiobThrowingExtraField aiobField = new AiobThrowingExtraField();

        // Serialize the faulty field into a local-file-data buffer: header + payload.
        final byte[] localFileData = new byte[EXTRA_FIELD_HEADER_BYTES + AiobThrowingExtraField.LENGTH];
        System.arraycopy(aiobField.getHeaderId().getBytes(), 0, localFileData, 0, 2);
        System.arraycopy(aiobField.getLocalFileDataLength().getBytes(), 0, localFileData, 2, 2);
        System.arraycopy(aiobField.getLocalFileDataData(), 0, localFileData, EXTRA_FIELD_HEADER_BYTES, AiobThrowingExtraField.LENGTH);

        // Parsing must surface the internal AIOOBE as a ZipException with a clear message.
        final ZipException thrown = assertThrows(ZipException.class, () -> ExtraFieldUtils.parse(localFileData), "data should be invalid");
        assertEquals("Failed to parse corrupt ZIP extra field of type 1000", thrown.getMessage(), "message");
    }

    /**
     * An extra field whose {@code parseFrom*} methods always throw
     * {@link ArrayIndexOutOfBoundsException}, simulating a corrupt field. Its
     * header id ({@link #AIOB_HEADER}, {@code 0x1000}) drives the expected
     * "type 1000" wording of the resulting {@link ZipException}.
     */
    public static class AiobThrowingExtraField implements ZipExtraField {

        static final int LENGTH = 4;

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
        public void parseFromLocalFileData(final byte[] buffer, final int offset, final int length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        @Override
        public void parseFromCentralDirectoryData(final byte[] buffer, final int offset, final int length) {
            parseFromLocalFileData(buffer, offset, length);
        }
    }
}
