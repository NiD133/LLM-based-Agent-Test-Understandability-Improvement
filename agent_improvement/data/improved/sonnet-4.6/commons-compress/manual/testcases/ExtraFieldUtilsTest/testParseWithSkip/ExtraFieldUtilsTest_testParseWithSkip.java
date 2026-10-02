package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.zip.ZipException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseWithSkip implements UnixStat {

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

    /** Unix directory mode: rwxr-xr-x (octal 0755) with directory bit set. */
    static final int UNIX_DIRECTORY_MODE = 040755;

    private AsiExtraField a;

    private UnrecognizedExtraField dummy;

    private byte[] data;

    private byte[] asiLocalData;

    @BeforeEach
    public void setUp() {
        a = new AsiExtraField();
        a.setMode(0755);
        a.setDirectory(true);
        dummy = new UnrecognizedExtraField();
        dummy.setHeaderId(UNRECOGNIZED_HEADER);
        dummy.setLocalFileDataData(new byte[] { 0 });
        dummy.setCentralDirectoryData(new byte[] { 0 });

        // Build a raw extra-field byte array with two entries back-to-back:
        //   [AsiExtraField header (2)] [AsiExtraField length (2)] [AsiExtraField data]
        //   [UnrecognizedExtraField header (2)] [UnrecognizedExtraField length (2)] [UnrecognizedExtraField data]
        asiLocalData = a.getLocalFileDataData();
        final byte[] dummyLocal = dummy.getLocalFileDataData();
        data = new byte[4 + asiLocalData.length + 4 + dummyLocal.length];
        System.arraycopy(a.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(asiLocalData, 0, data, 4, asiLocalData.length);
        System.arraycopy(dummy.getHeaderId().getBytes(), 0, data, 4 + asiLocalData.length, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, data, 4 + asiLocalData.length + 2, 2);
        System.arraycopy(dummyLocal, 0, data, 4 + asiLocalData.length + 4, dummyLocal.length);
    }

    /**
     * Parsing well-formed extra field data with SKIP behaviour should return both fields intact.
     */
    @Test
    void testParseWithSkip_validData_returnsBothFields() throws Exception {
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.SKIP);

        assertEquals(2, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(UNIX_DIRECTORY_MODE, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getLocalFileDataLength().getValue(), "data length field 2");
    }

    /**
     * When the byte array is truncated mid-way through the second field, SKIP behaviour should
     * silently drop the incomplete field and return only the first (complete) field.
     */
    @Test
    void testParseWithSkip_truncatedData_skipsIncompleteField() throws Exception {
        final byte[] truncatedData = new byte[data.length - 1];
        System.arraycopy(data, 0, truncatedData, 0, truncatedData.length);

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.SKIP);

        assertEquals(1, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(UNIX_DIRECTORY_MODE, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
    }
}
