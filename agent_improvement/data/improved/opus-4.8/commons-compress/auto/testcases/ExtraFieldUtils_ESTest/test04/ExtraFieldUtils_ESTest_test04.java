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
     * When the only extra field is an empty {@link UnparseableExtraFieldData}, it is treated as the
     * trailing "unparseable holder" rather than a regular field. Since it carries no central
     * directory data, merging produces an empty byte array (no header/length words are emitted).
     */
    @Test(timeout = 4000)
    public void mergeCentralDirectoryData_withOnlyEmptyUnparseableField_returnsEmptyArray() throws Throwable {
        UnparseableExtraFieldData emptyUnparseableField = new UnparseableExtraFieldData();
        ZipExtraField[] extraFields = { emptyUnparseableField };

        byte[] mergedData = ExtraFieldUtils.mergeCentralDirectoryData(extraFields);

        assertEquals(0, mergedData.length);
    }
}
