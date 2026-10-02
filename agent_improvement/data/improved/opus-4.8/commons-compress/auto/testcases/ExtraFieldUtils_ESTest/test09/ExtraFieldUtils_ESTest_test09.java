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
     * The STRICT_FOR_KNOW_EXTRA_FIELDS parsing mode should create a concrete
     * UnicodePathExtraField for the registered Unicode path header id, and a
     * freshly created field should report a name CRC-32 of zero.
     */
    @Test(timeout = 4000)
    public void createExtraField_forUnicodePathHeaderId_returnsFieldWithZeroNameCrc() throws Throwable {
        ZipArchiveEntry.ExtraFieldParsingMode parsingMode =
                ZipArchiveEntry.ExtraFieldParsingMode.STRICT_FOR_KNOW_EXTRA_FIELDS;
        ZipShort unicodePathHeaderId = UnicodePathExtraField.UPATH_ID;

        UnicodePathExtraField unicodePathField =
                (UnicodePathExtraField) parsingMode.createExtraField(unicodePathHeaderId);

        assertEquals(0L, unicodePathField.getNameCRC32());
    }
}
