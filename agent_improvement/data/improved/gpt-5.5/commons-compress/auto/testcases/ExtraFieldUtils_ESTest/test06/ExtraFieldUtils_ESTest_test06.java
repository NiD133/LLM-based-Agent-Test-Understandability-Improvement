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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        ExtraFieldUtils.UnparseableExtraField readUnparseableData = ExtraFieldUtils.UnparseableExtraField.READ;

        byte[] centralDirectoryData = new byte[4];
        centralDirectoryData[2] = (byte) (-62);

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(centralDirectoryData, false, readUnparseableData);
        byte[] mergedCentralDirectoryData = ExtraFieldUtils.mergeCentralDirectoryData(parsedFields);

        assertEquals(4, mergedCentralDirectoryData.length);
        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) (-62), (byte) 0 }, mergedCentralDirectoryData);
    }
}
