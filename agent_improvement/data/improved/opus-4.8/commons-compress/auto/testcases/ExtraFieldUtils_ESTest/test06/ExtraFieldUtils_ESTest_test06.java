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
public class ExtraFieldUtils_ESTest_test06 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * When central-directory extra-field data declares a block length that
     * exceeds the bytes actually available, the READ behavior keeps the raw
     * bytes in an UnparseableExtraFieldData holder. Merging that holder back
     * to bytes must reproduce the original 4-byte input unchanged.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // A 4-byte field: header id {0,0}, declared length from bytes {0xC2,0x00}
        // (194), which is far larger than the 4 bytes present -> unparseable.
        byte[] rawExtraField = new byte[4];
        rawExtraField[2] = (byte) -62;

        // Parse as central-directory data (local = false); READ retains the raw bytes.
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(
                rawExtraField, false, ExtraFieldUtils.UnparseableExtraField.READ);

        byte[] mergedBytes = ExtraFieldUtils.mergeCentralDirectoryData(parsedFields);

        assertEquals(4, mergedBytes.length);
        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) -62, (byte) 0 }, mergedBytes);
    }
}
