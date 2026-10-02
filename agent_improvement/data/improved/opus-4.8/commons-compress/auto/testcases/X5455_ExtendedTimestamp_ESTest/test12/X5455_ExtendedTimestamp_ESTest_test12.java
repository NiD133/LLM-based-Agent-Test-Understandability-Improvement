package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test12 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Parsing reads the flags byte from the given offset. Here the flags byte
     * (value 2) has the access-time bit set, so getFlags() should report 2.
     * No timestamp bytes follow, so only the flags are retained.
     */
    @Test(timeout = 4000)
    public void parsePopulatesFlagsFromGivenOffset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // The flags byte sits at index 2; value 2 == ACCESS_TIME_BIT.
        byte[] localFileData = new byte[3];
        localFileData[2] = (byte) 2;

        int offset = 2;
        int length = 2;
        extendedTimestamp.parseFromLocalFileData(localFileData, offset, length);

        assertEquals((byte) 2, extendedTimestamp.getFlags());
    }
}
