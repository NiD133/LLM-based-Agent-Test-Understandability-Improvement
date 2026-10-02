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

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        byte[] extraFieldData = new byte[13];
        ExtraFieldUtils.UnparseableExtraField unparseableFieldBehavior = ExtraFieldUtils.UnparseableExtraField.READ;

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(extraFieldData, true, unparseableFieldBehavior);
        byte[] mergedLocalFileData = ExtraFieldUtils.mergeLocalFileDataData(parsedFields);

        assertEquals(12, mergedLocalFileData.length);
    }
}
