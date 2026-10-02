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
     * Parsing extra-field data that contains one well-formed field followed by a
     * field whose declared length runs past the end of the buffer.
     *
     * <p>Layout of the 8-byte input (each extra field has a 4-byte header:
     * 2-byte header id + 2-byte length):</p>
     * <ul>
     *   <li>bytes 0-3: header id 0x0000, declared length 0 -&gt; a valid,
     *       zero-payload field that is parsed successfully (field #1).</li>
     *   <li>bytes 4-7: header id 0x0000, declared length 0xFA00 (64000) -&gt;
     *       claims far more data than remains, so it is treated as
     *       unparseable.</li>
     * </ul>
     *
     * <p>Under {@code ONLY_PARSEABLE_STRICT} the unparseable trailing field is
     * dropped rather than throwing, so exactly one field is returned.</p>
     */
    @Test(timeout = 4000)
    public void parseKeepsOnlyTheSingleParseableFieldInStrictMode() throws Throwable {
        // 8 bytes; the high byte of the second field's length is set so the field
        // claims 64000 bytes of payload, which the buffer cannot satisfy.
        byte[] extraFieldData = new byte[8];
        extraFieldData[7] = (byte) -6;

        ZipArchiveEntry.ExtraFieldParsingMode strictParseableOnly =
                ZipArchiveEntry.ExtraFieldParsingMode.ONLY_PARSEABLE_STRICT;

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(
                extraFieldData,
                false, // data comes from the central directory, not the local file header
                (ExtraFieldParsingBehavior) strictParseableOnly);

        assertEquals(1, parsedFields.length);
    }
}
