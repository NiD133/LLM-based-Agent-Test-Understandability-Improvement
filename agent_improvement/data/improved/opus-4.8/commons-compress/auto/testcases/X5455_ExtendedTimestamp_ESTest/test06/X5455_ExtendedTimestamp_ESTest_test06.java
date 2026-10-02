package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test06 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the modify time via a Date should turn on the modify-time bit
     * (bit 0, value 1) in the flags byte, and toString() should run without error.
     */
    @Test(timeout = 4000)
    public void settingModifyJavaTimeEnablesModifyTimeFlag() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        Date modifyTime = Date.from(MockInstant.ofEpochMilli(4L));

        extendedTimestamp.setModifyJavaTime(modifyTime);

        // toString() must not blow up while rendering the populated modify time.
        extendedTimestamp.toString();

        // Only the modify-time bit (MODIFY_TIME_BIT == 1) should be set.
        assertEquals((byte) 1, extendedTimestamp.getFlags());
    }
}
