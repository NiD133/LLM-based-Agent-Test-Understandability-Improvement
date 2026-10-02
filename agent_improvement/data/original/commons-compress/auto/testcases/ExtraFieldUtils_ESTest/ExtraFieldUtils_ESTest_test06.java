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
        ExtraFieldUtils.UnparseableExtraField extraFieldUtils_UnparseableExtraField0 = ExtraFieldUtils.UnparseableExtraField.READ;
        byte[] byteArray0 = new byte[4];
        byteArray0[2] = (byte) (-62);
        ZipExtraField[] zipExtraFieldArray0 = ExtraFieldUtils.parse(byteArray0, false, extraFieldUtils_UnparseableExtraField0);
        byte[] byteArray1 = ExtraFieldUtils.mergeCentralDirectoryData(zipExtraFieldArray0);
        assertEquals(4, byteArray1.length);
        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) (-62), (byte) 0 }, byteArray1);
    }
}
