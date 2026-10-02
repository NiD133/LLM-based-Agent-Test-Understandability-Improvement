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
public class ExtraFieldUtils_ESTest_test08 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Merging central directory data from an empty extra-field array should produce an empty byte array
        ZipExtraField[] emptyExtraFields = new ZipExtraField[0];
        byte[] mergedData = ExtraFieldUtils.mergeCentralDirectoryData(emptyExtraFields);
        assertEquals(0, mergedData.length);
    }
}
