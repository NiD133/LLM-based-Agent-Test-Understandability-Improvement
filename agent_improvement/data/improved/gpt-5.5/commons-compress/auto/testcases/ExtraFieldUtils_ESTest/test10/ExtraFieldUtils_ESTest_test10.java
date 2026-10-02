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
public class ExtraFieldUtils_ESTest_test10 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        byte[] extraFieldData = new byte[8];
        // Header id 0 and length 0 describe one parseable empty extra field; the trailing byte is ignored.
        extraFieldData[7] = (byte) (-6);

        ZipArchiveEntry.ExtraFieldParsingMode strictParsingMode = ZipArchiveEntry.ExtraFieldParsingMode.ONLY_PARSEABLE_STRICT;

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(
                extraFieldData,
                false,
                (ExtraFieldParsingBehavior) strictParsingMode);

        assertEquals(1, parsedFields.length);
    }
}
