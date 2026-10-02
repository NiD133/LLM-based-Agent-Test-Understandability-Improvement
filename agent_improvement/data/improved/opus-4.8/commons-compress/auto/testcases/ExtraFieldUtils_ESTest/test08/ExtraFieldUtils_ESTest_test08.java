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

    /**
     * Merging the central directory data of an empty extra-field array should
     * produce an empty byte array, since there is no field data to serialize.
     */
    @Test(timeout = 4000)
    public void mergeCentralDirectoryDataOfNoFieldsReturnsEmptyByteArray() throws Throwable {
        ZipExtraField[] noExtraFields = new ZipExtraField[0];

        byte[] mergedData = ExtraFieldUtils.mergeCentralDirectoryData(noExtraFields);

        assertEquals(0, mergedData.length);
    }
}
