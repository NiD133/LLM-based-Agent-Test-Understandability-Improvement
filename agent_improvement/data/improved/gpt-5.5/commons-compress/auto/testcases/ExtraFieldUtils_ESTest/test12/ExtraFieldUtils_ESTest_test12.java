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
        byte[] extraFieldData = new byte[13];
        extraFieldData[7] = (byte) (-8);

        ExtraFieldUtils.UnparseableExtraField readUnparseableData = ExtraFieldUtils.UnparseableExtraField.READ;
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(extraFieldData, true, readUnparseableData);
        byte[] mergedLocalFileData = ExtraFieldUtils.mergeLocalFileDataData(parsedFields);

        assertEquals(13, mergedLocalFileData.length);
        assertEquals(2, parsedFields.length);
    }
}
