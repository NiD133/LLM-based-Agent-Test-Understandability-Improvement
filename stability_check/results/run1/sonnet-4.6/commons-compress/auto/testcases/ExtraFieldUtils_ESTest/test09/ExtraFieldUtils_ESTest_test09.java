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
public class ExtraFieldUtils_ESTest_test09 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that STRICT_FOR_KNOW_EXTRA_FIELDS parsing mode creates a fresh
     * UnicodePathExtraField with a default CRC32 of 0 when given UPATH_ID.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        ZipArchiveEntry.ExtraFieldParsingMode strictMode =
                ZipArchiveEntry.ExtraFieldParsingMode.STRICT_FOR_KNOW_EXTRA_FIELDS;

        ZipShort unicodePathHeaderId = UnicodePathExtraField.UPATH_ID;

        UnicodePathExtraField createdField =
                (UnicodePathExtraField) strictMode.createExtraField(unicodePathHeaderId);

        assertEquals(0L, createdField.getNameCRC32());
    }
}
