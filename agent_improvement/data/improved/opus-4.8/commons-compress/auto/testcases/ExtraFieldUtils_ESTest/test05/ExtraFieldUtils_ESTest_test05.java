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
public class ExtraFieldUtils_ESTest_test05 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Merging the central directory data of a single empty {@link UnicodePathExtraField}
     * should produce only that field's 4-byte header block: the 2-byte header id
     * (0x7075, little-endian -> 0x75, 0x70) followed by a 2-byte data length of zero.
     */
    @Test(timeout = 4000)
    public void mergeCentralDirectoryDataEmitsHeaderIdAndZeroLength() throws Throwable {
        ZipExtraField[] fields = new ZipExtraField[] { new UnicodePathExtraField() };

        byte[] merged = ExtraFieldUtils.mergeCentralDirectoryData(fields);

        byte[] expectedHeaderBlock = new byte[] {
            (byte) 0x75, (byte) 0x70, // header id 0x7075 in little-endian byte order
            (byte) 0x00, (byte) 0x00  // central directory data length: 0 bytes
        };
        assertArrayEquals(expectedHeaderBlock, merged);
    }
}
