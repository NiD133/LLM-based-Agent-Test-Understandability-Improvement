package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test12 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that parsing local file extra field data containing a valid field followed by a
     * truncated (unparseable) field with READ behavior produces two ZipExtraField entries, and
     * that merging their local data reconstructs the original 13-byte buffer.
     *
     * Layout of the 13-byte buffer:
     *   Bytes 0-3:  first extra field — header ID [0x00,0x00], data length [0x00,0x00] (0 bytes of data)
     *   Bytes 4-7:  second field header — header ID [0x00,0x00], claimed length [0x00,0xF8] (63488, far exceeding remaining bytes)
     *   Bytes 8-12: remaining bytes that cannot satisfy the claimed length, triggering unparseable handling
     *
     * Because the second field's claimed length exceeds available data, ExtraFieldUtils.parse()
     * invokes the READ behavior, which wraps bytes 4-12 in an UnparseableExtraFieldData instance.
     * Merging reconstructs 4 bytes (header + zero-length for field 1) + 9 bytes (raw data for
     * the unparseable field) = 13 bytes total.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Build a 13-byte extra field buffer: one valid zero-length field, then a truncated field
        byte[] extraFieldData = new byte[13];
        // Bytes 0-3: first field with header [0,0] and zero-length data (all zeros by default)
        // Bytes 4-5: second field header [0,0] (all zeros by default)
        // Byte 7: high byte of the second field's claimed length → 0xF8, making length = 63488
        extraFieldData[7] = (byte) (-8); // 0xF8 — claimed length far exceeds remaining bytes

        // Use READ behavior so the truncated field is captured as UnparseableExtraFieldData
        ExtraFieldUtils.UnparseableExtraField readBehavior = ExtraFieldUtils.UnparseableExtraField.READ;

        // Parse as local file data; expect one normal field and one unparseable field
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(extraFieldData, true, readBehavior);
        assertEquals("Expected one valid field and one unparseable field", 2, parsedFields.length);

        // Merge the local data of both fields back into a byte array
        byte[] mergedData = ExtraFieldUtils.mergeLocalFileDataData(parsedFields);
        assertEquals("Merged data should equal the original extra field buffer length", 13, mergedData.length);
    }
}
