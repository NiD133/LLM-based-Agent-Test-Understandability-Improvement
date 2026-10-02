package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test07 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * A 4-byte block of zeros forms exactly one minimal extra field: a 2-byte
     * header id (0x0000) followed by a 2-byte length of 0, leaving no payload.
     * Parsing it as central-directory data yields a single field, and merging
     * that field back must reproduce the original 4-byte header with no payload.
     */
    @Test(timeout = 4000)
    public void mergeCentralDirectoryDataReproducesSingleEmptyField() throws Throwable {
        final byte[] singleEmptyExtraField = new byte[4];
        final boolean centralDirectoryData = false;

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(
                singleEmptyExtraField, centralDirectoryData, ExtraFieldUtils.UnparseableExtraField.READ);

        byte[] merged = ExtraFieldUtils.mergeCentralDirectoryData(parsedFields);

        assertEquals(4, merged.length);
    }
}
