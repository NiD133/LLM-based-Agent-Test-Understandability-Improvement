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
public class ExtraFieldUtils_ESTest_test15 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parseMinimalCentralDirectoryBuffer_returnsSingleExtraField() throws Throwable {
        // A 4-byte buffer is the minimum valid extra field block:
        // 2 bytes header ID + 2 bytes length field (both zero here, indicating empty data)
        byte[] minimalExtraFieldBuffer = new byte[4];

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(minimalExtraFieldBuffer, false);

        assertEquals("Parsing a minimal 4-byte central directory buffer should produce exactly one extra field",
                1, parsedFields.length);
    }
}
