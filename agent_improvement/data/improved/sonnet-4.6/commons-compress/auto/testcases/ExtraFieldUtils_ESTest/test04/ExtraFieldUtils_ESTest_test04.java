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
public class ExtraFieldUtils_ESTest_test04 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that mergeCentralDirectoryData returns an empty byte array when
     * the input contains only an UnparseableExtraFieldData with no data.
     *
     * UnparseableExtraFieldData is treated as the "last unparseable holder" and
     * its central directory data is appended raw (without a 4-byte header prefix).
     * Since no data has been parsed into it, getCentralDirectoryData() returns
     * an empty/null result, producing a zero-length output array.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arrange: create an unparseable extra field with no data loaded
        UnparseableExtraFieldData emptyUnparseableField = new UnparseableExtraFieldData();
        ZipExtraField[] fieldsWithOnlyUnparseable = new ZipExtraField[1];
        fieldsWithOnlyUnparseable[0] = (ZipExtraField) emptyUnparseableField;

        // Act: merge central directory data — unparseable field contributes no bytes
        byte[] mergedData = ExtraFieldUtils.mergeCentralDirectoryData(fieldsWithOnlyUnparseable);

        // Assert: result is empty because the unparseable field holds no central directory data
        assertEquals(0, mergedData.length);
    }
}
