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

    /**
     * Verifies that ONLY_PARSEABLE_STRICT discards a truncated extra field and
     * returns only the one valid field that preceded it.
     *
     * The 8-byte input encodes two back-to-back extra fields in central-directory
     * format (little-endian shorts):
     *
     *   Bytes 0-1: header ID = 0x0000
     *   Bytes 2-3: data length = 0x0000  (0 bytes of field data – valid)
     *   Bytes 4-5: header ID = 0x0000
     *   Bytes 6-7: data length = 0xFA00  (64000 bytes claimed, but none available)
     *
     * The second field's claimed length far exceeds the remaining buffer, so
     * ONLY_PARSEABLE_STRICT treats it as unparseable and skips it entirely,
     * leaving exactly one ZipExtraField in the result.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Build an 8-byte central-directory extra-data block.
        // The high byte of the second field's length is 0xFA (= -6 as a signed byte),
        // making the claimed length 0xFA00 = 64000, which overruns the buffer.
        byte[] extraFieldData = new byte[8];
        extraFieldData[7] = (byte) (-6); // sets second field's claimed length to 64000

        // ONLY_PARSEABLE_STRICT skips any field whose stated length overruns the buffer.
        ZipArchiveEntry.ExtraFieldParsingMode strictMode =
                ZipArchiveEntry.ExtraFieldParsingMode.ONLY_PARSEABLE_STRICT;

        // Parse as central-directory data (local = false).
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(
                extraFieldData, false, (ExtraFieldParsingBehavior) strictMode);

        // Only the first (valid) field should survive; the truncated second field is dropped.
        assertEquals(1, parsedFields.length);
    }
}
