package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test19 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting an access time should turn on the access-time bit (bit 1, value 2)
     * in the flags byte. Building the central directory data afterwards must not
     * disturb that flag.
     */
    @Test(timeout = 4000)
    public void settingAccessTimeSetsAccessTimeFlagBit() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setAccessTime(ZipLong.LFH_SIG);
        extendedTimestamp.getCentralDirectoryData();

        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
