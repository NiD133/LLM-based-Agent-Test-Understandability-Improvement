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
     * Verifies that STRICT_FOR_KNOW_EXTRA_FIELDS parsing mode creates a fresh UnicodePathExtraField
     * whose name CRC32 defaults to 0 (no path data has been set yet).
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        ZipArchiveEntry.ExtraFieldParsingMode strictKnownFieldsMode =
                ZipArchiveEntry.ExtraFieldParsingMode.STRICT_FOR_KNOW_EXTRA_FIELDS;

        ZipShort unicodePathHeaderId = UnicodePathExtraField.UPATH_ID;

        UnicodePathExtraField newUnicodePathField =
                (UnicodePathExtraField) strictKnownFieldsMode.createExtraField(unicodePathHeaderId);

        assertEquals("A newly created UnicodePathExtraField should have a name CRC32 of 0",
                0L, newUnicodePathField.getNameCRC32());
    }
}
