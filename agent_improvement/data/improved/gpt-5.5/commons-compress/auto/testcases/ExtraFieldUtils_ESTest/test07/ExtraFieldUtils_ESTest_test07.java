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
public class ExtraFieldUtils_ESTest_test07 extends ExtraFieldUtils_ESTest_scaffolding {

    private static final int EMPTY_EXTRA_FIELD_RECORD_LENGTH = 4;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        ExtraFieldUtils.UnparseableExtraField readUnparseableField = ExtraFieldUtils.UnparseableExtraField.READ;
        byte[] centralDirectoryExtraData = new byte[EMPTY_EXTRA_FIELD_RECORD_LENGTH];

        ZipExtraField[] parsedExtraFields = ExtraFieldUtils.parse(centralDirectoryExtraData, false, readUnparseableField);
        byte[] mergedCentralDirectoryData = ExtraFieldUtils.mergeCentralDirectoryData(parsedExtraFields);

        assertEquals(EMPTY_EXTRA_FIELD_RECORD_LENGTH, mergedCentralDirectoryData.length);
    }
}
