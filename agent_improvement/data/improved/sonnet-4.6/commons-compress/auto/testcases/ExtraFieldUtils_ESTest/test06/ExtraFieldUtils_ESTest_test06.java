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
public class ExtraFieldUtils_ESTest_test06 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Tests that an unparseable extra field (where the claimed data length exceeds available bytes)
     * is preserved as raw bytes when the READ behavior is used during central directory parsing,
     * and that merging the central directory data of the parsed result reconstructs the original bytes.
     *
     * The input bytes represent a minimal ZIP extra field header:
     *   bytes 0-1: header ID = {0x00, 0x00}
     *   bytes 2-3: claimed data length = {0xC2, 0x00} = 194 bytes
     * Since 194 bytes of data are claimed but 0 bytes follow the 4-byte header, the field is
     * unparseable. The READ policy wraps these 4 bytes into an UnparseableExtraFieldData instead
     * of throwing an exception.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Use READ policy so unparseable extra field data is captured rather than rejected
        ExtraFieldUtils.UnparseableExtraField readPolicy = ExtraFieldUtils.UnparseableExtraField.READ;

        // Construct a 4-byte central directory extra field whose claimed length (194) exceeds
        // the available remaining bytes (0), making it unparseable
        byte[] extraFieldBytes = new byte[4];
        extraFieldBytes[2] = (byte) (-62); // low byte of claimed length: 0xC2 = 194

        // Parse as central directory data (local=false); the READ policy wraps the unparseable
        // bytes into an UnparseableExtraFieldData entry
        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(extraFieldBytes, false, readPolicy);

        // Merging the central directory data of the parsed result should reconstruct the
        // original 4 bytes exactly
        byte[] mergedData = ExtraFieldUtils.mergeCentralDirectoryData(parsedFields);

        assertEquals(4, mergedData.length);
        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) (-62), (byte) 0 }, mergedData);
    }
}
