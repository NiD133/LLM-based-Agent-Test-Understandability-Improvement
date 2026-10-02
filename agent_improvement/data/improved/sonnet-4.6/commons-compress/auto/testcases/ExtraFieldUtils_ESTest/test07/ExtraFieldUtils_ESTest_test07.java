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

    /**
     * Parsing 4 zero bytes from central directory data with READ behavior wraps the content
     * in an UnparseableExtraFieldData; merging its central directory representation back
     * must reproduce exactly 4 bytes (the original input length).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // READ: treat unrecognised/malformed fields as UnparseableExtraFieldData instead of throwing
        ExtraFieldUtils.UnparseableExtraField readBehavior = ExtraFieldUtils.UnparseableExtraField.READ;

        // 4 zero bytes represent one minimal extra field entry (2-byte header id + 2-byte length = 0)
        byte[] centralDirectoryExtraBytes = new byte[4];

        // Parse from central directory data (local = false)
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(centralDirectoryExtraBytes, false, readBehavior);

        // Re-merge the parsed fields back into central-directory byte form
        byte[] mergedBytes = ExtraFieldUtils.mergeCentralDirectoryData(parsedFields);

        // The merged output must be the same length as the original 4-byte input
        assertEquals(4, mergedBytes.length);
    }
}
