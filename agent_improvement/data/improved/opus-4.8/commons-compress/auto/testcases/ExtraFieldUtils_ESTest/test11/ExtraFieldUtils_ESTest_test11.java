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
     * An extra field is laid out as: 2-byte header id, 2-byte block length,
     * then that many bytes of payload. Here we declare a block length of 35
     * bytes (byte index 2, little-endian) but only supply 8 bytes total, so
     * only 4 bytes of payload remain after the 4-byte header. Parsing must
     * reject this as an unparseable extra field with a ZipException.
     */
    @Test(timeout = 4000)
    public void parseRejectsBlockLengthExceedingAvailableData() throws Throwable {
        byte[] extraFieldData = new byte[8];
        extraFieldData[2] = (byte) 35; // declared block length = 35 bytes

        try {
            ExtraFieldUtils.parse(extraFieldData);
            fail("Expected a ZipException because the declared block length exceeds the available data");
        } catch (ZipException e) {
            // Message: "Bad extra field starting at 0.  Block length of 35 bytes
            //           exceeds remaining data of 4 bytes."
            verifyException("org.apache.commons.compress.archivers.zip.ExtraFieldUtils$UnparseableExtraField", e);
        }
    }
}
