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
public class X5455_ExtendedTimestamp_ESTest_test02 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a modify time should flag bit0 (modify-time-present) as set, and
     * that flag must still be present after the central directory data is built.
     */
    @Test(timeout = 4000)
    public void settingModifyTimeMarksModifyTimeBitPresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        Date modifyTime = Date.from(MockInstant.ofEpochMilli(4L));
        extendedTimestamp.setModifyJavaTime(modifyTime);

        // Building the central directory data must not clear the modify-time flag.
        extendedTimestamp.getCentralDirectoryData();

        assertTrue("modify-time bit (bit0) should be present after setting a modify time",
                extendedTimestamp.isBit0_modifyTimePresent());
    }
}
