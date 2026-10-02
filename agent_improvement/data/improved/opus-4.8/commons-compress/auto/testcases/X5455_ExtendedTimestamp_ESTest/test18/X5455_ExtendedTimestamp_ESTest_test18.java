package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test18 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting only the access-time flag bit and then building the central
     * directory data should leave the flags byte untouched.
     */
    @Test(timeout = 4000)
    public void settingAccessTimeFlagIsPreservedAfterBuildingCentralData() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setFlags(X5455_ExtendedTimestamp.ACCESS_TIME_BIT);
        extendedTimestamp.getCentralDirectoryData();

        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
