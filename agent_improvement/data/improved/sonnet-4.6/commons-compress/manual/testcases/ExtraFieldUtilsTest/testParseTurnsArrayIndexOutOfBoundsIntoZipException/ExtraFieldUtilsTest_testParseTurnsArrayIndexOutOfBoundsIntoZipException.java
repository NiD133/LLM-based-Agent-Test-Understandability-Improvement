package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.zip.ZipException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseTurnsArrayIndexOutOfBoundsIntoZipException implements UnixStat {

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
        data = new byte[4 + aLocal.length + 4 + dummyLocal.length];
        System.arraycopy(a.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(aLocal, 0, data, 4, aLocal.length);
        System.arraycopy(dummy.getHeaderId().getBytes(), 0, data, 4 + aLocal.length, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, data, 4 + aLocal.length + 2, 2);
        System.arraycopy(dummyLocal, 0, data, 4 + aLocal.length + 4, dummyLocal.length);
    }

    /**
     * Verifies that ExtraFieldUtils.parse() wraps ArrayIndexOutOfBoundsException in a ZipException.
     *
     * <p>
     * AiobThrowingExtraField deliberately throws ArrayIndexOutOfBoundsException during parsing.
     * The parse() method must catch it and re-throw as a ZipException with a descriptive message
     * identifying the corrupt extra field by its header type.
     * </p>
     */
    @Test
    void testParseTurnsArrayIndexOutOfBoundsIntoZipException() {
        ExtraFieldUtils.register(AiobThrowingExtraField.class);

        // Build a valid serialized extra field record:
        //   bytes 0-1: 2-byte header ID (little-endian)
        //   bytes 2-3: 2-byte data length (little-endian)
        //   bytes 4+ : field data (AiobThrowingExtraField.LENGTH bytes)
        final AiobThrowingExtraField aiobField = new AiobThrowingExtraField();
        final byte[] corruptExtraFieldData = new byte[4 + AiobThrowingExtraField.LENGTH];
        System.arraycopy(aiobField.getHeaderId().getBytes(), 0, corruptExtraFieldData, 0, 2);
        System.arraycopy(aiobField.getLocalFileDataLength().getBytes(), 0, corruptExtraFieldData, 2, 2);
        System.arraycopy(aiobField.getLocalFileDataData(), 0, corruptExtraFieldData, 4, AiobThrowingExtraField.LENGTH);

        final ZipException thrown = assertThrows(ZipException.class,
                () -> ExtraFieldUtils.parse(corruptExtraFieldData),
                "data should be invalid");
        assertEquals("Failed to parse corrupt ZIP extra field of type 1000", thrown.getMessage(), "message");
    }
}
