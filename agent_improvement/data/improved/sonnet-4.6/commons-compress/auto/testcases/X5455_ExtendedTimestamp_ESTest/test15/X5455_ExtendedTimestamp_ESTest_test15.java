package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Instant;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test15 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting a modify time via setModifyJavaTime() automatically
     * marks bit 0 (the modify-time-present flag) as set, and that hashCode()
     * completes without error once the modify time is populated.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Create a Date 4 milliseconds after the Unix epoch
        Instant epochPlus4ms = MockInstant.ofEpochMilli((byte) 4);
        Date modifyDate = Date.from(epochPlus4ms);

        // Setting a non-null modify time should implicitly set bit 0 (modify-time-present)
        extendedTimestamp.setModifyJavaTime(modifyDate);

        extendedTimestamp.hashCode();
        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
