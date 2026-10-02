package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test08 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the access time to a null Date should clear the access-time
     * timestamp: the "access time present" bit must stay unset and the flags
     * byte must remain zero.
     */
    @Test(timeout = 4000)
    public void settingNullAccessTimeLeavesAccessTimeAbsent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setAccessJavaTime((Date) null);

        assertFalse(extendedTimestamp.isBit1_accessTimePresent());
        assertEquals((byte) 0, extendedTimestamp.getFlags());
    }
}
