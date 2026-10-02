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
public class ExtraFieldUtils_ESTest_test11 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * ZIP extra-field layout (little-endian):
     *   bytes 0-1 : header ID
     *   bytes 2-3 : block length  <-- set to 35 here
     *   bytes 4+  : field data    <-- only 4 bytes remain in the 8-byte buffer
     *
     * Because the claimed block length (35) exceeds the remaining data (4 bytes),
     * parse() must throw ZipException when using the default THROW behaviour.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // 8-byte buffer: header ID = {0,0}, block length low-byte = 35, rest = 0
        byte[] extraFieldData = new byte[8];
        extraFieldData[2] = (byte) 35; // low byte of block-length field -> claimed length = 35

        try {
            ExtraFieldUtils.parse(extraFieldData);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            //
            // Bad extra field starting at 0.  Block length of 35 bytes exceeds remaining data of 4 bytes.
            //
            verifyException("org.apache.commons.compress.archivers.zip.ExtraFieldUtils$UnparseableExtraField", e);
        }
    }
}
