package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test03 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Merging an empty array of extra fields should produce an empty byte array,
     * since there is no local file data to concatenate.
     */
    @Test(timeout = 4000)
    public void mergeLocalFileDataData_withNoExtraFields_returnsEmptyByteArray() throws Throwable {
        ZipExtraField[] noExtraFields = new ZipExtraField[0];

        byte[] mergedData = ExtraFieldUtils.mergeLocalFileDataData(noExtraFields);

        assertEquals(0, mergedData.length);
    }
}
