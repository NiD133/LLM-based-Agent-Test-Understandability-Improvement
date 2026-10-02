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

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        byte[] byteArray0 = new byte[13];
        byteArray0[7] = (byte) (-8);
        ExtraFieldUtils.UnparseableExtraField extraFieldUtils_UnparseableExtraField0 = ExtraFieldUtils.UnparseableExtraField.READ;
        ZipExtraField[] zipExtraFieldArray0 = ExtraFieldUtils.parse(byteArray0, true, extraFieldUtils_UnparseableExtraField0);
        byte[] byteArray1 = ExtraFieldUtils.mergeLocalFileDataData(zipExtraFieldArray0);
        assertEquals(13, byteArray1.length);
        assertEquals(2, zipExtraFieldArray0.length);
    }
}
