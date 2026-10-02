package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test33 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting an access time via {@link X5455_ExtendedTimestamp#setAccessJavaTime(java.util.Date)}
     * should flag the access-time-present bit (bit 1). Calling hashCode() afterwards must remain
     * safe and must not clear that flag.
     */
    @Test(timeout = 4000)
    public void settingAccessJavaTimeMarksAccessTimeAsPresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setAccessJavaTime(new MockDate());
        extendedTimestamp.hashCode();

        assertTrue(extendedTimestamp.isBit1_accessTimePresent());
    }
}
