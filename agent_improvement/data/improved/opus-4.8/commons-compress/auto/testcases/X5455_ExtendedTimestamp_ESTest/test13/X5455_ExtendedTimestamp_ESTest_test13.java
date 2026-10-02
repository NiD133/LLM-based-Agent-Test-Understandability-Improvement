package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test13 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Parsing local file data whose flags byte has only the modify-time bit (0x01)
     * set should leave that exact value visible through {@link X5455_ExtendedTimestamp#getFlags()}.
     *
     * <p>The buffer is parsed starting at offset 1 for a length of 1 byte, so only the
     * flags byte at index 1 is consumed; no timestamp values follow.</p>
     */
    @Test(timeout = 4000)
    public void parseFlagsByteWithModifyTimeBitSet() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Layout: [0] = padding (ignored), [1] = flags byte with MODIFY_TIME_BIT set.
        byte[] localFileData = new byte[2];
        localFileData[1] = X5455_ExtendedTimestamp.MODIFY_TIME_BIT;

        final int offset = 1;
        final int length = 1;
        extendedTimestamp.parseFromLocalFileData(localFileData, offset, length);

        assertEquals(X5455_ExtendedTimestamp.MODIFY_TIME_BIT, extendedTimestamp.getFlags());
    }
}
