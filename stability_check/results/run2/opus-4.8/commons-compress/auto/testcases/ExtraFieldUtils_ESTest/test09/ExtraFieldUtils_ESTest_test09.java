package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test09 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that a freshly created UnicodePathExtraField, produced by the
     * STRICT_FOR_KNOW_EXTRA_FIELDS parsing mode for the Unicode-path header id,
     * starts out with a zero name CRC-32 (no name data parsed yet).
     */
    @Test(timeout = 4000)
    public void createUnicodePathExtraField_hasZeroNameCrc() throws Throwable {
        ZipArchiveEntry.ExtraFieldParsingMode strictParsingMode =
                ZipArchiveEntry.ExtraFieldParsingMode.STRICT_FOR_KNOW_EXTRA_FIELDS;
        ZipShort unicodePathHeaderId = UnicodePathExtraField.UPATH_ID;

        UnicodePathExtraField unicodePathField =
                (UnicodePathExtraField) strictParsingMode.createExtraField(unicodePathHeaderId);

        assertEquals(0L, unicodePathField.getNameCRC32());
    }
}
