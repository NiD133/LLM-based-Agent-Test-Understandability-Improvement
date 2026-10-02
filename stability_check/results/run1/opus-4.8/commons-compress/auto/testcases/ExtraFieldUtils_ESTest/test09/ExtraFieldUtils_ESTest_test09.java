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
     * When the STRICT_FOR_KNOW_EXTRA_FIELDS parsing mode creates an extra field for the
     * Unicode path header id, it should return a fresh UnicodePathExtraField whose
     * name CRC-32 has not yet been set (defaults to 0).
     */
    @Test(timeout = 4000)
    public void createUnicodePathExtraFieldHasZeroNameCrc() throws Throwable {
        ZipArchiveEntry.ExtraFieldParsingMode parsingMode =
                ZipArchiveEntry.ExtraFieldParsingMode.STRICT_FOR_KNOW_EXTRA_FIELDS;

        UnicodePathExtraField unicodePathExtraField =
                (UnicodePathExtraField) parsingMode.createExtraField(UnicodePathExtraField.UPATH_ID);

        assertEquals(0L, unicodePathExtraField.getNameCRC32());
    }
}
