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
public class ExtraFieldUtils_ESTest_test16 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that merging local file data from fields parsed out of a 13-byte
     * all-zeros buffer produces a 12-byte result.
     *
     * A 13-byte buffer of zeros yields three extra fields (each with a 2-byte
     * header ID of 0x0000 and a 2-byte length of 0, consuming 4 bytes each,
     * for 12 bytes total; the 13th byte is left over and does not start a new
     * complete 4-byte field header). The READ behavior preserves any unparseable
     * trailing data, but here every 4-byte slot is well-formed, so merging the
     * local file data of the three fields produces exactly 12 bytes (3 x 4-byte
     * field headers with zero-length payloads).
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        byte[] allZeroData = new byte[13];
        ExtraFieldUtils.UnparseableExtraField readUnparseable = ExtraFieldUtils.UnparseableExtraField.READ;
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(allZeroData, true, readUnparseable);
        byte[] mergedLocalData = ExtraFieldUtils.mergeLocalFileDataData(parsedFields);
        assertEquals(12, mergedLocalData.length);
    }
}
