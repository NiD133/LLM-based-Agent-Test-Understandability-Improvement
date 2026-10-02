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
public class ExtraFieldUtils_ESTest_test04 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        UnparseableExtraFieldData unparseableExtraFieldData0 = new UnparseableExtraFieldData();
        ZipExtraField[] zipExtraFieldArray0 = new ZipExtraField[1];
        zipExtraFieldArray0[0] = (ZipExtraField) unparseableExtraFieldData0;
        byte[] byteArray0 = ExtraFieldUtils.mergeCentralDirectoryData(zipExtraFieldArray0);
        assertEquals(0, byteArray0.length);
    }
}
