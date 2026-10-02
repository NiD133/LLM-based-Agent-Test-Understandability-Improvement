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
public class ExtraFieldUtils_ESTest_test16 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that parsing then re-merging local file data is round-trip consistent
     * in size for a sequence of empty extra fields.
     *
     * <p>An extra field is laid out as a 4-byte header (2-byte header id + 2-byte data
     * length) followed by that many data bytes. A 13-byte all-zero buffer therefore
     * decodes as three back-to-back empty fields (header id 0x0000, data length 0,
     * consuming 4 bytes each = 12 bytes); the trailing 13th byte is too short to start
     * another field and is ignored.</p>
     *
     * <p>Merging those three fields back into local file data reproduces only the three
     * 4-byte headers, so the result is exactly 12 bytes long.</p>
     */
    @Test(timeout = 4000)
    public void parseThenMergeOfEmptyFieldsYieldsHeaderOnlyBytes() throws Throwable {
        // 13 zero bytes => three empty extra fields (4 bytes each) + 1 leftover byte.
        byte[] localFileData = new byte[13];

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(
                localFileData, true, ExtraFieldUtils.UnparseableExtraField.READ);

        byte[] mergedData = ExtraFieldUtils.mergeLocalFileDataData(parsedFields);

        // Three empty fields => 3 x 4-byte headers and no payload bytes.
        assertEquals(12, mergedData.length);
    }
}
