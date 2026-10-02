package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseWithRead implements UnixStat {

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

    // Directory flag (040000) combined with rwxr-xr-x permissions (0755), as set in setUp()
    private static final int EXPECTED_DIR_MODE = 040755;

    // Number of bytes in a ZIP extra field header (2-byte ID + 2-byte length)
    private static final int EXTRA_FIELD_HEADER_SIZE = 4;

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
        // Build a raw extra field byte array concatenating both fields:
        // [AsiExtraField: 4-byte header + data][UnrecognizedExtraField: 4-byte header + data]
        data = new byte[EXTRA_FIELD_HEADER_SIZE + aLocal.length + EXTRA_FIELD_HEADER_SIZE + dummyLocal.length];
        System.arraycopy(a.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(aLocal, 0, data, 4, aLocal.length);
        System.arraycopy(dummy.getHeaderId().getBytes(), 0, data, 4 + aLocal.length, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, data, 4 + aLocal.length + 2, 2);
        System.arraycopy(dummyLocal, 0, data, 4 + aLocal.length + 4, dummyLocal.length);
    }

    @Test
    void testParseWithRead() throws Exception {
        // Scenario 1: parse well-formed data with READ behavior.
        // Both fields are parseable, so they should be returned as their recognized types.
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(EXPECTED_DIR_MODE, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Scenario 2: parse truncated data (last byte removed) with READ behavior.
        // The second field's claimed length exceeds available bytes, so READ mode wraps
        // the remaining 4 bytes (the truncated field's raw header) into UnparseableExtraFieldData.
        final byte[] truncatedData = new byte[data.length - 1];
        System.arraycopy(data, 0, truncatedData, 0, truncatedData.length);
        parsedFields = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(EXPECTED_DIR_MODE, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
        assertTrue(parsedFields[1] instanceof UnparseableExtraFieldData, "type field 2");
        // The unparseable field captures the 4 bytes where the second field's header begins
        assertEquals(4, parsedFields[1].getLocalFileDataLength().getValue(), "data length field 2");
        // Verify each of those 4 captured bytes matches the original truncated data
        final int secondFieldOffset = data.length - 5; // = start of dummy field header in truncatedData
        for (int i = 0; i < 4; i++) {
            assertEquals(truncatedData[secondFieldOffset + i], parsedFields[1].getLocalFileDataData()[i], "byte number " + i);
        }
    }
}
