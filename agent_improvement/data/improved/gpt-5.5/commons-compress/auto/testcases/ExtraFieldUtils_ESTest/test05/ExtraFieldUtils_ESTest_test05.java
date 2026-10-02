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
        final ZipExtraField[] extraFields = new ZipExtraField[1];
        final UnicodePathExtraField unicodePathExtraField = new UnicodePathExtraField();
        extraFields[0] = (ZipExtraField) unicodePathExtraField;

        final byte[] mergedCentralDirectoryData = ExtraFieldUtils.mergeCentralDirectoryData(extraFields);

        assertArrayEquals(new byte[] { (byte) 117, (byte) 112, (byte) 0, (byte) 0 }, mergedCentralDirectoryData);
    }
}
