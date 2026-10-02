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
public class X5455_ExtendedTimestamp_ESTest_test32 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Parsing with a negative length must fail, because the parser rejects any
     * length below 1 byte as "too short". Here the length argument is -55,
     * which is reported verbatim in the {@link ZipException} message.
     */
    @Test(timeout = 4000)
    public void parseWithNegativeLengthThrowsTooShortException() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        byte[] centralDirectoryData = extendedTimestamp.getCentralDirectoryData();

        int negativeOffset = -244;
        byte negativeLength = (byte) -55;
        try {
            extendedTimestamp.parseFromLocalFileData(centralDirectoryData, negativeOffset, negativeLength);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            // X5455_ExtendedTimestamp too short, only -55 bytes
            verifyException("org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp", e);
        }
    }
}
