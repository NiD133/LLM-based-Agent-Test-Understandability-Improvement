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
public class ExtraFieldUtils_ESTest_test05 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        ZipExtraField[] zipExtraFieldArray0 = new ZipExtraField[1];
        UnicodePathExtraField unicodePathExtraField0 = new UnicodePathExtraField();
        zipExtraFieldArray0[0] = (ZipExtraField) unicodePathExtraField0;
        byte[] byteArray0 = ExtraFieldUtils.mergeCentralDirectoryData(zipExtraFieldArray0);
        assertArrayEquals(new byte[] { (byte) 117, (byte) 112, (byte) 0, (byte) 0 }, byteArray0);
    }
}
